package ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.botapi.handlers.fillingProfile;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
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
    @Autowired
    private DataCache dataCache;
    @Autowired
    private Communication communication;


    @Override
    public BotApiMethod<?> handleUpdate(Update update) {
        int userID = Math.toIntExact(UpdateHandler.getId(update));
        String chatID = UpdateHandler.getChatId(update);

        User user = null;
        try {
            user = communication.getUser(userID);
        } catch (Exception e) {
            SendMessage neobhodimo_zaregistrirovatsa = new SendMessage(chatID, "neobhodimo zaregistrirovatsa");
            neobhodimo_zaregistrirovatsa.setReplyMarkup(InlineKeyBoardSelector.getInlineKeyboardMarkup(getHandlerName()));
            return neobhodimo_zaregistrirovatsa;
        }
        SendMessage sendMessage;
        if (user == null) {
            sendMessage = new SendMessage();
            sendMessage.setReplyMarkup(InlineKeyBoardSelector.getInlineKeyboardMarkup(getHandlerName()));
        } else {
            sendMessage = new SendMessage(chatID, "Воспользуйтесь главным меню");
            sendMessage.setReplyMarkup(MainMenuKeyboard.getMainMenuKeyboard());
        }
        return sendMessage;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.START_STATE;
    }
}
