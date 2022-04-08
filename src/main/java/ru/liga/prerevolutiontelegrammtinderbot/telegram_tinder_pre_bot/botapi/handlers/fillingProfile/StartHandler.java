package ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.botapi.handlers.fillingProfile;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.botapi.BotState;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.botapi.InputMessageHandler;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.cache.DataCache;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.entity.User;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.keyboards.InlineKeyBoardSelector;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.keyboards.MainMenuKeyboard;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.utils.Communication;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.utils.UpdateHandler;

@Component
@Data
public class StartHandler implements InputMessageHandler {
    public static final String NEED_REGISTRATION = "neobhodimo zaregistrirovatsa";
    public static final String USE_MAIN_MENU = "Воспользуйтесь главным меню";
    @Autowired
    private DataCache dataCache;
    @Autowired
    private Communication communication;


    @Override
    public BotApiMethod<?> handleUpdate(Update update) {
        long userID = UpdateHandler.getId(update);
        String chatID = UpdateHandler.getChatId(update);

        User user = null;
        try {
            user = communication.getUser(userID);
        } catch (Exception e) {
            SendMessage neobhodimo_zaregistrirovatsa = new SendMessage(chatID, NEED_REGISTRATION);
            neobhodimo_zaregistrirovatsa.setReplyMarkup(InlineKeyBoardSelector.getInlineKeyboardMarkup(getHandlerName()));
            return neobhodimo_zaregistrirovatsa;
        }
        SendMessage sendMessage;
        if (user == null) {
            dataCache.getUserProfileData(userID).setId(userID);
            sendMessage = new SendMessage(chatID,NEED_REGISTRATION);
            sendMessage.setReplyMarkup(InlineKeyBoardSelector.getInlineKeyboardMarkup(getHandlerName()));
        } else {
            sendMessage = new SendMessage(chatID, USE_MAIN_MENU);
            sendMessage.setReplyMarkup(MainMenuKeyboard.getMainMenuKeyboard());
        }
        return sendMessage;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.START_STATE;
    }
}
