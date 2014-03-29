package dmillerw.packagemod.block;

import dmillerw.packagemod.block.tile.TilePackage;
import dmillerw.packagemod.core.tab.CreativeTabPackage;
import dmillerw.packagemod.item.HandlerItem;
import dmillerw.packagemod.lib.ModInfo;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

/**
 * @author dmillerw
 */
public class BlockPackage extends BlockContainer {

	private Icon icon;

	public BlockPackage(int id) {
		super(id, Material.wood);

		setCreativeTab(CreativeTabPackage.TAB);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float fx, float fy, float fz) {
		if (!world.isRemote) {
			TilePackage tile = (TilePackage) world.getBlockTileEntity(x, y, z);

			if (tile != null) {
				if (!tile.taped) {
					if (!player.isSneaking() && !tile.open && player.getHeldItem() != null && player.getHeldItem().getItem() == HandlerItem.itemTape) {
						tile.tape();
					} else if (player.isSneaking()) {
						tile.toggleState();
					}
				}

				return true;
			}
		}

		return player.isSneaking();
	}

	@Override
	public Icon getIcon(int side, int meta) {
		return icon;
	}

	@Override
	public void registerIcons(IconRegister register) {
		icon = register.registerIcon(ModInfo.RESOURCE_PREFIX + "package_break");
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public int getRenderType() {
		return -1;
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TilePackage();
	}

}
