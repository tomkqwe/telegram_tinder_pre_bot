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
public class CheckWhoLikedMeListHandler implements InputMessageHandler {
    public static final String VOID_HERE = "К сожалению тут пусто\uD83D\uDE31";
    @Autowired
    private DataCache dataCache;
    @Autowired
    private Communication communication;

    @Override
    public BotApiMethod<?> handleUpdate(Update update) {
        String chatId = UpdateHandler.getChatId(update);
        String text = UpdateHandler.getText(update);
        long id = UpdateHandler.getId(update);
        List<User> whoLikedMe = communication.getWhoLikedMe(id)
                .stream()
                .distinct()
                .collect(Collectors.toList());
        if (whoLikedMe.size() == 0){
            SendMessage sendMessage = new SendMessage(chatId, VOID_HERE);
            sendMessage.setReplyMarkup(WeLikeKeayboard.getWeLikeKeayboard());
            return sendMessage;
        }
        int indexInWhoLikedMe = new GetFavoritesHandler().getIndex();
        switch (text) {
            case WeLikeKeayboard.NEXT: {
                indexInWhoLikedMe++;
                if (indexInWhoLikedMe == whoLikedMe.size()) {
                    indexInWhoLikedMe = 0;
                }
                User user = whoLikedMe.get(indexInWhoLikedMe);
                String resultToOutput = user.toString();
                SendMessage sendMessage = new SendMessage(chatId, resultToOutput);
                sendMessage.setReplyMarkup(WeLikeKeayboard.getWeLikeKeayboard());
                return sendMessage;
            }
            case WeLikeKeayboard.PREVIOUS: {
                indexInWhoLikedMe--;
                if (indexInWhoLikedMe == -1) {
                    indexInWhoLikedMe = whoLikedMe.size() - 1;
                }
                User user = whoLikedMe.get(indexInWhoLikedMe);
                String resultToOutput = user.toString();
                SendMessage sendMessage = new SendMessage(chatId, resultToOutput);
                sendMessage.setReplyMarkup(WeLikeKeayboard.getWeLikeKeayboard());
                return sendMessage;
            }
            case WeLikeKeayboard.BACK:
                SendMessage sendMessage = new SendMessage(chatId, WeLikeHandler.FAVORITES);
                sendMessage.setReplyMarkup(FavoritesKeyboard.getFavoritesKeyboard());
                dataCache.setUsersCurrentBotState(id, BotState.GET_FAVORITES);
                return sendMessage;
            default:
                SendMessage message = new SendMessage(chatId, VOID_HERE);
                message.setReplyMarkup(WeLikeKeayboard.getWeLikeKeayboard());
                return message;
        }

    }

    @Override
    public BotState getHandlerName() {
        return BotState.CHECK_WHO_LIKED_ME_LIST;
    }
}
