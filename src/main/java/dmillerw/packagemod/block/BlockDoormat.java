package dmillerw.packagemod.block;

import dmillerw.packagemod.block.tile.TileDoormat;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * @author dmillerw
 */
public class BlockDoormat extends BlockContainer {

	public BlockDoormat(int id) {
		super(id, Material.cloth);

		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
		setBlockUnbreakable();
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileDoormat();
	}

}
