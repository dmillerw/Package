package dmillerw.packagemod.block.tile;

import dmillerw.packagemod.lib.MathFX;
import net.minecraft.nbt.NBTTagCompound;

/**
 * @author dmillerw
 */
public class TilePackage extends TileCore {

	public static final float ROTATION_MIN = 0F;
	public static final float ROTATION_MAX = 245F;

	public static final int tapeTickMax = 20;

	public static final int topTickMax = 10;
	public static final int sideTickMax = 5;

	public boolean open = false;
	public boolean taped = false;

	public float rotation = ROTATION_MIN;

	public int rotationTick;
	public final int rotationTickMax = 30;

	public int tapeTick = topTickMax + (sideTickMax * 2);

	public void toggleState() {
		open = !open;
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setBoolean("open", open);
		sendData(nbt);
	}

	public void tape() {
		taped = true;
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setBoolean("taped", taped);
		sendData(nbt);
	}

	@Override
	public void updateEntity() {
		if (worldObj.isRemote) {
			// Rotation
			if (open && rotationTick < rotationTickMax) {
				rotationTick++;
			} else if (!open && rotationTick > 0) {
				rotationTick -= 2;
			}

			float percent = ((float) rotationTick / (float) rotationTickMax);

			if (open) {
				rotation = (MathFX.berp(ROTATION_MIN, ROTATION_MAX, percent));
			} else {
				rotation = ((MathFX.sinerp(ROTATION_MIN, ROTATION_MAX, percent)));
			}

			// Taping
			if (taped && tapeTick < tapeTickMax) {
				tapeTick++;
			}
		}
	}

	@Override
	public void readNBT(NBTTagCompound nbt) {
		open = nbt.getBoolean("open");
		taped = nbt.getBoolean("taped");

		// This stuff below should probably be moved somewhere better... :\

		// Rotation ticks are forced here, as any actual open changes are handled through onData
		rotationTick = open ? rotationTickMax : 0;
		rotation = open ? ROTATION_MAX : ROTATION_MIN;

		// Tape ticks are forced here for the same reason
		tapeTick = tapeTickMax;
	}

	@Override
	public void writeNBT(NBTTagCompound nbt) {
		nbt.setBoolean("open", open);
		nbt.setBoolean("taped", taped);
	}

	@Override
	public void onData(NBTTagCompound nbt) {
		if (nbt.hasKey("open")) {
			open = nbt.getBoolean("open");
		}

		if (nbt.hasKey("taped")) {
			taped = nbt.getBoolean("taped");
			tapeTick = 0;
		}
	}

}
