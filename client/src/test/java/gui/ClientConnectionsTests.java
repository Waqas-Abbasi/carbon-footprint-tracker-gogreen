package gui;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;


import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@SpringBootConfiguration
public class ClientConnectionsTests {

    ClientConnections clientConnections = Mockito.mock(ClientConnections.class);

    @Test
    public void sendPost() {
        JSONObject obj = new JSONObject();
        obj.put("username", "Waqas");
        obj.put("password", "Kudos");
        String str = obj.toString();

        Mockito.when(clientConnections.sendPost(str, "login")).thenReturn(new ResponseEntity<String>(HttpStatus.OK));

        assertEquals(new ResponseEntity<String>(HttpStatus.OK), clientConnections.sendPost(str, "login"));
    }

}
