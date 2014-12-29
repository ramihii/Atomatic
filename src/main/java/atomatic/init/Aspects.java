package atomatic.init;

import atomatic.reference.PrimalObject;
import atomatic.util.PrimalObjectHelper;

import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

public class Aspects
{
    public static void init()
    {
        registerItemAspects();
    }

    private static void registerItemAspects()
    {
        ThaumcraftApi.registerObjectTag(PrimalObjectHelper.getPrimalObject(PrimalObject.AIR_WEAK), new AspectList().add(Aspect.AIR, 8).add(Aspect.EARTH, 4));
        ThaumcraftApi.registerObjectTag(PrimalObjectHelper.getPrimalObject(PrimalObject.FIRE_WEAK), new AspectList().add(Aspect.FIRE, 8).add(Aspect.WATER, 4));
        ThaumcraftApi.registerObjectTag(PrimalObjectHelper.getPrimalObject(PrimalObject.WATER_WEAK), new AspectList().add(Aspect.WATER, 8).add(Aspect.ORDER, 4));
        ThaumcraftApi.registerObjectTag(PrimalObjectHelper.getPrimalObject(PrimalObject.EARTH_WEAK), new AspectList().add(Aspect.EARTH, 8).add(Aspect.FIRE, 4));
        ThaumcraftApi.registerObjectTag(PrimalObjectHelper.getPrimalObject(PrimalObject.ORDER_WEAK), new AspectList().add(Aspect.ORDER, 8).add(Aspect.ENTROPY, 4));
        ThaumcraftApi.registerObjectTag(PrimalObjectHelper.getPrimalObject(PrimalObject.ENTROPY_WEAK), new AspectList().add(Aspect.ENTROPY, 8).add(Aspect.AIR, 4));
        /*
        ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.primalObject, 1, 0), new AspectList().add(Aspect.AIR, 40).add(Aspect.MAGIC, 10));
        ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.primalObject, 1, 1), new AspectList().add(Aspect.FIRE, 40).add(Aspect.MAGIC, 10));
        ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.primalObject, 1, 2), new AspectList().add(Aspect.WATER, 40).add(Aspect.MAGIC, 10));
        ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.primalObject, 1, 3), new AspectList().add(Aspect.EARTH, 40).add(Aspect.MAGIC, 10));
        ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.primalObject, 1, 4), new AspectList().add(Aspect.ORDER, 40).add(Aspect.MAGIC, 10));
        ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.primalObject, 1, 5), new AspectList().add(Aspect.ENTROPY, 40).add(Aspect.MAGIC, 10));
        */
    }
}
