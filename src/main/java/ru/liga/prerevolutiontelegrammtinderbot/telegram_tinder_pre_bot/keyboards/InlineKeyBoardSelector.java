package ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.keyboards;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.botapi.BotState;

import java.util.ArrayList;
import java.util.List;

public class InlineKeyBoardSelector {

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
        inlineKeyboardSexButton1.setText("М");
        inlineKeyboardSexButton1.setCallbackData("ищу Мужчин");
        InlineKeyboardButton inlineKeyboardSexButton2 = new InlineKeyboardButton();
        inlineKeyboardSexButton2.setText("Ж");
        inlineKeyboardSexButton2.setCallbackData("ищу Женщин");
        InlineKeyboardButton inlineKeyboardSexButton3 = new InlineKeyboardButton();
        inlineKeyboardSexButton3.setText("ищу Всех");
        inlineKeyboardSexButton3.setCallbackData("Все");
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
        inlineKeyboardSexButton1.setText("Сударь");
        inlineKeyboardSexButton1.setCallbackData("я-сударь");
        InlineKeyboardButton inlineKeyboardSexButton2 = new InlineKeyboardButton();
        inlineKeyboardSexButton2.setText("Сударыня");
        inlineKeyboardSexButton2.setCallbackData("я-сударыня");
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
        inlineKeyboardButton.setText("Поехали");
        inlineKeyboardButton.setCallbackData("Поехали");
        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
        keyboardButtonsRow.add(inlineKeyboardButton);
        rowList.add(keyboardButtonsRow);
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }
}
