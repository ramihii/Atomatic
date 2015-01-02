package atomatic.item;

import atomatic.api.primal.PrimalObject;
import atomatic.reference.Names;
import atomatic.reference.Textures;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import java.util.List;

public class ItemPrimalObject extends ItemA
{
    @SideOnly(Side.CLIENT)
    private IIcon[] icons;

    public ItemPrimalObject()
    {
        super();
        this.setMaxStackSize(64);
        this.setHasSubtypes(true);
        this.setUnlocalizedName(Names.Items.PRIMAL_OBJECT);
    }

    public static PrimalObject getPrimalObject(ItemStack stack)
    {
        return getPrimalObject(stack.getItemDamage());
    }

    public static PrimalObject getPrimalObject(int meta)
    {
        return PrimalObject.values()[meta];
    }

    @Override
    public String getUnlocalizedName()
    {
        return String.format("item.%s%s", Textures.RESOURCE_PREFIX, Names.Items.PRIMAL_OBJECT);
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack)
    {
        return String.format("item.%s%s.%s", Textures.RESOURCE_PREFIX, Names.Items.PRIMAL_OBJECT, PrimalObject.values()[MathHelper.clamp_int(itemStack.getItemDamage(), 0, PrimalObject.values().length - 1)].toString());
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void getSubItems(Item item, CreativeTabs creativeTab, List list)
    {
        for (int meta = 0; meta < PrimalObject.values().length; ++meta)
        {
            list.add(new ItemStack(this, 1, meta));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int meta)
    {
        return icons[MathHelper.clamp_int(meta, 0, PrimalObject.values().length - 1)];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister)
    {
        icons = new IIcon[PrimalObject.values().length];

        for (int i = 0; i < PrimalObject.values().length; i++)
        {
            icons[i] = iconRegister.registerIcon(Textures.RESOURCE_PREFIX + Names.Items.PRIMAL_OBJECT + "." + PrimalObject.values()[i].toString());
        }
    }

	@Override
    @SideOnly(Side.CLIENT)
	@SuppressWarnings("rawtypes")
    public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List list, boolean flag)
    {

    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
        // TODO
        /* if (stack.getItemDamage() == PrimalObject.CORE.ordinal())
        {
            if (!NBTHelper.hasTag(stack, Names.NBT.USED))
            {
                NBTHelper.setBoolean(stack, Names.NBT.USED, false);
            }

            if (!NBTHelper.getBoolean(stack, Names.NBT.USED))
            {
                SpawnHelper.spawnItemAtPlayer(player, ThaumcraftReference.primordialPearl);
                NBTHelper.setBoolean(stack, Names.NBT.USED, true);
                return true;
            }

            return false;
        } */

        return false;
    }
}
