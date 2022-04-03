package ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.cache;

import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.botapi.BotState;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.entity.User;


public interface DataCache {
    void setUsersCurrentBotState(int userId, BotState botState);

    BotState getUsersCurrentBotState(int userId);

    User getUserProfileData(int userId);

    void saveUserProfileData(int userId, User userProfileData);
}
