package page.project_list;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import utils.BrowserContextFactory;

public class ProjectListPageTest {
    // Shared between all tests in this class.
    static Playwright playwright;
    static Browser browser;

    // New instance for each test method.
    BrowserContext context;
    Page page;
    ProjectListPage projectListPage;

    static BrowserContextFactory factory;

    @BeforeAll
    static void launchBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(false)
                .setSlowMo(100));
        factory = new BrowserContextFactory(browser);
    }

    @AfterAll
    static void closeBrowser() {
        playwright.close();
    }

    @BeforeEach
    void createContextAndPage() {
        context = factory.getAuthenticatedContext();
        page = context.newPage();
        projectListPage = new ProjectListPage(page);
    }

    @AfterEach
    void closeContext() {
        context.close();
    }

    @Test
    void shouldShowProjectList() {
        projectListPage.navigate();

        // Check should empty
        projectListPage.shouldProjectsListEmptyCheck();

        // Create a new project
        projectListPage.createNewProject("test");

        // Remove project
        projectListPage.removeProject("test");

        // Check should empty
        projectListPage.shouldProjectsListEmptyCheck();
    }
}
