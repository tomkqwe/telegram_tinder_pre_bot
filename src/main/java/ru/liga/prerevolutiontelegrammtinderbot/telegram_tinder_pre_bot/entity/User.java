package ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    private int id;
    private String name;
    //потом заменим на энамы, яхз как их в пост запросе передать
    private String sex;
    private Integer age;
    private String description;
    //сэты из видео, таблички он там делает в ручную
    private Set<User> whoChooseMe = new HashSet<>();
    private Set<User> whoDidIChoose = new HashSet<>();

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                ", description='" + description + '\'' +
                ", whoChooseMe=" + whoChooseMe +
                ", whoDidIChoose=" + whoDidIChoose +
                '}';
    }
}
