package atomatic.tileentity;

import atomatic.api.primal.Adjustment;
import atomatic.api.primal.ICrystal;

public class TileEntityCrystalPrimal extends TileEntityA implements ICrystal
{
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
