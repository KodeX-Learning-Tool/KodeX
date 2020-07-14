module kodex.KodeX {
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.base;
	requires org.kordamp.ikonli.javafx;
    requires org.controlsfx.controls;
    
    uses kodex.plugininterface.ProcedurePlugin;
    uses kodex.plugininterface.Pluginable;
    
    opens kodex.presenter to javafx.fxml;
    exports kodex;
}