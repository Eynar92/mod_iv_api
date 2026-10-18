package com.todoapi.core.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/** Reads the API base URL from the classpath configuration. */
public final class ConfigReader {
    private static final String CONFIG_RESOURCE = "config.properties";
    private final Properties properties;

    /** Loads configuration once when the reader is created.
     * @throws IllegalStateException when the resource is missing or unreadable
     */
    public ConfigReader() {
        properties = new Properties();
        try (InputStream stream = ConfigReader.class.getClassLoader().getResourceAsStream(CONFIG_RESOURCE)) {
            if (stream == null) {
                throw new IllegalStateException("Required classpath resource config.properties was not found");
            }
            properties.load(stream);
        } catch (IOException exception) {
            throw new IllegalStateException("Could not read classpath resource config.properties", exception);
        }
    }

    /** Returns the configured API base URL.
     * @return the API base URL
     */
    public String getBaseUrl() {
        return properties.getProperty("api.base.url");
    }
}
