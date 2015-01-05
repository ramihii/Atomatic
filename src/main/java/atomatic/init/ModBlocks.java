package atomatic.init;

import atomatic.block.BlockA;
import atomatic.block.BlockAltarRelay;
import atomatic.block.BlockCrystal;
import atomatic.block.BlockInverseDirt;
import atomatic.block.BlockPrimalAltar;
import atomatic.item.ItemBlockAltarRelay;
import atomatic.item.ItemBlockCrystal;
import atomatic.reference.Names;
import atomatic.reference.Reference;

import cpw.mods.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModBlocks
{
    public static final BlockA inverseDirt = new BlockInverseDirt();
    public static final BlockA primalAltar = new BlockPrimalAltar();
    public static final BlockA crystal = new BlockCrystal();
    public static final BlockA altarRelay = new BlockAltarRelay();

    public static void init()
    {
        GameRegistry.registerBlock(inverseDirt, Names.Blocks.INVERSE_DIRT);
        GameRegistry.registerBlock(primalAltar, Names.Blocks.PRIMAL_ALTAR);
        GameRegistry.registerBlock(crystal, ItemBlockCrystal.class, Names.Blocks.CRYSTAL);
        GameRegistry.registerBlock(altarRelay, ItemBlockAltarRelay.class, Names.Blocks.ALTAR_RELAY);
    }
}
