package application.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Base64;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(
  webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserControllerTest {

  @LocalServerPort
  private int port;

  @Autowired
  TestRestTemplate testRestTemplate;

  @Test
  public void loginUserCorrectInfo() {
    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    String notEncoded = "Waqas" + ":" + "Kudos";
    String encodedAuth = "Basic " + Base64.getEncoder().encodeToString(notEncoded.getBytes());
    headers.add("Authorization", encodedAuth);
    HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
    ResponseEntity result = testRestTemplate.exchange(
      "http://localhost:" + port + "/login", HttpMethod.GET, entity, String.class);
    int content = result.getStatusCodeValue();
    assertEquals(202, content);
  }

  @Test
  public void loginUserWrongPassword() {
    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    String notEncoded = "Waqas" + ":" + "test";
    String encodedAuth = "Basic " + Base64.getEncoder().encodeToString(notEncoded.getBytes());
    headers.add("Authorization", encodedAuth);
    HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
    ResponseEntity result = testRestTemplate.exchange(
      "http://localhost:" + port + "/login", HttpMethod.GET, entity, String.class);
    int content = result.getStatusCodeValue();
    assertEquals(401, content);
  }

  @Test
  public void loginUserWrongUsername() {
    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    String notEncoded = "test" + ":" + "Kudos";
    String encodedAuth = "Basic " + Base64.getEncoder().encodeToString(notEncoded.getBytes());
    headers.add("Authorization", encodedAuth);
    HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
    ResponseEntity result = testRestTemplate.exchange(
      "http://localhost:" + port + "/login", HttpMethod.GET, entity, String.class);
    int content = result.getStatusCodeValue();
    assertEquals(401, content);
  }
}

