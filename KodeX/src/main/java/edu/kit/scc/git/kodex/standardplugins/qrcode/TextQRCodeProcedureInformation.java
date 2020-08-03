package edu.kit.scc.git.kodex.standardplugins.qrcode;

import edu.kit.scc.git.kodex.plugininterface.ProcedureInformation;
import javafx.collections.FXCollections;
import javafx.scene.image.Image;

/**
 * This class contains information about the qr-code procedure plugin.
 *
 * @author Leonhard Kraft
 * @version 1.0
 */
public class TextQRCodeProcedureInformation extends ProcedureInformation {

  /** Creates a new instance of the QR-Code class and sets the fields accordingly. */
  public TextQRCodeProcedureInformation() {

    this.icon = new Image(getClass().getResourceAsStream("img/qr-code_kodex.png"));
    this.description =
        "In diesem Verfahren kann ein Text zu einem QR-Code umgewandelt werden."
            + "Umgedreht kann auch ein QR-Code wieder in einen Text übersetzt werden";
    this.labels =
        FXCollections.observableArrayList(
            "Klasse 7", "Kodierungsverfahren", "Kodieren und Dekodieren");
  }

  @Override
  public String getName() {
    return "QR-Code";
  }
}
