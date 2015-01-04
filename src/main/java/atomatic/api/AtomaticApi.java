package atomatic.api;

import atomatic.api.primal.PrimalObject;
import atomatic.api.primal.PrimalRecipe;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class AtomaticApi
{
    /**
     * All {@link PrimalRecipe}s.
     */
    private static List<PrimalRecipe> primalRecipes = new ArrayList<PrimalRecipe>();

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
        PrimalRecipe recipe = new PrimalRecipe(research, output, primal, input);
        primalRecipes.add(recipe);
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
}
