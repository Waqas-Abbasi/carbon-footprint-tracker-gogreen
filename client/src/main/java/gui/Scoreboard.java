package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Scoreboard extends Base implements Initializable {
    public class TableEntry {
        private SimpleIntegerProperty rank;
        private SimpleStringProperty name;
        private SimpleStringProperty score;

        private TableEntry(int rank, String name, String score) {
            this.rank = new SimpleIntegerProperty(rank);
            this.name = new SimpleStringProperty(name);
            this.score = new SimpleStringProperty(score);
        }

        public int getRank() {
            return rank.get();
        }

        public SimpleIntegerProperty rankProperty() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank.set(rank);
        }

        public String getName() {
            return name.get();
        }

        public SimpleStringProperty nameProperty() {
            return name;
        }

        public void setName(String name) {
            this.name.set(name);
        }

        public String getScore() {
            return score.get();
        }

        public SimpleStringProperty scoreProperty() {
            return score;
        }

        public void setScore(String score) {
            this.score.set(score);
        }
    }

    @FXML
    public TableColumn<TableEntry, Integer> rank;

    @FXML
    public TableColumn<TableEntry, String> name;

    @FXML
    public TableColumn<TableEntry, Double> score;

    @FXML
    private TableView<TableEntry> scoreboard;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        rank.setCellValueFactory(new PropertyValueFactory<>("Rank"));
        name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        score.setCellValueFactory(new PropertyValueFactory<>("Score"));

        scoreboard.setItems(getEntries());
    }

    private ObservableList<TableEntry> getEntries() {
        ClientConnections cc = new ClientConnections();
        String top = cc.sendGet("/top").getBody();
        String top1 = top.substring(2, top.length() - 2);
        String[] ar = top1.split("\",\"");

        ObservableList<TableEntry> list = FXCollections.observableArrayList();

        for (String e : ar)
            list.add(parseEntry(e));

        for (int i = 0; i < list.size(); i++)
            list.get(i).setRank(i + 1);


        return list;
    }

    private TableEntry parseEntry(String entry) {
        String e1 = entry.replace("{", "")
                .replace("}", "")
                .replace("\"", "")
                .replace("\\", "");

        String[] ar = e1.split(",");
        String score_str = ar[0].replace("score:", "");
        String name = ar[1].replace("username:", "");

        BigInteger score = new BigInteger("0");
        try {
            score = new BigDecimal(score_str).toBigInteger();
        } catch (NumberFormatException e) {
        }

        return new TableEntry(0, name, score.toString());
    }
}
