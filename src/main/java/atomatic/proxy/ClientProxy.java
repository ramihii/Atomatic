package atomatic.proxy;

import atomatic.client.render.RenderAltarRelay;
import atomatic.client.render.RenderBlock;
import atomatic.client.render.RenderCrystalSimple;
import atomatic.reference.Textures;
import atomatic.tileentity.TileEntityAltarRelayBasic;
import atomatic.tileentity.TileEntityCrystalBasic;
import atomatic.tileentity.TileEntityCrystalPrimal;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy
{
    @Override
    public ClientProxy getClientProxy()
    {
        return this;
    }

    @Override
    public void registerRenderer()
    {
        RenderingRegistry.registerBlockHandler(new RenderBlock());

        // ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPrimalAltar.class, new RenderCrystalSimple(Textures.Models.TEXTURE_CRYSTAL_PRIMAL));
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCrystalBasic.class, new RenderCrystalSimple(Textures.Models.TEXTURE_CRYSTAL_BASIC));
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCrystalPrimal.class, new RenderCrystalSimple(Textures.Models.TEXTURE_CRYSTAL_PRIMAL));
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAltarRelayBasic.class, new RenderAltarRelay(Textures.Models.TEXTURE_ALTAR_RELAY_BASIC));
    }
}
