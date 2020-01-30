package application.controller;

import application.model.PublicTransport;
import application.repository.PublicTransportRepository;
import application.security.SessionToUser;
import application.model.Activity;
import application.model.ActivityType;
import application.model.User;
import application.model.Vehicle;
import application.repository.ActivityRepository;
import application.repository.ActivityTypeRepository;
import application.repository.UserRepository;
import application.repository.VehicleRepository;

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
public class PublicTransportController {

    @Autowired
    PublicTransportRepository publicTransportRepository;

    @Autowired
    ActivityTypeRepository activityTypeRepository;

    @Autowired
    ActivityRepository activityRepository;

    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private SessionToUser sessionToUser;


    /**
     * calculates the score for vehicles
     * @param vehicleRepository
     * @param distance
     * @param carType
     * @return
     */
    public double calculateScore(
            VehicleRepository vehicleRepository, double distance, String carType
    ) {
        return distance * vehicleRepository.findByName(carType).getCarbon_Avoided();
    }

    /**
     * Sends POST request to addmeal mapping
     * @param str
     * @param cookie
     */
    @Transactional
    @RequestMapping(value = "/addpublictransport", method = RequestMethod.POST)
    public void addPublicTransport(
            @RequestBody String str, @RequestHeader(value = "Cookie") String cookie
    ) {

        JSONObject jsonObject = new JSONObject(str);
        User user = sessionToUser.sessionToUser(cookie);
        Date date = Date.valueOf(jsonObject.getString("date"));
        Time time = Time.valueOf(jsonObject.getString("time"));
        String carType = jsonObject.getString("carType");
        Double distance = Double.parseDouble(jsonObject.getString("distance"));

        double totalScore = calculateScore(vehicleRepository, distance, carType);

        user.setScore(user.getScore() + totalScore);
        ActivityType activityType = activityTypeRepository.findById(4);
        Activity activity = new Activity(totalScore, date, time);
        activity.setActivityType(activityType);
        activity.setUser(user);

        activityRepository.save(activity);

        Vehicle vehicle = vehicleRepository.findByName(carType);

        PublicTransport transport = new PublicTransport(distance);
        transport.setVehicle(vehicle);
        transport.setActivity(activity);
        publicTransportRepository.save(transport);

    }
}
