package atomatic.init;

import atomatic.reference.Researches;
import atomatic.reference.Textures;

import thaumcraft.api.research.ResearchCategories;

public class ModResearches
{
    public static void init()
    {
        ResearchCategories.registerCategory(Researches.CATEGORY, Textures.Thaumonomicon.THAUMONOMICON_TAB, Textures.Thaumonomicon.THAUMONOMICON_BACKGROUND);
    }
}