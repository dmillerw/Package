package dmillerw.packagemod.item;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import dmillerw.packagemod.core.IDAllocator;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;

/**
 * @author dmillerw
 */
public class HandlerItem {

	public static int itemResourceID;
	public static int itemTapeID;
	public static int itemDirectoryID;

	public static Item itemResource;
	public static Item itemTape;
	public static Item itemDirectory;

	public static void init(Configuration config) {
		assignIDs(new IDAllocator(config, 22000));
		register();
	}

	private static void assignIDs(IDAllocator allocator) {
		itemResourceID = allocator.getItem("resource");
		itemTapeID = allocator.getItem("tape");
		itemDirectoryID = allocator.getItem("directory");
	}

	private static void register() {
		itemResource = new ItemResource(itemResourceID).setUnlocalizedName("resource");
		GameRegistry.registerItem(itemResource, "resource");
		itemTape = new ItemTape(itemTapeID).setUnlocalizedName("tape");
		GameRegistry.registerItem(itemTape, "tape");
		itemDirectory = new ItemDirectory(itemDirectoryID).setUnlocalizedName("directory");
		GameRegistry.registerItem(itemDirectory, "directory");

		LanguageRegistry.addName(new ItemStack(itemResource, 0, 0), "Cardboard");
		LanguageRegistry.addName(new ItemStack(itemResource, 0, 1), "Package Label");
		LanguageRegistry.addName(itemTape, "Tape");
		LanguageRegistry.addName(itemDirectory, "Directory");
	}

}
