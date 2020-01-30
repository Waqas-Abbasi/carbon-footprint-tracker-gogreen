package application.controller;

import application.security.SessionToUser;
import application.model.Activity;
import application.model.ActivityType;
import application.model.Food;
import application.model.Meal;
import application.model.MealSub;
import application.model.User;
import application.repository.ActivityRepository;
import application.repository.ActivityTypeRepository;
import application.repository.FoodRepository;
import application.repository.MealRepository;
import application.repository.MealSubRepository;
import application.repository.UserRepository;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MealController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MealRepository mealRepository;

    @Autowired
    private MealSubRepository mealSubRepository;

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private ActivityTypeRepository activityTypeRepository;

    @Autowired
    private SessionToUser sessionToUser;

    /**
     * Sends POST request to addmeal mapping
     * @param str
     * @param cookie
     * @return
     * @throws ParseException
     */
    @Transactional
    @RequestMapping(value = "/addmeal", method = RequestMethod.POST)
    public String addMeal(
            @RequestBody String str, @RequestHeader(value = "Cookie") String cookie
    ) throws ParseException {
        JSONObject jsonObject = new JSONObject(str);
        User userTemp = sessionToUser.sessionToUser(cookie);
        String[] foodList = jsonObject.getString("foodList").split(",");

        //       SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
        //       java.util.Date dateTemp = sdf1.parse(jsonObject.getString("date"));
        //       java.sql.Date date = new java.sql.Date(dateTemp.getTime());

        Date date = Date.valueOf(jsonObject.getString("date"));
        Time time = Time.valueOf(jsonObject.getString("time"));

        int[] foodList2 = new int[foodList.length];

        double totalScore = 0;

        for (int i = 0; i < foodList.length; i++) {
            String food = foodList[i];

            Food foodTemp = foodRepository.findByFoodName(food);

            double score = foodTemp.getScore();

            totalScore += score;

            foodList2[i] = foodTemp.getFoodID();
        }

        double score = userTemp.getScore();


        userRepository.setUserScoreByUsername(score + totalScore, userTemp.getUsername());

        ActivityType activityType = activityTypeRepository.findById(1);

        Activity activity = new Activity(totalScore, date, time);

        activity.setUser(userTemp);
        activity.setActivityType(activityType);

        activityRepository.save(activity);

        //Adds meal and associates it with the user
        MealSub ms1 = new MealSub();
        mealSubRepository.save(ms1);

        for (int i = 0; i < foodList2.length; i++) {
            Meal meal = new Meal(foodList2[i]);
            meal.setMealSub(ms1);
            meal.setActivity(activity);
            mealRepository.save(meal);
        }

        User customer = userRepository.findByUsername(userTemp.getUsername());

        return customer.toJson().toString();
    }


    /**
     * Sends GET request to getmeals mapping
     * @param cookie
     * @return
     */
    @RequestMapping(value = "/getmeals", method = RequestMethod.GET)
    public List<String> getLastMeals(@RequestHeader(value = "Cookie") String cookie) {

        int id = sessionToUser.sessionToUser(cookie).getId();

        List<Activity> activities = activityRepository.findByUser_idOrderByDateDesc(id);

        int activityType = 1;

        List<String> meals = activities.stream()
                .filter(a -> a.getActivity_Type().getType_id() == activityType)
                .map(a -> a.getActivityID())
                .map(activityId -> {
                    JSONObject json = new JSONObject();
                    List<String> foodNames = mealRepository.findByActivityID(activityId).stream()
                            .map(meal -> foodRepository.findByFoodID(meal.getFoodID()).getFoodName())
                            .collect(Collectors.toList());

                    json.put("food", foodNames);
                    json.put("score", activityRepository.findByActivityID(activityId).getScore());
                    return json.toString();
                })
                .limit(5)
                .collect(Collectors.toList());

        return meals;
    }
}
