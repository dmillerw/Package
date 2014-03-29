package dmillerw.packagemod.client.render.tile;

import dmillerw.packagemod.block.tile.TileDoormat;
import dmillerw.packagemod.client.model.ModelPixel;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

/**
 * @author dmillerw
 */
public class RenderDoormatTile extends TileEntitySpecialRenderer {

	public static final ModelPixel pixel = new ModelPixel();

	public void renderDoormatAt(TileDoormat tile, double x, double y, double z, float f) {
		GL11.glPushMatrix();

		GL11.glTranslated(x, y, z);

		GL11.glDisable(GL11.GL_TEXTURE_2D);

		// Boxeh box

		GL11.glEnable(GL11.GL_TEXTURE_2D);

		GL11.glPopMatrix();
	}

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d0, double d1, double d2, float f) {
		renderDoormatAt((TileDoormat) tileentity, d0, d1, d2, f);
	}

}
