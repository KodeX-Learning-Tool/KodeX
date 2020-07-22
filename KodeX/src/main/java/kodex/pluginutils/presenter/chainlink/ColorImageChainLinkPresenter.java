package kodex.pluginutils.presenter.chainlink;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import kodex.plugininterface.ChainLinkPresenter;
import kodex.plugininterface.ChainStep;
import kodex.pluginutils.model.content.ColorImage;
import kodex.pluginutils.presenter.edit.ColorImageEditPresenter;
import kodex.pluginutils.presenter.header.ColorImageHeaderPresenter;

/**
 * @author Raimon Gramlich
 */
public class ColorImageChainLinkPresenter extends ChainLinkPresenter {
	
	/** The color image view displaying the image. */
	private ImageView colorImageView;
	
	/** The Constant PREFFERED_IMAGE_SIZE. Scales the image if it smaller than this value. */
	private final static int PREFFERED_IMAGE_SIZE = 90;
	
	/** The selected X coordinate. */
	private double selectedX;
	
	/** The selected Y coordinate. */
	private double selectedY;
	
	/** The scale factor of the image. */
	private int scaleFactor = 1;

	public ColorImageChainLinkPresenter(ChainLinkPresenter previous, ChainStep previousStep, ChainStep nextStep) {
		super(previous, previousStep, nextStep);
		chainLinkEditPresenter = new ColorImageEditPresenter(this);
		chainLinkHeaderPresenter = new ColorImageHeaderPresenter(this.getContent());
		content = new ColorImage();
	}



	@Override
	protected void mark(int id) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public AnchorPane getView() {
		AnchorPane chainLinkPane = new AnchorPane();
		Image image = ((ColorImage) this.getContent()).getImage();
		
		scaleFactor = (int) (PREFFERED_IMAGE_SIZE / Math.max(image.getWidth(), image.getHeight()));
		
		// scale if smaller than preffered size
		if (scaleFactor > 1) {
			colorImageView = new ImageView(resample(image, scaleFactor));
		} else {
			colorImageView = new ImageView(image);
			scaleFactor = 1;
		}
		
        // allows detection of clicks on transparent parts of the image
		colorImageView.setPickOnBounds(true);

		colorImageView.setOnMouseClicked(e -> {
			// store selected (marked) pixel
			selectedX = e.getX();
			selectedY = e.getY();
			
            handleMark();
        });
		
		chainLinkPane.getChildren().add(colorImageView);
		
		AnchorPane.setTopAnchor(colorImageView, 0d);
		AnchorPane.setRightAnchor(colorImageView, 0d);
		AnchorPane.setBottomAnchor(colorImageView, 0d);
		AnchorPane.setLeftAnchor(colorImageView, 0d);
		
		
		return chainLinkPane;
	}
	
	
	/**
	 * Resamples the given image according to the scale factor.
	 *
	 * @param input the input image
	 * @param scaleFactor the scale factor
	 * @return the scaled image
	 */
	private Image resample(Image input, int scaleFactor) {
	    final int W = (int) input.getWidth();
	    final int H = (int) input.getHeight();
	    final int S = scaleFactor;
	    
	    WritableImage output = new WritableImage(
	      W * S,
	      H * S
	    );
	    
	    PixelReader reader = input.getPixelReader();
	    PixelWriter writer = output.getPixelWriter();
	    
	    for (int y = 0; y < H; y++) {
	      for (int x = 0; x < W; x++) {
	        final int argb = reader.getArgb(x, y);
	        for (int dy = 0; dy < S; dy++) {
	          for (int dx = 0; dx < S; dx++) {
	            writer.setArgb(x * S + dx, y * S + dy, argb);
	          }
	        }
	      }
	    }
	 
	    return output;
	}
}