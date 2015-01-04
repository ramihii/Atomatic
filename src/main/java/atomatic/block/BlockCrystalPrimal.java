package atomatic.block;

import atomatic.client.render.RenderCrystalBlock;
import atomatic.reference.Names;
import atomatic.reference.Particles;
import atomatic.tileentity.TileEntityCrystalPrimal;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockCrystalPrimal extends BlockA implements ITileEntityProvider
{
    public BlockCrystalPrimal()
    {
        super(Material.glass);
        this.setHardness(6f);
        this.setBlockName(Names.Blocks.CRYSTAL_PRIMAL);
    }

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

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random random)
    {
        TileEntity tileEntity = world.getTileEntity(x, y, z);

        if (tileEntity != null && tileEntity instanceof TileEntityCrystalPrimal)
        {
            if (((TileEntityCrystalPrimal) tileEntity).isCrafting())
            {
                world.spawnParticle(Particles.RED_DUST, (double) ((x + 0.5F) + (random.nextFloat() * 0.5F - 0.3F)), (double) y + 0.6F, (double) ((z + 0.5F) + (random.nextFloat() * 0.5F - 0.3F)), 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileEntityCrystalPrimal();
    }
}
