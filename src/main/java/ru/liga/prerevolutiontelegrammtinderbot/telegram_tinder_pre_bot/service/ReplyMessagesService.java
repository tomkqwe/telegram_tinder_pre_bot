package ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
public class ReplyMessagesService {
    @Autowired
    private LocaleMessageService messageService;

    public ReplyMessagesService(LocaleMessageService messageService) {
        this.messageService = messageService;
    }

    public SendMessage getReplyMessage(String chatId, String replyMessage) {
        return new SendMessage(chatId, messageService.getMessage(replyMessage));
    }

    public SendMessage getReplyMessage(String chatId, String replyMessage, Object... args) {
        return new SendMessage(chatId, messageService.getMessage(replyMessage, args));
    }
    public String getReplyText(String replyText) {
        return messageService.getMessage(replyText);
    }
}
