import kodex.plugininterface.Pluginable;
import kodex.plugininterface.ProcedurePlugin;
import kodex.standardplugins.colorimageprocedure.ColorImageProcedurePlugin;

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
    opens kodex.standardplugins.colorimageprocedure.presenter to javafx.fxml;
    exports kodex;
    
    provides Pluginable
    	with ColorImageProcedurePlugin;
    
    provides ProcedurePlugin
    	with ColorImageProcedurePlugin;
}
