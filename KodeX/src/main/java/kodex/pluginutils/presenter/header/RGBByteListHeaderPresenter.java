package kodex.pluginutils.presenter.header;

import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import kodex.plugininterface.ChainLinkHeaderPresenter;
import kodex.plugininterface.Content;

/** This class manages the view for the header of a RGB byte list.
 * 
 *  @author Raimon Gramlich
 *  @version 1.0
 */
public class RGBByteListHeaderPresenter extends ChainLinkHeaderPresenter {

  public RGBByteListHeaderPresenter(Content content) {
    super(content);
    // TODO Auto-generated constructor stub
  }

  @Override
  public AnchorPane getView() {
    Map<String, Object> map = content.getHeader();
    
    ListView<Text> headerListView = new ListView<>();
    
    ObservableList<Text> headerEntryList = FXCollections.observableArrayList();
    
    for (Map.Entry<String, Object> entry : map.entrySet()) {
      headerEntryList.add(new Text(entry.getKey() + ": " + entry.getValue()));
    }
    
    headerListView.setItems(headerEntryList);
    
    return new AnchorPane(headerListView);
  }
}
