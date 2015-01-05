package atomatic.client.model;

import atomatic.reference.Textures;

import net.minecraftforge.client.model.obj.WavefrontObject;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class Models
{
    public static WavefrontObject modelCrystalSimple = new WavefrontObject(Textures.Models.MODEL_CRYSTAL_SIMPLE);
    public static WavefrontObject modelCrystalComplex = new WavefrontObject(Textures.Models.MODEL_CRYSTAL_COMPLEX);
    public static WavefrontObject modelAltarRelay = new WavefrontObject(Textures.Models.MODEL_ALTAR_RELAY);
}
