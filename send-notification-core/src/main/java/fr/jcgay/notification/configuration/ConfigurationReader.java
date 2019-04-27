package fr.jcgay.notification.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

public class ConfigurationReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationReader.class);

    private final Properties properties;
    private final URL location;

    private ConfigurationReader(Properties properties) {
        this(properties, null);
    }

    public ConfigurationReader(Properties configuration, URL location) {
        this.properties = configuration;
        this.location = location;
    }

    public static ConfigurationReader atPath(String path) {
        try {
            return atUrl(new File(path).toURI().toURL());
        } catch (MalformedURLException e) {
            LOGGER.debug("URL built for path [{}] is malformed will use default configuration.", path, e);
            return new ConfigurationReader(new Properties());
        }
    }

    public static ConfigurationReader atUrl(URL url) {
        Properties configuration = new Properties();
        try {
            configuration.load(url.openStream());
        } catch (IOException e) {
            LOGGER.debug("Cannot read configuration at [{}], will use default one.", url, e);
        }
        return new ConfigurationReader(configuration, url);
    }

    public Properties get() {
        LOGGER.debug("Notification configuration read from {} is: {}.", location, properties);
        return properties;
    }
}
