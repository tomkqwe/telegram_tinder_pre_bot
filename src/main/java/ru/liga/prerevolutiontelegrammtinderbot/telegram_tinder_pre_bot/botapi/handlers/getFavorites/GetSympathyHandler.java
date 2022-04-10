package ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.botapi.handlers.getFavorites;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.botapi.BotState;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.botapi.InputMessageHandler;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.cache.DataCache;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.entity.User;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.keyboards.FavoritesKeyboard;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.keyboards.WeLikeKeayboard;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.utils.Communication;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.utils.UpdateHandler;

import java.util.List;

@Component
public class GetSympathyHandler implements InputMessageHandler {
    @Autowired
    private DataCache dataCache;
    @Autowired
    private Communication communication;
    private int index = 0;

    @Override
    public PartialBotApiMethod<?> handleUpdate(Update update) {
        String chatId = UpdateHandler.getChatId(update);
        String text = UpdateHandler.getText(update);
        long id = UpdateHandler.getId(update);

        List<User> sympathy = communication.getSympathy(id);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setReplyMarkup(WeLikeKeayboard.getWeLikeKeayboard());


        switch (text) {
            case WeLikeKeayboard.NEXT: {
                index++;
                if (index == sympathy.size()) {
                    index = 0;
                }
                User user = sympathy.get(index);
                String resultToOutput = user.toString();
                sendMessage.setText(resultToOutput);
                return sendMessage;
            }
            case WeLikeKeayboard.PREVIOUS: {
                index--;
                if (index == -1) {
                    index = sympathy.size() - 1;
                }
                User user = sympathy.get(index);
                String resultToOutput = user.toString();
                sendMessage.setText(resultToOutput);
                return sendMessage;
            }
            case WeLikeKeayboard.BACK: {
                sendMessage.setText(WeLikeHandler.FAVORITES);
                sendMessage.setReplyMarkup(FavoritesKeyboard.getFavoritesKeyboard());
                dataCache.setUsersCurrentBotState(id, BotState.GET_FAVORITES);
                return sendMessage;
            }
            default:
                sendMessage.setText("default");
                return sendMessage;
        }

    }

    @Override
    public BotState getHandlerName() {
        return BotState.CHECK_SYMPATHY_LIST;
    }
}
