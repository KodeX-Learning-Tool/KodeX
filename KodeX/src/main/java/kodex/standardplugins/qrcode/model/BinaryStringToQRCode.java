package StandardPlugins.QR-Code.Model;

import java.util.*;

/**
 * 
 */
public class BinaryStringToQRCode extends ChainStep {

    /**
     * Default constructor
     */
    public BinaryStringToQRCode() {
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