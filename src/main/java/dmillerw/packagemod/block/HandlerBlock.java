package dmillerw.packagemod.block;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import dmillerw.packagemod.block.tile.TileDoormat;
import dmillerw.packagemod.block.tile.TilePackage;
import dmillerw.packagemod.core.IDAllocator;
import net.minecraft.block.Block;
import net.minecraftforge.common.Configuration;

/**
 * @author dmillerw
 */
public class HandlerBlock {

	public static int blockPackageID;
	public static int blockDoormatID;

	public static Block blockPackage;
	public static Block blockDoormat;

	public static void init(Configuration config) {
		assignIDs(new IDAllocator(config, 2200));
		register();
	}

	private static void assignIDs(IDAllocator allocator) {
		blockPackageID = allocator.getBlock("cardboard_box");
		blockDoormatID = allocator.getBlock("doormat");
	}

	private static void register() {
		blockPackage = new BlockPackage(blockPackageID).setUnlocalizedName("cardboard_box");
		GameRegistry.registerBlock(blockPackage, "cardboard_box");
		GameRegistry.registerTileEntity(TilePackage.class, "cardboard_box");
		blockDoormat = new BlockDoormat(blockDoormatID).setUnlocalizedName("doormat");
		GameRegistry.registerBlock(blockDoormat, "doormat");
		GameRegistry.registerTileEntity(TileDoormat.class, "doormat");

		LanguageRegistry.addName(blockPackage, "Cardboard Box");
	}

}
