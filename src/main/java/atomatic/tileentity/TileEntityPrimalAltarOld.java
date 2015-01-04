package atomatic.tileentity;

import atomatic.api.AtomaticApi;
import atomatic.api.primal.PrimalObject;
import atomatic.api.primal.PrimalRecipe;

import atomatic.item.ItemPrimalObject;
import atomatic.reference.Names;
import atomatic.reference.Sounds;
import atomatic.reference.ThaumcraftReference;
import atomatic.util.InputDirection;
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
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.List;

public class TileEntityPrimalAltarOld extends TileEntityA implements ISidedInventory, IWandable, IAspectContainer
{
    public static final int INVENTORY_SIZE = 1;
    public static final int DISPLAY_SLOT_INVENTORY_INDEX = 0;
    public static final int[] SLOTS = new int[]{DISPLAY_SLOT_INVENTORY_INDEX};
    public static final int INVENTORY_STACK_LIMIT = 1;
    public static final int CRYSTAL_OFFSET = 2;
    public static final int PEDESTAL_OFFSET = CRYSTAL_OFFSET * 2;
    public static final String X_AXIS = "x";
    public static final String Z_AXIS = "z";
    public static final int PEDESTAL_SLOT = 0;
    public static final int MAX_VIS_DRAIN = 20;
    public static final int FREQUENCY = 5;
    public static final int SOUND_FREQUENCY = 65;

    private static final int TRUE = 1;
    private static final int FALSE = -TRUE;

    protected int ticks = 0;
    protected boolean crafting = false;
    protected AspectList vis = new AspectList();
    protected PrimalRecipe recipe = null;
    protected EntityPlayer player = null;

    private ItemStack[] inventory;

    public TileEntityPrimalAltarOld()
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
                int random = worldObj.rand.nextInt(100);

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
                    LogHelper.debug("Attempting to drain vis at tick " + ticks + " (" + toString() + ")");
                    Aspect aspect = vis.getAspectsSortedAmount()[vis.getAspectsSortedAmount().length - 1];
                    int visDrain = VisNetHandler.drainVis(worldObj, xCoord, yCoord, zCoord, aspect, Math.min(MAX_VIS_DRAIN, vis.getAmount(aspect)));

                    if (visDrain > 0)
                    {
                        LogHelper.debug("Drained " + visDrain + " " + aspect.getTag() + " vis (" + toString() + ")");
                        vis.reduce(aspect, visDrain);
                        needsUpdate = true;
                    }
                }

                if (vis.visSize() <= 0)
                {
                    final String axis = pedestalAxis();
                    final InputDirection direction = inputDirection();

                    getPrimalPedestal().decrStackSize(PEDESTAL_SLOT, 1);
                    getPrimalPedestalTileEntity(axis, direction).getWorldObj().markBlockForUpdate(getPrimalPedestalTileEntity(axis, direction).xCoord, getPrimalPedestalTileEntity(axis, direction).yCoord, getPrimalPedestalTileEntity(axis, direction).zCoord);
                    getPrimalPedestalTileEntity(axis, direction).markDirty();

                    getInputPedestal(axis, direction).setInventorySlotContents(PEDESTAL_SLOT, recipe.getOutput().copy());
                    getInputPedestalTileEntity(axis, direction).getWorldObj().markBlockForUpdate(getInputPedestalTileEntity(axis, direction).xCoord, getInputPedestalTileEntity(axis, direction).yCoord, getInputPedestalTileEntity(axis, direction).zCoord);
                    getInputPedestalTileEntity(axis, direction).markDirty();

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

        if(stack != null && stack.stackSize > getInventoryStackLimit())
        {
            stack.stackSize = getInventoryStackLimit();
        }

        markDirty();

        if(!worldObj.isRemote)
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
    public int onWandRightClick(World world, ItemStack wandstack, EntityPlayer player, int x, int y, int z, int side, int md)
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

                    ThaumcraftApiHelper.consumeVisFromWand(wandstack, player, aspectList, true, false);
                }

                if (AtomaticApi.getPrimalRecipeWarp(recipe) > 0)
                {
                    ThaumcraftApiHelper.addWarpToPlayer(player, AtomaticApi.getPrimalRecipeWarp(recipe) * 2, true);
                }

                final String axis = pedestalAxis();
                final InputDirection direction = inputDirection();

                getPrimalPedestal().setInventorySlotContents(PEDESTAL_SLOT, null);
                getPrimalPedestalTileEntity(axis, direction).getWorldObj().markBlockForUpdate(getPrimalPedestalTileEntity(axis, direction).xCoord, getPrimalPedestalTileEntity(axis, direction).yCoord, getPrimalPedestalTileEntity(axis, direction).zCoord);
                getPrimalPedestalTileEntity(axis, direction).markDirty();

                getInputPedestal(axis, direction).setInventorySlotContents(PEDESTAL_SLOT, null);
                getInputPedestalTileEntity(axis, direction).getWorldObj().markBlockForUpdate(getInputPedestalTileEntity(axis, direction).xCoord, getInputPedestalTileEntity(axis, direction).yCoord, getInputPedestalTileEntity(axis, direction).zCoord);
                getInputPedestalTileEntity(axis, direction).markDirty();

                worldObj.setBlock(xCoord, yCoord + 1, zCoord, Block.getBlockFromItem(ThaumcraftReference.fluxGas.getItem()), ThaumcraftReference.fluxGas.getItemDamage(), 3);
                worldObj.setBlock(xCoord + 1, yCoord, zCoord, Block.getBlockFromItem(ThaumcraftReference.fluxGas.getItem()), ThaumcraftReference.fluxGas.getItemDamage(), 3);
                worldObj.setBlock(xCoord - 1, yCoord, zCoord, Block.getBlockFromItem(ThaumcraftReference.fluxGas.getItem()), ThaumcraftReference.fluxGas.getItemDamage(), 3);
                worldObj.setBlock(xCoord, yCoord, zCoord + 1, Block.getBlockFromItem(ThaumcraftReference.fluxGas.getItem()), ThaumcraftReference.fluxGas.getItemDamage(), 3);
                worldObj.setBlock(xCoord, yCoord, zCoord - 1, Block.getBlockFromItem(ThaumcraftReference.fluxGas.getItem()), ThaumcraftReference.fluxGas.getItemDamage(), 3);
                worldObj.setBlock(getPrimalPedestalTileEntity(axis, direction).xCoord, getPrimalPedestalTileEntity(axis, direction).yCoord + 1, getPrimalPedestalTileEntity(axis, direction).zCoord, Block.getBlockFromItem(ThaumcraftReference.fluxGoo.getItem()), ThaumcraftReference.fluxGoo.getItemDamage(), 3);
                worldObj.setBlock(getInputPedestalTileEntity(axis, direction).xCoord, getInputPedestalTileEntity(axis, direction).yCoord + 1, getInputPedestalTileEntity(axis, direction).zCoord, Block.getBlockFromItem(ThaumcraftReference.fluxGoo.getItem()), ThaumcraftReference.fluxGoo.getItemDamage(), 3);

                ticks = 0;
                crafting = false;
                vis = new AspectList();
                recipe = null;
                this.player = null;
                worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
                markDirty();
                worldObj.playSoundEffect((double) xCoord, (double) yCoord, (double) zCoord, Sounds.THAUMCRAFT_CRAFT_FAIL, 1.0F, 0.6F);
                worldObj.createExplosion(null, (double) (float) xCoord, (double) ((float) yCoord + 0.5F), (double) ((float) zCoord), 2.0F, false);
                LogHelper.debug("Crafting stopped (" + toString() + ")");
                return TRUE;
            }

            PrimalRecipe pr = AtomaticApi.getPrimalRecipe(getInputStack(), getPrimalObject());

            if (pr != null && (pr.getResearch() == null || pr.getResearch().equals("") || ThaumcraftApiHelper.isResearchComplete(player.getCommandSenderName(), pr.getResearch())))
            {
                if (ThaumcraftApiHelper.consumeVisFromWand(wandstack, player, recipe.getStartingAspectsForWand(), true, false))
                {
                    if (AtomaticApi.getPrimalRecipeWarp(pr) > 0)
                    {
                        ThaumcraftApiHelper.addStickyWarpToPlayer(player, AtomaticApi.getPrimalRecipeWarp(pr));

                        if (world.rand.nextInt(50) >= AtomaticApi.getPrimalRecipeWarp(pr))
                        {
                            ThaumcraftApiHelper.addWarpToPlayer(player, Math.min(3, Math.round((float) AtomaticApi.getPrimalRecipeWarp(pr) / 2)), true);
                        }
                    }

                    worldObj.playSoundEffect((double) xCoord, (double) yCoord, (double) zCoord, Sounds.THAUMCRAFT_CRAFT_START, 0.7F, 1.0F);
                    recipe = pr;
                    crafting = true;
                    vis = recipe.getAspects().copy();
                    this.player = player;
                    worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
                    markDirty();
                    LogHelper.debug("Crafting started (" + toString() + ")");
                    return TRUE;
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

    public TileThaumcraft getInputPedestalTileEntity()
    {
        if (inputDirection() == InputDirection.NEGATIVE)
        {
            if (pedestalAxis().equals(X_AXIS))
            {
                return getPedestalTileEntity(xCoord - PEDESTAL_OFFSET, yCoord, zCoord);
            }
            else if (pedestalAxis().equals(Z_AXIS))
            {
                return getPedestalTileEntity(xCoord, yCoord, zCoord - PEDESTAL_OFFSET);
            }
        }
        else if (inputDirection() == InputDirection.POSITIVE)
        {
            if (pedestalAxis().equals(X_AXIS))
            {
                return getPedestalTileEntity(xCoord + PEDESTAL_OFFSET, yCoord, zCoord);
            }
            else if (pedestalAxis().equals(Z_AXIS))
            {
                return getPedestalTileEntity(xCoord, yCoord, zCoord + PEDESTAL_OFFSET);
            }
        }

        return null;
    }

    public TileThaumcraft getPrimalPedestalTileEntity()
    {
        if (inputDirection() == InputDirection.NEGATIVE)
        {
            if (pedestalAxis().equals(X_AXIS))
            {
                return getPedestalTileEntity(xCoord + PEDESTAL_OFFSET, yCoord, zCoord);
            }
            else if (pedestalAxis().equals(Z_AXIS))
            {
                return getPedestalTileEntity(xCoord, yCoord, zCoord + PEDESTAL_OFFSET);
            }
        }
        else if (inputDirection() == InputDirection.POSITIVE)
        {
            if (pedestalAxis().equals(X_AXIS))
            {
                return getPedestalTileEntity(xCoord - PEDESTAL_OFFSET, yCoord, zCoord);
            }
            else if (pedestalAxis().equals(Z_AXIS))
            {
                return getPedestalTileEntity(xCoord, yCoord, zCoord - PEDESTAL_OFFSET);
            }
        }

        return null;
    }

    protected InputDirection inputDirection()
    {
        InputDirection direction = null;

        if (pedestalAxis() != null && !pedestalAxis().equals(""))
        {
            if (pedestalAxis().equals(X_AXIS) && getPedestal(xCoord + PEDESTAL_OFFSET, yCoord, zCoord) != null && getPedestal(xCoord - PEDESTAL_OFFSET, yCoord, zCoord) != null)
            {
                if (AtomaticApi.primalRecipeExists(getPedestal(xCoord + PEDESTAL_OFFSET, yCoord, zCoord).getStackInSlot(PEDESTAL_SLOT), ItemPrimalObject.getPrimalObject(getPedestal(xCoord - PEDESTAL_OFFSET, yCoord, zCoord).getStackInSlot(PEDESTAL_SLOT))))
                {
                    direction = InputDirection.POSITIVE;
                }
                else if (AtomaticApi.primalRecipeExists(getPedestal(xCoord - PEDESTAL_OFFSET, yCoord, zCoord).getStackInSlot(PEDESTAL_SLOT), ItemPrimalObject.getPrimalObject(getPedestal(xCoord + PEDESTAL_OFFSET, yCoord, zCoord).getStackInSlot(PEDESTAL_SLOT))))
                {
                    direction = InputDirection.NEGATIVE;
                }
            }
            else if (pedestalAxis().equals(Z_AXIS) && getPedestal(xCoord, yCoord, zCoord + PEDESTAL_OFFSET) != null && getPedestal(xCoord, yCoord, zCoord - PEDESTAL_OFFSET) != null)
            {
                if (AtomaticApi.primalRecipeExists(getPedestal(xCoord, yCoord, zCoord + PEDESTAL_OFFSET).getStackInSlot(PEDESTAL_SLOT), ItemPrimalObject.getPrimalObject(getPedestal(xCoord, yCoord, zCoord - PEDESTAL_OFFSET).getStackInSlot(PEDESTAL_SLOT))))
                {
                    direction = InputDirection.POSITIVE;
                }
                else if (AtomaticApi.primalRecipeExists(getPedestal(xCoord, yCoord, zCoord - PEDESTAL_OFFSET).getStackInSlot(PEDESTAL_SLOT), ItemPrimalObject.getPrimalObject(getPedestal(xCoord, yCoord, zCoord + PEDESTAL_OFFSET).getStackInSlot(PEDESTAL_SLOT))))
                {
                    direction = InputDirection.NEGATIVE;
                }
            }
        }

        return direction;
    }

    protected boolean isPedestal(int x, int y, int z)
    {
        return worldObj.blockExists(x, y, z) && Item.getItemFromBlock(worldObj.getBlock(x, y, z)) == ThaumcraftReference.arcanePedestal.getItem() && worldObj.getBlockMetadata(x, y, z) == ThaumcraftReference.arcanePedestal.getItemDamage() && worldObj.getTileEntity(x, y, z) instanceof ISidedInventory;
    }

    protected TileThaumcraft getPedestalTileEntity(int x, int y, int z)
    {
        return isPedestal(x, y, z) ? (TileThaumcraft) worldObj.getTileEntity(x, y, z) : null;
    }

    protected IInventory getPedestal(int x, int y, int z)
    {
        return isPedestal(x, y, z) ? (IInventory) worldObj.getTileEntity(x, y, z) : null;
    }

    protected TileThaumcraft getInputPedestalTileEntity(String axis, InputDirection direction)
    {
        if (direction == InputDirection.NEGATIVE)
        {
            if (axis.equals(X_AXIS))
            {
                return getPedestalTileEntity(xCoord - PEDESTAL_OFFSET, yCoord, zCoord);
            }
            else if (axis.equals(Z_AXIS))
            {
                return getPedestalTileEntity(xCoord, yCoord, zCoord - PEDESTAL_OFFSET);
            }
        }
        else if (direction == InputDirection.POSITIVE)
        {
            if (axis.equals(X_AXIS))
            {
                return getPedestalTileEntity(xCoord + PEDESTAL_OFFSET, yCoord, zCoord);
            }
            else if (axis.equals(Z_AXIS))
            {
                return getPedestalTileEntity(xCoord, yCoord, zCoord + PEDESTAL_OFFSET);
            }
        }

        return null;
    }

    protected IInventory getInputPedestal()
    {
        if (inputDirection() == InputDirection.NEGATIVE)
        {
            if (pedestalAxis().equals(X_AXIS))
            {
                return getPedestal(xCoord - PEDESTAL_OFFSET, yCoord, zCoord);
            }
            else if (pedestalAxis().equals(Z_AXIS))
            {
                return getPedestal(xCoord, yCoord, zCoord - PEDESTAL_OFFSET);
            }
        }
        else if (inputDirection() == InputDirection.POSITIVE)
        {
            if (pedestalAxis().equals(X_AXIS))
            {
                return getPedestal(xCoord + PEDESTAL_OFFSET, yCoord, zCoord);
            }
            else if (pedestalAxis().equals(Z_AXIS))
            {
                return getPedestal(xCoord, yCoord, zCoord + PEDESTAL_OFFSET);
            }
        }

        return null;
    }

    protected IInventory getInputPedestal(String axis, InputDirection direction)
    {
        if (direction == InputDirection.NEGATIVE)
        {
            if (axis.equals(X_AXIS))
            {
                return getPedestal(xCoord - PEDESTAL_OFFSET, yCoord, zCoord);
            }
            else if (axis.equals(Z_AXIS))
            {
                return getPedestal(xCoord, yCoord, zCoord - PEDESTAL_OFFSET);
            }
        }
        else if (direction == InputDirection.POSITIVE)
        {
            if (axis.equals(X_AXIS))
            {
                return getPedestal(xCoord + PEDESTAL_OFFSET, yCoord, zCoord);
            }
            else if (axis.equals(Z_AXIS))
            {
                return getPedestal(xCoord, yCoord, zCoord + PEDESTAL_OFFSET);
            }
        }

        return null;
    }

    protected TileThaumcraft getPrimalPedestalTileEntity(String axis, InputDirection direction)
    {
        if (direction == InputDirection.NEGATIVE)
        {
            if (axis.equals(X_AXIS))
            {
                return getPedestalTileEntity(xCoord + PEDESTAL_OFFSET, yCoord, zCoord);
            }
            else if (axis.equals(Z_AXIS))
            {
                return getPedestalTileEntity(xCoord, yCoord, zCoord + PEDESTAL_OFFSET);
            }
        }
        else if (direction == InputDirection.POSITIVE)
        {
            if (axis.equals(X_AXIS))
            {
                return getPedestalTileEntity(xCoord - PEDESTAL_OFFSET, yCoord, zCoord);
            }
            else if (axis.equals(Z_AXIS))
            {
                return getPedestalTileEntity(xCoord, yCoord, zCoord - PEDESTAL_OFFSET);
            }
        }

        return null;
    }

    protected IInventory getPrimalPedestal()
    {
        if (inputDirection() == InputDirection.NEGATIVE)
        {
            if (pedestalAxis().equals(X_AXIS))
            {
                return getPedestal(xCoord + PEDESTAL_OFFSET, yCoord, zCoord);
            }
            else if (pedestalAxis().equals(Z_AXIS))
            {
                return getPedestal(xCoord, yCoord, zCoord + PEDESTAL_OFFSET);
            }
        }
        else if (inputDirection() == InputDirection.POSITIVE)
        {
            if (pedestalAxis().equals(X_AXIS))
            {
                return getPedestal(xCoord - PEDESTAL_OFFSET, yCoord, zCoord);
            }
            else if (pedestalAxis().equals(Z_AXIS))
            {
                return getPedestal(xCoord, yCoord, zCoord - PEDESTAL_OFFSET);
            }
        }

        return null;
    }

    protected IInventory getPrimalPedestal(String axis, InputDirection direction)
    {
        if (direction == InputDirection.NEGATIVE)
        {
            if (axis.equals(X_AXIS))
            {
                return getPedestal(xCoord + PEDESTAL_OFFSET, yCoord, zCoord);
            }
            else if (axis.equals(Z_AXIS))
            {
                return getPedestal(xCoord, yCoord, zCoord + PEDESTAL_OFFSET);
            }
        }
        else if (direction == InputDirection.POSITIVE)
        {
            if (axis.equals(X_AXIS))
            {
                return getPedestal(xCoord - PEDESTAL_OFFSET, yCoord, zCoord);
            }
            else if (axis.equals(Z_AXIS))
            {
                return getPedestal(xCoord, yCoord, zCoord - PEDESTAL_OFFSET);
            }
        }

        return null;
    }

    protected ItemStack getInputStack()
    {
        if (getInputPedestal() != null)
        {
            return getInputPedestal().getStackInSlot(PEDESTAL_SLOT);
        }

        return null;
    }

    protected ItemStack getPrimalStack()
    {
        if (getPrimalPedestal() != null)
        {
            return getPrimalPedestal().getStackInSlot(PEDESTAL_SLOT);
        }

        return null;
    }

    protected PrimalObject getPrimalObject()
    {
        if (getPrimalStack() != null)
        {
            return ItemPrimalObject.getPrimalObject(getPrimalStack());
        }

        return null;
    }

    protected String pedestalAxis()
    {
        String axis = "";

        if (isPedestal(xCoord + PEDESTAL_OFFSET, yCoord, zCoord) && isPedestal(xCoord - PEDESTAL_OFFSET, yCoord, zCoord))
        {
            axis = X_AXIS;
        }
        else if (isPedestal(xCoord, yCoord, zCoord + PEDESTAL_OFFSET) && isPedestal(xCoord, yCoord, zCoord - PEDESTAL_OFFSET))
        {
            axis = Z_AXIS;
        }

        return axis;
    }

    protected boolean canCraft()
    {
        return inputDirection() != null && AtomaticApi.getPrimalRecipe(getInputStack(), getPrimalObject()) != null && recipe != null && recipe.equals(AtomaticApi.getPrimalRecipe(getInputStack(), getPrimalObject()));
    }
}
