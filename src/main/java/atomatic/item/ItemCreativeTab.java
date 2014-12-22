package atomatic.item;

import atomatic.reference.Names;

public class ItemCreativeTab extends ItemA
{
    public ItemCreativeTab()
    {
        super();
        this.setUnlocalizedName(Names.Items.CREATIVE_TAB);
        this.setCreativeTab(null);
        this.setMaxStackSize(1);
    }
}
