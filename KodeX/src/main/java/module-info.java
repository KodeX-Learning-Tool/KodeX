module kodex.KodeX {
    requires javafx.controls;
    requires javafx.fxml;

    opens kodex.KodeX to javafx.fxml;
    exports kodex.KodeX;
}