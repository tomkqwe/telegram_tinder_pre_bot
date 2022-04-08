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
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.utils.UpdateHandler;

@Data
@Component
public class AskAgeHandler implements InputMessageHandler {
    public static final String HOW_OLD = "Сколько вам лет?";
    @Autowired
    private DataCache dataCache;
    @Override
    public BotApiMethod<?> handleUpdate(Update update) {

        long userID = UpdateHandler.getId(update);
        String text= UpdateHandler.getText(update);
        String chatID = UpdateHandler.getChatId(update);

        User userProfileData = dataCache.getUserProfileData(userID);
       //Получаем имя, сэтим его юзеру,спрашиваем про возраст,сэтить возраст будем в ASK_DESCRIPTION
        userProfileData.setName(text);
        dataCache.saveUserProfileData(userID,dataCache.getUserProfileData(userID));
        SendMessage sendMessage = new SendMessage(chatID, HOW_OLD);
        dataCache.setUsersCurrentBotState(userID,BotState.ASK_DESCRIPTION);
        return sendMessage;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.ASK_AGE;
    }
}
