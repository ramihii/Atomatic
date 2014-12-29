package atomatic.util;

import atomatic.Atomatic;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import net.minecraftforge.common.util.FakePlayer;

public class SpawnHelper
{
    public static void spawnItemAtPlayer(EntityPlayer player, ItemStack stack)
    {
        if (!player.worldObj.isRemote)
        {
            if (player instanceof FakePlayer || !player.inventory.addItemStackToInventory(stack))
            {
                EntityItem entityitem = new EntityItem(player.worldObj, player.posX + 0.5D, player.posY + 0.5D, player.posZ + 0.5D, stack);
                player.worldObj.spawnEntityInWorld(entityitem);

                if (!(player instanceof FakePlayer))
                {
                    entityitem.onCollideWithPlayer(player);
                }
            }
            else
            {
                player.worldObj.playSoundAtEntity(player, "random.pop", 0.2F, ((Atomatic.random.nextFloat() - Atomatic.random.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                player.inventory.markDirty();
            }
        }
    }
}
