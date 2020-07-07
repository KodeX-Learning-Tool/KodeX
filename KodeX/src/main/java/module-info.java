module kodex.KodeX {
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.base;
	requires org.kordamp.ikonli.javafx;
    requires org.controlsfx.controls;

    opens kodex.presenter to javafx.fxml;
    exports kodex;
}