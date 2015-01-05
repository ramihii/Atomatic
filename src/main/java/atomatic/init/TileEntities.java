package atomatic.init;

import atomatic.reference.Names;
import atomatic.tileentity.TileEntityAltarRelayBasic;
import atomatic.tileentity.TileEntityCrystalBasic;
import atomatic.tileentity.TileEntityCrystalPrimal;
import atomatic.tileentity.TileEntityPrimalAltar;

import cpw.mods.fml.common.registry.GameRegistry;

public class TileEntities
{
    public static void init()
    {
        GameRegistry.registerTileEntity(TileEntityPrimalAltar.class, Names.Blocks.PRIMAL_ALTAR);
        GameRegistry.registerTileEntity(TileEntityCrystalBasic.class, Names.Blocks.CRYSTAL + "." + Names.Blocks.CRYSTAL_TYPES[0]);
        GameRegistry.registerTileEntity(TileEntityCrystalPrimal.class, Names.Blocks.CRYSTAL + "." + Names.Blocks.CRYSTAL_TYPES[1]);
        GameRegistry.registerTileEntity(TileEntityAltarRelayBasic.class, Names.Blocks.ALTAR_RELAY + "." + Names.Blocks.ALTAR_RELAY_TYPES[0]);
    }
}
