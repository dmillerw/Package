package dmillerw.packagemod.block;

import dmillerw.packagemod.core.tab.CreativeTabPackage;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * @author dmillerw
 */
public class BlockAddressPanel extends BlockContainer {

	public BlockAddressPanel(int id) {
		super(id, Material.wood);

		setCreativeTab(CreativeTabPackage.TAB);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return null;
	}

}
