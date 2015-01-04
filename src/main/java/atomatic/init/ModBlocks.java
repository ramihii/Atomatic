package atomatic.init;

import atomatic.block.BlockA;
import atomatic.block.BlockCrystalPrimal;
import atomatic.block.BlockInverseDirt;
import atomatic.block.BlockPrimalAltar;
import atomatic.reference.Names;
import atomatic.reference.Reference;

import cpw.mods.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModBlocks
{
    public static final BlockA inverseDirt = new BlockInverseDirt();
    public static final BlockA primalAltar = new BlockPrimalAltar();
    public static final BlockA crystalPrimal = new BlockCrystalPrimal();

    public static void init()
    {
        GameRegistry.registerBlock(inverseDirt, Names.Blocks.INVERSE_DIRT);
        GameRegistry.registerBlock(primalAltar, Names.Blocks.PRIMAL_ALTAR);
        GameRegistry.registerBlock(crystalPrimal, Names.Blocks.CRYSTAL_PRIMAL);
    }
}
