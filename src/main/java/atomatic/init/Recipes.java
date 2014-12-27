package atomatic.init;

import thaumcraft.api.ItemApi;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

import thaumcraft.common.config.ConfigItems;

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
        ItemStack balancedShard = ItemApi.getItem("itemShard", 6);
        ItemStack shard = ItemApi.getItem("itemShard", 0);
        Researches.recipes.put(Researches.WEAK_PRIM_AIR, ThaumcraftApi.addInfusionCraftingRecipe(Researches.WEAK_PRIM_AIR, new ItemStack(ModItems.weakPrimalObject, 1, 0), 5, new AspectList().add(Aspect.AIR, 32).add(Aspect.EARTH, 16), shard, new ItemStack[]{balancedShard, balancedShard, shard, shard, shard, shard, new ItemStack(Items.feather), new ItemStack(Items.feather)}));
        shard = ItemApi.getItem("itemShard", 1);
        Researches.recipes.put(Researches.WEAK_PRIM_FIRE, ThaumcraftApi.addInfusionCraftingRecipe(Researches.WEAK_PRIM_FIRE, new ItemStack(ModItems.weakPrimalObject, 1, 1), 5, new AspectList().add(Aspect.FIRE, 32).add(Aspect.WATER, 16), shard, new ItemStack[]{balancedShard, balancedShard, shard, shard, shard, shard, new ItemStack(Items.blaze_rod), new ItemStack(Items.blaze_rod)}));
    }
}
