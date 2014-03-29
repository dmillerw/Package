package dmillerw.packagemod;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import dmillerw.packagemod.block.HandlerBlock;
import dmillerw.packagemod.core.proxy.CommonProxy;
import dmillerw.packagemod.item.HandlerItem;
import dmillerw.packagemod.lib.ModInfo;
import net.minecraftforge.common.Configuration;

/**
 * @author dmillerw
 */
@Mod(modid = ModInfo.ID, name = ModInfo.NAME, version = ModInfo.VERSION)
public class Package {

	@Mod.Instance(ModInfo.ID)
	public static Package instance;

	@SidedProxy(serverSide = ModInfo.COMMON_PROXY, clientSide = ModInfo.CLIENT_PROXY)
	public static CommonProxy proxy;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();

		HandlerItem.init(config);
		HandlerBlock.init(config);

		if (config.hasChanged()) {
			config.save();
		}

		proxy.registerRenders();
	}

}
