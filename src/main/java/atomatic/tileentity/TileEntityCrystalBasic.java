package atomatic.tileentity;

import atomatic.api.ICrystal;
import atomatic.api.adjusting.Adjustment;

public class TileEntityCrystalBasic extends TileEntityA implements ICrystal
{
    @Override
    public boolean isPrimal()
    {
        return false;
    }

    @Override
    public boolean isAdjustment()
    {
        return false;
    }

    @Override
    public Adjustment getAdjustment()
    {
        return null;
    }
}
