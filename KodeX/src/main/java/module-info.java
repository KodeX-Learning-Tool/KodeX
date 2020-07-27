import kodex.plugininterface.Pluginable;
import kodex.plugininterface.ProcedurePlugin;
import kodex.standardplugins.bwimageprocedure.BWImageProcedurePlugin;
import kodex.standardplugins.colorimageprocedure.ColorImageProcedurePlugin;
import kodex.standardplugins.greyscaleimageprocedure.GreyScaleImageProcedurePlugin;
import kodex.standardplugins.qrcode.TextQRCodeProcedurePlugin;
import kodex.standardplugins.rle.TextRLEProcedurePlugin;

module kodex.KodeX {
  requires javafx.controls;
  requires javafx.fxml;
  requires transitive javafx.graphics;
  requires javafx.base;
  requires org.kordamp.ikonli.javafx;
  requires org.controlsfx.controls;
  

  uses kodex.plugininterface.ProcedurePlugin;
  uses kodex.plugininterface.Pluginable;
  
  opens kodex;
  opens kodex.presenter to javafx.fxml;
  opens kodex.standardplugins.colorimageprocedure.presenter to javafx.fxml;
  opens kodex.standardplugins.rle.presenter to javafx.fxml;
  opens kodex.standardplugins.greyscaleimageprocedure.presenter to javafx.fxml;
  opens kodex.standardplugins.bwimageprocedure.presenter to javafx.fxml;
  exports kodex;

  provides Pluginable
  with ColorImageProcedurePlugin, BWImageProcedurePlugin, GreyScaleImageProcedurePlugin, TextQRCodeProcedurePlugin, TextRLEProcedurePlugin;

  provides ProcedurePlugin
  with ColorImageProcedurePlugin, BWImageProcedurePlugin, GreyScaleImageProcedurePlugin, TextQRCodeProcedurePlugin, TextRLEProcedurePlugin;
}
