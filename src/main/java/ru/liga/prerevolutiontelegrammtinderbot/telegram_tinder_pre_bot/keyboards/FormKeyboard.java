package ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.keyboards;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;

public class FormKeyboard {

    public static final String CHANGE_FORM = "Изменить анкету";
    public static final String BACK = "Назад";

    public static ReplyKeyboard getFormKeyboard(){
        ReplyKeyboardMarkup replyKeyboard = new ReplyKeyboardMarkup();

        replyKeyboard.setSelective(true);
        replyKeyboard.setResizeKeyboard(true);
        replyKeyboard.setOneTimeKeyboard(false);

        ArrayList<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardButtons = new KeyboardRow();

        keyboardButtons.add(new KeyboardButton(CHANGE_FORM));
        keyboardButtons.add(new KeyboardButton(BACK));

        keyboardRows.add(keyboardButtons);
        replyKeyboard.setKeyboard(keyboardRows);

        return replyKeyboard;
    }
}
