package com.neu.reviewerfinder.ui;

import static org.loadui.testfx.Assertions.verifyThat;
import static org.loadui.testfx.controls.Commons.hasText;

import java.util.concurrent.TimeUnit;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

public class LoginPageTest extends ApplicationTest {

  @Before
  public void setUpClass() throws Exception {
    ApplicationTest.launch(Login.class, new String[0]);
  }

  @Override
  public void start(Stage stage) {
    stage.show();
  }

  @Test
  public void testValidLogin() {
    verifyThat("#title", hasText("Login"));
    // when:
    clickOn("#username").write("admin");
    clickOn("#password").write("admin");
    clickOn("#btnLogin");
    WaitForAsyncUtils.sleep(20, TimeUnit.SECONDS);
    // then:
    verifyThat("#title", hasText("Search Authors"));
  }

  @Test
  public void testInvalidLogin() {
    verifyThat("#title", hasText("Login"));
    // when:
    clickOn("#username").write("admin");
    clickOn("#password").write("test123");
    clickOn("#btnLogin");
    WaitForAsyncUtils.sleep(10, TimeUnit.SECONDS);
    // then:
    verifyThat("#title", hasText("Login Failed!"));
    clickOn("#ok");
  }

  @Test
  public void testNullUsernameAndPassword() {
    verifyThat("#title", hasText("Login"));
    // when:
    clickOn("#btnLogin");
    // then:
    verifyThat("#title", hasText("Login Failed!"));
    clickOn("#ok");
  }

  @Test
  public void testEmptyUsernameAndPassword() {
    verifyThat("#title", hasText("Login"));
    // when:
    clickOn("#username").write("");
    clickOn("#password").write("");
    clickOn("#btnLogin");
    // then:
    verifyThat("#title", hasText("Login Failed!"));
    clickOn("#ok");
  }
}
