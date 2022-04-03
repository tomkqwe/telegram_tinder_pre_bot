package ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.configuration;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.client.RestTemplate;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.botapi.TelegramFacade;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.botapi.TelegramTPB;

@Configuration
@ComponentScan("ru.liga.prerevolutiontelegrammtinderbot")
public class MyConfig {
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
    @Bean
    public TelegramTPB myTelegramTPB(TelegramFacade telegramFacade){
        return new TelegramTPB(telegramFacade);
    }
    @Bean
    public MessageSource messageSource(){
        ReloadableResourceBundleMessageSource messageSource =
                new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}

