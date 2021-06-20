package org.helalanwar.snake;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DataBaseController implements Initializable,SnakeData {
    @FXML
    TableView<HighScore> ScoreBoard;
    @FXML
    TableColumn<String, HighScore> nameColumn;
    @FXML
    TableColumn<Integer, HighScore> ScoreColumn;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            dataBase.readFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        ScoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
        ScoreBoard.getItems().addAll(dataBase.temporaryData.values());
    }
}
