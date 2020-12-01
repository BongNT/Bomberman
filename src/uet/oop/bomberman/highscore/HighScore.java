package uet.oop.bomberman.highscore;

public class HighScore {
    private String name;
    private int score;

    public String getName() {
        return name;
    }

    public HighScore(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
