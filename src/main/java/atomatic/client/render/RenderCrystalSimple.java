package atomatic.client.render;

import atomatic.client.model.Models;

import net.minecraft.util.ResourceLocation;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderCrystalSimple extends RenderCrystal
{
    public RenderCrystalSimple(ResourceLocation texture)
    {
        super(Models.modelCrystalSimple, texture);
    }

    public RenderCrystalSimple(String texture)
    {
        this(new ResourceLocation(texture));
    }
}
