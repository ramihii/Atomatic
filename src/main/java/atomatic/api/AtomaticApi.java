package atomatic.api;

import atomatic.api.primal.AdjustEffect;
import atomatic.api.primal.Adjustment;
import atomatic.api.primal.PrimalObject;
import atomatic.api.primal.PrimalRecipe;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AtomaticApi
{
    /**
     * All {@link PrimalRecipe}s.
     */
    private static List<PrimalRecipe> primalRecipes = new ArrayList<PrimalRecipe>();

    /**
     * All primal crafting adjusters.
     */
    private static Map<List<Object>, Adjustment> primalCraftingAdjusters = new HashMap<List<Object>, Adjustment>();

    /**
     * Warp effects for {@link PrimalRecipe}s.
     */
    private static Map<Integer, Integer> primalRecipeWarpMap = new HashMap<Integer, Integer>();

    /**
     * Adds a new {@link PrimalRecipe}.
     *
     * @param research the research key required for this recipe to work. {@code null} or empty {@code String} if no
     *                 research is required.
     * @param output   the recipe's output.
     * @param primal   the {@link PrimalObject} used to craft this.
     * @param input    the recipe's input.
     *
     * @return the created {@link PrimalRecipe}.
     */
    public static PrimalRecipe addPrimalRecipe(String research, ItemStack output, PrimalObject primal, ItemStack input)
    {
        return addPrimalRecipe(research, output, 0, primal, input);
    }

    /**
     * Adds a new {@link PrimalRecipe}.
     *
     * @param research the research key required for this recipe to work. {@code null} or empty {@code String} if no
     *                 research is required.
     * @param output   the recipe's output.
     * @param warp     the amount of warp gained by starting this crafting process.
     * @param primal   the {@link PrimalObject} used to craft this.
     * @param input    the recipe's input.
     *
     * @return the created {@link PrimalRecipe}.
     */
    public static PrimalRecipe addPrimalRecipe(String research, ItemStack output, int warp, PrimalObject primal, ItemStack input)
    {
        PrimalRecipe recipe = new PrimalRecipe(research, output, primal, input);
        primalRecipes.add(recipe);

        if (warp > 0)
        {
            addWarpToPrimalRecipe(recipe, warp);
        }

        return recipe;
    }

    /**
     * Gives the first matching registered {@link PrimalRecipe} from the for an input {@link ItemStack}.
     *
     * @param input the input {@link ItemStack}.
     *
     * @return the {@link PrimalRecipe}.
     */
    public static PrimalRecipe getPrimalRecipe(ItemStack input)
    {
        if (input != null)
        {
            for (PrimalRecipe recipe : primalRecipes)
            {
                if (recipe.getInput().isItemEqual(input))
                {
                    return recipe;
                }
            }
        }

        return null;
    }

    /**
     * Gives a registered {@link PrimalRecipe} for an input {@link ItemStack} and {@link PrimalObject}.
     *
     * @param input  the input {@link ItemStack}.
     * @param primal the {@link PrimalObject} used in the recipe.
     *
     * @return the {@link PrimalRecipe}.
     */
    public static PrimalRecipe getPrimalRecipe(ItemStack input, PrimalObject primal)
    {
        if (input != null && primal != null)
        {
            for (PrimalRecipe recipe : primalRecipes)
            {
                if (recipe.getInput().isItemEqual(input) && recipe.getPrimal() == primal)
                {
                    return recipe;
                }
            }
        }

        return null;
    }

    /**
     * Gives a registered {@link PrimalRecipe} for a hash code.
     *
     * @param hash the hash code.
     *
     * @return the {@link PrimalRecipe}.
     */
    public static PrimalRecipe getPrimalRecipeForHash(int hash)
    {
        for (PrimalRecipe recipe : primalRecipes)
        {
            if (recipe.hashCode() == hash)
            {
                return recipe;
            }
        }

        return null;
    }

    /**
     * Checks if a {@link PrimalRecipe} for an input {@link ItemStack} exists.
     *
     * @param input the input {@link ItemStack}.
     *
     * @return {@code true} if the {@link PrimalRecipe} exists, otherwise {@code false}.
     */
    public static boolean primalRecipeExists(ItemStack input)
    {
        return getPrimalRecipe(input) != null;
    }

    /**
     * Checks if a {@link PrimalRecipe} for an input {@link ItemStack} and {@link PrimalObject} exists.
     *
     * @param input  the input {@link ItemStack}.
     * @param primal the {@link PrimalObject} used in the recipe.
     *
     * @return {@code true} if the {@link PrimalRecipe} exists, otherwise {@code false}.
     */
    public static boolean primalRecipeExists(ItemStack input, PrimalObject primal)
    {
        return getPrimalRecipe(input, primal) != null;
    }

    /**
     * Adds a new primal crafting adjuster.
     *
     * @param stack    the primal crafting adjuster.
     * @param effect   the {@link AdjustEffect}.
     * @param strength the strength of the {@link AdjustEffect}.
     */
    public static void addPrimalCraftingAdjuster(ItemStack stack, AdjustEffect effect, int strength)
    {
        primalCraftingAdjusters.put(Arrays.asList(stack.getItem(), stack.getItemDamage()), new Adjustment(effect, strength));
    }

    /**
     * Gives a primal crafting adjustment.
     *
     * @param stack the {@link ItemStack}.
     *
     * @return the {@link Adjustment}.
     */
    public static Adjustment getAdjustment(ItemStack stack)
    {
        if (stack != null && primalCraftingAdjusters.containsKey(Arrays.asList(stack.getItem(), stack.getItemDamage())))
        {
            return primalCraftingAdjusters.get(Arrays.asList(stack.getItem(), stack.getItemDamage()));
        }

        return null;
    }

    /**
     * Adds permanent warp into {@link PrimalRecipe}. The amount is used as it is to add permanent warp, but some
     * temporary warp is calculated from this value.
     *
     * @param recipe the {@link PrimalRecipe}.
     * @param amount the amount of warp.
     */
    public static void addWarpToPrimalRecipe(PrimalRecipe recipe, int amount)
    {
        addWarpToPrimalRecipe(recipe.hashCode(), amount);
    }

    /**
     * Adds permanent warp into {@link PrimalRecipe}. The amount is used as it is to add permanent warp, but some
     * temporary warp is calculated from this value.
     *
     * @param recipeHash the {@link PrimalRecipe#hashCode} of the {@link PrimalRecipe}.
     * @param amount     the amount of warp.
     */
    public static void addWarpToPrimalRecipe(int recipeHash, int amount)
    {
        primalRecipeWarpMap.put(recipeHash, amount);
    }

    /**
     * Tells how much permanent warp is gained from {@link PrimalRecipe}.
     *
     * @param recipe the {@link PrimalRecipe}.
     *
     * @return the amount of permanent warp gained from the {@link PrimalRecipe}.
     */
    public static int getPrimalRecipeWarp(PrimalRecipe recipe)
    {
        return recipe == null ? 0 : getPrimalRecipeWarp(recipe.hashCode());
    }

    /**
     * Tells how much permanent warp is gained from {@link PrimalRecipe}.
     *
     * @param recipeHash the {@link PrimalRecipe#hashCode} of the {@link PrimalRecipe}.
     *
     * @return the amount of permanent warp gained from the {@link PrimalRecipe}.
     */
    public static int getPrimalRecipeWarp(int recipeHash)
    {
        if (primalRecipeWarpMap.containsKey(recipeHash))
        {
            return primalRecipeWarpMap.get(recipeHash);
        }

        return 0;
    }
}
