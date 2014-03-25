package dmillerw.packagemod.item;

import dmillerw.packagemod.core.tab.CreativeTabPackage;
import dmillerw.packagemod.lib.ModInfo;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.Icon;

/**
 * @author dmillerw
 */
public class ItemDirectory extends Item {

	public Icon icon;

	public ItemDirectory(int id) {
		super(id);

		setMaxStackSize(1);
		setCreativeTab(CreativeTabPackage.TAB);
	}

	@Override
	public Icon getIconFromDamage(int damage) {
		return icon;
	}

	@Override
	public void registerIcons(IconRegister register) {
		icon = register.registerIcon(ModInfo.RESOURCE_PREFIX + "directory");
	}

}
