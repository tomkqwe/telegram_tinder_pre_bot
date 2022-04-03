package ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.botapi;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

/**Обработчик сообщений
 */
public interface InputMessageHandler {
    SendMessage handle(Update update);

    BotState getHandlerName();
}
