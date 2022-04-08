package ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.cache;

import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.botapi.BotState;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.entity.User;


public interface DataCache {
    void setUsersCurrentBotState(long userId, BotState botState);

    BotState getUsersCurrentBotState(long userId);

    User getUserProfileData(long userId);

    void saveUserProfileData(long userId, User userProfileData);
}
