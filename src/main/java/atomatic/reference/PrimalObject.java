package atomatic.reference;

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

    @Override
    public String toString()
    {
        return this.name().contains("_") ? this.name().split("_")[0].toLowerCase() + this.name().split("_")[1].substring(0, 1).toUpperCase() + this.name().split("_")[1].substring(1) : this.name().toLowerCase();
    }
}
