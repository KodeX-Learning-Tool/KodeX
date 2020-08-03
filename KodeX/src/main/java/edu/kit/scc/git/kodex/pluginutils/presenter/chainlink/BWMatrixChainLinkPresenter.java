package edu.kit.scc.git.kodex.pluginutils.presenter.chainlink;

import edu.kit.scc.git.kodex.plugininterface.ChainLinkPresenter;
import edu.kit.scc.git.kodex.plugininterface.ChainStep;
import edu.kit.scc.git.kodex.pluginutils.model.content.BinaryMatrix;
import edu.kit.scc.git.kodex.pluginutils.presenter.edit.BWMatrixEditPresenter;
import edu.kit.scc.git.kodex.pluginutils.presenter.header.RGBMatrixHeaderPresenter;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;

/** 
 *  The Class BWMatrixChainLinkPresenter manages the view for the black & white matrix.
 * 
 *  @author Raimon Gramlich
 *  @author Patrick Spiesberger
 */
public class BWMatrixChainLinkPresenter extends ChainLinkPresenter {

  /**
   * The Class MatrixButton.
   *
   * @author Raimon Gramlich
   * @author Patrick Spiesberger
   */
  private class MatrixButton extends Button {

    /**
     * Instantiates a new matrix button.
     *
     * @param text the text to be set
     * @param id the id of the button
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
  private static final String CHAIN_LINK_NAME = "Binär-Matrix";

  /** The Constant NOT_MARKED. */
  private static final int NOT_MARKED = -1;

  /** The universal ID of the selected element. */
  private int selectedElementID;

  /** The matrix grid pane. */
  private TilePane matrixPane = new TilePane();

  /** The ID of the last element marked. */
  private int lastElementMarked = NOT_MARKED;

  /**
   * Instantiates a new RGB matrix chain link presenter.
   *
   * @param previous the previous ChainLinkPresenter
   * @param previousStep the previous step
   * @param nextStep the next step
   */
  public BWMatrixChainLinkPresenter(
      ChainLinkPresenter previous, ChainStep previousStep, ChainStep nextStep) {
    super(previous, previousStep, nextStep);
    chainLinkEditPresenter = new BWMatrixEditPresenter(this);
    content = new BinaryMatrix(3, 3);
    chainLinkHeaderPresenter = new RGBMatrixHeaderPresenter(this.getContent());
  }

  @Override
  protected int calculateID() {
    return selectedElementID;
  }

  /**
   * Edits the font color of the button.
   *
   * @param id the universal id
   * @param color the color to be set
   */
  private void editMatrixElementColor(int id, Color color) {    
    MatrixButton element = (MatrixButton) matrixPane.getChildren().get(id);
    element.setTextFill(color);
  }

  @Override
  public AnchorPane getView() {
    
    BinaryMatrix matrix = (BinaryMatrix) content;
    
    matrixPane.setPrefColumns(matrix.getWidth());
    matrixPane.setPrefHeight(matrix.getHeight());
    matrixPane.getChildren().clear();

    // create buttons for each element in the 2d array
    for (int j = 0; j < matrix.getHeight(); j++) {
      for (int i = 0; i < matrix.getWidth(); i++) {
        MatrixButton btn =
            new MatrixButton(matrix.get(i, j).toString(), i + j * matrix.getWidth());

        matrixPane.getChildren().add(btn);
      }
    }
    
    AnchorPane chainLinkPane = new AnchorPane();

    chainLinkPane.getChildren().add(matrixPane);

    AnchorPane.setTopAnchor(matrixPane, 0d);
    AnchorPane.setRightAnchor(matrixPane, 0d);
    AnchorPane.setBottomAnchor(matrixPane, 0d);
    AnchorPane.setLeftAnchor(matrixPane, 0d);

    return chainLinkPane;
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

    // set mark id for editing
    chainLinkEditPresenter.setMarkID(id);
  }

  @Override
  public void updateView() {
    // TODO Auto-generated method stub
    
  }
}
