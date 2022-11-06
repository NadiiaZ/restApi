package rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.Cookie;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import rest.model.User;

import java.net.HttpRetryException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Communication {
    @Autowired
    private RestTemplate restTemplate;

    private String sessionId;

    private final String URL = "http://94.198.50.185:7081/api/users";

    private HttpHeaders setHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", sessionId);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
    public List<User> showAllUsers() {
        ResponseEntity<List<User>> responseEntity =
                restTemplate.exchange(URL, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<User>>() {
                        });
        HttpHeaders headers = responseEntity.getHeaders();
        sessionId = headers.getFirst(HttpHeaders.SET_COOKIE);
        return  responseEntity.getBody();
    }

    public String saveUser(User user) {
        HttpEntity<User> entity = new HttpEntity<>(user, setHeader());
        ResponseEntity<String> result = restTemplate.postForEntity(URL, entity, String.class);
        return result.getBody();
    }

    public String updateUser(User user) {
        HttpEntity<User> headers = new HttpEntity<>(user, setHeader());
        ResponseEntity<String> result = restTemplate.exchange(URL, HttpMethod.PUT, headers, String.class);
        return result.getBody();
    }

    public String deleteUser(long id) {
        ResponseEntity<String> result = restTemplate.exchange(URL + "/" + id, HttpMethod.DELETE,
                new HttpEntity<>(setHeader()), String.class);

       return result.getBody();
    }
}
