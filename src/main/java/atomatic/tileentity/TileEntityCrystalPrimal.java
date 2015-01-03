package atomatic.tileentity;

import atomatic.api.AtomaticApi;
import atomatic.api.primal.PrimalObject;
import atomatic.api.primal.PrimalRecipe;

import atomatic.init.ModItems;
import atomatic.item.ItemPrimalObject;
import atomatic.reference.ThaumcraftReference;
import atomatic.util.InputDirection;
import atomatic.util.LogHelper;

import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.visnet.VisNetHandler;
import thaumcraft.api.wands.IWandable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

// TODO Own special pedestal type for the primal object (maybe?)
// TODO Explode if crafting is interrupted >:)
// TODO Some fancy particles during the crafting
// TODO Start crafting only with wand
public class TileEntityCrystalPrimal extends TileEntity implements IWandable
{
    public static final int PEDESTAL_OFFSET = 2;
    public static final String X_AXIS = "x";
    public static final String Y_AXIS = "y";
    public static final int PEDESTAL_SLOT = 0;
    public static final int MAX_VIS_DRAIN = 20;
    public static final int FREQUENCY = 10;

    private static final int TRUE = 1;
    private static final int FALSE = -TRUE;

    protected int ticks = 0;
    protected boolean crafting = false;
    protected int time = 0;
    protected AspectList aspects = new AspectList();
    protected ItemStack target = null;
    protected boolean done = false;
    protected boolean correctStructure = false;
    protected String pedestalAxis = "";
    protected InputDirection inputDirection = null;
    protected PrimalRecipe recipe = null;

    @Override
    public void updateEntity()
    {
        if (!this.worldObj.isRemote)
        {
            if (correctStructure())
            {
                if (crafting)
                {
                    if (correctCrafting())
                    {
                        if (ticks % FREQUENCY == 0)
                        {
                            if (aspects.visSize() <= 0 && ticks >= time)
                            {
                                done = true;
                                LogHelper.debug("Crafting done");
                            }
                            else
                            {
                                LogHelper.debug("Attempting to drain vis");
                                Aspect aspect = aspects.getAspectsSortedAmount()[aspects.getAspectsSortedAmount().length - 1];
                                int visDrain = VisNetHandler.drainVis(worldObj, xCoord, yCoord, zCoord, aspect, Math.min(MAX_VIS_DRAIN, aspects.getAmount(aspect)));

                                if (visDrain > 0)
                                {
                                    aspects.reduce(aspect, visDrain);
                                    worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
                                    markDirty();

                                    LogHelper.debug("Drained " + visDrain + " " + aspect.getTag() + " vis");
                                }
                            }
                        }

                        ticks++;

                        if (done)
                        {
                            getPrimalPedestal().setInventorySlotContents(PEDESTAL_SLOT, null);
                            getInputPedestal().setInventorySlotContents(PEDESTAL_SLOT, target);
                            reset();
                            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
                            markDirty();

                            LogHelper.debug("Crafting finished");
                        }
                    }
                    else
                    {
                        stopCrafting();
                    }
                }
            }
            else
            {
                if (crafting)
                {
                    stopCrafting();
                }
                else
                {
                    reset(); // TODO Check if this makes the game lag
                }
            }
        }
    }

    public boolean stopCrafting()
    {
        // TODO EXPLODE!!! >:D
        return reset();
    }

    public boolean isCrafting()
    {
        return crafting;
    }

    protected boolean reset()
    {
        ticks = 0;
        crafting = false;
        time = 0;
        aspects = new AspectList();
        target = null;
        done = false;
        pedestalAxis = "";
        correctStructure = false;
        inputDirection = null;
        recipe = null;

        return true;
    }

    protected InputDirection inputDirection()
    {
        InputDirection direction = null;

        if (pedestalAxis.equals(X_AXIS))
        {
            if (AtomaticApi.primalRecipeExists(getPedestal(xCoord + PEDESTAL_OFFSET, yCoord, zCoord).getStackInSlot(PEDESTAL_SLOT)))
            {
                direction = InputDirection.POSITIVE;
            }
            else if (AtomaticApi.primalRecipeExists(getPedestal(xCoord - PEDESTAL_OFFSET, yCoord, zCoord).getStackInSlot(PEDESTAL_SLOT)))
            {
                direction = InputDirection.NEGATIVE;
            }
        }
        else if (pedestalAxis.equals(Y_AXIS))
        {
            if (AtomaticApi.primalRecipeExists(getPedestal(xCoord, yCoord + PEDESTAL_OFFSET, zCoord).getStackInSlot(PEDESTAL_SLOT)))
            {
                direction = InputDirection.POSITIVE;
            }
            else if (AtomaticApi.primalRecipeExists(getPedestal(xCoord, yCoord - PEDESTAL_OFFSET, zCoord).getStackInSlot(PEDESTAL_SLOT)))
            {
                direction = InputDirection.NEGATIVE;
            }
        }

        return direction;
    }

    protected boolean isPedestal(int x, int y, int z)
    {
        return worldObj.blockExists(x, y, z) && Item.getItemFromBlock(worldObj.getBlock(x, y, z)) == ThaumcraftReference.arcanePedestal.getItem() && worldObj.getBlockMetadata(x, y, z) == ThaumcraftReference.arcanePedestal.getItemDamage() && worldObj.getTileEntity(x, y, z) instanceof ISidedInventory;
    }

    protected ISidedInventory getPedestal(int x, int y, int z)
    {
        return isPedestal(x, y, z) ? (ISidedInventory) worldObj.getTileEntity(x, y, z) : null;
    }

    protected ISidedInventory getInputPedestal()
    {
        if (inputDirection == InputDirection.NEGATIVE)
        {
            if (pedestalAxis.equals(X_AXIS))
            {
                return getPedestal(xCoord - PEDESTAL_OFFSET, yCoord, zCoord);
            }
            else if (pedestalAxis.equals(Y_AXIS))
            {
                return getPedestal(xCoord, yCoord - PEDESTAL_OFFSET, zCoord);
            }
        }
        else if (inputDirection == InputDirection.POSITIVE)
        {
            if (pedestalAxis.equals(X_AXIS))
            {
                return getPedestal(xCoord + PEDESTAL_OFFSET, yCoord, zCoord);
            }
            else if (pedestalAxis.equals(Y_AXIS))
            {
                return getPedestal(xCoord, yCoord + PEDESTAL_OFFSET, zCoord);
            }
        }

        return null;
    }

    protected ISidedInventory getPrimalPedestal()
    {
        if (inputDirection == InputDirection.NEGATIVE)
        {
            if (pedestalAxis.equals(X_AXIS))
            {
                return getPedestal(xCoord + PEDESTAL_OFFSET, yCoord, zCoord);
            }
            else if (pedestalAxis.equals(Y_AXIS))
            {
                return getPedestal(xCoord, yCoord + PEDESTAL_OFFSET, zCoord);
            }
        }
        else if (inputDirection == InputDirection.POSITIVE)
        {
            if (pedestalAxis.equals(X_AXIS))
            {
                return getPedestal(xCoord - PEDESTAL_OFFSET, yCoord, zCoord);
            }
            else if (pedestalAxis.equals(Y_AXIS))
            {
                return getPedestal(xCoord, yCoord - PEDESTAL_OFFSET, zCoord);
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
        else if (isPedestal(xCoord, yCoord + PEDESTAL_OFFSET, zCoord) && isPedestal(xCoord, yCoord - PEDESTAL_OFFSET, zCoord))
        {
            axis = Y_AXIS;
        }

        return axis;
    }

    protected boolean correctStructure()
    {
        correctStructure = pedestalAxis.equals(pedestalAxis());
        return correctStructure;
    }

    protected boolean initStructure()
    {
        LogHelper.debug("Initializing the primal crafting structure");
        pedestalAxis = pedestalAxis();
        return correctStructure();
    }

    protected boolean correctCrafting()
    {
        return inputDirection == inputDirection() && AtomaticApi.getPrimalRecipe(getInputStack(), getPrimalObject()) != null;

    }

    protected boolean initCrafting()
    {
        if (!initStructure())
        {
            return false;
        }

        inputDirection = inputDirection();

        LogHelper.debug("Initializing the primal crafting process");

        return (!isCrafting()) && correctCrafting();
    }

    @Override
    public int onWandRightClick(World world, ItemStack wandstack, EntityPlayer player, int x, int y, int z, int side, int md)
    {
        LogHelper.debug("Wanded");

        if (initCrafting() && ThaumcraftApiHelper.isResearchComplete(player.getCommandSenderName(), recipe.getResearch()))
        {
            // TODO Also make it possible to drain all of the required aspects out of the wand (draws some extra vis) (maybe?)
            // TODO Drain some starting aspects out of the wand (maybe?)
            crafting = true;
            time = recipe.getTime();
            aspects = recipe.getAspects();
            target = recipe.getOutput();

            LogHelper.debug("Started primal crafting for recipe " + recipe.toString());

            return TRUE;
        }

        LogHelper.debug("Didn't match any recipe");

        if (crafting)
        {
            return stopCrafting() ? TRUE : FALSE;
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
}
