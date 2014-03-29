package dmillerw.packagemod.client.texture;

/**
 * @author dmillerw
 */
public class TextureHelper {

	public static TextureSection getSection(int parentWidth, int parentHeight, int x, int y, int w, int h) {
		return new TextureSection(parentWidth, parentHeight, x, y, w, h);
	}

}
