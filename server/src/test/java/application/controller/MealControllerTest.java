package application.controller;

import application.SessionBasedMockMvc;
import application.model.User;
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
public class MealControllerTest {

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
  public void addMealTest() throws Exception {
    setUp("nafieTest", "password");
    User user = userRepository.findByUsername("nafieTest");

    JSONObject testJson = new JSONObject();
    testJson.put("foodList", "Lamb");
    testJson.put("date", "2019-03-17");
    testJson.put("time", "04:58:01");

    RequestBuilder requestBuilder = MockMvcRequestBuilders
      .post("/addmeal")
      .accept(MediaType.APPLICATION_JSON).content(testJson.toString())
      .contentType(MediaType.APPLICATION_JSON);

    MvcResult result = mockMvc.perform(requestBuilder).andReturn();

    String content = result.getResponse().getContentAsString();

    assertEquals(user.toJson().toString(), content);

  }

  @Test
  @Transactional
  public void getLastMeals() throws Exception{
    setUp("daeba", "test123");
    RequestBuilder requestBuilder = MockMvcRequestBuilders
            .get("/getmeals")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON);

    MvcResult result = mockMvc.perform(requestBuilder).andReturn();

    String content = result.getResponse().getContentAsString().replace("\"", "");
    String regex = "\\Q},{\\E";
    assertEquals(2, content.split(regex).length, 0);
  }
}
