package dmillerw.packagemod.block.item;

import dmillerw.packagemod.block.tile.TileDoormat;
import dmillerw.packagemod.core.registry.PlayerRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * @author dmillerw
 */
public class ItemDoormat extends ItemBlock {

	public ItemDoormat(int id) {
		super(id);
	}

	@Override
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata) {
		if (!world.isRemote) {
			if (PlayerRegistry.registered(player.getCommandSenderName())) {
				return false;
			}
		}

		boolean succeed = super.placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY, hitZ, metadata);

		if (succeed) {
			TileDoormat tile = (TileDoormat) world.getBlockTileEntity(x, y, z);

			if (tile != null) {
				tile.owner = player.getCommandSenderName();
			} else {
				succeed = false;
			}
		}

		return succeed;
	}
}
