package org.helalanwar.snake;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

public class DialogBox implements SnakeData{
    @FXML
    TextField name;
    @FXML
    public void on_OK() throws IOException {
        if (name.getText() != null){
            dataBase.readFile();
            dataBase.fileWriter(SnakeBoardLayout.SCORE,name.getText());
            SnakeBoardLayout.dialogBox.close();
        }

    }
}
