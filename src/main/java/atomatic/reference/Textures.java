package atomatic.reference;

import atomatic.util.ResourceLocationHelper;

import net.minecraft.util.ResourceLocation;

public class Textures
{
    public static final String RESOURCE_PREFIX = Reference.MOD_ID.toLowerCase() + ":";

    public static final class Thaumonomicon
    {
        private static final String RESEARCH_SHEET_LOCATION = "textures/misc/r_";

        public static final ResourceLocation THAUMONOMICON_BACKGROUND = ResourceLocationHelper.getResourceLocation(Reference.THAUMCRAFT_ID, "textures/gui/gui_researchback.png");

        public static final ResourceLocation THAUMONOMICON_TAB = ResourceLocationHelper.getResourceLocation(RESEARCH_SHEET_LOCATION + "tab.png");
    }
}
