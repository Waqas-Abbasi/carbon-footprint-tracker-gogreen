package application.controller;

import application.SessionBasedMockMvc;
import application.model.Achievement;
import application.model.HasAchievement;
import application.model.User;
import application.repository.HasAchievementRepository;
import application.repository.UserRepository;
//import org.junit.jupiter.api.Test;
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
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AchievementControllerTest {

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

    @Autowired
    HasAchievementRepository hasAchievementRepository;

    @LocalServerPort
    private int port;

    public void setUp(String name, String password) {
        mockMvc = sessionBasedMockMvc.createSessionBasedMockMvc(name, password, webApplicationContext, springSecurityFilterChain, port, testRestTemplate);
    }

    @Test
    @Transactional
    public void assignAchievement_firstActivity_UserHas1Activity_No_achievement() throws Exception {
        setUp("nafieTest", "password");
        User user = userRepository.findByUsername("nafieTest");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/myachievements_first_activity")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals("The achievement : \" First activity \" has been added", response.getContentAsString());

        HasAchievement helper = hasAchievementRepository.findByUser_idAndAchievement_id(user.getId(), 1);
        hasAchievementRepository.deleteById(helper.getId());
    }

    @Test
    @Transactional
    public void assignAchievement_firstActivity_UserHas1Activity_DoNotQualify_achievement() throws Exception {
        setUp("Wuqas", "Kudos");
        User user = userRepository.findByUsername("Wuqas");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/myachievements_first_activity")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals("This user doesn't qualify for this achievement", response.getContentAsString());
    }

    @Test
    @Transactional
    public void assignAchievement_firstActivity_UserHas1Activity_Has_achievement() throws Exception {
        setUp("Waqas", "Kudos");
        User user = userRepository.findByUsername("Waqas");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/myachievements_first_activity")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals("The achievement : \" First activity \" already exists", response.getContentAsString());
    }

    @Test
    @Transactional
    public void assignAchievement_10_Activities_UserHas10Activities_No_achievement() throws Exception {
        setUp("daeba", "test123");
        User user = userRepository.findByUsername("daeba");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/myachievements_10_activities")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals("The achievement : \" First 10 activity \" has been added", response.getContentAsString());

        HasAchievement helper = hasAchievementRepository.findByUser_idAndAchievement_id(user.getId(), 2);
        hasAchievementRepository.deleteById(helper.getId());
    }

    @Test
    @Transactional
    public void assignAchievement_10_Activities_UserHas10Activities_DoNotQualify_achievement() throws Exception {
        setUp("WaqasDS", "Kudos");
        User user = userRepository.findByUsername("WaqasDS");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/myachievements_10_activities")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals("This user doesn't qualify for this achievement", response.getContentAsString());
    }

    @Test
    @Transactional
    public void assignAchievement_10_Activities_UserHas10Activities_Has_achievement() throws Exception {
        setUp("Waqas", "Kudos");
        User user = userRepository.findByUsername("Waqas");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/myachievements_10_activities")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals("The achievement \" First 10 activity \" already exists", response.getContentAsString());
    }

    @Test
    @Transactional
    public void assignAchievement_first_friend_UserHas1friend_No_achievement() throws Exception {
        setUp("testNafie2", "nafie2");
        User user = userRepository.findByUsername("nafieTest2");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/myachievements_first_friend")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals("The achievement : \" First friend \" has been added", response.getContentAsString());
    }

    @Test
    @Transactional
    public void assignAchievement_first_friend_UserHas1friend_Has_achievement() throws Exception {
        setUp("Waqas", "Kudos");
        User user = userRepository.findByUsername("Waqas");
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/myachievements_first_friend")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals("The achievement : \" First friend \" already exists", response.getContentAsString());
    }

    @Test
    @Transactional
    public void assignAchievement_first_friend_UserHas1friend_DoNotQualify_achievement() throws Exception {
        setUp("nafieTest", "password");
        User user = userRepository.findByUsername("nafieTest");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/myachievements_first_friend")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals("This user doesn't qualify for this achievement", response.getContentAsString());
    }

    @Test
    @Transactional
    public void assignAchievement_first10_friend_UserHas10friend_No_achievement() throws Exception {
        setUp("daeba", "test123");
        User user = userRepository.findByUsername("daeba");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/myachievements_10_friends")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals("The achievement : \" First 10 friends \" has been added", response.getContentAsString());

        HasAchievement helper = hasAchievementRepository.findByUser_idAndAchievement_id(user.getId(), 5);
        hasAchievementRepository.deleteById(helper.getId());
    }

    @Test
    @Transactional
    public void assignAchievement_first10_friend_UserHas10friend_DoNotQualify_achievement() throws Exception {
        setUp("nafieTest", "password");
        User user = userRepository.findByUsername("nafieTest");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/myachievements_10_friends")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals("This user doesn't qualify for this achievement", response.getContentAsString());
    }

    @Test
    @Transactional
    public void assignAchievement_first10_friend_UserHas10friend_Has_achievement() throws Exception {
        setUp("testNafie1", "nafie");
        User user = userRepository.findByUsername("testNafie1");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/myachievements_10_friends")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals("The achievement : \" First 10 friends \" already exists", response.getContentAsString());
    }

    @Test
    @Transactional
    public void assignAchievement_solar_panel_No_achievement() throws Exception {
        setUp("testNafie1", "nafie");
        User user = userRepository.findByUsername("testNafie1");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/myachievements_solar_panel")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals("The achievement : \" Solar captain. \" has been added", response.getContentAsString());

        HasAchievement helper = hasAchievementRepository.findByUser_idAndAchievement_id(user.getId(), 6);
        hasAchievementRepository.deleteById(helper.getId());
    }

    @Test
    @Transactional
    public void assignAchievement_solar_panel_DONOT_Qualify_achievement() throws Exception {
        setUp("Wuqas", "Kudos");
        User user = userRepository.findByUsername("Wuqas");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/myachievements_solar_panel")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals("This user doesn't qualify for this achievement", response.getContentAsString());
    }

    @Test
    @Transactional
    public void assignAchievement_solar_panel_Has_achievement() throws Exception {
        setUp("daeba", "test123");
        User user = userRepository.findByUsername("daeba");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/myachievements_solar_panel")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals("The achievement : \" Solar captain. \" already exists", response.getContentAsString());
    }

    @Test
    @Transactional
    public void getAchievements_UserNotNull() throws Exception {
        setUp("testNafie1", "nafie");
        User user = userRepository.findByUsername("testNafie1");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/getachievement")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();


        List<HasAchievement> list_result = hasAchievementRepository.findAllByUser_id(user.getId());


        List<Achievement> list = new ArrayList<>();
        for(int i = 0; i < list_result.size(); i++) {
            list.add(list_result.get(i).getAchievement());
        }
        String resultString = "[";
        for(int i = 0; i < list.size()-1; i++){
            resultString =  resultString + list.get(i).toString()+ ",";
        }
        resultString = resultString + list.get(list.size()-1).toString() + "]";

        assertEquals(resultString, response.getContentAsString());
    }


}
