package atomatic.init;

import atomatic.reference.Reference;
import atomatic.util.ResourceLocationHelper;

import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.CrucibleRecipe;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.HashMap;

public class Researches
{
    public static final String CATEGORY_QUANTA = "QUANTA";

    private static final String PAGE_PREFIX = "tc.research_page.";

    ////////////////////// QUANTA ////////////////////////////////////////
    public static final String INVERSE_DIRT = "INVERSEDIRT";
    public static final String SIZE_SUSPICION = "SIZESUSPICION";
    public static final String ATOM = "ATOM";
    public static final String QUANTUM = "QUANTUM";

    public static HashMap<String, Object> recipes = new HashMap<String, Object>();

    public static void init()
    {
        initCategories();

        initQuantaResearch();
    }

    private static void initCategories()
    {
        ResearchCategories.registerCategory(Researches.CATEGORY_QUANTA, ResourceLocationHelper.getResourceLocation("textures/misc/r_quanta.png"), ResourceLocationHelper.getResourceLocation(Reference.THAUMCRAFT_ID, "textures/gui/gui_researchback.png"));
    }

    private static void initQuantaResearch()
    {
        new ResearchItem(INVERSE_DIRT, CATEGORY_QUANTA, new AspectList().add(Aspect.MAGIC, 2).add(Aspect.EXCHANGE, 2).add(Aspect.EARTH, 2), -4, -2, 1, new ItemStack(ModBlocks.inverseDirt)).setPages(new ResearchPage(PAGE_PREFIX + INVERSE_DIRT + ".1"), new ResearchPage((CrucibleRecipe) recipes.get(INVERSE_DIRT))).registerResearchItem();
        new ResearchItem(SIZE_SUSPICION, CATEGORY_QUANTA, new AspectList().add(Aspect.ORDER, 1).add(Aspect.EARTH, 1), -3, -1, 1, new ItemStack(Items.potato)).setParents(INVERSE_DIRT).setSecondary().setRound().setPages(new ResearchPage(PAGE_PREFIX + SIZE_SUSPICION + ".1")).registerResearchItem();
        new ResearchItem(ATOM, CATEGORY_QUANTA, new AspectList().add(Aspect.ORDER, 2).add(Aspect.MECHANISM, 2), 0, 0, 2, ResourceLocationHelper.getResourceLocation("textures/misc/r_atom.png")).setParents(SIZE_SUSPICION).setSpecial().setRound().setPages(new ResearchPage(PAGE_PREFIX + ATOM + ".1"), new ResearchPage(PAGE_PREFIX + ATOM + ".2"), new ResearchPage(QUANTUM, PAGE_PREFIX + ATOM + ".3")).registerResearchItem();
        ThaumcraftApi.addWarpToResearch(ATOM, 3);
        new ResearchItem(QUANTUM, CATEGORY_QUANTA, new AspectList().add(Aspect.ENERGY, 3).add(Aspect.MECHANISM, 3).add(Aspect.EXCHANGE, 3).add(Aspect.LIGHT, 3), 1, 3, 3, ResourceLocationHelper.getResourceLocation("textures/misc/r_quantum.png")).setParents(ATOM).setConcealed().setSpecial().setRound().setPages(new ResearchPage(PAGE_PREFIX + QUANTUM + ".1")).registerResearchItem();
        ThaumcraftApi.addWarpToResearch(QUANTUM, 4);
    }
}
