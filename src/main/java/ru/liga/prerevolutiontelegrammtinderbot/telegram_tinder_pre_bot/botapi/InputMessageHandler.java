package ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.botapi;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

/**Обработчик сообщений
 */
public interface InputMessageHandler {
    BotApiMethod<?> handleUpdate(Update update);

    BotState getHandlerName();
}
