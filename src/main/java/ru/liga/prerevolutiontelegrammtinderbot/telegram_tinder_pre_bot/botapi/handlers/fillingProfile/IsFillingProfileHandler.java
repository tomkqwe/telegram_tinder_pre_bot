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
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.keyboards.MainMenuKeyboard;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.utils.Communication;

@Component
@Data
public class IsFillingProfileHandler implements InputMessageHandler {
    @Autowired
    private DataCache dataCache;
    @Autowired
    private Communication communication;
    @Autowired
    private MainMenuKeyboard mainMenuKeyboard;



    @Override
    public BotApiMethod<?> handleUpdate(Update update) {
        int userID;
        String chatID;
        if (update.hasCallbackQuery()) {
            CallbackQuery query = update.getCallbackQuery();
            userID = Math.toIntExact(query.getFrom().getId());
            chatID = query.getFrom().getId().toString();
        } else {
            Message message = update.getMessage();
            userID = Math.toIntExact(message.getFrom().getId());
            chatID = message.getChatId().toString();
        }
        User user = communication.getUser(userID);
        if (user.getName() != null){
            dataCache.setUsersCurrentBotState(userID,BotState.SHOW_MAIN_MENU);
            return mainMenuKeyboard.getMainMenuMessage(chatID,"Добро пожаловать");
        }else {
            dataCache.setUsersCurrentBotState(userID,BotState.ASK_GENDER);
            return  new SendMessage(chatID,"Пользователь не найден, необходимо зарегистрироваться");
        }
    }

    @Override
    public BotState getHandlerName() {
        return BotState.IS_FILLING_PROFILE;
    }
}
