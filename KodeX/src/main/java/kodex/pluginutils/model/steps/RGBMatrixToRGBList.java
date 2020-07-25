package kodex.pluginutils.model.steps;

import java.util.HashMap;

import javafx.scene.paint.Color;
import kodex.plugininterface.ChainStep;
import kodex.plugininterface.Content;
import kodex.pluginutils.model.content.RGBList;
import kodex.pluginutils.model.content.RGBMatrix;

/**
 * 
 */
public class RGBMatrixToRGBList extends ChainStep {

    /**
     * Default constructor
     */
    public RGBMatrixToRGBList() {
    }

	@Override
	public void encode(Content left, Content right) {
		RGBMatrix leftmtx = (RGBMatrix) left;
		RGBList rightlist = (RGBList) right;

		for (int y = 0; y < leftmtx.getHeight(); y++) {
			for (int x = 0; x < leftmtx.getWidth(); x++) {	
				rightlist.add(leftmtx.get(x, y)); 
			}
		}
		
		rightlist.setHeader(leftmtx.getHeader());
	}

	@Override
	public void decode(Content right, Content left) {
		RGBMatrix leftmtx = (RGBMatrix) left;
		RGBList rightlist = (RGBList) right;
		
		int width = (int) rightlist.getHeader().get("width");
		int height = (int) rightlist.getHeader().get("height");
		leftmtx.setSize(width, height);
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				Color color = rightlist.get(y * width + x);
				leftmtx.set(x, y, color);
			}
		}
		
		leftmtx.setHeader(rightlist.getHeader());
	}

}