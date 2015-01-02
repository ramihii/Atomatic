package atomatic.tileentity;

import atomatic.api.AtomaticApi;
import atomatic.api.primal.PrimalRecipe;
import atomatic.init.ModItems;
import atomatic.item.ItemPrimalObject;
import atomatic.reference.ThaumcraftReference;
import atomatic.util.InputDirection;

import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.visnet.VisNetHandler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

// TODO Own special pedestal type for the primal object (maybe?)
// TODO Explode if crafting is interrupted >:)
// TODO Some fancy particles during the crafting
// TODO Start crafting only with wand
public class TileEntityCrystalPrimal extends TileEntity
{
    public static final int PEDESTAL_OFFSET = 2;
    public static final String X_AXIS = "x";
    public static final String Y_AXIS = "y";
    public static final int PEDESTAL_SLOT = 0;
    public static final int MAX_VIS_DRAIN = 20;

    private int ticks = 0;
    private boolean crafting = false;
    private int time = 0;
    private AspectList aspects = new AspectList();
    ItemStack target = null;

    @Override
    public void updateEntity()
    {
        if (!this.worldObj.isRemote)
        {
            boolean done = false;
            String pedestalAxis = "";
            boolean correctStructure = false;

            if (isPedestal(xCoord + PEDESTAL_OFFSET, yCoord, zCoord))
            {
                pedestalAxis = X_AXIS;
            }
            else if (isPedestal(xCoord, yCoord + PEDESTAL_OFFSET, zCoord))
            {
                pedestalAxis = Y_AXIS;
            }

            if (pedestalAxis.equals(X_AXIS))
            {
                if (isPedestal(xCoord - PEDESTAL_OFFSET, yCoord, zCoord))
                {
                    correctStructure = true;
                }
            }
            else if (pedestalAxis.equals(Y_AXIS))
            {
                if (isPedestal(xCoord, yCoord - PEDESTAL_OFFSET, zCoord))
                {
                    correctStructure = true;
                }
            }

            InputDirection inputDirection = inputDirection(pedestalAxis);

            if (correctStructure && (inputDirection != null))
            {
                if (!isCrafting() && getPrimalPedestal(pedestalAxis, inputDirection).getStackInSlot(PEDESTAL_SLOT).getItem().equals(ModItems.primalObject))
                {
                    PrimalRecipe recipe = AtomaticApi.getPrimalRecipe(getInputPedestal(pedestalAxis, inputDirection).getStackInSlot(PEDESTAL_SLOT), ItemPrimalObject.getPrimalObject(getPrimalPedestal(pedestalAxis, inputDirection).getStackInSlot(PEDESTAL_SLOT)));

                    crafting = true;
                    time = recipe.getTime();
                    aspects = recipe.getAspects();
                    target = recipe.getOutput();
                }

                if (isCrafting())
                {
                    Aspect aspect = aspects.getAspectsSortedAmount()[aspects.getAspectsSortedAmount().length - 1];
                    int visDrain = VisNetHandler.drainVis(worldObj, xCoord, yCoord, zCoord, aspect, Math.min(MAX_VIS_DRAIN, aspects.getAmount(aspect)));

                    if (visDrain > 0)
                    {
                        aspects.reduce(aspect, visDrain);
                        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
                        markDirty();
                    }

                    ticks++;
                }

                if (aspects.visSize() <= 0 && ticks >= time)
                {
                    done = true;
                }
            }
            else
            {
                if (isCrafting())
                {
                    // TODO EXPLODE!!! >:D
                    reset();
                    done = false;
                    pedestalAxis = "";
                    correctStructure = false;
                }
            }

            if (done)
            {
                getPrimalPedestal(pedestalAxis, inputDirection).setInventorySlotContents(PEDESTAL_SLOT, null);
                getInputPedestal(pedestalAxis, inputDirection).setInventorySlotContents(PEDESTAL_SLOT, target);
                reset();
                worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
                markDirty();
            }
        }
    }

    public boolean startCrafting(EntityPlayer player)
    {
        String pedestalAxis = "";
        boolean correctStructure = false;

        if (isPedestal(xCoord + PEDESTAL_OFFSET, yCoord, zCoord))
        {
            pedestalAxis = X_AXIS;
        }
        else if (isPedestal(xCoord, yCoord + PEDESTAL_OFFSET, zCoord))
        {
            pedestalAxis = Y_AXIS;
        }

        if (pedestalAxis.equals(X_AXIS))
        {
            if (isPedestal(xCoord - PEDESTAL_OFFSET, yCoord, zCoord))
            {
                correctStructure = true;
            }
        }
        else if (pedestalAxis.equals(Y_AXIS))
        {
            if (isPedestal(xCoord, yCoord - PEDESTAL_OFFSET, zCoord))
            {
                correctStructure = true;
            }
        }

        InputDirection inputDirection = inputDirection(pedestalAxis);

        if (correctStructure && (inputDirection != null))
        {
            if (!isCrafting() && getPrimalPedestal(pedestalAxis, inputDirection).getStackInSlot(PEDESTAL_SLOT).getItem().equals(ModItems.primalObject))
            {
                PrimalRecipe recipe = AtomaticApi.getPrimalRecipe(getInputPedestal(pedestalAxis, inputDirection).getStackInSlot(PEDESTAL_SLOT), ItemPrimalObject.getPrimalObject(getPrimalPedestal(pedestalAxis, inputDirection).getStackInSlot(PEDESTAL_SLOT)));

                if (ThaumcraftApiHelper.isResearchComplete(player.getCommandSenderName(), recipe.getResearch()))
                {
                    // TODO Also make it possible to drain all of the required aspects out of the wand (draws some extra vis)
                    // TODO Drain some starting aspects out of the wand
                    crafting = true;
                    time = recipe.getTime();
                    aspects = recipe.getAspects();
                    target = recipe.getOutput();
                }
            }

            if (isCrafting())
            {
                // TODO Stop crafting
            }
        }

        return false;
    }

    public boolean isCrafting()
    {
        return crafting;
    }

    private void reset()
    {
        ticks = 0;
        crafting = false;
        time = 0;
        aspects = new AspectList();
        target = null;
    }

    private InputDirection inputDirection(String pedestalAxis)
    {
        if (pedestalAxis.equals(X_AXIS))
        {
            if (AtomaticApi.primalRecipeExists(getPedestal(xCoord + PEDESTAL_OFFSET, yCoord, zCoord).getStackInSlot(PEDESTAL_SLOT)))
            {
                return InputDirection.POSITIVE;
            }
            else if (AtomaticApi.primalRecipeExists(getPedestal(xCoord - PEDESTAL_OFFSET, yCoord, zCoord).getStackInSlot(PEDESTAL_SLOT)))
            {
                return InputDirection.NEGATIVE;
            }
        }
        else if (pedestalAxis.equals(Y_AXIS))
        {
            if (AtomaticApi.primalRecipeExists(getPedestal(xCoord, yCoord + PEDESTAL_OFFSET, zCoord).getStackInSlot(PEDESTAL_SLOT)))
            {
                return InputDirection.POSITIVE;
            }
            else if (AtomaticApi.primalRecipeExists(getPedestal(xCoord, yCoord - PEDESTAL_OFFSET, zCoord).getStackInSlot(PEDESTAL_SLOT)))
            {
                return InputDirection.NEGATIVE;
            }
        }

        return null;
    }

    private boolean isPedestal(int x, int y, int z)
    {
        return worldObj.blockExists(x, y, z) && Item.getItemFromBlock(worldObj.getBlock(x, y, z)) == ThaumcraftReference.arcanePedestal.getItem() && worldObj.getBlockMetadata(x, y, z) == ThaumcraftReference.arcanePedestal.getItemDamage() && worldObj.getTileEntity(x, y, z) instanceof ISidedInventory;
    }

    private ISidedInventory getPedestal(int x, int y, int z)
    {
        return isPedestal(x, y, z) ? (ISidedInventory) worldObj.getTileEntity(x, y, z) : null;
    }

    private ISidedInventory getInputPedestal(String pedestalAxis, InputDirection inputDirection)
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

    private ISidedInventory getPrimalPedestal(String pedestalAxis, InputDirection inputDirection)
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
}
