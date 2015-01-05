package atomatic.client.render;

import atomatic.client.model.Models;
import atomatic.util.ActiveNumber;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.client.model.IModelCustom;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderAltarRelay extends TileEntitySpecialRenderer
{
    protected final IModelCustom model;
    protected final ResourceLocation texture;
    protected ActiveNumber rotation = new ActiveNumber(ActiveNumber.MODE_KEEP_WITHIN_BOUNDS).setBounds(0D, 359D);
    protected ActiveNumber hover = new ActiveNumber(ActiveNumber.MODE_OSCILLATE).setBounds(0D, 2D).setDirection(ActiveNumber.DIR_DEC);

    public RenderAltarRelay(String texture)
    {
        this(new ResourceLocation(texture));
    }

    public RenderAltarRelay(ResourceLocation texture)
    {
        this(Models.modelAltarRelay, texture);
    }

    public RenderAltarRelay(IModelCustom model, ResourceLocation texture)
    {
        this.model = model;
        this.texture = texture;
    }

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float partTick)
    {
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(texture);

        GL11.glPushMatrix();

        GL11.glTranslated(x + .5D, y, z + .5D);
        // GL11.glRotated(0D, 0D, 0D, 0D);
        GL11.glScaled(1.3D, 1.3D, 1.3D);

        model.renderAll();
        GL11.glPopMatrix();
    }

    public IModelCustom getModel()
    {
        return model;
    }

    public ResourceLocation getTexture()
    {
        return texture;
    }
}
