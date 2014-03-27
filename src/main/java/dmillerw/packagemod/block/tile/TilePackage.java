package dmillerw.packagemod.block.tile;

import dmillerw.packagemod.lib.MathFX;
import net.minecraft.nbt.NBTTagCompound;

/**
 * @author dmillerw
 */
public class TilePackage extends TileCore {

	public static final float ROTATION_MIN = 0F;
	public static final float ROTATION_MAX = 245F;

	public float rotation = ROTATION_MIN;

	public boolean state = false;

	public int tick = 0;
	private final int tickMax = 30;

	@Override
	public void onPoke() {
		state = !state;
	}

	@Override
	public void updateEntity() {
		if (worldObj.isRemote) {
			if (state && tick < tickMax) {
				tick++;
			} else if (!state && tick > 0) {
				tick -= 2;
			}

			float percent = ((float) tick / (float) tickMax);

			if (state) {
				rotation = (MathFX.berp(ROTATION_MIN, ROTATION_MAX, percent));
			} else {
				rotation = ((MathFX.sinerp(ROTATION_MIN, ROTATION_MAX, percent)));
			}
		}
	}

	@Override
	public void readNBT(NBTTagCompound nbt) {
		state = nbt.getBoolean("state");
	}

	@Override
	public void writeNBT(NBTTagCompound nbt) {
		nbt.setBoolean("state", state);
	}

}
