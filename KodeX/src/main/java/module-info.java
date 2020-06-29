module kodex.KodeX {
    requires javafx.controls;
    requires javafx.fxml;

    opens kodex to javafx.fxml;
    exports kodex;
}