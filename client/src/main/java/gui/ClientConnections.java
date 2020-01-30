package gui;

import java.util.Base64;
import java.util.Collections;
import java.util.Objects;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class ClientConnections {

  private String urlHeroku = "https://warm-shelf-72548.herokuapp.com/";

//  private String local = "http://localhost:8443/";

  private RestTemplate restTemplate = new RestTemplate();
  private static String session = "";

  public ResponseEntity<String> sendPost(String str, String mapping) {
    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    headers.add("Cookie", session);
    HttpEntity<String> entity = new HttpEntity<>(str, headers);
    String url = urlHeroku + mapping;
    return restTemplate
        .exchange(url, HttpMethod.POST, entity, String.class);
  }

  ResponseEntity<String> sendGet(String mapping) {
    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    headers.add("Cookie", session);
    HttpEntity<String> entity = new HttpEntity<>(headers);
    String url = urlHeroku + mapping;
    return restTemplate
          .exchange(url, HttpMethod.GET, entity, String.class);
  }

  // TODO Clean up and add comments
  Boolean sendGetAuth(String username, String password) {
    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    String notEncoded = username + ":" + password;
    String encodedAuth = "Basic " + Base64.getEncoder().encodeToString(notEncoded.getBytes());
    headers.add("Authorization", encodedAuth);
    String url = urlHeroku + "login";
    RestTemplate restTemplate = new RestTemplate();
    try {
      HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
      ResponseEntity<String> response = restTemplate
          .exchange(url, HttpMethod.GET, entity, String.class);
      session = Objects.requireNonNull(response.getHeaders().get("Set-Cookie")).toString();
      session = session.replaceAll("\\[", "").replaceAll("]", "");
      return true;
    } catch (Exception eek) {
      return false;
    }
  }
}
