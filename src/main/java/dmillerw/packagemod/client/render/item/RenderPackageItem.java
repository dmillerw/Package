package dmillerw.packagemod.client.render.item;

import dmillerw.packagemod.block.tile.TilePackage;
import dmillerw.packagemod.client.render.tile.RenderPackageTile;
import dmillerw.packagemod.lib.ModInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;

import java.util.Random;

/**
 * @author dmillerw
 */
public class RenderPackageItem implements IItemRenderer {

	public static final ResourceLocation TEXTURE = new ResourceLocation(ModInfo.RESOURCE_PREFIX + "textures/models/package.png");
	public static final ResourceLocation TEXTURE_TAPED = new ResourceLocation(ModInfo.RESOURCE_PREFIX + "textures/models/package_taped.png");

	private static final Random rand = new Random();

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

		if (item.hasTagCompound()) {
			// In theory this should work
			int x = item.getTagCompound().getInteger("tile_x");
			int y = item.getTagCompound().getInteger("tile_y");
			int z = item.getTagCompound().getInteger("tile_z");

			float rotationLeft = 0F;
			float rotationRight = 0F;

			boolean open = item.getTagCompound().getBoolean("open");
			boolean taped = item.getTagCompound().getBoolean("taped");

			if (open) {
				rotationLeft = TilePackage.ROTATION_MAX;
				rotationRight = TilePackage.ROTATION_MAX;
			} else {
				// To get the same random flap offset as the world block
				// Pointless, but a nice effect
				rand.setSeed(x + y + z);

				rotationLeft = TilePackage.ROTATION_START_OFFSET * rand.nextFloat();
				rotationRight = TilePackage.ROTATION_START_OFFSET * rand.nextFloat();
			}

			Minecraft.getMinecraft().renderEngine.bindTexture(taped ? TEXTURE_TAPED : TEXTURE);

			modelPackage.renderOnly(RenderPackageTile.BOX);

			// Left flap
			GL11.glPushMatrix();

			GL11.glTranslated(0.5, 1, 0);
			GL11.glRotated(-rotationLeft, 0, 0, 1);
			GL11.glTranslated(-0.5, -1, -0);

			// Fixes flap being slightly offset
			GL11.glTranslated(0, 0, 0.0055);

			modelPackage.renderOnly(RenderPackageTile.FLAP_LEFT);
			GL11.glPopMatrix();

			// Right flap
			GL11.glPushMatrix();

			GL11.glTranslated(-0.5, 1, 0);
			GL11.glRotated(rotationRight, 0, 0, 1);
			GL11.glTranslated(0.5, -1, -0);

			modelPackage.renderOnly(RenderPackageTile.FLAP_RIGHT);
			GL11.glPopMatrix();
		} else {
			Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE);
			modelPackage.renderAllExcept(RenderPackageTile.FLAP_LEFT, RenderPackageTile.FLAP_RIGHT);
		}

		GL11.glPopMatrix();
	}

}
