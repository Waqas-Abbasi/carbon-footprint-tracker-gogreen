package application.security;

import application.model.Session;
import application.model.User;
import application.repository.SessionIdRepository;
import application.repository.UserRepository;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionToUser {

    @Autowired
    UserRepository userRepository;

    @Autowired
    SessionIdRepository sessionRepository;

    public User sessionToUser(String cookie) {
        Pattern pat = Pattern.compile("\\SESSION=([^;]+)");
        Matcher matcher = pat.matcher(cookie);
        boolean found = matcher.find();
        String sessionIdEncoded = (found ? matcher.group(1) : "not found");
        byte[] byteArray = Base64.decodeBase64(sessionIdEncoded.getBytes());
        String sessionId = new String(byteArray);
        Session session = sessionRepository.findBySessionId(sessionId);
        String username = session.getUsername();
        User user = userRepository.findByUsername(username);
        return user;
    }
}
