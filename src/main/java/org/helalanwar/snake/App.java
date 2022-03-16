package org.helalanwar.snake;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    static Scene scene;
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("canvas.fxml")));
        scene=new Scene(root,743,756);
        scene.addEventFilter(KeyEvent.KEY_PRESSED, SnakeBoardLayout::change_Directions);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource("icon.jpg")).toString()));
        primaryStage.setTitle("Snake");
        primaryStage.setMinWidth(743+16);
        primaryStage.setMinHeight(756+25);
        primaryStage.show();
    }
}

