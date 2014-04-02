package dmillerw.packagemod.block.tile;

import dmillerw.packagemod.core.registry.PlayerRegistry;
import net.minecraft.nbt.NBTTagCompound;

/**
 * @author dmillerw
 */
public class TileDoormat extends TileCore {

	public String owner;

	private boolean registered;

	@Override
	public void updateEntity() {
		if (!worldObj.isRemote) {
			if (!registered) {
				PlayerRegistry.register(this);
			}
		}
	}

	@Override
	public void invalidate() {
		super.invalidate();
		remove();
	}

	@Override
	public void onChunkUnload() {
		super.onChunkUnload();
		remove();
	}

	private void remove() {
		if (worldObj == null || !worldObj.isRemote) {
			if (registered) {
				PlayerRegistry.unregister(this);
			}
		}
	}

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
