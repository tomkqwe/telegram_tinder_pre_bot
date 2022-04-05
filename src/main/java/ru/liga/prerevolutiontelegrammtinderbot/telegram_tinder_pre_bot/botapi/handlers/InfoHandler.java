package ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.botapi.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.botapi.BotState;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.botapi.InputMessageHandler;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.service.ReplyMessagesService;

@Component
@Slf4j
public class InfoHandler implements InputMessageHandler {
    private final ReplyMessagesService messageService;

    public InfoHandler(ReplyMessagesService messageService) {
        this.messageService = messageService;
    }


    public SendMessage handleTextMessage(Message message) {
        SendMessage replyMessage = messageService.getReplyMessage(message.getChatId().toString(), message.getText());
        return replyMessage;
    }

    @Override
    public BotApiMethod<?> handleUpdate(Update update) {
        return null;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.INFO;
    }
}
