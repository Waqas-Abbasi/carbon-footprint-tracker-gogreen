package application.controller;

import application.model.Country;
import application.model.User;
import application.repository.CountryRepository;
import application.repository.UserRepository;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RegisterController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CountryRepository countryRepository;

    /**
     * Sends POST request to register mapping
     *
     * @param body nj
     * @return hh
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerInfo(
            @RequestBody String body
    ) {
        JSONObject jsonObj = new JSONObject(body);
        String name = jsonObj.getString("name");
        String username = jsonObj.getString("username");
        String password = jsonObj.getString("password");
        User customer = userRepository.findByUsername(username);

        if (customer == null) {
            String countryName = jsonObj.getString("country");

            Country country = countryRepository.findByName(countryName);
            double score = country.getAverage_Footprint();
            score = -99999 * Math.log((score - 1) / 15);

            //Encrypts Password
            password = passwordEncoder.encode(password);
            User user = new User(username, name, password, country.getCountryId(), score);
            userRepository.save(user);
            return "welcome";
        } else {
            return "false";
        }
    }
}
