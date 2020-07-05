package kodex.presenter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Separator;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import kodex.model.Help;

/**
 * This presenter is responsible for the Help page. On the Help page the program
 * displays some information about itself and an FAQ.
 * 
 * @author Yannick Neubert
 * @author Raimon Gramlich
 * 
 * @version 1.0
 */
public class HelpPresenter extends Presenter {
	
	/** The VBox which displays the FAQ items.*/
	@FXML
	private VBox faqBox;
	
	/** The TextFlow which displays the information about this program.*/
	@FXML
	private TextFlow infoBox;
	
	/** The Text which displays a part of the information about this program .*/
	@FXML
	private Text infoText;
	
	/** The List of FAQ items in form of a TitledPane.*/
	private ArrayList<TitledPane> faq = new ArrayList<>();
	
	/** The List of answers.*/
	private List<String> questions = new ArrayList<>();
	
	/** The List of questions.*/
	private List<String> answers = new ArrayList<>();
	
	/** The information text.*/
	private String text;
		
    /** The instance of the Help class which loads the information as well as the questions and answers.  */
    private Help help;

    /**
     * Creates a new HelpPresenter with a reference to a PresenterManger.
     * 
     * @param pm : The reference to the PresenterManager
     */
    public HelpPresenter(PresenterManager pm) {
    	super(pm, "helppage");
    	
    	help = new Help();
    }
    
    /**
     * Initializes the view-object created by the FXMLLoader.
     * It gets the Strings which it displays as information text or FAQ items.
     */
	@FXML
	private void initialize() {	
		// get the lists of questions and answers
		questions = help.getQuestions();
		answers = help.getAnswers();
		
		// iterate over the lists in parallel while adding them to the list of FAQ items
		Iterator<String> qIt = questions.iterator();
		Iterator<String> aIt = answers.iterator();
		
		while (qIt.hasNext() && aIt.hasNext()) {
			String question = qIt.next();
			String answer = aIt.next();
			
			faq.add(createFAQItems(question, answer));
		}
		
		// get the information about the program and display it
		for (String info : help.getInfo()) {
			text = text.concat(info + "\n");
		}
		
		infoText.setText(text);
		
		// add all FAQ items to the view
		for (TitledPane item: faq) {
			faqBox.getChildren().add(item);
			faqBox.getChildren().add(new Separator());
		}
	}
	
	/**
	 * Creates FAQ items by creating a displayable object out of a set of a question and an answer.
	 * 
	 * @param question : the question
	 * @param answer : the answer to the question
	 * @return TitledPane - the combined FAQ item
	 */
	private TitledPane createFAQItems(String question, String answer) {
		// create the content (answer) of the whole FAQ item		
		TextFlow answerBlock = new TextFlow(new Text(answer));
		answerBlock.getStyleClass().add("help-page__faq__element__content");
		answerBlock.setPadding(new Insets(0,0,0,50));
		
		// create the FAQ item and set the question as the title and the answer as the content
		TitledPane item = new TitledPane("Q: " + question, answerBlock);
		item.getStyleClass().add("help-page__faq__element");
		item.setExpanded(false);
		return item;
	}
}