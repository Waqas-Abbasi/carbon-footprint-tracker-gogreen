package gui;

public class Friendinfo {
    String username;
    double score;
    double footprint;

    /** Creates a new instance of Friendinfo.
     * @param username the username
     * @param score the score
     * @param footprint the footprint
     */
    public Friendinfo(String username, double score, double footprint) {
        this.score = score;
        this.username = username;
        this.footprint = footprint;
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

    public double getFootprint() {
        return footprint;
    }

    public void setFootprint(double footprint) {
        this.footprint = footprint;
    }
}
