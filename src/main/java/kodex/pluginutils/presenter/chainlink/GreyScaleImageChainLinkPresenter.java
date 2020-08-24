package kodex.pluginutils.presenter.chainlink;

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser.ExtensionFilter;
import kodex.model.I18N;
import kodex.plugininterface.ChainLinkPresenter;
import kodex.plugininterface.ChainStep;
import kodex.pluginutils.model.content.GreyScaleImage;
import kodex.pluginutils.presenter.edit.GreyScaleImageEditPresenter;
import kodex.pluginutils.presenter.header.ColorImageHeaderPresenter;

/** The Class GreyScaleImageChainLinkPresenter manages the view for the grey scale image.
 * 
 *  @author Raimon Gramlich
 *  @author Patrick Spiesberger
 *  
 *  @version 1.0
 */
public class GreyScaleImageChainLinkPresenter extends ChainLinkPresenter {

  /** The chain link name. */
  private static final String CHAIN_LINK_NAME = "Graustufenbild";

  /** The color image view displaying the image. */
  private ImageView colorImageView;

  /**
   * The Constant PREFFERED_IMAGE_SIZE. Scales the image if it smaller than this
   * value.
   */
  private static final int PREFFERED_IMAGE_SIZE = 360;

  /** The selected X coordinate. */
  private double selectedX;

  /** The selected Y coordinate. */
  private double selectedY;

  /** The scale factor of the image. */
  private int scaleFactor = 1;

  /** The Constant NOT_MARKED. */
  private static final int NOT_MARKED = -1;

  /** The ID of the last element marked. */
  private int lastElementMarked = NOT_MARKED;

  /** The last marked color. */
  private Color lastMarkedColor;

  /** The image which is shown. */
  private Image image;

  /**
   * Instantiates a new color image chain link presenter.
   *
   * @param previous the previous ChainLinkPresenter
   * @param previousStep the previous step
   * @param nextStep the next step
   */
  public GreyScaleImageChainLinkPresenter(
      ChainLinkPresenter previous, ChainStep previousStep, ChainStep nextStep) {
    super(previous, previousStep, nextStep);
    content = new GreyScaleImage();
    chainLinkEditPresenter = new GreyScaleImageEditPresenter(this);
    chainLinkHeaderPresenter = new ColorImageHeaderPresenter(this.getContent());
    
    colorImageView = new ImageView();
    
    // allows detection of clicks on transparent parts of the image
    colorImageView.setPickOnBounds(true);

    colorImageView.setOnMouseClicked(
        e -> {
          // store selected (marked) pixel
          selectedX = e.getX();
          selectedY = e.getY();
      
          handleMark();
        });   
  }

  @Override
  protected int calculateID() {
    return (int) (selectedX / scaleFactor)
        + ((int) (selectedY / scaleFactor)
            * (int) (colorImageView.getImage().getWidth() / scaleFactor));
  }

  /**
   * Edits the color of the pixel or a set of pixels if the image is scaled up.
   *
   * @param writer the PixelWriter
   * @param reader the PixelReader
   * @param id the universal id for marking elements
   * @param color the color which the "pixel" should be edited to
   */
  private void editPixelColor(PixelWriter writer, PixelReader reader, int id, Color color) {
    int x = id % (int) Math.round(colorImageView.getImage().getWidth());
    int y = (id / (int) Math.round(colorImageView.getImage().getWidth())) * scaleFactor;

    // store original color
    if (lastElementMarked != id) {
      lastMarkedColor = reader.getColor(x, y);
    }

    // mark the image
    for (int i = x; i < x + scaleFactor; i++) {
      for (int j = y; j < y + scaleFactor; j++) {
        writer.setColor(i, j, color);
      }
    }

    // store last marked "pixel"
    lastElementMarked = id;
  }

  @Override
  public AnchorPane getView() {
    updateView();

    StackPane alignmentPane = new StackPane();
    
    // anchor the alignment pane in the center
    AnchorPane.setTopAnchor(alignmentPane, 0d);
    AnchorPane.setRightAnchor(alignmentPane, 0d);
    AnchorPane.setBottomAnchor(alignmentPane, 0d);
    AnchorPane.setLeftAnchor(alignmentPane, 0d);
    
    // align the image view in the center
    alignmentPane.getChildren().add(colorImageView);
    alignmentPane.setAlignment(Pos.CENTER);
    
    return new AnchorPane(alignmentPane);
  }

  @Override
  public String getName() {
    return CHAIN_LINK_NAME;
  }

  @Override
  protected void mark(int id) {    
    id = id * scaleFactor;
    
    int width = (int) image.getWidth();
    int height = (int) image.getHeight();

    // Creating a writable image
    WritableImage writableImage = new WritableImage(width, height);

    // Reading color from the loaded image
    PixelReader pixelReader = image.getPixelReader();

    // getting the pixel writer
    PixelWriter writer = writableImage.getPixelWriter();

    // Reading the color of the image
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        // Retrieving the color of the pixel of the loaded image
        Color color = pixelReader.getColor(x, y);

        // Setting the color to the writable image
        writer.setColor(x, y, color);
      }
    }

    // unmark last marked "pixel"
    if (lastElementMarked != NOT_MARKED) {
      editPixelColor(writer, pixelReader, lastElementMarked, lastMarkedColor);
    }

    // mark "pixel"
    editPixelColor(writer, pixelReader, id, Color.RED);

    // show marked image
    colorImageView.setImage(writableImage);
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

    WritableImage output = new WritableImage(W * S, H * S);

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
  
  @Override
  public void updateView() {
    image = ((GreyScaleImage) content).getImage();
    
    scaleFactor = (int) (PREFFERED_IMAGE_SIZE / Math.max(image.getWidth(), image.getHeight()));
    
    // scale if smaller than preferred size
    if (scaleFactor > 1) {
      image = resample(image, scaleFactor);
    } else {
      scaleFactor = 1;
    }

    colorImageView.setImage(image);
    
    // remarks the view
    if (lastElementMarked !=  NOT_MARKED) {
      // mark(lastElementMarked / scaleFactor);
      
      // remember the new value
      int x = lastElementMarked % (int) Math.round(colorImageView.getImage().getWidth());
      int y = (lastElementMarked 
          / (int) Math.round(colorImageView.getImage().getWidth())) * scaleFactor;
      
      lastMarkedColor = image.getPixelReader().getColor(x, y);
    } 
  }
  
  @Override
  public List<ExtensionFilter> getExtensionsFilter() {
    List<ExtensionFilter> extensionFilters = new ArrayList<>();
    extensionFilters.add(new ExtensionFilter(I18N.get("files.image"), "*.png", "*.jpg"));
    return extensionFilters;
  }
}
