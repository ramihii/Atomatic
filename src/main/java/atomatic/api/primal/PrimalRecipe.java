package atomatic.api.primal;

import atomatic.api.util.AspectListHelper;

import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

import net.minecraft.item.ItemStack;

public class PrimalRecipe
{
    public static final int DEFAULT_ASPECT_AMOUNT = 100;
    public static final int DEFAULT_STARTING_ASPECT_AMOUNT = 10;
    public static final int WAND_MULTIPLIER = 100;

    private final String research;
    private final ItemStack output;
    private final AspectList aspects;
    private final AspectList startingAspects;
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
     * Constructs a new {@link PrimalRecipe}.
     *
     * @param research the research key required for this recipe to work.
     * @param output   the recipe's output.
     * @param aspects  the primal aspects required to craft this.
     * @param primal   the {@link PrimalObject} used to craft this.
     * @param input    the recipe's input.
     */
    public PrimalRecipe(String research, ItemStack output, AspectList aspects, PrimalObject primal, ItemStack input)
    {
        this(research, output, aspects, null, primal, input);
    }

    /**
     * Constructs a new {@link PrimalRecipe}.
     *
     * @param research        the research key required for this recipe to work.
     * @param output          the recipe's NBT sensitive output.
     * @param aspects         the primal aspects required to craft this.
     * @param startingAspects the primal aspects drawn from the wand at the beginning of the crafting process.
     * @param primal          the {@link PrimalObject} used to craft this.
     * @param input           the recipe's NBT sensitive input.
     */
    private PrimalRecipe(String research, ItemStack output, AspectList aspects, AspectList startingAspects, PrimalObject primal, ItemStack input)
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
            int amount = DEFAULT_ASPECT_AMOUNT;

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

        if (startingAspects == null)
        {
            AspectList aspectList = new AspectList();
            int amount = DEFAULT_STARTING_ASPECT_AMOUNT;

            for (int i = 0; i < primal.getAspects().length; i++)
            {
                aspectList.add(primal.getAspects()[i], amount);
                amount--;
            }

            this.startingAspects = aspectList;
        }
        else
        {
            this.startingAspects = startingAspects;
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

    public AspectList getAspects()
    {
        return aspects;
    }

    public AspectList getAspectsForWand()
    {
        AspectList aspectList = new AspectList();

        for (int i = 0; i < aspects.size(); i++)
        {
            aspectList.add(aspects.getAspectsSorted()[i], aspects.getAmount(aspects.getAspectsSorted()[i]) * WAND_MULTIPLIER);
        }

        return aspectList;
    }

    public AspectList getStartingAspects()
    {
        return startingAspects;
    }

    public AspectList getStartingAspectsForWand()
    {
        AspectList aspectList = new AspectList();

        for (int i = 0; i < startingAspects.size(); i++)
        {
            aspectList.add(startingAspects.getAspectsSorted()[i], startingAspects.getAmount(startingAspects.getAspectsSorted()[i]) * WAND_MULTIPLIER);
        }

        return aspectList;
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

        if (!AspectListHelper.equals(aspects, recipe.aspects))
        {
            return false;
        }

        if (!AspectListHelper.equals(startingAspects, recipe.startingAspects))
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
        int result = research == null || research.equals("") ? 1 : research.hashCode();

        result = 31 * result + output.hashCode();

        for (Aspect aspect : aspects.getAspectsSorted())
        {
            result = 31 * result + aspect.getTag().hashCode();
        }

        for (Aspect aspect : startingAspects.getAspectsSorted())
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
        return "PrimalRecipe{" + "research='" + research + '\'' + ", output=" + output + ", aspects=" + AspectListHelper.toString(aspects) + ", startingAspects=" + AspectListHelper.toString(startingAspects) + ", primal=" + primal + ", input=" + input + '}';
    }
}
