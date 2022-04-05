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
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.keyboards.KeyBoardSelector;

@Data
@Component
public class GetProfileFilledHandler implements InputMessageHandler {
    @Autowired
    private DataCache dataCache;

    @Override
    public BotApiMethod<?> handleUpdate(Update update) {
        int userId;
//        String text;
        String chatID;
        if (update.hasCallbackQuery()) {
            CallbackQuery query = update.getCallbackQuery();
            userId = Math.toIntExact(query.getFrom().getId());
//            text = query.getData();
            chatID = query.getFrom().getId().toString();
        } else {
            Message message = update.getMessage();
//            text = message.getText();
            userId = Math.toIntExact(message.getFrom().getId());
            chatID = message.getChatId().toString();
        }
        //получаем callbackquery с partnerGender, сэтим его в юзера,
        //если пользователь ввел ручками partnerGender, то получаем текст
        //и просим пользователя нажать на кнопку для получения callbackquery
        //когда юзер заполнен, наконец-то заполнение анкеты завершено
        //переходим в главное меню
        User userProfileData = dataCache.getUserProfileData(userId);
        SendMessage sendMessage = null;
        if (update.hasCallbackQuery()) {
            String data = update.getCallbackQuery().getData();
            userProfileData.setPartnerSex(data);
            sendMessage = new SendMessage(chatID, "Анкета заполнена!");
        } else {
            sendMessage = new SendMessage(chatID, "Кого ищем?\nНажмите на кнопку!");
            sendMessage.setReplyMarkup(KeyBoardSelector.getInlineKeyboardMarkup(BotState.ASK_PARTNER_GENDER));
                dataCache.setUsersCurrentBotState(userId,getHandlerName());
        }
        return sendMessage;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.PROFILE_FILLED;
    }
}
