package ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.botapi.handlers.getFavorites;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.botapi.BotState;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.botapi.InputMessageHandler;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.cache.DataCache;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.keyboards.FavoritesKeyboard;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.keyboards.MainMenuKeyboard;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.keyboards.SearchKeyboard;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.utils.Communication;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.utils.UpdateHandler;

@Component
public class GetFavoritesHandler implements InputMessageHandler {
    public static final String USE_MAIN_MENU = "Воспользуйтесь главным меню";
    public static final String TEST_STRING = "Ne pridumal cho TUT DOLJNO BIT";
    public static final String BACK = "Назад";
    @Autowired
    private DataCache dataCache;
    @Autowired
    private Communication communication;
    @Override
    public BotApiMethod<?> handleUpdate(Update update) {
        String chatId = UpdateHandler.getChatId(update);
        String text = UpdateHandler.getText(update);
        int id = Math.toIntExact(UpdateHandler.getId(update));
        if (text.equals(BACK)){
            SendMessage sendMessage = new SendMessage(chatId, USE_MAIN_MENU);
            sendMessage.setReplyMarkup(MainMenuKeyboard.getMainMenuKeyboard());
            return sendMessage;
        }
        SendMessage sendMessage = new SendMessage(chatId, TEST_STRING);
        sendMessage.setReplyMarkup(FavoritesKeyboard.getFavoritesKeyboard());
        return sendMessage;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.GET_FAVORITES;
    }
}
