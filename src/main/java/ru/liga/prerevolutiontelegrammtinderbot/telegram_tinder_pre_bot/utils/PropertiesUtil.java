package ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class PropertiesUtil {
    private static final Properties PROPERTIES = new Properties();

    private PropertiesUtil() {
    }

    static {
        loadProperties();
    }

    private static void loadProperties() {
        try (InputStream resourceAsStream = PropertiesUtil.class.getClassLoader().getResourceAsStream("application.properties")) {
            PROPERTIES.load(resourceAsStream);
        } catch (IOException e) {
            throw new RuntimeException("Не удалось прочитать Property",e);
        }
    }
    public static String get(String key){
        return PROPERTIES.getProperty(key);
    }

}
