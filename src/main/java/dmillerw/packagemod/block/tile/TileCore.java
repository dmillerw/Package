package dmillerw.packagemod.block.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;

/**
 * @author dmillerw
 */
public abstract class TileCore extends TileEntity {

	public abstract void readNBT(NBTTagCompound nbt);

	public abstract void writeNBT(NBTTagCompound nbt);

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		readNBT(nbt);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		writeNBT(nbt);
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbt = new NBTTagCompound();
		writeToNBT(nbt);
		return new Packet132TileEntityData(xCoord, yCoord, zCoord, 0, nbt);
	}

	@Override
	public void onDataPacket(INetworkManager manager, Packet132TileEntityData packet) {
		readFromNBT(packet.data);
	}

	public void update() {
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

}
