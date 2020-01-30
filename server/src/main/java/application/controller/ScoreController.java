package application.controller;

import application.repository.UserRepository;
import application.security.SessionToUser;
import application.model.User;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ScoreController {

    @Autowired
    private SessionToUser sessionToUser;

    @Autowired
    private UserRepository userRepository;

    /**
     * Gets score.
     * Sends GET request to getscore mapping
     * @param cookie the cookie
     * @return the score
     */
    @RequestMapping(value = "/getscore", method = RequestMethod.GET)
    public String getScore(
            @RequestHeader(value = "Cookie") String cookie
    ) {
        User user = sessionToUser.sessionToUser(cookie);
        return "" + user.getScore();
    }

    @FunctionalInterface
    interface projectItems {
        String project(User user);
    }

    projectItems projectUsernameAndScore = user -> {
        JSONObject res = new JSONObject();
        res.put("username", user.getUsername());
        res.put("score", user.getScore());
        return res.toString();
    };

    @RequestMapping(value = "/top", method = RequestMethod.GET)
    public List<String> getTopUsers() {
        return userRepository.findAll(Sort.by("score").descending())
                .stream()
                .map(projectUsernameAndScore::project)
                .limit(10)
                .collect(Collectors.toList());
    }
}
