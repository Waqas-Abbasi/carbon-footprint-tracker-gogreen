package application.controller;

import application.model.Activity;
import application.repository.ActivityRepository;
import application.repository.ActivityTypeRepository;
import application.repository.UserRepository;
import application.security.SessionToUser;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
//import java.util.ArrayList;
//import java.util.Comparator;
import java.util.List;
//import java.util.function.Predicate;
import java.util.stream.Collectors;

@RestController
public class ActivitiesController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ActivityRepository activityRepository;

    @Autowired
    ActivityTypeRepository activityTypeRepository;

    @Autowired
    SessionToUser sessionToUser;//List<Activity> findByUser_idOrderByDateDesc(int id);

    /**
     * sneds get to myactivities mapping
     * @param cookie the cookie
     * @return List<String>
     */
    @Transactional
    @RequestMapping(value = "/myactivities", method = RequestMethod.GET)
    public List<String> getActivities(@RequestHeader(value = "Cookie") String cookie) {


        int id = sessionToUser.sessionToUser(cookie).getId();


        List<Activity> activities = activityRepository.findByUser_idOrderByDateDesc(id);


        return activities.stream()
                .limit(activities.size() >= 5 ? 5 : activities.size())
                .map(a -> {
                    String type = activityTypeRepository.findById(a.getActivity_Type().getType_id()).getName();
                    String score = a.getScore() + "";
                    Date date = a.getDate();
                    JSONObject json = new JSONObject();

                    json.put("type", type);
                    json.put("score", score);
                    json.put("date", date);
                    return json.toString();
                }).collect(Collectors.toList());

    }

}
