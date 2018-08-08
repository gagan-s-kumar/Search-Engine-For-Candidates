package com.neu.reviewerfinder.ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * This is the class for displaying the Login Error if the user login fails
 * 
 * @author Kaushik
 */
public class LoginError extends Application {

  /**
   * This method displays the Login Error page
   * 
   * @param primaryStage
   */
  @Override
  public void start(Stage primaryStage) {
    VBox vb = new VBox();
    Label label = new Label("Login Failed!");
    label.setId("title");
    Text text1 = new Text("Please enter valid Username/Password");
    label.setFont(Font.font("Calibri", FontWeight.BOLD, 24));
    Button btn = new Button("OK");
    btn.setId("ok");
    vb.getChildren().addAll(label, text1, btn);
    vb.setAlignment(Pos.CENTER);
    vb.setSpacing(10);

    btn.setOnAction(event -> {

      Login login = new Login();
      login.start(primaryStage);

    });

    BorderPane bp = new BorderPane();
    bp.setPadding(new Insets(10, 50, 20, 50));
    bp.setTop(vb);
    bp.setBackground(
        new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
    Scene scene = new Scene(bp, 400, 120);

    primaryStage.setScene(scene);
    primaryStage.setResizable(false);
    primaryStage.show();
  }
}
