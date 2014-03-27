package dmillerw.packagemod.block.item;

import dmillerw.packagemod.block.HandlerBlock;
import dmillerw.packagemod.block.tile.TileAddressLabel;
import dmillerw.packagemod.block.tile.TileDoormat;
import dmillerw.packagemod.core.registry.AddressRegistry;
import dmillerw.packagemod.lib.BlockCoord;
import dmillerw.packagemod.lib.ModInfo;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

/**
 * @author dmillerw
 */
public class ItemAddressPanel extends ItemBlock {

	public static ItemStack getFlag(World world, int x, int y, int z) {
		ItemStack stack = new ItemStack(HandlerBlock.blockAddressPanel, 1, 1);
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("d", world.provider.dimensionId);
		nbt.setInteger("x", x);
		nbt.setInteger("y", y);
		nbt.setInteger("z", z);
		stack.setTagCompound(nbt);
		return stack;
	}

	public Icon[] icons;

	public ItemAddressPanel(int id) {
		super(id);
	}

	// Mmm, hacky ways to get intended functionality
	@Override
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata) {
		if (stack.getItemDamage() == 0) {
			if (AddressRegistry.registered(player.getCommandSenderName())) {
				player.sendChatToPlayer(new ChatMessageComponent(ChatMessageComponent.createFromText("You already have an address! Remove the existing plate before placing a new one.")));
				return false;
			} else {
				// Check if target side is solid
				if (!world.isRemote) {
					BlockCoord coord = new BlockCoord(x, y, z);
					coord.offset(ForgeDirection.getOrientation(side).getOpposite());

					if (!world.isBlockSolidOnSide(coord.x, coord.y, coord.z, ForgeDirection.getOrientation(side))) {
						return false;
					}
				}

				// Place block as intended
				super.placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY, hitZ, metadata);

				// Update tile with orientation
				TileAddressLabel tile = (TileAddressLabel) world.getBlockTileEntity(x, y, z);

				if (tile != null) {
					tile.owner = player.getCommandSenderName();
					tile.orientation = ForgeDirection.getOrientation(side);
					tile.update();
				}

				if (!world.isRemote) {
					// Update stack
					player.setCurrentItemOrArmor(0, getFlag(world, x, y, z));
					((EntityPlayerMP)player).updateHeldItem();
				}

				return true;
			}
		} else if (stack.getItemDamage() == 1) {
			if (!world.isRemote) {
				NBTTagCompound nbt = stack.getTagCompound();
				TileAddressLabel tile = (TileAddressLabel) world.getBlockTileEntity(nbt.getInteger("x"), nbt.getInteger("y"), nbt.getInteger("z"));

				if (tile != null) {
					BlockCoord coordinates = new BlockCoord(x, y, z);
					tile.doormatLocation = coordinates;
					tile.update();

					world.setBlock(x, y, z, HandlerBlock.blockDoormatID, 0, 3);

					TileDoormat tile1 = (TileDoormat) world.getBlockTileEntity(x, y, z);

					if (tile1 != null) {
						tile1.owner = tile.owner;
						tile.update();
					}
				}

				player.setCurrentItemOrArmor(0, null);
				((EntityPlayerMP)player).updateHeldItem();
			}

			return false;
		}

		player.setCurrentItemOrArmor(0, null); // Something went wrong, so clear the item
		return false;
	}

	@Override
	public int getSpriteNumber() {
		return 1;
	}

	@Override
	public Icon getIconFromDamage(int damage) {
		return icons[damage];
	}

	@Override
	public void registerIcons(IconRegister register) {
		icons = new Icon[2];

		icons[0] = register.registerIcon(ModInfo.RESOURCE_PREFIX + "address_panel");
		icons[1] = register.registerIcon(ModInfo.RESOURCE_PREFIX + "flag");
	}

}
