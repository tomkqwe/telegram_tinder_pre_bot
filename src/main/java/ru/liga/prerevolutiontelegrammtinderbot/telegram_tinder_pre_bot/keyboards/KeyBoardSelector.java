package ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.keyboards;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.botapi.BotState;

import java.util.ArrayList;
import java.util.List;

public class KeyBoardSelector {

    public static InlineKeyboardMarkup getInlineKeyboardMarkup(BotState botState) {
        InlineKeyboardMarkup inlineKeyboardMarkup = null;
        switch (botState) {
            case ASK_GENDER:
                inlineKeyboardMarkup = getSexKeyboard();
                break;
            case ASK_PARTNER_GENDER:
                inlineKeyboardMarkup = getPartnerSexKeyboard();
                break;
        }
        return inlineKeyboardMarkup;
    }

    private static InlineKeyboardMarkup getPartnerSexKeyboard() {
        InlineKeyboardMarkup keyboard = getSexKeyboard();
        InlineKeyboardButton inlineKeyboardSexButton = new InlineKeyboardButton();
        inlineKeyboardSexButton.setText("М");
        inlineKeyboardSexButton.setCallbackData("Мужской");
        keyboard.getKeyboard().get(0).add(inlineKeyboardSexButton);
        return keyboard;
    }

    private static InlineKeyboardMarkup getSexKeyboard() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardSexButton1 = new InlineKeyboardButton();
        inlineKeyboardSexButton1.setText("М");
        inlineKeyboardSexButton1.setCallbackData("Мужской");
        InlineKeyboardButton inlineKeyboardSexButton2 = new InlineKeyboardButton();
        inlineKeyboardSexButton2.setText("М");
        inlineKeyboardSexButton2.setCallbackData("Мужской");
        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
        keyboardButtonsRow.add(inlineKeyboardSexButton1);
        keyboardButtonsRow.add(inlineKeyboardSexButton2);
        rowList.add(keyboardButtonsRow);
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }
}
