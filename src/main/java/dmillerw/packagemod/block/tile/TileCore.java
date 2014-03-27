package dmillerw.packagemod.block.tile;

import cpw.mods.fml.common.network.PacketDispatcher;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;

/**
 * @author dmillerw
 */
public abstract class TileCore extends TileEntity {

	private static final int DATA_CLIENT = 0;
	private static final int DATA_POKE = 1;

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
		return new Packet132TileEntityData(xCoord, yCoord, zCoord, DATA_CLIENT, nbt);
	}

	@Override
	public void onDataPacket(INetworkManager manager, Packet132TileEntityData packet) {
		switch (packet.actionType) {
			case DATA_CLIENT:
				readFromNBT(packet.data);
				break;
			case DATA_POKE:
				onPoke();
				break;
		}
	}

	public void update() {
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	public void poke() {
		PacketDispatcher.sendPacketToAllAround(xCoord, yCoord, zCoord, 64, worldObj.provider.dimensionId, new Packet132TileEntityData(xCoord, yCoord, zCoord, DATA_POKE, new NBTTagCompound()));
	}

	public void onPoke() {

	}

}
