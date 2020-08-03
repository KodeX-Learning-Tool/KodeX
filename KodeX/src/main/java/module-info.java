import edu.kit.scc.git.kodex.plugininterface.Pluginable;
import edu.kit.scc.git.kodex.plugininterface.ProcedurePlugin;
import edu.kit.scc.git.kodex.standardplugins.bwimageprocedure.BWImageProcedurePlugin;
import edu.kit.scc.git.kodex.standardplugins.colorimageprocedure.ColorImageProcedurePlugin;
import edu.kit.scc.git.kodex.standardplugins.greyscaleimageprocedure.GreyScaleImageProcedurePlugin;
import edu.kit.scc.git.kodex.standardplugins.qrcode.TextQRCodeProcedurePlugin;
import edu.kit.scc.git.kodex.standardplugins.rle.TextRLEProcedurePlugin;

module edu.kit.scc.git.kodex {
  requires javafx.fxml;
  requires transitive javafx.controls;
  requires transitive javafx.graphics;
  requires transitive javafx.base;
  requires org.kordamp.ikonli.javafx;
  requires org.controlsfx.controls;
  requires java.desktop;
  requires com.google.zxing.javase;
  requires transitive com.google.zxing;

  uses edu.kit.scc.git.kodex.plugininterface.ProcedurePlugin;
  uses edu.kit.scc.git.kodex.plugininterface.Pluginable;
  
  opens edu.kit.scc.git.kodex;
  opens edu.kit.scc.git.kodex.presenter to javafx.fxml;
  opens edu.kit.scc.git.kodex.standardplugins.colorimageprocedure.presenter to javafx.fxml;
  opens edu.kit.scc.git.kodex.standardplugins.rle.presenter to javafx.fxml;
  opens edu.kit.scc.git.kodex.standardplugins.qrcode.presenter to javafx.fxml;
  opens edu.kit.scc.git.kodex.standardplugins.greyscaleimageprocedure.presenter to javafx.fxml;
  opens edu.kit.scc.git.kodex.standardplugins.bwimageprocedure.presenter to javafx.fxml;
  exports edu.kit.scc.git.kodex;

  provides Pluginable
  with ColorImageProcedurePlugin, BWImageProcedurePlugin, GreyScaleImageProcedurePlugin, TextQRCodeProcedurePlugin, TextRLEProcedurePlugin;

  provides ProcedurePlugin
  with ColorImageProcedurePlugin, BWImageProcedurePlugin, GreyScaleImageProcedurePlugin, TextQRCodeProcedurePlugin, TextRLEProcedurePlugin;
}
