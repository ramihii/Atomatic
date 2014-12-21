package atomatic.block;

import atomatic.reference.Names;

import thaumcraft.api.crafting.IInfusionStabiliser;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockInverseDirt extends BlockA implements IInfusionStabiliser
{
    public BlockInverseDirt()
    {
        super(Material.ground);
        this.setBlockName(Names.Blocks.INVERSE_DIRT);
        this.setHardness(0.5F);
        this.setStepSound(soundTypeGravel);
    }

    @Override
    public Item getItemDropped(int par1, Random random, int par2)
    {
        return Item.getItemFromBlock(Blocks.dirt);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        blockIcon = iconRegister.registerIcon("dirt");
    }

    /**
     * returns true if the block can stabilise things
     *
     * @param world
     * @param x
     * @param y
     * @param z
     */
    @Override
    public boolean canStabaliseInfusion(World world, int x, int y, int z)
    {
        return true;
    }
}
