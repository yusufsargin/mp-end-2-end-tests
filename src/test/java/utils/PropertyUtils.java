package utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Properties;

public class PropertyUtils {
    private final Properties properties = new Properties();

    public PropertyUtils() {
        String devResource = "application.dev";
        String prodResource = "application.prod";

        String resourceName = Objects.equals(System.getenv("profile"), "dev") ? devResource : prodResource; // could also be a constant
        ClassLoader loader = Thread.currentThread().getContextClassLoader();

        try(InputStream resourceStream = loader.getResourceAsStream(resourceName)) {
            properties.load(resourceStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public URI getBaseUri() throws URISyntaxException {
        String baseUrl = properties.getProperty("base-url");

        return new URI(baseUrl);
    }

    public URI getBaseBackendUri() throws URISyntaxException {
        return new URI(properties.getProperty("base-backend-url"));
    }

    public String getUserEmail(){
        return properties.getProperty("user-email");
    }

     public String getBrowserType(){
        return properties.getProperty("browser-type");
    }
}
