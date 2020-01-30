package application.controller;

import application.model.User;
import application.repository.UserRepository;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(
  webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class RegisterControllerTest {

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Autowired
  TestRestTemplate testRestTemplate;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Test
  @Transactional
  public void registerUser()
    throws Exception {
    MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    User testUser = new User("test", "test", passwordEncoder.encode("test"), 38, 125408.46505365895);

    JSONObject testJson = new JSONObject();
    testJson.put("username", "test");
    testJson.put("name", "test");
    testJson.put("password", "test");
    testJson.put("country", "Netherlands");

    RequestBuilder requestBuilder = MockMvcRequestBuilders
      .post("/register")
      .accept(MediaType.APPLICATION_JSON).content(testJson.toString())
      .contentType(MediaType.APPLICATION_JSON);

    mockMvc.perform(requestBuilder);

    User u = userRepository.findByUsername("test");

    assertEquals(u.getUsername(), testUser.getUsername());
    assertEquals(u.getName(), testUser.getName());
    assertEquals(u.getCountry(), testUser.getCountry());
    assertEquals(u.getScore(), testUser.getScore(), 0);
    assertTrue(passwordEncoder.matches("test", u.getPassword()));

    requestBuilder = MockMvcRequestBuilders
      .post("/register")
      .accept(MediaType.APPLICATION_JSON).content(testJson.toString())
      .contentType(MediaType.APPLICATION_JSON);

    MvcResult result = mockMvc.perform(requestBuilder).andReturn();

    String content = result.getResponse().getContentAsString();

    assertEquals("false", content);

    userRepository.deleteUserByUsername("test");
  }
}