package atomatic.util;

import atomatic.api.primal.AdjustEffect;
import atomatic.api.primal.Adjustment;

public class AdjustmentHelper
{
    public static boolean isRuntimeAdjustment(Adjustment adjustment)
    {
        return !(adjustment.effect == AdjustEffect.LESS_VIS || adjustment.effect == AdjustEffect.LESS_WAND_VIS || adjustment.effect == AdjustEffect.NO_WAND_VIS || adjustment.effect == AdjustEffect.LESS_WARP || adjustment.effect == AdjustEffect.NO_WARP);
    }
}
