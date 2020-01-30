package application.controller;

import application.security.SessionToUser;
import application.model.Activity;
import application.model.ActivityType;
import application.model.Continent;
import application.model.Country;
import application.model.Product;
import application.model.User;
import application.repository.ActivityRepository;
import application.repository.ActivityTypeRepository;
import application.repository.ContinentDistanceRepository;
import application.repository.ContinentRepository;
import application.repository.CountryRepository;
import application.repository.ProductRepository;
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
public class ProductController {


    @Autowired
    ActivityRepository activityRepository;

    @Autowired
    CountryRepository countryRepository;

    @Autowired
    ActivityTypeRepository activityTypeRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ContinentRepository continentRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ContinentDistanceRepository continentDistanceRepository;

    @Autowired
    private SessionToUser sessionToUser;

    public double calculateScoreByContinentDistance(
            ContinentDistanceRepository continentDistanceRepository, int boughtIn, int productOrigin
    ) {
        return continentDistanceRepository.getScore(boughtIn, productOrigin);
    }

    /**
     * Sends POST request to addproduct mapping
     * @param str
     * @param cookie
     */
    @Transactional
    @RequestMapping(value = "/addproduct", method = RequestMethod.POST)
    public void addproduct(
            @RequestBody String str, @RequestHeader(value = "Cookie") String cookie
    ) {
        JSONObject jsonObj = new JSONObject(str);
        User user = sessionToUser.sessionToUser(cookie);
        String countryName = jsonObj.getString("country_name");
        Date date = Date.valueOf(jsonObj.getString("date"));
        Time time = Time.valueOf(jsonObj.getString("time"));


        Country origin = countryRepository.findByName(countryName);

        int countryBoughtInId = userRepository.findByUsername(user.getUsername()).getCountry();
        Country countryBoughtIn = countryRepository.findByCountryId(countryBoughtInId);

        double totalScore = calculateScoreByContinentDistance(continentDistanceRepository,
                countryBoughtIn.getContinent_id(), origin.getContinent_id());

        ActivityType activityType = activityTypeRepository.findById(2);
        Activity activity = new Activity(totalScore, date, time);
        activity.setActivityType(activityType);
        activity.setUser(user);

        activityRepository.save(activity);


        double oldScore = userRepository.findByUsername(user.getUsername()).getScore();
        double newScore = oldScore + totalScore;
        userRepository.setUserScoreByUsername(newScore, user.getUsername());

        String productName = jsonObj.getString("productName");
        Continent continent = origin.getContinent();
        Product product = new Product(productName);
        product.setActivity(activity);
        product.setContinent(continent);

        productRepository.save(product);

    }

}
