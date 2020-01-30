package application.controller;

import application.SessionBasedMockMvc;
import application.model.User;
import application.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ScoreControllerTest {

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

    @Test
    @Transactional
    public void scoreTest()
            throws Exception{

        setUp("nafieTest", "password");
        User user = userRepository.findByUsername("nafieTest");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/getscore")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String content = result.getResponse().getContentAsString();

        assertEquals(user.getScore(), Double.parseDouble(content), 0);

    }


    @Test
    @Transactional
    public void getTopUsersTest() throws Exception{
        setUp("nafieTest", "password");
        User user = userRepository.findByUsername("nafieTest");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/top")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String content = result.getResponse().getContentAsString().replace("\"", "");
        String regex = "\\Q},{\\E";
        assertEquals(10, content.split(regex).length, 0);
    }
}
