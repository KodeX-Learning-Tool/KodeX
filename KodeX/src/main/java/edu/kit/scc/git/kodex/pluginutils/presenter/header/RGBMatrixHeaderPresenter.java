package edu.kit.scc.git.kodex.pluginutils.presenter.header;

import java.util.Map;

import edu.kit.scc.git.kodex.plugininterface.ChainLinkHeaderPresenter;
import edu.kit.scc.git.kodex.plugininterface.Content;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/** This class manages the view for the header of a RGB matrix.
 * 
 *  @author Raimon Gramlich
 *  @version 1.0
 */
public class RGBMatrixHeaderPresenter extends ChainLinkHeaderPresenter {

  /**
   * Instantiates a new RGB matrix header presenter.
   *
   * @param content the RGB matrix
   */
  public RGBMatrixHeaderPresenter(Content<?> content) {
    super(content);
  }

  @Override
  public AnchorPane getView() {
    Map<String, Object> map = content.getHeader();
    
    TextFlow headerTextFlow = new TextFlow();
    
    for (Map.Entry<String, Object> entry : map.entrySet()) {
      Text headerEntry = new Text(entry.getKey() + ": " + entry.getValue() + "\n");
      headerTextFlow.getChildren().add(headerEntry);
    }
    
    return new AnchorPane(headerTextFlow);
  }
}
