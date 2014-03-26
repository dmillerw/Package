package dmillerw.packagemod.block;

import dmillerw.packagemod.block.tile.TileAddressLabel;
import dmillerw.packagemod.core.registry.AddressRegistry;
import dmillerw.packagemod.core.tab.CreativeTabPackage;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Map;

/**
 * @author dmillerw
 */
public class BlockAddressPanel extends BlockContainer {

	public BlockAddressPanel(int id) {
		super(id, Material.wood);

		setCreativeTab(CreativeTabPackage.TAB);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float fx, float fy, float fz) {
		if (!world.isRemote) {
			for (Map.Entry<String, TileAddressLabel> entry : AddressRegistry.map.entrySet()) {
				System.out.println(entry.getKey());
			}
		}

		return true;
	}

	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		TileAddressLabel tile = (TileAddressLabel) world.getBlockTileEntity(x, y, z);

		if (tile != null) {
			int l = tile.orientation.ordinal();
			float f = 0.28125F;
			float f1 = 0.78125F;
			float f2 = 0.0F;
			float f3 = 1.0F;
			float f4 = 0.125F;
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);

			if (l == 2) {
				this.setBlockBounds(f2, f, 1.0F - f4, f3, f1, 1.0F);
			}

			if (l == 3) {
				this.setBlockBounds(f2, f, 0.0F, f3, f1, f4);
			}

			if (l == 4) {
				this.setBlockBounds(1.0F - f4, f, f2, 1.0F, f1, f3);
			}

			if (l == 5) {
				this.setBlockBounds(0.0F, f, f2, f4, f1, f3);
			}
		}
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, int id, int meta) {
		if (!world.isRemote) {
			TileAddressLabel tile = (TileAddressLabel) world.getBlockTileEntity(x, y, z);

			if (tile != null) {
				tile.onBreak();
			}
		}
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
		return new TileAddressLabel();
	}

}
