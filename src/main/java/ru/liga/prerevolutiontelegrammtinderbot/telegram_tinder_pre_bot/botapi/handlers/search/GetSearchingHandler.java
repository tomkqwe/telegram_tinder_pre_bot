package ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.botapi.handlers.search;

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
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.utils.Communication;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.utils.UpdateHandler;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GetSearchingHandler implements InputMessageHandler {
    @Autowired
    private DataCache dataCache;
    @Autowired
    private Communication communication;
    private int count = 0;

    @Override
    public BotApiMethod<?> handleUpdate(Update update) {
        int userID = Math.toIntExact(UpdateHandler.getId(update));
        String chatID = UpdateHandler.getChatId(update);
        String text = UpdateHandler.getText(update);
        List<User> allUsers = communication.getAllUsers();
        List<User> users = filterList(userID, allUsers);
        if (text.equals("Следующий")) {
            count++;
            if (count == users.size()) {
                count = 0;
            }
        } else if (text.equals("Предидущий")) {
            count--;
            if (count == -1){
                count = users.size()-1;
            }
        }else if (text.equals("Назад")){
            SendMessage sendMessage = new SendMessage(chatID,"Воспользуйтесь главным меню");
            sendMessage.setReplyMarkup(MainMenuKeyboard.getMainMenuKeyboard());
            return sendMessage;
        }

        User user = users.get(count);
        String changeThisShitToPicture = user.toString();
        SendMessage sendMessage = new SendMessage(chatID, changeThisShitToPicture);
        sendMessage.setReplyMarkup(FavoritesKeyboard.getFavoritsKeyboard());
        return sendMessage;
    }

    private List<User> filterList(int userID, List<User> allUsers) {
        return allUsers
                .stream()
                .filter(user -> user.getId() != userID)
                .collect(Collectors.toList());
    }

    @Override
    public BotState getHandlerName() {
        return BotState.GET_SEARCHING;
    }

}
