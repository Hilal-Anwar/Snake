package org.helalanwar.snake;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.*;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class SnakeBoardLayout implements Initializable,SnakeData {
    static AudioClip killed = new AudioClip(Objects.requireNonNull(SnakeBoardLayout.class.getResource("Untitled 2.wav")).toString());
    static AudioClip start = new AudioClip(Objects.requireNonNull(SnakeBoardLayout.class.getResource("progress_start.wav")).toString());
    static AudioClip end = new AudioClip(Objects.requireNonNull(SnakeBoardLayout.class.getResource("progress_end.wav")).toString());
    static AudioClip point = new AudioClip(Objects.requireNonNull(SnakeBoardLayout.class.getResource("big_point.wav")).toString());
    final Image BACKGROUND_IMAGE=new Image(Objects.requireNonNull(SnakeBoardLayout.class.getResource("image.jpg")).toString());
    final  Image BIG_FOOD=new Image(Objects.requireNonNull(SnakeBoardLayout.class.getResource("big_food.png")).toString());
    final  Image FOOD=new Image(Objects.requireNonNull(SnakeBoardLayout.class.getResource("food.png")).toString());
    final Image SNAKE_BODY=new Image(Objects.requireNonNull(SnakeBoardLayout.class.getResource("snakeimage.png")).toString());
    static AudioClip Background_Sound = new AudioClip(Objects.requireNonNull(SnakeBoardLayout.class.getResource("background_sound.mp3")).toString());
    static int speed = 7;
    int diff_x,diff_y;
    static int WIDTH = 37;
    static int HEIGHT =32;
    static int FOOD_X = 0;
    static int FOOD_Y = 0;
    static int SCORE = 0;
    static int cornerSize = 20;
    static int counter = 0;
    int kn=5;
    static int cons = 4;
    static AnimationTimer x;
    static Stage dialogBox = new Stage();
    static List<snakeBody> snake = new ArrayList<>();
    static Dir direction = Dir.left;
    static boolean gameOver = false;
    static Random rand = new Random();
    static String HEARD_DIRECTION = "leftmouth.png";
    static boolean condition = true;
    Stage HIGH_SCORE_STAGE = new Stage();
    AudioClip eating_sound = new AudioClip
            (Objects.requireNonNull(SnakeBoardLayout.class.
                    getResource("Untitled 1.wav")).toString());
    @FXML
    Canvas game_board;
    @FXML
    Label length, score,final_score;
    @FXML
    ProgressBar progress;
    @FXML
    ImageView top_image,bottom_image;
    @FXML
    VBox game_over_layer;
    @FXML
    CheckMenuItem on,off;
    private final Image e=new Image(Objects.requireNonNull(getClass().getResource("icon.jpg")).toString());
    public static void change_Directions(KeyEvent key)
    {
        if ((snake.get(0).x >= 0) && (snake.get(0).x != WIDTH) && (snake.get(0).y >= 0) && (snake.get(0).y != HEIGHT)) {
            if ((key.getCode() == KeyCode.UP) && (direction != Dir.down)) {
                direction = Dir.up;
            }
            if (key.getCode() == KeyCode.LEFT && (direction != Dir.right)) {
                direction = Dir.left;
            }
            if (key.getCode() == KeyCode.DOWN && (direction != Dir.up)) {
                direction = Dir.down;
            }
            if (key.getCode() == KeyCode.RIGHT && (direction != Dir.left)) {
                direction = Dir.right;
            }
            if (key.getCode() == KeyCode.SPACE) {
                if (gameOver) {
                    condition = true;
                    snake.clear();
                    snake.add(new snakeBody(WIDTH / 2, HEIGHT / 2));
                    snake.add(new snakeBody(WIDTH / 2, HEIGHT / 2));
                    snake.add(new snakeBody(WIDTH / 2, HEIGHT / 2));
                    gameOver = false;
                    speed = 7;
                    SCORE = 0;
                    counter = 0;
                    killed.stop();
                    x.start();
                }
            }
        }
    }
    @FXML
    public void showHigh_Score(ActionEvent actionEvent) throws IOException
    {
        MenuItem menuItem = (MenuItem) actionEvent.getSource();
        if (menuItem.getText().equals("High score"))
            showHighScore();
        else {
             showAbout();
        }
    }

    private void showAbout() throws IOException {
        Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("About.fxml"))), 903, 489);
        HIGH_SCORE_STAGE.setTitle("Snake");
        HIGH_SCORE_STAGE.setScene(scene);
        HIGH_SCORE_STAGE.getIcons().add(e);
        HIGH_SCORE_STAGE.show();
    }

    public void newFood() {
        start:
        while (true) {
            FOOD_X = rand.nextInt(WIDTH);
            FOOD_Y = rand.nextInt(HEIGHT);
            for (snakeBody c : snake) {
                if (c.x == FOOD_X && c.y == FOOD_Y) {
                    continue start;
                }
            }
            counter++;
            if (counter == 6) {
                progress.setProgress(1);
                if (Background_Sound.isPlaying()) {
                    point.play();
                    start.play();
                }
            } else {
                if (counter > 6)
                    counter = 0;
                progress.setProgress(0);
                if(kn%2==0)
                    kn++;
            }
            break;

        }
    }
    public void DrawSnake_board_on_Canvas(GraphicsContext gc) throws IOException {
        if (!gameOver)
            game_over_layer.setVisible(false);
        gc.clearRect(0, 0, game_board.getWidth(),game_board.getHeight());
        WIDTH= (int) (game_board.getWidth()/cornerSize);
        HEIGHT= (int) (game_board.getHeight()/cornerSize);
        Main.scene.widthProperty().addListener(observable -> {
            game_board.setWidth(Main.scene.getWidth());
            top_image.setFitWidth(Main.scene.getWidth());
            bottom_image.setFitWidth(Main.scene.getWidth());
            diff_x=((int) (game_board.getWidth()/cornerSize))-WIDTH;
        });
        Main.scene.heightProperty().addListener(observable ->{
            game_board.setHeight(Main.scene.getHeight()-116);
            diff_y=((int) (game_board.getHeight()/cornerSize))-HEIGHT;
        });
        if(diff_x!=0||diff_y!=0){
            resetPosition();
        }
        diff_y=0;
        diff_x=0;
        if (progress.getProgress() < 0) {
            if (Background_Sound.isPlaying()) end.play();
            progress.setProgress(0);
        }
        if (SCORE == 0 && snake.size() == 3) {
            score.setText("Score : "+"0");
            length.setText("Snake length : "+"3");
        }
        if (gameOver) {
            progress.setProgress(0);
            killed.play();
            game_over_layer.setVisible(true);
            x.stop();
            kn=5;
            final_score.setText(SCORE+"");
            if (condition && SCORE > 0) {
                condition = false;
                dataBase.readFile();
                var x = dataBase.temporaryData;
                if (!x.isEmpty()) {
                    System.out.println(new ArrayList<>(x.keySet().stream().sorted(Comparator.naturalOrder()).toList()));
                    if ((new ArrayList<>(x.keySet().stream().sorted(Comparator.naturalOrder()).toList()).get(0) < SCORE) || x.size() < 5) {
                        snake_Master();
                    }
                } else {
                    snake_Master();
                }

            }
            return;
        }

        for (int i = snake.size() - 1; i >= 1; i--) {
            snake.get(i).x = snake.get(i - 1).x;
            snake.get(i).y = snake.get(i - 1).y;
        }
        switch (direction) {
            case up -> {
                HEARD_DIRECTION = "upmouth.png";
                snake.get(0).y--;
                if (snake.get(0).y < 0) {
                    snake.get(0).y = HEIGHT;
                }
            }
            case down -> {
                HEARD_DIRECTION = "downmouth.png";
                snake.get(0).y++;
                if (snake.get(0).y > HEIGHT) {
                    snake.get(0).y = 0;
                }
            }
            case left -> {
                HEARD_DIRECTION = "leftmouth.png";
                snake.get(0).x--;
                if (snake.get(0).x < 0) {
                    snake.get(0).x = WIDTH;
                }
            }
            case right -> {
                HEARD_DIRECTION = "rightmouth.png";
                snake.get(0).x++;
                if (snake.get(0).x > WIDTH) {
                    snake.get(0).x = 0;
                }
            }
        }
        // eat food
        if (FOOD_X == snake.get(0).x && FOOD_Y == snake.get(0).y) {
            if (Background_Sound.isPlaying()) {
                eating_sound.stop();
                eating_sound.play();
            }
            if (progress.getProgress() <= 0)
                SCORE = SCORE + cons;
            else {
                speed++;
                cons++;
                SCORE = SCORE + (int) (progress.getProgress() * cons / 2 * 100 * (speed - kn));
            }
            score.setText("Score : "+SCORE);
            length.setText("Snake length : "+snake.size() + "");
            snake.add(new snakeBody(-1, -1));
            newFood();
        }

        // self destroy
        for (int i = 1; i < snake.size(); i++) {
            if (snake.get(0).x == snake.get(i).x && snake.get(0).y == snake.get(i).y) {
                gameOver = true;
                break;
            }
        }
        gc.drawImage(BACKGROUND_IMAGE, 0, 0,Main.scene.getWidth(),Main.scene.getHeight()-2*58);
        if (counter == 6 && progress.getProgress() != 0) {
            gc.drawImage(BIG_FOOD, FOOD_X * cornerSize, FOOD_Y * cornerSize);
            progress.setProgress(progress.getProgress() - 0.01 * (speed - kn-1));
        } else {
            gc.drawImage(FOOD, FOOD_X  *cornerSize, FOOD_Y * cornerSize);
        }
        // snake
        for (int i = 0; i < snake.size(); i++) {
            snakeBody c = snake.get(i);
            if (i == 0)
                gc.drawImage(new Image(Objects.requireNonNull(SnakeBoardLayout.class.getResource(HEARD_DIRECTION)).toString()),
                        (c.x)* cornerSize, (c.y) * cornerSize);
            else
                gc.drawImage(SNAKE_BODY, (c.x) * cornerSize, (c.y) * cornerSize);
        }

    }

    private void resetPosition() {
        if (direction.equals(Dir.left)){
           snake.get(0).x= ((int) game_board.getWidth())/cornerSize;
           int k=(((int) game_board.getHeight())/cornerSize)/2;
           snake.get(0).y=k;
           for (int i = 1; i <snake.size()-1 ; i++) {
               snake.get(i).x=-1;
               snake.get(i).y=k;
           }
       }
        if (direction.equals(Dir.right)){
            snake.get(0).x= 0;
            int k=(((int) game_board.getHeight())/cornerSize)/2;
            snake.get(0).y=k;
            for (int i = 1; i <snake.size()-1 ; i++) {
                snake.get(i).x=-1;
                snake.get(i).y=k;
            }
        }
        if (direction.equals(Dir.up)){
            snake.get(0).y= ((int) game_board.getHeight())/cornerSize;
            int k=(((int) game_board.getWidth())/cornerSize)/2;
            snake.get(0).x=k;
            for (int i = 1; i <snake.size()-1 ; i++) {
                snake.get(i).y=-1;
                snake.get(i).x=k;
            }
        }
        if (direction.equals(Dir.down)){
            snake.get(0).y= 0;
            int k=(((int) game_board.getWidth())/cornerSize)/2;
            snake.get(0).x=k;
            for (int i = 1; i <snake.size()-1 ; i++) {
                snake.get(i).y=-1;
                snake.get(i).x=k;
            }
        }
        FOOD_X= rand.nextInt((((int) game_board.getWidth())/cornerSize)-10);
        FOOD_Y=rand.nextInt((((int) game_board.getHeight())/cornerSize)-10);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        HIGH_SCORE_STAGE.initModality(Modality.APPLICATION_MODAL);
        dialogBox.initModality(Modality.APPLICATION_MODAL);
        GraphicsContext graphicsContext = game_board.getGraphicsContext2D();
        newFood();
        Background_Sound.setVolume(0.1);
        start.setVolume(0.3);
        end.setVolume(0.3);
        eating_sound.setVolume(0.3);
        point.setVolume(0.3);
        Background_Sound.setCycleCount(AudioClip.INDEFINITE);
        Background_Sound.play();
        snake.add(new snakeBody(WIDTH / 2, HEIGHT / 2));
        snake.add(new snakeBody(WIDTH / 2, HEIGHT / 2));
        snake.add(new snakeBody(WIDTH / 2, HEIGHT / 2));
        x = new AnimationTimer() {
            long lastTick = 0;

            @Override
            public void handle(long now) {
                if (lastTick == 0) {
                    lastTick = now;
                    return;
                }
                if (now - lastTick > 1000000000 / (speed)) {
                    lastTick = now;
                    try {
                        DrawSnake_board_on_Canvas(graphicsContext);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        x.start();
    }

    void snake_Master() throws IOException {
        Scene scene1 = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("dialogBox.fxml"))), 288, 191);
        dialogBox.setResizable(false);
        dialogBox.setTitle("Snake");
        dialogBox.getIcons().add(e);
        dialogBox.setScene(scene1);
        dialogBox.show();
    }

    void showHighScore() throws IOException {
        Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("scoreBoard.fxml"))), 462, 322);
        HIGH_SCORE_STAGE.setTitle("Snake");
        HIGH_SCORE_STAGE.setScene(scene);
        HIGH_SCORE_STAGE.getIcons().add(e);
        HIGH_SCORE_STAGE.show();
    }
    @FXML
    protected void sound(ActionEvent actionEvent) {
        var checkboxMenuItem = (CheckMenuItem) actionEvent.getSource();
        if (checkboxMenuItem.getText().equals("on")) {
            Background_Sound.play();
            on.setSelected(true);
            off.setSelected(false);
        } else {
            Background_Sound.stop();
            off.setSelected(true);
            on.setSelected(false);
        }
    }
    public enum Dir {
        left, right, up, down
    }

    public static class snakeBody {
        int x, y;

        snakeBody(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
