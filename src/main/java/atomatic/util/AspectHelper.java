package atomatic.util;

import thaumcraft.api.ItemApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IEssentiaContainerItem;

import net.minecraft.item.ItemStack;

public class AspectHelper
{
    public static ItemStack getWispEssence(Aspect aspect)
    {
        ItemStack ret = ItemApi.getItem("itemWispEssence", 0);
        ((IEssentiaContainerItem) ret.getItem()).setAspects(ret, new AspectList().add(aspect, 1));
        return ret;
    }

    public static ItemStack getCrystalEssence(Aspect aspect)
    {
        ItemStack ret = ItemApi.getItem("itemCrystalEssence", 0);
        ((IEssentiaContainerItem) ret.getItem()).setAspects(ret, new AspectList().add(aspect, 1));
        return ret;
    }
}
