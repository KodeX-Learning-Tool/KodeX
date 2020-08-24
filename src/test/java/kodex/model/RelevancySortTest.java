package kodex.model;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import kodex.plugininterface.ProcedurePlugin;
import kodex.standardplugins.bwimageprocedure.BWImageProcedurePlugin;
import kodex.standardplugins.colorimageprocedure.ColorImageProcedurePlugin;
import kodex.standardplugins.greyscaleimageprocedure.GreyScaleImageProcedurePlugin;
import kodex.standardplugins.qrcode.TextQRCodeProcedurePlugin;
import kodex.standardplugins.rle.TextRLEProcedurePlugin;

class RelevancySortTest {

  private static BWImageProcedurePlugin bw = new BWImageProcedurePlugin();
  private static GreyScaleImageProcedurePlugin gs = new GreyScaleImageProcedurePlugin();
  private static TextQRCodeProcedurePlugin qr = new TextQRCodeProcedurePlugin();
  private static TextRLEProcedurePlugin rle = new TextRLEProcedurePlugin();
  private static ColorImageProcedurePlugin cl = new ColorImageProcedurePlugin();

  private static ObservableList<ProcedurePlugin> actual;
  
  private static FilterStrategy sort;
  
  private static IndexPage index = new IndexPage();


  @BeforeAll
  static void init() {
    actual = FXCollections.observableArrayList(bw, gs, qr, rle, cl);
    sort = new RelevancySort();
  }

  @Test
  void checkSizeNull() {
    sort.filterProcedures(actual);
    assertTrue(actual.size() == 0);
  }
  
  @Test
  void checkSizeClicked() {
    index.getValueProcedure().clear();
    index.increaseRelevancy(gs);
    index.increaseRelevancy(rle);
    index.increaseRelevancy(cl);
    sort.filterProcedures(actual);
    assertTrue(actual.size() == 3);
  }

  @Test
  void checkContent() {
    index.getValueProcedure().clear();
    index.increaseRelevancy(gs);
    index.increaseRelevancy(gs);
    index.increaseRelevancy(bw);
    new RelevancySort().filterProcedures(actual);
    assertTrue(actual.size() == 2 && actual.get(0).toString().equals(gs.toString())
        && actual.get(1).toString().equals(bw.toString()));
  }
  
  @AfterAll
  static void clean() {
    actual.clear();
    index.getValueProcedure().clear();
  }

}
