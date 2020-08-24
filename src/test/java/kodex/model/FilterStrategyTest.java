package kodex.model;

import static org.junit.jupiter.api.Assertions.assertTrue;

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

class FilterStrategyTest {

  private static BWImageProcedurePlugin bw = new BWImageProcedurePlugin();
  private static GreyScaleImageProcedurePlugin gs = new GreyScaleImageProcedurePlugin();
  private static TextQRCodeProcedurePlugin qr = new TextQRCodeProcedurePlugin();
  private static TextRLEProcedurePlugin rle = new TextRLEProcedurePlugin();
  private static ColorImageProcedurePlugin cl = new ColorImageProcedurePlugin();

  private static ObservableList<ProcedurePlugin> actual;
  
  private FilterStrategy alphabetic = new AlphaNumericalSort();
  private FilterStrategy grade = new LabelSort();
  private FilterStrategy relevancy = new RelevancySort();


  @BeforeAll
  static void init() {
    actual = FXCollections.observableArrayList(bw, gs, qr, rle, cl);
  }
  
  @Test
  void testInstances() {
    assertTrue(alphabetic instanceof AlphaNumericalSort && grade instanceof LabelSort
        && relevancy instanceof RelevancySort);
  }
  
  //Only tests size. The functionality is checked in the
  //respective test classes of the sorting process
  @Test
  void testFilterProceduresAlphabetic() {
    alphabetic.filterProcedures(actual);
    assertTrue(actual.size() == 5);
  }
  
  @Test
  void testFilterProceduresGrade() {
    grade.filterProcedures(actual);
    assertTrue(actual.size() == 5);
  }
  
  @Test
  void testFilterProceduresRelevancy() {
    relevancy.filterProcedures(actual);
    assertTrue(actual.size() == 0);
  }

}
