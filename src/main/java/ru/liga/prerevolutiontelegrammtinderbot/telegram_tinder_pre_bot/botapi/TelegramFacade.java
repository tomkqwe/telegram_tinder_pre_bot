package ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.botapi;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.cache.DataCache;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.entity.User;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.service.MainMenuService;

/**
 * @author Sergei Viacheslaev
 */
@Data
@Component
@Slf4j
public class TelegramFacade {
    private BotStateContext botStateContext;
    private DataCache userDataCache;
    private MainMenuService mainMenuService;

    public TelegramFacade(BotStateContext botStateContext, DataCache userDataCache, MainMenuService mainMenuService) {
        this.botStateContext = botStateContext;
        this.userDataCache = userDataCache;
        this.mainMenuService = mainMenuService;
    }

    public BotApiMethod<?> handleUpdate(Update update) {

        BotApiMethod<?> replyMessage = null;
        Message message = update.getMessage();
        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            log.info("New callbackQuery from User: {}, userId: {}, with data: {}", update.getCallbackQuery().getFrom().getUserName(),
                    callbackQuery.getFrom().getId(), update.getCallbackQuery().getData());
            replyMessage = processCallbackQuery(callbackQuery);
            return replyMessage;
        } else if (message != null && message.hasText()) {
            log.info("New message from User:{}, chatId: {},  with text: {}",
                    message.getFrom().getUserName(), message.getChatId(), message.getText());
            replyMessage = handleInputMessage(message);
        }

        return replyMessage;
    }

    private BotApiMethod<?> processCallbackQuery(CallbackQuery callbackQuery) {
        Long chatId = callbackQuery.getMessage().getChatId();
        int id = Math.toIntExact(callbackQuery.getFrom().getId());
        BotApiMethod<?> callBackAnswer = mainMenuService.getMainMenuMessage(chatId.toString(),"Воспользуйтесь главным меню");


        if (callbackQuery.getData().equals("Мужской")) {
//        if (callbackQuery.getData().equals("Сударь")) {
            User userProfileData = userDataCache.getUserProfileData(id);
            userProfileData.setSex("Сударь");
            userDataCache.saveUserProfileData(id, userProfileData);
            userDataCache.setUsersCurrentBotState(id, BotState.ASK_AGE);//tut ispravil
            String sex = userDataCache.getUserProfileData(id).getSex();
            callBackAnswer = new SendMessage(chatId.toString(), "Как вас величать?"+ sex);

        } else if (callbackQuery.getData().equals("Женский")) {
//        } else if (callbackQuery.getData().equals("Сударыня")) {
            User userProfileData = userDataCache.getUserProfileData(id);
            userProfileData.setSex("Сударыня");
            userDataCache.saveUserProfileData(id, userProfileData);
            userDataCache.setUsersCurrentBotState(id, BotState.ASK_AGE);
            String sex = userDataCache.getUserProfileData(id).getSex();
            callBackAnswer = new SendMessage(chatId.toString(), "Как вас величать?"+sex);

        } else if (callbackQuery.getData().equals("ищу Мужчин")) {
            User userProfileData = userDataCache.getUserProfileData(id);
            userProfileData.setPartnerSex("Судари");
            userDataCache.setUsersCurrentBotState(id, BotState.SHOW_MAIN_MENU);
            callBackAnswer = new SendMessage(chatId.toString(), "Анкета заполнена!");

        } else if (callbackQuery.getData().equals("ищу Женщин")) {
            User userProfileData = userDataCache.getUserProfileData(id);
            userProfileData.setPartnerSex("Сударыни");
            userDataCache.setUsersCurrentBotState(id, BotState.SHOW_MAIN_MENU);
            callBackAnswer = new SendMessage(chatId.toString(), "Анкета заполнена!");

        } else if (callbackQuery.getData().equals("Все")) {
            User userProfileData = userDataCache.getUserProfileData(id);
            userProfileData.setPartnerSex("Все");
            userDataCache.setUsersCurrentBotState(id, BotState.SHOW_MAIN_MENU);
            callBackAnswer = new SendMessage(chatId.toString(), "Анкета заполнена!");
        }else {
            userDataCache.setUsersCurrentBotState(id,BotState.SHOW_MAIN_MENU);
        }
        return callBackAnswer;
    }

    private SendMessage handleInputMessage(Message message) {
        String inputMsg = message.getText();
        int userId = Math.toIntExact(message.getFrom().getId());
        BotState botState;
        SendMessage replyMessage;
//        Проверить логику работы свитчкейса получается пользователь у бота спрашивает про имя пол и тд?итд
        switch (inputMsg) {
            case "/start":
                botState = BotState.FILLING_PROFILE;
                break;
//            case "Как вас величать?":
//                botState = BotState.ASK_NAME;
//                break;
//            case "Опишите себя":
//                botState = BotState.ASK_DESCRIPTION;
//                break;
            case "Поиск":
                botState = BotState.GET_SEARCHING;
                break;
            case "Анкета":
                botState = BotState.SHOW_USER_PROFILE;
                break;
            case "Любимцы":
                botState = BotState.GET_FAVORITES;
                break;
            case "Помощь":
                botState = BotState.SHOW_HELP_MENU;
                break;

            default:
                botState = userDataCache.getUsersCurrentBotState(userId);
        }

        userDataCache.setUsersCurrentBotState(userId, botState);

        replyMessage = botStateContext.processInputMessage(botState, message);

        return replyMessage;
    }


}
