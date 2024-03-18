package utils;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.Cookie;

import java.net.URISyntaxException;
import java.util.List;

public class ContextFactory {
    private final Playwright playwright;
    private final PropertyUtils propertyUtils = new PropertyUtils();

    public ContextFactory() {
        this.playwright = Playwright.create();
    }

    private BrowserContext getAuthenticatedContext(Browser browser) throws URISyntaxException {
        String token = LoginUtils.getUserToken();
        BrowserContext context = browser.newContext();
        List<Cookie> cookies = List.of(
                new Cookie("token", token)
                        .setDomain(propertyUtils.getBaseUri().getHost())
                        .setPath("/")
        );
        context.addCookies(cookies);

        return context;
    }

    public BrowserContext getContext(BrowserType.LaunchOptions launchOptions) throws URISyntaxException {
        Browser browser = new BrowserFactory(playwright).getBrowser(launchOptions);

        return getAuthenticatedContext(browser);
    }

    public void close(){
        playwright.close();
    }
}
