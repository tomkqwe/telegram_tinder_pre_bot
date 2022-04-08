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
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.keyboards.MainMenuKeyboard;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.keyboards.WeLikeKeayboard;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.utils.Communication;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.utils.UpdateHandler;

import java.util.List;

@Component
public class GetFavoritesHandler implements InputMessageHandler {
    public static final String USE_MAIN_MENU = "Воспользуйтесь главным меню";
    public static final String MENU_FAVORITES = "Меню Любимцы";
    public static final String BACK = "Назад";
    public static final String YOURCHOOSE = "Нравятся вам";
    public static final String WHO_LIKED_ME = "Выбрали вас";
    public static final String SYMPATHY = "Взаимный выбор";
    private final int index = 0;
    @Autowired
    private DataCache dataCache;
    @Autowired
    private Communication communication;

    public int getIndex() {
        return index;
    }

    @Override
    public BotApiMethod<?> handleUpdate(Update update) {
        String chatId = UpdateHandler.getChatId(update);
        String text = UpdateHandler.getText(update);
        long id = UpdateHandler.getId(update);
        List<User> weLike = communication.getWeLike(id);
        List<User> whoLikedMe = communication.getWhoLikedMe(id);
        List<User> sympathy = communication.getSympathy(id);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setReplyMarkup(WeLikeKeayboard.getWeLikeKeayboard());

        switch (text) {
            case YOURCHOOSE: {
                if (weLike.isEmpty()) {
                    sendMessage.setText(CheckWhoLikedMeListHandler.VOID_HERE);
                    sendMessage.setReplyMarkup(FavoritesKeyboard.getFavoritesKeyboard());
                    return sendMessage;
                }
                User user = weLike.get(index);
                String resultToOutput = user.toString();
                sendMessage.setText(resultToOutput);
                dataCache.setUsersCurrentBotState(id, BotState.CHECK_WE_LIKE_LIST);
                return sendMessage;

            }
            case WHO_LIKED_ME: {
                if (whoLikedMe.isEmpty()) {
                    sendMessage.setText(CheckWhoLikedMeListHandler.VOID_HERE);
                    sendMessage.setReplyMarkup(FavoritesKeyboard.getFavoritesKeyboard());
                    return sendMessage;
                }
                User user = whoLikedMe.get(index);
                String resultToOutput = user.toString();
                sendMessage.setText(resultToOutput);
                dataCache.setUsersCurrentBotState(id, BotState.CHECK_WHO_LIKED_ME_LIST);
                return sendMessage;

            }
            case SYMPATHY: {
                if (sympathy.isEmpty()) {
                    sendMessage.setText(CheckWhoLikedMeListHandler.VOID_HERE);
                    sendMessage.setReplyMarkup(FavoritesKeyboard.getFavoritesKeyboard());
                    return sendMessage;
                }
                User user = sympathy.get(index);
                String resultToOutput = user.toString();
                sendMessage.setText(resultToOutput);
                dataCache.setUsersCurrentBotState(id, BotState.CHECK_SYMPATHY_LIST);
                return sendMessage;
            }
            case BACK: {
                sendMessage.setText(USE_MAIN_MENU);
                sendMessage.setReplyMarkup(MainMenuKeyboard.getMainMenuKeyboard());
                return sendMessage;
            }
        }
        sendMessage.setText(MENU_FAVORITES);
        sendMessage.setReplyMarkup(FavoritesKeyboard.getFavoritesKeyboard());
        return sendMessage;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.GET_FAVORITES;
    }
}
