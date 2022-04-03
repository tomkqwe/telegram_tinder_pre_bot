package ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.botapi.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.botapi.BotState;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.botapi.InputMessageHandler;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.cache.DataCache;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.entity.User;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.keyboards.KeyBoardSelector;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.service.ReplyMessagesService;

@Slf4j
@Component
public class FillingProfileHandler implements InputMessageHandler {
    private final DataCache dataCache;
    private final ReplyMessagesService messageService;

    public FillingProfileHandler(DataCache dataCache, ReplyMessagesService messageService) {
        this.dataCache = dataCache;
        this.messageService = messageService;
    }

    @Override
    public SendMessage handle(Update update) {
        if (dataCache.getUsersCurrentBotState(Math.toIntExact(update.getUpdateId())).equals(BotState.FILLING_PROFILE)) {
            dataCache.setUsersCurrentBotState(Math.toIntExact(update.getUpdateId()), BotState.ASK_GENDER);
        }
        return processUsersInput(update);
    }

    @Override
    public BotState getHandlerName() {
        return BotState.FILLING_PROFILE;
    }

    private SendMessage processUsersInput(Update update) {
        int userId = Math.toIntExact(update.getUpdateId());
        Long chatId = getChatId(update);
        User userProfileData = dataCache.getUserProfileData(userId);
        BotState botState = dataCache.getUsersCurrentBotState(userId);

        SendMessage replyToUser = null;

        if (botState.equals(BotState.ASK_GENDER)) {
            replyToUser = messageService.getReplyMessage(chatId.toString(), "ask.gender");
            replyToUser.setReplyMarkup(KeyBoardSelector.getInlineKeyboardMarkup(BotState.ASK_GENDER));
            dataCache.setUsersCurrentBotState(userId, BotState.ASK_NAME);
        }
        if (botState.equals(BotState.ASK_NAME)) {
            replyToUser = messageService.getReplyMessage(chatId.toString(), "ask.name");
            String userAnswer;
            if (getText(update).equals("Мужской")) {
                userAnswer = "Сударь";
            } else {
                userAnswer = "Сударыня";
            }
            userProfileData.setSex(userAnswer);
            dataCache.setUsersCurrentBotState(userId, BotState.ASK_AGE);
        }
        if (botState.equals(BotState.ASK_AGE)) {
            String userAnswer = getText(update);
            userProfileData.setName(userAnswer);
            replyToUser = messageService.getReplyMessage(chatId.toString(), "ask.age");
            dataCache.setUsersCurrentBotState(userId, BotState.ASK_DESCRIPTION);
        }
        if (botState.equals(BotState.ASK_DESCRIPTION)) {
            String userAnswer = getText(update);
            userProfileData.setAge(Integer.parseInt(userAnswer));
            replyToUser = messageService.getReplyMessage(chatId.toString(), "ask.description");
            dataCache.setUsersCurrentBotState(userId, BotState.ASK_PARTNER_GENDER);
        }
        if (botState.equals(BotState.ASK_PARTNER_GENDER)) {
            String userAnswer = getText(update);
            userProfileData.setDescription(userAnswer);
            replyToUser = messageService.getReplyMessage(chatId.toString(), "ask.partnerGender");
            replyToUser.setReplyMarkup(KeyBoardSelector.getInlineKeyboardMarkup(BotState.ASK_PARTNER_GENDER));
            dataCache.setUsersCurrentBotState(userId, BotState.PROFILE_FILLED);
        }
        if (botState.equals(BotState.PROFILE_FILLED)) {
            String userAnswer;
            if (getText(update).equals("ищу Мужчин")) {
                userAnswer = "Судари";
            } else if (update.getCallbackQuery().getData().equals("ищу Женщин")) {
                userAnswer = "Сударыни";
            } else {
                userAnswer = "Все";
            }
            userProfileData.setPartnerSex(userAnswer);
            dataCache.setUsersCurrentBotState(userId, BotState.SHOW_MAIN_MENU);
            replyToUser = new SendMessage(chatId.toString(), String.format("%s %s", "Данные по вашей анкете", userProfileData));
        }

        dataCache.saveUserProfileData(userId, userProfileData);
        return replyToUser;
    }

    private String getText(Update update){
        if (update.hasCallbackQuery()){
            return update.getCallbackQuery().getData();
        } else {
            return update.getMessage().getText();
        }
    }

    private long getChatId(Update update){
        if (update.hasCallbackQuery()){
            return update.getCallbackQuery().getFrom().getId();
        } else {
            return update.getMessage().getChatId();
        }
    }
}
