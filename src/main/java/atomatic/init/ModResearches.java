package atomatic.init;

import atomatic.reference.Researches;
import atomatic.reference.Textures;

import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

/**
 * TODO Proper research icons
 */
public class ModResearches
{
    public static ResearchItem atom;
    public static ResearchItem quantum;

    public static AspectList atomAL = new AspectList();
    public static AspectList quantumAL = new AspectList();

    public static void init()
    {
        ResearchCategories.registerCategory(Researches.CATEGORY, Textures.Thaumonomicon.THAUMONOMICON_TAB, Textures.Thaumonomicon.THAUMONOMICON_BACKGROUND);

        initResearches();
    }

    private static void initResearches()
    {
        atomAL.add(Aspect.AIR, 1);
        /* atomAL.add(Aspect.EARTH, 1);
        atomAL.add(Aspect.FIRE, 1);
        atomAL.add(Aspect.WATER, 1);
        atomAL.add(Aspect.ORDER, 1);
        atomAL.add(Aspect.ENTROPY, 1); */
        atom = new ResearchItem(Researches.ATOM, Researches.CATEGORY, atomAL, 6, 2, 2, new ItemStack(Items.apple));
        atom.setSecondary();
        atom.setPages(new ResearchPage("1"));
        atom.registerResearchItem();

        quantumAL.add(Aspect.VOID, 1);
        quantumAL.add(Aspect.ENERGY, 2);
        quantumAL.add(Aspect.MECHANISM, 2);
        quantum = new ResearchItem(Researches.QUANTUM, Researches.CATEGORY, atomAL, 7, 3, 3, new ItemStack(Items.diamond));
        quantum.setParents(Researches.ATOM);
        quantum.setSpecial();
        quantum.setPages(new ResearchPage("1"));
        quantum.registerResearchItem();

        ThaumcraftApi.addWarpToResearch(Researches.QUANTUM, 5);
    }
}