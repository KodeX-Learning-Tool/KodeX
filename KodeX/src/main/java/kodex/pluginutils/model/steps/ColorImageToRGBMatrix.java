package kodex.pluginutils.model.steps;

import java.util.HashMap;

import kodex.plugininterface.ChainStep;
import kodex.plugininterface.Content;
import kodex.pluginutils.model.content.ColorImage;
import kodex.pluginutils.model.content.RGBMatrix;

/**
 * 
 */
public class ColorImageToRGBMatrix extends ChainStep {

    /**
     * Default constructor
     */
    public ColorImageToRGBMatrix() {
    }



	@Override
	public void encode(Content left, Content right) {
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
	public void decode(Content right, Content left) {
		// TODO Auto-generated method stub
		
	}

}