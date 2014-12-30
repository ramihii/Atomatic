package atomatic.api.primal;

import thaumcraft.api.aspects.Aspect;

public enum PrimalObject
{
    AIR_WEAK,
    FIRE_WEAK,
    WATER_WEAK,
    EARTH_WEAK,
    ORDER_WEAK,
    ENTROPY_WEAK,
    AIR,
    FIRE,
    WATER,
    EARTH,
    ORDER,
    ENTROPY,
    CORE,
    PRIMAL;

    public Aspect[] getAspects()
    {
        switch (this)
        {
            case AIR_WEAK:
                return new Aspect[]{Aspect.AIR, Aspect.EARTH};
            case FIRE_WEAK:
                return new Aspect[]{Aspect.FIRE, Aspect.WATER};
            case WATER_WEAK:
                return new Aspect[]{Aspect.WATER, Aspect.ORDER};
            case EARTH_WEAK:
                return new Aspect[]{Aspect.EARTH, Aspect.FIRE};
            case ORDER_WEAK:
                return new Aspect[]{Aspect.ORDER, Aspect.ENTROPY};
            case ENTROPY_WEAK:
                return new Aspect[]{Aspect.ENTROPY, Aspect.AIR};
            case AIR:
                return new Aspect[]{Aspect.AIR};
            case FIRE:
                return new Aspect[]{Aspect.FIRE};
            case WATER:
                return new Aspect[]{Aspect.WATER};
            case EARTH:
                return new Aspect[]{Aspect.EARTH};
            case ORDER:
                return new Aspect[]{Aspect.ORDER};
            case ENTROPY:
                return new Aspect[]{Aspect.ENTROPY};
            case CORE:
                return new Aspect[]{Aspect.AIR, Aspect.FIRE, Aspect.WATER, Aspect.EARTH, Aspect.ORDER, Aspect.ENTROPY};
            case PRIMAL:
                return new Aspect[]{Aspect.AIR, Aspect.FIRE, Aspect.WATER, Aspect.EARTH, Aspect.ORDER, Aspect.ENTROPY};
        }

        return null;
    }

    @Override
    public String toString()
    {
        return this.name().contains("_") ? this.name().split("_")[0].toLowerCase() + this.name().split("_")[1].substring(0, 1).toUpperCase() + this.name().split("_")[1].substring(1).toLowerCase() : this.name().toLowerCase();
    }
}
