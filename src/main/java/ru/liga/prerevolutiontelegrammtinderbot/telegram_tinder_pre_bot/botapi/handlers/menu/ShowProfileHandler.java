package ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.botapi.handlers.menu;

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
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.utils.Communication;

/**
 * В это класс нужно забульбенить картинку с текстом для просмотра своей анкеты на картинке
 */

@Component
public class ShowProfileHandler implements InputMessageHandler {
    private DataCache dataCache;

    public ShowProfileHandler(DataCache dataCache) {
        this.dataCache = dataCache;
    }

    //


    @Override
    public BotApiMethod<?> handleUpdate(Update update) {
        int userID;
        String chatId;
        if (update.hasCallbackQuery()) {
            CallbackQuery query = update.getCallbackQuery();
            userID = Math.toIntExact(query.getFrom().getId());
            chatId = query.getFrom().getId().toString();
        } else {
            Message message = update.getMessage();
            userID = Math.toIntExact(message.getFrom().getId());
            chatId = message.getChatId().toString();
        }

        User userProfileData = dataCache.getUserProfileData(userID);
        dataCache.setUsersCurrentBotState(userID,BotState.SHOW_MAIN_MENU);

        return new SendMessage(chatId, String.format("%s%n -------------------%nИмя: %s%nВозраст: %d%n Пол: %s%nОписание: %s%n" +
                        "Кого ищем: %s%n", "Данные по вашей анкете", userProfileData.getName(), userProfileData.getAge(), userProfileData.getSex(), userProfileData.getDescription(),
                userProfileData.getPartnerSex()));
    }


    @Override
    public BotState getHandlerName() {
        return BotState.SHOW_USER_PROFILE;
    }
}
