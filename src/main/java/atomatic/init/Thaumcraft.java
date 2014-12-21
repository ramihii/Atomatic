package atomatic.init;

import atomatic.aspects.AspectStack;
import atomatic.reference.Textures;
import atomatic.util.LogHelper;

import thaumcraft.api.ItemApi;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.IArcaneRecipe;
import thaumcraft.api.crafting.ShapedArcaneRecipe;
import thaumcraft.api.crafting.ShapelessArcaneRecipe;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.HashMap;
import java.util.List;

public class Thaumcraft
{
    public static final String DEFAULT_KEY = "NIL";

    public static ItemStack airShard;
    public static ItemStack fireShard;
    public static ItemStack waterShard;
    public static ItemStack earthShard;
    public static ItemStack orderShard;
    public static ItemStack entropyShard;

    public static void preInit()
    {
        LogHelper.info("TC compatibility pre-initialization phase");
    }

    public static void init()
    {
        LogHelper.info("TC compatibility initialization phase");

        Recipes.init();
    }

    public static void postInit()
    {
        LogHelper.info("TC compatibility post-initialization phase");

        airShard = ItemApi.getItem("itemShard", 0);
        fireShard = ItemApi.getItem("itemShard", 1);
        waterShard = ItemApi.getItem("itemShard", 2);
        earthShard = ItemApi.getItem("itemShard", 3);
        orderShard = ItemApi.getItem("itemShard", 4);
        entropyShard = ItemApi.getItem("itemShard", 5);

        Recipes.init();

        ResearchCategories.registerCategory(Researches.CATEGORY, Textures.Thaumonomicon.THAUMONOMICON_TAB,
                                            Textures.Thaumonomicon.THAUMONOMICON_BACKGROUND);

        Researches.initResearches();
    }

    private static AspectStack aspectStack(Aspect aspect)
    {
        return new AspectStack(aspect);
    }

    private static AspectStack aspectStack(Aspect aspect, int amount)
    {
        return new AspectStack(aspect, amount);
    }

    private static AspectStack[] aspectStacks(Aspect[] aspects, int[] amounts)
    {
        if (aspects.length != amounts.length)
        {
            LogHelper.warn("The length of aspects and their amounts should be equal");
            return null;
        }

        AspectStack[] ret = new AspectStack[aspects.length];

        for (int i = 0; i < aspects.length; i++)
        {
            ret[i] = aspectStack(aspects[i], amounts[i]);
        }

        return ret;
    }

    public static class Aspects
    {
        public static HashMap<String, HashMap<String, AspectList>> arcaneAspects = Maps.newHashMap();
        public static HashMap<String, AspectList> researchAspects = Maps.newHashMap();

        private static void addArcaneAspects(AspectStack... aspects)
        {
            addArcaneAspects(Recipes.Arcane.lastResearch, Recipes.Arcane.lastKey, aspects);
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
                else if (arcaneAspects.get(research).values().size() != 0
                        && arcaneAspects.get(research).values().size() == 1 && arcaneAspects.get(research).containsKey(
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

            private static String lastResearch = null;
            private static String lastKey = null;

            private static void init()
            {
                addShapeless(Researches.INVERSE_DIRT, ModBlocks.inverseDirt, Blocks.dirt, airShard,
                             fireShard, waterShard, earthShard, orderShard, entropyShard);
                Aspects.addArcaneAspects(aspectStack(Aspect.EARTH, 10), aspectStack(Aspect.ORDER, 5), aspectStack(
                        Aspect.ENTROPY, 5));
            }

            private static void addShapeless(String research, Item result, Object... recipe)
            {
                addShapeless(research, DEFAULT_KEY, result, recipe);
            }

            private static void addShapeless(String research, Block result, Object... recipe)
            {
                addShapeless(research, DEFAULT_KEY, result, recipe);
            }

            private static void addShapeless(String research, ItemStack result, Object... recipe)
            {
                addShapeless(research, DEFAULT_KEY, result, recipe);
            }

            private static void addShapeless(String research, String key, Item result, Object... recipe)
            {
                addShapeless(research, key, new ItemStack(result), recipe);
            }

            private static void addShapeless(String research, String key, Block result, Object... recipe)
            {
                addShapeless(research, key, new ItemStack(result), recipe);
            }

            private static void addShapeless(String research, String key, ItemStack result, Object... recipe)
            {
                add(research, key, result, true, recipe);
            }

            private static void addShaped(String research, Item result, Object... recipe)
            {
                addShaped(research, DEFAULT_KEY, result, recipe);
            }

            private static void addShaped(String research, Block result, Object... recipe)
            {
                addShaped(research, DEFAULT_KEY, result, recipe);
            }

            private static void addShaped(String research, ItemStack result, Object... recipe)
            {
                addShaped(research, DEFAULT_KEY, result, recipe);
            }

            private static void addShaped(String research, String key, Item result, Object... recipe)
            {
                addShaped(research, key, new ItemStack(result), recipe);
            }

            private static void addShaped(String research, String key, Block result, Object... recipe)
            {
                addShaped(research, key, new ItemStack(result), recipe);
            }

            private static void addShaped(String research, String key, ItemStack result, Object... recipe)
            {
                add(research, key, result, false, recipe);
            }

            private static void add(String research, String key, ItemStack result, boolean shapeless, Object... recipe)
            {
                IArcaneRecipe arcaneRecipe;

                if (shapeless)
                {
                    arcaneRecipe = new ShapelessArcaneRecipe(research, result, Aspects.getArcaneAspects(research, key),
                                                             recipe);
                }
                else
                {
                    arcaneRecipe = new ShapedArcaneRecipe(research, result, Aspects.getArcaneAspects(research, key),
                                                          recipe);
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

                if (!map.containsKey(key))
                {
                    map.put(key, arcaneRecipe);
                    recipes.put(research, map);
                }

                lastResearch = research;
                lastKey = key;
            }

            private static IArcaneRecipe getWithKey(String key)
            {
                return get(Researches.key, key);
            }

            private static IArcaneRecipe get()
            {
                return get(Researches.key);
            }

            private static IArcaneRecipe get(String research)
            {
                return get(research, DEFAULT_KEY);
            }

            private static IArcaneRecipe get(String research, String key)
            {
                return recipes.containsKey(research) && recipes.get(research).containsKey(key) ? recipes.get(research)
                        .get(
                                key) : null;

            }
        }
    }

    public static class Researches
    {
        public static final String CATEGORY = "ATOMATIC";

        public static final String INVERSE_DIRT = "INVERSEDIRT";
        public static final String ATOM = "ATOM";
        public static final String QUANTUM = "QUANTUM";

        private static final String PAGE_PREFIX = "tc.research_page.";

        private static HashMap<String, List<AspectStack>> aspects = Maps.newHashMap();

        private static String key = null;
        private static int warp = 0;
        private static ResearchItem research = null;

        private static void initResearches()
        {
            set(INVERSE_DIRT);
            addAspect(Aspect.VOID);
            addAspect(Aspect.ENERGY);
            addAspect(Aspect.EARTH, 2);
            create(-2, -2, 1, 0, ModBlocks.inverseDirt);
            // TODO Find good parents. research.setParents();
            setPages(new ResearchPage("1"), new ResearchPage(Recipes.Arcane.get()));
            register();

            set(ATOM);
            addAspect(Aspect.ORDER, 2);
            addAspect(Aspect.VOID, 5);
            addAspect(Aspect.MOTION, 2);
            create(-2, 0, 1, 0, Items.apple);
            setSecondary();
            setParents(INVERSE_DIRT);
            setPages(new ResearchPage("1"));
            register();

            set(QUANTUM);
            addAspect(Aspect.VOID, 5);
            addAspect(Aspect.ENERGY, 5);
            addAspect(Aspect.ELDRITCH, 2);
            addAspect(Aspect.MECHANISM, 5);
            create(0, 0, 3, 3, Items.diamond);
            setParents(ATOM);
            setSpecial();
            setRound();
            setConcealed();
            setPages(new ResearchPage("1"));
            register();
        }

        private static void set(String key)
        {
            Researches.key = key;
        }

        private static void addAspect(Aspect aspect)
        {
            addAspect(aspect, 1);
        }

        private static void addAspect(Aspect aspect, int amount)
        {
            List<AspectStack> list;

            if (aspects.containsKey(key))
            {
                list = aspects.get(key);
            }
            else
            {
                list = Lists.newArrayList();
            }

            list.add(aspectStack(aspect, amount));

            aspects.put(key, list);
        }

        private static void create(int col, int row, int complex, int warp, Item icon)
        {
            create(col, row, complex, warp, new ItemStack(icon));
        }

        private static void create(int col, int row, int complex, int warp, Block icon)
        {
            create(col, row, complex, warp, new ItemStack(icon));
        }

        private static void create(int col, int row, int complex, int warp, ItemStack icon)
        {
            AspectList aspectList = new AspectList();

            for (AspectStack stack : aspects.get(key))
            {
                aspectList.add(stack.getAspect(), stack.getAmount());
            }

            Researches.warp = warp;

            research = new ResearchItem(key, CATEGORY, aspectList, col, row, complex, icon);
        }

        private static void create(int col, int row, int complex, int warp, ResourceLocation icon)
        {
            AspectList aspectList = new AspectList();

            for (AspectStack stack : aspects.get(key))
            {
                aspectList.add(stack.getAspect(), stack.getAmount());
            }

            Researches.warp = warp;

            research = new ResearchItem(key, CATEGORY, aspectList, col, row, complex, icon);
        }

        private static void setSpecial()
        {
            research.setSpecial();
        }

        private static void setStub()
        {
            research.setStub();
        }

        private static void setConcealed()
        {
            research.setConcealed();
        }

        private static void setHidden()
        {
            research.setHidden();
        }

        private static void setVirtual()
        {
            research.setVirtual();
        }

        private static void setAutoUnlock()
        {
            research.setAutoUnlock();
        }

        private static void setRound()
        {
            research.setRound();
        }

        private static void setSecondary()
        {
            research.setSecondary();
        }

        private static void setParents(String... parents)
        {
            research.setParents(parents);
        }

        private static void setPages(ResearchPage... pages)
        {
            ResearchPage[] ret = new ResearchPage[pages.length];

            for (int i = 0; i < pages.length; i++)
            {
                ResearchPage page = pages[i];

                if (page.text != null)
                {
                    String old = page.text;
                    page.text = PAGE_PREFIX + key + "." + old;
                }

                ret[i] = page;
            }

            research.setPages(ret);
        }

        private static void register()
        {
            research.registerResearchItem();

            if (warp > 0)
            {
                ThaumcraftApi.addWarpToResearch(key, warp);
            }

            key = null;
            warp = 0;
            research = null;
        }
    }
}
