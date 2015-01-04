package atomatic.init;

import atomatic.reference.Names;
import atomatic.tileentity.TileEntityPrimalAltar;

import cpw.mods.fml.common.registry.GameRegistry;

public class TileEntities
{
    public static void init()
    {
        GameRegistry.registerTileEntity(TileEntityPrimalAltar.class, Names.Blocks.PRIMAL_ALTAR);
    }
}
