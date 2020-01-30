package gui;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MyActivitiesController implements Initializable {
    public class AchievementEntry {
        private final SimpleStringProperty achievement;
        private final SimpleStringProperty description;

        private AchievementEntry(String achievement, String description) {
            this.achievement = new SimpleStringProperty(achievement);
            this.description = new SimpleStringProperty(description);
        }

        public String getAchievement() {
            return achievement.get();
        }

        public SimpleStringProperty achievementProperty() {
            return achievement;
        }

        public void setAchievement(String achievement) {
            this.achievement.set(achievement);
        }

        public String getDescription() {
            return description.get();
        }

        public SimpleStringProperty descriptionProperty() {
            return description;
        }

        public void setDescription(String description) {
            this.description.set(description);
        }
    }

    public class ActivitiesEntry {
        private final SimpleStringProperty type;
        private final SimpleIntegerProperty score;
        private final SimpleStringProperty date;

        public ActivitiesEntry(String type, int score, String date) {
            this.type = new SimpleStringProperty(type);
            this.score = new SimpleIntegerProperty(score);
            this.date = new SimpleStringProperty(date);
        }

        public String getType() {
            return type.get();
        }

        public SimpleStringProperty typeProperty() {
            return type;
        }

        public void setType(String type) {
            this.type.set(type);
        }

        public int getScore() {
            return score.get();
        }

        public SimpleIntegerProperty scoreProperty() {
            return score;
        }

        public void setScore(int score) {
            this.score.set(score);
        }

        public String getDate() {
            return date.get();
        }

        public SimpleStringProperty dateProperty() {
            return date;
        }

        public void setDate(String date) {
            this.date.set(date);
        }

        @Override
        public String toString() {
            return "ActivitiesEntry{" +
                    "type=" + type +
                    ", score=" + score +
                    ", date=" + date +
                    '}';
        }
    }

    // ids of the labels you need to update
    @FXML
    Label footPrint;

    @FXML
    Label carbonSaved;

    @FXML
    Label savedThisWeek;


    @FXML
    TableView<AchievementEntry> achievements;

    @FXML
    TableColumn<AchievementEntry, String> ach_achievement;

    @FXML
    TableColumn<AchievementEntry, String> ach_desc;


    @FXML
    TableView<ActivitiesEntry> activities;

    @FXML
    TableColumn<ActivitiesEntry, String> act_type;

    @FXML
    TableColumn<ActivitiesEntry, Integer> act_score;

    @FXML
    TableColumn<ActivitiesEntry, String> act_date;

    /*
    default footprint value - 4000
    @dynamicFootprint the value that catches the footprint from the db
    if dynamicFootprint is null then the value in the label will stay untouched
     */

    public List<AchievementEntry> generateAchievements() {
        return Arrays.stream(parseAchievements()).map(row -> {
            String title = row.split(",")[1].replace("title:", "").replace(".", "");
            String description = row.split(",")[2].replace("description:", "");

            AchievementEntry ar = new AchievementEntry(title, description);
            return ar;
        }).collect(Collectors.toList());
    }

    public String[] parseAchievements() {
        ClientConnections cc = new ClientConnections();

        String response = cc.sendPost("", "/getachievement").getBody();

        String replace = response.replace("[", "")
                .replace("]", "")
                .replace("\"", "")
                .replace("\\", "");

        if (replace.length() == 0) {
            String[] ret = {};
            return ret;
        }

        String replace2 = replace.substring(1, replace.length() - 1);
        String regex = "\\Q},{\\E";
        return replace2.split(regex);
    }

    public List<ActivitiesEntry> generateActivities() {
        return Arrays.stream(parseActivities()).map(row -> {
            String type = row.split(",")[2].replace("type:", "");
            String score = row.split(",")[1].replace("score:", "");
            String date = row.split(",")[0].replace("date:", "");

            int scoreInt = 0;
            try {
                scoreInt = (int) Math.round(Double.parseDouble(score));
            } catch (NumberFormatException e) {
            }

            ActivitiesEntry ar = new ActivitiesEntry(type, scoreInt, date);
            return ar;
        }).collect(Collectors.toList());
    }

    public String[] parseActivities() {
        ClientConnections cc = new ClientConnections();

        String response = cc.sendGet("/myactivities").getBody();

        String replace = response.replace("[", "")
                .replace("]", "")
                .replace("\"", "")
                .replace("\\", "");

        if (replace.length() == 0) {
            String[] ret = {};
            return ret;
        }

        String replace2 = replace.substring(1, replace.length() - 1);
        String regex = "\\Q},{\\E";

        return replace2.split(regex);
    }

    private void fillAchievements(List<AchievementEntry> list) {
        ObservableList<AchievementEntry> obsList = FXCollections.observableList(list);
        achievements.setItems(obsList);
    }

    private void fillActivities(List<ActivitiesEntry> list) {
        ObservableList<ActivitiesEntry> obsList = FXCollections.observableArrayList(list);
        activities.setItems(obsList);
    }

    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        act_date.setCellValueFactory(new PropertyValueFactory<ActivitiesEntry, String>("date"));
        act_score.setCellValueFactory(new PropertyValueFactory<ActivitiesEntry, Integer>("score"));
        act_type.setCellValueFactory(new PropertyValueFactory<ActivitiesEntry, String>("type"));

        ach_desc.setCellValueFactory(new PropertyValueFactory<AchievementEntry, String>("description"));
        ach_achievement.setCellValueFactory(new PropertyValueFactory<AchievementEntry, String>("achievement"));


        String footprint = null;

        ClientConnections cc = new ClientConnections();
        footprint = cc.sendGet("getscore").getBody();
        Double d = Double.parseDouble(footprint);
        DecimalFormat df2 = new DecimalFormat(".##");
        String dStr = df2.format(d);
        footPrint.setText(dStr);
        String carbonFoot = cc.sendGet("getemission").getBody();
        Double d2 = Double.parseDouble(carbonFoot);
        String dStr2 = df2.format(d2);
        carbonSaved.setText(dStr2);


        fillActivities(generateActivities());
        fillAchievements(generateAchievements());
    }

}
