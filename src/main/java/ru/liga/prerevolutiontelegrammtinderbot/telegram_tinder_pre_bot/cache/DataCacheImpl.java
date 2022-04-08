package ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.cache;

import org.springframework.stereotype.Component;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.botapi.BotState;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.entity.User;

import java.util.HashMap;
import java.util.Map;
@Component
public class DataCacheImpl implements DataCache {

    private Map<Long, BotState> usersBotStates = new HashMap<>();
    private Map<Long, User> usersProfileData = new HashMap<>();

    @Override
    public void setUsersCurrentBotState(long userId, BotState botState) {
        usersBotStates.put(userId,botState);
    }

    @Override
    public BotState getUsersCurrentBotState(long userId) {
        BotState botState = usersBotStates.get(userId);
        if (botState == null){
            botState = BotState.START_STATE;
        }
        return botState;
    }

    @Override
    public User getUserProfileData(long userId) {
        User user = usersProfileData.get(userId);
        if (user == null){
            user = new User();
        }
        return user;
    }

    @Override
    public void saveUserProfileData(long userId, User userProfileData) {
        usersProfileData.put(userId,userProfileData);
    }
}
