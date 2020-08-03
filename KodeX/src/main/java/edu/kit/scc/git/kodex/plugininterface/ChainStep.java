package edu.kit.scc.git.kodex.plugininterface;

/**
 * This class represents an abstract step between two links. It contains the functionality to be
 * able to decode or code the content between two explicitly defined levels. Specific steps must
 * expand this class.
 *
 * @author Patrick Spiesberger
 * @version 1.0
 */
public interface ChainStep {

  /**
   * Decodes the content of one link and places it in the content of the previous link.
   *
   * @param input : the content of the link to be decoded
   * @param output : the content of the level which should be set
   */
  public abstract void decode(Content<?> input, Content<?> output);

  /**
   * Encodes the content of one link and places it in the content of the next link.
   *
   * @param input : the content of the link to be encoded
   * @param output : the content of the level which should be set
   */
  public abstract void encode(Content<?> input, Content<?> output);
}
