package ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.keyboards;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.botapi.BotState;

import java.util.ArrayList;
import java.util.List;

public class InlineKeyBoardSelector {

    public static final String LETS_GO = "Поехали";
    public static final String SUDAR = "Сударь";
    public static final String MANS = "Мужчин";
    public static final String SUDARINA = "Сударыня";
    public static final String WOMENS = "Женщин";
    public static final String ALL = "Все";
    public static final String FIND_ALL = "Всех";

    public static InlineKeyboardMarkup getInlineKeyboardMarkup(BotState botState) {
        InlineKeyboardMarkup inlineKeyboardMarkup = null;
        switch (botState) {
            case ASK_GENDER:
                inlineKeyboardMarkup = getSexKeyboard();
                break;
            case ASK_PARTNER_GENDER:
                inlineKeyboardMarkup = getPartnerSexKeyboard();
                break;
            case START_STATE:
                inlineKeyboardMarkup = getStartRegistration();
        }
        return inlineKeyboardMarkup;
    }

    private static InlineKeyboardMarkup getPartnerSexKeyboard() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardSexButton1 = new InlineKeyboardButton();
        inlineKeyboardSexButton1.setText(SUDAR);
        inlineKeyboardSexButton1.setCallbackData(MANS);
        InlineKeyboardButton inlineKeyboardSexButton2 = new InlineKeyboardButton();
        inlineKeyboardSexButton2.setText(SUDARINA);
        inlineKeyboardSexButton2.setCallbackData(WOMENS);
        InlineKeyboardButton inlineKeyboardSexButton3 = new InlineKeyboardButton();
        inlineKeyboardSexButton3.setText(FIND_ALL);
        inlineKeyboardSexButton3.setCallbackData(ALL);
        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
        keyboardButtonsRow.add(inlineKeyboardSexButton1);
        keyboardButtonsRow.add(inlineKeyboardSexButton2);
        keyboardButtonsRow.add(inlineKeyboardSexButton3);
        rowList.add(keyboardButtonsRow);
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }

    private static InlineKeyboardMarkup getSexKeyboard() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardSexButton1 = new InlineKeyboardButton();
        inlineKeyboardSexButton1.setText(SUDAR);
        inlineKeyboardSexButton1.setCallbackData(SUDAR);
        InlineKeyboardButton inlineKeyboardSexButton2 = new InlineKeyboardButton();
        inlineKeyboardSexButton2.setText(SUDARINA);
        inlineKeyboardSexButton2.setCallbackData(SUDARINA);
        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
        keyboardButtonsRow.add(inlineKeyboardSexButton1);
        keyboardButtonsRow.add(inlineKeyboardSexButton2);
        rowList.add(keyboardButtonsRow);
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }

    private static InlineKeyboardMarkup getStartRegistration() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText(LETS_GO);
        inlineKeyboardButton.setCallbackData(LETS_GO);
        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
        keyboardButtonsRow.add(inlineKeyboardButton);
        rowList.add(keyboardButtonsRow);
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }
}
