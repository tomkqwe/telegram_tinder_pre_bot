package ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.botapi.handlers.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.botapi.BotState;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.botapi.InputMessageHandler;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.cache.DataCache;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.configuration.MyConfig;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.entity.User;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.utils.Communication;

/**
 * В это класс нужно забульбенить картинку с текстом для просмотра своей анкеты на картинке
 */
@Component
public class ShowProfileHandler implements InputMessageHandler {
    private DataCache dataCache;
    @Autowired
    private Communication communication;

    public ShowProfileHandler(DataCache dataCache, Communication communication) {
        this.dataCache = dataCache;
        this.communication = communication;
    }

    @Override
    public SendMessage handle(Message message) {
        int id = Math.toIntExact(message.getFrom().getId());
        User userProfileData = dataCache.getUserProfileData(id);
//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyConfig.class);
//        Communication communication = context.getBean("communication", Communication.class);
//        communication.saveUser(userProfileData);

        dataCache.setUsersCurrentBotState(id,BotState.SHOW_MAIN_MENU);

        return new SendMessage(message.getChatId().toString(), String.format("%s%n -------------------%nИмя: %s%nВозраст: %d%n Пол: %s%nОписание: %s%n" +
                        "Кого ищем: %s%n", "Данные по вашей анкете", userProfileData.getName(), userProfileData.getAge(), userProfileData.getSex(), userProfileData.getDescription(),
                userProfileData.getPartnerSex()));
    }

    @Override
    public BotState getHandlerName() {
        return BotState.SHOW_USER_PROFILE;
    }
}
