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

@Data
@Component
public class AskAgeHandler implements InputMessageHandler {
    @Autowired
    private DataCache dataCache;
    @Override
    public BotApiMethod<?> handleUpdate(Update update) {
        int userID;
        String text="";
        String chatID;

        if (update.hasCallbackQuery()) {
            CallbackQuery query = update.getCallbackQuery();
            userID = Math.toIntExact(query.getFrom().getId());
//            text = query.getData();
            chatID = query.getFrom().getId().toString();
        } else {
            Message message = update.getMessage();
            text = message.getText();
            userID = Math.toIntExact(message.getFrom().getId());
            chatID = message.getChatId().toString();
        }

        User userProfileData = dataCache.getUserProfileData(userID);
       //Получаем имя, сэтим его юзеру,спрашиваем про возраст,сэтить возраст будем в ASK_DESCRIPTION
        userProfileData.setName(text);
        SendMessage sendMessage = new SendMessage(chatID,"Сколько вам лет?");
        dataCache.setUsersCurrentBotState(userID,BotState.ASK_DESCRIPTION);
        return sendMessage;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.ASK_AGE;
    }
}
