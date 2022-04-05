package ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.botapi.handlers.fillingProfile;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.botapi.BotState;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.botapi.InputMessageHandler;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.cache.DataCache;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.entity.User;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.keyboards.KeyBoardSelector;
@Data
@Component
public class AskPartnerGender implements InputMessageHandler {
    @Autowired
    private DataCache dataCache;

    @Override
    public BotApiMethod<?> handleUpdate(Update update) {

        Message message = update.getMessage();
        String chatID = message.getChatId().toString();
        int userID = Math.toIntExact(message.getFrom().getId());
        User userProfileData = dataCache.getUserProfileData(userID);
        //получаем description, сэтим его юзеру, выкатываем кнопки с
        //выбором того, кого ищет наш юзер и обрабатываем этот выбор
        //в PROFILE_FILLED
        userProfileData.setDescription(message.getText());
        SendMessage sendMessage = new SendMessage(chatID, "Кого вы ищите?");
        sendMessage.setReplyMarkup(KeyBoardSelector.getInlineKeyboardMarkup(getHandlerName()));
        dataCache.setUsersCurrentBotState(userID,BotState.PROFILE_FILLED);
        dataCache.saveUserProfileData(userID,dataCache.getUserProfileData(userID));
        return sendMessage;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.ASK_PARTNER_GENDER;
    }
}
