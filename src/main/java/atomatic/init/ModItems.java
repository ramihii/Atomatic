package atomatic.init;

import atomatic.item.ItemCreativeTab;
import atomatic.reference.Names;
import atomatic.reference.Reference;

import cpw.mods.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModItems
{
    public static final ItemCreativeTab creativeTab = new ItemCreativeTab();

    public static void init()
    {
        GameRegistry.registerItem(creativeTab, Names.Items.CREATIVE_TAB);
    }
}
