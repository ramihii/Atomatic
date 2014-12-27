package atomatic.init;

import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

import net.minecraft.item.ItemStack;

public class Aspects
{
    public static void init()
    {
        registerItemAspects();
    }

    private static void registerItemAspects()
    {
        ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.weakPrimalObject, 1, 0), new AspectList().add(Aspect.AIR, 8).add(Aspect.EARTH, 4));
        ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.weakPrimalObject, 1, 1), new AspectList().add(Aspect.FIRE, 8).add(Aspect.WATER, 4));
        ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.weakPrimalObject, 1, 2), new AspectList().add(Aspect.WATER, 8).add(Aspect.ORDER, 4));
        ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.weakPrimalObject, 1, 3), new AspectList().add(Aspect.EARTH, 8).add(Aspect.FIRE, 4));
        ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.weakPrimalObject, 1, 4), new AspectList().add(Aspect.ORDER, 8).add(Aspect.ENTROPY, 4));
        ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.weakPrimalObject, 1, 5), new AspectList().add(Aspect.ENTROPY, 8).add(Aspect.AIR, 4));
    }
}
