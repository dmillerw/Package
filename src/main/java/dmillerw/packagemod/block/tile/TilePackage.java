package dmillerw.packagemod.block.tile;

import cpw.mods.fml.common.FMLCommonHandler;
import dmillerw.packagemod.lib.MathFX;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.Random;

/**
 * @author dmillerw
 */
public class TilePackage extends TileCore implements IInventory {

	public static final int INVENTORY_SIZE = 8;

	/* START ANIMATION VARS */
	public static final float ROTATION_START_OFFSET = 15F;
	public static final float ROTATION_MAX = 245F;
	public static final float ROTATION_MAX_BLOCKED = 150F;

	public static final int topTickMax = 10;
	public static final int sideTickMax = 5;

	public static final int tapeTickMax = topTickMax + (sideTickMax * 2);

	public static final int flattenTickMax = 5;

	public boolean blockedLeft = false;
	public boolean blockedRight = false;
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
	/* END ANIMATION VARS */

	private ItemStack[] inventory = new ItemStack[INVENTORY_SIZE];

	public void updateInventory() {
		// Just a pass to markBlockForUpdate
		update();
	}

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
						rotationLeft = (MathFX.berp(rotationLeftMin, blockedLeft ? ROTATION_MAX_BLOCKED : ROTATION_MAX, percent));
						rotationRight = (MathFX.berp(rotationRightMin, blockedRight ? ROTATION_MAX_BLOCKED : ROTATION_MAX, percent));
					}
				} else {
					if (rotationTick <= 2) {
						rotationLeft = rotationLeftMin;
						rotationRight = rotationRightMin;
					} else {
						rotationLeft = ((MathFX.sinerp(rotationLeftMin, blockedLeft ? ROTATION_MAX_BLOCKED : ROTATION_MAX, percent)));
						rotationRight = ((MathFX.sinerp(rotationRightMin, blockedRight ? ROTATION_MAX_BLOCKED : ROTATION_MAX, percent)));
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
		// Makes it a good place to set initial values that require some amount of calculation
		if (FMLCommonHandler.instance().getEffectiveSide().isClient()) {
			// Handles the randomization of the flaps
			// Obviously not entirely random because of the seed, but that's just for consistency
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

		inventory = new ItemStack[INVENTORY_SIZE];
		NBTTagList tagList = nbt.getTagList("inventory");

		// Ensures the current inventory is all based around the current defined size
		for (int i=0; i<inventory.length; i++) {
			try {
				NBTTagCompound item = (NBTTagCompound) tagList.tagAt(i);

				if (item != null) {
					inventory[i] = ItemStack.loadItemStackFromNBT(item);
				}
			} catch (IndexOutOfBoundsException ex) {
				// Just noise, discard
				// Here in case the current size is larger than it was at the last NBT save
			}
		}
	}

	@Override
	public void writeNBT(NBTTagCompound nbt) {
		nbt.setBoolean("open", open);
		nbt.setBoolean("taped", taped);

		NBTTagList tagList = new NBTTagList();
		for (int i=0; i<inventory.length; i++) {
			ItemStack item = inventory[i];

			if (item != null) {
				NBTTagCompound tag = new NBTTagCompound();
				item.writeToNBT(tag);
				tagList.appendTag(tag);
			}
		}
		nbt.setTag("inventory", tagList);
	}

	@Override
	public void onData(NBTTagCompound nbt) {
		if (nbt.hasKey("open")) {
			open = nbt.getBoolean("open");

			blockedLeft = !worldObj.isAirBlock(xCoord + 1, yCoord, zCoord);
			blockedRight = !worldObj.isAirBlock(xCoord - 1, yCoord, zCoord);
		}

		if (nbt.hasKey("taped")) {
			taped = nbt.getBoolean("taped");
			flattenTick = 0;
			tapeTick = 0;
		}
	}

	/* IINVENTORY */
	@Override
	public int getSizeInventory() {
		return INVENTORY_SIZE;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return inventory[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		if (this.inventory[slot] != null) {
			ItemStack itemstack;

			if (this.inventory[slot].stackSize <= amount) {
				itemstack = this.inventory[slot];
				this.inventory[slot] = null;
				return itemstack;
			} else {
				itemstack = this.inventory[slot].splitStack(amount);

				if (this.inventory[slot].stackSize == 0) {
					this.inventory[slot] = null;
				}

				return itemstack;
			}
		} else {
			return null;
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		return null; // Never used (obviously)
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		inventory[slot] = stack;
	}

	@Override
	public String getInvName() {
		return "container.package";
	}

	@Override
	public boolean isInvNameLocalized() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 1; // May change?
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return true;
	}

	@Override
	public void openChest() {
		// Chests use these to sync animation, but we don't
	}

	@Override
	public void closeChest() {
		// Chests use these to sync animation, but we don't
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return true;
	}
}
