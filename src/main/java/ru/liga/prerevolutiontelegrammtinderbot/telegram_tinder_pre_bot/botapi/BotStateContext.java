package ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.botapi;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Defines message handlers for each state.
 */
@Component
public class BotStateContext {
    private final Map<BotState, InputMessageHandler> messageHandlers = new HashMap<>();

    public BotStateContext(List<InputMessageHandler> messageHandlers) {
        messageHandlers.forEach(handler -> this.messageHandlers.put(handler.getHandlerName(), handler));
    }

//    public SendMessage processInputMessage(BotState currentState, Message message) {
//        InputMessageHandler currentMessageHandler = findMessageHandler(currentState);
//
//        return currentMessageHandler.handle(message);
//    }
    public BotApiMethod<?> processInputMessage(BotState currentState, Update update) {
        InputMessageHandler currentMessageHandler = findMessageHandler(currentState);

        return currentMessageHandler.handleUpdate(update);
    }

    private InputMessageHandler findMessageHandler(BotState currentState) {
//        if (isFillingProfileState(currentState)) {
////            return messageHandlers.get(BotState.FILLING_PROFILE);
//            return messageHandlers.get(BotState.ASK_GENDER);
//        }

        return messageHandlers.get(currentState);
    }

    private boolean isFillingProfileState(BotState currentState) {
        switch (currentState) {
            case ASK_NAME:
            case ASK_AGE:
            case ASK_GENDER:
            case ASK_DESCRIPTION:
            case ASK_PARTNER_GENDER:
            case IS_FILLING_PROFILE:
            case PROFILE_FILLED://если профиль заполнен то это состояние заполнение профиля?
                return true;
            default:
                return false;
        }
    }
}





