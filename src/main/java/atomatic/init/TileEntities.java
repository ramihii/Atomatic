package atomatic.init;

import atomatic.reference.Names;
import atomatic.tileentity.TileEntityCrystalPrimal;

import cpw.mods.fml.common.registry.GameRegistry;

public class TileEntities
{
    public static void init()
    {
        GameRegistry.registerTileEntity(TileEntityCrystalPrimal.class, Names.Blocks.CRYSTAL_PRIMAL);
    }
}
