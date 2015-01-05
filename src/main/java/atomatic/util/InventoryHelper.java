package atomatic.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class InventoryHelper
{
    public static void dropItemsAtEntity(World world, int x, int y, int z, Entity entity)
    {
        TileEntity tileEntity = world.getTileEntity(x, y, z);

        if (tileEntity instanceof IInventory)
        {
            IInventory inventory = (IInventory) tileEntity;

            for (int i = 0; i < inventory.getSizeInventory(); ++i)
            {
                ItemStack stack = inventory.getStackInSlot(i);

                if (stack != null && stack.stackSize > 0)
                {
                    EntityItem entityItem = new EntityItem(world, entity.posX, entity.posY + (double) (entity.getEyeHeight() / 2.0F), entity.posZ, stack.copy());
                    world.spawnEntityInWorld(entityItem);
                    inventory.setInventorySlotContents(i, null);
                }
            }
        }
    }
}
