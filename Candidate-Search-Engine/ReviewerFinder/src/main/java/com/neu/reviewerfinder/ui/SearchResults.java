package com.neu.reviewerfinder.ui;

import com.neu.reviewerfinder.queryengine.AuthorDetails;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

/**
 * This is the class which displays the author search results
 * 
 * @author Kaushik, Surekha
 */
public class SearchResults extends Application {

  public static final String AuthorMapKey = "A";
  public static final String ConfPaperMapKey = "C";
  public static final String JournPaperMapKey = "J";
  int i;

  public SearchResults(int i) {
    this.i = i;
  }

  public SearchResults() {
    this.i = 1;
  }

  /**
   * This method displays the Search Results page Each column is can be sorted by clicking on the
   * column header
   * 
   * @param primaryStage
   */
  @SuppressWarnings({"rawtypes", "unchecked"})
  @Override
  public void start(Stage primaryStage) {
    Group root = new Group();

    Scene scene = new Scene(root, 700, 550);
    primaryStage.setTitle("Reviewer Finder");

    HBox hbt = new HBox();
    Label title = new Label("Search Results");
    title.setFont(Font.font("Calibri", FontWeight.BOLD, 28));
    title.setId("title");
    hbt.getChildren().add(title);
    hbt.setPadding(new Insets(10, 0, 0, 0));
    hbt.setAlignment(Pos.CENTER);

    HBox hbt1 = new HBox();
    Button searchAuth = new Button("Back");
    searchAuth.setId("searchAuth");
    Button logout = new Button("Logout");
    logout.setId("logout");
    hbt1.setPadding(new Insets(0, 20, 10, 0));
    hbt1.setAlignment(Pos.CENTER_RIGHT);
    hbt1.getChildren().addAll(searchAuth, logout);
    hbt1.setSpacing(7);

    searchAuth.setOnAction(event -> {

      SearchAuthors sa = new SearchAuthors(i);
      sa.start(primaryStage);

    });

    logout.setOnAction(event -> {

      Login log = new Login();
      log.start(primaryStage);

    });

    TableColumn<Map, String> authName = new TableColumn<>("Author Name");
    TableColumn<Map, String> confPaperCount = new TableColumn<>("Conference Paper Count");
    TableColumn<Map, String> journPaperCount = new TableColumn<>("Journal Paper Count");
    authName.setSortable(true);
    confPaperCount.setSortable(true);
    journPaperCount.setSortable(true);

    authName.setCellValueFactory(new MapValueFactory(AuthorMapKey));
    authName.setMinWidth(280);
    confPaperCount.setCellValueFactory(new MapValueFactory(ConfPaperMapKey));
    confPaperCount.setMinWidth(200);
    journPaperCount.setCellValueFactory(new MapValueFactory(JournPaperMapKey));
    journPaperCount.setMinWidth(200);

    TableView tableView = new TableView<>(generateDataInMap());
    tableView.setId("tableView");
    tableView.setEditable(false);
    tableView.getSelectionModel().setCellSelectionEnabled(true);
    tableView.getColumns().setAll(authName, confPaperCount, journPaperCount);
    Callback<TableColumn<Map, String>, TableCell<Map, String>> cellFactoryForMap =
        (TableColumn<Map, String> p) -> new TextFieldTableCell(new StringConverter() {
          @Override
          public String toString(Object t) {
            return t.toString();
          }

          @Override
          public Object fromString(String string) {
            return string;
          }
        });

    authName.setCellFactory(cellFactoryForMap);
    confPaperCount.setCellFactory(cellFactoryForMap);
    journPaperCount.setCellFactory(cellFactoryForMap);

    HBox hbt2 = new HBox();
    Button authorDetail = new Button("Author Details");
    authorDetail.setId("authorDetail");
    hbt2.setPadding(new Insets(0, 20, 10, 0));
    hbt2.setAlignment(Pos.CENTER);
    hbt2.getChildren().addAll(authorDetail);
    hbt2.setSpacing(7);


    authorDetail.setOnAction(event -> {
      HashMap<String, String> selectedRow =
          (HashMap<String, String>) tableView.getSelectionModel().getSelectedItem();
      if (selectedRow == null) {
        SearchError sa = new SearchError("Please select an Author to view details", i, true);
        sa.start(primaryStage);
      } else {
        DetailsPage sd = new DetailsPage(i, selectedRow.get(AuthorMapKey));
        sd.start(primaryStage);
      }
    });

    VBox vbox = new VBox();

    vbox.setSpacing(7);
    vbox.setPadding(new Insets(10, 0, 0, 10));
    vbox.getChildren().addAll(hbt, hbt1, tableView, hbt2);
    vbox.setAlignment(Pos.CENTER);
    root.getChildren().addAll(vbox);

    primaryStage.setScene(scene);
    primaryStage.setResizable(false);
    primaryStage.show();

    Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
    primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
    primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
  }

  /**
   * This method gets the list of authors Returns the observable list of author details composed of
   * 1) Author name 2) Conference Paper Count 3) Journal Paper Count
   * 
   * @return
   */
  @SuppressWarnings("rawtypes")
  private ObservableList<Map> generateDataInMap() {
    SearchAuthors sa = new SearchAuthors(i);
    ObservableList<Map> allData = FXCollections.observableArrayList();
    List<AuthorDetails> data = sa.getAuthorData();
    for (AuthorDetails r : data) {
      Map<String, String> dataRow = new HashMap<>();
      String value1 = r.getAuthorName();
      String value2 = String.valueOf(r.getConferencePaperCount());
      String value3 = String.valueOf(r.getJournalPaperCount());

      dataRow.put(AuthorMapKey, value1);
      dataRow.put(ConfPaperMapKey, value2);
      dataRow.put(JournPaperMapKey, value3);

      allData.add(dataRow);
    }
    return allData;
  }
}
