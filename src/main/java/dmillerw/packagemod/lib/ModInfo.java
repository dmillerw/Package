package dmillerw.packagemod.lib;

/**
 * @author dmillerw
 */
public class ModInfo {

	public static final String ID = "Package";
	public static final String NAME = "Package";

	public static final String MAJOR_VERSION = "@MAJOR@";
	public static final String MINOR_VERSION = "@MINOR@";
	public static final String REVISION_VERSION = "@REVIS@";
	public static final String BUILD = "@BUILD@";

	public static final String VERSION = MAJOR_VERSION + "." + MINOR_VERSION + "." + REVISION_VERSION + "." + BUILD;

	public static final String BASE_PACKAGE = "dmillerw.packagemod";

	public static final String COMMON_PROXY = BASE_PACKAGE + ".core.proxy.CommonProxy";
	public static final String CLIENT_PROXY = BASE_PACKAGE + ".core.proxy.ClientProxy";

	public static final String RESOURCE_PREFIX = ID.toLowerCase() + ":";

}
