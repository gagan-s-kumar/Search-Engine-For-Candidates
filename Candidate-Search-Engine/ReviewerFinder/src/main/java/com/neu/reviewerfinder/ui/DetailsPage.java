package com.neu.reviewerfinder.ui;

import com.neu.reviewerfinder.queryengine.AuthorDetails;
import java.util.List;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
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

/**
 * This is the class for viewing the author details
 * 
 * @author Surekha
 */
public class DetailsPage extends Application {

  int i;
  String authorName;
  AuthorDetails author = null;

  public DetailsPage(int i, String authorName) {
    this.i = i;
    this.authorName = authorName;
  }

  public DetailsPage() {
    this.i = 1;
    this.authorName = null;
  }

  /**
   * This method displays the Search Details page
   * 
   * @param primaryStage
   */
  @Override
  public void start(Stage primaryStage) {
    primaryStage.setTitle("Reviewer Finder");
    StackPane root = new StackPane();
    Scene scene = new Scene(root, 600, 300);

    HBox hbt = new HBox();
    Label title = new Label("Author Details");
    title.setId("title");
    title.setFont(Font.font("Calibri", FontWeight.BOLD, 28));
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

      SearchResults sa = new SearchResults(i);
      sa.start(primaryStage);

    });

    logout.setOnAction(event -> {

      Login log = new Login();
      log.start(primaryStage);

    });

    SearchAuthors sa = new SearchAuthors(i);
    List<AuthorDetails> data = sa.getAuthorData();
    for (AuthorDetails r : data) {
      if (r.getAuthorName().equalsIgnoreCase(authorName)) {
        this.author = r;
        break;
      }
    }

    Label name = new Label("Author Name:");
    name.setFont(Font.font("Calibri", FontWeight.BOLD, 14));
    Label authorName = new Label(author.getAuthorName());

    Label affLabel = new Label("Affiliation:");
    affLabel.setFont(Font.font("Calibri", FontWeight.BOLD, 14));
    String a = author.getAffiliation();
    if (a == null || a.isEmpty()) {
      a = "Unknown";
    }
    Label aff = new Label(a);

    Label countryLabel = new Label("Country:");
    countryLabel.setFont(Font.font("Calibri", FontWeight.BOLD, 14));
    String c = author.getCountry();
    if (c == null || c.isEmpty()) {
      c = "Unknown";
    }
    Label country = new Label(c);

    Label deptLabel = new Label("Department:");
    deptLabel.setFont(Font.font("Calibri", FontWeight.BOLD, 14));
    String d = author.getDept();
    if (d == null || d.isEmpty()) {
      d = "Unknown";
    }
    Label dept = new Label(d);

    Label confLabel = new Label("Number of Conference Papers published:");
    confLabel.setFont(Font.font("Calibri", FontWeight.BOLD, 14));
    Label conf = new Label(Integer.toString(author.getConferencePaperCount()));

    Label jLabel = new Label("Number of Journal Papers published:");
    jLabel.setFont(Font.font("Calibri", FontWeight.BOLD, 14));
    Label journal = new Label(Integer.toString(author.getJournalPaperCount()));

    Label keyLabel = new Label("Paper Keys:");
    keyLabel.setFont(Font.font("Calibri", FontWeight.BOLD, 14));
    TextArea key = new TextArea(author.getPaperKeys().toString());
    key.setEditable(true);
    key.setBorder(null);
    key.setMaxWidth(250);

    GridPane gridPane = new GridPane();
    gridPane.setPadding(new Insets(20, 20, 20, 20));
    gridPane.setHgap(5);
    gridPane.setVgap(5);
    gridPane.add(name, 0, 0);
    gridPane.add(authorName, 1, 0);

    gridPane.add(affLabel, 0, 1);
    gridPane.add(aff, 1, 1);

    gridPane.add(countryLabel, 0, 2);
    gridPane.add(country, 1, 2);

    gridPane.add(deptLabel, 0, 3);
    gridPane.add(dept, 1, 3);

    gridPane.add(confLabel, 0, 4);
    gridPane.add(conf, 1, 4);

    gridPane.add(jLabel, 0, 5);
    gridPane.add(journal, 1, 5);

    gridPane.add(keyLabel, 0, 6);
    gridPane.add(key, 1, 6);

    VBox vbox = new VBox();

    vbox.getChildren().addAll(hbt, hbt1, gridPane);
    vbox.setBackground(
        new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

    root.getChildren().addAll(vbox);
    primaryStage.setScene(scene);
    primaryStage.setResizable(false);
    primaryStage.show();

    Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
    primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
    primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);

  }
}
