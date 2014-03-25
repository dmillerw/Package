package dmillerw.packagemod.core.tab;

import cpw.mods.fml.common.registry.LanguageRegistry;
import dmillerw.packagemod.lib.ModInfo;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

/**
 * @author dmillerw
 */
public class CreativeTabPackage extends CreativeTabs {

	public static final CreativeTabs TAB = new CreativeTabPackage(ModInfo.NAME);

	public CreativeTabPackage(String label) {
		super(label.toLowerCase().replace(" ", "_"));
		LanguageRegistry.instance().addStringLocalization("itemGroup." + label.toLowerCase().replace(" ", "_"), label);
	}

	@Override
	public ItemStack getIconItemStack() {
		return new ItemStack(Block.stone);
	}

}
