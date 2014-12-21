package atomatic.handler;

import atomatic.reference.Messages;
import atomatic.reference.Reference;
import atomatic.reference.Settings;

import net.minecraft.util.StatCollector;

import net.minecraftforge.common.config.Configuration;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

import java.io.File;

public class ConfigurationHandler
{
    public static Configuration configuration;

    public static void init(File configFile)
    {
        if (configuration == null)
        {
            configuration = new Configuration(configFile);
            loadConfiguration();
        }
    }

    private static void loadConfiguration()
    {
        Settings.General.debugMode = configuration.getBoolean(Messages.Configuration.GENERAL_DEBUG_MODE, Configuration.CATEGORY_GENERAL, false, StatCollector.translateToLocal(Messages.Configuration.GENERAL_DEBUG_MODE_COMMENT), Messages.Configuration.GENERAL_DEBUG_MODE_LABEL);

        if (configuration.hasChanged())
        {
            configuration.save();
        }
    }

    @SubscribeEvent
    public static void onOnConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if (event.modID.equalsIgnoreCase(Reference.MOD_ID))
        {
            loadConfiguration();
        }
    }
}
