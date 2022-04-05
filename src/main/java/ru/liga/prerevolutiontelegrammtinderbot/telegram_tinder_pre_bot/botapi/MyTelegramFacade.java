package ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.botapi;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.cache.DataCache;

import java.util.Optional;

@Data
@Component
@Slf4j
public class MyTelegramFacade {

    private BotStateContext botStateContext;
    private DataCache userDataCache;

    public MyTelegramFacade(BotStateContext botStateContext, DataCache userDataCache) {
        this.botStateContext = botStateContext;
        this.userDataCache = userDataCache;
    }

    public BotApiMethod<?> handleUpdate(Update update) {
        BotApiMethod<?> replyMessage = null;
        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            log.info("New message from User:{}, userId: {}, chatId: {},  with text: {}",
                    message.getFrom().getUserName(), message.getFrom().getId(), message.getChatId(), message.getText());
            replyMessage = handleInputMessage(update);
        }
        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            log.info("New callbackQuery from User: {}, userId: {}, with data: {}", update.getCallbackQuery().getFrom().getUserName(),
                    callbackQuery.getFrom().getId(), update.getCallbackQuery().getData());
            replyMessage = handleInputMessage(update);
        }
        return replyMessage;
    }

    private BotApiMethod<?> handleInputCallbackQuery(CallbackQuery callbackQuery) {
        Long chatId = callbackQuery.getMessage().getChatId();
        int id = Math.toIntExact(callbackQuery.getFrom().getId());
        return null;
    }

    private BotApiMethod<?> handleInputMessage(Update update) {
        int id;
        String text;
        if (update.hasCallbackQuery()){
             id = Math.toIntExact(update.getCallbackQuery().getFrom().getId());
             text = update.getCallbackQuery().getData();
        }else {
            Message message = update.getMessage();
            text = message.getText();
             id = Math.toIntExact(message.getFrom().getId());
        }
        BotState botState;
        BotApiMethod<?> replyMessage;
        switch (text) {
            case "/start":
                botState = BotState.ASK_GENDER;
                break;
            case "Анкета":
                botState = BotState.SHOW_USER_PROFILE;
                break;
            case "Помощь":
                botState = BotState.SHOW_HELP_MENU;
                break;
            default:
                botState = userDataCache.getUsersCurrentBotState(id);
        }
        userDataCache.setUsersCurrentBotState(id, botState);

        replyMessage = botStateContext.processInputMessage(botState, update);

        return replyMessage;
    }

}
