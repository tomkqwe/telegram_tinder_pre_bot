package ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.botapi.handlers.menu;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.botapi.BotState;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.botapi.InputMessageHandler;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.keyboards.MainMenuKeyboard;

@Component
public class MainMenuHandler implements InputMessageHandler {
    private MainMenuKeyboard mainMenuKeyboard;

    public MainMenuHandler(MainMenuKeyboard mainMenuKeyboard) {
        this.mainMenuKeyboard = mainMenuKeyboard;
    }


    @Override
    public BotApiMethod<?> handleUpdate(Update update) {
        String chatID;
        if (update.hasMessage()) {
            chatID = update.getMessage().getChatId().toString();
        } else {
            chatID = update.getCallbackQuery().getId();
        }
        return mainMenuKeyboard.getMainMenuMessage(chatID, "это сообщение прилетает из глвного меню");

    }

    @Override
    public BotState getHandlerName() {
        return BotState.SHOW_MAIN_MENU;
    }
}
