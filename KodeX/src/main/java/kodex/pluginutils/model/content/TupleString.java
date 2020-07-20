package kodex.pluginutils.model.content;

import java.io.File;

import kodex.model.Tuple;
import kodex.plugininterface.Content;

public class TupleString extends Content<String> {
    
    private Tuple<String, Integer>[] tuples;

    @Override
    protected File toFile() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isValid(String input) {
        
        String[] inputStrings = input.split(" ");
        
        tuples = (Tuple<String, Integer>[]) new Tuple[inputStrings.length];
        
        for (int i = 0; i < inputStrings.length; i++) {
            
            String[] tupleParts = inputStrings[i].split(":");
            
            if (tupleParts.length != 2) {
                return false;
            }
            
            if (!isValidLetter(tupleParts[0])) {
                return false;
            }
            
            if (!isValidNumber(tupleParts[1])) {
                return false;
            }
            
            tuples[i] = new Tuple<>(tupleParts[0], Integer.parseInt(tupleParts[1]));
        }
        
        return true;
    }
    
    /**
     * Checks if the given string is a valid integer.
     * 
     * @param input The input string.
     * @return True if the string is a valid integer.
     */
    private boolean isValidNumber(String input) {
        return input.matches("^[1-9]\\d*$");
    }
    
    /**
     * Checks if the given string is a valid letter.
     * 
     * @param input The input string.
     * @return True if the string is a valid letter.
     */
    private boolean isValidLetter(String input) {
        
        char[] charArray = input.toCharArray();
        
        if (charArray.length != 1) {
            return false;
        }
        
        char firstElement = charArray[0];
        
        return Character. isLetter(firstElement);
    }

    /**
     * @return the tuples
     */
    public Tuple<String, Integer>[] getTuples() {
        return tuples;
    }

    /**
     * @param tuples the tuples to set
     */
    public void setTuples(Tuple<String, Integer>[] tuples) {
        this.tuples = tuples;
    }

}
