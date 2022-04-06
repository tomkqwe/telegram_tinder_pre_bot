package ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.keyboards;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
@Service
public class MainMenuKeyboard {
//    public static SendMessage getMainMenuMessage(final String chatId) {
//        final ReplyKeyboardMarkup replyKeyboardMarkup = getMainMenuKeyboard();
//                createMessageWithKeyboard(chatId,)
//        return mainMenuMessage;
//    }

    public static ReplyKeyboardMarkup getMainMenuKeyboard() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        ArrayList<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        KeyboardRow row3 = new KeyboardRow();


        row1.add(new KeyboardButton("/start"));
        row2.add(new KeyboardButton("Поиск"));
        row2.add(new KeyboardButton("Анкета"));
        row3.add(new KeyboardButton("Любимцы"));

        keyboard.add(row1);
        keyboard.add(row2);
        keyboard.add(row3);


        replyKeyboardMarkup.setKeyboard(keyboard);

        return replyKeyboardMarkup;
    }

    private static SendMessage createMessageWithKeyboard(String chatId, String textMessage, ReplyKeyboardMarkup replyKeyboardMarkup) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(textMessage);
        if (replyKeyboardMarkup != null) {
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
        }

        return sendMessage;
    }
}
