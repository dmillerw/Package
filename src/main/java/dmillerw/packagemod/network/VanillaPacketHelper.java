package dmillerw.packagemod.network;

import net.minecraft.network.packet.Packet;
import net.minecraft.server.management.PlayerInstance;
import net.minecraft.server.management.PlayerManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;

/**
 * @author dmillerw
 */
public class VanillaPacketHelper {

	public static void sendToAllWatchingTile(TileEntity tile, Packet packet) {
		sendToAllWatchingChunk(tile.worldObj.getChunkFromBlockCoords(tile.xCoord, tile.zCoord), packet);
	}

	/**
	 * Sends the specified packet to all players either in specified chunk, or at least have that chunk loaded
	 */
	public static void sendToAllWatchingChunk(Chunk chunk, Packet packet) {
		World world = chunk.worldObj;

		if (world instanceof WorldServer) {
			PlayerManager playerManager = ((WorldServer) world).getPlayerManager();
			PlayerInstance playerInstance = playerManager.getOrCreateChunkWatcher(chunk.xPosition, chunk.zPosition, false);

			if (playerInstance != null) {
				playerInstance.sendToAllPlayersWatchingChunk(packet);
			}
		}
	}

}
