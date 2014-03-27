package dmillerw.packagemod.lib;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

/**
 * @author dmillerw
 */
public class BlockCoord {

	public int x;
	public int y;
	public int z;

	public BlockCoord(TileEntity tile) {
		this(tile.xCoord, tile.yCoord, tile.zCoord);
	}

	public BlockCoord(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public BlockCoord offset(ForgeDirection direction) {
		return offset(direction.offsetX, direction.offsetY, direction.offsetZ);
	}

	public BlockCoord offset(int offset) {
		return offset(offset, offset, offset);
	}

	public BlockCoord offset(int x, int y, int z) {
		this.x += x;
		this.y += y;
		this.z += z;

		return this;
	}

	public BlockCoord copy() {
		return new BlockCoord(x, y, z);
	}

	@Override
	public String toString() {
		return "BlockCoord [" + x + ", " + y + ", " + z + "]";
	}

}
