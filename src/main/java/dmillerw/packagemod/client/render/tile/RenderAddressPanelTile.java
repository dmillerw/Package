package dmillerw.packagemod.client.render.tile;

import dmillerw.packagemod.block.tile.TileAddressLabel;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.model.ModelSign;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * @author dmillerw
 */
public class RenderAddressPanelTile extends TileEntitySpecialRenderer {

	private static final ResourceLocation TEXTURE = new ResourceLocation("textures/entity/sign.png");

	private final ModelSign modelSign = new ModelSign();

	public void renderPanelAt(TileAddressLabel tile, double x, double y, double z, float partial) {
		GL11.glPushMatrix();

		float f1 = 0.6666667F;
		float f2;

		int i = tile.orientation.ordinal();
		f2 = 0.0F;

		if (i == 2) {
			f2 = 180.0F;
		}

		if (i == 4) {
			f2 = 90.0F;
		}

		if (i == 5) {
			f2 = -90.0F;
		}

		GL11.glTranslatef((float) x + 0.5F, (float) y + 0.75F * f1, (float) z + 0.5F);
		GL11.glRotatef(-f2, 0.0F, 1.0F, 0.0F);
		GL11.glTranslatef(0.0F, -0.3125F, -0.4375F);
		this.modelSign.signStick.showModel = false;

		this.bindTexture(TEXTURE);
		GL11.glPushMatrix();
		GL11.glScalef(f1, -f1, -f1);
		this.modelSign.renderSign();
		GL11.glPopMatrix();
		FontRenderer fontrenderer = this.getFontRenderer();
		f2 = 0.016666668F * f1;
		GL11.glTranslatef(0.0F, 0.5F, 0.07F * f1);
		GL11.glScalef(f2, -f2, f2);
		GL11.glNormal3f(0.0F, 0.0F, -1.0F * f2);
		GL11.glDepthMask(false);
		byte b0 = 0;

		String s = tile.owner;

		fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, fontrenderer.FONT_HEIGHT, b0);

		GL11.glDepthMask(true);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glPopMatrix();
	}

	public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8) {
		this.renderPanelAt((TileAddressLabel) par1TileEntity, par2, par4, par6, par8);
	}

}
