package kodex.pluginutils.model.steps;

import java.util.HashMap;

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
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("size", rightlist.size());
		rightlist.setHeader(map);
	}

	@Override
	public void decode(Content right, Content left) {
		// TODO Auto-generated method stub
		
	}

}