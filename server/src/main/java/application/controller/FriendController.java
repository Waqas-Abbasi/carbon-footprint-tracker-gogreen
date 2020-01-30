package application.controller;

import application.model.FriendWith;
import application.model.Friendinfo;
import application.model.User;
import application.repository.FriendWithRepository;
import application.repository.UserRepository;
import application.security.SessionToUser;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

import java.util.*;

@RestController
public class FriendController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FriendWithRepository friend_withRepository;

    @Autowired
    private SessionToUser sessionToUser;

    /**
     * sends post request to friends_with_selection mapping
     */
    @Transactional
    @RequestMapping(value = "/friends_with_selection", method = RequestMethod.POST)
    public List<String> friends_with_selection(
            @RequestBody String string
    ) {
        JSONObject jsonObj = new JSONObject(string);
        String name = jsonObj.getString("name");
        User user2 = userRepository.findByNameLikeIgnoreCase(name);
        List<User> list_users = new ArrayList<>();
        int number_letters = name.length();
        if (user2 == null) {
            list_users = userRepository.findByNameStartsWithIgnoreCase(name.substring(0, number_letters) + "%");
        } else {
            list_users.add(user2);
        }

        List<String> list_name = new ArrayList<>();
        for (int i = 0; i < list_users.size(); i++) {
            list_name.add(list_users.get(i).getName());
        }

        return list_name;
    }


    /**
     * Sends get request to add_friends_wit, throws exception
     * @param string
     * @param cookie
     * @return
     * @throws JSONException
     */
    @Transactional
    @RequestMapping(value = "/add_friends_with", method = RequestMethod.GET)
    public int add_friends_with(
            @RequestBody String string, @RequestHeader(value = "Cookie") String cookie
    ) throws JSONException {
        JSONObject jsonObj = new JSONObject(string);
        String username2 = jsonObj.getString("username2");
        User user1 = sessionToUser.sessionToUser(cookie);
        User user2 = userRepository.findByUsername(username2);

        FriendWith friend_with = new FriendWith();
        friend_with.setUser1(user1);
        friend_with.setUser2(user2);
        friend_withRepository.save(friend_with);

        FriendWith user = friend_withRepository.findByUser1AndUser2(user1, user2);
        return user.getId();
    }

    /**
     * sends post request containing data to addfriend mapping
     * @param string
     * @param cookie
     * @return
     */
    @Transactional
    @RequestMapping(value = "/addfriend", method = RequestMethod.POST)
    public String addFriend(
            @RequestBody String string, @RequestHeader(value = "Cookie") String cookie
    ) {
        JSONObject obj = new JSONObject(string);
        String friendName = obj.getString("friendName");

        User user1 = sessionToUser.sessionToUser(cookie);
        User user2 = userRepository.findByName(friendName);

        if (user1.equals(user2)) {
            return "false";
        }

        if (user2 == null) {
            return "false";
        }
        FriendWith friend_with = new FriendWith();
        friend_with.setUser1(user1);
        friend_with.setUser2(user2);
        friend_withRepository.save(friend_with);

        return "true";
    }

    /**
     * sends GET request containing data to getfriends mapping
     * @param cookie the cookie
     * @return
     */
    @Transactional
    @RequestMapping(value = "/getfriends", method = RequestMethod.GET)
    public List<String> getFriends(
            @RequestHeader(value = "Cookie") String cookie
    ) {
        User user = sessionToUser.sessionToUser(cookie);

        List<FriendWith> friendsList = new ArrayList<>();

        int i = 0;
        List<String> list = new ArrayList<>();
        //       if (friend_withRepository.findByUser1(user) != null) {
        List<FriendWith> friendsList1 = friend_withRepository.findByUser1(user);
        friendsList.addAll(friendsList1);
        for (; i < friendsList.size(); i++) {
            User user2 = friendsList.get(i).getUser2();
            Friendinfo friend = new Friendinfo(user2.getName(), user2.getScore());
            list.add(friend.toString());
        }
        //       }

//        if (friend_withRepository.findByUser2(user) != null) {
        List<FriendWith> friendsList2 = friend_withRepository.findByUser2(user);
        friendsList.addAll(friendsList2);
        for (; i < friendsList.size(); i++) {
            User user1 = friendsList.get(i).getUser1();
            Friendinfo friend = new Friendinfo(user1.getName(), user1.getScore());
            list.add(friend.toString());
        }
        //       }
        return list;
    }


    /**
     * sends post request containing data to deletefriend mapping
     * @param string friend to terminate
     * @param cookie 
     * @return
     */
    @Transactional
    @RequestMapping(value = "/deletefriend", method = RequestMethod.POST)
    public String deleteFriend(
            @RequestBody String string, @RequestHeader(value = "Cookie") String cookie
    ) {
        JSONObject obj = new JSONObject(string);
        String friendName = obj.getString("friendName");
        User user = sessionToUser.sessionToUser(cookie);
        User user2 = userRepository.findByName(friendName);

        if (friend_withRepository.findByUser1AndUser2(user, user2) != null) {
            friend_withRepository.deleteFriend_withById(friend_withRepository.findByUser1AndUser2(user, user2).getId());
            return "true";
        } else if (friend_withRepository.findByUser1AndUser2(user2, user) != null) {
            friend_withRepository.deleteFriend_withById(friend_withRepository.findByUser1AndUser2(user2, user).getId());
            return "true";
        } else {
            return "false";
        }
    }

}

