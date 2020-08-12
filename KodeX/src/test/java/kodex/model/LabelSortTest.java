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

class LabelSortTest {

  private static BWImageProcedurePlugin bw = new BWImageProcedurePlugin(); //Label: 7
  private static GreyScaleImageProcedurePlugin gs = new GreyScaleImageProcedurePlugin(); //Label: 7
  private static TextQRCodeProcedurePlugin qr = new TextQRCodeProcedurePlugin(); //Label: 8
  private static TextRLEProcedurePlugin rle = new TextRLEProcedurePlugin(); //Label: 8
  private static ColorImageProcedurePlugin cl = new ColorImageProcedurePlugin(); //Label: 7

  private static ObservableList<ProcedurePlugin> actual;
  private static ObservableList<ProcedurePlugin> expected;


  @BeforeAll
  static void init() {
    actual = FXCollections.observableArrayList(bw, gs, qr, rle, cl);
    expected = FXCollections.observableArrayList(cl, bw, gs, qr, rle);

    LabelSort sort = new LabelSort();
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
