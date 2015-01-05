package atomatic.item;

import atomatic.reference.Names;
import atomatic.reference.Textures;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;

public class ItemBlockCrystal extends ItemBlock
{
    public ItemBlockCrystal(Block block)
    {
        super(block);
        setHasSubtypes(true);
    }

    @Override
    public int getMetadata(int meta)
    {
        return meta;
    }

    @Override
    public String getUnlocalizedName()
    {
        return String.format("tile.%s%s", Textures.RESOURCE_PREFIX, Names.Blocks.CRYSTAL);
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack)
    {
        return String.format("tile.%s%s.%s", Textures.RESOURCE_PREFIX, Names.Blocks.CRYSTAL, Names.Blocks.CRYSTAL_TYPES[MathHelper.clamp_int(itemStack.getItemDamage(), 0, Names.Blocks.CRYSTAL_TYPES.length - 1)]);
    }
}
