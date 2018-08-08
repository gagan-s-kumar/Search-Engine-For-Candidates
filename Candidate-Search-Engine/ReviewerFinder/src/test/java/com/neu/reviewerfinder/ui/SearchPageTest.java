package com.neu.reviewerfinder.ui;

import static org.loadui.testfx.Assertions.verifyThat;
import static org.loadui.testfx.controls.Commons.hasText;

import com.neu.reviewerfinder.hibernate.HibernateSetup;
import java.util.concurrent.TimeUnit;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

public class SearchPageTest extends ApplicationTest {

  @BeforeClass
  public static void setUp() {
    HibernateSetup.getSessionFactory().openSession();
  }

  @Before
  public void setUpClass() throws Exception {
    ApplicationTest.launch(SearchAuthors.class, new String[0]);
  }

  @Override
  public void start(Stage stage) {
    stage.show();
  }

  @Test
  public void testQuickSearch1() {
    verifyThat("#title", hasText("Search Authors"));
    clickOn("#quick1");
    clickOn("#quickSearch");
    WaitForAsyncUtils.sleep(10, TimeUnit.SECONDS);
    verifyThat("#title", hasText("Search Results"));
    clickOn("#searchAuth");
  }

  @Test
  public void testQuickSearch2() {
    verifyThat("#title", hasText("Search Authors"));
    clickOn("#quick2");
    clickOn("#quickSearch");
    WaitForAsyncUtils.sleep(10, TimeUnit.SECONDS);
    verifyThat("#title", hasText("Search Results"));
    clickOn("#logout");
  }

  @Test
  public void testQuickSearch3() {
    verifyThat("#title", hasText("Search Authors"));
    clickOn("#quick3");
    clickOn("#authorNameLike").write("Frank Tip");
    clickOn("#quickSearch");
    WaitForAsyncUtils.sleep(10, TimeUnit.SECONDS);
    verifyThat("#title", hasText("Search Results"));
  }

  @Test
  public void testQuickSearch4() {
    verifyThat("#title", hasText("Search Authors"));
    clickOn("#quick4");
    clickOn("#keywords").write("MultiJava,symmetric");
    clickOn("#quickSearch");
    WaitForAsyncUtils.sleep(20, TimeUnit.SECONDS);
    verifyThat("#title", hasText("Search Results"));
    clickOn("#tableView");
    clickOn("#authorDetail");
    verifyThat("#title", hasText("Author Details"));
    clickOn("#searchAuth");
  }

  @Test
  public void testToggleButtons() {
    verifyThat("#title", hasText("Search Authors"));
    clickOn("#quick4");
    clickOn("#keywords").write("MultiJava,symmetric");
    clickOn("#quick3");
    verifyThat("#keywords", hasText(""));
    clickOn("#authorNameLike").write("Frank Tip");
    clickOn("#quick1");
    verifyThat("#authorNameLike", hasText(""));
    clickOn("#quick4");
    clickOn("#keywords").write("Multi");
    clickOn("#quick2");
    verifyThat("#keywords", hasText(""));
    clickOn("#quick3");
    clickOn("#authorNameLike").write("Frank Tip");
    clickOn("#quick4");
    verifyThat("#authorNameLike", hasText(""));
  }

  @Test
  public void testLogout() {
    verifyThat("#title", hasText("Search Authors"));
    // when:
    clickOn("#logout");
    // then:
    verifyThat("#title", hasText("Login"));
  }

  @Test
  public void testAdvancedSearch1() {
    verifyThat("#title", hasText("Search Authors"));
    clickOn("#advancedTab");
    clickOn("#checkComboBox").type(KeyCode.TAB).type(KeyCode.SPACE).clickOn("#checkComboBox");
    clickOn("#titleText").write("MultiJava,symmetric");
    clickOn("#datePicker").write("3/1/2013");
    clickOn("#minPaper").write("2");
    clickOn("#authorName").write("Craig Chambers");
    clickOn("#advancedSearch");
    WaitForAsyncUtils.sleep(20, TimeUnit.SECONDS);
    verifyThat("#title", hasText("Search Results"));
    clickOn("#tableView");
    clickOn("#authorDetail");
  }

  @Test
  public void testAdvancedSearch2() {
    verifyThat("#title", hasText("Search Authors"));
    clickOn("#advancedTab");
    clickOn("#advancedSearch");
    verifyThat("#title", hasText("Search Failed!"));
    clickOn("#ok");
  }

  @Test
  public void testAdvancedSearch3() {
    verifyThat("#title", hasText("Search Authors"));
    clickOn("#advancedTab");
    clickOn("#checkComboBox").type(KeyCode.TAB).type(KeyCode.SPACE).clickOn("#checkComboBox");
    clickOn("#datePicker").write("3/1/2013");
    clickOn("#spinner").type(KeyCode.HOME).type(KeyCode.NUMPAD3).type(KeyCode.END)
        .type(KeyCode.BACK_SPACE, 1);
    WaitForAsyncUtils.waitForFxEvents();
    clickOn("#advancedSearch");
    WaitForAsyncUtils.sleep(20, TimeUnit.SECONDS);
    verifyThat("#title", hasText("Search Results"));
    clickOn("#authorDetail");
    verifyThat("#title", hasText("Search Failed!"));
    clickOn("#ok");
  }

  @Test
  public void testAdvancedSearchWrongInputs() {
    verifyThat("#title", hasText("Search Authors"));
    clickOn("#advancedTab");
    clickOn("#minPaper").write("abc");
    clickOn("#advancedSearch");
    verifyThat("#title", hasText("Search Failed!"));
  }

  @Test
  public void testAdvancedSearchFilterOnly() {
    verifyThat("#title", hasText("Search Authors"));
    clickOn("#advancedTab");
    clickOn("#minPaper").write("23");
    clickOn("#spinner").type(KeyCode.HOME).type(KeyCode.NUMPAD3).type(KeyCode.END)
        .type(KeyCode.BACK_SPACE, 1);
    clickOn("#advancedSearch");
    WaitForAsyncUtils.sleep(10, TimeUnit.SECONDS);
    verifyThat("#title", hasText("Search Failed!"));
  }

  @Test
  public void testAdvancedSearch4() {
    verifyThat("#title", hasText("Search Authors"));
    clickOn("#advancedTab");
    clickOn("#checkComboBox").type(KeyCode.TAB).type(KeyCode.SPACE).clickOn("#checkComboBox");
    clickOn("#datePicker").write("3/1/2013");
    clickOn("#spinner").type(KeyCode.HOME).type(KeyCode.NUMPAD3).type(KeyCode.END)
        .type(KeyCode.BACK_SPACE, 1);
    WaitForAsyncUtils.waitForFxEvents();
    clickOn("#advancedSearch");
    WaitForAsyncUtils.sleep(10, TimeUnit.SECONDS);
    verifyThat("#title", hasText("Search Results"));
    clickOn("#tableView");
    clickOn("#authorDetail");
    verifyThat("#title", hasText("Author Details"));
    clickOn("#logout");
  }
}
