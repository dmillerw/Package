package dmillerw.packagemod.client.render.item;

import dmillerw.packagemod.lib.ModInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;

/**
 * @author dmillerw
 */
public class RenderPackageItem implements IItemRenderer {

	public static final ResourceLocation TEXTURE = new ResourceLocation(ModInfo.RESOURCE_PREFIX + "textures/models/package.png");
	public static final ResourceLocation TEXTURE_TAPED = new ResourceLocation(ModInfo.RESOURCE_PREFIX + "textures/models/package_taped.png");

	private IModelCustom modelPackage;

	public RenderPackageItem() {
		modelPackage = AdvancedModelLoader.loadModel("/assets/package/models/package.obj");
	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		GL11.glPushMatrix();

		GL11.glTranslated(0, -0.5, 0);

		Minecraft.getMinecraft().renderEngine.bindTexture(item.getItemDamage() == 1 ? TEXTURE_TAPED : TEXTURE);
		modelPackage.renderAll();

		GL11.glPopMatrix();
	}

}
