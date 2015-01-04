package atomatic.tileentity;

import atomatic.api.AtomaticApi;
import atomatic.api.primal.AdjustEffect;
import atomatic.api.primal.Adjustment;
import atomatic.api.primal.ICrystal;
import atomatic.api.primal.PrimalObject;
import atomatic.api.primal.PrimalRecipe;

import atomatic.init.ModItems;
import atomatic.item.ItemPrimalObject;
import atomatic.reference.Names;
import atomatic.reference.Sounds;
import atomatic.reference.ThaumcraftReference;
import atomatic.util.AdjustmentHelper;
import atomatic.util.LogHelper;

import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.TileThaumcraft;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.visnet.VisNetHandler;
import thaumcraft.api.wands.IWandable;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

// TODO Add boost items
public class TileEntityPrimalAltar extends TileEntityA implements ISidedInventory, IWandable, IAspectContainer
{
    public static final int INVENTORY_SIZE = 1;
    public static final int SLOT_INVENTORY_INDEX = 0;
    public static final int[] SLOTS = new int[]{SLOT_INVENTORY_INDEX};
    public static final int INVENTORY_STACK_LIMIT = 1;
    public static final int CRYSTAL_OFFSET = 2;
    public static final int PEDESTAL_OFFSET = CRYSTAL_OFFSET * 2;
    public static final int PEDESTAL_SLOT = 0;
    public static final int MAX_VIS_DRAIN = 20;
    public static final int FREQUENCY = 5;
    public static final int SOUND_FREQUENCY = 65;
    public static final int ADJUSTMENT_FREQUENCY = 100;
    public static final int MAX_ADJUSTMENTS = 3;

    private static final int TRUE = 1;
    private static final int FALSE = -TRUE;

    private ItemStack[] inventory;
    private int ticks = 0;
    private boolean crafting = false;
    private AspectList vis = new AspectList();
    private PrimalRecipe recipe = null;
    private EntityPlayer player = null;
    private List<ChunkCoordinates> pedestals = new ArrayList<ChunkCoordinates>();
    private List<ChunkCoordinates> crystals = new ArrayList<ChunkCoordinates>();
    private ChunkCoordinates primalPedestal = null;
    private boolean runningAdjustments = false;
    private int runningAdjustmentsTicks = 0;
    private int runningAdjustmentsCount = 0;

    public TileEntityPrimalAltar()
    {
        inventory = new ItemStack[INVENTORY_SIZE];
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {
        super.readFromNBT(nbtTagCompound);
        readNBT(nbtTagCompound);
    }

    @Override
    protected void readNBT(NBTTagCompound nbtTagCompound)
    {
        NBTTagList tagList = nbtTagCompound.getTagList(Names.NBT.ITEMS, 10);
        inventory = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < tagList.tagCount(); ++i)
        {
            NBTTagCompound tagCompound = tagList.getCompoundTagAt(i);
            byte slotIndex = tagCompound.getByte("Slot");

            if (slotIndex >= 0 && slotIndex < inventory.length)
            {
                inventory[slotIndex] = ItemStack.loadItemStackFromNBT(tagCompound);
            }
        }

        ticks = nbtTagCompound.getInteger(Names.NBT.TICKS);
        crafting = nbtTagCompound.getBoolean(Names.NBT.CRAFTING);
        vis.readFromNBT(nbtTagCompound, Names.NBT.VIS);

        int recipeHash = nbtTagCompound.getInteger(Names.NBT.RECIPE);

        if (recipeHash == 0)
        {
            recipe = null;
        }
        else
        {
            recipe = AtomaticApi.getPrimalRecipeForHash(recipeHash);
        }

        if (nbtTagCompound.getBoolean(Names.NBT.NULL_PLAYER))
        {
            player = null;
        }
        else
        {
            player.readFromNBT(nbtTagCompound);
        }

        if (nbtTagCompound.getBoolean(Names.NBT.NULL_PEDESTALS))
        {
            pedestals = new ArrayList<ChunkCoordinates>();
        }
        else
        {
            NBTTagCompound compound = nbtTagCompound.getCompoundTag(Names.NBT.PEDESTALS);
            int count = compound.getInteger(Names.NBT.COUNT);

            pedestals = new ArrayList<ChunkCoordinates>();

            for (int i = 0; i <= count; i++)
            {
                NBTTagCompound cc = compound.getCompoundTag(Integer.toString(i));

                int x = cc.getInteger(Names.NBT.POS_X);
                int y = cc.getInteger(Names.NBT.POS_Y);
                int z = cc.getInteger(Names.NBT.POS_Z);

                pedestals.add(new ChunkCoordinates(x, y, z));
            }
        }

        if (nbtTagCompound.getBoolean(Names.NBT.NULL_CRYSTALS))
        {
            crystals = new ArrayList<ChunkCoordinates>();
        }
        else
        {
            NBTTagCompound compound = nbtTagCompound.getCompoundTag(Names.NBT.CRYSTALS);
            int count = compound.getInteger(Names.NBT.COUNT);

            crystals = new ArrayList<ChunkCoordinates>();

            for (int i = 0; i <= count; i++)
            {
                NBTTagCompound cc = compound.getCompoundTag(Integer.toString(i));

                int x = cc.getInteger(Names.NBT.POS_X);
                int y = cc.getInteger(Names.NBT.POS_Y);
                int z = cc.getInteger(Names.NBT.POS_Z);

                crystals.add(new ChunkCoordinates(x, y, z));
            }
        }

        if (nbtTagCompound.getBoolean(Names.NBT.NULL_PRIMAL_PEDESTAL))
        {
            primalPedestal = null;
        }
        else
        {
            NBTTagCompound compound = nbtTagCompound.getCompoundTag(Names.NBT.PRIMAL_PEDESTAL);

            int x = compound.getInteger(Names.NBT.POS_X);
            int y = compound.getInteger(Names.NBT.POS_Y);
            int z = compound.getInteger(Names.NBT.POS_Z);

            primalPedestal = new ChunkCoordinates(x, y, z);
        }

        runningAdjustments = nbtTagCompound.getBoolean(Names.NBT.RUNNING_ADJUSTMENTS);
        runningAdjustmentsTicks = nbtTagCompound.getInteger(Names.NBT.RUNNING_ADJUSTMENTS_TICKS);
        runningAdjustmentsCount = nbtTagCompound.getInteger(Names.NBT.RUNNING_ADJUSTMENTS_COUNT);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound)
    {
        super.writeToNBT(nbtTagCompound);
        writeNBT(nbtTagCompound);
    }

    @Override
    protected void writeNBT(NBTTagCompound nbtTagCompound)
    {
        NBTTagList tagList = new NBTTagList();

        for (int currentIndex = 0; currentIndex < inventory.length; ++currentIndex)
        {
            if (inventory[currentIndex] != null)
            {
                NBTTagCompound tagCompound = new NBTTagCompound();
                tagCompound.setByte("Slot", (byte) currentIndex);
                inventory[currentIndex].writeToNBT(tagCompound);
                tagList.appendTag(tagCompound);
            }
        }

        nbtTagCompound.setTag(Names.NBT.ITEMS, tagList);
        nbtTagCompound.setInteger(Names.NBT.TICKS, ticks);
        nbtTagCompound.setBoolean(Names.NBT.CRAFTING, crafting);
        vis.writeToNBT(nbtTagCompound, Names.NBT.VIS);
        nbtTagCompound.setInteger(Names.NBT.RECIPE, recipe == null ? 0 : recipe.hashCode());

        if (player == null)
        {
            nbtTagCompound.setBoolean(Names.NBT.NULL_PLAYER, true);
        }
        else
        {
            player.writeToNBT(nbtTagCompound);
            nbtTagCompound.setBoolean(Names.NBT.NULL_PLAYER, false);
        }

        if (pedestals.size() <= 0)
        {
            nbtTagCompound.setBoolean(Names.NBT.NULL_PEDESTALS, true);
        }
        else
        {
            NBTTagCompound compound = new NBTTagCompound();

            int count = 0;

            for (ChunkCoordinates coordinates : pedestals)
            {
                NBTTagCompound cc = new NBTTagCompound();

                cc.setInteger(Names.NBT.POS_X, coordinates.posX);
                cc.setInteger(Names.NBT.POS_Y, coordinates.posY);
                cc.setInteger(Names.NBT.POS_Z, coordinates.posZ);

                compound.setTag(Integer.toString(count), cc);

                count++;
            }

            compound.setInteger(Names.NBT.COUNT, count);

            nbtTagCompound.setTag(Names.NBT.PEDESTALS, compound);
            nbtTagCompound.setBoolean(Names.NBT.NULL_PEDESTALS, false);
        }

        if (crystals.size() <= 0)
        {
            nbtTagCompound.setBoolean(Names.NBT.NULL_CRYSTALS, true);
        }
        else
        {
            NBTTagCompound compound = new NBTTagCompound();

            int count = 0;

            for (ChunkCoordinates coordinates : crystals)
            {
                NBTTagCompound cc = new NBTTagCompound();

                cc.setInteger(Names.NBT.POS_X, coordinates.posX);
                cc.setInteger(Names.NBT.POS_Y, coordinates.posY);
                cc.setInteger(Names.NBT.POS_Z, coordinates.posZ);

                compound.setTag(Integer.toString(count), cc);

                count++;
            }

            compound.setInteger(Names.NBT.COUNT, count);

            nbtTagCompound.setTag(Names.NBT.CRYSTALS, compound);
            nbtTagCompound.setBoolean(Names.NBT.NULL_CRYSTALS, false);
        }

        if (primalPedestal != null)
        {
            nbtTagCompound.setBoolean(Names.NBT.PRIMAL_PEDESTAL, true);
        }
        else
        {
            NBTTagCompound compound = new NBTTagCompound();

            compound.setInteger(Names.NBT.POS_X, primalPedestal.posX);
            compound.setInteger(Names.NBT.POS_Y, primalPedestal.posY);
            compound.setInteger(Names.NBT.POS_Z, primalPedestal.posZ);

            nbtTagCompound.setTag(Names.NBT.PRIMAL_PEDESTAL, compound);
            nbtTagCompound.setBoolean(Names.NBT.NULL_PRIMAL_PEDESTAL, false);
        }

        nbtTagCompound.setBoolean(Names.NBT.RUNNING_ADJUSTMENTS, runningAdjustments);
        nbtTagCompound.setInteger(Names.NBT.RUNNING_ADJUSTMENTS_TICKS, runningAdjustmentsTicks);
        nbtTagCompound.setInteger(Names.NBT.RUNNING_ADJUSTMENTS_COUNT, runningAdjustmentsCount);
    }

    @Override
    @SuppressWarnings("rawtypes")
    public void updateEntity()
    {
        boolean needsUpdate = false;

        if (crafting)
        {
            ++ticks;
            LogHelper.debug("Tick " + ticks + " (" + toString() + ")");

            if (runningAdjustments)
            {
                ++runningAdjustmentsTicks;
                LogHelper.debug("Running adjustments tick " + ticks + " (" + toString() + ")");
            }
        }

        if (worldObj.isRemote)
        {
            if (crafting)
            {
                if (ticks == 0)
                {
                    LogHelper.debug("Playing sound " + Sounds.THAUMCRAFT_INFUSER_START + " at tick " + ticks + " (" + toString() + ")");
                    worldObj.playSound((double) xCoord, (double) yCoord, (double) zCoord, Sounds.THAUMCRAFT_INFUSER_START, 0.5F, 2.0F, false);
                }
                else if (ticks % SOUND_FREQUENCY == 0)
                {
                    LogHelper.debug("Playing sound " + Sounds.THAUMCRAFT_INFUSER + " at tick " + ticks + " (" + toString() + ")");
                    worldObj.playSound((double) xCoord, (double) yCoord, (double) zCoord, Sounds.THAUMCRAFT_INFUSER, 0.5F, 2.0F, false);
                }
            }
        }
        else
        {
            if (crafting && canCraft())
            {
                if (ticks % ADJUSTMENT_FREQUENCY == 0)
                {
                    LogHelper.debug("Started running adjustments at tick " + ticks + " (" + toString() + ")");
                    runningAdjustments = true;
                    needsUpdate = true;
                }

                boolean stabilize = false;
                int stabilizeAmount = 0;

                boolean moreMaxDrain = false;
                int moreMaxDrainAmount = 0;

                if (runningAdjustments)
                {
                    if (runningAdjustmentsTicks % FREQUENCY == 0)
                    {
                        LogHelper.debug("Running adjustment at running tick " + runningAdjustmentsTicks + " and at tick " + ticks + " (" + toString() + ")");
                        Adjustment adjustment = getRuntimeAdjustments()[runningAdjustmentsCount];

                        if (adjustment.effect == AdjustEffect.STABILIZE)
                        {
                            stabilize = true;
                            stabilizeAmount = adjustment.strength;
                        }
                        else if (adjustment.effect == AdjustEffect.MORE_MAX_DRAIN)
                        {
                            moreMaxDrain = true;
                            moreMaxDrainAmount = adjustment.strength;
                        }

                        ++runningAdjustmentsCount;
                        needsUpdate = true;
                    }

                    if (runningAdjustmentsCount >= MAX_ADJUSTMENTS || runningAdjustmentsCount >= getRuntimeAdjustments().length)
                    {
                        runningAdjustments = false;
                        needsUpdate = true;
                    }
                }

                int maxInstability = 500;

                if (stabilize)
                {
                    maxInstability += stabilizeAmount;
                }

                int random = worldObj.rand.nextInt(maxInstability);

                if (random > 40 && random < 55)
                {
                    worldObj.createExplosion(null, (double) (float) xCoord, (double) ((float) yCoord + 0.5F), (double) ((float) zCoord), 1.2F + worldObj.rand.nextFloat(), false);
                }
                else if (random < 5)
                {
                    worldObj.setBlock(xCoord, yCoord + 1, zCoord, Block.getBlockFromItem(ThaumcraftReference.fluxGas.getItem()), ThaumcraftReference.fluxGas.getItemDamage(), 3);
                }
                else if (random > 90)
                {
                    List entities = worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox((double) xCoord, (double) yCoord, (double) zCoord, (double) (xCoord + 1), (double) (yCoord + 1), (double) (zCoord + 1)).expand(9.0D, 9.0D, 9.0D));

                    if (entities != null && entities.size() > 0)
                    {
                        for (Object entity : entities)
                        {
                            if (worldObj.rand.nextFloat() < 0.25F)
                            {
                                ThaumcraftApiHelper.addStickyWarpToPlayer((EntityPlayer) entity, 2);
                            }
                            else
                            {
                                ThaumcraftApiHelper.addWarpToPlayer((EntityPlayer) entity, 2 + worldObj.rand.nextInt(7), true);
                            }
                        }
                    }
                }

                if (ticks % FREQUENCY == 0)
                {
                    int maxDrain = MAX_VIS_DRAIN;

                    if (moreMaxDrain)
                    {
                        maxDrain += moreMaxDrainAmount;
                    }

                    LogHelper.debug("Attempting to drain vis at tick " + ticks + " (" + toString() + ")");
                    Aspect aspect = vis.getAspectsSortedAmount()[vis.getAspectsSortedAmount().length - 1];
                    int visDrain = VisNetHandler.drainVis(worldObj, xCoord, yCoord, zCoord, aspect, Math.min(maxDrain, vis.getAmount(aspect)));

                    if (visDrain > 0)
                    {
                        LogHelper.debug("Drained " + visDrain + " " + aspect.getTag() + " vis (" + toString() + ")");
                        vis.reduce(aspect, visDrain);
                        needsUpdate = true;
                    }
                }

                if (vis.visSize() <= 0)
                {
                    getPrimalPedestalInventory().setInventorySlotContents(PEDESTAL_SLOT, null);
                    getPrimalPedestal().getWorldObj().markBlockForUpdate(primalPedestal.posX, primalPedestal.posY, primalPedestal.posZ);
                    getPrimalPedestal().markDirty();

                    inventory[SLOT_INVENTORY_INDEX] = recipe.getOutput().copy();

                    // TODO Find better sound worldObj.playSoundEffect((double) xCoord, (double) yCoord, (double) zCoord, Sounds.RANDOM_FIZZ, 0.9F, 1.0F);

                    if (ThaumcraftApi.getWarp(recipe.getOutput().copy()) > 0)
                    {
                        ThaumcraftApiHelper.addStickyWarpToPlayer(player, ThaumcraftApi.getWarp(recipe.getOutput().copy()));
                    }

                    ticks = 0;
                    crafting = false;
                    vis = new AspectList();
                    recipe = null;
                    player = null;
                    runningAdjustments = false;
                    runningAdjustmentsTicks = 0;
                    runningAdjustmentsCount = 0;
                    needsUpdate = true;
                }
            }

            if (crafting && !canCraft())
            {
                List entities = worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox((double) xCoord, (double) yCoord, (double) zCoord, (double) (xCoord + 1), (double) (yCoord + 1), (double) (zCoord + 1)).expand(9.0D, 9.0D, 9.0D));

                if (entities != null && entities.size() > 0)
                {
                    for (Object entity : entities)
                    {
                        if (worldObj.rand.nextFloat() < 0.25F)
                        {
                            ThaumcraftApiHelper.addStickyWarpToPlayer((EntityPlayer) entity, 4);
                        }
                        else
                        {
                            ThaumcraftApiHelper.addWarpToPlayer((EntityPlayer) entity, 4 + worldObj.rand.nextInt(6), true);
                        }
                    }
                }

                ticks = 0;
                crafting = false;
                vis = new AspectList();
                recipe = null;
                player = null;
                runningAdjustments = false;
                runningAdjustmentsTicks = 0;
                runningAdjustmentsCount = 0;
                worldObj.playSoundEffect((double) xCoord, (double) yCoord, (double) zCoord, Sounds.THAUMCRAFT_CRAFT_FAIL, 1.0F, 0.6F);
                worldObj.createExplosion(null, (double) (float) xCoord, (double) ((float) yCoord + 0.5F), (double) ((float) zCoord), 4.0F, true);
                needsUpdate = true;
            }
        }

        if (needsUpdate)
        {
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            markDirty();
        }
    }

    @Override
    public int getSizeInventory()
    {
        return inventory.length;
    }

    @Override
    public ItemStack getStackInSlot(int slot)
    {
        return inventory[slot];
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount)
    {
        if (inventory[slot] != null)
        {
            if (!worldObj.isRemote)
            {
                worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            }

            ItemStack stack;

            if (inventory[slot].stackSize <= amount)
            {
                stack = inventory[slot];
                inventory[slot] = null;
            }
            else
            {
                stack = inventory[slot].splitStack(amount);

                if (inventory[slot].stackSize == 0)
                {
                    inventory[slot] = null;
                }
            }

            markDirty();

            return stack;
        }

        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot)
    {
        if (inventory[slot] != null)
        {
            ItemStack stack = inventory[slot];
            inventory[slot] = null;

            return stack;
        }

        return null;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack)
    {
        inventory[slot] = stack;

        if (stack != null && stack.stackSize > getInventoryStackLimit())
        {
            stack.stackSize = getInventoryStackLimit();
        }

        markDirty();

        if (!worldObj.isRemote)
        {
            worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
        }
    }

    @Override
    public String getInventoryName()
    {
        return this.hasCustomName() ? this.getCustomName() : Names.Containers.PRIMAL_ALTAR;
    }

    @Override
    public boolean hasCustomInventoryName()
    {
        return this.hasCustomName();
    }

    @Override
    public int getInventoryStackLimit()
    {
        return INVENTORY_STACK_LIMIT;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player)
    {
        return worldObj.getTileEntity(xCoord, yCoord, zCoord) == this && player.getDistanceSq((double) xCoord + 0.5D, (double) yCoord + 0.5D, (double) zCoord + 0.5D) <= 64.0D;
    }

    @Override
    public void openInventory()
    {

    }

    @Override
    public void closeInventory()
    {

    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack)
    {
        return true; // TODO Maybe check for recipe
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int side)
    {
        return SLOTS;
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side)
    {
        return inventory[slot] == null;
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side)
    {
        return true;
    }

    @Override
    public int onWandRightClick(World world, ItemStack wandstack, EntityPlayer entityPlayer, int x, int y, int z, int side, int md)
    {
        if (!worldObj.isRemote)
        {
            LogHelper.debug("Wanded (" + toString() + ")");

            if (recipe != null && crafting)
            {
                if (recipe.getStartingAspectsForWand() != null)
                {
                    AspectList aspectList = new AspectList();

                    for (int i = 0; i < recipe.getStartingAspectsForWand().getAspectsSorted().length; i++)
                    {
                        aspectList.add(recipe.getStartingAspectsForWand().getAspectsSorted()[i], recipe.getStartingAspectsForWand().getAmount(recipe.getStartingAspectsForWand().getAspectsSorted()[i]) * 2);
                    }

                    ThaumcraftApiHelper.consumeVisFromWand(wandstack, entityPlayer, aspectList, true, false);
                }

                if (AtomaticApi.getPrimalRecipeWarp(recipe) > 0)
                {
                    ThaumcraftApiHelper.addWarpToPlayer(entityPlayer, AtomaticApi.getPrimalRecipeWarp(recipe) * 2, true);
                }

                // getPrimalPedestalInventory().setInventorySlotContents(PEDESTAL_SLOT, null);
                // getPrimalPedestal().getWorldObj().markBlockForUpdate(primalPedestal.posX, primalPedestal.posY, primalPedestal.posZ);
                // getPrimalPedestal().markDirty();

                // inventory[SLOT_INVENTORY_INDEX] = null;

                for (ChunkCoordinates coordinates : pedestals)
                {
                    worldObj.setBlock(coordinates.posX, coordinates.posY + 1, coordinates.posZ, Block.getBlockFromItem(ThaumcraftReference.fluxGoo.getItem()), ThaumcraftReference.fluxGoo.getItemDamage(), 3);
                }

                if (worldObj.isAirBlock(xCoord, yCoord + 1, zCoord))
                {
                    worldObj.setBlock(xCoord, yCoord + 1, zCoord, Block.getBlockFromItem(ThaumcraftReference.fluxGoo.getItem()), ThaumcraftReference.fluxGoo.getItemDamage(), 3);
                }

                if (worldObj.isAirBlock(xCoord + 1, yCoord, zCoord))
                {
                    worldObj.setBlock(xCoord + 1, yCoord, zCoord, Block.getBlockFromItem(ThaumcraftReference.fluxGas.getItem()), ThaumcraftReference.fluxGas.getItemDamage(), 3);
                }

                if (worldObj.isAirBlock(xCoord - 1, yCoord, zCoord))
                {
                    worldObj.setBlock(xCoord - 1, yCoord, zCoord, Block.getBlockFromItem(ThaumcraftReference.fluxGas.getItem()), ThaumcraftReference.fluxGas.getItemDamage(), 3);
                }

                if (worldObj.isAirBlock(xCoord, yCoord, zCoord + 1))
                {
                    worldObj.setBlock(xCoord, yCoord, zCoord + 1, Block.getBlockFromItem(ThaumcraftReference.fluxGas.getItem()), ThaumcraftReference.fluxGas.getItemDamage(), 3);
                }

                if (worldObj.isAirBlock(xCoord, yCoord, zCoord - 1))
                {
                    worldObj.setBlock(xCoord, yCoord, zCoord - 1, Block.getBlockFromItem(ThaumcraftReference.fluxGas.getItem()), ThaumcraftReference.fluxGas.getItemDamage(), 3);
                }

                ticks = 0;
                crafting = false;
                vis = new AspectList();
                recipe = null;
                player = null;
                runningAdjustments = false;
                runningAdjustmentsTicks = 0;
                runningAdjustmentsCount = 0;
                worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
                markDirty();
                worldObj.playSoundEffect((double) xCoord, (double) yCoord, (double) zCoord, Sounds.THAUMCRAFT_CRAFT_FAIL, 1.0F, 0.6F);
                worldObj.createExplosion(null, (double) (float) xCoord, (double) ((float) yCoord + 0.5F), (double) ((float) zCoord), 2.0F, false);
                LogHelper.debug("Crafting stopped (" + toString() + ")");
                return TRUE;
            }

            if (setEnvironment())
            {
                PrimalRecipe pr = AtomaticApi.getPrimalRecipe(inventory[SLOT_INVENTORY_INDEX], getPrimalObject());

                // TODO Think about adding primal reversion research as minimum required research
                if (pr != null && (pr.getResearch() == null || pr.getResearch().equals("") || ThaumcraftApiHelper.isResearchComplete(entityPlayer.getCommandSenderName(), pr.getResearch())))
                {
                    boolean lessVis = false;
                    int lessVisAmount = 0;

                    boolean lessWandVis = false;
                    int lessWandVisAmount = 0;

                    boolean noWandVis = false;

                    boolean lessWarp = false;
                    int lessWarpAmount = 0;

                    boolean noWarp = false;

                    for (Adjustment adjustment : getAdjustments())
                    {
                        if (adjustment.effect == AdjustEffect.LESS_VIS)
                        {
                            lessVis = true;
                            lessVisAmount = adjustment.strength;
                        }
                        else if (adjustment.effect == AdjustEffect.LESS_WAND_VIS)
                        {
                            lessWandVis = true;
                            lessWandVisAmount = adjustment.strength;
                        }
                        else if (adjustment.effect == AdjustEffect.NO_WAND_VIS)
                        {
                            noWandVis = true;
                        }
                        else if (adjustment.effect == AdjustEffect.LESS_WARP)
                        {
                            lessWarp = true;
                            lessWarpAmount = adjustment.strength;
                        }
                        else if (adjustment.effect == AdjustEffect.NO_WARP)
                        {
                            noWarp = true;
                        }
                    }

                    AspectList startingAspects = recipe.getStartingAspectsForWand().copy();

                    if (lessWandVis)
                    {
                        AspectList recipeAspects = recipe.getStartingAspectsForWand().copy();
                        startingAspects = new AspectList();

                        for (int i = 0; i < recipeAspects.size(); i++)
                        {
                            Aspect aspect = recipeAspects.getAspectsSorted()[i];
                            startingAspects.add(aspect, recipeAspects.getAmount(aspect) - lessWandVisAmount);
                        }
                    }

                    if (noWandVis || ThaumcraftApiHelper.consumeVisFromWand(wandstack, entityPlayer, startingAspects, true, false))
                    {
                        if (noWarp || AtomaticApi.getPrimalRecipeWarp(pr) > 0)
                        {
                            int warpAmount = AtomaticApi.getPrimalRecipeWarp(pr);

                            if (lessWarp)
                            {
                                warpAmount = Math.max(0, warpAmount - lessWarpAmount);
                            }

                            ThaumcraftApiHelper.addStickyWarpToPlayer(entityPlayer, warpAmount);

                            if (world.rand.nextInt(50) >= (AtomaticApi.getPrimalRecipeWarp(pr) + (lessWarpAmount * 3)))
                            {
                                int tempWarpAmount = Math.min(3, Math.round((float) AtomaticApi.getPrimalRecipeWarp(pr) / 2));

                                if (lessWarp)
                                {
                                    tempWarpAmount = Math.max(0, tempWarpAmount - lessWarpAmount);
                                }

                                ThaumcraftApiHelper.addWarpToPlayer(entityPlayer, tempWarpAmount, true);
                            }
                        }

                        worldObj.playSoundEffect((double) xCoord, (double) yCoord, (double) zCoord, Sounds.THAUMCRAFT_CRAFT_START, 0.7F, 1.0F);
                        recipe = pr;
                        crafting = true;
                        AspectList tempVis = recipe.getAspects().copy();

                        if (lessVis)
                        {
                            tempVis = new AspectList();

                            for (int i = 0; i < recipe.getAspects().size(); i++)
                            {
                                Aspect aspect = recipe.getAspects().getAspectsSorted()[i];
                                tempVis.add(aspect, recipe.getAspects().getAmount(aspect) - lessVisAmount);
                            }
                        }

                        vis = tempVis;
                        player = entityPlayer;
                        runningAdjustments = false;
                        runningAdjustmentsTicks = 0;
                        runningAdjustmentsCount = 0;
                        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
                        markDirty();
                        LogHelper.debug("Crafting started (" + toString() + ")");
                        return TRUE;
                    }
                }
            }

            worldObj.playSoundEffect((double) xCoord, (double) yCoord, (double) zCoord, Sounds.THAUMCRAFT_CRAFT_FAIL, 1.0F, 0.6F);

            LogHelper.debug("No recipe found (" + toString() + ")");
        }

        return FALSE;
    }

    @Override
    public ItemStack onWandRightClick(World world, ItemStack wandstack, EntityPlayer player)
    {
        // I have no idea what I should use this for
        return null;
    }

    @Override
    public void onUsingWandTick(ItemStack wandstack, EntityPlayer player, int count)
    {
        // NO-OP
    }

    @Override
    public void onWandStoppedUsing(ItemStack wandstack, World world, EntityPlayer player, int count)
    {
        // NO-OP
    }

    @Override
    public AspectList getAspects()
    {
        return vis;
    }

    @Override
    public void setAspects(AspectList aspects)
    {
        // NO-OP
    }

    @Override
    public boolean doesContainerAccept(Aspect tag)
    {
        return true;
    }

    @Override
    public int addToContainer(Aspect tag, int amount)
    {
        return 0;
    }

    @Override
    public boolean takeFromContainer(Aspect tag, int amount)
    {
        return false;
    }

    @Override
    public boolean takeFromContainer(AspectList ot)
    {
        return false;
    }

    @Override
    public boolean doesContainerContainAmount(Aspect tag, int amount)
    {
        return false;
    }

    @Override
    public boolean doesContainerContain(AspectList ot)
    {
        return false;
    }

    @Override
    public int containerContains(Aspect tag)
    {
        return 0;
    }

    public boolean isCrafting()
    {
        return crafting;
    }

    protected boolean checkEnvironment()
    {
        for (ChunkCoordinates coordinates : pedestals)
        {
            if (!isPedestal(coordinates.posX, coordinates.posY, coordinates.posZ))
            {
                return false;
            }
        }

        for (ChunkCoordinates coordinates : crystals)
        {
            if (!isCrystal(coordinates.posX, coordinates.posY, coordinates.posZ))
            {
                return false;
            }
        }

        return !(getPrimalPedestal() == null || ((IInventory) getPrimalPedestal()).getStackInSlot(PEDESTAL_SLOT).getItem() != ModItems.primalObject);
    }

    protected Adjustment[] getAdjustments()
    {
        Adjustment[] adjustments = null;

        for (int i = 0; i < pedestals.size(); i++)
        {
            ChunkCoordinates coordinates = pedestals.get(i);

            if (coordinates.equals(primalPedestal))
            {
                continue;
            }

            IInventory pedestal = getPedestalInventory(coordinates.posX, coordinates.posY, coordinates.posZ);

            if (AtomaticApi.getAdjustment(pedestal.getStackInSlot(PEDESTAL_SLOT)) != null)
            {
                if (adjustments == null)
                {
                    adjustments = new Adjustment[pedestals.size() - 1];
                }

                adjustments[i] = AtomaticApi.getAdjustment(pedestal.getStackInSlot(PEDESTAL_SLOT));
            }
        }

        return adjustments;
    }

    protected Adjustment[] getRuntimeAdjustments()
    {
        Adjustment[] temp = new Adjustment[getAdjustments().length];
        int count = 0;

        for (int i = 0; i < getAdjustments().length; i++)
        {
            if (AdjustmentHelper.isRuntimeAdjustment(getAdjustments()[i]))
            {
                temp[count] = getAdjustments()[i];
                count++;
            }
        }

        Adjustment[] adjustments = new Adjustment[count];
        System.arraycopy(temp, 0, adjustments, 0, adjustments.length);

        return adjustments;
    }

    protected boolean setEnvironment()
    {
        pedestals.clear();
        crystals.clear();

        if (isPedestal(xCoord + PEDESTAL_OFFSET, yCoord, zCoord) && isCrystal(xCoord + CRYSTAL_OFFSET, yCoord, zCoord))
        {
            pedestals.add(new ChunkCoordinates(xCoord + PEDESTAL_OFFSET, yCoord, zCoord));
            crystals.add(new ChunkCoordinates(xCoord + CRYSTAL_OFFSET, yCoord, zCoord));

            if (primalPedestal == null && ((IInventory) getPedestal(xCoord + PEDESTAL_OFFSET, yCoord, zCoord)).getStackInSlot(PEDESTAL_SLOT).getItem() == ModItems.primalObject)
            {
                primalPedestal = new ChunkCoordinates(xCoord + PEDESTAL_OFFSET, yCoord, zCoord);
            }
        }

        if (isPedestal(xCoord - PEDESTAL_OFFSET, yCoord, zCoord) && isCrystal(xCoord - CRYSTAL_OFFSET, yCoord, zCoord))
        {
            pedestals.add(new ChunkCoordinates(xCoord - PEDESTAL_OFFSET, yCoord, zCoord));
            crystals.add(new ChunkCoordinates(xCoord - CRYSTAL_OFFSET, yCoord, zCoord));

            if (primalPedestal == null && ((IInventory) getPedestal(xCoord - PEDESTAL_OFFSET, yCoord, zCoord)).getStackInSlot(PEDESTAL_SLOT).getItem() == ModItems.primalObject)
            {
                primalPedestal = new ChunkCoordinates(xCoord - PEDESTAL_OFFSET, yCoord, zCoord);
            }
        }

        if (isPedestal(xCoord, yCoord, zCoord + PEDESTAL_OFFSET) && isCrystal(xCoord, yCoord, zCoord + CRYSTAL_OFFSET))
        {
            pedestals.add(new ChunkCoordinates(xCoord, yCoord, zCoord + PEDESTAL_OFFSET));
            crystals.add(new ChunkCoordinates(xCoord, yCoord, zCoord + CRYSTAL_OFFSET));

            if (primalPedestal == null && ((IInventory) getPedestal(xCoord, yCoord, zCoord + PEDESTAL_OFFSET)).getStackInSlot(PEDESTAL_SLOT).getItem() == ModItems.primalObject)
            {
                primalPedestal = new ChunkCoordinates(xCoord, yCoord, zCoord + PEDESTAL_OFFSET);
            }
        }

        if (isPedestal(xCoord, yCoord, zCoord - PEDESTAL_OFFSET) && isCrystal(xCoord, yCoord, zCoord - CRYSTAL_OFFSET))
        {
            pedestals.add(new ChunkCoordinates(xCoord, yCoord, zCoord - PEDESTAL_OFFSET));
            crystals.add(new ChunkCoordinates(xCoord, yCoord, zCoord - CRYSTAL_OFFSET));

            if (primalPedestal == null && ((IInventory) getPedestal(xCoord, yCoord, zCoord - PEDESTAL_OFFSET)).getStackInSlot(PEDESTAL_SLOT).getItem() == ModItems.primalObject)
            {
                primalPedestal = new ChunkCoordinates(xCoord, yCoord, zCoord - PEDESTAL_OFFSET);
            }
        }

        return primalPedestal != null;
    }

    protected boolean canCraft()
    {
        return checkEnvironment() && AtomaticApi.getPrimalRecipe(inventory[SLOT_INVENTORY_INDEX], getPrimalObject()) != null && recipe != null && recipe.equals(AtomaticApi.getPrimalRecipe(inventory[SLOT_INVENTORY_INDEX], getPrimalObject()));
    }

    protected boolean isPedestal(int x, int y, int z)
    {
        return worldObj.blockExists(x, y, z) && Item.getItemFromBlock(worldObj.getBlock(x, y, z)) == ThaumcraftReference.arcanePedestal.getItem() && worldObj.getBlockMetadata(x, y, z) == ThaumcraftReference.arcanePedestal.getItemDamage() && worldObj.getTileEntity(x, y, z) instanceof ISidedInventory && worldObj.getTileEntity(x, y, z) instanceof TileThaumcraft;
    }

    protected TileThaumcraft getPedestal(int x, int y, int z)
    {
        return isPedestal(x, y, z) ? (TileThaumcraft) worldObj.getTileEntity(x, y, z) : null;
    }

    protected IInventory getPedestalInventory(int x, int y, int z)
    {
        return getPedestal(x, y, z) == null ? null : (IInventory) getPedestal(x, y, z);
    }

    protected TileThaumcraft getPrimalPedestal()
    {
        return getPedestal(primalPedestal.posX, primalPedestal.posY, primalPedestal.posZ);
    }

    protected IInventory getPrimalPedestalInventory()
    {
        return getPrimalPedestal() == null ? null : (IInventory) getPrimalPedestal();
    }

    protected PrimalObject getPrimalObject()
    {
        return getPrimalPedestalInventory() == null ? null : ItemPrimalObject.getPrimalObject(getPrimalPedestalInventory().getStackInSlot(PEDESTAL_SLOT));
    }

    protected boolean isCrystal(int x, int y, int z)
    {
        return worldObj.blockExists(x, y, z) && worldObj.getTileEntity(x, y, z) instanceof ICrystal;
    }

    protected TileEntity getCrystal(int x, int y, int z)
    {
        return isCrystal(x, y, z) ? worldObj.getTileEntity(x, y, z) : null;
    }
}
