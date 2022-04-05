package ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.botapi.handlers.fillingProfile;

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
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.keyboards.KeyBoardSelector;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.utils.Communication;

/**
 * В это класс нужно забульбенить картинку с текстом для просмотра своей анкеты на картинке
 */

@Component
public class ShowProfileFilledHandler implements InputMessageHandler {
    private DataCache dataCache;

    public ShowProfileFilledHandler(DataCache dataCache) {
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
        //получаем callbackquery с partnerGender, сэтим его в юзера,
        //если пользователь ввел ручками partnerGender, то получаем текст
        //и просим пользователя нажать на кнопку для получения callbackquery
        //когда юзер заполнен, наконец-то заполнение анкеты завершено
        //переходим в главное меню

        User userProfileData = dataCache.getUserProfileData(userID);
        SendMessage sendMessage = null;
        if (update.hasCallbackQuery()) {
            String data = update.getCallbackQuery().getData();
            userProfileData.setPartnerSex(data);
            sendMessage = new SendMessage(chatId, "Анкета заполнена!");
            dataCache.saveUserProfileData(userID,dataCache.getUserProfileData(userID));
            dataCache.setUsersCurrentBotState(userID,BotState.SHOW_MAIN_MENU);

        } else {
            sendMessage = new SendMessage(chatId, "Кого ищем?\nНажмите на кнопку!");
            sendMessage.setReplyMarkup(KeyBoardSelector.getInlineKeyboardMarkup(BotState.ASK_PARTNER_GENDER));
            dataCache.setUsersCurrentBotState(userID,getHandlerName());
            return sendMessage;
        }

        return new SendMessage(chatId, String.format("%s%n -------------------%nИмя: %s%nВозраст: %d%n Пол: %s%nОписание: %s%n" +
                        "Кого ищем: %s%n", "Данные по вашей анкете", userProfileData.getName(), userProfileData.getAge(), userProfileData.getSex(), userProfileData.getDescription(),
                userProfileData.getPartnerSex()));
    }


    @Override
    public BotState getHandlerName() {
        return BotState.PROFILE_FILLED;
    }
}
