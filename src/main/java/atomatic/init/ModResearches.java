package atomatic.init;

import atomatic.aspects.AspectStack;
import atomatic.reference.Researches;
import atomatic.reference.Textures;
import atomatic.util.LogHelper;

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
    public static ResearchItem reversedDirt;
    public static ResearchItem atom;
    public static ResearchItem quantum;

    public static AspectList atomAL = new AspectList();
    public static AspectList quantumAL = new AspectList();

    public static void register()
    {
        initCategories();
        registerResearches();
    }

    private static void initCategories()
    {
        ResearchCategories.registerCategory(Researches.CATEGORY, Textures.Thaumonomicon.THAUMONOMICON_TAB,
                                            Textures.Thaumonomicon.THAUMONOMICON_BACKGROUND);
    }

    private static void registerResearches()
    {
        reversedDirt = new ResearchItem(Researches.REVERSED_DIRT, Researches.CATEGORY, reversedDirtAL, -2, -2, 1,
                                        new ItemStack(ModBlocks.reversedDirt));
        reversedDirt.setSecondary();
        reversedDirt.setPages(new ResearchPage(Researches.Pages.REVERSED_DIRT + "1"));
        reversedDirt.registerResearchItem();

        atom = new ResearchItem(Researches.ATOM, Researches.CATEGORY, atomAL, -2, 0, 1, new ItemStack(Items.apple));
        atom.setSecondary();
        atom.setPages(new ResearchPage(Researches.Pages.ATOM + "1"));
        atom.registerResearchItem();

        quantumAL.add(Aspect.VOID, 1);
        quantumAL.add(Aspect.ENERGY, 2);
        quantumAL.add(Aspect.MECHANISM, 2);
        quantum = new ResearchItem(Researches.QUANTUM, Researches.CATEGORY, quantumAL, 0, 0, 3,
                                   new ItemStack(Items.diamond));
        quantum.setParents(Researches.ATOM);
        quantum.setSpecial();
        quantum.setPages(new ResearchPage(Researches.Pages.QUANTUM + "1"));
        quantum.registerResearchItem();

        ThaumcraftApi.addWarpToResearch(Researches.QUANTUM, 3);
    }

    private static void addSecondaryResearch(String key, int col, int row, int complexity, ItemStack icon,
                                             String[] parents,
                                             String[] parentsHidden, String[] siblings, ItemStack[] itemTriggers,
                                             String[] entityTriggers, Aspect[] aspectTriggers, ResearchPage[] pages,
                                             int warp)
    {
    }

    private static void addResearch(String key, int col, int row, int complexity, ItemStack icon, boolean isSpecial,
                                    boolean isSecondary, boolean isRound, boolean isStub, boolean isVirtual,
                                    boolean isConcealed, boolean isHidden, boolean isAutoUnlock, String[] parents,
                                    String[] parentsHidden, String[] siblings, ItemStack[] itemTriggers,
                                    String[] entityTriggers, Aspect[] aspectTriggers, ResearchPage[] pages, int warp,
                                    AspectStack... aspects)
    {
        ResearchItem researchItem = new ResearchItem(key, Researches.CATEGORY, Aspects.getResearchAspects(key), col,
                                                     row, complexity, icon);

        if (isSpecial)
        {
            researchItem.setSpecial();
        }
        else if (isRound)
        {
            researchItem.setRound();
        }
        else if (isSecondary)
        {
            researchItem.setSecondary();
        }
        else if (isStub)
        {
            researchItem.setStub();
        }
        else if (isVirtual)
        {
            researchItem.setVirtual();
        }
        else if (isConcealed)
        {
            researchItem.setConcealed();
        }
        else if (isHidden)
        {
            researchItem.setHidden();
        }
        else if (isAutoUnlock)
        {
            researchItem.setAutoUnlock();
        }

        if (parents != null && parents.length > 0)
        {
            researchItem.setParents(parents);
        }

        if (parentsHidden != null && parentsHidden.length > 0)
        {
            researchItem.setParentsHidden(parentsHidden);
        }

        if (siblings != null && siblings.length > 0)
        {
            researchItem.setSiblings(siblings);
        }

        if (itemTriggers != null && itemTriggers.length > 0)
        {
            researchItem.setItemTriggers(itemTriggers);
        }

        if (entityTriggers != null && entityTriggers.length > 0)
        {
            researchItem.setEntityTriggers(entityTriggers);
        }

        if (aspectTriggers != null && aspectTriggers.length > 0)
        {
            researchItem.setAspectTriggers(aspectTriggers);
        }

        if (pages == null || pages.length <= 0)
        {
            LogHelper.warn("Attempting to register a research with no pages (research:" + key + ")");
            return;
        }

        researchItem.setPages(pages);

        if (warp > 0)
        {
            ThaumcraftApi.addWarpToResearch(key, warp);
        }

        researchItem.registerResearchItem();
    }
}
