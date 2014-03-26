package dmillerw.packagemod.core.registry;

import dmillerw.packagemod.block.tile.TileAddressLabel;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dmillerw
 */
public class AddressRegistry {

	public static Map<String, TileAddressLabel> map = new HashMap<String, TileAddressLabel>();

	public static void register(TileAddressLabel tile) {
		map.put(tile.owner, tile);
	}

	public static void unregister(TileAddressLabel tile) {
		map.remove(tile.owner);
	}

	public static TileAddressLabel get(String address) {
		return map.get(address);
	}

}
