package atomatic.block;

import atomatic.client.render.RenderCrystalBlock;
import atomatic.reference.Names;
import atomatic.tileentity.TileEntityCrystalPrimal;

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
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileEntityCrystalPrimal();
    }
}
