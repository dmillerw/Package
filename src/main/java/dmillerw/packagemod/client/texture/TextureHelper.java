package dmillerw.packagemod.client.texture;

/**
 * @author dmillerw
 */
public class TextureHelper {

	private int width;
	private int height;

	public TextureHelper(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public TextureSection getSection(int x, int y, int w, int h) {
		return new TextureSection(width, height, x, y, w, h);
	}

}
