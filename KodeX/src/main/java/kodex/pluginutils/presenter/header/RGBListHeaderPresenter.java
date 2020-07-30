package kodex.pluginutils.presenter.header;

import java.util.Map;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import kodex.plugininterface.ChainLinkHeaderPresenter;
import kodex.plugininterface.Content;

/** This class manages the view for the header of a RGB list.
 * 
 *  @author Raimon Gramlich
 *  @version 1.0
 */
public class RGBListHeaderPresenter extends ChainLinkHeaderPresenter {

  /**
   * Instantiates a new RGB list header presenter.
   *
   * @param content the RGB list
   */
  public RGBListHeaderPresenter(Content<?> content) {
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
