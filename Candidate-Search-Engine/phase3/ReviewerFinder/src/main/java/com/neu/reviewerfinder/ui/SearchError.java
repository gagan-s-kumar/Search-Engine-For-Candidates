package com.neu.reviewerfinder.ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * This is the class for displaying the Search Error
 * if the user fails to enter at least one criteria
 * @author Kaushik
 */
public class SearchError extends Application {

	/**
	 * This method displays the Search Error page
	 * @param primaryStage
	 */
	@Override
	public void start(Stage primaryStage) {
		
		VBox vb = new VBox();
		Label text = new Label("Search Failed!");
		text.setId("title");
		Text text1 = new Text("Please select at least one search criteria");
		text.setFont(Font.font("Calibri", FontWeight.BOLD, 24));
		Button btn = new Button("OK");
		btn.setId("ok");
		vb.getChildren().addAll(text,text1,btn);
		vb.setAlignment(Pos.CENTER);
		vb.setSpacing(10);

		btn.setOnAction(event -> {

	        	 SearchAuthors sa = new SearchAuthors();
	        	 sa.start(primaryStage);

	     });
		
		BorderPane bp = new BorderPane();
		bp.setPadding(new Insets(10,50,20,50));
		bp.setTop(vb);
		bp.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
		Scene scene = new Scene(bp, 400, 120);

		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();

		Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
		primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
		primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
	}
}
