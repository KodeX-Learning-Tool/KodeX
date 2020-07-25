package kodex.pluginutils.model.content;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This class holds data in string format. A BinaryString consists of only 1's and 0's.
 * Extending AbstractString, it adds validation and exporting capabilities to Java's String.
 */
public class BinaryString extends AbstractString {
	
    /**
     * Creates a new BinaryString
     */
    public BinaryString() {
    	super.data = "";
    }
	
    /**
     * Creates a new BinaryString with the given input.
     */
    public BinaryString(String input) {
    	super.data = input;
    }

	@Override
	public boolean isValid(String input) {
		if (input == null) {
			return false;
		}
		for (int i = 0; i < input.length(); i++) {
			if (input.charAt(i) != '0' && input.charAt(i) != '1') {
				return false;
			}
		}
		return true;
	}

	@Override
	public void export(File file) {
		try {
			
			if (!file.getAbsolutePath().endsWith(".txt"))
				file = new File(file.getAbsoluteFile() + ".txt");
			
			FileWriter writer = new FileWriter(file);

			//header
			writer.write("HEADER\n");
			header.forEach( (key, value) -> { try {
				writer.write(key + " " + value + "\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} });
			
			//content
			writer.write("CONTENT\n");
			writer.write(data);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}