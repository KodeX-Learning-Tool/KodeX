package kodex.pluginutils.presenter.chainlink;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser.ExtensionFilter;
import kodex.model.I18N;
import kodex.plugininterface.ChainLinkPresenter;
import kodex.plugininterface.ChainStep;
import kodex.pluginutils.model.content.RGBMatrix;
import kodex.pluginutils.presenter.edit.RGBMatrixEditPresenter;
import kodex.pluginutils.presenter.header.RGBMatrixHeaderPresenter;

/** The Class RGBMatrixChainLinkPresenter manages the view for the RGB matrix.
 * 
 * @author Raimon Gramlich
 */
public class RGBMatrixChainLinkPresenter extends ChainLinkPresenter {

  /**
   * The Class MatrixButton.
   *
   * @author Raimon Gramlich
   */
  private class MatrixButton extends Button {

    /**
     * Instantiates a new matrix button.
     *
     * @param text the text to be set
     * @param id   the id of the button
     */
    MatrixButton(String text, int id) {
      this.getStyleClass().add("matrix__button");
      this.setText(text);
      this.setOnAction(e -> {
        selectedElementID = id;
        handleMark();
      });
    }
  }

  /** The chain link name. */
  private static final String CHAIN_LINK_NAME = "RGB-Matrix";

  /** The Constant NOT_MARKED. */
  private static final int NOT_MARKED = -1;

  /** The universal ID of the selected element. */
  private int selectedElementID;

  /** The matrix grid pane. */
  private TilePane matrixPane;

  /** The ID of the last element marked. */
  private int lastElementMarked = NOT_MARKED;

  /**
   * Instantiates a new RGB matrix chain link presenter.
   *
   * @param previous     the previous ChainLinkPresenter
   * @param previousStep the previous step
   * @param nextStep     the next step
   */
  public RGBMatrixChainLinkPresenter(ChainLinkPresenter previous, 
      ChainStep previousStep, ChainStep nextStep) {
    super(previous, previousStep, nextStep);
    content = new RGBMatrix(3, 3);
    chainLinkEditPresenter = new RGBMatrixEditPresenter(this);
    chainLinkHeaderPresenter = new RGBMatrixHeaderPresenter(this.getContent());
    
    matrixPane = new TilePane();
  }

  @Override
  protected int calculateID() {
    return selectedElementID;
  }

  /**
   * Gets the RGB string of the given color.
   *
   * @param color the color
   * @return the rgb string
   */
  private String colorToRGBString(Color color) {
    return "(" + (int) Math.round(color.getRed() * 255) + ", "
        + (int) Math.round(color.getGreen() * 255) + ", "
        + (int) Math.round(color.getBlue() * 255) + ")";
  }

  /**
   * Edits the font color of the button.
   *
   * @param id    the universal id
   * @param color the color to be set
   */
  private void editMatrixElementColor(int id, Color color) {
    MatrixButton element = (MatrixButton) matrixPane.getChildren().get(id);
    element.setTextFill(color);
  }

  @Override
  public AnchorPane getView() {
    updateView();

    AnchorPane.setTopAnchor(matrixPane, 0d);
    AnchorPane.setRightAnchor(matrixPane, 0d);
    AnchorPane.setBottomAnchor(matrixPane, 0d);
    AnchorPane.setLeftAnchor(matrixPane, 0d);

    return new AnchorPane(matrixPane);
  }

  @Override
  public String getName() {
    return CHAIN_LINK_NAME;
  }

  @Override
  protected void mark(int id) {    
    // unmark last element marked
    if (lastElementMarked != NOT_MARKED) {
      editMatrixElementColor(lastElementMarked, Color.BLACK);
    }
    
    // mark selected element
    editMatrixElementColor(id, Color.RED);
    lastElementMarked = id;
  }
  
  @Override
  public void updateView() {
    RGBMatrix matrix = (RGBMatrix) content;
    
    matrixPane.setPrefColumns(matrix.getWidth());
    matrixPane.setPrefHeight(matrix.getHeight());
    matrixPane.getChildren().clear();
    
    // create buttons for each element in the 2d array
    for (int j = 0; j < matrix.getHeight(); j++) {
      for (int i = 0; i < matrix.getWidth(); i++) {
        MatrixButton btn = new MatrixButton(
            colorToRGBString(matrix.get(i, j)), i + j * matrix.getWidth());
        
        matrixPane.getChildren().add(btn);
      }
    }
    
    // remarks the view
    if (lastElementMarked !=  NOT_MARKED) {
      mark(lastElementMarked);
    }
  }
  
  @Override
  public List<ExtensionFilter> getExtensionsFilter() {
    List<ExtensionFilter> extensionFilters = new ArrayList<>();
    extensionFilters.add(new ExtensionFilter(I18N.get("files.text"), "*.txt"));
    return extensionFilters;
  }
}
