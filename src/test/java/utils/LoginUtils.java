package utils;

import com.google.gson.Gson;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import models.TokenResponseDto;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class LoginUtils {
    static private final String API_TOKEN = System.getenv("API_TOKEN");
    static private final Playwright playwright = Playwright.create();
    static private final PropertyUtils propertyUtils = new PropertyUtils();

    public static String getUserToken() throws URISyntaxException {
        Map<String, String> headers = new HashMap<>();

        headers.put("x-playwright-api-token", API_TOKEN);

        APIRequestContext context = playwright.request().newContext(new APIRequest.NewContextOptions()
                .setBaseURL(propertyUtils.getBaseBackendUri().toString())
                .setExtraHTTPHeaders(headers));

        Gson gson = new Gson();
        String json = context.post("/webapi/user/test/signIn?email=" + propertyUtils.getUserEmail(), RequestOptions.create()).text();
        TokenResponseDto responseDto = gson.fromJson(json, TokenResponseDto.class);

        return responseDto.getMessage();
    }
}
