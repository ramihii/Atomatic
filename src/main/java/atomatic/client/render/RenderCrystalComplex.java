package atomatic.client.render;

import atomatic.client.model.Models;

import net.minecraft.util.ResourceLocation;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderCrystalComplex extends RenderCrystal
{
    public RenderCrystalComplex(String texture)
    {
        this(new ResourceLocation(texture));
    }

    public RenderCrystalComplex(ResourceLocation texture)
    {
        super(Models.modelCrystalComplex, texture);
    }
}
