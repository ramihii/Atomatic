package atomatic.item;

import net.minecraft.util.IIcon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemPrimalObject extends ItemA
{
    @SideOnly(Side.CLIENT)
    private IIcon[] icons;

    public ItemPrimalObject()
    {
        super();
    }
}
