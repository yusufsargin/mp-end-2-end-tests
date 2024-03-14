package utils;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.options.Cookie;

import java.util.List;

public class BrowserContextFactory {
    private final Browser browser;

    public BrowserContextFactory(Browser browser) {
        this.browser = browser;
    }

    public BrowserContext getAuthenticatedContext(){
        String token = LoginUtils.getUserToken();
        BrowserContext context = browser.newContext();
        List<Cookie> cookies = List.of(
                new Cookie("token", token)
                        .setDomain(LoginUtils.BASE_URL.getHost())
                        .setPath("/")
        );
        context.addCookies(cookies);

        return context;
    }
}
