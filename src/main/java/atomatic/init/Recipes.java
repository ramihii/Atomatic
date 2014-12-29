package atomatic.init;

import atomatic.reference.Names;
import atomatic.reference.ThaumcraftReference;
import atomatic.util.AspectHelper;

import thaumcraft.api.ItemApi;
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
                ThaumcraftApi.addArcaneCraftingRecipe(Researches.PRIM_PRIMAL, new ItemStack(ModItems.primalObject, 1, 6), new AspectList().add(Aspect.AIR, 40).add(Aspect.FIRE, 40).add(Aspect.WATER, 40).add(Aspect.EARTH, 40).add(Aspect.ORDER, 40).add(Aspect.ENTROPY, 40), "ABE", "FPO", "WBN", 'A', ThaumcraftReference.airShard, 'F', ThaumcraftReference.fireShard, 'W', ThaumcraftReference.waterShard, 'E', ThaumcraftReference.earthShard, 'O', ThaumcraftReference.orderShard, 'N', ThaumcraftReference.entropyShard, 'B', ThaumcraftReference.balancedShard, 'P', ThaumcraftReference.primordialPearl),
                ThaumcraftApi.addArcaneCraftingRecipe(Researches.PRIM_PRIMAL, new ItemStack(ModItems.primalObject, 1, 6), new AspectList().add(Aspect.AIR, 40).add(Aspect.FIRE, 40).add(Aspect.WATER, 40).add(Aspect.EARTH, 40).add(Aspect.ORDER, 40).add(Aspect.ENTROPY, 40), "AFW", "BPB", "EON", 'A', ThaumcraftReference.airShard, 'F', ThaumcraftReference.fireShard, 'W', ThaumcraftReference.waterShard, 'E', ThaumcraftReference.earthShard, 'O', ThaumcraftReference.orderShard, 'N', ThaumcraftReference.entropyShard, 'B', ThaumcraftReference.balancedShard, 'P', ThaumcraftReference.primordialPearl)
        });
        ThaumcraftApi.addWarpToItem(new ItemStack(ModItems.primalObject, 1, 6), 2);
    }

    private static void initInfusionRecipes()
    {
        ItemStack core = new ItemStack(Items.ender_pearl);
        ItemStack balancedShard = ThaumcraftReference.balancedShard;
        ItemStack shard = ThaumcraftReference.airShard;
        Researches.recipes.put(Researches.WEAK_PRIM_AIR, ThaumcraftApi.addInfusionCraftingRecipe(Researches.WEAK_PRIM_AIR, new ItemStack(ModItems.weakPrimalObject, 1, 0), 4, new AspectList().add(Aspect.AIR, 32).add(Aspect.EARTH, 16), core, new ItemStack[]{
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
        Researches.recipes.put(Researches.WEAK_PRIM_FIRE, ThaumcraftApi.addInfusionCraftingRecipe(Researches.WEAK_PRIM_FIRE, new ItemStack(ModItems.weakPrimalObject, 1, 1), 4, new AspectList().add(Aspect.FIRE, 32).add(Aspect.WATER, 16), core, new ItemStack[]{
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
        Researches.recipes.put(Researches.WEAK_PRIM_WATER, ThaumcraftApi.addInfusionCraftingRecipe(Researches.WEAK_PRIM_WATER, new ItemStack(ModItems.weakPrimalObject, 1, 2), 4, new AspectList().add(Aspect.WATER, 32).add(Aspect.ORDER, 16), core, new ItemStack[]{
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
        Researches.recipes.put(Researches.WEAK_PRIM_EARTH, ThaumcraftApi.addInfusionCraftingRecipe(Researches.WEAK_PRIM_EARTH, new ItemStack(ModItems.weakPrimalObject, 1, 3), 4, new AspectList().add(Aspect.EARTH, 32).add(Aspect.FIRE, 16), core, new ItemStack[]{
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
        Researches.recipes.put(Researches.WEAK_PRIM_ORDER, ThaumcraftApi.addInfusionCraftingRecipe(Researches.WEAK_PRIM_ORDER, new ItemStack(ModItems.weakPrimalObject, 1, 4), 3, new AspectList().add(Aspect.ORDER, 32).add(Aspect.ENTROPY, 16), core, new ItemStack[]{
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
        Researches.recipes.put(Researches.WEAK_PRIM_ENTROPY, ThaumcraftApi.addInfusionCraftingRecipe(Researches.WEAK_PRIM_ENTROPY, new ItemStack(ModItems.weakPrimalObject, 1, 5), 5, new AspectList().add(Aspect.ENTROPY, 32).add(Aspect.AIR, 16), core, new ItemStack[]{
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
        Researches.recipes.put(Researches.PRIM_AIR, ThaumcraftApi.addInfusionCraftingRecipe(Researches.PRIM_AIR, new ItemStack(ModItems.primalObject, 1, 0), 6, new AspectList().add(Aspect.AIR, 48).add(Aspect.MAGIC, 16), core, new ItemStack[]{
                new ItemStack(ModItems.weakPrimalObject, 1, 0),
                voidSeed,
                voidSeed,
                AspectHelper.getCrystalEssence(Aspect.AIR),
                AspectHelper.getCrystalEssence(Aspect.AIR),
                AspectHelper.getWispEssence(Aspect.AIR),
                magicSalt,
                magicSalt
        }));
        Researches.recipes.put(Researches.PRIM_FIRE, ThaumcraftApi.addInfusionCraftingRecipe(Researches.PRIM_FIRE, new ItemStack(ModItems.primalObject, 1, 1), 6, new AspectList().add(Aspect.FIRE, 48).add(Aspect.MAGIC, 16), core, new ItemStack[]{
                new ItemStack(ModItems.weakPrimalObject, 1, 1),
                voidSeed,
                voidSeed,
                AspectHelper.getCrystalEssence(Aspect.FIRE),
                AspectHelper.getCrystalEssence(Aspect.FIRE),
                AspectHelper.getWispEssence(Aspect.FIRE),
                magicSalt,
                magicSalt
        }));
        Researches.recipes.put(Researches.PRIM_WATER, ThaumcraftApi.addInfusionCraftingRecipe(Researches.PRIM_WATER, new ItemStack(ModItems.primalObject, 1, 2), 6, new AspectList().add(Aspect.WATER, 48).add(Aspect.MAGIC, 16), core, new ItemStack[]{
                new ItemStack(ModItems.weakPrimalObject, 1, 2),
                voidSeed,
                voidSeed,
                AspectHelper.getCrystalEssence(Aspect.WATER),
                AspectHelper.getCrystalEssence(Aspect.WATER),
                AspectHelper.getWispEssence(Aspect.WATER),
                magicSalt,
                magicSalt
        }));
        Researches.recipes.put(Researches.PRIM_EARTH, ThaumcraftApi.addInfusionCraftingRecipe(Researches.PRIM_EARTH, new ItemStack(ModItems.primalObject, 1, 3), 6, new AspectList().add(Aspect.EARTH, 48).add(Aspect.MAGIC, 16), core, new ItemStack[]{
                new ItemStack(ModItems.weakPrimalObject, 1, 3),
                voidSeed,
                voidSeed,
                AspectHelper.getCrystalEssence(Aspect.EARTH),
                AspectHelper.getCrystalEssence(Aspect.EARTH),
                AspectHelper.getWispEssence(Aspect.EARTH),
                magicSalt,
                magicSalt
        }));
        Researches.recipes.put(Researches.PRIM_ORDER, ThaumcraftApi.addInfusionCraftingRecipe(Researches.PRIM_ORDER, new ItemStack(ModItems.primalObject, 1, 4), 5, new AspectList().add(Aspect.ORDER, 48).add(Aspect.MAGIC, 16), core, new ItemStack[]{
                new ItemStack(ModItems.weakPrimalObject, 1, 4),
                voidSeed,
                voidSeed,
                AspectHelper.getCrystalEssence(Aspect.ORDER),
                AspectHelper.getCrystalEssence(Aspect.ORDER),
                AspectHelper.getWispEssence(Aspect.ORDER),
                magicSalt,
                magicSalt
        }));
        Researches.recipes.put(Researches.PRIM_ENTROPY, ThaumcraftApi.addInfusionCraftingRecipe(Researches.PRIM_ENTROPY, new ItemStack(ModItems.primalObject, 1, 5), 7, new AspectList().add(Aspect.ENTROPY, 48).add(Aspect.MAGIC, 16), core, new ItemStack[]{
                new ItemStack(ModItems.weakPrimalObject, 1, 5),
                voidSeed,
                voidSeed,
                AspectHelper.getCrystalEssence(Aspect.ENTROPY),
                AspectHelper.getCrystalEssence(Aspect.ENTROPY),
                AspectHelper.getWispEssence(Aspect.ENTROPY),
                magicSalt,
                magicSalt
        }));
        Researches.recipes.put(Researches.PRIM_PRIMAL, ThaumcraftApi.addInfusionCraftingRecipe(Researches.PRIM_PRIMAL, new ItemStack(ModItems.primalObject, 1, 7), 9, new AspectList().add(Aspect.AIR, 64).add(Aspect.FIRE, 64).add(Aspect.WATER, 64).add(Aspect.EARTH, 64).add(Aspect.ORDER, 64).add(Aspect.ENTROPY, 64), new ItemStack(ModItems.primalObject, 1, 6), new ItemStack[]{
                new ItemStack(ModItems.primalObject, 1, 0),
                new ItemStack(ModItems.primalObject, 1, 1),
                new ItemStack(ModItems.primalObject, 1, 2),
                new ItemStack(ModItems.primalObject, 1, 3),
                new ItemStack(ModItems.primalObject, 1, 4),
                new ItemStack(ModItems.primalObject, 1, 5)
        }));
    }
}
