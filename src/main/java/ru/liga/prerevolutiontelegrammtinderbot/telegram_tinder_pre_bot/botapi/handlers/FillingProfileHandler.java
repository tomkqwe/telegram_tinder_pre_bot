package ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.botapi.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.botapi.BotState;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.botapi.InputMessageHandler;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.cache.DataCache;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.entity.User;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.keyboards.KeyBoardSelector;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.service.ReplyMessagesService;

@Slf4j
@Component
public class FillingProfileHandler  implements InputMessageHandler {
    private final DataCache dataCache;
    private final ReplyMessagesService messageService;

    public FillingProfileHandler(DataCache dataCache, ReplyMessagesService messageService) {
        this.dataCache = dataCache;
        this.messageService = messageService;
    }

    @Override
    public SendMessage handle(Message message) {
        if (dataCache.getUsersCurrentBotState(Math.toIntExact(message.getFrom().getId())).equals(BotState.FILLING_PROFILE)){
            dataCache.setUsersCurrentBotState(Math.toIntExact(message.getFrom().getId()),BotState.ASK_GENDER);
        }
        return processUsersInput(message);
    }

    @Override
    public BotState getHandlerName() {
        return BotState.FILLING_PROFILE;
    }

    private SendMessage processUsersInput(Message inputMessage) {
        String userAnswer = inputMessage.getText();
        int userId = Math.toIntExact(inputMessage.getFrom().getId());
        Long chatId = inputMessage.getChatId();

        User userProfileData = dataCache.getUserProfileData(userId);
        BotState botState = dataCache.getUsersCurrentBotState(userId);

        SendMessage replyToUser = null;

        if (botState.equals(BotState.ASK_GENDER)){
            replyToUser = messageService.getReplyMessage(chatId.toString(),"ask.gender");
            replyToUser.setReplyMarkup(KeyBoardSelector.getInlineKeyboardMarkup(BotState.ASK_GENDER));
            dataCache.setUsersCurrentBotState(userId,BotState.ASK_NAME);
        }
        if (botState.equals(BotState.ASK_NAME)){
            userProfileData.setSex(userAnswer);
            if (userProfileData.getName() == null) {
                replyToUser = messageService.getReplyMessage(chatId.toString(), "ask.name");
            }
            dataCache.setUsersCurrentBotState(userId,BotState.ASK_AGE);
        }
        if (botState.equals(BotState.ASK_AGE)){
            userProfileData.setName(userAnswer);
            replyToUser = messageService.getReplyMessage(chatId.toString(),"ask.age");
            dataCache.setUsersCurrentBotState(userId,BotState.ASK_DESCRIPTION);
        }
        if (botState.equals(BotState.ASK_DESCRIPTION)){
            userProfileData.setAge(Integer.parseInt(userAnswer));
            replyToUser = messageService.getReplyMessage(chatId.toString(),"ask.description");
            dataCache.setUsersCurrentBotState(userId,BotState.ASK_PARTNER_GENDER);
        }
        if (botState.equals(BotState.ASK_PARTNER_GENDER)){
            userProfileData.setDescription(userAnswer);
            replyToUser = messageService.getReplyMessage(chatId.toString(),"ask.partnerGender");
            replyToUser.setReplyMarkup(KeyBoardSelector.getInlineKeyboardMarkup(BotState.ASK_PARTNER_GENDER));
            dataCache.setUsersCurrentBotState(userId,BotState.PROFILE_FILLED);
        }
        if (botState.equals(BotState.PROFILE_FILLED)){
            userProfileData.setPartnerSex(userAnswer);
            dataCache.setUsersCurrentBotState(userId,BotState.SHOW_MAIN_MENU);
            replyToUser = new SendMessage(chatId.toString(),String.format("%s %s","Данные по вашей анкете",userProfileData));
        }

        dataCache.saveUserProfileData(userId,userProfileData);
        return replyToUser;

    }
}
