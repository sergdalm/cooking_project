package com.sergdalm.cooking.util;

import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {
    private static final Properties PROPERTIES = new Properties();

    private PropertiesUtil() {

    }

    static {
        loadProperties();
    }

    private static void loadProperties() {
        try (var inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream("application.properties")) {
            PROPERTIES.load(inputStream);
        } catch (IOException exc) {
            throw new RuntimeException(exc);
        }
    }

    public static String get(String key) {
        return PROPERTIES.getProperty(key);
    }
}