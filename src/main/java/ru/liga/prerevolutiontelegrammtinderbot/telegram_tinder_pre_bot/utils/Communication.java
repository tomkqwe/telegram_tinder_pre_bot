package ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.utils;

import lombok.Getter;
import lombok.extern.flogger.Flogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.configuration.MyConfig;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.entity.User;

import java.util.List;
@Getter
@Slf4j
@Component
public class Communication {
    private final String URL = "http://localhost:8888/api/users/";
    @Autowired
    private RestTemplate restTemplate;

    public List<User> getAllUsers() {
        log.info("get list of all users");
        return restTemplate.exchange(URL, HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {
        }).getBody();
    }

    public User getUser(int id) {
        log.info("get user by "+id);
        return restTemplate.getForObject(URL + "/" + id, User.class);

    }

    public void saveUser(User user) {
        int id = user.getId();
        if (id == 0) {
            restTemplate.postForEntity(URL, user, String.class);
            log.info("New user was added to DB");
        } else {
            restTemplate.put(URL, user);
            log.info("user with ID " + id + " was updated");
        }
    }

    public void deleteEmployee(int id) {
        restTemplate.delete(URL + "/" + id);
    }

}
