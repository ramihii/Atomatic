package atomatic.init;

import atomatic.api.AtomaticApi;
import atomatic.api.primal.PrimalObject;

import atomatic.reference.ThaumcraftReference;
import atomatic.util.AspectHelper;
import atomatic.util.PrimalObjectHelper;

import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.ShapedArcaneRecipe;

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
        initPrimalRecipes();
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
        Researches.recipes.put(Researches.PRIM_PRIMAL_CORE, new ShapedArcaneRecipe[]{
                ThaumcraftApi.addArcaneCraftingRecipe(Researches.PRIM_PRIMAL, PrimalObjectHelper.getPrimalObjectStack(PrimalObject.CORE), new AspectList().add(Aspect.AIR, 40).add(Aspect.FIRE, 40).add(Aspect.WATER, 40).add(Aspect.EARTH, 40).add(Aspect.ORDER, 40).add(Aspect.ENTROPY, 40), "ABE", "FPO", "WBN", 'A', ThaumcraftReference.airShard, 'F', ThaumcraftReference.fireShard, 'W', ThaumcraftReference.waterShard, 'E', ThaumcraftReference.earthShard, 'O', ThaumcraftReference.orderShard, 'N', ThaumcraftReference.entropyShard, 'B', ThaumcraftReference.balancedShard, 'P', ThaumcraftReference.primordialPearl),
                ThaumcraftApi.addArcaneCraftingRecipe(Researches.PRIM_PRIMAL, PrimalObjectHelper.getPrimalObjectStack(PrimalObject.CORE), new AspectList().add(Aspect.AIR, 40).add(Aspect.FIRE, 40).add(Aspect.WATER, 40).add(Aspect.EARTH, 40).add(Aspect.ORDER, 40).add(Aspect.ENTROPY, 40), "AFW", "BPB", "EON", 'A', ThaumcraftReference.airShard, 'F', ThaumcraftReference.fireShard, 'W', ThaumcraftReference.waterShard, 'E', ThaumcraftReference.earthShard, 'O', ThaumcraftReference.orderShard, 'N', ThaumcraftReference.entropyShard, 'B', ThaumcraftReference.balancedShard, 'P', ThaumcraftReference.primordialPearl)
        });
        ThaumcraftApi.addWarpToItem(PrimalObjectHelper.getPrimalObjectStack(PrimalObject.CORE), 2);
    }

    private static void initInfusionRecipes()
    {
        ItemStack core = new ItemStack(Items.ender_pearl);
        ItemStack balancedShard = ThaumcraftReference.balancedShard;
        ItemStack shard = ThaumcraftReference.airShard;
        Researches.recipes.put(Researches.WEAK_PRIM_AIR, ThaumcraftApi.addInfusionCraftingRecipe(Researches.WEAK_PRIM_AIR, PrimalObjectHelper.getPrimalObjectStack(PrimalObject.AIR_WEAK), 4, new AspectList().add(Aspect.AIR, 32).add(Aspect.EARTH, 16), core, new ItemStack[]{
                balancedShard,
                balancedShard,
                shard,
                shard,
                shard,
                shard,
                new ItemStack(Items.feather),
                new ItemStack(Items.feather)
        }));
        shard = ThaumcraftReference.fireShard;
        Researches.recipes.put(Researches.WEAK_PRIM_FIRE, ThaumcraftApi.addInfusionCraftingRecipe(Researches.WEAK_PRIM_FIRE, PrimalObjectHelper.getPrimalObjectStack(PrimalObject.FIRE_WEAK), 4, new AspectList().add(Aspect.FIRE, 32).add(Aspect.WATER, 16), core, new ItemStack[]{
                balancedShard,
                balancedShard,
                shard,
                shard,
                shard,
                shard,
                new ItemStack(Items.blaze_rod),
                new ItemStack(Items.blaze_rod)
        }));
        shard = ThaumcraftReference.waterShard;
        Researches.recipes.put(Researches.WEAK_PRIM_WATER, ThaumcraftApi.addInfusionCraftingRecipe(Researches.WEAK_PRIM_WATER, PrimalObjectHelper.getPrimalObjectStack(PrimalObject.WATER_WEAK), 4, new AspectList().add(Aspect.WATER, 32).add(Aspect.ORDER, 16), core, new ItemStack[]{
                balancedShard,
                balancedShard,
                shard,
                shard,
                shard,
                shard,
                new ItemStack(Items.water_bucket),
                new ItemStack(Items.water_bucket)
        }));
        shard = ThaumcraftReference.earthShard;
        Researches.recipes.put(Researches.WEAK_PRIM_EARTH, ThaumcraftApi.addInfusionCraftingRecipe(Researches.WEAK_PRIM_EARTH, PrimalObjectHelper.getPrimalObjectStack(PrimalObject.EARTH_WEAK), 4, new AspectList().add(Aspect.EARTH, 32).add(Aspect.FIRE, 16), core, new ItemStack[]{
                balancedShard,
                balancedShard,
                shard,
                shard,
                shard,
                shard,
                new ItemStack(Blocks.obsidian),
                new ItemStack(Blocks.obsidian)
        }));
        shard = ThaumcraftReference.orderShard;
        Researches.recipes.put(Researches.WEAK_PRIM_ORDER, ThaumcraftApi.addInfusionCraftingRecipe(Researches.WEAK_PRIM_ORDER, PrimalObjectHelper.getPrimalObjectStack(PrimalObject.ORDER_WEAK), 3, new AspectList().add(Aspect.ORDER, 32).add(Aspect.ENTROPY, 16), core, new ItemStack[]{
                balancedShard,
                balancedShard,
                shard,
                shard,
                shard,
                shard,
                new ItemStack(Blocks.sandstone, 1, 2),
                new ItemStack(Blocks.sandstone, 1, 2)
        }));
        shard = ThaumcraftReference.entropyShard;
        Researches.recipes.put(Researches.WEAK_PRIM_ENTROPY, ThaumcraftApi.addInfusionCraftingRecipe(Researches.WEAK_PRIM_ENTROPY, PrimalObjectHelper.getPrimalObjectStack(PrimalObject.ENTROPY_WEAK), 5, new AspectList().add(Aspect.ENTROPY, 32).add(Aspect.AIR, 16), core, new ItemStack[]{
                balancedShard,
                balancedShard,
                shard,
                shard,
                shard,
                shard,
                new ItemStack(Blocks.cactus),
                new ItemStack(Blocks.cactus)
        }));

        core = new ItemStack(Items.nether_star);
        ItemStack voidSeed = ThaumcraftReference.voidSeed;
        ItemStack magicSalt = ThaumcraftReference.magicSalt;
        Researches.recipes.put(Researches.PRIM_AIR, ThaumcraftApi.addInfusionCraftingRecipe(Researches.PRIM_AIR, PrimalObjectHelper.getPrimalObjectStack(PrimalObject.AIR), 6, new AspectList().add(Aspect.AIR, 48).add(Aspect.MAGIC, 16), core, new ItemStack[]{
                PrimalObjectHelper.getPrimalObjectStack(PrimalObject.AIR_WEAK),
                voidSeed,
                voidSeed,
                AspectHelper.getCrystalEssence(Aspect.AIR),
                AspectHelper.getCrystalEssence(Aspect.AIR),
                AspectHelper.getWispEssence(Aspect.AIR),
                magicSalt,
                magicSalt
        }));
        Researches.recipes.put(Researches.PRIM_FIRE, ThaumcraftApi.addInfusionCraftingRecipe(Researches.PRIM_FIRE, PrimalObjectHelper.getPrimalObjectStack(PrimalObject.FIRE), 6, new AspectList().add(Aspect.FIRE, 48).add(Aspect.MAGIC, 16), core, new ItemStack[]{
                PrimalObjectHelper.getPrimalObjectStack(PrimalObject.FIRE_WEAK),
                voidSeed,
                voidSeed,
                AspectHelper.getCrystalEssence(Aspect.FIRE),
                AspectHelper.getCrystalEssence(Aspect.FIRE),
                AspectHelper.getWispEssence(Aspect.FIRE),
                magicSalt,
                magicSalt
        }));
        Researches.recipes.put(Researches.PRIM_WATER, ThaumcraftApi.addInfusionCraftingRecipe(Researches.PRIM_WATER, PrimalObjectHelper.getPrimalObjectStack(PrimalObject.WATER), 6, new AspectList().add(Aspect.WATER, 48).add(Aspect.MAGIC, 16), core, new ItemStack[]{
                PrimalObjectHelper.getPrimalObjectStack(PrimalObject.WATER_WEAK),
                voidSeed,
                voidSeed,
                AspectHelper.getCrystalEssence(Aspect.WATER),
                AspectHelper.getCrystalEssence(Aspect.WATER),
                AspectHelper.getWispEssence(Aspect.WATER),
                magicSalt,
                magicSalt
        }));
        Researches.recipes.put(Researches.PRIM_EARTH, ThaumcraftApi.addInfusionCraftingRecipe(Researches.PRIM_EARTH, PrimalObjectHelper.getPrimalObjectStack(PrimalObject.EARTH), 6, new AspectList().add(Aspect.EARTH, 48).add(Aspect.MAGIC, 16), core, new ItemStack[]{
                PrimalObjectHelper.getPrimalObjectStack(PrimalObject.EARTH_WEAK),
                voidSeed,
                voidSeed,
                AspectHelper.getCrystalEssence(Aspect.EARTH),
                AspectHelper.getCrystalEssence(Aspect.EARTH),
                AspectHelper.getWispEssence(Aspect.EARTH),
                magicSalt,
                magicSalt
        }));
        Researches.recipes.put(Researches.PRIM_ORDER, ThaumcraftApi.addInfusionCraftingRecipe(Researches.PRIM_ORDER, PrimalObjectHelper.getPrimalObjectStack(PrimalObject.ORDER), 5, new AspectList().add(Aspect.ORDER, 48).add(Aspect.MAGIC, 16), core, new ItemStack[]{
                PrimalObjectHelper.getPrimalObjectStack(PrimalObject.ORDER_WEAK),
                voidSeed,
                voidSeed,
                AspectHelper.getCrystalEssence(Aspect.ORDER),
                AspectHelper.getCrystalEssence(Aspect.ORDER),
                AspectHelper.getWispEssence(Aspect.ORDER),
                magicSalt,
                magicSalt
        }));
        Researches.recipes.put(Researches.PRIM_ENTROPY, ThaumcraftApi.addInfusionCraftingRecipe(Researches.PRIM_ENTROPY, PrimalObjectHelper.getPrimalObjectStack(PrimalObject.ENTROPY), 7, new AspectList().add(Aspect.ENTROPY, 48).add(Aspect.MAGIC, 16), core, new ItemStack[]{
                PrimalObjectHelper.getPrimalObjectStack(PrimalObject.ENTROPY_WEAK),
                voidSeed,
                voidSeed,
                AspectHelper.getCrystalEssence(Aspect.ENTROPY),
                AspectHelper.getCrystalEssence(Aspect.ENTROPY),
                AspectHelper.getWispEssence(Aspect.ENTROPY),
                magicSalt,
                magicSalt
        }));
        Researches.recipes.put(Researches.PRIM_PRIMAL, ThaumcraftApi.addInfusionCraftingRecipe(Researches.PRIM_PRIMAL, PrimalObjectHelper.getPrimalObjectStack(PrimalObject.PRIMAL), 9, new AspectList().add(Aspect.AIR, 64).add(Aspect.FIRE, 64).add(Aspect.WATER, 64).add(Aspect.EARTH, 64).add(Aspect.ORDER, 64).add(Aspect.ENTROPY, 64), PrimalObjectHelper.getPrimalObjectStack(PrimalObject.CORE), new ItemStack[]{
                PrimalObjectHelper.getPrimalObjectStack(PrimalObject.AIR),
                PrimalObjectHelper.getPrimalObjectStack(PrimalObject.FIRE),
                PrimalObjectHelper.getPrimalObjectStack(PrimalObject.WATER),
                PrimalObjectHelper.getPrimalObjectStack(PrimalObject.EARTH),
                PrimalObjectHelper.getPrimalObjectStack(PrimalObject.ORDER),
                PrimalObjectHelper.getPrimalObjectStack(PrimalObject.ENTROPY)
        }));
    }

    private static void initPrimalRecipes()
    {
        // TODO Test recipe
        AtomaticApi.addPrimalRecipe(null, new ItemStack(Items.nether_star), PrimalObject.EARTH, new ItemStack(Blocks.dirt));
    }
}
