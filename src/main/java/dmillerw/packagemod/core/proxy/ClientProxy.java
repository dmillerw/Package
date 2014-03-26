package dmillerw.packagemod.core.proxy;

import cpw.mods.fml.client.registry.ClientRegistry;
import dmillerw.packagemod.block.tile.TileAddressLabel;
import dmillerw.packagemod.client.render.tile.RenderAddressPanelTile;

/**
 * @author dmillerw
 */
public class ClientProxy extends CommonProxy {

	@Override
	public void renders() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileAddressLabel.class, new RenderAddressPanelTile());
	}

}
