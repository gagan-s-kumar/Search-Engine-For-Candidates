package com.neu.reviewerfinder.ui;

import com.neu.reviewerfinder.hibernate.dao.UserDaoImpl;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
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
 * This is the class to perform user login
 * 
 * @author Surekha, Kaushik
 */
public class Login extends Application {
  private StackPane root = new StackPane();

  /**
   * This method validates the user for the given User-Name and Password
   * 
   * @param username
   * @param password
   * @param loadingIndicator
   * @param primaryStage
   */
  private void validateUser(String username, String password,
      final ProgressIndicator loadingIndicator, Stage primaryStage) {
    if (loadingIndicator.isVisible()) {
      return;
    }

    loadingIndicator.setVisible(true);
    loadingIndicator.getParent().setDisable(true);
    UserDaoImpl usr = new UserDaoImpl();
    Task<Boolean> userValidator = new Task<Boolean>() {
      {
        setOnSucceeded(workerStateEvent -> {

          if (getValue()) {
            SearchAuthors sa = new SearchAuthors(1);
            sa.start(primaryStage);
            loadingIndicator.setVisible(false);
          } else {
            LoginError le = new LoginError();
            le.start(primaryStage);
            loadingIndicator.setVisible(false);
          }

        });

        setOnFailed(workerStateEvent -> {
          // getException().printStackTrace();
          throw new RuntimeException();
        });
      }

      @Override
      protected Boolean call() throws Exception {
        if (username == null || username.isEmpty() || password == null || password.isEmpty())
          return false;
        return usr.checkIfUserExists(username, password);
      }
    };

    Thread userValidatorThread = new Thread(userValidator, "user-validator");
    userValidatorThread.setDaemon(true);
    userValidatorThread.start();
  }

  /**
   * This method displays the Login page
   */
  @Override
  public void start(Stage primaryStage) {
    primaryStage.setTitle("Reviewer Finder");
    VBox vb = new VBox();
    vb.setAlignment(Pos.CENTER);
    // Adding HBox
    HBox hb = new HBox();
    Label title = new Label("Login");
    title.setFont(Font.font("Calibri", FontWeight.BOLD, 28));
    hb.getChildren().add(title);
    hb.setAlignment(Pos.CENTER);
    final ProgressIndicator loadingIndicator = new ProgressIndicator();
    loadingIndicator.setMaxWidth(100);
    loadingIndicator.setMaxHeight(100);
    loadingIndicator.setVisible(false);
    // Adding GridPane
    GridPane gridPane = new GridPane();
    gridPane.setPadding(new Insets(20, 20, 20, 20));
    gridPane.setHgap(5);
    gridPane.setVgap(5);

    // Input Fields
    Label lblUserName = new Label("Username: ");
    final TextField txtUserName = new TextField();
    txtUserName.setId("username");
    Label lblPassword = new Label("Password: ");
    final PasswordField pf = new PasswordField();
    pf.setId("password");
    Button btnLogin = new Button("Login");

    btnLogin.setTranslateX(100);

    btnLogin.setOnAction(
        event -> validateUser(txtUserName.getText(), pf.getText(), loadingIndicator, primaryStage));

    gridPane.add(lblUserName, 0, 0);
    gridPane.add(txtUserName, 1, 0);
    gridPane.add(lblPassword, 0, 1);
    gridPane.add(pf, 1, 1);
    gridPane.add(btnLogin, 0, 2);

    root.setPadding(new Insets(50, 50, 50, 50));
    root.setId("root");
    gridPane.setId("grid");
    btnLogin.setId("btnLogin");
    title.setId("title");

    vb.getChildren().addAll(hb, gridPane);
    root.getChildren().addAll(vb, loadingIndicator);
    root.setBackground(
        new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
    Scene scene = new Scene(root, 400, 200);

    primaryStage.setScene(scene);
    primaryStage.setResizable(false);
    primaryStage.show();

    Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
    primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
    primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);

  }
}
