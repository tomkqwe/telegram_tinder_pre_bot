package ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.botapi;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * @author Sergei Viacheslaev
 */
@Data
@Component
@Slf4j
public class TelegramFacade {
    private BotStateContext botStateContext;
//    private UserDataCache userDataCache;

//    public TelegramFacade(BotStateContext botStateContext, UserDataCache userDataCache) {
//        this.botStateContext = botStateContext;
//        this.userDataCache = userDataCache;
//    }

    public SendMessage handleUpdate(Update update) {
        SendMessage replyMessage = null;

        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            log.info("New message from User:{}, chatId: {},  with text: {}",
                    message.getFrom().getUserName(), message.getChatId(), message.getText());
            replyMessage = handleInputMessage(message);
        }

        return replyMessage;
    }

    private SendMessage handleInputMessage(Message message) {
        String inputMsg = message.getText();
//        int userId = message.getFrom().getId();
        BotState botState;
        SendMessage replyMessage;

        switch (inputMsg) {
            case "/start":
                botState = BotState.ASK_GENDER;
                break;
            case "Как вас величать?":
                botState = BotState.ASK_NAME;
                break;
            case "Опишите себя":
                botState = BotState.ASK_DESCRIPTION;
                break;
            case "Поиск":
                botState = BotState.GET_SEARCHING;
            case "Анкета":
                botState = BotState.GET_CV;
            case "Любимцы":
                botState = BotState.GET_FAVORITES;

            default:
                botState = BotState.INFO;
                break;
        }

//        userDataCache.setUsersCurrentBotState(userId, botState);

        replyMessage = botStateContext.processInputMessage(botState, message);

        return replyMessage;
    }


}
