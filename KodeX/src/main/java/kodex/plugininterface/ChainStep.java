package kodex.plugininterface;

/**
 * Diese Klasse stellt einen abstrakten Schritt zwischen zwei Stufen dar.
 * Sie beinhaltet die Funktionalität um den Content zwischen zwei explizit
 * festgelegten Stufen dekodieren bzw.kodieren zu können.
 * Konkrete Schritte müssen diese Klasse erweitern.
 * 
 * @author Patrick Spiesberger
 * 
 * @version 1.0
 *
 */
public abstract class ChainStep {

    /**
     * Konstruktor der Klasse ChainStep
     */
    public ChainStep() {}

    /**
     * Kodiert den Content von einer Stufe und setzt ihn in den Content ihrer nächsten Stufe
     * @param left : der Content der Stufe der kodiert werden soll
     * @param right : der Content der Stufe welcher gesetzt werden soll
     */
    public abstract void encode(Content left, Content right);

    /**
     * @param right : der Content der Stufe der dekodiert werden soll
     * @param left : der Content der Stufe welcher gesetzt werden soll
     */
    public abstract void decode(Content right, Content left);

}