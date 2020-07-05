module kodex.KodeX {
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.graphics;

    opens kodex.presenter to javafx.fxml;
    exports kodex;
}