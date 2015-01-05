package atomatic.block;

import atomatic.reference.Names;
import atomatic.reference.Particles;
import atomatic.reference.Sounds;
import atomatic.reference.ThaumcraftReference;
import atomatic.tileentity.TileEntityPrimalAltar;

import thaumcraft.api.TileThaumcraft;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockPrimalAltar extends BlockA implements ITileEntityProvider
{
    public BlockPrimalAltar()
    {
        super(Material.rock);
        this.setHardness(10f);
        this.setBlockName(Names.Blocks.PRIMAL_ALTAR);
    }

    // TODO Do later
    /*
    @Override
    public boolean isBlockSolid(IBlockAccess world, int x, int y, int z, int side)
    {
        return false;
    }

    @Override
    public int getRenderType()
    {
        return RenderCrystalBlock.ID;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }
    */

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float par7, float par8, float par9)
    {
        if (!world.isRemote)
        {
            if (player.getCurrentEquippedItem() == null || !new ItemStack(player.getCurrentEquippedItem().getItem(), 1, 0).isItemEqual(ThaumcraftReference.wandCasting))
            {
                TileEntity tileEntity = world.getTileEntity(x, y, z);

                if (tileEntity != null && tileEntity instanceof TileEntityPrimalAltar)
                {
                    TileEntityPrimalAltar altar = (TileEntityPrimalAltar) tileEntity;

                    if (!player.isSneaking())
                    {
                        if (altar.getStackInSlot(TileEntityPrimalAltar.SLOT_INVENTORY_INDEX) != null)
                        {
                            altar.dropItemsAtEntity(player);
                            world.playSoundEffect((double) x, (double) y, (double) z, Sounds.RANDOM_POP, 0.2F, ((world.rand.nextFloat() - world.rand.nextFloat()) * 0.7F + 1.0F) * 1.5F);
                            return true;
                        }

                        if (player.getCurrentEquippedItem() != null)
                        {
                            ItemStack stack = player.getCurrentEquippedItem().copy();
                            stack.stackSize = 1;
                            altar.setInventorySlotContents(TileEntityPrimalAltar.SLOT_INVENTORY_INDEX, stack);
                            --player.getCurrentEquippedItem().stackSize;

                            if (player.getCurrentEquippedItem().stackSize <= 0)
                            {
                                player.setCurrentItemOrArmor(0, null);
                            }

                            player.inventory.markDirty();
                            world.playSoundEffect((double) x, (double) y, (double) z, Sounds.RANDOM_POP, 0.2F, ((world.rand.nextFloat() - world.rand.nextFloat()) * 0.7F + 1.0F) * 1.6F);
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random random)
    {
        TileEntity tileEntity = world.getTileEntity(x, y, z);

        if (tileEntity != null && tileEntity instanceof TileEntityPrimalAltar)
        {
            TileEntityPrimalAltar primalAltar = (TileEntityPrimalAltar) tileEntity;

            if (primalAltar.isCrafting())
            {
                world.spawnParticle(Particles.RED_DUST, (double) ((x + 0.5F) + (random.nextFloat() * 0.5F - 0.3F)), (double) y + 0.8F, (double) ((z + 0.5F) + (random.nextFloat() * 0.5F - 0.3F)), 0.0D, 0.0D, 0.0D);

                /* if (primalAltar.getInputPedestalTileEntity() != null)
                {
                    world.spawnParticle(Particles.FLAME, (double) ((primalAltar.getInputPedestalTileEntity().xCoord + 0.5F) + (random.nextFloat() * 0.5F - 0.3F)), (double) primalAltar.getInputPedestalTileEntity().yCoord + 0.9F, (double) ((primalAltar.getInputPedestalTileEntity().yCoord + 0.5F) + (random.nextFloat() * 0.5F - 0.3F)), 0.0D, 0.0D, 0.0D);
                } */

                if (primalAltar.getPrimalPedestal() != null)
                {
                    world.spawnParticle(Particles.ENCHANTMENT_TABLE, (double) ((primalAltar.getPrimalPedestal().xCoord + 0.5F) + (random.nextFloat() * 0.5F - 0.3F)), (double) primalAltar.getPrimalPedestal().yCoord + 0.9F, (double) ((primalAltar.getPrimalPedestal().yCoord + 0.5F) + (random.nextFloat() * 0.5F - 0.3F)), 0.0D, 0.0D, 0.0D);
                }
            }
        }
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileEntityPrimalAltar();
    }
}
