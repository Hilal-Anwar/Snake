package org.helalanwar.snake;


import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class About implements Message {
    @FXML
    TextArea view;
    public void about_game() {
          view.setText(about);
    }
    @FXML
    public void license() {
             view.setText(license);
    }
    @FXML
    public void HowToPlay() {
        view.setText(how_to_play);
    }
}
