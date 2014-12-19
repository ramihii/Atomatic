package atomatic.init;

import atomatic.block.BlockA;
import atomatic.block.BlockReversedDirt;
import atomatic.reference.Names;
import atomatic.reference.Reference;

import cpw.mods.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModBlocks
{
    public static final BlockA reversedDirt = new BlockReversedDirt();

    public static void init()
    {
        GameRegistry.registerBlock(reversedDirt, Names.Blocks.REVERSED_DIRT);
    }
}
