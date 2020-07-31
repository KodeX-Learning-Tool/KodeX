package kodex.pluginutils.presenter.chainlink;

import java.io.File;
import java.io.IOException;

import com.google.zxing.client.j2se.MatrixToImageWriter;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import kodex.plugininterface.ChainLinkPresenter;
import kodex.plugininterface.ChainStep;
import kodex.pluginutils.model.content.QRCode;

/** 
 * This class is the ChainLinkPresenter for a QRCode. It's main purpose is to 
 * generate the view for a QRCode.
 * 
 * @author Yannick Neubert
 * @author Raimon Gramlich
 *
 */
public class QRCodeChainLinkPresenter extends ChainLinkPresenter {
  
  /** The chain link name. */
  private static final String CHAIN_LINK_NAME = "QRCode";
  
  /** The ImageView displaying the QRCode. */
  private ImageView qrView;
  
  /** The scale factor of the image. */
  private int scaleFactor = 1;
  
  /**
   * The constant PREFFERED_IMAGE_SIZE. Scale the image if it smaller than this
   * value.
   */
  private static final int PREFFERED_IMAGE_SIZE = 90;

  public QRCodeChainLinkPresenter(
      ChainLinkPresenter previous, ChainStep previousStep, ChainStep nextStep) {
    super(previous, previousStep, nextStep);
    this.content = new QRCode();
  }

  @Override
  public AnchorPane getView() {
    File file = new File("dummy");
    Image image = null;
    try {
      MatrixToImageWriter.writeToPath(
          ((QRCode) this.getContent()).getBitMatrix(), "PNG", file.toPath());
      image = new Image(file.toURI().toString());
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    if (image == null) {
      return null;
    }
    scaleFactor = (int) (PREFFERED_IMAGE_SIZE / Math.max(image.getWidth(), image.getHeight()));
    
    // scale if smaller than preferred size
    if (scaleFactor > 1) {
      qrView = new ImageView(resample(image, scaleFactor));
    } else {
      qrView = new ImageView(image);
      scaleFactor = 1;
    }

    StackPane alignmentPane = new StackPane();
    
    // anchor the alignment pane in the center
    AnchorPane.setTopAnchor(alignmentPane, 0d);
    AnchorPane.setRightAnchor(alignmentPane, 0d);
    AnchorPane.setBottomAnchor(alignmentPane, 0d);
    AnchorPane.setLeftAnchor(alignmentPane, 0d);
    
    // align the image view in the center
    alignmentPane.getChildren().add(qrView);
    alignmentPane.setAlignment(Pos.CENTER);
    
    AnchorPane chainLinkPane = new AnchorPane();
    
    chainLinkPane.getChildren().add(alignmentPane);
    
    return chainLinkPane;
  }

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
  public String getName() {
    return CHAIN_LINK_NAME;
  }

  @Override
  protected void mark(int id) {
    // TODO Auto-generated method stub

  }
}
