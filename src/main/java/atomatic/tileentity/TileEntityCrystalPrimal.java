package atomatic.tileentity;

import atomatic.reference.ThaumcraftReference;

import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;

public class TileEntityCrystalPrimal extends TileEntity
{
    @Override
    public void updateEntity()
    {
        if (!this.worldObj.isRemote)
        {
            String pedestalAxis = "";
            boolean correctStructure = false;

            if (worldObj.blockExists(xCoord + 2, yCoord, zCoord) && Item.getItemFromBlock(worldObj.getBlock(xCoord + 2, yCoord, zCoord)) == ThaumcraftReference.arcanePedestal.getItem() && worldObj.getBlockMetadata(xCoord + 2, yCoord, zCoord) == ThaumcraftReference.arcanePedestal.getItemDamage())
            {
                pedestalAxis = "x";
            }
            else if (worldObj.blockExists(xCoord, yCoord + 2, zCoord) && Item.getItemFromBlock(worldObj.getBlock(xCoord, yCoord + 2, zCoord)) == ThaumcraftReference.arcanePedestal.getItem() && worldObj.getBlockMetadata(xCoord, yCoord + 2, zCoord) == ThaumcraftReference.arcanePedestal.getItemDamage())
            {
                pedestalAxis = "y";
            }

            if (pedestalAxis.equals("x"))
            {
                if (worldObj.blockExists(xCoord - 2, yCoord, zCoord) && Item.getItemFromBlock(worldObj.getBlock(xCoord - 2, yCoord, zCoord)) == ThaumcraftReference.arcanePedestal.getItem() && worldObj.getBlockMetadata(xCoord - 2, yCoord, zCoord) == ThaumcraftReference.arcanePedestal.getItemDamage())
                {
                    correctStructure = true;
                }
            }
            else if (pedestalAxis.equals("y"))
            {
                if (worldObj.blockExists(xCoord, yCoord - 2, zCoord) && Item.getItemFromBlock(worldObj.getBlock(xCoord, yCoord - 2, zCoord)) == ThaumcraftReference.arcanePedestal.getItem() && worldObj.getBlockMetadata(xCoord, yCoord - 2, zCoord) == ThaumcraftReference.arcanePedestal.getItemDamage())
                {
                    correctStructure = true;
                }
            }
        }
    }
}
