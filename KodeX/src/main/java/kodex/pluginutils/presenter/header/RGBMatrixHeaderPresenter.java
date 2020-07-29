package kodex.pluginutils.presenter.header;

import java.util.Map;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import kodex.plugininterface.ChainLinkHeaderPresenter;
import kodex.plugininterface.Content;

/** This class manages the view for the header of a RGB matrix.
 * 
 *  @author Raimon Gramlich
 *  @version 1.0
 */
public class RGBMatrixHeaderPresenter extends ChainLinkHeaderPresenter {

  public RGBMatrixHeaderPresenter(Content content) {
    super(content);
    // TODO Auto-generated constructor stub
  }

  @Override
  public AnchorPane getView() {
    Map<String, Object> map = content.getHeader();
    
    TableColumn<Map.Entry<String, Object>, String> keyColumn = new TableColumn<>("Key");
    keyColumn.setCellValueFactory(entry -> new SimpleStringProperty(entry.getValue().getKey()));
    
    TableColumn<Map.Entry<String, Object>, Object> valueColumn = new TableColumn<>("Value");
    valueColumn.setCellValueFactory(entry -> new SimpleObjectProperty<>(entry.getValue().getValue()));

    
    ObservableList<Map.Entry<String, Object>> entries =
        FXCollections.observableArrayList(map.entrySet()); 
    
    final TableView<Map.Entry<String, Object>> headerTableView = new TableView<>(entries);
    
    headerTableView.setColumnResizePolicy((param) -> true );
    
    headerTableView.getColumns().setAll(keyColumn, valueColumn);
        
    return new AnchorPane(headerTableView);
  }
}
