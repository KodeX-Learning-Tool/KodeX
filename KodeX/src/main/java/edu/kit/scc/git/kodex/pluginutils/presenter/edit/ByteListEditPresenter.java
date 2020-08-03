package edu.kit.scc.git.kodex.pluginutils.presenter.edit;

import java.util.function.UnaryOperator;

import edu.kit.scc.git.kodex.plugininterface.ChainLinkEditPresenter;
import edu.kit.scc.git.kodex.plugininterface.ChainLinkPresenter;
import edu.kit.scc.git.kodex.pluginutils.model.content.ByteList;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * This class manages the edit view and is responsible for editing a RGB byte list.
 * 
 *  @author Raimon Gramlich
 */
public class ByteListEditPresenter extends ChainLinkEditPresenter {
  
  private ByteList content;
  
  private TextField byteField;
  
  /** The text formatter which only allows binary input. */
  private TextFormatter<String> byteFormatter;
  
  /** The length of one unit used to determine how many list elements belong together. */
  private int unitLength;
  
  private AnchorPane view;

  /**
   * Instantiates a new RGB byte list edit presenter.
   *
   * @param chainLinkPresenter the corresponding chain link presenter
   */
  public ByteListEditPresenter(ChainLinkPresenter chainLinkPresenter) {
    super(chainLinkPresenter);
    
    // only allows 0 or 1 as input
    UnaryOperator<TextFormatter.Change> filter = change -> {
      if (change.getControlNewText().matches("[0-1]{8}")) {
        return change;
      } else {
        return null;
      }
    };
    
    byteFormatter = new TextFormatter<>(filter);
    
    byteField = new TextField();
    byteField.setTextFormatter(byteFormatter);
    
    Label byteLabel = new Label("Byte: ");
    
    HBox byteBox = new HBox(byteLabel, byteField);
   
    view = new AnchorPane((new VBox(byteBox)));
  }

  @Override
  public AnchorPane getView() {
    unitLength = (int) content.getHeader().get("unit-length");
    
    updateMarkedElement();
    
    return view;
  }

  @Override
  public void handleSubmit() {
    content.getList().set(markID * unitLength, byteField.getText());
    
    chainLinkPresenter.updateChain();
  }

  @Override
  protected void updateMarkedElement() {
    
    content = (ByteList) chainLinkPresenter.getContent();
    
    byteField.setText(String.valueOf(content.get(markID * unitLength)));
  }
}
