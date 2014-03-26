package dmillerw.packagemod.block;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import dmillerw.packagemod.block.item.ItemAddressPanel;
import dmillerw.packagemod.block.tile.TileAddressLabel;
import dmillerw.packagemod.block.tile.TileDoormat;
import dmillerw.packagemod.block.tile.TilePackage;
import dmillerw.packagemod.core.IDAllocator;
import net.minecraft.block.Block;
import net.minecraftforge.common.Configuration;

/**
 * @author dmillerw
 */
public class HandlerBlock {

	public static int blockAddressPanelID;
	public static int blockPackageID;
	public static int blockDoormatID;

	public static Block blockAddressPanel;
	public static Block blockPackage;
	public static Block blockDoormat;

	public static void init(Configuration config) {
		assignIDs(new IDAllocator(config, 2200));
		register();
	}

	private static void assignIDs(IDAllocator allocator) {
		blockAddressPanelID = allocator.getBlock("address_panel");
		blockPackageID = allocator.getBlock("package");
		blockDoormatID = allocator.getBlock("doormat");
	}

	private static void register() {
		blockAddressPanel = new BlockAddressPanel(blockAddressPanelID).setUnlocalizedName("address_panel");
		GameRegistry.registerBlock(blockAddressPanel, ItemAddressPanel.class, "address_panel");
		GameRegistry.registerTileEntity(TileAddressLabel.class, "address_panel");
		blockPackage = new BlockPackage(blockPackageID).setUnlocalizedName("package");
		GameRegistry.registerBlock(blockPackage, "package");
		GameRegistry.registerTileEntity(TilePackage.class, "package");
		blockDoormat = new BlockDoormat(blockDoormatID).setUnlocalizedName("doormat");
		GameRegistry.registerBlock(blockDoormat, "doormat");
		GameRegistry.registerTileEntity(TileDoormat.class, "doormat");

		LanguageRegistry.addName(blockAddressPanel, "Address Panel");
		LanguageRegistry.addName(blockPackage, "Package");
	}

}
