package dmillerw.packagemod.block.tile;

import net.minecraft.nbt.NBTTagCompound;

/**
 * @author dmillerw
 */
public class TilePackage extends TileCore {

	public static final float ROTATION_MIN = 0F;
	public static final float ROTATION_MAX = 245F;

	//Temp
	public float rotation = ROTATION_MIN;
	private boolean inverse = false;
	private float inc = 5F;

	@Override
	public void updateEntity() {
		rotation += inverse ? -inc : inc;

		if (rotation <= 0F) {
			inverse = false;
		} else if (rotation >= ROTATION_MAX) {
			inverse = true;
		}
	}

	@Override
	public void readNBT(NBTTagCompound nbt) {

	}

	@Override
	public void writeNBT(NBTTagCompound nbt) {

	}

}
