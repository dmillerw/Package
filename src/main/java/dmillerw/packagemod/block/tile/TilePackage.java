package dmillerw.packagemod.block.tile;

import cpw.mods.fml.common.FMLCommonHandler;
import dmillerw.packagemod.lib.MathFX;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Random;

/**
 * @author dmillerw
 */
public class TilePackage extends TileCore {

	public static final float ROTATION_START_OFFSET = 15F;
	public static final float ROTATION_MAX = 245F;

	public static final int topTickMax = 10;
	public static final int sideTickMax = 5;

	public static final int tapeTickMax = topTickMax + (sideTickMax * 2);

	public static final int flattenTickMax = 5;

	public boolean open = false;
	public boolean taped = false;

	public float rotationLeftMin = -1F;
	public float rotationRightMin = -1F;
	public float rotationLeft;
	public float rotationRight;

	public int rotationTick;
	public final int rotationTickMax = 50;

	public int tapeTick = tapeTickMax;

	public int flattenTick = flattenTickMax;

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
			// Ticks
			if (taped) {
				if (flattenTick < flattenTickMax) {
					flattenTick++;
				} else {
					if (tapeTick < tapeTickMax) {
						tapeTick++;
					}
				}
			}

			if (open && rotationTick < rotationTickMax) {
				rotationTick++;
			} else if (!open && rotationTick > 0) {
				rotationTick -= 2;
			}

			// Rotation
			if (taped) {
				float percent = (1F - ((float) flattenTick / (float) flattenTickMax));

				rotationLeft = ((MathFX.sinerp(0, rotationLeftMin, percent)));
				rotationRight = ((MathFX.sinerp(0, rotationRightMin, percent)));
			} else if (rotationTick > 0) {
				float percent = ((float) rotationTick / (float) rotationTickMax);

				if (open) {
					if (rotationTick <= 2) {
						rotationLeft = rotationLeftMin;
						rotationRight = rotationRightMin;
					} else {
						rotationLeft = (MathFX.berp(rotationLeftMin, ROTATION_MAX, percent));
						rotationRight = (MathFX.berp(rotationRightMin, ROTATION_MAX, percent));
					}
				} else {
					if (rotationTick <= 2) {
						rotationLeft = rotationLeftMin;
						rotationRight = rotationRightMin;
					} else {
						rotationLeft = ((MathFX.sinerp(rotationLeftMin, ROTATION_MAX, percent)));
						rotationRight = ((MathFX.sinerp(rotationRightMin, ROTATION_MAX, percent)));
					}
				}
			}

		}
	}

	@Override
	public void readNBT(NBTTagCompound nbt) {
		open = nbt.getBoolean("open");
		taped = nbt.getBoolean("taped");

		// This stuff below should probably be moved somewhere better... :\
		// Everything inside this block is run when the tile is first initialized on the client
		// Makes it a good place to set inital values that require some amount of calculation
		if (FMLCommonHandler.instance().getEffectiveSide().isClient()) {
			// Handles the randomization of the flaps
			// Obviously not entirely random because of the seed, but that's just for consistancy
			Random rand = new Random(xCoord + yCoord + zCoord);
			if (rotationLeftMin == -1F) {
				rotationLeftMin = ROTATION_START_OFFSET * rand.nextFloat();
			}
			if (rotationRightMin == -1F) {
				rotationRightMin = ROTATION_START_OFFSET * rand.nextFloat();
			}

			// Rotation ticks are forced here, as any actual open changes are handled through onData
			rotationTick = open ? rotationTickMax : 0;
			rotationLeft = open ? ROTATION_MAX : rotationLeftMin;
			rotationRight = open ? ROTATION_MAX : rotationRightMin;
		}

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
			flattenTick = 0;
			tapeTick = 0;
		}
	}

}
