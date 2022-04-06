package ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.botapi.handlers.fillingProfile;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.botapi.BotState;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.botapi.InputMessageHandler;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.cache.DataCache;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.entity.User;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.keyboards.InlineKeyBoardSelector;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.utils.UpdateHandler;

@Data
@Component
public class AskNameHandler implements InputMessageHandler {
    @Autowired
    private DataCache dataCache;

    @Override
    public BotApiMethod<?> handleUpdate(Update update) {
        int userID = Math.toIntExact(UpdateHandler.getId(update));

        String chatId = UpdateHandler.getChatId(update);


//        Получаем ответ с кнопки, если нету ответа с кнопки зацикливаемся пока его не получим
//        Когда его получаем, спрашиваем про имя и переходим в состояние ASK_AGE
//        Обрабатывать имя будем в ASK_AGE handlere
        User userProfileData = dataCache.getUserProfileData(userID);
        SendMessage sendMessage = null;
        if (update.hasCallbackQuery()) {
            sendMessage = new SendMessage(chatId, "Как вас величать?");
            String data = update.getCallbackQuery().getData();
            userProfileData.setSex(data);
            dataCache.setUsersCurrentBotState(userID, BotState.ASK_AGE);
            dataCache.saveUserProfileData(userID,dataCache.getUserProfileData(userID));
        } else {
            sendMessage = new SendMessage(chatId, "Выберите пол!");
            sendMessage.setReplyMarkup(InlineKeyBoardSelector.getInlineKeyboardMarkup(BotState.ASK_GENDER));
            dataCache.setUsersCurrentBotState(userID, BotState.ASK_NAME);
        }

        return sendMessage;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.ASK_NAME;
    }
}
