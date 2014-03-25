package dmillerw.packagemod.item;

import dmillerw.packagemod.core.tab.CreativeTabPackage;
import dmillerw.packagemod.lib.ModInfo;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

import java.util.List;

/**
 * @author dmillerw
 */
public class ItemResource extends Item {

	public static final String[] NAMES = new String[] {"cardboard", "package_label"};

	public Icon[] icons;

	public ItemResource(int id) {
		super(id);

		setMaxDamage(0);
		setHasSubtypes(true);
		setCreativeTab(CreativeTabPackage.TAB);
	}

	@Override
	public Icon getIconFromDamage(int damage) {
		return icons[damage];
	}

	@Override
	public void getSubItems(int id, CreativeTabs tab, List list) {
		for (int i=0; i<NAMES.length; i++) {
			list.add(new ItemStack(this, 1, i));
		}
	}

	@Override
	public void registerIcons(IconRegister register) {
		icons = new Icon[NAMES.length];

		for (int i=0; i<NAMES.length; i++) {
			icons[i] = register.registerIcon(ModInfo.RESOURCE_PREFIX + NAMES[i]);
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return super.getUnlocalizedName(stack) + "." + NAMES[stack.getItemDamage()];
	}

}
