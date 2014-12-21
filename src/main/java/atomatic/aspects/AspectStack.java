package atomatic.aspects;

import thaumcraft.api.aspects.Aspect;

public class AspectStack
{
    private final Aspect aspect;
    private final int amount;

    public AspectStack(Aspect aspect)
    {
        this(aspect, 1);
    }

    public AspectStack(Aspect aspect, int amount)
    {
        this.aspect = aspect;
        this.amount = amount;
    }

    public static AspectStack[] createStacks(Aspect... aspects)
    {
        AspectStack[] aspectStacks = new AspectStack[aspects.length];

        for (int i = 0; i < aspects.length; i++)
        {
            aspectStacks[i] = new AspectStack(aspects[i]);
        }

        return aspectStacks;
    }

    public Aspect getAspect()
    {
        return aspect;
    }

    public int getAmount()
    {
        return amount;
    }
}
