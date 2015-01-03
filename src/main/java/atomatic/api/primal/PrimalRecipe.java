package atomatic.api.primal;

import atomatic.api.util.AspectListHelper;

import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

import net.minecraft.item.ItemStack;

public class PrimalRecipe
{
    private final String research;
    private final ItemStack output;
    private final int time;
    private final AspectList aspects;
    private final PrimalObject primal;
    private final ItemStack input;

    /**
     * Constructs a new {@link PrimalRecipe}.
     *
     * @param research the research key required for this recipe to work.
     * @param output   the recipe's output.
     * @param time     the time this recipe takes to make in ticks.
     * @param primal   the {@link PrimalObject} used to craft this.
     * @param input    the recipe's input.
     */
    public PrimalRecipe(String research, ItemStack output, int time, PrimalObject primal, ItemStack input)
    {
        this(research, output, time, null, primal, input);
    }

    /**
     * Constructs a new {@link PrimalRecipe}. You shouldn't use this TIL I decide to make this public.
     *
     * @param research the research key required for this recipe to work.
     * @param output   the recipe's NBT sensitive output.
     * @param time     the time this recipe takes to make in ticks.
     * @param aspects  the primal aspects required to craft this.
     * @param primal   the {@link PrimalObject} used to craft this.
     * @param input    the recipe's NBT sensitive input.
     */
    private PrimalRecipe(String research, ItemStack output, int time, AspectList aspects, PrimalObject primal, ItemStack input)
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

        this.time = time;

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

    public String getResearch()
    {
        return research;
    }

    public ItemStack getOutput()
    {
        return output;
    }

    public int getTime()
    {
        return time;
    }

    public AspectList getAspects()
    {
        return aspects;
    }

    public PrimalObject getPrimal()
    {
        return primal;
    }

    public ItemStack getInput()
    {
        return input;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }

        if (o == null || getClass() != o.getClass())
        {
            return false;
        }

        PrimalRecipe recipe = (PrimalRecipe) o;

        if (time != recipe.time)
        {
            return false;
        }

        if (!AspectListHelper.equals(aspects, recipe.aspects))
        {
            return false;
        }

        if (!input.equals(recipe.input))
        {
            return false;
        }

        if (!output.equals(recipe.output))
        {
            return false;
        }

        if (primal != recipe.primal)
        {
            return false;
        }

        if (!research.equals(recipe.research))
        {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = research.hashCode();

        result = 31 * result + output.hashCode();
        result = 31 * result + time;

        for (Aspect aspect : aspects.getAspectsSorted())
        {
            result = 31 * result + aspect.getTag().hashCode();
        }

        result = 31 * result + primal.hashCode();
        result = 31 * result + input.hashCode();

        return result;
    }

    @Override
    public String toString()
    {
        return "PrimalRecipe{" + "research='" + research + '\'' + ", output=" + output + ", time=" + time + ", aspects=" + aspects + ", primal=" + primal + ", input=" + input + '}';
    }
}
