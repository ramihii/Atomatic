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

    ////////////////////// QUANTA ////////////////////////////////////////
    public static final String INVERSE_DIRT = "INVERSEDIRT";
    public static final String SIZE_SUSPICION = "SIZESUSPICION";
    public static final String ATOM = "ATOM";
    public static final String QUANTUM = "QUANTUM";
    public static final String CHARGE_INVERSION = "CHARGEINVERSION";
    public static final String PRIMAL_REVERSION = "PRIMALREVERSION";
    public static final String RADIOACTIVITY = "RADIOACTIVITY";
    public static final String NUCLEAR_FISSION = "NUCLEARFISSION";
    public static final String NUCLEAR_FUSION = "NUCLEARFUSION";

    private static final String PAGE_PREFIX = "tc.research_page.";
    private static final String ICON_LOCATION = "textures/misc/r_";
    private static final String FILE_EXTENSION = ".png";

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
        new ResearchItem(ATOM, CATEGORY_QUANTA, new AspectList().add(Aspect.ORDER, 2).add(Aspect.MECHANISM, 2), 0, 0, 2, ResourceLocationHelper.getResourceLocation(ICON_LOCATION + ATOM.toLowerCase() + FILE_EXTENSION)).setParents(SIZE_SUSPICION).setSpecial().setRound().setPages(new ResearchPage(PAGE_PREFIX + ATOM + ".1"), new ResearchPage(PAGE_PREFIX + ATOM + ".2"), new ResearchPage(QUANTUM, PAGE_PREFIX + ATOM + ".3"), new ResearchPage(QUANTUM, PAGE_PREFIX + ATOM + ".4")).registerResearchItem();
        ThaumcraftApi.addWarpToResearch(ATOM, 3);
        new ResearchItem(QUANTUM, CATEGORY_QUANTA, new AspectList().add(Aspect.ENERGY, 3).add(Aspect.MECHANISM, 3).add(Aspect.EXCHANGE, 3).add(Aspect.LIGHT, 3), 1, 3, 3, ResourceLocationHelper.getResourceLocation(ICON_LOCATION + QUANTUM.toLowerCase() + FILE_EXTENSION)).setParents(ATOM).setConcealed().setSpecial().setRound().setPages(new ResearchPage(PAGE_PREFIX + QUANTUM + ".1")).registerResearchItem();
        ThaumcraftApi.addWarpToResearch(QUANTUM, 4);
        new ResearchItem(CHARGE_INVERSION, CATEGORY_QUANTA, new AspectList().add(Aspect.ENERGY, 2).add(Aspect.MECHANISM, 2), 2, 0, 1, ResourceLocationHelper.getResourceLocation(ICON_LOCATION + CHARGE_INVERSION.toLowerCase() + FILE_EXTENSION)).setParentsHidden(QUANTUM).setPages(new ResearchPage(PAGE_PREFIX + CHARGE_INVERSION + ".1")).registerResearchItem();
        new ResearchItem(PRIMAL_REVERSION, CATEGORY_QUANTA, new AspectList().add(Aspect.AIR, 2).add(Aspect.FIRE, 2).add(Aspect.WATER, 2).add(Aspect.EARTH, 2).add(Aspect.ORDER, 2).add(Aspect.ENTROPY, 2).add(Aspect.ELDRITCH, 2), 4, 0, 3, ResourceLocationHelper.getResourceLocation(ICON_LOCATION + PRIMAL_REVERSION.toLowerCase() + FILE_EXTENSION)).setParentsHidden(QUANTUM, "PRIMPEARL").setSpecial().setConcealed().setPages(new ResearchPage(PAGE_PREFIX + PRIMAL_REVERSION + ".1")).registerResearchItem();
        ThaumcraftApi.addWarpToResearch(PRIMAL_REVERSION, 5);
        new ResearchItem(RADIOACTIVITY, CATEGORY_QUANTA, new AspectList().add(Aspect.ENTROPY, 5).add(Aspect.ENERGY, 3), -2, 3, 2, ResourceLocationHelper.getResourceLocation(ICON_LOCATION + RADIOACTIVITY.toLowerCase() + FILE_EXTENSION)).setParents(ATOM).setParentsHidden(QUANTUM).setRound().setConcealed().setPages(new ResearchPage(PAGE_PREFIX + RADIOACTIVITY + ".1")).registerResearchItem();
        new ResearchItem(NUCLEAR_FISSION, CATEGORY_QUANTA, new AspectList().add(Aspect.ENTROPY, 2).add(Aspect.ORDER, 2). add(Aspect.ENERGY, 2), -3, 4, 2, ResourceLocationHelper.getResourceLocation(ICON_LOCATION + NUCLEAR_FISSION.toLowerCase() + FILE_EXTENSION)).setParents(RADIOACTIVITY).setSecondary().setPages(new ResearchPage(PAGE_PREFIX + NUCLEAR_FISSION + ".1")).registerResearchItem();
        new ResearchItem(NUCLEAR_FUSION, CATEGORY_QUANTA, new AspectList().add(Aspect.ENTROPY, 3).add(Aspect.ORDER, 3).add(Aspect.ENERGY, 4), -5, 4, 3, ResourceLocationHelper.getResourceLocation(ICON_LOCATION + NUCLEAR_FUSION.toLowerCase() + FILE_EXTENSION)).setParentsHidden(NUCLEAR_FISSION).setSpecial().setConcealed().setPages(new ResearchPage(PAGE_PREFIX + NUCLEAR_FUSION + ".1")).registerResearchItem();
    }
}
