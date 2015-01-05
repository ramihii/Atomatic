package atomatic.api;

import atomatic.api.adjusting.Adjustment;

public interface ICrystal extends IRelay
{
    public boolean isPrimal();

    public boolean isAdjustment();

    public Adjustment getAdjustment();
}
