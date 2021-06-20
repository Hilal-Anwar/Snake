package org.helalanwar.snake;

public class HighScore {
    private final String name;
    private final Integer score;
    public String getName()
    {
        return name;
    }

    public Integer getScore() {
        return score;
    }
    HighScore(String name, int score){
        this.name=name;
        this.score=score;
    }
}
