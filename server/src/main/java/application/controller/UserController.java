package application.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

// TODO Clean up and add comments

@RestController
public class UserController {

    /**
     * Receives user credentials when POST request is requested from /login
     * and returns whether the given credentials are correct.
     *
     * @return if the credentials are a valid combinations
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String login(HttpServletRequest request) {
        return request.getSession(true).getId();
    }

    @RequestMapping("/destroy")
    public String destroySession(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/";
    }
}
