package kodex.pluginutils.model.steps;

import java.util.HashMap;

import kodex.plugininterface.ChainStep;
import kodex.plugininterface.Content;
import kodex.pluginutils.model.content.BinaryString;
import kodex.pluginutils.model.content.RGBByteList;

/**
 * 
 */
public class RGBByteListToBinaryString extends ChainStep {

    /**
     * Default constructor
     */
    public RGBByteListToBinaryString() {
    }

	@Override
	public void encode(Content left, Content right) {
		RGBByteList leftlist = (RGBByteList) left;
		BinaryString rightstring = (BinaryString) right;
		
		String result = "";
		for (int i = 0; i < leftlist.getList().size(); i++) {
			result += leftlist.get(i);
		}
		rightstring.setString(result);
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("length", rightstring.length());
		rightstring.setHeader(map);
	}

	@Override
	public void decode(Content right, Content left) {
		// TODO Auto-generated method stub
		
	}


}