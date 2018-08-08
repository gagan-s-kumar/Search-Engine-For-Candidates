package com.neu.reviewerfinder.ui;

import com.neu.reviewerfinder.queryengine.AuthorDetails;
import com.neu.reviewerfinder.queryengine.Criteria;
import com.neu.reviewerfinder.queryengine.CriteriaImpl;
import com.neu.reviewerfinder.queryengine.QueryEngine;
import com.neu.reviewerfinder.queryengine.QueryEngineImpl;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;

import javafx.collections.ObservableList;
import org.controlsfx.control.CheckComboBox;
import javafx.collections.FXCollections;
import javafx.collections.*;

/**
 * This is the class for Searching the authors It returns the list of authors
 * 
 * @author Surekha, Kaushik
 */
public class SearchAuthors extends Application {

  private static List<AuthorDetails> authorData = new ArrayList<AuthorDetails>();
  int i;

  public SearchAuthors(int i) {
    this.i = i;
  }

  public SearchAuthors() {
    this.i = 1;
  }

  private QueryEngine qe = null;

  /**
   * This method does a Quick Search to get the list of authors based upon 1) OOPSLA and year since
   * 2010 2) OOPSLA or ECOOP, at least 2 papers published and not served on committee for two
   * consecutive years 3) Author name 4) Keywords in published paper titles
   * 
   * @param group
   * @param authorName
   * @param keywordStr
   * @param loadingIndicator
   * @param primaryStage
   */
  private void quickSearch(ToggleGroup group, String authorName, String keywordStr,
      ProgressIndicator loadingIndicator, Stage primaryStage) {
    if (loadingIndicator.isVisible()) {
      return;
    }

    loadingIndicator.setVisible(true);
    loadingIndicator.getParent().setDisable(true);

    Task<Boolean> quickSearch = new Task<Boolean>() {
      {
        setOnSucceeded(workerStateEvent -> {
          if (getValue()) {
            SearchResults ui = new SearchResults(1);
            ui.start(primaryStage);
            loadingIndicator.setVisible(false);
            loadingIndicator.getParent().setDisable(false);
          } else {
            SearchError sa = new SearchError("Please enter valid input", 1);
            sa.start(primaryStage);
          }
        });

        setOnFailed(workerStateEvent -> getException().printStackTrace());
      }

      /**
       * This method returns the list of authors based upon query number
       */
      @Override
      protected Boolean call() throws Exception {
        int selected = (int) group.getSelectedToggle().getUserData();
        if (selected == 1) {
          ArrayList<String> conferenceIds1 = new ArrayList<String>();
          conferenceIds1.add("OOPSLA");
          Criteria q1 = new CriteriaImpl();
          q1.setQueryNum(1);
          q1.setPublishers(conferenceIds1);
          q1.setYearSince(2010);
          authorData = qe.getAuthors(q1);
        } else if (selected == 2) {
          ArrayList<String> conferenceIds1 = new ArrayList<String>();
          conferenceIds1.add("OOPSLA");
          conferenceIds1.add("ecoop");
          Criteria q2 = new CriteriaImpl();
          q2.setQueryNum(2);
          q2.setPublishers(conferenceIds1);
          q2.setMinimumPapersPublished(2);
          q2.setConsecutiveYearConstraint(2);
          authorData = qe.getAuthors(q2);
        } else if (selected == 3) {
          Criteria q3 = new CriteriaImpl();
          q3.setQueryNum(3);
          if (authorName == null || authorName.isEmpty() || authorName.contains("%")) {
            return false;
          }
          q3.setAuthorName(authorName);
          authorData = qe.getAuthors(q3);
        } else if (selected == 4) {
          ArrayList<String> keywords;

          Criteria q4 = new CriteriaImpl();
          q4.setQueryNum(4);
          if (keywordStr != null && !keywordStr.isEmpty()) {
            keywords = new ArrayList<String>(Arrays.asList(keywordStr.split(",")));
            q4.setTitleKeywords(keywords);
          }

          if ((q4.getTitleKeywords() == null || q4.getTitleKeywords().isEmpty())) {
            return false;
          }
          authorData = qe.getAuthors(q4);
        } else {
          return false;
        }
        return true;
      }
    };

    Thread quickSearchThread = new Thread(quickSearch, "quick-search");
    quickSearchThread.setDaemon(true);
    quickSearchThread.start();
  }

  /**
   * This method does an Advanced Search to get the list of authors based upon 1) Journal/conference
   * name 2) Year published 3) Years served on committee 4) Keywords in published paper titles 5)
   * Minimum papers published 6) Author name
   * 
   * @param pubStr
   * @param titleStr
   * @param dateSince
   * @param minPaper
   * @param consecutiveYear
   * @param authorName
   * @param loadingIndicator
   * @param primaryStage
   */
  private void advancedSearch(ArrayList<String> list, String titleStr, LocalDate dateSince,
      String minPaper, Integer consecutiveYear, String authorName,
      ProgressIndicator loadingIndicator, Stage primaryStage) {
    if (loadingIndicator.isVisible()) {
      return;
    }

    loadingIndicator.setVisible(true);
    loadingIndicator.getParent().setDisable(true);

    Task<Boolean> advancedSearch = new Task<Boolean>() {
      {
        setOnSucceeded(workerStateEvent -> {
          if (getValue()) {
            SearchResults ui = new SearchResults(2);
            ui.start(primaryStage);
            loadingIndicator.setVisible(false);
            loadingIndicator.getParent().setDisable(false);
          } else {
            SearchError sa =
                new SearchError("Please enter valid input/ Select at least one criteria", 2);
            sa.start(primaryStage);
          }
        });

        setOnFailed(workerStateEvent -> getException().printStackTrace());
      }

      /**
       * This method returns the list of authors based upon search values
       */
      @Override
      protected Boolean call() throws Exception {
        Criteria c = new CriteriaImpl();
        c.setQueryNum(5);
        if (list != null && !list.isEmpty()) {
          c.setPublishers(list);
        }

        if (titleStr != null && !titleStr.isEmpty()) {
          ArrayList<String> keywords = new ArrayList<String>(Arrays.asList(titleStr.split(",")));
          c.setTitleKeywords(keywords);
        }

        if (dateSince != null) {
          String date_str = String.valueOf(dateSince);
          int date_value = Integer.parseInt(date_str.substring(0, 4));
          c.setYearSince(date_value);
        }

        if (consecutiveYear != 0) {
          c.setConsecutiveYearConstraint(consecutiveYear);
        }

        if (minPaper != null && !minPaper.isEmpty()) {
          try {
            int mini_num = Integer.parseInt(minPaper);
            c.setMinimumPapersPublished(mini_num);
          } catch (NumberFormatException e) {
            return false;
          }
        }

        if (authorName != null && !authorName.isEmpty() && !authorName.contains("%")) {
          c.setAuthorName(authorName);
        } else if (authorName.contains("%")) {
          return false;
        }
        // Minimum Number and Consecutive Year Constraint are just
        // filters
        if ((c.getTitleKeywords() == null || c.getTitleKeywords().isEmpty())
            && (c.getPublishers() == null || c.getPublishers().isEmpty())
            && (c.getAuthorName() == null || c.getAuthorName().isEmpty()) &&
        // c.getMinimumPapersPublished() == Integer.MAX_VALUE &&
        // c.getConsecutiveYearConstraint() == Integer.MAX_VALUE &&
        c.getYearSince() == Integer.MAX_VALUE) {
          return false;
        } else {
          try {
            authorData = qe.getAuthors(c);
            return true;
          } catch (Exception e) {
            e.printStackTrace();
            return false;
          }
        }
      }
    };

    Thread advancedSearchThread = new Thread(advancedSearch, "advanced-search");
    advancedSearchThread.setDaemon(true);
    advancedSearchThread.start();
  }

  /**
   * This method displays the Search Authors page
   * 
   * @param primaryStage
   */
  @Override
  public void start(Stage primaryStage) {
    qe = new QueryEngineImpl();
    primaryStage.setTitle("Reviewer Finder");
    StackPane root = new StackPane();
    final ProgressIndicator loadingIndicator = new ProgressIndicator();
    loadingIndicator.setMaxWidth(100);
    loadingIndicator.setMaxHeight(100);
    loadingIndicator.setVisible(false);

    Scene scene = new Scene(root, 700, 400);

    HBox hbt = new HBox();
    Label title = new Label("Search Authors");
    title.setId("title");
    title.setFont(Font.font("Calibri", FontWeight.BOLD, 28));
    hbt.getChildren().add(title);
    hbt.setPadding(new Insets(10, 0, 0, 0));
    hbt.setAlignment(Pos.CENTER);

    HBox hbt1 = new HBox();
    Button logout = new Button("Logout");
    logout.setId("logout");
    hbt1.setPadding(new Insets(0, 20, 10, 0));
    hbt1.setAlignment(Pos.CENTER_RIGHT);
    hbt1.getChildren().add(logout);

    logout.setOnAction(event -> {

      Login log = new Login();
      log.start(primaryStage);

    });

    TabPane tabPane = new TabPane();

    BorderPane borderPane = new BorderPane();

    // *****************Quick Search*****************//

    Tab tab1 = new Tab();
    tab1.setText("Quick Search");
    tab1.setClosable(false);
    tab1.setId("quickId");
    HBox hb = new HBox();

    ToggleGroup group = new ToggleGroup();
    RadioButton button1 = new RadioButton("Find authors who published papers in OOPSLA since 2010");
    button1.setToggleGroup(group);
    button1.setSelected(true);
    button1.setUserData(1);
    button1.idProperty().set("quick1");

    RadioButton button2 = new RadioButton(
        "Find authors with at least two published papers in OOPSLA or ECOOP and did not serve on the committee for two consecutive years");
    button2.setWrapText(true);
    button2.setToggleGroup(group);
    button2.setUserData(2);
    button2.idProperty().set("quick2");

    RadioButton button3 = new RadioButton("Find authors with similar profiles");
    button3.setToggleGroup(group);
    button3.setUserData(3);
    button3.idProperty().set("quick3");
    TextField authorNameLike = new TextField();
    authorNameLike.setPromptText("Author Name");
    authorNameLike.setEditable(false);
    authorNameLike.setMaxWidth(630);
    authorNameLike.setId("authorNameLike");

    RadioButton button4 = new RadioButton("Find authors by keywords in published paper titles");
    button4.setToggleGroup(group);
    button4.setUserData(4);
    button4.idProperty().set("quick4");
    TextField keywords = new TextField();
    keywords.setPromptText("Comma Separated Keywords");
    keywords.setEditable(false);
    keywords.setMaxWidth(630);
    keywords.setId("keywords");

    Button btn = new Button("Search");
    btn.setId("quickSearch");

    VBox newvb = new VBox();
    newvb.setPadding(new Insets(20, 20, 20, 20));
    newvb.getChildren().addAll(button1, button2, button3, authorNameLike, button4, keywords, btn);
    authorNameLike.setTranslateX(22);
    keywords.setTranslateX(22);
    btn.setTranslateX(325);
    newvb.setSpacing(10);

    group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

      /**
       * This method changes the editable property of the text fields in quick search based upon the
       * query number
       */
      @Override
      public void changed(ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1) {
        int selected = (int) group.getSelectedToggle().getUserData();
        if (selected == 1 || selected == 2) {
          if (!authorNameLike.getText().equals(""))
            authorNameLike.clear();
          if (!keywords.getText().equals(""))
            keywords.clear();
          authorNameLike.setEditable(false);
          keywords.setEditable(false);
        } else if (selected == 3) {
          if (!keywords.getText().equals(""))
            keywords.clear();
          authorNameLike.setEditable(true);
          keywords.setEditable(false);
        } else if (selected == 4) {
          if (!authorNameLike.getText().equals(""))
            authorNameLike.clear();
          authorNameLike.setEditable(false);
          keywords.setEditable(true);
        }
      }
    });

    btn.setOnAction(event -> quickSearch(group, authorNameLike.getText(), keywords.getText(),
        loadingIndicator, primaryStage));

    BorderPane bp = new BorderPane();
    bp.setPadding(new Insets(10, 50, 50, 50));

    hb.getChildren().add(newvb);
    hb.setAlignment(Pos.CENTER);
    tab1.setContent(hb);

    // *****************Advanced Search*****************//

    Tab tab2 = new Tab();
    tab2.setText("Advanced Search");
    tab2.setClosable(false);
    tab2.setId("advancedTab");
    GridPane gridPane1 = new GridPane();
    gridPane1.setPadding(new Insets(20, 20, 20, 20));
    gridPane1.setHgap(20);
    gridPane1.setVgap(10);

    Label label = new Label("Journal/Conference Name:  ");
    label.setWrapText(true);

    ArrayList<String> list = new ArrayList<String>();
    final ObservableList<String> strings = FXCollections.observableArrayList();
    strings.add("OOPSLA");
    strings.add("ECOOP");
    strings.add("ACE");
    strings.add("ESOP");
    strings.add("FSE");
    strings.add("ICFP");
    strings.add("ICSE");
    strings.add("ISMM");
    strings.add("ISSTA");
    strings.add("PLDI");
    strings.add("POPL");
    strings.add("PPOPP");
    strings.add("ACM Trans. Program. Lang. Syst.");
    strings.add("IEEE Trans. Software Eng.");

    // Create the CheckComboBox with the data
    final CheckComboBox<String> checkComboBox = new CheckComboBox<String>(strings);
    checkComboBox.setPrefHeight(5.0);
    checkComboBox.setPrefWidth(100.0);
    checkComboBox.setPrefSize(checkComboBox.getPrefWidth(), checkComboBox.getPrefHeight());

    checkComboBox.getCheckModel().getCheckedItems().addListener(new ListChangeListener<String>() {
      public void onChanged(ListChangeListener.Change<? extends String> c) {
        // System.out.println(checkComboBox.getCheckModel().getCheckedItems());
        list.addAll(checkComboBox.getCheckModel().getCheckedItems());
      }
    });

    checkComboBox.setId("checkComboBox");

    Label label1 = new Label("Title Keywords:  ");
    label1.setWrapText(true);
    TextField titleText = new TextField();
    titleText.setPromptText("Comma Separated Keywords");
    titleText.setMinWidth(220);
    titleText.setId("titleText");

    Label label2 = new Label("Date published since:  ");
    label2.setWrapText(true);
    DatePicker datePicker = new DatePicker();
    datePicker.setId("datePicker");

    Label label3 = new Label("Minimum number of papers published:  ");
    label3.setWrapText(true);
    TextField minPaper = new TextField();
    minPaper.setPromptText("2");
    minPaper.setId("minPaper");

    Label label4 = new Label("Not on Committee for consective years:  ");
    label4.setWrapText(true);
    Spinner<Integer> spinner = new Spinner<Integer>();
    SpinnerValueFactory<Integer> valueFactory =
        new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20);
    spinner.setValueFactory(valueFactory);
    spinner.setEditable(true);
    spinner.setId("spinner");

    Label label5 = new Label("Author Like:  ");
    label5.setWrapText(true);
    TextField authorName = new TextField();
    authorName.setPromptText("Author Name");
    authorName.setMinWidth(240);
    authorName.setId("authorName");

    Button btn1 = new Button("Search");
    btn1.setAlignment(Pos.CENTER);
    btn1.setId("advancedSearch");

    btn1.setOnAction(event -> advancedSearch(list, titleText.getText(), datePicker.getValue(),
        minPaper.getText(), spinner.getValue(), authorName.getText(), loadingIndicator,
        primaryStage));

    HBox hb1 = new HBox();
    hb1.getChildren().addAll(label, checkComboBox);

    HBox hb2 = new HBox();
    hb2.getChildren().addAll(label1, titleText);

    HBox hb3 = new HBox();
    hb3.getChildren().addAll(label2, datePicker);

    HBox hb4 = new HBox();
    hb4.getChildren().addAll(label3, minPaper);

    HBox hb5 = new HBox();
    hb5.getChildren().addAll(label4, spinner);

    HBox hb6 = new HBox();
    hb6.getChildren().addAll(label5, authorName);

    Label search = new Label("Search Criteria:");
    search.setFont(Font.font("Calibri", FontWeight.BOLD, 20));

    Label filter = new Label("Filter Criteria:");
    filter.setFont(Font.font("Calibri", FontWeight.BOLD, 20));

    Label empty = new Label("");
    empty.setFont(Font.font("Calibri", FontWeight.BOLD, 20));

    Label empty1 = new Label("");
    empty1.setFont(Font.font("Calibri", FontWeight.BOLD, 20));

    VBox vb1 = new VBox();
    vb1.getChildren().addAll(search, hb1, hb3, filter, hb5);
    vb1.setSpacing(7);

    VBox vb2 = new VBox();
    vb2.getChildren().addAll(empty, hb2, hb6, empty1, hb4);
    vb2.setSpacing(7);

    ColumnConstraints col1 = new ColumnConstraints();
    col1.setPercentWidth(50);
    ColumnConstraints col2 = new ColumnConstraints();
    col2.setPercentWidth(50);
    gridPane1.getColumnConstraints().addAll(col1, col2);

    gridPane1.add(vb1, 0, 0);
    gridPane1.add(vb2, 1, 0);
    gridPane1.add(btn1, 1, 1);

    HBox hb_a = new HBox();
    hb_a.getChildren().add(gridPane1);
    hb_a.setAlignment(Pos.CENTER);
    tab2.setContent(hb_a);

    tabPane.getTabs().addAll(tab1, tab2);

    borderPane.prefHeightProperty().bind(scene.heightProperty());
    borderPane.prefWidthProperty().bind(scene.widthProperty());

    if (i == 1) {
      tabPane.getSelectionModel().select(tab1);
    } else if (i == 2) {
      tabPane.getSelectionModel().select(tab2);
    }

    borderPane.setCenter(tabPane);

    VBox vbox = new VBox();

    vbox.getChildren().addAll(hbt, hbt1);
    vbox.getChildren().add(borderPane);
    vbox.setBackground(
        new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

    root.getChildren().addAll(vbox, loadingIndicator);
    primaryStage.setScene(scene);
    primaryStage.setResizable(false);
    primaryStage.show();

    Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
    primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
    primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);

  }

  /**
   * This method returns the list of authors to the results table in Search Results page
   * 
   * @return
   */
  public List<AuthorDetails> getAuthorData() {
    return authorData;
  }
}
