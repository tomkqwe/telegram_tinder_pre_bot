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
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.keyboards.InlineKeyBoardSelector;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.utils.UpdateHandler;

@Data
@Component
public class AskGenderHandler implements InputMessageHandler {
    public static final String CHOOSE_YOUR_GENDER = "Вы сударь иль сударыня?";
    @Autowired
    private DataCache dataCache;


    @Override
    public BotApiMethod<?> handleUpdate(Update update) {
        long userID = UpdateHandler.getId(update);
        String chatId = UpdateHandler.getChatId(update);

//Начинается заполнение анкеты, задаем вопрос и прекрепляем кнопки
//Переходим в состояние BOT_ASK_NAME
// обрабатываем ответ уже в состоянии BOT_ASK_NAME
        SendMessage replyToUser = new SendMessage(chatId, CHOOSE_YOUR_GENDER);
        replyToUser.setReplyMarkup(InlineKeyBoardSelector.getInlineKeyboardMarkup(getHandlerName()));
        dataCache.setUsersCurrentBotState(userID, BotState.ASK_NAME);
        dataCache.saveUserProfileData(userID,dataCache.getUserProfileData(userID));
        return replyToUser;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.ASK_GENDER;
    }
}
