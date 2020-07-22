package kodex.pluginutils.presenter.chainlink;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import kodex.plugininterface.ChainLinkPresenter;
import kodex.plugininterface.ChainStep;
import kodex.pluginutils.model.content.RGBMatrix;
import kodex.pluginutils.presenter.edit.RGBMatrixEditPresenter;
import kodex.pluginutils.presenter.header.RGBMatrixHeaderPresenter;

/**
 * @author Raimon Gramlich
 */
public class RGBMatrixChainLinkPresenter extends ChainLinkPresenter {
	
	/** The universal ID of the selected element. */
	private int selectedElementID;
	
	/** The matrix grid pane. */
	private GridPane matrixPane = new GridPane();
	
	
	/**
	 * Instantiates a new RGB matrix chain link presenter.
	 *
	 * @param previous the previous ChainLinkPresenter
	 * @param previousStep the previous step
	 * @param nextStep the next step
	 */
	public RGBMatrixChainLinkPresenter(ChainLinkPresenter previous, ChainStep previousStep, ChainStep nextStep) {
		super(previous, previousStep, nextStep);
		chainLinkEditPresenter = new RGBMatrixEditPresenter(this);
		chainLinkHeaderPresenter = new RGBMatrixHeaderPresenter(this.getContent());
	}



	@Override
	protected void mark(int id) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public AnchorPane getView() {
		AnchorPane chainLinkPane = new AnchorPane();
		RGBMatrix matrix = (RGBMatrix) content;
		
		Color[][] colorMatrix = matrix.getMatrix();
		
		// create buttons for each element in the 2d array
		for (int i = 0; i < matrix.getWidth(); i++) {
			for (int j = 0; j < matrix.getHeight(); j++) {
				matrixPane.add(new MatrixButton(colorToRGBString(colorMatrix[i][j]), i + j * matrix.getHeight()), i, j);
			}
		}
		
		chainLinkPane.getChildren().add(matrixPane);
		
		AnchorPane.setTopAnchor(matrixPane, 0d);
		AnchorPane.setRightAnchor(matrixPane, 0d);
		AnchorPane.setBottomAnchor(matrixPane, 0d);
		AnchorPane.setLeftAnchor(matrixPane, 0d);
		
		return chainLinkPane;
	}
	
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
		 * @param id the id of the button
		 */
		MatrixButton(String text, int id) {
			this.setText(text);
			this.setOnAction(new EventHandler<ActionEvent>() {
			    @Override public void handle(ActionEvent e) {
			    	selectedElementID = id;
			        handleMark();
			    }
			});
		}
		
		
	}
	
	/**
	 * Gets the RGB string of the given color.
	 *
	 * @param color the color
	 * @return the rgb string
	 */
	private String colorToRGBString(Color color) {		
		return "(" + String.valueOf((int) Math.round(color.getRed() * 255)) + ", " + String.valueOf((int) Math.round(color.getGreen() * 255)) 
		+ ", " + String.valueOf((int) Math.round(color.getBlue() * 255)) + ")";
	}

}