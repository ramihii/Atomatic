package atomatic.client.render;

import atomatic.block.BlockCrystalPrimal;
import atomatic.block.BlockPrimalAltar;
import atomatic.client.model.Models;
import atomatic.reference.Textures;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

import org.lwjgl.opengl.GL11;

public class RenderBlock implements ISimpleBlockRenderingHandler
{
    public static final int ID = RenderingRegistry.getNextAvailableRenderId();

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
    {
        if (modelID == ID)
        {
            GL11.glPushMatrix();

            if (block instanceof BlockCrystalPrimal)
            {
                FMLClientHandler.instance().getClient().renderEngine.bindTexture(Textures.Models.TEXTURE_CRYSTAL_PRIMAL);
                crystalSimpleGL11();
            }
            /* else if (block instanceof BlockPrimalAltar)
            {
                FMLClientHandler.instance().getClient().renderEngine.bindTexture(Textures.Models.TEXTURE_PRIMAL_ALTAR);
                crystalSimpleGL11();
            } */

            GL11.glPopMatrix();
        }
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelID, RenderBlocks renderer)
    {
        if (modelID == ID)
        {
            Tessellator tes = Tessellator.instance;
        }

        return false;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId)
    {
        return true;
    }

    @Override
    public int getRenderId()
    {
        return ID;
    }

    protected void crystalSimpleGL11()
    {
        GL11.glTranslatef(0f, -1f, 0f); // TODO: tweak to render correctly in inventory & in hand
        GL11.glScalef(.7f, .7f, .7f);
        Models.modelCrystalSimple.renderAll();
    }

    protected void crystalComplexGL11()
    {

    }
}
