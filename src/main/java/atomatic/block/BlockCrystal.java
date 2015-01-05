package atomatic.block;

import atomatic.client.render.RenderBlock;
import atomatic.reference.Names;
import atomatic.tileentity.TileEntityCrystalBasic;
import atomatic.tileentity.TileEntityCrystalPrimal;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import java.util.List;

public class BlockCrystal extends BlockA implements ITileEntityProvider
{
    public BlockCrystal()
    {
        super(Material.glass);
        this.setHardness(4f);
        this.setBlockName(Names.Blocks.CRYSTAL);
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void getSubBlocks(Item item, CreativeTabs creativeTabs, List list)
    {
        for (int meta = 0; meta < Names.Blocks.CRYSTAL_TYPES.length; meta++)
        {
            list.add(new ItemStack(item, 1, meta));
        }
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
        if (meta == 0)
        {
            return new TileEntityCrystalBasic();
        }
        else if (meta == 1)
        {
            return new TileEntityCrystalPrimal();
        }

        return null;
    }
}
