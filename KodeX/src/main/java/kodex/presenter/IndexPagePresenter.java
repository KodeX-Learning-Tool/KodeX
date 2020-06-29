package kodex.presenter;

import java.util.List;

import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import kodex.model.Filter;
import kodex.plugininterface.ProcedurePlugin;

/**
 * 
 */
public class IndexPagePresenter extends Presenter {

    /**
     * Default constructor
     */
    public IndexPagePresenter() {
    }

    /**
     * 
     */
    private List<ProcedurePlugin> shownProcedures;





    /**
     * @param pm 
     * @param pf
     */
    public void IndexPagePresenter(PresenterManager pm, PresenterFactory pf) {
        // TODO implement here
    }

    /**
     * 
     */
    public void handleProcedureSelected() {
        // TODO implement here
    }

    /**
     * 
     */
    public void handleSearch() {
        // TODO implement here
    }

    /**
     * 
     */
    public void handleFilter() {
        // TODO implement here
    }

    /**
     * @param searchTerm
     */
    private void searchProcedures(String searchTerm) {
        // TODO implement here
    }

    /**
     * @param filterTerm
     */
    private void filterProcedures(Filter filterTerm) {
        // TODO implement here
    }

    /**
     * 
     */
    private void createProcedureButtons() {
        // TODO implement here
    }

    /**
     * @param procedure
     */
    private void addProcedureButton(Button procedure) {
        // TODO implement here
    }

	@Override
	public AnchorPane getView() {
		// TODO Auto-generated method stub
		return null;
	}

}