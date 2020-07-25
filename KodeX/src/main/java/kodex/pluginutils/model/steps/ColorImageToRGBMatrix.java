package kodex.pluginutils.model.steps;

import java.util.HashMap;

import kodex.plugininterface.ChainStep;
import kodex.plugininterface.Content;
import kodex.pluginutils.model.content.ColorImage;
import kodex.pluginutils.model.content.RGBMatrix;

/**
 * This class represents the bidirectional step between ColorImage and RGBMatrix.
 * It contains the functionality to decode and encode the content between these explicitly defined levels.
 */
public class ColorImageToRGBMatrix extends ChainStep {

    /**
     * Default constructor
     */
    public ColorImageToRGBMatrix() {
    }



	@Override
	public void encode(Content<?> left, Content<?> right) {
		ColorImage leftimg = (ColorImage) left;
		RGBMatrix rightmtx = (RGBMatrix) right;
		
		rightmtx.setSize(leftimg.getWidth(), leftimg.getHeight());
		for (int y = 0; y < leftimg.getHeight(); y++) {
			for (int x = 0; x < leftimg.getWidth(); x++) {
				rightmtx.set(x, y, leftimg.getColor(x, y));
			}	
		}
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("width", Integer.valueOf(leftimg.getWidth()));
		map.put("height", Integer.valueOf(leftimg.getHeight()));
		rightmtx.setHeader(map);
	}

	@Override
	public void decode(Content<?> right, Content<?> left) {
		ColorImage leftimg = (ColorImage) left;
		RGBMatrix rightmtx = (RGBMatrix) right;
		
		int width = (int) rightmtx.getHeader().get("width");
		int height = (int) rightmtx.getHeader().get("height");
		leftimg.setSize(width, height);
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				leftimg.setColor(x, y, rightmtx.get(x, y));
			}
		}
		
		leftimg.setHeader(rightmtx.getHeader());
	}

}