package atomatic.client.render;

import atomatic.block.BlockAltarRelay;
import atomatic.block.BlockCrystal;
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

            if (block instanceof BlockCrystal)
            {
                if (metadata == 0)
                {
                    FMLClientHandler.instance().getClient().renderEngine.bindTexture(Textures.Models.TEXTURE_CRYSTAL_BASIC);
                    GL11.glTranslatef(0F, -1F, 0F);
                    GL11.glScalef(.7F, .7F, .7F);
                    Models.modelCrystalSimple.renderAll();
                }
                else if (metadata == 1)
                {
                    FMLClientHandler.instance().getClient().renderEngine.bindTexture(Textures.Models.TEXTURE_CRYSTAL_PRIMAL);
                    GL11.glTranslatef(0F, -1F, 0F);
                    GL11.glScalef(.7F, .7F, .7F);
                    Models.modelCrystalSimple.renderAll();
                }
            }
            else if (block instanceof BlockAltarRelay)
            {
                if (metadata == 0)
                {
                    FMLClientHandler.instance().getClient().renderEngine.bindTexture(Textures.Models.TEXTURE_ALTAR_RELAY_BASIC);
                    GL11.glTranslatef(0F, -0.5F, 0F);
                    GL11.glScalef(1.7F, 1.7F, 1.7F);
                    Models.modelAltarRelay.renderAll();
                }
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
}
