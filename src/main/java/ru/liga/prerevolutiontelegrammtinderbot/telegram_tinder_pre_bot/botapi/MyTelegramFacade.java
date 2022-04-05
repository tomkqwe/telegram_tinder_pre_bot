package ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.botapi;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.cache.DataCache;

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
        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            log.info("New message from User:{}, userId: {}, chatId: {},  with text: {}",
                    message.getFrom().getUserName(), message.getFrom().getId(), message.getChatId(), message.getText());
        }
        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            log.info("New callbackQuery from User: {}, userId: {}, with data: {}", update.getCallbackQuery().getFrom().getUserName(),
                    callbackQuery.getFrom().getId(), update.getCallbackQuery().getData());
        }
        return handleInputMessage(update);
    }

    private BotApiMethod<?> handleInputMessage(Update update) {
        int id;
        String text;
        if (update.hasCallbackQuery()) {
            id = Math.toIntExact(update.getCallbackQuery().getFrom().getId());
            text = update.getCallbackQuery().getData();
        } else {
            Message message = update.getMessage();
            text = message.getText();
            id = Math.toIntExact(message.getFrom().getId());
        }
        BotState botState;
        switch (text) {
            case "/start":
                botState = BotState.START_STATE;
                break;
            case "Поехали":
                botState = BotState.ASK_GENDER;
                break;
            case "Анкета":
                botState = BotState.SHOW_USER_PROFILE;
                break;
            case "Смотреть любимцов":
                botState = BotState.GET_FAVORITES;
            case "Искать":
                botState = BotState.GET_SEARCHING;
                break;
            default:
                botState = userDataCache.getUsersCurrentBotState(id);
        }
        userDataCache.setUsersCurrentBotState(id, botState);

        return botStateContext.processInputMessage(botState, update);
    }


}
