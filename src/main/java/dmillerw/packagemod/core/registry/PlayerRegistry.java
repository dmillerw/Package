package dmillerw.packagemod.core.registry;

import dmillerw.packagemod.block.tile.TileDoormat;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dmillerw
 */
public class PlayerRegistry {

	private static Map<String, TileDoormat> map = new HashMap<String, TileDoormat>();

	public static void register(TileDoormat tile) {
		map.put(tile.owner, tile);
	}

	public static void unregister(TileDoormat tile) {
		map.remove(tile.owner);
	}

	public static boolean registered(String player) {
		return map.containsKey(player);
	}

	public static TileDoormat get(String player) {
		return map.get(player);
	}

}
