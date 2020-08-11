import kodex.plugininterface.Plugin;
import kodex.plugininterface.ProcedurePlugin;
import kodex.standardplugins.bwimageprocedure.BWImageProcedurePlugin;
import kodex.standardplugins.colorimageprocedure.ColorImageProcedurePlugin;
import kodex.standardplugins.greyscaleimageprocedure.GreyScaleImageProcedurePlugin;
import kodex.standardplugins.qrcode.TextQRCodeProcedurePlugin;
import kodex.standardplugins.rle.TextRLEProcedurePlugin;

module kodex.KodeX {
  requires transitive javafx.fxml;
  requires transitive javafx.controls;
  requires transitive javafx.graphics;
  requires org.kordamp.ikonli.javafx;
  requires java.desktop;
  requires com.google.zxing;
  requires com.google.zxing.javase;
  requires org.apache.commons.io;
  requires com.google.gson;
  

  uses kodex.plugininterface.ProcedurePlugin;
  uses kodex.plugininterface.Plugin;
  
  opens kodex;
  opens kodex.model;
  opens kodex.presenter to javafx.fxml;
  opens kodex.standardplugins.colorimageprocedure.presenter to javafx.fxml;
  opens kodex.standardplugins.rle.presenter to javafx.fxml;
  opens kodex.standardplugins.qrcode.presenter to javafx.fxml;
  opens kodex.standardplugins.greyscaleimageprocedure.presenter to javafx.fxml;
  opens kodex.standardplugins.bwimageprocedure.presenter to javafx.fxml;
  exports kodex;

  provides Plugin
  with ColorImageProcedurePlugin, BWImageProcedurePlugin, GreyScaleImageProcedurePlugin, TextQRCodeProcedurePlugin, TextRLEProcedurePlugin;

  provides ProcedurePlugin
  with ColorImageProcedurePlugin, BWImageProcedurePlugin, GreyScaleImageProcedurePlugin, TextQRCodeProcedurePlugin, TextRLEProcedurePlugin;
}
