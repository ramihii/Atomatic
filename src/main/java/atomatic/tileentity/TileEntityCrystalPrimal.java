package atomatic.tileentity;

import atomatic.Atomatic;
import atomatic.api.AtomaticApi;
import atomatic.reference.ThaumcraftReference;
import atomatic.util.InputDirection;

import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;

public class TileEntityCrystalPrimal extends TileEntity
{
    private static final String X_AXIS = "x";
    private static final String Y_AXIS = "y";
    private static final int PEDESTAL_SLOT = 0;

    @Override
    public void updateEntity()
    {
        if (!this.worldObj.isRemote)
        {
            String pedestalAxis = "";
            boolean correctStructure = false;

            if (isPedestal(xCoord + 2, yCoord, zCoord))
            {
                pedestalAxis = X_AXIS;
            }
            else if (isPedestal(xCoord, yCoord + 2, zCoord))
            {
                pedestalAxis = Y_AXIS;
            }

            if (pedestalAxis.equals(X_AXIS))
            {
                if (isPedestal(xCoord - 2, yCoord, zCoord))
                {
                    correctStructure = true;
                }
            }
            else if (pedestalAxis.equals(Y_AXIS))
            {
                if (isPedestal(xCoord, yCoord - 2, zCoord))
                {
                    correctStructure = true;
                }
            }

            if (correctStructure && (inputDirection(pedestalAxis) != null))
            {

            }
        }
    }

    private InputDirection inputDirection(String pedestalAxis)
    {
        if (pedestalAxis.equals(X_AXIS))
        {
            if (AtomaticApi.primalRecipeExists(getPedestal(xCoord + 2, yCoord, zCoord).getStackInSlot(PEDESTAL_SLOT)))
            {
                return InputDirection.POSITIVE;
            }
            else if (AtomaticApi.primalRecipeExists(getPedestal(xCoord - 2, yCoord, zCoord).getStackInSlot(PEDESTAL_SLOT)))
            {
                return InputDirection.NEGATIVE;
            }
        }
        else if (pedestalAxis.equals(Y_AXIS))
        {
            if (AtomaticApi.primalRecipeExists(getPedestal(xCoord, yCoord + 2, zCoord).getStackInSlot(PEDESTAL_SLOT)))
            {
                return InputDirection.POSITIVE;
            }
            else if (AtomaticApi.primalRecipeExists(getPedestal(xCoord, yCoord - 2, zCoord).getStackInSlot(PEDESTAL_SLOT)))
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
}
