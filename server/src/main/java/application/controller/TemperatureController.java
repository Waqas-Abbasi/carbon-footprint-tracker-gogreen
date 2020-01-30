package application.controller;

import application.model.LoweringTemp;
import application.security.SessionToUser;
import application.model.Activity;
import application.model.ActivityType;
import application.model.User;
import application.repository.ActivityRepository;
import application.repository.ActivityTypeRepository;
import application.repository.LoweringTemperatureRepository;
import application.repository.UserRepository;

import java.sql.Date;
import java.sql.Time;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TemperatureController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ActivityRepository activityRepository;

    @Autowired
    ActivityTypeRepository activityTypeRepository;

    @Autowired
    LoweringTemperatureRepository loweringTemperatureRepository;

    @Autowired
    private SessionToUser sessionToUser;

    /**
     * Sends POST request to loweringtemp mapping
     *
     * @param str the str
     */
    @Transactional
    @RequestMapping(value = "/loweringtemp", method = RequestMethod.POST)
    public void loweringtemp(
            @RequestBody String str, @RequestHeader(value = "Cookie") String cookie
    ) {
        JSONObject jsonObj = new JSONObject(str);
        User user = sessionToUser.sessionToUser(cookie);
        Date date = Date.valueOf(jsonObj.getString("date"));
        Time time = Time.valueOf(jsonObj.getString("time"));
        Double tempInside = Double.parseDouble(jsonObj.getString("tempInside"));
        Double tempOutside = Double.parseDouble(jsonObj.getString("tempOutside"));

        ActivityType activityType = activityTypeRepository.findById(5);

        //Change later
        double totalScore = 100 * Math.abs(tempInside - tempOutside);

        userRepository.setUserScoreByUsername(totalScore + user.getScore(), user.getUsername());

        Activity activity = new Activity(totalScore, date, time);

        activity.setUser(user);

        activity.setActivityType(activityType);

        activityRepository.save(activity);

        LoweringTemp loweringTemp = new LoweringTemp(tempInside, tempOutside);
        loweringTemp.setActivity(activity);

        loweringTemperatureRepository.save(loweringTemp);
    }

}
