package ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.botapi.handlers.fillingProfile;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.botapi.BotState;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.botapi.InputMessageHandler;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.cache.DataCache;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.entity.User;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.keyboards.InlineKeyBoardSelector;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.utils.UpdateHandler;

@Data
@Component
public class AskPartnerGender implements InputMessageHandler {
    public static final String WHO_ARE_YOU_LOOKING_FOR = "Кого вы ищите?";
    @Autowired
    private DataCache dataCache;

    @Override
    public PartialBotApiMethod<?> handleUpdate(Update update) {

        String chatID = UpdateHandler.getChatId(update);
        long userID = UpdateHandler.getId(update);
        String text = UpdateHandler.getText(update);
        User userProfileData = dataCache.getUserProfileData(userID);
        //получаем description, сэтим его юзеру, выкатываем кнопки с
        //выбором того, кого ищет наш юзер и обрабатываем этот выбор
        //в SHOW_PROFILE
        userProfileData.setDescription(text);
        SendMessage sendMessage = new SendMessage(chatID, WHO_ARE_YOU_LOOKING_FOR);
        sendMessage.setReplyMarkup(InlineKeyBoardSelector.getInlineKeyboardMarkup(getHandlerName()));
        dataCache.setUsersCurrentBotState(userID,BotState.PROFILE_FILLED);
        dataCache.saveUserProfileData(userID,dataCache.getUserProfileData(userID));
        return sendMessage;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.ASK_PARTNER_GENDER;
    }
}
