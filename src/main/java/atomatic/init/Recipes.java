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
        ItemStack shard = /* new ItemStack(ConfigItems.itemShard, 1, 0) */ ItemApi.getItem("itemShard", 0);
        Researches.recipes.put(Researches.WEAK_PRIM_AIR, ThaumcraftApi.addInfusionCraftingRecipe(Researches.WEAK_PRIM_AIR, new ItemStack(ModItems.weakPrimalObject, 1, 0), 5, new AspectList().add(Aspect.AIR, 32).add(Aspect.EARTH, 16), /* new ItemStack(ConfigItems.itemShard, 1, 6) */ ItemApi.getItem("itemShard", 6), new ItemStack[]{shard, shard, shard, shard, new ItemStack(Items.reeds), new ItemStack(Items.feather)}));
        shard = /* new ItemStack(ConfigItems.itemShard, 1, 1) */ ItemApi.getItem("itemShard", 1);
        Researches.recipes.put(Researches.WEAK_PRIM_EARTH, ThaumcraftApi.addInfusionCraftingRecipe(Researches.WEAK_PRIM_EARTH, new ItemStack(ModItems.weakPrimalObject, 1, 1), 5, new AspectList().add(Aspect.EARTH, 32).add(Aspect.AIR, 16), /* new ItemStack(ConfigItems.itemShard, 1, 6) */ ItemApi.getItem("itemShard", 6), new ItemStack[]{shard, shard, shard, shard, new ItemStack(Blocks.obsidian), new ItemStack(Blocks.obsidian)}));
    }
}
