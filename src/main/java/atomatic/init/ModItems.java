package atomatic.init;

import atomatic.item.ItemA;
import atomatic.item.ItemCreativeTab;
import atomatic.item.ItemPrimalObject;
import atomatic.item.ItemWeakPrimalObject;
import atomatic.reference.Names;
import atomatic.reference.Reference;

import cpw.mods.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModItems
{
    public static final ItemCreativeTab creativeTab = new ItemCreativeTab();
    public static final ItemA primalObject = new ItemPrimalObject();
    public static final ItemA weakPrimalObject = new ItemWeakPrimalObject();

    public static void init()
    {
        GameRegistry.registerItem(creativeTab, Names.Items.CREATIVE_TAB);
        GameRegistry.registerItem(primalObject, Names.Items.PRIMAL_OBJECT);
        GameRegistry.registerItem(weakPrimalObject, Names.Items.WEAK_PRIMAL_OBJECT);
    }
}
