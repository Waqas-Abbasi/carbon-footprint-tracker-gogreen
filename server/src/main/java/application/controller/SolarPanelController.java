package application.controller;

import application.model.SolarPanels;
import application.security.SessionToUser;
import application.model.Activity;
import application.model.ActivityType;
import application.model.User;
import application.repository.ActivityRepository;
import application.repository.ActivityTypeRepository;
import application.repository.SolarPanelsRepository;
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
public class SolarPanelController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ActivityRepository activityRepository;

    @Autowired
    ActivityTypeRepository activityTypeRepository;

    @Autowired
    SolarPanelsRepository solarPanelsRepository;

    @Autowired
    private SessionToUser sessionToUser;

    /**
     * Addd solar panels.
     * Sends POST request to addsolarpanels mapping
     * @param str the str
     */
    @Transactional
    @RequestMapping(value = "/addsolarpanels", method = RequestMethod.POST)
    public void addSolarPanels(
            @RequestBody String str, @RequestHeader(value = "Cookie") String cookie
    ) {
        JSONObject jsonObj = new JSONObject(str);
        User user = sessionToUser.sessionToUser(cookie);
        Double electricityUsage = Double.parseDouble(jsonObj.getString("electricityUsage"));
        Double percentageSaved = Double.parseDouble(jsonObj.getString("percentageSaved"));
        Date date = Date.valueOf(jsonObj.getString("date"));
        Time time = Time.valueOf(jsonObj.getString("time"));

        ActivityType activityType = activityTypeRepository.findById(6);

        double energyGeneratedBySolar = electricityUsage * percentageSaved;
        double totalScore = energyGeneratedBySolar * 1.2;

        userRepository.setUserScoreByUsername(user.getScore() + totalScore, user.getUsername());

        Activity activity = new Activity(totalScore, date, time);

        activity.setUser(user);

        activity.setActivityType(activityType);

        activityRepository.save(activity);

        SolarPanels solarPanels = new SolarPanels(electricityUsage, percentageSaved);
        solarPanels.setActivity(activity);

        solarPanelsRepository.save(solarPanels);
    }


}
