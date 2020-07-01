package kodex.plugininterface;

import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import kodex.presenter.IPresenter;

/**
 * Diese Klasse stellt eine abstrakte Stufe dar. Über eine Stufe kann der Content
 * exportiert und in der View markiert werden, sowie der View ihres Contents,
 * als auch der, in ihr gespeicherten, Presenter angefordert werden.
 * Eine Stufe eines Verfahrens muss diese Klasse erweitern.
 * 
 * @author Patrick Spiesberger
 * 
 * @version 1.0 
 *
 */
public abstract class ChainLinkPresenter implements IPresenter {

	
	/* Die Stufe, welche nach dieser Stufe in der Verfahrenskette kommt */
    protected ChainLinkPresenter next;

    /* Die Stufe, welche vor dieser Stufe in der Verfahrenskette kommt */
    protected ChainLinkPresenter previous;

    /* Die Daten, welche diese Stufe beinhaltet */
    protected Content content;

    /* 
     * Der Schritt in der Verfahrenskette, mit welchem man den Inhalt dieser 
     * Stufe in den Inhalt für die vorherige Stufe umrechnen kann 
     */
    protected ChainStep previousStep;

    /* 
     * Der Schritt in der Verfahrenskette, mit welchem man den Inhalt dieser 
     * Stufe in den Inhalt für die nächste Stufe umrechnen kann 
     */
    protected ChainStep nextStep;


    /**
     * Konstrukor der Klasse ChainLinkPresenter.
     * Setzt die vorherige Stufe, sowie den nächsten und vorherigen Schritt.
     * Die nächste Stufe wird nicht gesetzt, da diese beim Initialisieren einer
     * Kette noch nicht bekannt ist.
     * @param previous : vorherige Stufe
     * @param previousStep : vorheriger Schritt
     * @param nextStep : nächster Schritt
     */
    public ChainLinkPresenter(ChainLinkPresenter previous, ChainStep previousStep, ChainStep nextStep) {
        this.previous = previous;
        this.previousStep = previousStep;
        this.nextStep = nextStep;
    }

    
    /**
     * Gibt den ChainLinkEditPresenter für diese Stufe zurück
     * @return ChainLinkEditPresenter für diese Stufe
     */
    public ChainLinkEditPresenter getChainLinkEditPresenter() {
        return null;
    }

    /**
     * Gibt die View des Headers für diese Stufe zurück, ruft nur getView() auf
     * dem ChainLinkHeaderPresenter dieser Stufe auf und gibt das Ergebnis zurück.
     * @return View des ChainLinkHeaderPresenter dieser Stufe
     */
    public Pane getChainLinkHeaderView() {
        return null;
    }

    /**
     * Setzt den Content für diese Stufe
     * @param content : zu setzender Content
     */
    public void setContent(Content content) {
        this.content = content;
    }

    /**
     * Gibt den Content dieser Stufe zurück
     * @return Content dieser Stufe
     */
    public Content getContent() {
        return this.content;
    }

    /**
     * Updatet rekursiv den Content aller Stufen der Verfahrenskette von dieser
     * Stufe aus. Muss immer aufgerufen werden wenn der Content der Stufe
     * bearbeitet wurde
     */
    public void updateChain() {
    	updateNextChainLink();
    	updatePrevChainLink();
    }

    /**
     * Aktualisiert die nächste Stufe
     */
    public void updateNextChainLink() {
    	next.updateChain();
    	if (next.getNext() != null) {
    		next = next.getNext();
    	}
    }

    /**
     * Aktualisiert die vorherige Stufe
     */
    public void updatePrevChainLink() {
    	previous.updateChain();
    	if (previous.getPrev() != null) {
    		prev = previous.getPrev();
    	}
    }

    /**
     * Markiert mit Hilfe der übergebenen ID das entsprechende Element
     * im View dieser Stufe
     * @param id : Die ID um das zu markierende Element zu identifizieren
     */
    protected abstract void mark(int id);

    /**
     * Wird aufgerufen, wenn etwas im View dieser Stufe angeklickt und somit 
     * markiert wird. Markiert das zur id gehörende Element im View und 
     * sorgt dafür, dass rekursiv in allen anderen Stufen des Verfahrens das
     * entsprechende Element markiert wird.
     */
    public void handleMark() {
        int id = calculateID();
        if (id != -1) {
        	mark(id);
        	markPrev(id);
        	markNext(id);
        }
    }
    
    
    /**
     * Berechnet die ID, welche auf dem Content angeklickt wurde
     * @return : ID, welche das zu markierende Element, repräsentiert
     */
    protected int calculateID() {
		return -1;
    }

    /**
     * Markiert das, der ID entsprechende, Element in der vorherigen Stufe und 
     * somit rekursiv in allen vorherigen Stufen
     * @param id : repräsentierende ID
     */
    public void markPrev(int id) {
        previous.mark(id);
        if (previous.getPrev() != null) {
        	previous = previous.getPrev();
        	previous.markPrev(id);
        }
    }

    /**
     * Markiert das, der id entsprechende, Element in der nächsten Stufe und 
     * somit rekursiv in allen weiteren Stufen
     * @param id : repräsentierende ID
     */
    public void markNext(int id) {
        next.mark(id);
        if (next.getNext() != null) {
        	next = next.getNext();
        	next.markNext(id);
        }
    }

    /**
     * Setzt die nächste Stufe auf die gewünschte nächste Stufe der Kette
     * @param next : nächste Stufe
     */
    public void setNext(ChainLinkPresenter next) {
        this.next = next;
    }

    /**
     * Gibt die nächste Stufe zurück
     * @return nächste Stufe
     */
    public ChainLinkPresenter getNext() {
        return this.next;
    }

    /**
     * Setzt die vorherige Stufe auf die gewünschte vorherige Stufe der Kette
     * @param prev : vorherige Stufe
     */
    public void setPrev(ChainLinkPresenter prev) {
        this.previous = prev;
    }

    /**
     * Gibt die vorherige Stufe zurück
     * @return vorherige Stufe
     */
    public ChainLinkPresenter getPrev() {
        return this.previous;
    }

    /**
     * Gibt das Symbol zurück, welches in der Übersicht zur Repräsentation
     * dieser Stufe verwendet wird.
     * @return Symbol, welches diese Stufe repäsentiert
     */
    public Image getSymbol() {
        // TODO return Standard Bild
        return null;
    }

    /**
     * Wird aufgerufen, wenn der Benutzer eine Stufe exportieren möchte.
     * Öffnet einen FileChooser, mit welchem die Datei zum speichern angegeben
     * wird, um diese dann an den Content zum exportieren weiter zu geben.
     */
    public void handleExport() {
        // TODO Fehlerfenster anzeigen oder Export ausblenden
    	System.out.println("Es wurde kein Export definiert.");
    	System.out.println("Falls du denkst, dass dies ein Fehler ist,");
    	System.out.println("wende dich bitte an den Ersteller des Plugins.");
    }

    /**
     * Gibt die View des Contents zurück
     * @return View in Form eines AnchorPane, in dem der Inhalt angezeigt wird
     */
    public abstract AnchorPane getView();

}