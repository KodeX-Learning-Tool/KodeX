package kodex.presenter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Separator;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import kodex.model.Help;

/**
 * Diese Klasse verwaltet die Haupt-Presenter und hat die Referenz zu dem Fenster,
 * das angezeigt werden soll. Sie baut aus den einzelnen View-Objekten, die sie anfordert,
 * eine zusammenhängende View für das Programm und zeigt sie im Fenster an.
 * 
 * @author Yannick Neubert
 * 
 * @version 1.0
 */
public class HelpPresenter extends Presenter {
	
	@FXML
	private VBox faqBox;
	
	@FXML
	private TextFlow infoBox;
	
	@FXML
	private Text infoText;
	
	private ArrayList<TitledPane> faq = new ArrayList<>();
	
	private List<String> questions = new ArrayList<>();
	
	private List<String> answers = new ArrayList<>();
	
	private String text;
		
    /**
     * Die Instanz der Hilfe Klasse mit dem FAQ
     */
    private Help help;

    /**
     * Erstellt einen neuen HelpPresenter mit einer Referenz zu einem PresenterManger.
     * @param pm : Der PresenterManager
     */
    public HelpPresenter(PresenterManager pm) {
    	super(pm, "helppage");
    	
    	help = new Help();
    }
    
	@FXML
	private void initialize() {	
		questions = help.getQuestions();
		answers = help.getAnswers();
		
		Iterator<String> qIt = questions.iterator();
		
		Iterator<String> aIt = answers.iterator();
		
		while (qIt.hasNext() && aIt.hasNext()) {
			String question = qIt.next();
			String answer = aIt.next();
			
			faq.add(createFAQElement(question, answer));
		}
		
		
		for (String info : help.getInfo()) {
			text = text.concat(info + "\n");
		}
		
		infoText.setText(text);
		
		for (TitledPane element: faq) {
			faqBox.getChildren().add(element);
			faqBox.getChildren().add(new Separator());
		}
	}
	
	private TitledPane createFAQElement(String question, String answer) {
		TextFlow answerBlock = new TextFlow(new Text(answer));
		answerBlock.getStyleClass().add("help-page__faq__element__content");
		answerBlock.setPadding(new Insets(0,0,0,50));
		TitledPane element = new TitledPane("Q: " + question, answerBlock);
		element.getStyleClass().add("help-page__faq__element");
		element.setExpanded(false);
		return element;
	}
}