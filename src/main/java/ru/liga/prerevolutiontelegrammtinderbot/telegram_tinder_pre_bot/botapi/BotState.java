package ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.botapi;

/**Возможные состояния бота
 */

public enum BotState {
    ASK_GENDER,
    ASK_NAME,
    ASK_AGE,
    ASK_DESCRIPTION,
    ASK_PARTNER_GENDER,

    IS_FILLING_PROFILE,//узнаем зарегистрирован пользователь в базе или нет
    PROFILE_FILLED,

    GET_SEARCHING,
    GET_FAVORITES,
    SHOW_MAIN_MENU,
    SHOW_HELP_MENU,
    SHOW_USER_PROFILE,
    INFO,

}
