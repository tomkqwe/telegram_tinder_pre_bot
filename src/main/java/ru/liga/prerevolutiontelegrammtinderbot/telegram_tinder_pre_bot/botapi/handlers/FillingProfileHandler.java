package ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.botapi.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.botapi.BotState;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.botapi.InputMessageHandler;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.cache.DataCache;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.service.ReplyMessagesService;

@Slf4j
@Component
public class FillingProfileHandler  implements InputMessageHandler {
    private DataCache dataCache;
    private ReplyMessagesService messageService;
    @Override
    public SendMessage handle(Message message) {
        return null;
    }

    @Override
    public BotState getHandlerName() {
        return null;
    }
}
