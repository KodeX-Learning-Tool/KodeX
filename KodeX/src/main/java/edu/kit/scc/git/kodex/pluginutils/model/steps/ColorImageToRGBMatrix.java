package edu.kit.scc.git.kodex.pluginutils.model.steps;

import java.util.HashMap;
import java.util.Map;

import edu.kit.scc.git.kodex.plugininterface.ChainStep;
import edu.kit.scc.git.kodex.plugininterface.Content;
import edu.kit.scc.git.kodex.pluginutils.model.content.ColorImage;
import edu.kit.scc.git.kodex.pluginutils.model.content.RGBMatrix;

/**
 * This class represents the bidirectional step between ColorImage and RGBMatrix.
 * It contains the functionality to decode and encode the content between 
 * these explicitly defined levels.
 * 
 * @author Yannick Neubert
 * @version 1.0
 */

public class ColorImageToRGBMatrix implements ChainStep {

  @SuppressWarnings("unchecked")
  @Override
  public void decode(Content<?> input, Content<?> output) {
    ColorImage img = (ColorImage) output;
    RGBMatrix mtx = (RGBMatrix) input;

    int width = (int) mtx.getHeader().get("width");
    int height = (int) mtx.getHeader().get("height");
    img.setSize(width, height);
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        img.setColor(x, y, mtx.get(x, y));
      }
    }
    
    if (img.getHeader() == null || img.getHeader().isEmpty()) {  
      Map<String, Object> header = new HashMap<>();
      Map<String, Object> map =  mtx.getHeader();
      
      for (Map.Entry<String, Object> entry: map.entrySet()) {
        header.put(entry.getKey(), entry.getValue());
      }

      img.setHeader(header);
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public void encode(Content<?> input, Content<?> output) {
    ColorImage img = (ColorImage) input;
    RGBMatrix mtx = (RGBMatrix) output;

    mtx.setSize(img.getWidth(), img.getHeight());
    for (int y = 0; y < img.getHeight(); y++) {
      for (int x = 0; x < img.getWidth(); x++) {
        mtx.set(x, y, img.getColor(x, y));
      }
    }
    
    if (mtx.getHeader() == null || mtx.getHeader().isEmpty()) {  
      Map<String, Object> header = new HashMap<>();
      Map<String, Object> map =  img.getHeader();
      
      for (Map.Entry<String, Object> entry: map.entrySet()) {
        header.put(entry.getKey(),  ((int) Math.round((double) entry.getValue())));
      }

      mtx.setHeader(header);
    }
  }
}
