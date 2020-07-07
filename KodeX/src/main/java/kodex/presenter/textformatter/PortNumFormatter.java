package kodex.presenter.textformatter;

import java.util.function.UnaryOperator;

import javafx.scene.control.TextFormatter;
import javafx.util.converter.IntegerStringConverter;

public class PortNumFormatter {

    /*
     * UnaryOperator to filter an occurring change in the control this Formatter is
     * used in.
     */
    private static UnaryOperator<TextFormatter.Change> filter = new UnaryOperator<TextFormatter.Change>() {
        @Override
        public TextFormatter.Change apply(TextFormatter.Change change) {

            if (!change.getControlNewText().matches("\\d{0,5}")) {
                // change is too long or uses invalid characters
                return null;
            }
            return change;
        }
    };

    /*
     * Constructor is private because this is a utility class.
     */
    private PortNumFormatter() {
    }

    /**
     * Creates a new port number TextFormatter.
     * 
     * @return The TextFormatter.
     */
    public static TextFormatter<Integer> createTextFormatter() {
        return new TextFormatter<>(new IntegerStringConverter(), 0, filter);
    }
}
