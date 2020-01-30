package application.controller;

import application.SessionBasedMockMvc;
import application.model.PublicTransport;
import application.model.User;
import application.repository.CountryRepository;
import application.repository.PublicTransportRepository;
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
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;

import java.util.List;
import java.util.function.Predicate;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PublicTransportControllerTest {

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
    PublicTransportRepository public_transportRepository;

    @Autowired
    CountryRepository countryRepository;

    @LocalServerPort
    private int port;

    public void setUp(String name, String password) {
        mockMvc = sessionBasedMockMvc.createSessionBasedMockMvc(name, password, webApplicationContext, springSecurityFilterChain, port, testRestTemplate);
    }

    @Test
    public void addPublicTransport() throws  Exception {

        setUp("nafieTest", "password");
        User user = userRepository.findByUsername("nafieTest");

        JSONObject testJson = new JSONObject();

        testJson.put("date", "2019-03-20");
        testJson.put("time", "13:13:13");
        testJson.put("carType", "Electric car");
        testJson.put("distance", "30");


        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/addpublictransport")
                .accept(MediaType.APPLICATION_JSON).content(testJson.toString())
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        List<PublicTransport> testResult = public_transportRepository.findByVehicle_idAndDistance(5, (double) 30);

        Predicate<PublicTransport> findWasa = p -> p.getActivity().getUser().getUsername().equals("nafieTest");

        assertTrue(testResult.stream().anyMatch(findWasa));

        int deleteId = testResult.stream().filter(findWasa).mapToInt(p->p.getId()).toArray()[0];

        public_transportRepository.deleteById(deleteId);
    }
}