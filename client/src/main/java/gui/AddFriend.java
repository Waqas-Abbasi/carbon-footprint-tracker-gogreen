package gui;

import com.jfoenix.controls.JFXButton;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import gui.ClientConnections;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class AddFriend extends Base implements Initializable, Runnable {

    //inner class to create an entry which will contain a friends name and delete button
    public class Entry {
        Label friendLabel;
        Label scoreLabel;
        Label footprintLabel;
        Button delete;


        /**
         * todo.
         *
         * @param friendLabel friend name
         * @param delete      delete button
         */
        public Entry(Label friendLabel, Label scoreLabel, Label footprintLabel, Button delete) {
            this.delete = delete;
            this.friendLabel = friendLabel;
            this.scoreLabel = scoreLabel;
            this.footprintLabel = footprintLabel;
            //when the delete button pressed, print the friend name. AND
            //need to GET the Friendinfo[] array from databases again to
            //create the new friend list
            delete.setOnAction(event -> {
                //String result is the name which user wants to delete
                //todo: need to send this String to databases to delete this friend.
                String result = friendLabel.getText();//the friend name which the user delete.
                ClientConnections cc = new ClientConnections();
                JSONObject obj = new JSONObject();
                obj.put("friendName", result);
                cc.sendPost(obj.toString(), "deletefriend");
                friendPane.getChildren().clear();
                List<Friendinfo> friendList = getFriends();
                initEntry(friendList);
                //initEntry(Array:the data sent from databases );
                // todo:the data sent from databases.
            });
        }
    }

    public class FriendSearchEntry {
        Label friendSearchName;
        Button addButton;

        /**
         * to put the friend name label and add button into one entry.
         *
         * @param friendSearchName friendSearchName
         * @param addButton        addButton
         */
        public FriendSearchEntry(Label friendSearchName, Button addButton) {
            this.friendSearchName = friendSearchName;
            this.addButton = addButton;
            addButton.setOnAction(event -> {
                if (check()) {
                    ClientConnections cc = new ClientConnections();
                    JSONObject obj = new JSONObject();
                    obj.put("friendName", friendSearchName.getText());
                    ResponseEntity<String> response = cc.sendPost(obj.toString(), "addfriend");
                    String responseBody = response.getBody();

                    if (responseBody.equals("true")) {
                        tips.setText("Succesfull Added!");
                    } else {
                        tips.setText("Player Not Found!");
                    }

                    friendPane.getChildren().clear(); //clean the friendList
                    List<Friendinfo> friendList = getFriends();
                    initEntry(friendList);
                    scrollPane.setVisible(false);
                    // for test, can be deleted after implement getting the real data from databases
                    //initEntry(Array:the data sent from databases);
                    // todo: need to get the data from databases.
                }
            });


        }

    }

    List<String> myList;
    @FXML
    Pane pane;
    @FXML
    ScrollPane scrollPane;
    @FXML
    GridPane friendSearchList;
    @FXML
    ImageView imagefriend;
    @FXML
    JFXButton searchButton;
    @FXML
    Label tips;
    @FXML
    TextField friendName;
    @FXML
    private GridPane friendPane;

    private JSONObject searchObj;
    private String friendsSearch = "friends_with_selection";

    public GridPane getFriendSearchList() {
        return friendSearchList;
    }

    public void setFriendSearchList(GridPane friendSearchList) {
        this.friendSearchList = friendSearchList;
    }

    /**
     * to check the field is filled or not.
     *
     * @return true if it is filled
     */
    @FXML
    public boolean check() {
        boolean result = false;
        if (!friendName.getText().isEmpty()) {
            result = true;
        }
        return result;
    }

    /**
     * the Method create the whole friend list.
     * get the String array of friendship from databased and put it in the friend list.
     *
     * @param friendinfo the friendship array from databases.
     */
    public void initEntry(List<Friendinfo> friendinfo) {
        ArrayList<Entry> friendList = new ArrayList<>();
        for (int i = 0; i < friendinfo.size(); i++) {
            Label username = new Label(friendinfo.get(i).getUsername());
            Label score = new Label(String.valueOf(friendinfo.get(i).getScore()));
            Label footprint = new Label(String.valueOf(friendinfo.get(i).getFootprint()));
            Button delete = new Button("delete");
            Entry entry = new Entry(username, score, footprint, delete);
            friendList.add(entry);
        }
        for (int j = 0; j < friendList.size(); j++) {
            friendPane.add(friendList.get(j).friendLabel, 0, j);
            friendPane.add(friendList.get(j).scoreLabel, 1, j);
            friendPane.add(friendList.get(j).footprintLabel, 2, j);
            friendPane.add(friendList.get(j).delete, 3, j);
        }
    }

    /**
     * submit and send the add friend request to databases.
     * After add a new friend, need to call initEntry Again to create latest friend list.
     */
    @FXML
    public void submit() {
        if (check()) {
            //friendName.getText() can return the String name which the user want to add

            ClientConnections cc = new ClientConnections();
            JSONObject obj = new JSONObject();
            obj.put("friendName", friendName.getText());
            ResponseEntity<String> response = cc.sendPost(obj.toString(), "addfriend");
            String responseBody = response.getBody();
            cc.sendPost("", "/myachievements_first_friend");

            if (responseBody.equals("true")) {
                tips.setText("Succesfull Added!");
            } else {
                tips.setText("Player Not Found!");

            }
            friendPane.getChildren().clear(); //clean the friendList
            List<Friendinfo> friendList = getFriends();
            initEntry(friendList);
            // for test, can be deleted after implement getting the real data from databases
            //initEntry(Array:the data sent from databases);
            // todo: need to get the data from databases.
        }
    }

    /**
     * initializes from an url and a supplied resource bundle.
     * @param url url to send
     * @param resourceBundle resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        scrollPane.setVisible(false);
        tips.setText("Search For A Player In The Search Engine!");
        friendPane.getChildren().clear();
        List<Friendinfo> friendList = getFriends();
        initEntry(friendList);
        // for test, can delete after implement the connection to databases
        //initEntry(Friendinfo[] Array:the data sent by databases);
        // todo:get the data from databases.
        imagefriend.setImage(new Image("/images/friendship.png"));
    }

    /**
     * returns all the client friends as a list.
     * @return list of friends
     */
    private List<Friendinfo> getFriends() {
        ClientConnections cc = new ClientConnections();
        ResponseEntity<String> response = cc.sendGet("getfriends");
        String responseBody = response.getBody();


        String replace = responseBody.replace("[",
                "").replace("]",
                "").replace("\"",
                "");
        List<String> myList = new ArrayList<String>(Arrays.asList(replace.split(",")));

        List<Friendinfo> friendList = new ArrayList<>();

        if (myList.size() < 2) {
            return friendList;
        }
        for (int i = 0; i < myList.size(); i += 2) {
            String name = myList.get(i);
            String score = myList.get(i + 1);


            Double doublenumber = Double.parseDouble(score);
            DecimalFormat df2 = new DecimalFormat("#.##");
            DecimalFormatSymbols dfs = new DecimalFormatSymbols();
            dfs.setDecimalSeparator('.');
            df2.setDecimalFormatSymbols(dfs);
            Double footprint = doublenumber / 149.25;
            String dstr = df2.format(doublenumber);
            System.out.print(dstr);
            String dstr2 = df2.format(footprint);
            System.out.print(dstr);
            Friendinfo friend = new Friendinfo(name,
                    Double.parseDouble(dstr),
                    Double.parseDouble(dstr2));
            friendList.add(friend);

        }
        return friendList;
    }

    /**
     * init Friend Search List.
     *
     * @param friendinfo friend info
     */
    @FXML
    public void initFriendSearchList(List<String> friendinfo) {
        ArrayList<FriendSearchEntry> friendList = new ArrayList<>();
        for (int i = 0; i < friendinfo.size(); i++) {
            Label friendName = new Label(friendinfo.get(i));
            Button addfriendButton = new Button("ADD");
            FriendSearchEntry friendSearchEntry = new FriendSearchEntry(friendName,
                    addfriendButton);
            friendList.add(friendSearchEntry);
        }
        for (int j = 0; j < friendList.size(); j++) {
            //changed != to !(...).equals("");
            if (!friendList.get(j).friendSearchName.getText().equals("")) {
                friendSearchList.add(friendList.get(j).friendSearchName, 0, j);
                friendSearchList.add(friendList.get(j).addButton, 1, j);
            }
        }
    }


    /**
     * sets the search action or throws error.
     * @throws Exception if not available
     */
    @FXML
    public void setSearchAction() throws Exception {
        friendSearchList.getChildren().clear();
        searchObj = new JSONObject();
        searchObj.put("name", friendName.getText());
        Thread t = new Thread(this);
        t.start();
        tips.setText("Loading Please Wait!");
    }

    /**
     * run
     */
    @Override
    public void run() {
        ClientConnections cc = new ClientConnections();
        ResponseEntity<String> response = cc.sendPost(searchObj.toString(), friendsSearch);
        updateFriendList(response);
    }


    /**
     * searchFriend.
     */
    public void searchFriend() {
        Platform.runLater(() -> {
            initFriendSearchList(myList);
            scrollPane.setVisible(true);
        });
    }


    /**
     * updateFriendList.
     *
     * @param response response
     */
    public void updateFriendList(ResponseEntity<String> response) {
        myList = new ArrayList<>();

        if (response != null) {
            String responseBody = response.getBody();
            String replace = responseBody.replace("[", "").replace("]", "").replace("\"", "");
            myList = new ArrayList<String>(Arrays.asList(replace.split(",")));
        }

        searchFriend();
    }

    @FXML
    public void setClear() {
        scrollPane.setVisible(false);
    }

}
