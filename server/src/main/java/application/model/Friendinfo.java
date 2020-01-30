package application.model;

public class Friendinfo {
    String username;
    double score;

    public Friendinfo(String username, double score) {
        this.score = score;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public double getScore() {
        return score;

    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return username + "," + Double.toString(score);
    }
}
