package dmillerw.packagemod.block;

import dmillerw.packagemod.core.IDAllocator;
import net.minecraft.block.Block;
import net.minecraftforge.common.Configuration;

/**
 * @author dmillerw
 */
public class HandlerBlock {

	public static int blockAddressPanelID;
	public static int blockPackageID;

	public static Block blockAddressPanel;
	public static Block blockPackage;

	public static void init(Configuration config) {
		assignIDs(new IDAllocator(config, 2200));
		register();
	}

	private static void assignIDs(IDAllocator allocator) {
		blockAddressPanelID = allocator.getBlock("address_panel");
		blockPackageID = allocator.getBlock("package");
	}

	private static void register() {

	}

}
