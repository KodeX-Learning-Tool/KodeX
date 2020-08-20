package kodex.presenter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Field;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import javafx.stage.Stage;
import kodex.model.SideMenuTypes;

@ExtendWith(ApplicationExtension.class)
class PresenterManagerTest {

  private PresenterManager pm;

  @Start
  void setUp(Stage stage) {
    PresenterManager.setRootStage(stage);

    pm = new PresenterManager();
  }

  @Test
  void setSMPonExitCalled() {
    SideMenuPresenter smp = Mockito.mock(SideMenuPresenter.class);

    pm.setSideMenuPresenter(smp);

    SideMenuPresenter newSMP = Mockito.mock(SideMenuPresenter.class);

    pm.setSideMenuPresenter(newSMP);

    Mockito.verify(smp).onExit();
  }

  @Test
  void setNullSMPonExitNotCalled() throws Exception {

    SideMenuPresenter oldSMP = Mockito.mock(SideMenuPresenter.class);
    SideMenuPresenter newSMP = null;

    pm.setSideMenuPresenter(oldSMP);
    pm.setSideMenuPresenter(newSMP);

    Mockito.verify(oldSMP, Mockito.never()).onExit();
  }

  @Test
  void setSMPTypeChanged() throws Exception {

    SideMenuPresenter smp = Mockito.mock(SideMenuPresenter.class);

    pm.setSideMenuPresenter(smp);

    Field sideMenuPresenter = pm.getClass().getDeclaredField("sideMenuPresenter");
    sideMenuPresenter.setAccessible(true);

    Mockito.verify(smp).getView();
    assertEquals((SideMenuPresenter) sideMenuPresenter.get(pm), smp);
  }

  @Test
  void updateSMView() {
    SideMenuPresenter smp = Mockito.mock(SideMenuPresenter.class);

    pm.setSideMenuPresenter(smp);
    pm.updateSideMenuView(SideMenuTypes.MINI_MENU);

    Mockito.verify(smp).changeSideMenuType(Mockito.any(SideMenuTypes.class));
  }

  @Test
  void testUpdatePresenter() {
    Presenter presenter = Mockito.mock(Presenter.class);
    pm.updatePresenter(presenter);
  }

  @Test
  void testUpdatePresenterOnExitCall() {
    Presenter oldPresenter = Mockito.mock(Presenter.class);
    pm.updatePresenter(oldPresenter);

    Presenter newPresenter = Mockito.mock(Presenter.class);
    pm.updatePresenter(newPresenter);

    Mockito.verify(oldPresenter).onExit();
  }

  @Test
  void testUpdatePresenterNullOnExitCall() throws Exception {

    Presenter presenter = Mockito.mock(Presenter.class);
    pm.updatePresenter(presenter);

    Field currentPresenter = pm.getClass().getDeclaredField("currentPresenter");
    currentPresenter.setAccessible(true);

    assertEquals((Presenter) currentPresenter.get(pm), presenter);
  }

  @Test
  void testStop() {
    Presenter presenter = Mockito.mock(Presenter.class);
    pm.updatePresenter(presenter);

    SideMenuPresenter smp = Mockito.mock(SideMenuPresenter.class);
    pm.setSideMenuPresenter(smp);

    pm.stop();

    Mockito.verify(presenter).onExit();
    Mockito.verify(smp).onExit();
  }
}
