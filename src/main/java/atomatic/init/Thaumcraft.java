package atomatic.init;

import atomatic.aspects.AspectStack;
import atomatic.research.ResearchContainer;
import atomatic.util.LogHelper;

import thaumcraft.api.ItemApi;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;

import net.minecraft.item.ItemStack;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.HashMap;

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
        Researches.initResearches();
    }

    public static void init()
    {

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
            addResearchAspects(Researches.REVERSED_DIRT, new AspectStack(Aspect.VOID), new AspectStack(Aspect.ENERGY), new AspectStack(Aspect.EARTH));
            addResearchAspects(Researches.ATOM, AspectStack.createStacks(Aspect.AIR, Aspect.EARTH, Aspect.FIRE, Aspect.WATER, Aspect.ORDER, Aspect.ENTROPY));
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
                LogHelper.warn("Trying to register duplicate arcane aspects (research:" + research + ",key:" + key + ")");
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
        public static class Arcane
        {

        }
    }

    public static class Researches
    {
        public static final String CATEGORY = "ATOMATIC";

        private static ArrayList<ResearchContainer> researchContainers = Lists.newArrayList();

        private static void initResearches()
        {

        }

        private static void addResearch(String key, int col, int row, int complexity, ItemStack icon, boolean isSpecial,
                                        boolean isSecondary, boolean isRound, boolean isStub, boolean isVirtual,
                                        boolean isConcealed, boolean isHidden, boolean isAutoUnlock, String[] parents,
                                        String[] parentsHidden, String[] siblings, ItemStack[] itemTriggers,
                                        String[] entityTriggers, Aspect[] aspectTriggers, ResearchPage[] pages, int warp,
                                        AspectStack... aspects)
        {
            ResearchContainer container = new ResearchContainer(key, col, row, complexity, icon);

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

            container.setPages(pages);

            if (warp > 0)
            {
                container.setWarp(warp);
            }

            researchContainers.add(container);

            Aspects.addResearchAspects(key, aspects);
        }

        public static final class ResearchNames
        {
            public static final String REVERSED_DIRT = "REVERSEDDIRT";
            public static final String ATOM = "ATOM";
            public static final String QUANTUM = "QUANTUM";
        }

        public static final class Pages
        {
            private static final String PAGE_PREFIX = "tc.research_page.";

            public static final String QUANTUM = PAGE_PREFIX + ResearchNames.QUANTUM + ".";
            public static final String ATOM = PAGE_PREFIX + ResearchNames.ATOM + ".";
            public static final String REVERSED_DIRT = PAGE_PREFIX + ResearchNames.REVERSED_DIRT + ".";
        }
    }
}
