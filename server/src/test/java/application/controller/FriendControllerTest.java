package application.controller;

import application.SessionBasedMockMvc;
import application.model.FriendWith;
import application.model.User;
import application.repository.FriendWithRepository;
import application.repository.UserRepository;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;
import java.util.List;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class FriendControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    private Filter springSecurityFilterChain;

    @Autowired
    private SessionBasedMockMvc sessionBasedMockMvc;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int port;

    public void setUp(String name, String password) {
        mockMvc = sessionBasedMockMvc.createSessionBasedMockMvc(name, password, webApplicationContext, springSecurityFilterChain, port, testRestTemplate);
    }

    @Autowired
    FriendWithRepository friend_withRepository;

    @Test
    @Transactional
    public void add_friends_NotNull()
            throws Exception {
        setUp("nafieTest", "password");
        JSONObject testJson = new JSONObject();
        testJson.put("username", "Waqas");
        testJson.put("friendName", "Wuq Abbasi");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/addfriend")
                .accept(MediaType.APPLICATION_JSON).content(testJson.toString())
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals("true", response.getContentAsString());
    }

    @Test
    @Transactional
    public void add_friends_User2Null()
            throws Exception {
        setUp("nafieTest", "password");
        JSONObject testJson = new JSONObject();
        testJson.put("username", "Waqas");
        testJson.put("friendName", "");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/addfriend")
                .accept(MediaType.APPLICATION_JSON).content(testJson.toString())
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();
        assertEquals("false", response.getContentAsString());
    }

    @Test
    @Transactional
    public void addFriendSamePerson()
            throws Exception {
        setUp("nafieTest", "password");
        JSONObject testJson = new JSONObject();
        testJson.put("friendName", "Do not delete");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/addfriend")
                .accept(MediaType.APPLICATION_JSON).content(testJson.toString())
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals("false", response.getContentAsString());
    }


    @Test
    @Transactional
    public void add_friends_with()
            throws Exception {
        setUp("nafieTest", "password");
        User user = userRepository.findByUsername("nafieTest");

        JSONObject testJson = new JSONObject();
        testJson.put("username2", "Wuqas");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/add_friends_with")
                .accept(MediaType.APPLICATION_JSON).content(testJson.toString())
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        User u1 = userRepository.findByUsername("nafieTest");
        User u2 = userRepository.findByUsername("Wuqas");

        FriendWith test_friendship1 = new FriendWith();
        test_friendship1.setUser1(u1);
        test_friendship1.setUser2(u2);

        FriendWith test_friendship2 = friend_withRepository.findByUser1AndUser2(u1, u2);

        assertEquals(test_friendship1.getUser_id_1(), test_friendship2.getUser_id_1());
        // assertEquals(test_friendship1.getUser_id_2(), test_friendship2.getUser_id_2);

        friend_withRepository.deleteById(test_friendship2.getId());
    }

    @Test
    public void friends_with_selection_complete_name() throws Exception {
        setUp("nafieTest", "password");
        JSONObject testJson = new JSONObject();
        testJson.put("name", "Waqas Abbasi");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/friends_with_selection")
                .accept(MediaType.APPLICATION_JSON).content(testJson.toString())
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals("[\"" + "Waqas Abbasi" + "\"]", response.getContentAsString());
    }

    @Test
    public void friends_with_selection_search_engine() throws Exception {
        setUp("nafieTest", "password");
        JSONObject testJson = new JSONObject();
        testJson.put("name", "Wa");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/friends_with_selection")
                .accept(MediaType.APPLICATION_JSON).content(testJson.toString())
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        List<User> listUsers = userRepository.findByNameStartsWithIgnoreCase("Wa");


        String resultString = "[\"";
        for (int i = 0; i < listUsers.size() - 1; i++) {
            resultString = resultString + listUsers.get(i).getName() + "\",\"";
        }
        resultString = resultString + listUsers.get(listUsers.size() - 1).getName() + "\"]";
        assertEquals(resultString, response.getContentAsString());
    }

    @Test
    @Transactional
    public void get_friends_user_isUser1ANdUSer2()
            throws Exception {

        setUp("WaqasAA", "Kudos");

        User user = userRepository.findByUsername("WaqasAA");
        User u2 = userRepository.findByName("was bas");
        User u1 = userRepository.findByName("Wuq Abbasi");


        FriendWith friendship1 = new FriendWith();
        friendship1.setUser1(user);
        friendship1.setUser2(u1);
        friend_withRepository.save(friendship1);

        FriendWith friendship2 = new FriendWith();
        friendship2.setUser1(user);
        friendship2.setUser2(u2);
        friend_withRepository.save(friendship2);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/getfriends")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals("[\"" + u1.getName() + "," + u1.getScore() + "\",\"" +
                u2.getName() + "," + u2.getScore() +
                "\"]", response.getContentAsString());

        friend_withRepository.deleteFriend_withById(friend_withRepository.findByUser1AndUser2(user, u1).getId());
        friend_withRepository.deleteFriend_withById(friend_withRepository.findByUser1AndUser2(user, u2).getId());
    }

    @Test
    @Transactional
    public void get_friends_user_isUser1()
            throws Exception {

        setUp("WaqasAA", "Kudos");

        User user = userRepository.findByUsername("WaqasAA");
        User otherUser = userRepository.findByName("Wuq Abbasi");


        FriendWith friendship1 = new FriendWith();
        friendship1.setUser1(user);
        friendship1.setUser2(otherUser);
        friend_withRepository.save(friendship1);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/getfriends")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals("[\"" + otherUser.getName() + "," + otherUser.getScore() +
                "\"]", response.getContentAsString());

        friend_withRepository.deleteFriend_withById(friend_withRepository.findByUser1AndUser2(user, otherUser).getId());
    }

    @Test
    @Transactional
    public void get_friends_user_isUser2()
            throws Exception {

        setUp("WaqasAA", "Kudos");

        User user = userRepository.findByUsername("WaqasAA");
        User otherUser = userRepository.findByName("Wuq Abbasi");


        FriendWith friendship1 = new FriendWith();
        friendship1.setUser1(otherUser);
        friendship1.setUser2(user);
        friend_withRepository.save(friendship1);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/getfriends")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals("[\"" + otherUser.getName() + "," + otherUser.getScore() +
                "\"]", response.getContentAsString());

        friend_withRepository.deleteFriend_withById(friend_withRepository.findByUser1AndUser2(otherUser, user).getId());
    }


    @Test
    @Transactional
    public void get_friends_IsNotIn_Friendship()
            throws Exception {
        setUp("nafieTest", "password");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/getfriends")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals("[]", response.getContentAsString());
    }


    @Test
    @Transactional
    public void delete_friend()
            throws Exception {
        setUp("nafieTest", "password");
        JSONObject testJson = new JSONObject();
        testJson.put("username", "Waqas");
        testJson.put("friendName", "Wuqas");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/deletefriend")
                .accept(MediaType.APPLICATION_JSON).content(testJson.toString())
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals("false", response.getContentAsString());

        User u1 = userRepository.findByUsername("Waqas");
        User u2 = userRepository.findByUsername("Wuqas");

        FriendWith test_friendship1 = new FriendWith();
        test_friendship1.setUser1(u1);
        test_friendship1.setUser2(u2);

        friend_withRepository.save(test_friendship1);

    }

    @Test
    @Transactional
    public void delete_friends_userIsUser1()
            throws Exception {
        setUp("Wuqas", "Kudos");
        User user = userRepository.findByUsername("Wuqas");

        JSONObject testJson = new JSONObject();
        testJson.put("friendName", "Waqas Abbasi");
        User user1 = userRepository.findByUsername("Waqas");

        FriendWith friendship1 = new FriendWith();
        friendship1.setUser1(user);
        friendship1.setUser2(user1);
        friend_withRepository.save(friendship1);


        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/deletefriend")
                .accept(MediaType.APPLICATION_JSON).content(testJson.toString())
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals("true", response.getContentAsString());

        friend_withRepository.deleteFriend_withById(friendship1.getId());
    }

    @Test
    @Transactional
    public void delete_friends_userIsUser2()
            throws Exception {
        setUp("Wuqas", "Kudos");
        User user = userRepository.findByUsername("Wuqas");

        JSONObject testJson = new JSONObject();
        testJson.put("friendName", "Waqas Abbasi");
        User user1 = userRepository.findByUsername("Waqas");

        FriendWith friendship1 = new FriendWith();
        friendship1.setUser1(user1);
        friendship1.setUser2(user);
        friend_withRepository.save(friendship1);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/deletefriend")
                .accept(MediaType.APPLICATION_JSON).content(testJson.toString())
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals("true", response.getContentAsString());

        friend_withRepository.deleteFriend_withById(friendship1.getId());
    }

    @Test
    @Transactional
    public void delete_friends_usersAreNotFriends()
            throws Exception {
        setUp("Wuqas", "Kudos");
        User user = userRepository.findByUsername("Wuqas");

        JSONObject testJson = new JSONObject();
        testJson.put("friendName", "Waqas Abbasi");
        User user1 = userRepository.findByUsername("Waqas");

        /*Friend_with friendship1 = new Friend_with();
        friendship1.setUser1(user1);
        friendship1.setUser2(user);
        friend_withRepository.save(friendship1);*/

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/deletefriend")
                .accept(MediaType.APPLICATION_JSON).content(testJson.toString())
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals("false", response.getContentAsString());

        /*friend_withRepository.deleteFriend_withById(friendship1.getId());*/
    }

    /*@Test
    @Transactional
    public void get_friends_userIsBoth()
            throws Exception{

        User user = new User("test", "test", passwordEncoder.encode("test"),38,5.28*149.25);
        User user1 = new User("testT", "testT", passwordEncoder.encode("test"),38,5.28*149.25);
        User user2 = new User("testTT", "testTT", passwordEncoder.encode("test"),38,5.28*149.25);
        userRepository.save(user);
        userRepository.save(user1);
        userRepository.save(user2);

        JSONObject testJson = new JSONObject();
        testJson.put("username", "test");

        Friend_with friendship1 = new Friend_with();
        friendship1.setUser1(user);
        friendship1.setUser2(user1);
        friend_withRepository.save(friendship1);

        Friend_with friendship2 = new Friend_with();
        friendship2.setUser1(user2);
        friendship2.setUser2(user);
        friend_withRepository.save(friendship2);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/getfriends")
                .accept(MediaType.APPLICATION_JSON).content(testJson.toString())
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals("[\"testT,788.0400000000001\",\"testTT,788.0400000000001\"]", response.getContentAsString());

        friend_withRepository.deleteFriend_withById(friendship1.getId());
        friend_withRepository.deleteFriend_withById(friendship2.getId());
        userRepository.deleteUserByUsername("test");
        userRepository.deleteUserByUsername("testT");
        userRepository.deleteUserByUsername("testTT");
    }*/
}
