package atomatic;

import atomatic.handler.ConfigurationHandler;
import atomatic.handler.CraftingHandler;
import atomatic.handler.GuiHandler;
import atomatic.init.Aspects;
import atomatic.init.ModBlocks;
import atomatic.init.ModItems;
import atomatic.init.Recipes;
import atomatic.init.Researches;
import atomatic.init.TileEntities;
import atomatic.network.PacketHandler;
import atomatic.proxy.IProxy;
import atomatic.reference.Reference;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, certificateFingerprint = Reference.FINGERPRINT, version = Reference.VERSION, dependencies = Reference.DEPENDENCIES, guiFactory = Reference.GUI_FACTORY_CLASS)
public class Atomatic
{
    @Mod.Instance(Reference.MOD_ID)
    public static Atomatic instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static IProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        ConfigurationHandler.init(event.getSuggestedConfigurationFile());

        PacketHandler.init();

        ModItems.init();

        ModBlocks.init();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());

        TileEntities.init();

        proxy.registerEventHandlers();

        CraftingHandler.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        Recipes.init();

        Aspects.init();

        Researches.init();
    }
}
