package ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.botapi.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.botapi.BotState;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.botapi.InputMessageHandler;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.service.ReplyMessagesService;

@Component
@Slf4j
public class AgeExceptionHandler implements InputMessageHandler {
    private final ReplyMessagesService messageService;

    public AgeExceptionHandler(ReplyMessagesService messageService) {
        this.messageService = messageService;
    }

    @Override
    public SendMessage handle(Message message) {
        return messageService.getReplyMessage(message.getChatId().toString(), "age.exception");
    }

    @Override
    public BotState getHandlerName() {
        return BotState.CATCH_EXCEPTION;
    }
}
