package gui;

import com.jfoenix.controls.JFXCheckBox;
import org.json.JSONObject;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import gui.ClientConnections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AddNewMealController extends Base implements Initializable {

    //strings might be changed to the actual model later on
    HashSet<String> ingredients = new HashSet<>();
    HashSet<String> meals = new HashSet<>();

    // we declare the checkboxes whose status we will check

    //CHECKSTYLE:OFF
    @FXML
    JFXCheckBox lamb, beef, chicken, pork ,
            tuna, turkey, cheese, eggs,
            potatoes, rice, nuts, fruit,
            tofu, beans, milk, vegetables,
            lentils;
    //CHECkSTYLE:ON
    @FXML
    VBox mealList1;
    @FXML
    Label submissionStatus;
    @FXML
    Label selectedIngredients;
    @FXML
    HBox foodIngredientsPane;


    @FXML
    Label statusNewMeal;
    @FXML
    Label statusExistingMeal;
    @FXML
    Label selectedMeals;

    List<JFXCheckBox> mealCheckBoxes = new ArrayList<>();

    SceneManager manager = new SceneManager();

    /**
     * list checkbox item.
     *
     * @param source JFXCheckBox
     * @param target Label
     * @param list   list
     */
    public void listCheckBoxItem(JFXCheckBox source, Label target, HashSet<String> list) {
        if (source.isSelected()) {
            String text = target.getText();
            text += " " + source.getText();

            target.setText(text);
            list.add(source.getText());

        } else {
            String text = target.getText();
            text = Arrays.asList(text.split(source.getText()))
                    .stream()
                    .map(s -> s.trim())
                    .collect(Collectors.joining(" "));

            target.setText(text);
            list.remove(source.getText());
        }
    }

    /**
     * checks if a button is clicked.
     *
     * @param event ActionEvent
     */
    public void ingredientChecked(ActionEvent event) {

        //we check which button is clicked and add the name of the ingredient to the ingredients set
        JFXCheckBox sourceButton = (JFXCheckBox) event.getSource();

        // makes sure the meal button do not affect the input
        if (sourceButton.getParent().getId().equals("mealList1")
                || sourceButton.getParent().getId().equals("mealList2")) {
            return;
        }

        //check if the checkBox is selected
        //if it is selected then add the ingredint
        //otherwise delete it from the list
        listCheckBoxItem(sourceButton, selectedIngredients, ingredients);
    }

    /**
     * checks if meal button is checked.
     *
     * @param event ActionEvent
     */
    public void mealChecked(ActionEvent event) {
        JFXCheckBox sourceButton = (JFXCheckBox) event.getSource();

        if (!(sourceButton.getParent().getId().equals("mealList1")
                || sourceButton.getParent().getId().equals("mealList2"))) {
            return;
        }

        listCheckBoxItem(sourceButton, selectedMeals, meals);
    }

    /**
     * submit and refresh.
     *
     * @param event  event
     * @param target target
     * @param list   list
     * @throws Exception Exception
     */
    public void submitItemsAndRefreshStage(
            ActionEvent event, Label target, HashSet<String> list) throws Exception {
        if (!list.isEmpty()) {
            target.setText("Success");
        }

        manager.loadStageWithChild(event, "/dashBoard.fxml", "/addNewMeal.fxml");
    }

    /**
     * sends items.
     *
     * @param cc   client connection
     * @param list list as string
     */
    public void sendItems(ClientConnections cc, String list) {
        JSONObject obj = new JSONObject();

        obj.put("foodList", list);

        LocalDate today = LocalDate.now();
        LocalTime localTime = LocalTime.now();
        String time = localTime.toString();
        time = time.substring(0, time.indexOf("."));

        obj.put("date", today);
        obj.put("time", time);

        cc.sendPost(obj.toString(), "addmeal");
    }

    //checks if any checkBox is checked

    /**
     * submits meal throws exception.
     *
     * @param event ActionEvent
     * @throws Exception for cc
     */
    public void submitNewMeal(ActionEvent event) throws Exception {
        String str = ingredients.toString();
        str = str.replace("[", "");
        str = str.replace("]", "");
        str = str.replace(" ", "");

        System.out.println(str);

        sendItems(new ClientConnections(), str);

        submitItemsAndRefreshStage(event, statusNewMeal, ingredients);
    }

    /**
     * submits meal, throws exception.
     *
     * @param event ActionEvent
     * @throws Exception for cc
     */
    public void submitNewMealFromExisting(ActionEvent event) throws Exception {
        String mealsToText = mealCheckBoxes
                .stream()
                .filter(jfxCheckBox -> jfxCheckBox.isSelected())
                .map(jfxCheckBox -> jfxCheckBox.getText())
                .map(ingredient -> ingredient.split("Score")[0].trim())
                .collect(Collectors.joining(","));

        System.out.println(mealsToText);

        sendItems(new ClientConnections(), mealsToText);

        submitItemsAndRefreshStage(event, statusExistingMeal, meals);
    }


    /**
     * generates meals.
     *
     * @param meals meals to send
     * @return List of meals (as JSONObjects)
     */
    public List<JSONObject> generateMeals(String[] meals) {
        return Arrays.asList(meals).stream()
                .map(meal -> {
                    JSONObject js = new JSONObject();
                    js.put("score", meal.split(",")[0].split("score:")[1]);
                    js.put("food", meal.split("food:")[1]);
                    return js;
                }).collect(Collectors.toList());
    }

    /**
     * generates meals.
     *
     * @param response response
     * @return String[]
     */
    public String[] parseMeals(String response) {

        String regex = "\\Q},{\\E";
        String replace = response.replace("\"", "")
                .replace("\\", "")
                .replace("[", "")
                .replace("]", "");
        try {
            String replace2 = replace.substring(1, replace.length() - 1);
            return replace2.split(regex);
        } catch (Exception e) {
            String[] empty = new String[0];
            return empty;
        }
    }

    /**
     * requests meals.
     *
     * @param cc      client connection.
     * @param mapping String
     * @return List
     */
    public List<JSONObject> requestMeals(ClientConnections cc, String mapping) {
        String res = cc.sendGet(mapping).getBody();
        return generateMeals(parseMeals(res));
    }

    /**
     * nothing if meals list is empty.
     *
     * @param vbox  VBox
     * @param meals List
     */
    public void initMealVbox(VBox vbox, List<JSONObject> meals) {
        if (meals.isEmpty()) {
            return;
        }


        List<JFXCheckBox> buttons = meals.stream()
                .map(meal -> {
                    String text = meal.getString("food") + " Score: " + meal.getString("score");
                    JFXCheckBox existingMeal = new JFXCheckBox(text);
                    mealCheckBoxes.add(existingMeal);
                    return existingMeal;
                }).collect(Collectors.toList());

        vbox.getChildren().addAll(buttons);

    }


    /**
     * initializes connection.
     *
     * @param url            url to send
     * @param resourceBundle resourcebundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ClientConnections cc = new ClientConnections();

        //JSONObject js = new JSONObject();
        //js.put("username", LoginPage.user.getUsername());
        //String res = cc.sendPost(js.toString(), "getmeals").getBody();

        //List<JSONObject> meals = generateMeals(parseMeals(res));



        List<JSONObject> meals = requestMeals(cc, "getmeals");
        initMealVbox(mealList1, meals);
    }
}
