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
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.List;

// TODO Some fancy particles during the crafting
// TODO Start crafting only with wand
public class TileEntityCrystalPrimal extends TileEntityA implements IWandable, IAspectContainer
{
    public static final int PEDESTAL_OFFSET = 2;
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
                    getInputPedestal(axis, direction).setInventorySlotContents(PEDESTAL_SLOT, recipe.getOutput().copy());
                    getInputPedestalTileEntity(axis, direction).getWorldObj().markBlockForUpdate(getInputPedestalTileEntity(axis, direction).xCoord, getInputPedestalTileEntity(axis, direction).yCoord, getInputPedestalTileEntity(axis, direction).zCoord);
                    getInputPedestalTileEntity(axis, direction).markDirty();

                    worldObj.playSoundEffect((double) xCoord, (double) yCoord, (double) zCoord, Sounds.RANDOM_FIZZ, 0.9F, 1.0F);

                    ticks = 0;
                    crafting = false;
                    vis = new AspectList();
                    recipe = null;
                    needsUpdate = true;
                }
            }

            if (crafting && !canCraft())
            {
                ticks = 0;
                crafting = false;
                vis = new AspectList();
                recipe = null;
                worldObj.playSoundEffect((double) xCoord, (double) yCoord, (double) zCoord, Sounds.THAUMCRAFT_CRAFT_FAIL, 1.0F, 0.6F);
                worldObj.createExplosion(null, (double) (float) xCoord, (double) ((float) yCoord + 0.5F), (double) ((float) zCoord), 1.0F, false);
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
    public int onWandRightClick(World world, ItemStack wandstack, EntityPlayer player, int x, int y, int z, int side, int md)
    {
        if (!worldObj.isRemote)
        {
            LogHelper.debug("Wanded (" + toString() + ")");

            if (recipe != null)
            {
                if (recipe.equals(AtomaticApi.getPrimalRecipe(getInputStack(), getPrimalObject())))
                {
                    worldObj.playSoundEffect((double) xCoord, (double) yCoord, (double) zCoord, Sounds.THAUMCRAFT_CRAFT_FAIL, 1.0F, 0.6F);
                    return FALSE;
                }
            }

            PrimalRecipe pr = AtomaticApi.getPrimalRecipe(getInputStack(), getPrimalObject());

            if (pr != null && (pr.getResearch() == null || pr.getResearch().equals("") || ThaumcraftApiHelper.isResearchComplete(player.getCommandSenderName(), pr.getResearch())))
            {
                worldObj.playSoundEffect((double) xCoord, (double) yCoord, (double) zCoord, Sounds.THAUMCRAFT_CRAFT_START, 0.7F, 1.0F);
                recipe = pr;
                ThaumcraftApiHelper.consumeVisFromWand(wandstack, player, new AspectList().add(Aspect.AIR, 1).add(Aspect.FIRE, 1).add(Aspect.WATER, 1).add(Aspect.EARTH, 1).add(Aspect.ORDER, 1).add(Aspect.ENTROPY, 1), true, false);
                crafting = true;
                vis = recipe.getAspects().copy();
                worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
                markDirty();
                return TRUE;
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

    protected TileThaumcraft getInputPedestalTileEntity()
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

    protected TileThaumcraft getPrimalPedestalTileEntity()
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
