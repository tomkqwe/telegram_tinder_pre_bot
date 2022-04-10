package ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.utils;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.yaml.snakeyaml.events.Event;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.entity.User;

import java.io.File;
import java.util.Arrays;
import java.util.List;

@Getter
@Slf4j
@Component
public class Communication {
    public static final String LIKE = "like/";
    public static final String WE_LIKE = "weLike/";
    public static final String DELIMITER = "_";
    public static final String WHO_LIKED_ME = "whoLikedMe/";
    public static final String SYMPATHY = "sympathy/";
    public static final String IMAGE = "image/";
    private final String URL = "http://localhost:8888/api/users/";
    @Autowired
    private RestTemplate restTemplate;

    public List<User> getAllUsers() {
        log.info("get list of all users");
        return restTemplate.exchange(URL, HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {
        }).getBody();
    }

    public User getUser(long id) {
        log.info("get user by " + id);
        return restTemplate.getForObject(URL + "/" + id, User.class);
    }

    public void saveUser(User user) {
        restTemplate.postForEntity(URL, user, String.class);
        log.info("New user was added to DB");
    }

    public void updateUser(User user) {
        long id = user.getId();
        restTemplate.put(URL, user);
        log.info("user with ID " + id + " was updated");
    }
    public List<User> getUsersToSearch(Long userID){
        ResponseEntity<User[]> forEntity = restTemplate.getForEntity(URL + "filtredListToSearch/" + userID, User[].class);
        log.info("getUsersToSearch");
        return Arrays.asList(forEntity.getBody());
    }

    public void likeRequest(long current, long target) {
        List<Long> longs = Arrays.asList(current, target);
        restTemplate.postForObject(URL + LIKE + current + DELIMITER + target, longs, ResponseEntity.class);
        log.info("Send current {} id likes target id {}", current, target);
    }

    public List<User> getWeLike(Long userID) {
        ResponseEntity<User[]> responseEntity = restTemplate.getForEntity(URL + WE_LIKE + userID, User[].class);
        log.info("getWeLike");
        return Arrays.asList(responseEntity.getBody());
    }

    public List<User> getWhoLikedMe(Long userID) {
        ResponseEntity<User[]> forEntity = restTemplate.getForEntity(URL + WHO_LIKED_ME + userID, User[].class);
        log.info("getWhoLikedMe");
        return Arrays.asList(forEntity.getBody());
    }
    public List<User> getSympathy(Long userID){
        ResponseEntity<User[]> forEntity = restTemplate.getForEntity(URL + SYMPATHY + userID, User[].class);
        log.info("getSympathy");
        return Arrays.asList(forEntity.getBody());
    }

    public File getTextImageMaker(String text){
        HttpEntity<String> stringHttpEntity = new HttpEntity<>(text);
        return  restTemplate.postForObject(URL+ IMAGE,stringHttpEntity,File.class);
    }


    public void deleteUser(long id) {
        restTemplate.delete(URL + "/" + id);
    }


}
