package dmillerw.packagemod.client.texture;

/**
 * @author dmillerw
 */
public class TextureSection {

	private float minU;
	private float minV;
	private float maxU;
	private float maxV;

	public TextureSection(int parentWidth, int parentHeight, int x, int y, int w, int h) {
		float f = (float) (0.009999999776482582D / (double) parentWidth);
		float f1 = (float) (0.009999999776482582D / (double) parentHeight);
		this.minU = (float) x / (float) ((double) parentWidth) + f;
		this.maxU = (float) (x + w) / (float) ((double) parentWidth) - f;
		this.minV = (float) y / (float) parentHeight + f1;
		this.maxV = (float) (y + h) / (float) parentHeight - f1;
	}

	public float getMinV() {
		return minV;
	}

	public float getMaxV() {
		return maxV;
	}

	public float getMaxU() {
		return maxU;
	}

	public float getMinU() {
		return minU;
	}

	public float getInterpolatedU(double progress) {
		float f = this.maxU - this.minU;
		return this.minU + f * (float) progress / 1F;
	}

	public float getInterpolatedV(double progress) {
		float f = this.maxV - this.minV;
		return this.minV + f * (float) progress / 1F;
	}

}
