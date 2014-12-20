package atomatic.init;

import atomatic.reference.Researches;

import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.IArcaneRecipe;
import thaumcraft.api.crafting.ShapedArcaneRecipe;
import thaumcraft.api.crafting.ShapelessArcaneRecipe;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import com.google.common.collect.Maps;

import java.util.HashMap;

public class ArcaneRecipes
{
    public static HashMap<String, IArcaneRecipe[]> arcaneRecipes = Maps.newHashMap();

    public static AspectList reversedDirt = new AspectList().add(Aspect.ENTROPY, 2);

    static void init()
    {
        ThaumcraftApi
                .addShapelessArcaneCraftingRecipe(Researches.REVERSED_DIRT, new ItemStack(ModBlocks.reversedDirt, 3),
                                                  reversedDirt, new ItemStack(Blocks.dirt), Thaumcraft.airShard,
                                                  Thaumcraft.fireShard, Thaumcraft.waterShard, Thaumcraft.earthShard,
                                                  Thaumcraft.orderShard, Thaumcraft.entropyShard);
    }

    private static IArcaneRecipe arcaneRecipe(String research, ItemStack result, boolean shapeless, Object ... recipe)
    {
        return null; // TODO
    }

    @Deprecated
    private static IArcaneRecipe arcaneRecipe(String research, ItemStack result, AspectList aspects, boolean shapeless, Object ... recipe)
    {
        if (shapeless)
        {
            ThaumcraftApi.addShapelessArcaneCraftingRecipe(research, result, aspects, recipe);
            return new ShapelessArcaneRecipe(research, result, aspects, recipe);
        }
        else
        {
            ThaumcraftApi.addArcaneCraftingRecipe(research, result, aspects, recipe);
            return new ShapedArcaneRecipe(research, result, aspects, recipe);
        }
    }
}
