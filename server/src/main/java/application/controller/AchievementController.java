package application.controller;

import application.model.Achievement;
import application.model.HasAchievement;
import application.model.User;
import application.repository.AchievementRepository;
import application.repository.ActivityRepository;
import application.repository.FriendWithRepository;
import application.repository.HasAchievementRepository;
import application.security.SessionToUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
public class AchievementController {

    @Autowired
    private HasAchievementRepository hasAchievementRepository;

    @Autowired
    private AchievementRepository achievementRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private FriendWithRepository friendWithRepository;

    @Autowired
    private SessionToUser sessionToUser;

    /**
     * Sends POST request to myachievements_first_activity mapping
     * @param cookie the cookie
     * @return string
     */
    @Transactional
    @RequestMapping(value = "/myachievements_first_activity", method = RequestMethod.POST)
    public String assignAchievement_firstActivity(
            @RequestHeader(value = "Cookie") String cookie
    ) {
        User user = sessionToUser.sessionToUser(cookie);

        int number_activities = activityRepository.countAllByUser(user.getId());
        if (number_activities >= 1) {
            int helper1 = hasAchievementRepository.findByUser_idAndAchievement_id1(user.getId(), 1);
            if (helper1 == 0) {
                Achievement achievement1 = achievementRepository.findByAchievementID(1);

                HasAchievement hasAchievement1 = new HasAchievement();
                hasAchievement1.setUser(user);
                hasAchievement1.setAchievement(achievement1);
                hasAchievement1.setDate(LocalDate.now());
                hasAchievementRepository.save(hasAchievement1);
                return "The achievement : \" First activity \" has been added";
            } else {
                return "The achievement : \" First activity \" already exists";
            }
        }
        return "This user doesn't qualify for this achievement";
    }

    /**
     * Sends POST request to myachievements_10_activities mapping
     * @param cookie the cookie
     * @return string
     */
    @Transactional
    @RequestMapping(value = "/myachievements_10_activities", method = RequestMethod.POST)
    public String assignAchievement_10_activities(
            @RequestHeader(value = "Cookie") String cookie
    ) {
        User user = sessionToUser.sessionToUser(cookie);

        int number_activities = activityRepository.countAllByUser(user.getId());
        if (number_activities >= 10) {
            int helper = hasAchievementRepository.findByUser_idAndAchievement_id1(user.getId(), 2);
            if (helper == 0) {
                Achievement first_10_activities = achievementRepository.findByAchievementID(2);
                HasAchievement hasAchievement2 = new HasAchievement(LocalDate.now());
                hasAchievement2.setUser(user);
                hasAchievement2.setAchievement(first_10_activities);
                hasAchievementRepository.save(hasAchievement2);
                return "The achievement : \" First 10 activity \" has been added";
            } else {
                return "The achievement \" First 10 activity \" already exists";
            }
        } else {
            return "This user doesn't qualify for this achievement";
        }
    }

    /**
     * maps to myachievements_first_friend
     * @param cookie the cookie
     * @return string
     */
    @Transactional
    @RequestMapping(value = "/myachievements_first_friend", method = RequestMethod.POST)
    public String assignAchievement_first_friend(
            @RequestHeader(value = "Cookie") String cookie
    ) {
        User user = sessionToUser.sessionToUser(cookie);

        int number_friends = friendWithRepository.countAllByUser1OrUser2(user.getId(), user.getId());
        if (number_friends >= 1) {
            int helper = hasAchievementRepository.findByUser_idAndAchievement_id1(user.getId(), 4);
            if (helper == 0) {
                Achievement first_friend = achievementRepository.findByAchievementID(4);
                HasAchievement hasAchievement3 = new HasAchievement(LocalDate.now());
                hasAchievement3.setUser(user);
                hasAchievement3.setAchievement(first_friend);
                hasAchievementRepository.save(hasAchievement3);
                return "The achievement : \" First friend \" has been added";
            } else {
                return "The achievement : \" First friend \" already exists";
            }
        } else {
            return "This user doesn't qualify for this achievement";
        }
    }

    /**
     * maps to myachievements_10_friends
     * @param cookie the cookie
     * @return string
     */
    @Transactional
    @RequestMapping(value = "/myachievements_10_friends", method = RequestMethod.POST)
    public String assignAchievement_10_friends(
            @RequestHeader(value = "Cookie") String cookie
    ) {
        User user = sessionToUser.sessionToUser(cookie);

        int number_friends = friendWithRepository.countAllByUser1OrUser2(user.getId(), user.getId());
        if (number_friends >= 10) {
            int helper = hasAchievementRepository.findByUser_idAndAchievement_id1(user.getId(), 5);
            if (helper == 0) {
                Achievement first_10_friends = achievementRepository.findByAchievementID(5);
                HasAchievement hasAchievement4 = new HasAchievement(LocalDate.now());
                hasAchievement4.setUser(user);
                hasAchievement4.setAchievement(first_10_friends);
                hasAchievementRepository.save(hasAchievement4);
                return "The achievement : \" First 10 friends \" has been added";
            } else {
                return "The achievement : \" First 10 friends \" already exists";
            }
        } else {
            return "This user doesn't qualify for this achievement";
        }
    }


    /**
     * maps to myachievements_solar_panel
     * @param cookie the cookie
     * @return string
     */
    @Transactional
    @RequestMapping(value = "/myachievements_solar_panel", method = RequestMethod.POST)
    public String assignAchievement_solar_panel(
            @RequestHeader(value = "Cookie") String cookie
    ) {
        User user = sessionToUser.sessionToUser(cookie);

        int number_solarPanel = activityRepository.CountAllByUserAndActivity_Type(user.getId(), 6);
        if (number_solarPanel >= 1) {
            int helper = hasAchievementRepository.findByUser_idAndAchievement_id1(user.getId(), 6);
            if (helper == 0) {
                Achievement first_solarPanel = achievementRepository.findByAchievementID(6);
                HasAchievement hasAchievement5 = new HasAchievement(LocalDate.now());
                hasAchievement5.setUser(user);
                hasAchievement5.setAchievement(first_solarPanel);
                hasAchievementRepository.save(hasAchievement5);
                return "The achievement : \" Solar captain. \" has been added";
            } else {
                return "The achievement : \" Solar captain. \" already exists";
            }
        } else {
            return "This user doesn't qualify for this achievement";
        }
    }


    /**
     * maps to getachievement
     * @param cookie the cookie
     * @return string
     */
    @Transactional
    @RequestMapping(value = "/getachievement", method = RequestMethod.POST)
    public List<Achievement> getAchievements(
            @RequestHeader(value = "Cookie") String cookie
    ) {
        User user = sessionToUser.sessionToUser(cookie);

        List<Achievement> list = new ArrayList<>();

        List<HasAchievement> list_has_achievement = hasAchievementRepository.findAllByUser_id(user.getId());

        for (int i = 0; i < list_has_achievement.size(); i++) {
            list.add(list_has_achievement.get(i).getAchievement());
        }
        return list;
    }
}

