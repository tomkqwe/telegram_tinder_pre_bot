package ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.utils;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

public class UpdateHandler {

    public static long getId(Update update) {
        if (update.hasCallbackQuery()) {
            return update.getCallbackQuery().getFrom().getId();
        } else {
            return update.getMessage().getFrom().getId();
        }
    }

    public static String getChatId(Update update) {
        if (update.hasCallbackQuery()){
            return update.getCallbackQuery().getFrom().getId().toString();
        }else {
            return update.getMessage().getChatId().toString();
        }
    }

    public static String getText(Update update) {
        if (update.hasCallbackQuery()) {
            return update.getCallbackQuery().getData();
        } else {
            return update.getMessage().getText();
        }
    }
}