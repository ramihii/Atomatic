package atomatic.init;

import atomatic.aspects.AspectStack;
import atomatic.research.ResearchContainer;
import atomatic.util.LogHelper;

import thaumcraft.api.ItemApi;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.IArcaneRecipe;
import thaumcraft.api.crafting.ShapedArcaneRecipe;
import thaumcraft.api.crafting.ShapelessArcaneRecipe;
import thaumcraft.api.research.ResearchPage;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Thaumcraft
{
    public static final String DEFAULT_KEY = "NIL";

    public static ItemStack airShard = ItemApi.getItem("itemShard", 0);
    public static ItemStack fireShard = ItemApi.getItem("itemShard", 1);
    public static ItemStack waterShard = ItemApi.getItem("itemShard", 2);
    public static ItemStack earthShard = ItemApi.getItem("itemShard", 3);
    public static ItemStack orderShard = ItemApi.getItem("itemShard", 4);
    public static ItemStack entropyShard = ItemApi.getItem("itemShard", 5);

    public static void preInit()
    {
        Aspects.assignAspects();
    }

    public static void init()
    {
        Recipes.initRecipes();
        Researches.initResearches();
    }

    public static void postInit()
    {

    }

    public static class Aspects
    {
        public static HashMap<String, HashMap<String, AspectList>> arcaneAspects = Maps.newHashMap();
        public static HashMap<String, AspectList> researchAspects = Maps.newHashMap();

        private static void assignAspects()
        {
            initArcaneAspects();
            initResearchAspects();
        }

        private static void initArcaneAspects()
        {
        }

        private static void initResearchAspects()
        {
            addResearchAspects(Researches.REVERSED_DIRT, new AspectStack(Aspect.VOID), new AspectStack(Aspect.ENERGY),
                               new AspectStack(Aspect.EARTH));
            addResearchAspects(Researches.ATOM, AspectStack
                    .createStacks(Aspect.AIR, Aspect.EARTH, Aspect.FIRE, Aspect.WATER, Aspect.ORDER, Aspect.ENTROPY));
        }

        private static void addArcaneAspects(String research, AspectStack... aspects)
        {
            addArcaneAspects(research, DEFAULT_KEY, aspects);
        }

        private static void addArcaneAspects(String research, String key, AspectStack... aspects)
        {
            HashMap<String, AspectList> map;

            if (arcaneAspects.containsKey(research))
            {
                map = arcaneAspects.get(research);
            }
            else
            {
                map = Maps.newHashMap();
            }

            if (!map.containsKey(key))
            {
                AspectList aspectList = new AspectList();

                for (AspectStack stack : aspects)
                {
                    aspectList.add(stack.getAspect(), stack.getAmount());
                }

                map.put(key, aspectList);

                arcaneAspects.put(research, map);
            }
            else
            {
                LogHelper.warn("Trying to register duplicate arcane aspects (research:" + research + ",key:" + key
                                       + ")");
            }
        }

        public static AspectList getArcaneAspects(String research)
        {
            return getArcaneAspects(research, DEFAULT_KEY);
        }

        private static AspectList getArcaneAspects(String research, String key)
        {
            if (arcaneAspects.containsKey(research))
            {
                if (arcaneAspects.get(research).containsKey(key))
                {
                    return arcaneAspects.get(research).get(key);
                }
                else if (arcaneAspects.get(research).values().size() != 0 && arcaneAspects.get(research).values().size() == 1 && arcaneAspects.get(research).containsKey(
                        DEFAULT_KEY))
                {
                    return arcaneAspects.get(research).get(DEFAULT_KEY);
                }
            }

            return new AspectList(); // TODO Is null better?
        }

        private static void addResearchAspects(String research, AspectStack... aspects)
        {
            if (!researchAspects.containsKey(research))
            {
                AspectList aspectList = new AspectList();

                for (AspectStack stack : aspects)
                {
                    aspectList.add(stack.getAspect(), stack.getAmount());
                }

                researchAspects.put(research, aspectList);
            }
            else
            {
                LogHelper.warn("Trying to register duplicate research aspects (research:" + research + ")");
            }
        }

        public static AspectList getResearchAspects(String research)
        {
            if (researchAspects.containsKey(research))
            {
                return researchAspects.get(research);
            }

            return new AspectList(); // TODO Is null better?
        }
    }

    public static class Recipes
    {
        private static HashMap<String, List<IRecipe>> recipes = Maps.newHashMap();

        private static void init()
        {
            Arcane.init();
        }

        public static class Arcane
        {
            private static HashMap<String, HashMap<String, IArcaneRecipe>> recipes = Maps.newHashMap();

            private static void init()
            {

            }

            private static void add(String research, String key, ItemStack result, boolean shapeless, Object ... recipe)
            {
                IArcaneRecipe arcaneRecipe;

                if (shapeless)
                {
                    arcaneRecipe = new ShapelessArcaneRecipe(research, result, Aspects.getArcaneAspects(research, key), recipe);
                }
                else
                {
                    arcaneRecipe = new ShapedArcaneRecipe(research, result, Aspects.getArcaneAspects(research, key), recipe);
                }

                HashMap<String, IArcaneRecipe> map;

                if (recipes.containsKey(research))
                {
                    map = recipes.get(research);
                }
                else
                {
                    map = Maps.newHashMap();
                }
            }
        }
    }

    public static class Researches
    {
        public static final String CATEGORY = "ATOMATIC";
        private static final String PAGE_PREFIX = "tc.research_page.";

        private static ArrayList<ResearchContainer> researchContainers = Lists.newArrayList();

        private static void initResearches()
        {
            addResearch(ResearchNames.REVERSED_DIRT, -2, -2, 1, new ItemStack(ModBlocks.reversedDirt), new ResearchPage[]{new ResearchPage("1"), new ResearchPage()});
        }

        private static void addSpecialResearch(String key, int col, int row, int complexity, ItemStack icon,
                                               String[] parents, ResearchPage[] pages, AspectStack... aspects)
        {
            addSpecialResearch(key, col, row, complexity, icon, parents, pages, 0, aspects);
        }

        private static void addSpecialResearch(String key, int col, int row, int complexity, ResourceLocation icon,
                                               String[] parents, ResearchPage[] pages, AspectStack... aspects)
        {
            addSpecialResearch(key, col, row, complexity, icon, parents, pages, 0, aspects);
        }

        private static void addSpecialResearch(String key, int col, int row, int complexity, ItemStack icon,
                                               String[] parents, ResearchPage[] pages, int warp, AspectStack... aspects)
        {
            addSpecialResearch(key, col, row, complexity, icon, parents, null, pages, warp, aspects);
        }

        private static void addSpecialResearch(String key, int col, int row, int complexity, ResourceLocation icon,
                                               String[] parents, ResearchPage[] pages, int warp, AspectStack... aspects)
        {
            addSpecialResearch(key, col, row, complexity, icon, parents, null, pages, warp, aspects);
        }

        private static void addSpecialResearch(String key, int col, int row, int complexity, ItemStack icon,
                                               String[] parents, String[] parentsHidden, ResearchPage[] pages, int warp,
                                               AspectStack... aspects)
        {
            addSpecialResearch(key, col, row, complexity, icon, parents, parentsHidden, null, pages, warp, aspects);
        }

        private static void addSpecialResearch(String key, int col, int row, int complexity, ResourceLocation icon,
                                               String[] parents, String[] parentsHidden, ResearchPage[] pages, int warp,
                                               AspectStack... aspects)
        {
            addSpecialResearch(key, col, row, complexity, icon, parents, parentsHidden, null, pages, warp, aspects);
        }

        private static void addSpecialResearch(String key, int col, int row, int complexity, ItemStack icon,
                                               String[] parents, String[] parentsHidden, String[] siblings,
                                               ResearchPage[] pages, int warp, AspectStack... aspects)
        {
            addSpecialResearch(key, col, row, complexity, icon, parents, parentsHidden, siblings, null, null, null,
                               pages, warp, aspects);
        }

        private static void addSpecialResearch(String key, int col, int row, int complexity, ResourceLocation icon,
                                               String[] parents, String[] parentsHidden, String[] siblings,
                                               ResearchPage[] pages, int warp, AspectStack... aspects)
        {
            addSpecialResearch(key, col, row, complexity, icon, parents, parentsHidden, siblings, null, null, null,
                               pages, warp, aspects);
        }

        private static void addSpecialResearch(String key, int col, int row, int complexity, ItemStack icon,
                                               String[] parents, String[] parentsHidden, String[] siblings,
                                               ItemStack[] itemTriggers, String[] entityTriggers,
                                               Aspect[] aspectTriggers, ResearchPage[] pages, int warp,
                                               AspectStack... aspects)
        {
            addResearch(key, col, row, complexity, icon, null, true, false, false, false, false, false, false, false,
                        parents, parentsHidden, siblings, itemTriggers, entityTriggers, aspectTriggers, pages, warp,
                        aspects);
        }

        private static void addSpecialResearch(String key, int col, int row, int complexity, ResourceLocation icon,
                                               String[] parents, String[] parentsHidden, String[] siblings,
                                               ItemStack[] itemTriggers, String[] entityTriggers,
                                               Aspect[] aspectTriggers, ResearchPage[] pages, int warp,
                                               AspectStack... aspects)
        {
            addResearch(key, col, row, complexity, null, icon, true, false, false, false, false, false, false, false,
                        parents, parentsHidden, siblings, itemTriggers, entityTriggers, aspectTriggers, pages, warp,
                        aspects);
        }

        private static void addSecondaryResearch(String key, int col, int row, int complexity, ItemStack icon,
                                                 String[] parents, ResearchPage[] pages, AspectStack... aspects)
        {
            addSecondaryResearch(key, col, row, complexity, icon, parents, pages, 0, aspects);
        }

        private static void addSecondaryResearch(String key, int col, int row, int complexity, ResourceLocation icon,
                                                 String[] parents, ResearchPage[] pages, AspectStack... aspects)
        {
            addSecondaryResearch(key, col, row, complexity, icon, parents, pages, 0, aspects);
        }

        private static void addSecondaryResearch(String key, int col, int row, int complexity, ItemStack icon,
                                                 String[] parents, ResearchPage[] pages, int warp,
                                                 AspectStack... aspects)
        {
            addSecondaryResearch(key, col, row, complexity, icon, parents, null, pages, warp, aspects);
        }

        private static void addSecondaryResearch(String key, int col, int row, int complexity, ResourceLocation icon,
                                                 String[] parents, ResearchPage[] pages, int warp,
                                                 AspectStack... aspects)
        {
            addSecondaryResearch(key, col, row, complexity, icon, parents, null, pages, warp, aspects);
        }

        private static void addSecondaryResearch(String key, int col, int row, int complexity, ItemStack icon,
                                                 String[] parents, String[] parentsHidden, ResearchPage[] pages,
                                                 int warp, AspectStack... aspects)
        {
            addSecondaryResearch(key, col, row, complexity, icon, parents, parentsHidden, null, pages, warp, aspects);
        }

        private static void addSecondaryResearch(String key, int col, int row, int complexity, ResourceLocation icon,
                                                 String[] parents, String[] parentsHidden, ResearchPage[] pages,
                                                 int warp, AspectStack... aspects)
        {
            addSecondaryResearch(key, col, row, complexity, icon, parents, parentsHidden, null, pages, warp, aspects);
        }

        private static void addSecondaryResearch(String key, int col, int row, int complexity, ItemStack icon,
                                                 String[] parents, String[] parentsHidden, String[] siblings,
                                                 ResearchPage[] pages, int warp, AspectStack... aspects)
        {
            addSecondaryResearch(key, col, row, complexity, icon, parents, parentsHidden, siblings, null, null, null,
                                 pages, warp, aspects);
        }

        private static void addSecondaryResearch(String key, int col, int row, int complexity, ResourceLocation icon,
                                                 String[] parents, String[] parentsHidden, String[] siblings,
                                                 ResearchPage[] pages, int warp, AspectStack... aspects)
        {
            addSecondaryResearch(key, col, row, complexity, icon, parents, parentsHidden, siblings, null, null, null,
                                 pages, warp, aspects);
        }

        private static void addSecondaryResearch(String key, int col, int row, int complexity, ItemStack icon,
                                                 String[] parents, String[] parentsHidden, String[] siblings,
                                                 ItemStack[] itemTriggers, String[] entityTriggers,
                                                 Aspect[] aspectTriggers, ResearchPage[] pages, int warp,
                                                 AspectStack... aspects)
        {
            addResearch(key, col, row, complexity, icon, null, false, true, false, false, false, false, false, false,
                        parents, parentsHidden, siblings, itemTriggers, entityTriggers, aspectTriggers, pages, warp,
                        aspects);
        }

        private static void addSecondaryResearch(String key, int col, int row, int complexity, ResourceLocation icon,
                                                 String[] parents, String[] parentsHidden, String[] siblings,
                                                 ItemStack[] itemTriggers, String[] entityTriggers,
                                                 Aspect[] aspectTriggers, ResearchPage[] pages, int warp,
                                                 AspectStack... aspects)
        {
            addResearch(key, col, row, complexity, null, icon, false, true, false, false, false, false, false, false,
                        parents, parentsHidden, siblings, itemTriggers, entityTriggers, aspectTriggers, pages, warp,
                        aspects);
        }

        private static void addRoundResearch(String key, int col, int row, int complexity, ItemStack icon,
                                             String[] parents, ResearchPage[] pages, AspectStack... aspects)
        {
            addRoundResearch(key, col, row, complexity, icon, parents, pages, 0, aspects);
        }

        private static void addRoundResearch(String key, int col, int row, int complexity, ResourceLocation icon,
                                             String[] parents, ResearchPage[] pages, AspectStack... aspects)
        {
            addRoundResearch(key, col, row, complexity, icon, parents, pages, 0, aspects);
        }

        private static void addRoundResearch(String key, int col, int row, int complexity, ItemStack icon,
                                             String[] parents, ResearchPage[] pages, int warp, AspectStack... aspects)
        {
            addRoundResearch(key, col, row, complexity, icon, parents, null, pages, warp, aspects);
        }

        private static void addRoundResearch(String key, int col, int row, int complexity, ResourceLocation icon,
                                             String[] parents, ResearchPage[] pages, int warp, AspectStack... aspects)
        {
            addRoundResearch(key, col, row, complexity, icon, parents, null, pages, warp, aspects);
        }

        private static void addRoundResearch(String key, int col, int row, int complexity, ItemStack icon,
                                             String[] parents, String[] parentsHidden, ResearchPage[] pages, int warp,
                                             AspectStack... aspects)
        {
            addRoundResearch(key, col, row, complexity, icon, parents, parentsHidden, null, pages, warp, aspects);
        }

        private static void addRoundResearch(String key, int col, int row, int complexity, ResourceLocation icon,
                                             String[] parents, String[] parentsHidden, ResearchPage[] pages, int warp,
                                             AspectStack... aspects)
        {
            addRoundResearch(key, col, row, complexity, icon, parents, parentsHidden, null, pages, warp, aspects);
        }

        private static void addRoundResearch(String key, int col, int row, int complexity, ItemStack icon,
                                             String[] parents, String[] parentsHidden, String[] siblings,
                                             ResearchPage[] pages, int warp, AspectStack... aspects)
        {
            addRoundResearch(key, col, row, complexity, icon, parents, parentsHidden, siblings, null, null, null,
                             pages, warp, aspects);
        }

        private static void addRoundResearch(String key, int col, int row, int complexity, ResourceLocation icon,
                                             String[] parents, String[] parentsHidden, String[] siblings,
                                             ResearchPage[] pages, int warp, AspectStack... aspects)
        {
            addRoundResearch(key, col, row, complexity, icon, parents, parentsHidden, siblings, null, null, null,
                             pages, warp, aspects);
        }

        private static void addRoundResearch(String key, int col, int row, int complexity, ItemStack icon,
                                             String[] parents, String[] parentsHidden, String[] siblings,
                                             ItemStack[] itemTriggers, String[] entityTriggers, Aspect[] aspectTriggers,
                                             ResearchPage[] pages, int warp, AspectStack... aspects)
        {
            addResearch(key, col, row, complexity, icon, null, false, false, true, false, false, false, false, false,
                        parents, parentsHidden, siblings, itemTriggers, entityTriggers, aspectTriggers, pages, warp,
                        aspects);
        }

        private static void addRoundResearch(String key, int col, int row, int complexity, ResourceLocation icon,
                                             String[] parents, String[] parentsHidden, String[] siblings,
                                             ItemStack[] itemTriggers, String[] entityTriggers, Aspect[] aspectTriggers,
                                             ResearchPage[] pages, int warp, AspectStack... aspects)
        {
            addResearch(key, col, row, complexity, null, icon, false, false, true, false, false, false, false, false,
                        parents, parentsHidden, siblings, itemTriggers, entityTriggers, aspectTriggers, pages, warp,
                        aspects);
        }

        private static void addHiddenResearch(String key, int col, int row, int complexity, ItemStack icon,
                                              ResearchPage[] pages, AspectStack... aspects)
        {
            addHiddenResearch(key, col, row, complexity, icon, pages, 0, aspects);
        }

        private static void addHiddenResearch(String key, int col, int row, int complexity, ResourceLocation icon,
                                              ResearchPage[] pages, AspectStack... aspects)
        {
            addHiddenResearch(key, col, row, complexity, icon, pages, 0, aspects);
        }

        private static void addHiddenResearch(String key, int col, int row, int complexity, ItemStack icon,
                                              ResearchPage[] pages, int warp, AspectStack... aspects)
        {
            addHiddenResearch(key, col, row, complexity, icon, null, pages, warp, aspects);
        }

        private static void addHiddenResearch(String key, int col, int row, int complexity, ResourceLocation icon,
                                              ResearchPage[] pages, int warp, AspectStack... aspects)
        {
            addHiddenResearch(key, col, row, complexity, icon, null, pages, warp, aspects);
        }

        private static void addHiddenResearch(String key, int col, int row, int complexity, ItemStack icon,
                                              String[] siblings, ResearchPage[] pages, int warp, AspectStack... aspects)
        {
            addHiddenResearch(key, col, row, complexity, icon, siblings, null, null, null, pages, warp, aspects);
        }

        private static void addHiddenResearch(String key, int col, int row, int complexity, ResourceLocation icon,
                                              String[] siblings, ResearchPage[] pages, int warp, AspectStack... aspects)
        {
            addHiddenResearch(key, col, row, complexity, icon, siblings, null, null, null, pages, warp, aspects);
        }

        private static void addHiddenResearch(String key, int col, int row, int complexity, ItemStack icon,
                                              ItemStack[] itemTriggers, String[] entityTriggers,
                                              Aspect[] aspectTriggers, ResearchPage[] pages, int warp,
                                              AspectStack... aspects)
        {
            addHiddenResearch(key, col, row, complexity, icon, null, itemTriggers, entityTriggers, aspectTriggers,
                              pages, warp, aspects);
        }

        private static void addHiddenResearch(String key, int col, int row, int complexity, ResourceLocation icon,
                                              ItemStack[] itemTriggers, String[] entityTriggers,
                                              Aspect[] aspectTriggers, ResearchPage[] pages, int warp,
                                              AspectStack... aspects)
        {
            addHiddenResearch(key, col, row, complexity, icon, null, itemTriggers, entityTriggers, aspectTriggers,
                              pages, warp, aspects);
        }

        private static void addHiddenResearch(String key, int col, int row, int complexity, ItemStack icon,
                                              String[] siblings, ItemStack[] itemTriggers, String[] entityTriggers,
                                              Aspect[] aspectTriggers, ResearchPage[] pages, int warp,
                                              AspectStack... aspects)
        {
            addResearch(key, col, row, complexity, icon, null, false, false, false, false, false, false, true, false,
                        null, null, siblings, itemTriggers, entityTriggers, aspectTriggers, pages, warp, aspects);
        }

        private static void addHiddenResearch(String key, int col, int row, int complexity, ResourceLocation icon,
                                              String[] siblings, ItemStack[] itemTriggers, String[] entityTriggers,
                                              Aspect[] aspectTriggers, ResearchPage[] pages, int warp,
                                              AspectStack... aspects)
        {
            addResearch(key, col, row, complexity, null, icon, false, false, false, false, false, false, true, false,
                        null, null, siblings, itemTriggers, entityTriggers, aspectTriggers, pages, warp, aspects);
        }

        private static void addAutoResearch(String key, int col, int row, int complexity, ItemStack icon,
                                            ResearchPage[] pages)
        {
            addResearch(key, col, row, complexity, icon, null, false, false, true, false, false, false, false, true,
                        null, null, null, null, null, null, pages, 0);
        }

        private static void addAutoResearch(String key, int col, int row, int complexity, ResourceLocation icon,
                                            ResearchPage[] pages)
        {
            addResearch(key, col, row, complexity, null, icon, false, false, true, false, false, false, false, true,
                        null, null, null, null, null, null, pages, 0);
        }

        private static void addResearch(String key, int col, int row, int complexity, ItemStack icon,
                                        ResearchPage[] pages, int warp, AspectStack... aspects)
        {
            addResearch(key, col, row, complexity, icon, null, pages, warp, aspects);
        }

        private static void addResearch(String key, int col, int row, int complexity, ResourceLocation icon,
                                        ResearchPage[] pages, int warp, AspectStack... aspects)
        {
            addResearch(key, col, row, complexity, icon, null, pages, warp, aspects);
        }

        private static void addResearch(String key, int col, int row, int complexity, ItemStack icon, String[] parents,
                                        ResearchPage[] pages, int warp, AspectStack... aspects)
        {
            addResearch(key, col, row, complexity, icon, parents, null, pages, warp, aspects);
        }

        private static void addResearch(String key, int col, int row, int complexity, ResourceLocation icon,
                                        String[] parents, ResearchPage[] pages, int warp, AspectStack... aspects)
        {
            addResearch(key, col, row, complexity, icon, parents, null, pages, warp, aspects);
        }

        private static void addResearch(String key, int col, int row, int complexity, ItemStack icon, String[] parents,
                                        String[] parentsHidden, ResearchPage[] pages, int warp, AspectStack... aspects)
        {
            addResearch(key, col, row, complexity, icon, parents, parentsHidden, null, pages, warp, aspects);
        }

        private static void addResearch(String key, int col, int row, int complexity, ResourceLocation icon,
                                        String[] parents, String[] parentsHidden, ResearchPage[] pages, int warp,
                                        AspectStack... aspects)
        {
            addResearch(key, col, row, complexity, icon, parents, parentsHidden, null, pages, warp, aspects);
        }

        private static void addResearch(String key, int col, int row, int complexity, ItemStack icon, String[] parents,
                                        String[] parentsHidden, String[] siblings, ResearchPage[] pages, int warp,
                                        AspectStack... aspects)
        {
            addResearch(key, col, row, complexity, icon, parents, parentsHidden, siblings, null, null, null, pages,
                        warp, aspects);
        }

        private static void addResearch(String key, int col, int row, int complexity, ResourceLocation icon,
                                        String[] parents, String[] parentsHidden, String[] siblings,
                                        ResearchPage[] pages, int warp, AspectStack... aspects)
        {
            addResearch(key, col, row, complexity, icon, parents, parentsHidden, siblings, null, null, null, pages,
                        warp, aspects);
        }

        private static void addResearch(String key, int col, int row, int complexity, ItemStack icon, String[] parents,
                                        String[] parentsHidden, String[] siblings, ItemStack[] itemTriggers,
                                        String[] entityTriggers, Aspect[] aspectTriggers, ResearchPage[] pages,
                                        int warp, AspectStack... aspects)
        {
            addResearch(key, col, row, complexity, icon, null, false, false, true, false, false, false, false, false,
                        parents, parentsHidden, siblings, itemTriggers, entityTriggers, aspectTriggers, pages, warp,
                        aspects);
        }

        private static void addResearch(String key, int col, int row, int complexity, ResourceLocation icon,
                                        String[] parents, String[] parentsHidden, String[] siblings,
                                        ItemStack[] itemTriggers, String[] entityTriggers, Aspect[] aspectTriggers,
                                        ResearchPage[] pages, int warp, AspectStack... aspects)
        {
            addResearch(key, col, row, complexity, null, icon, false, false, true, false, false, false, false, false,
                        parents, parentsHidden, siblings, itemTriggers, entityTriggers, aspectTriggers, pages, warp,
                        aspects);
        }

        private static void addResearch(String key, int col, int row, int complexity, ItemStack icon,
                                        ResourceLocation resIcon, boolean isSpecial, boolean isSecondary,
                                        boolean isRound, boolean isStub, boolean isVirtual, boolean isConcealed,
                                        boolean isHidden, boolean isAutoUnlock, String[] parents,
                                        String[] parentsHidden, String[] siblings, ItemStack[] itemTriggers,
                                        String[] entityTriggers, Aspect[] aspectTriggers, ResearchPage[] pages,
                                        int warp, AspectStack... aspects)
        {
            ResearchContainer container;

            if (icon != null)
            {
                container = new ResearchContainer(key, col, row, complexity, icon);
            }
            else if (resIcon != null)
            {
                container = new ResearchContainer(key, col, row, complexity, resIcon);
            }
            else
            {
                LogHelper.warn("Attempting to initialize a research container with no icon (research:" + key + ")");
                return;
            }

            if (isSpecial)
            {
                container.setSpecial();
            }
            else if (isRound)
            {
                container.setRound();
            }
            else if (isSecondary)
            {
                container.setSecondary();
            }
            else if (isStub)
            {
                container.setStub();
            }
            else if (isVirtual)
            {
                container.setVirtual();
            }
            else if (isConcealed)
            {
                container.setConcealed();
            }
            else if (isHidden)
            {
                container.setHidden();
            }
            else if (isAutoUnlock)
            {
                container.setAutoUnlock();
            }

            if (parents != null && parents.length > 0)
            {
                container.setParents(parents);
            }

            if (parentsHidden != null && parentsHidden.length > 0)
            {
                container.setParentsHidden(parentsHidden);
            }

            if (siblings != null && siblings.length > 0)
            {
                container.setSiblings(siblings);
            }

            if (itemTriggers != null && itemTriggers.length > 0)
            {
                container.setItemTriggers(itemTriggers);
            }

            if (entityTriggers != null && entityTriggers.length > 0)
            {
                container.setEntityTriggers(entityTriggers);
            }

            if (aspectTriggers != null && aspectTriggers.length > 0)
            {
                container.setAspectTriggers(aspectTriggers);
            }

            if (pages == null || pages.length <= 0)
            {
                LogHelper.warn("Attempting to initialize a research container with no pages (research:" + key + ")");
                return;
            }

            ResearchPage[] researchPages = new ResearchPage[pages.length];

            for (int i = 0; i < pages.length; i++)
            {
                ResearchPage page = pages[i];

                if (pages[i].text != null)
                {
                    String text = page.text;
                    page.text = PAGE_PREFIX + key + text;
                }

                researchPages[i] = page;
            }

            container.setPages(researchPages);

            if (warp > 0)
            {
                container.setWarp(warp);
            }

            researchContainers.add(container);

            if (!isAutoUnlock)
            {
                if (aspects == null || aspects.length <= 0)
                {
                    LogHelper.warn("Attempting to initialize a research container with no aspects (research:" + key
                                           + ")");
                }
                else
                {
                    Aspects.addResearchAspects(key, aspects);
                }
            }
        }

        public static final class ResearchNames
        {
            public static final String REVERSED_DIRT = "REVERSEDDIRT";
            public static final String ATOM = "ATOM";
            public static final String QUANTUM = "QUANTUM";
        }
    }
}
