package dmillerw.packagemod.block;

import dmillerw.packagemod.block.tile.TilePackage;
import dmillerw.packagemod.core.tab.CreativeTabPackage;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * @author dmillerw
 */
public class BlockPackage extends BlockContainer {

	public BlockPackage(int id) {
		super(id, Material.wood);

		setCreativeTab(CreativeTabPackage.TAB);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TilePackage();
	}

}
