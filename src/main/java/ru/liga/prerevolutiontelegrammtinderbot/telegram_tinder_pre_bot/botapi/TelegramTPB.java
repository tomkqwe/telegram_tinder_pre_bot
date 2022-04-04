package ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.botapi;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.configuration.MyConfig;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.entity.User;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.utils.Communication;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.utils.PropertiesUtil;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TelegramTPB extends TelegramWebhookBot {
    private final String webHookPath = "bot.webHookPath";
    private final String botUserName = "bot.name";
    private final String botToken = "bot.token";
    @Autowired
    private TelegramFacade telegramFacade;

    public TelegramTPB(TelegramFacade telegramFacade) {
        this.telegramFacade = telegramFacade;
    }


    @Override
    public String getBotUsername() {
        return PropertiesUtil.get(botUserName);
    }

    @Override
    public String getBotToken() {
        return PropertiesUtil.get(botToken);
    }

    @Override
    public String getBotPath() {
        return PropertiesUtil.get(webHookPath);
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
//                if (update.getMessage() != null && update.getMessage().hasText()) {
//            long chat_id = update.getMessage().getChatId();
//            String s = update.getMessage().getChatId().toString();
//            try {
//                returnUserById(update,chat_id);
////                execute(new SendMessage(s, "Hi" + update.getMessage().getText()));
//            } catch (TelegramApiException e) {
//                e.printStackTrace();
//            }
//        }

        return telegramFacade.handleUpdate(update);
//        return null;
    }

    private void returnUserById(Update update, Long chatId) throws TelegramApiException {
        String s = update.getMessage().getText();
        execute(new SendMessage(chatId.toString(), getAllUsers().toString()));
    }
//
    private String getAllUsers() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyConfig.class);
        Communication communication = context.getBean("communication", Communication.class);
        List<User> allUsers = communication.getAllUsers();
        return allUsers.stream().map(User::toString).collect(Collectors.joining("\n"));
    }
//
//
//    private void deleteNullUser() {
//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyConfig.class);
//        Communication communication = context.getBean("communication", Communication.class);
//        communication.deleteUser(11);
//    }
}
