package utils;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;

public class BrowserFactory {
    private final Playwright playwright;
    private final PropertyUtils propertyUtils = new PropertyUtils();

    public BrowserFactory(Playwright playwright) {
        this.playwright = playwright;
    }

    public Browser getBrowser(BrowserType.LaunchOptions launchOptions) {
        return switch (propertyUtils.getBrowserType()) {
            case "chrome" -> playwright.
                    chromium()
                    .launch(launchOptions);
            case "firefox" -> playwright.
                    firefox()
                    .launch(launchOptions);
            case "safari" -> playwright.
                    webkit()
                    .launch(launchOptions);
            default -> throw new IllegalArgumentException("Browser type does not found");
        };
    }
}
