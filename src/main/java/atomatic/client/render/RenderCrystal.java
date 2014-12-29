package atomatic.client.render;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.client.model.IModelCustom;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public abstract class RenderCrystal extends TileEntitySpecialRenderer
{
    protected final IModelCustom model;
    protected final ResourceLocation texture;
    // protected ActiveNumber rotation = new ActiveNumber(ActiveNumber.MODE_KEEP_WITHIN_BOUNDS).setBounds(0D, 359D);
    // protected ActiveNumber hover = new ActiveNumber(ActiveNumber.MODE_OSCILLATE).setBounds(0D, 2D).setDirection(ActiveNumber.DIR_DEC);

    public RenderCrystal(IModelCustom model, ResourceLocation texture)
    {
        this.model = model;
        this.texture = texture;
    }

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float partTick)
    {
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(texture);

        GL11.glPushMatrix();

        gl11(tileEntity, x, y, z, partTick);

        model.renderAll();
        GL11.glPopMatrix();
    }

    protected void gl11(TileEntity tileEntity, double x, double y, double z, float partTick)
    {
        GL11.glTranslated(x + .5d, y, z + .5d);
        // GL11.glRotated(0D, 0D, 0D, 0D);
        GL11.glScaled(.5d, .4d, .5d);
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
