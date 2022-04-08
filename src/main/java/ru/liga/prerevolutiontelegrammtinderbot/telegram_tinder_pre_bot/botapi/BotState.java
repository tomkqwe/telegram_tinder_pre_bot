package ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.botapi;

/**Возможные состояния бота
 */

public enum BotState {
    START_STATE,//узнаем зарегистрирован пользователь в базе или нет

    ASK_GENDER,
    ASK_NAME,
    ASK_AGE,
    ASK_DESCRIPTION,
    ASK_PARTNER_GENDER,

    PROFILE_FILLED,

    GET_SEARCHING,

    GET_FAVORITES,
    CHECK_WE_LIKE_LIST,
    CHECK_WHO_LIKED_ME_LIST,


    SHOW_MAIN_MENU,
    SHOW_USER_PROFILE,
    INFO,

}
