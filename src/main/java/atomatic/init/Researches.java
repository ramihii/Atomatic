package atomatic.init;

import atomatic.reference.Reference;
import atomatic.util.ResourceLocationHelper;

import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.CrucibleRecipe;
import thaumcraft.api.crafting.IArcaneRecipe;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.HashMap;

public class Researches
{
    public static final String CATEGORY_QUANTA = "QUANTA";

    ////////////////////// THAUMATURGY ////////////////////////////////////////
    public static final String VIS_POWER = "VISPOWER";

    ////////////////////// ALCHEMY ////////////////////////////////////////
    public static final String ESSENCE_CRYSTAL = "ESSENTIACRYSTAL";

    ////////////////////// ELDRITCH ////////////////////////////////////////
    public static final String PRIM_PEARL = "PRIMPEARL";

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
    public static final String WEAK_PRIM_AIR = "WEAKPRIMAIR";
    public static final String WEAK_PRIM_FIRE = "WEAKPRIMFIRE";
    public static final String WEAK_PRIM_WATER = "WEAKPRIMWATER";
    public static final String WEAK_PRIM_EARTH = "WEAKPRIMEARTH";
    public static final String WEAK_PRIM_ORDER = "WEAKPRIMORDER";
    public static final String WEAK_PRIM_ENTROPY = "WEAKPRIMENTROPY";
    public static final String PRIM_AIR = "PRIMAIR";
    public static final String PRIM_FIRE = "PRIMFIRE";
    public static final String PRIM_WATER = "PRIMWATER";
    public static final String PRIM_EARTH = "PRIMEARTH";
    public static final String PRIM_ORDER = "PRIMORDER";
    public static final String PRIM_ENTROPY = "PRIMENTROPY";
    public static final String PRIM_PRIMAL = "PRIMPRIMAL";

    public static final String PRIM_PRIMAL_CORE = PRIM_PRIMAL + "CORE";

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
        new ResearchItem(INVERSE_DIRT, CATEGORY_QUANTA, new AspectList().add(Aspect.MAGIC, 2).add(Aspect.EXCHANGE, 2).add(Aspect.EARTH, 2), -4, -2, 1, new ItemStack(ModBlocks.inverseDirt)).setSiblings(SIZE_SUSPICION).setPages(new ResearchPage(PAGE_PREFIX + INVERSE_DIRT + ".1"), new ResearchPage((CrucibleRecipe) recipes.get(INVERSE_DIRT))).registerResearchItem();
        new ResearchItem(SIZE_SUSPICION, CATEGORY_QUANTA, new AspectList(), -3, -1, 1, new ItemStack(Items.potato)).setRound().setStub().setPages(new ResearchPage(PAGE_PREFIX + SIZE_SUSPICION + ".1")).registerResearchItem();
        new ResearchItem(ATOM, CATEGORY_QUANTA, new AspectList().add(Aspect.ORDER, 6).add(Aspect.MECHANISM, 5).add(Aspect.ENERGY, 5).add(Aspect.EXCHANGE, 5).add(Aspect.ENTROPY, 5), 0, 0, 2, ResourceLocationHelper.getResourceLocation(ICON_LOCATION + ATOM.toLowerCase() + FILE_EXTENSION)).setParents(SIZE_SUSPICION).setParentsHidden(INVERSE_DIRT).setSpecial().setRound().setPages(new ResearchPage(PAGE_PREFIX + ATOM + ".1"), new ResearchPage(PAGE_PREFIX + ATOM + ".2"), new ResearchPage(QUANTUM, PAGE_PREFIX + ATOM + ".3"), new ResearchPage(QUANTUM, PAGE_PREFIX + ATOM + ".4")).registerResearchItem();
        ThaumcraftApi.addWarpToResearch(ATOM, 3);
        new ResearchItem(QUANTUM, CATEGORY_QUANTA, new AspectList().add(Aspect.ENERGY, 8).add(Aspect.MECHANISM, 8).add(Aspect.EXCHANGE, 8).add(Aspect.LIGHT, 8).add(Aspect.MOTION, 8).add(Aspect.ORDER, 9).add(Aspect.ENTROPY, 8), 1, 3, 3, ResourceLocationHelper.getResourceLocation(ICON_LOCATION + QUANTUM.toLowerCase() + FILE_EXTENSION)).setParents(ATOM).setConcealed().setSpecial().setRound().setPages(new ResearchPage(PAGE_PREFIX + QUANTUM + ".1")).registerResearchItem();
        ThaumcraftApi.addWarpToResearch(QUANTUM, 4);
        new ResearchItem(CHARGE_INVERSION, CATEGORY_QUANTA, new AspectList().add(Aspect.ENERGY, 5).add(Aspect.MECHANISM, 5).add(Aspect.EXCHANGE, 5), 2, 0, 1, ResourceLocationHelper.getResourceLocation(ICON_LOCATION + CHARGE_INVERSION.toLowerCase() + FILE_EXTENSION)).setParentsHidden(QUANTUM).setPages(new ResearchPage(PAGE_PREFIX + CHARGE_INVERSION + ".1")).registerResearchItem();
        new ResearchItem(PRIMAL_REVERSION, CATEGORY_QUANTA, new AspectList().add(Aspect.AIR, 5).add(Aspect.FIRE, 5).add(Aspect.WATER, 5).add(Aspect.EARTH, 5).add(Aspect.ORDER, 5).add(Aspect.ENTROPY, 5).add(Aspect.ELDRITCH, 5).add(Aspect.MAGIC, 5), 4, 0, 3, ResourceLocationHelper.getResourceLocation(ICON_LOCATION + PRIMAL_REVERSION.toLowerCase() + FILE_EXTENSION)).setParentsHidden(QUANTUM).setSpecial().setConcealed().setPages(new ResearchPage(PAGE_PREFIX + PRIMAL_REVERSION + ".1")).registerResearchItem();
        ThaumcraftApi.addWarpToResearch(PRIMAL_REVERSION, 5);
        new ResearchItem(RADIOACTIVITY, CATEGORY_QUANTA, new AspectList().add(Aspect.ENTROPY, 5).add(Aspect.ENERGY, 3).add(Aspect.EXCHANGE, 1), -2, 3, 2, ResourceLocationHelper.getResourceLocation(ICON_LOCATION + RADIOACTIVITY.toLowerCase() + FILE_EXTENSION)).setParents(ATOM).setParentsHidden(QUANTUM, VIS_POWER).setSecondary().setRound().setConcealed().setPages(new ResearchPage(PAGE_PREFIX + RADIOACTIVITY + ".1")).registerResearchItem();
        new ResearchItem(NUCLEAR_FISSION, CATEGORY_QUANTA, new AspectList().add(Aspect.ENTROPY, 2).add(Aspect.ORDER, 2).add(Aspect.ENERGY, 2).add(Aspect.EXCHANGE, 2), -3, 4, 2, ResourceLocationHelper.getResourceLocation(ICON_LOCATION + NUCLEAR_FISSION.toLowerCase() + FILE_EXTENSION)).setParents(RADIOACTIVITY).setSecondary().setPages(new ResearchPage(PAGE_PREFIX + NUCLEAR_FISSION + ".1")).registerResearchItem();
        new ResearchItem(NUCLEAR_FUSION, CATEGORY_QUANTA, new AspectList().add(Aspect.ENTROPY, 3).add(Aspect.ORDER, 3).add(Aspect.ENERGY, 4).add(Aspect.EXCHANGE, 3), -5, 4, 3, ResourceLocationHelper.getResourceLocation(ICON_LOCATION + NUCLEAR_FUSION.toLowerCase() + FILE_EXTENSION)).setParentsHidden(NUCLEAR_FISSION).setParents(RADIOACTIVITY).setSpecial().setConcealed().setPages(new ResearchPage(PAGE_PREFIX + NUCLEAR_FUSION + ".1")).registerResearchItem();
        new ResearchItem(WEAK_PRIM_AIR, CATEGORY_QUANTA, new AspectList().add(Aspect.AIR, 5).add(Aspect.EARTH, 3), 5, -3, 1, ResourceLocationHelper.getResourceLocation(ICON_LOCATION + WEAK_PRIM_AIR.toLowerCase() + FILE_EXTENSION)).setParents(PRIMAL_REVERSION).setSecondary().setConcealed().setPages(new ResearchPage(PAGE_PREFIX + WEAK_PRIM_AIR + ".1"), new ResearchPage((InfusionRecipe) recipes.get(WEAK_PRIM_AIR))).registerResearchItem();
        new ResearchItem(WEAK_PRIM_FIRE, CATEGORY_QUANTA, new AspectList().add(Aspect.FIRE, 5).add(Aspect.WATER, 3), 6, -2, 1, ResourceLocationHelper.getResourceLocation(ICON_LOCATION + WEAK_PRIM_FIRE.toLowerCase() + FILE_EXTENSION)).setParents(PRIMAL_REVERSION).setSecondary().setConcealed().setPages(new ResearchPage(PAGE_PREFIX + WEAK_PRIM_FIRE + ".1"), new ResearchPage((InfusionRecipe) recipes.get(WEAK_PRIM_FIRE))).registerResearchItem();
        new ResearchItem(WEAK_PRIM_WATER, CATEGORY_QUANTA, new AspectList().add(Aspect.WATER, 5).add(Aspect.ORDER, 3), 7, -1, 1, ResourceLocationHelper.getResourceLocation(ICON_LOCATION + WEAK_PRIM_WATER.toLowerCase() + FILE_EXTENSION)).setParents(PRIMAL_REVERSION).setSecondary().setPages(new ResearchPage(PAGE_PREFIX + WEAK_PRIM_WATER + ".1"), new ResearchPage((InfusionRecipe) recipes.get(WEAK_PRIM_WATER))).registerResearchItem();
        new ResearchItem(WEAK_PRIM_EARTH, CATEGORY_QUANTA, new AspectList().add(Aspect.EARTH, 5).add(Aspect.FIRE, 3), 7, 1, 1, ResourceLocationHelper.getResourceLocation(ICON_LOCATION + WEAK_PRIM_EARTH.toLowerCase() + FILE_EXTENSION)).setParents(PRIMAL_REVERSION).setSecondary().setConcealed().setPages(new ResearchPage(PAGE_PREFIX + WEAK_PRIM_EARTH + ".1"), new ResearchPage((InfusionRecipe) recipes.get(WEAK_PRIM_EARTH))).registerResearchItem();
        new ResearchItem(WEAK_PRIM_ORDER, CATEGORY_QUANTA, new AspectList().add(Aspect.ORDER, 5).add(Aspect.ENTROPY, 3), 6, 2, 1, ResourceLocationHelper.getResourceLocation(ICON_LOCATION + WEAK_PRIM_ORDER.toLowerCase() + FILE_EXTENSION)).setParents(PRIMAL_REVERSION).setSecondary().setPages(new ResearchPage(PAGE_PREFIX + WEAK_PRIM_ORDER + ".1"), new ResearchPage((InfusionRecipe) recipes.get(WEAK_PRIM_ORDER))).registerResearchItem();
        new ResearchItem(WEAK_PRIM_ENTROPY, CATEGORY_QUANTA, new AspectList().add(Aspect.ENTROPY, 5).add(Aspect.AIR, 3), 5, 3, 1, ResourceLocationHelper.getResourceLocation(ICON_LOCATION + WEAK_PRIM_ENTROPY.toLowerCase() + FILE_EXTENSION)).setParents(PRIMAL_REVERSION).setSecondary().setConcealed().setPages(new ResearchPage(PAGE_PREFIX + WEAK_PRIM_ENTROPY + ".1"), new ResearchPage((InfusionRecipe) recipes.get(WEAK_PRIM_ENTROPY))).registerResearchItem();
        new ResearchItem(PRIM_AIR, CATEGORY_QUANTA, new AspectList().add(Aspect.AIR, 15).add(Aspect.MAGIC, 5).add(Aspect.ENERGY, 5).add(Aspect.ELDRITCH, 5), 11, -2, 2, ResourceLocationHelper.getResourceLocation(ICON_LOCATION + PRIM_AIR.toLowerCase() + FILE_EXTENSION)).setParents(WEAK_PRIM_AIR).setParentsHidden(WEAK_PRIM_FIRE, WEAK_PRIM_WATER, WEAK_PRIM_EARTH, WEAK_PRIM_ORDER, WEAK_PRIM_ENTROPY, ESSENCE_CRYSTAL).setConcealed().setPages(new ResearchPage(PAGE_PREFIX + PRIM_AIR + ".1"), new ResearchPage((InfusionRecipe) recipes.get(PRIM_AIR))).registerResearchItem();
        ThaumcraftApi.addWarpToResearch(PRIM_AIR, 3);
        new ResearchItem(PRIM_FIRE, CATEGORY_QUANTA, new AspectList().add(Aspect.FIRE, 15).add(Aspect.MAGIC, 5).add(Aspect.ENERGY, 5).add(Aspect.ELDRITCH, 5), 10, -1, 2, ResourceLocationHelper.getResourceLocation(ICON_LOCATION + PRIM_FIRE.toLowerCase() + FILE_EXTENSION)).setParents(WEAK_PRIM_FIRE).setParentsHidden(WEAK_PRIM_AIR, WEAK_PRIM_WATER, WEAK_PRIM_EARTH, WEAK_PRIM_ORDER, WEAK_PRIM_ENTROPY, ESSENCE_CRYSTAL).setConcealed().setPages(new ResearchPage(PAGE_PREFIX + PRIM_FIRE + ".1"), new ResearchPage((InfusionRecipe) recipes.get(PRIM_FIRE))).registerResearchItem();
        ThaumcraftApi.addWarpToResearch(PRIM_FIRE, 3);
        new ResearchItem(PRIM_WATER, CATEGORY_QUANTA, new AspectList().add(Aspect.WATER, 15).add(Aspect.MAGIC, 5).add(Aspect.ENERGY, 5).add(Aspect.ELDRITCH, 5), 9, 0, 2, ResourceLocationHelper.getResourceLocation(ICON_LOCATION + PRIM_WATER.toLowerCase() + FILE_EXTENSION)).setParents(WEAK_PRIM_WATER).setParentsHidden(WEAK_PRIM_AIR, WEAK_PRIM_FIRE, WEAK_PRIM_EARTH, WEAK_PRIM_ORDER, WEAK_PRIM_ENTROPY, ESSENCE_CRYSTAL).setConcealed().setPages(new ResearchPage(PAGE_PREFIX + PRIM_WATER + ".1"), new ResearchPage((InfusionRecipe) recipes.get(PRIM_WATER))).registerResearchItem();
        ThaumcraftApi.addWarpToResearch(PRIM_WATER, 3);
        new ResearchItem(PRIM_EARTH, CATEGORY_QUANTA, new AspectList().add(Aspect.EARTH, 15).add(Aspect.MAGIC, 5).add(Aspect.ENERGY, 5).add(Aspect.ELDRITCH, 5), 9, 2, 2, ResourceLocationHelper.getResourceLocation(ICON_LOCATION + PRIM_EARTH.toLowerCase() + FILE_EXTENSION)).setParents(WEAK_PRIM_EARTH).setParentsHidden(WEAK_PRIM_AIR, WEAK_PRIM_FIRE, WEAK_PRIM_WATER, WEAK_PRIM_ORDER, WEAK_PRIM_ENTROPY, ESSENCE_CRYSTAL).setConcealed().setPages(new ResearchPage(PAGE_PREFIX + PRIM_EARTH + ".1"), new ResearchPage((InfusionRecipe) recipes.get(PRIM_EARTH))).registerResearchItem();
        ThaumcraftApi.addWarpToResearch(PRIM_EARTH, 3);
        new ResearchItem(PRIM_ORDER, CATEGORY_QUANTA, new AspectList().add(Aspect.ORDER, 15).add(Aspect.MAGIC, 5).add(Aspect.ENERGY, 5).add(Aspect.ELDRITCH, 5), 10, 3, 2, ResourceLocationHelper.getResourceLocation(ICON_LOCATION + PRIM_ORDER.toLowerCase() + FILE_EXTENSION)).setParents(WEAK_PRIM_ORDER).setParentsHidden(WEAK_PRIM_AIR, WEAK_PRIM_FIRE, WEAK_PRIM_WATER, WEAK_PRIM_EARTH, WEAK_PRIM_ENTROPY, ESSENCE_CRYSTAL).setConcealed().setPages(new ResearchPage(PAGE_PREFIX + PRIM_ORDER + ".1"), new ResearchPage((InfusionRecipe) recipes.get(PRIM_ORDER))).registerResearchItem();
        ThaumcraftApi.addWarpToResearch(PRIM_ORDER, 2);
        new ResearchItem(PRIM_ENTROPY, CATEGORY_QUANTA, new AspectList().add(Aspect.ENTROPY, 15).add(Aspect.MAGIC, 5).add(Aspect.ENERGY, 5).add(Aspect.ELDRITCH, 5), 11, 4, 2, ResourceLocationHelper.getResourceLocation(ICON_LOCATION + PRIM_ENTROPY.toLowerCase() + FILE_EXTENSION)).setParents(WEAK_PRIM_ENTROPY).setParentsHidden(WEAK_PRIM_AIR, WEAK_PRIM_FIRE, WEAK_PRIM_WATER, WEAK_PRIM_EARTH, WEAK_PRIM_ORDER, ESSENCE_CRYSTAL).setConcealed().setPages(new ResearchPage(PAGE_PREFIX + PRIM_ENTROPY + ".1"), new ResearchPage((InfusionRecipe) recipes.get(PRIM_ENTROPY))).registerResearchItem();
        ThaumcraftApi.addWarpToResearch(PRIM_ENTROPY, 4);
        new ResearchItem(PRIM_PRIMAL, CATEGORY_QUANTA, new AspectList().add(Aspect.AIR, 10).add(Aspect.FIRE, 10).add(Aspect.WATER, 10).add(Aspect.EARTH, 10).add(Aspect.ORDER, 10).add(Aspect.ENTROPY, 10).add(Aspect.MAGIC, 5).add(Aspect.ENERGY, 5).add(Aspect.ELDRITCH, 5), 12, 1, 3, ResourceLocationHelper.getResourceLocation(ICON_LOCATION + PRIM_PRIMAL.toLowerCase() + FILE_EXTENSION)).setParents(PRIM_AIR, PRIM_FIRE, PRIM_WATER, PRIM_EARTH, PRIM_ORDER, PRIM_ENTROPY).setParentsHidden(PRIM_PEARL).setSpecial().setConcealed().setPages(new ResearchPage(PAGE_PREFIX + PRIM_PRIMAL + ".1"), new ResearchPage((IArcaneRecipe) recipes.get(PRIM_PRIMAL_CORE)), new ResearchPage((InfusionRecipe) recipes.get(PRIM_PRIMAL))).registerResearchItem();
        ThaumcraftApi.addWarpToResearch(PRIM_PRIMAL, 6);
    }
}
