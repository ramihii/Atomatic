package atomatic.init;

import atomatic.util.AspectHelper;

import thaumcraft.api.ItemApi;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class Recipes
{
    public static void init()
    {
        initVanillaRecipes();
        initAlchemyRecipes();
        initArcaneRecipes();
        initInfusionRecipes();
    }

    private static void initVanillaRecipes()
    {
    }

    private static void initAlchemyRecipes()
    {
        Researches.recipes.put(Researches.INVERSE_DIRT, ThaumcraftApi.addCrucibleRecipe(Researches.INVERSE_DIRT, new ItemStack(ModBlocks.inverseDirt), new ItemStack(Blocks.dirt), new AspectList().add(Aspect.MAGIC, 2).add(Aspect.EXCHANGE, 2)));
    }

    private static void initArcaneRecipes()
    {

    }

    private static void initInfusionRecipes()
    {
        ItemStack core = new ItemStack(Items.ender_pearl);
        ItemStack balancedShard = ItemApi.getItem("itemShard", 6);
        ItemStack shard = ItemApi.getItem("itemShard", 0);
        Researches.recipes.put(Researches.WEAK_PRIM_AIR, ThaumcraftApi.addInfusionCraftingRecipe(Researches.WEAK_PRIM_AIR, new ItemStack(ModItems.weakPrimalObject, 1, 0), 4, new AspectList().add(Aspect.AIR, 32).add(Aspect.EARTH, 16), core, new ItemStack[]{balancedShard, balancedShard, shard, shard, shard, shard, new ItemStack(Items.feather), new ItemStack(Items.feather)}));
        shard = ItemApi.getItem("itemShard", 1);
        Researches.recipes.put(Researches.WEAK_PRIM_FIRE, ThaumcraftApi.addInfusionCraftingRecipe(Researches.WEAK_PRIM_FIRE, new ItemStack(ModItems.weakPrimalObject, 1, 1), 4, new AspectList().add(Aspect.FIRE, 32).add(Aspect.WATER, 16), core, new ItemStack[]{balancedShard, balancedShard, shard, shard, shard, shard, new ItemStack(Items.blaze_rod), new ItemStack(Items.blaze_rod)}));
        shard = ItemApi.getItem("itemShard", 2);
        Researches.recipes.put(Researches.WEAK_PRIM_WATER, ThaumcraftApi.addInfusionCraftingRecipe(Researches.WEAK_PRIM_WATER, new ItemStack(ModItems.weakPrimalObject, 1, 2), 4, new AspectList().add(Aspect.WATER, 32).add(Aspect.ORDER, 16), core, new ItemStack[]{balancedShard, balancedShard, shard, shard, shard, shard, new ItemStack(Items.water_bucket), new ItemStack(Items.water_bucket)}));
        shard = ItemApi.getItem("itemShard", 3);
        Researches.recipes.put(Researches.WEAK_PRIM_EARTH, ThaumcraftApi.addInfusionCraftingRecipe(Researches.WEAK_PRIM_EARTH, new ItemStack(ModItems.weakPrimalObject, 1, 3), 4, new AspectList().add(Aspect.EARTH, 32).add(Aspect.FIRE, 16), core, new ItemStack[]{balancedShard, balancedShard, shard, shard, shard, shard, new ItemStack(Blocks.obsidian), new ItemStack(Blocks.obsidian)}));
        shard = ItemApi.getItem("itemShard", 4);
        Researches.recipes.put(Researches.WEAK_PRIM_ORDER, ThaumcraftApi.addInfusionCraftingRecipe(Researches.WEAK_PRIM_ORDER, new ItemStack(ModItems.weakPrimalObject, 1, 4), 3, new AspectList().add(Aspect.ORDER, 32).add(Aspect.ENTROPY, 16), core, new ItemStack[]{balancedShard, balancedShard, shard, shard, shard, shard, new ItemStack(Blocks.sandstone, 1, 2), new ItemStack(Blocks.sandstone, 1, 2)}));
        shard = ItemApi.getItem("itemShard", 5);
        Researches.recipes.put(Researches.WEAK_PRIM_ENTROPY, ThaumcraftApi.addInfusionCraftingRecipe(Researches.WEAK_PRIM_ENTROPY, new ItemStack(ModItems.weakPrimalObject, 1, 5), 5, new AspectList().add(Aspect.ENTROPY, 32).add(Aspect.AIR, 16), core, new ItemStack[]{balancedShard, balancedShard, shard, shard, shard, shard, new ItemStack(Blocks.cactus), new ItemStack(Blocks.cactus)}));

        core = new ItemStack(Items.nether_star);
        ItemStack voidSeed = ItemApi.getItem("itemResource", 17);
        ItemStack magicSalt = ItemApi.getItem("itemResource", 14);
        Researches.recipes.put(Researches.PRIM_AIR, ThaumcraftApi.addInfusionCraftingRecipe(Researches.PRIM_AIR, new ItemStack(ModItems.primalObject, 1, 0), 6, new AspectList().add(Aspect.AIR, 48).add(Aspect.MAGIC, 16), core, new ItemStack[]{new ItemStack(ModItems.weakPrimalObject, 1, 0), voidSeed, voidSeed, AspectHelper.getCrystalEssence(Aspect.AIR), AspectHelper.getCrystalEssence(Aspect.AIR), AspectHelper.getWispEssence(Aspect.AIR), magicSalt, magicSalt}));
        Researches.recipes.put(Researches.PRIM_FIRE, ThaumcraftApi.addInfusionCraftingRecipe(Researches.PRIM_FIRE, new ItemStack(ModItems.primalObject, 1, 1), 6, new AspectList().add(Aspect.FIRE, 48).add(Aspect.MAGIC, 16), core, new ItemStack[]{new ItemStack(ModItems.weakPrimalObject, 1, 1), voidSeed, voidSeed, AspectHelper.getCrystalEssence(Aspect.FIRE), AspectHelper.getCrystalEssence(Aspect.FIRE), AspectHelper.getWispEssence(Aspect.FIRE), magicSalt, magicSalt}));
        Researches.recipes.put(Researches.PRIM_WATER, ThaumcraftApi.addInfusionCraftingRecipe(Researches.PRIM_WATER, new ItemStack(ModItems.primalObject, 1, 2), 6, new AspectList().add(Aspect.WATER, 48).add(Aspect.MAGIC, 16), core, new ItemStack[]{new ItemStack(ModItems.weakPrimalObject, 1, 2), voidSeed, voidSeed, AspectHelper.getCrystalEssence(Aspect.WATER), AspectHelper.getCrystalEssence(Aspect.WATER), AspectHelper.getWispEssence(Aspect.WATER), magicSalt, magicSalt}));
        Researches.recipes.put(Researches.PRIM_EARTH, ThaumcraftApi.addInfusionCraftingRecipe(Researches.PRIM_EARTH, new ItemStack(ModItems.primalObject, 1, 3), 6, new AspectList().add(Aspect.EARTH, 48).add(Aspect.MAGIC, 16), core, new ItemStack[]{new ItemStack(ModItems.weakPrimalObject, 1, 3), voidSeed, voidSeed, AspectHelper.getCrystalEssence(Aspect.EARTH), AspectHelper.getCrystalEssence(Aspect.EARTH), AspectHelper.getWispEssence(Aspect.EARTH), magicSalt, magicSalt}));
        Researches.recipes.put(Researches.PRIM_ORDER, ThaumcraftApi.addInfusionCraftingRecipe(Researches.PRIM_ORDER, new ItemStack(ModItems.primalObject, 1, 4), 5, new AspectList().add(Aspect.ORDER, 48).add(Aspect.MAGIC, 16), core, new ItemStack[]{new ItemStack(ModItems.weakPrimalObject, 1, 4), voidSeed, voidSeed, AspectHelper.getCrystalEssence(Aspect.ORDER), AspectHelper.getCrystalEssence(Aspect.ORDER), AspectHelper.getWispEssence(Aspect.ORDER), magicSalt, magicSalt}));
        Researches.recipes.put(Researches.PRIM_ENTROPY, ThaumcraftApi.addInfusionCraftingRecipe(Researches.PRIM_ENTROPY, new ItemStack(ModItems.primalObject, 1, 5), 7, new AspectList().add(Aspect.ENTROPY, 48).add(Aspect.MAGIC, 16), core, new ItemStack[]{new ItemStack(ModItems.weakPrimalObject, 1, 5), voidSeed, voidSeed, AspectHelper.getCrystalEssence(Aspect.ENTROPY), AspectHelper.getCrystalEssence(Aspect.ENTROPY), AspectHelper.getWispEssence(Aspect.ENTROPY), magicSalt, magicSalt}));
    }
}
