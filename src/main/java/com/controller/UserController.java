package com.controller;

import com.model.User;
import org.springframework.http.*;;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
public class UserController {

    final static RestTemplate restTemplate = new RestTemplate();
    private static String url = "http://91.241.64.178:7081/api/users";
    private static String url_del = "http://91.241.64.178:7081/api/users/{id}";
    private static String cookie;


    public static void main(String[] args) {
        getUsers();
    }

    private static void getUsers() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>("param", httpHeaders);
        ResponseEntity<String> result = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        HttpHeaders headers = result.getHeaders();
        cookie = headers.getFirst(headers.SET_COOKIE);
        System.out.println(result.toString());
        System.out.println(cookie);
        postUser();
        putUser();
        deleteUser();
        System.out.println(postUser() + putUser() + deleteUser());
    }

    private static String postUser() {
        User user = new User(3L, "James", "Brown", (byte) 20);
        RequestEntity<User> requestEntity = RequestEntity
                .post(URI.create(url))
                .contentType(MediaType.APPLICATION_JSON)
                .header("Cookie", cookie)
                .body(user);
        ResponseEntity<String> entity = restTemplate.exchange(requestEntity, String.class);
        return entity.getBody();
    }

    private static String putUser() {
        Long id = 3L;
        User updateUser = new User(id, "Thomas", "Shelby", (byte) 30);
        RequestEntity<User> requestEntity = RequestEntity
                .put(URI.create(url))
                .contentType(MediaType.APPLICATION_JSON)
                .header("Cookie", cookie)
                .body(updateUser);
        ResponseEntity<String> entity = restTemplate.exchange(requestEntity, String.class);
        return entity.getBody();
    }

    private static String deleteUser() {
        Map<String, Long> param = new HashMap<>();
        param.put("id", 3L);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", cookie);
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url_del, HttpMethod.DELETE, httpEntity, String.class, param);
        return responseEntity.getBody();
    }
}
