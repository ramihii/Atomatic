package atomatic.block;

import atomatic.reference.Names;
import atomatic.reference.Particles;
import atomatic.tileentity.TileEntityPrimalAltar;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
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

                if (primalAltar.getInputPedestalTileEntity() != null)
                {
                    world.spawnParticle(Particles.FLAME, (double) ((primalAltar.getInputPedestalTileEntity().xCoord + 0.5F) + (random.nextFloat() * 0.5F - 0.3F)), (double) primalAltar.getInputPedestalTileEntity().yCoord + 0.9F, (double) ((primalAltar.getInputPedestalTileEntity().yCoord + 0.5F) + (random.nextFloat() * 0.5F - 0.3F)), 0.0D, 0.0D, 0.0D);
                }

                if (primalAltar.getPrimalPedestalTileEntity() != null)
                {
                    world.spawnParticle(Particles.ENCHANTMENT_TABLE, (double) ((primalAltar.getPrimalPedestalTileEntity().xCoord + 0.5F) + (random.nextFloat() * 0.5F - 0.3F)), (double) primalAltar.getPrimalPedestalTileEntity().yCoord + 0.9F, (double) ((primalAltar.getPrimalPedestalTileEntity().yCoord + 0.5F) + (random.nextFloat() * 0.5F - 0.3F)), 0.0D, 0.0D, 0.0D);
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
