package dmillerw.packagemod.block.tile;

import dmillerw.packagemod.core.registry.AddressRegistry;
import dmillerw.packagemod.lib.BlockCoord;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;

/**
 * @author dmillerw
 */
public class TileAddressLabel extends TileCore {

	public String owner;

	public BlockCoord doormatLocation;

	public ForgeDirection orientation = ForgeDirection.UNKNOWN;

	private boolean registered = false;

	@Override
	public void readNBT(NBTTagCompound nbt) {
		if (nbt.hasKey("owner")) {
			owner = nbt.getString("owner");
		} else {
			owner = "";
		}

		if (nbt.hasKey("doormat")) {
			NBTTagCompound tag = nbt.getCompoundTag("doormat");
			doormatLocation = new BlockCoord(tag.getInteger("x"), tag.getInteger("y"), tag.getInteger("z"));
		} else {
			doormatLocation = null;
		}

		orientation = ForgeDirection.getOrientation(nbt.getByte("orientation"));
	}

	@Override
	public void writeNBT(NBTTagCompound nbt) {
		if (owner != null && !(owner.isEmpty())) {
			nbt.setString("owner", owner);
		}

		if (doormatLocation != null) {
			NBTTagCompound tag = new NBTTagCompound();
			tag.setInteger("x", doormatLocation.x);
			tag.setInteger("y", doormatLocation.y);
			tag.setInteger("z", doormatLocation.z);
			nbt.setCompoundTag("doormat", tag);
		}

		nbt.setByte("orientation", (byte)orientation.ordinal());
	}

	@Override
	public void updateEntity() {
		if (!worldObj.isRemote) {
			if (!registered) {
				AddressRegistry.register(this);
			}
		}
	}

	@Override
	public void invalidate() {
		super.invalidate();

		AddressRegistry.unregister(this);
	}

	@Override
	public void onChunkUnload() {
		super.onChunkUnload();

		AddressRegistry.unregister(this);
	}

	public void onBreak() {
		worldObj.setBlockToAir(doormatLocation.x, doormatLocation.y, doormatLocation.z);

		AddressRegistry.unregister(this);
	}

}
