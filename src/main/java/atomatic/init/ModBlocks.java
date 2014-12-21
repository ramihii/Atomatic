package atomatic.init;

import atomatic.block.BlockA;
import atomatic.block.BlockInverseDirt;
import atomatic.reference.Names;
import atomatic.reference.Reference;

import cpw.mods.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModBlocks
{
    public static final BlockA inverseDirt = new BlockInverseDirt();

    public static void init()
    {
        GameRegistry.registerBlock(inverseDirt, Names.Blocks.INVERSE_DIRT);
    }
}
