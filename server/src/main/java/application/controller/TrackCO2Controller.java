package application.controller;


import application.model.User;
import application.security.SessionToUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TrackCO2Controller {

    @Autowired
    private SessionToUser sessionToUser;

    /**
     * Gets CO2.
     *
     * @param cookie the cookie
     * @return the co2 emission
     */
    @RequestMapping(value = "/getemission")
    public String getCo2(
            @RequestHeader(value = "Cookie") String cookie
    ) {
        User user = sessionToUser.sessionToUser(cookie);
        double result = 15 * Math.exp(-user.getScore() / 99999) + 1;
        return "" + result;
    }
}
