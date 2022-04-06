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
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.utils.UpdateHandler;

@Data
@Component
public class AskDescription implements InputMessageHandler {
    @Autowired
    private DataCache dataCache;
    @Override
    public BotApiMethod<?> handleUpdate(Update update) {
        Message message = update.getMessage();
        String chatID = UpdateHandler.getChatId(update);
        int userID = Math.toIntExact(UpdateHandler.getId(update));

        User userProfileData = dataCache.getUserProfileData(userID);
        //Получаем строку в которой должно быть число, если пользователь вводит буквы,
        //ловим ошибку,и просим его ввести цифры, когда он ввел цифры сэтим их юзеру в возраст
        //и спрашиваем про описание и переходим к ASK_PARTNER_GENDER, где и будем сэтить DESCRIPTION
        try{
            userProfileData.setAge(Integer.parseInt(update.getMessage().getText()));
        }catch (NumberFormatException e){
            dataCache.setUsersCurrentBotState(userID,BotState.ASK_DESCRIPTION);
            return new SendMessage(chatID,"Возраст не может быть с буквами!\nПовторите ввод!");
        }
        dataCache.setUsersCurrentBotState(userID,BotState.ASK_PARTNER_GENDER);
        dataCache.saveUserProfileData(userID,dataCache.getUserProfileData(userID));
        return new SendMessage(chatID, "Опишите себя");
    }
    @Override
    public BotState getHandlerName() {
        return BotState.ASK_DESCRIPTION;
    }
}
