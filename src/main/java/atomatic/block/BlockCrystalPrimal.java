package atomatic.block;

import atomatic.client.render.RenderBlock;
import atomatic.reference.Names;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCrystalPrimal extends BlockA implements ITileEntityProvider
{
    public BlockCrystalPrimal()
    {
        super(Material.glass);
        this.setHardness(6f);
        this.setBlockName(Names.Blocks.PRIMAL_ALTAR);
    }

    @Override
    public boolean isBlockSolid(IBlockAccess world, int x, int y, int z, int side)
    {
        return false;
    }

    @Override
    public int getRenderType()
    {
        return RenderBlock.ID;
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
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return null; // TODO new TileEntityPrimalAltar();
    }
}
