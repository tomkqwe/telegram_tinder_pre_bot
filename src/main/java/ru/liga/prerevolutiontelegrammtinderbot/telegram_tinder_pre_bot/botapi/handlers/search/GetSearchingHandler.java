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
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.keyboards.SearchKeyboard;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.keyboards.MainMenuKeyboard;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.utils.Communication;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.utils.UpdateHandler;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GetSearchingHandler implements InputMessageHandler {
    public static final String LIKE = "Лайк❤️";
    public static final String NEXT = "Следующий❌";
    public static final String BACK = "Назад";
    public static final String USE_MAIN_MENU = "Воспользуйтесь главным меню";
    @Autowired
    private DataCache dataCache;
    @Autowired
    private Communication communication;
    private int index = 0;
    private final HashSet<User> myChoose = new HashSet<>();


    @Override
    public BotApiMethod<?> handleUpdate(Update update) {
        int userID = Math.toIntExact(UpdateHandler.getId(update));
        String chatID = UpdateHandler.getChatId(update);
        String text = UpdateHandler.getText(update);
        User currentUser = communication.getUser(userID);
        List<User> allUsers = communication.getAllUsers();
        List<User> users = filterList(userID, allUsers);
        if (text.equals(LIKE)){
            myChoose.add(users.get(index));
             currentUser.getWhoDidIChoose().add(users.get(index));
            communication.updateUser(currentUser);
            index++;
            if (index == users.size()) {
                index = 0;
            }
        }
       else if (text.equals(NEXT)) {
            index++;
            if (index == users.size()) {
                index = 0;
            }
        }
        else if (text.equals(BACK)){
            SendMessage sendMessage = new SendMessage(chatID, USE_MAIN_MENU);
            sendMessage.setReplyMarkup(MainMenuKeyboard.getMainMenuKeyboard());
            return sendMessage;
        }

        User user = users.get(index);
        String changeThisShitToPicture = user.toString();
        SendMessage sendMessage = new SendMessage(chatID, changeThisShitToPicture);
        sendMessage.setReplyMarkup(SearchKeyboard.getSearchKeyboard());
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
