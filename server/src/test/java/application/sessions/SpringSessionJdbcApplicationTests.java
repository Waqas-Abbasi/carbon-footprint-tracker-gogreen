package application.sessions;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest(
  webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SpringSessionJdbcApplicationTests {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate testRestTemplate;

  private List<String> getSessionIdsFromDatabase()
    throws SQLException {

    List<String> result = new ArrayList<>();
    ResultSet rs = getResultSet(
      "SELECT * FROM SPRING_SESSION");

    while (rs.next()) {
      result.add(rs.getString("SESSION_ID"));
    }
    return result;
  }

  private ResultSet getResultSet(String sql)
    throws SQLException {

    Connection conn = DriverManager
      .getConnection("jdbc:postgresql://ec2-54-225-89-195.compute-1.amazonaws.com:5432/davodlfjcsueja", "kpzjjqjugvaxmh", "84462c2419ec113db877c9ea47b30764df527ba99317ac253fc2d37983af7b4d");
    Statement stat = conn.createStatement();
    return stat.executeQuery(sql);
  }

  @Test
  public void whenPostgresDbIsQueried_thenOneSessionIsCreated()
    throws SQLException {
    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    String notEncoded = "Waqas" + ":" + "Kudos";
    String encodedAuth = "Basic " + Base64.getEncoder().encodeToString(notEncoded.getBytes());
    headers.add("Authorization", encodedAuth);
    HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
    String session = this.testRestTemplate.exchange(
      "http://localhost:" + port + "/login", HttpMethod.GET, entity, String.class).getHeaders().get("Set-Cookie").toString();
    session = session.replaceAll("\\[", "").replaceAll("\\]", "");
    HttpHeaders headersSession = new HttpHeaders();
    headersSession.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    headersSession.add("Cookie", session);
    HttpEntity<String> entityDestr = new HttpEntity<>("parameters", headersSession);
    this.testRestTemplate.exchange(
      "http://localhost:" + port + "/destroy", HttpMethod.GET, entityDestr, String.class);
    assertEquals(0, getSessionIdsFromDatabase().size());
  }
}
