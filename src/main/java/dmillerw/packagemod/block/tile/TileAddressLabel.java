package dmillerw.packagemod.block.tile;

import dmillerw.packagemod.core.registry.AddressRegistry;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.common.ForgeDirection;

/**
 * @author dmillerw
 */
public class TileAddressLabel extends TileCore {

	public String owner;

	public ChunkCoordinates doormatLocation;

	public ForgeDirection orientation = ForgeDirection.UNKNOWN;

	@Override
	public void readNBT(NBTTagCompound nbt) {
		if (nbt.hasKey("owner")) {
			owner = nbt.getString("owner");
		} else {
			owner = "";
		}

		if (nbt.hasKey("doormat")) {
			NBTTagCompound tag = nbt.getCompoundTag("doormat");
			doormatLocation = new ChunkCoordinates(tag.getInteger("x"), tag.getInteger("y"), tag.getInteger("z"));
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
			tag.setInteger("x", doormatLocation.posX);
			tag.setInteger("y", doormatLocation.posY);
			tag.setInteger("z", doormatLocation.posZ);
			nbt.setCompoundTag("doormat", tag);
		}

		nbt.setByte("orientation", (byte)orientation.ordinal());
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
		worldObj.setBlockToAir(doormatLocation.posX, doormatLocation.posY, doormatLocation.posZ);

		// Unregister
		AddressRegistry.unregister(this);
	}

}
