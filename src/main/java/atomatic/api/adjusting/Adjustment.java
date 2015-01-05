package atomatic.api.adjusting;

public class Adjustment
{
    public final AdjustEffect effect;
    public final int strength;

    public Adjustment(AdjustEffect effect, int strength)
    {
        this.effect = effect;
        this.strength = strength;
    }

    public Adjustment copy()
    {
        return new Adjustment(effect, strength);
    }
}
