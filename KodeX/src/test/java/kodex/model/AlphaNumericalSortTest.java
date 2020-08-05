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

class AlphaNumericalSortTest {

  private static BWImageProcedurePlugin bw = new BWImageProcedurePlugin();
  private static GreyScaleImageProcedurePlugin gs = new GreyScaleImageProcedurePlugin();
  private static TextQRCodeProcedurePlugin qr = new TextQRCodeProcedurePlugin();
  private static TextRLEProcedurePlugin rle = new TextRLEProcedurePlugin();
  private static ColorImageProcedurePlugin cl = new ColorImageProcedurePlugin();

  private static ObservableList<ProcedurePlugin> actual;
  private static ObservableList<ProcedurePlugin> expected;

  @BeforeAll
  static void init() {
    actual = FXCollections.observableArrayList(bw, gs, qr, rle, cl);
    expected = FXCollections.observableArrayList(cl, gs, rle, qr, bw);

    AlphaNumericalSort sort = new AlphaNumericalSort();
    sort.filterProcedures(actual);
  }

  @Test
  void checkSize() {
    assertTrue(actual.size() == expected.size());
  }

  @Test
  void checkContent() {
    boolean same = true;
    for (int i = 0; i < expected.size(); i++) {
      if (actual.get(i) != expected.get(i)) {
        same = false;
      }
    }
    assertTrue(same);
  }
  
  @AfterAll
  static void clean() {
    actual.clear();
    expected.clear();
  }

}
