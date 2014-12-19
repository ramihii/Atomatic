package atomatic.creativetab;

import atomatic.reference.Reference;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class CreativeTab
{
    public static final CreativeTabs A_TAB = new CreativeTabs(Reference.MOD_ID.toLowerCase())
    {
        @Override
        public Item getTabIconItem()
        {
            return Items.apple; // ModItems.something;
        }
    };
}
