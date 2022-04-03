package ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.botapi;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.cache.DataCache;

/**
 * @author Sergei Viacheslaev
 */
@Data
@Component
@Slf4j
public class TelegramFacade {
    private BotStateContext botStateContext;
    private DataCache userDataCache;

    public TelegramFacade(BotStateContext botStateContext, DataCache userDataCache) {
        this.botStateContext = botStateContext;
        this.userDataCache = userDataCache;
    }

    public BotApiMethod<?> handleUpdate(Update update) {
        String inputMsg = getText(update);
        int userId = Math.toIntExact(update.getUpdateId());
        BotState botState;
        SendMessage replyMessage;
//        Проверить логику работы свитчкейса получается пользователь у бота спрашивает про имя пол и тд?итд
        switch (inputMsg) {
            case "/start":
                botState = BotState.FILLING_PROFILE;
                break;
//            case "Как вас величать?":
//                botState = BotState.ASK_NAME;
//                break;
//            case "Опишите себя":
//                botState = BotState.ASK_DESCRIPTION;
//                break;
            case "Поиск":
                botState = BotState.GET_SEARCHING;
            case "Анкета":
                botState = BotState.GET_CV;
            case "Любимцы":
                botState = BotState.GET_FAVORITES;

            default:
                botState = userDataCache.getUsersCurrentBotState(userId);
        }

        userDataCache.setUsersCurrentBotState(userId, botState);

        replyMessage = botStateContext.processInputMessage(botState, update);

        return replyMessage;
    }

    private String getText(Update update){
        if (update.hasCallbackQuery()){
            return update.getCallbackQuery().getData();
        } else {
            return update.getMessage().getText();
        }
    }
}
