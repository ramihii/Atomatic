package atomatic.reference;

import atomatic.util.ResourceLocationHelper;

import net.minecraft.util.ResourceLocation;

public class Textures
{
    public static final String RESOURCE_PREFIX = Reference.MOD_ID.toLowerCase() + ":";

    public static final class Models
    {
        // Model textures
        public static final String MODEL_TEXTURE_LOCATION = "textures/models/";
        public static final String MODEL_LOCATION = "models/";

        public static final ResourceLocation TEXTURE_CRYSTAL_PRIMAL = new ResourceLocation("textures/blocks/lapis_block.png");

        public static final ResourceLocation MODEL_CRYSTAL_SIMPLE = ResourceLocationHelper.getResourceLocation(MODEL_LOCATION + "crystalSimple.obj");
        public static final ResourceLocation MODEL_CRYSTAL_COMPLEX = ResourceLocationHelper.getResourceLocation(MODEL_LOCATION + "crystalComplex.obj");
    }
}
