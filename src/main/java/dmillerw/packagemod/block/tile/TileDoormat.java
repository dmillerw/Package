package dmillerw.packagemod.block.tile;

import net.minecraft.nbt.NBTTagCompound;

/**
 * @author dmillerw
 */
public class TileDoormat extends TileCore {

	public String owner;

	@Override
	public void readNBT(NBTTagCompound nbt) {
		if (nbt.hasKey("owner")) {
			owner = nbt.getString("owner");
		} else {
			owner = "";
		}
	}

	@Override
	public void writeNBT(NBTTagCompound nbt) {
		if (owner != null && !(owner.isEmpty())) {
			nbt.setString("owner", owner);
		}
	}

}
