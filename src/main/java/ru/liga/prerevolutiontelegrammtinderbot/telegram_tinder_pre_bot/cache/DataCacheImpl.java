package ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.cache;

import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.botapi.BotState;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.entity.User;

import java.util.HashMap;
import java.util.Map;
@Component
public class DataCacheImpl implements DataCache {

    private Map<Integer, BotState> usersBotStates = new HashMap<>();
    private Map<Integer, User> usersProfileData = new HashMap<>();

    @Override
    public void setUsersCurrentBotState(int userId, BotState botState) {
        usersBotStates.put(userId,botState);
    }

    @Override
    public BotState getUsersCurrentBotState(int userId) {
        BotState botState = usersBotStates.get(userId);
        if (botState == null){
            botState = BotState.ASK_GENDER;
        }
        return botState;
    }

    @Override
    public User getUserProfileData(int userId) {
        User user = usersProfileData.get(userId);
        if (user == null){
            user = new User();
        }
        return user;
    }

    @Override
    public void saveUserProfileData(int userId, User userProfileData) {
        usersProfileData.put(userId,userProfileData);
    }
}
