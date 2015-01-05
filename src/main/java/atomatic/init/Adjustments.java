package atomatic.init;

import atomatic.api.AtomaticApi;
import atomatic.api.adjusting.AdjustEffect;

import atomatic.reference.ThaumcraftReference;

public class Adjustments
{
    public static void init()
    {
        AtomaticApi.addPrimalCraftingAdjuster(ThaumcraftReference.balancedShard, AdjustEffect.LESS_WAND_VIS, 2);
    }
}
