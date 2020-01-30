package application;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import javax.servlet.Filter;
import java.util.Arrays;
import java.util.Base64;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@Component
public class SessionBasedMockMvc {

  private String session;

  public MockMvc createSessionBasedMockMvc(String username, String password, WebApplicationContext webApplicationContext, Filter springSecurityFilterChain, int port, TestRestTemplate testRestTemplate) {
    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    String notEncoded = username + ":" + password;
    String encodedAuth = "Basic " + Base64.getEncoder().encodeToString(notEncoded.getBytes());
    headers.add("Authorization", encodedAuth);
    HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
    session = (testRestTemplate.exchange(
      "http://localhost:" + port + "/login", HttpMethod.GET, entity, String.class).getHeaders().get("Set-Cookie")).toString();
    session = session.replaceAll("\\[", "").replaceAll("\\]", "");
    HttpHeaders headersSession = new HttpHeaders();
    headersSession.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    headersSession.add("Cookie", session);
    final MockHttpServletRequestBuilder defaultRequestBuilder = get("/").secure(true).headers(headersSession).with(httpBasic(username, password));
    return MockMvcBuilders.webAppContextSetup(webApplicationContext)
      .defaultRequest(defaultRequestBuilder)
      .alwaysDo(result -> setSessionBackOnRequestBuilder(defaultRequestBuilder, result.getRequest())).alwaysDo(print())
      .apply(springSecurity(springSecurityFilterChain))
      .build();
  }

  private MockHttpServletRequest setSessionBackOnRequestBuilder(final MockHttpServletRequestBuilder requestBuilder,
                                                                final MockHttpServletRequest request) {
    requestBuilder.session((MockHttpSession) request.getSession());
    return request;
  }

  public void removeSession(int port, TestRestTemplate testRestTemplate){
    HttpHeaders headersSession = new HttpHeaders();
    headersSession.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    headersSession.add("Cookie", session);
    HttpEntity<String> entityDestr = new HttpEntity<>("parameters", headersSession);
    testRestTemplate.exchange(
      "http://localhost:" + port + "/destroy", HttpMethod.GET, entityDestr, String.class);
  }
}