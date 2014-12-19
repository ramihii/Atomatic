package atomatic.init;

import atomatic.reference.Researches;

import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class ArcaneRecipes
{
    public static AspectList reversedDirt = new AspectList().add(Aspect.EARTH, 2);

    static void init()
    {
        ThaumcraftApi.addShapelessArcaneCraftingRecipe(Researches.ATOM, new ItemStack(ModBlocks.reversedDirt, 3),
                                                       reversedDirt, new ItemStack(Blocks.dirt), Thaumcraft.airShard,
                                                       Thaumcraft.fireShard, Thaumcraft.waterShard,
                                                       Thaumcraft.earthShard, Thaumcraft.orderShard,
                                                       Thaumcraft.entropyShard);
    }
}
