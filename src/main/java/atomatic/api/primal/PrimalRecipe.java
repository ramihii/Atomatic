package atomatic.api.primal;

import thaumcraft.api.aspects.AspectList;

import net.minecraft.item.ItemStack;

public class PrimalRecipe
{
    private final String research;
    private final ItemStack output;
    private final AspectList aspects;
    private final PrimalObject primal;
    private final ItemStack input;

    /**
     * Constructs a new {@link PrimalRecipe}.
     *
     * @param research the research key required for this recipe to work.
     * @param output   the recipe's output.
     * @param primal   the {@link PrimalObject} used to craft this.
     * @param input    the recipe's input.
     */
    public PrimalRecipe(String research, ItemStack output, PrimalObject primal, ItemStack input)
    {
        this(research, output, null, primal, input);
    }

    /**
     * Constructs a new {@link PrimalRecipe}. You shouldn't use this TIL I decide to make this public.
     *
     * @param research the research key required for this recipe to work.
     * @param output   the recipe's NBT sensitive output.
     * @param aspects  the primal aspects required to craft this.
     * @param primal   the {@link PrimalObject} used to craft this.
     * @param input    the recipe's NBT sensitive input.
     */
    private PrimalRecipe(String research, ItemStack output, AspectList aspects, PrimalObject primal, ItemStack input)
    {
        this.research = research;

        if (output.stackTagCompound != null)
        {
            ItemStack stack = new ItemStack(output.getItem(), 1, output.getItemDamage());
            stack.setTagCompound(output.getTagCompound());
            this.output = stack;
        }
        else
        {
            this.output = new ItemStack(output.getItem(), 1, output.getItemDamage());
        }

        if (aspects == null)
        {
            AspectList aspectList = new AspectList();
            int amount = 30;

            for (int i = 0; i < primal.getAspects().length; i++)
            {
                aspectList.add(primal.getAspects()[i], amount);
                amount -= 5;
            }

            this.aspects = aspectList;
        }
        else
        {
            this.aspects = aspects;
        }

        this.primal = primal;

        if (input.stackTagCompound != null)
        {
            ItemStack stack = new ItemStack(input.getItem(), 1, input.getItemDamage());
            stack.setTagCompound(input.getTagCompound());
            this.input = stack;
        }
        else
        {
            this.input = new ItemStack(input.getItem(), 1, input.getItemDamage());
        }
    }

    /**
     * Checks if this {@link PrimalRecipe} matches the given {@link PrimalRecipe}.
     *
     * @param recipe the other {@link PrimalRecipe}.
     *
     * @return {@code true} if the two {@link PrimalRecipe} match, otherwise {@code false}.
     */
    public boolean matches(PrimalRecipe recipe)
    {
        if (!research.equals(recipe.research))
        {
            return false;
        }

        if (!output.isItemEqual(recipe.output))
        {
            return false;
        }

        if (aspects.aspects.size() != recipe.aspects.aspects.size())
        {
            return false;
        }

        for (int i = 0; i < aspects.getAspectsSorted().length; i++)
        {
            if (!aspects.getAspectsSorted()[i].getTag().equals(recipe.aspects.getAspectsSorted()[i].getTag()))
            {
                return false;
            }

            if (aspects.getAmount(aspects.getAspectsSorted()[i]) != recipe.aspects.getAmount(recipe.aspects.getAspectsSorted()[i]))
            {
                return false;
            }
        }

        return primal.toString().equals(recipe.primal.toString()) && input.isItemEqual(recipe.input);
    }
}
