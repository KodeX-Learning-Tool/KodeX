package kodex.plugininterface;

import java.util.*;

/**
 * 
 */
public abstract class ChainStep {

    /**
     * Default constructor
     */
    public ChainStep() {
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