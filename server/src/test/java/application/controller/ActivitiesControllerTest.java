package application.controller;

import application.SessionBasedMockMvc;
import application.model.User;
import application.repository.HasAchievementRepository;
import application.repository.UserRepository;
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

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ActivitiesControllerTest {

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
    public void getActivities5OrMore() throws Exception {
        setUp("nafieTest", "password");
        User user = userRepository.findByUsername("nafieTest");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/myactivities")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        String content = response.getContentAsString().replace("\"", "");
        String regex = "\\Q},{\\E";

        assertTrue(content.split(regex).length >= 5);
    }

    @Test
    @Transactional
    public void getActivitiesLessThan5() throws Exception {
        setUp("testNafie2", "nafie2");
        User user = userRepository.findByUsername("nafieTest");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/myactivities")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        String content = response.getContentAsString().replace("\"", "");
        String regex = "\\Q},{\\E";

        assertTrue(content.split(regex).length < 5);
    }
}