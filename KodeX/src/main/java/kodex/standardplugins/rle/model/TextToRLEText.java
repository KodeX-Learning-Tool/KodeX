package StandardPlugins.RLE.Model;

import java.util.*;

/**
 * 
 */
public class TextToRLEText extends ChainStep {

    /**
     * Default constructor
     */
    public TextToRLEText() {
    }

    /**
     * @param left 
     * @param right
     */
    public abstract void encode(Content left, Content right);

    /**
     * @param right 
     * @param left
     */
    public abstract void decode(Content right, Content left);

}