module Snake {
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.media;
    opens org.helalanwar.snake to javafx.fxml;
    exports org.helalanwar.snake;
}