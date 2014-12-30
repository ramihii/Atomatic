package atomatic.api;

import atomatic.api.primal.PrimalObject;
import atomatic.api.primal.PrimalRecipe;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class AtomaticApi
{
    /**
     * The added {@link PrimalRecipe}s.
     */
    private static List<PrimalRecipe> primalRecipes = new ArrayList<PrimalRecipe>();

    /**
     * Adds a new {@link PrimalRecipe}.
     *
     * @param research the research key required for this recipe to work.
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
}
