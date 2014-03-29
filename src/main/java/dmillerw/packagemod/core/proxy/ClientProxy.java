package dmillerw.packagemod.core.proxy;

import cpw.mods.fml.client.registry.ClientRegistry;
import dmillerw.packagemod.block.HandlerBlock;
import dmillerw.packagemod.block.tile.TileDoormat;
import dmillerw.packagemod.block.tile.TilePackage;
import dmillerw.packagemod.client.render.item.RenderPackageItem;
import dmillerw.packagemod.client.render.tile.RenderDoormatTile;
import dmillerw.packagemod.client.render.tile.RenderPackageTile;
import net.minecraftforge.client.MinecraftForgeClient;

/**
 * @author dmillerw
 */
public class ClientProxy extends CommonProxy {

	@Override
	public void registerRenders() {
		MinecraftForgeClient.registerItemRenderer(HandlerBlock.blockPackageID, new RenderPackageItem());

		ClientRegistry.bindTileEntitySpecialRenderer(TilePackage.class, new RenderPackageTile());
		ClientRegistry.bindTileEntitySpecialRenderer(TileDoormat.class, new RenderDoormatTile());
	}

}
