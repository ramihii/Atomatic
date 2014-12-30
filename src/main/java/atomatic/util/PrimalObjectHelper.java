package atomatic.util;

import atomatic.api.primal.PrimalObject;
import atomatic.init.ModItems;

import net.minecraft.item.ItemStack;

public class PrimalObjectHelper
{
    public static ItemStack getPrimalObject(PrimalObject object)
    {
        return new ItemStack(ModItems.primalObject, 1, object.ordinal());
    }
}
