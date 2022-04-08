package ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.botapi.handlers.getFavorites;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
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
import java.util.stream.Collectors;

@Component
public class WeLikeHandler implements InputMessageHandler {
    public static final String FAVORITES = "Возвращаемся в меню Любимцы";
    @Autowired
    private DataCache dataCache;
    @Autowired
    private Communication communication;

    @Override
    public BotApiMethod<?> handleUpdate(Update update) {
        String chatId = UpdateHandler.getChatId(update);
        String text = UpdateHandler.getText(update);
        long id = UpdateHandler.getId(update);
        List<User> weLike = communication
                .getWeLike(id)
                .stream()
                .distinct()
                .collect(Collectors.toList());

        if (weLike.size() == 0){
            SendMessage sendMessage = new SendMessage(chatId, CheckWhoLikedMeListHandler.VOID_HERE);
            sendMessage.setReplyMarkup(WeLikeKeayboard.getWeLikeKeayboard());
            return sendMessage;
        }

        int indexInWeLike = new GetFavoritesHandler().getIndex();
        switch (text) {
            case WeLikeKeayboard.NEXT: {
                indexInWeLike++;
                if (indexInWeLike == weLike.size()) {
                    indexInWeLike = 0;
                }
                User user = weLike.get(indexInWeLike);
                String resultToOutput = user.toString();
                SendMessage sendMessage = new SendMessage(chatId, resultToOutput);
                sendMessage.setReplyMarkup(WeLikeKeayboard.getWeLikeKeayboard());
                return sendMessage;
            }
            case WeLikeKeayboard.PREVIOUS: {
                indexInWeLike--;
                if (indexInWeLike == -1) {
                    indexInWeLike = weLike.size() - 1;
                }
                User user = weLike.get(indexInWeLike);
                String resultToOutput = user.toString();
                SendMessage sendMessage = new SendMessage(chatId, resultToOutput);
                sendMessage.setReplyMarkup(WeLikeKeayboard.getWeLikeKeayboard());
                return sendMessage;
            }
            case WeLikeKeayboard.BACK: {
                SendMessage sendMessage = new SendMessage(chatId, FAVORITES);
                sendMessage.setReplyMarkup(FavoritesKeyboard.getFavoritesKeyboard());
                dataCache.setUsersCurrentBotState(id, BotState.GET_FAVORITES);
                return sendMessage;
            }
            default:
                return new SendMessage(chatId, "defaul bivaet zhe");
        }
    }

    @Override
    public BotState getHandlerName() {
        return BotState.CHECK_WE_LIKE_LIST;
    }
}
