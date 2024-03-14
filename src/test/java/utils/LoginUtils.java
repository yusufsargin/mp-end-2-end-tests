package utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import models.TokenResponseDto;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LoginUtils {
    static private final String API_TOKEN = System.getenv("API_TOKEN");
    static private final Playwright playwright = Playwright.create();
    static public final URI BASE_URL;

    static {
        try {
            BASE_URL = new URI(System.getenv("BASE_URL"));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    static public final String BE_BASE_URL = System.getenv("BE_BASE_URL");
    static private final String email = System.getenv("USER_EMAIL");

    public static String getUserToken() {
        Map<String, String> headers = new HashMap<>();

        headers.put("x-playwright-api-token", API_TOKEN);

        APIRequestContext context = playwright.request().newContext(new APIRequest.NewContextOptions()
                .setBaseURL(BE_BASE_URL)
                .setExtraHTTPHeaders(headers));

        Gson gson = new Gson();
        String json = context.post("/webapi/user/test/signIn?email=" + email, RequestOptions.create()).text();
        TokenResponseDto responseDto = gson.fromJson(json, TokenResponseDto.class);

        return responseDto.getMessage();
    }
}
