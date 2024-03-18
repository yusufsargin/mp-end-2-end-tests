package page.projectList;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import utils.ContextFactory;

import java.net.URISyntaxException;

public class ProjectListPageTest {
    // Shared between all tests in this class.
    static ContextFactory contextFactory;

    // New instance for each test method.
    BrowserContext context;
    Page page;
    ProjectListPage projectListPage;

    @BeforeAll
    static void startBrowser(){
        contextFactory = new ContextFactory();
    }

    @AfterAll
    static void closeBrowser() {
        contextFactory.close();
    }

    @BeforeEach
    void createContextAndPage() throws URISyntaxException {
        context = contextFactory.getContext(
                new BrowserType.LaunchOptions()
                        .setHeadless(false)
                        .setSlowMo(100)
        );
        page = context.newPage();
        projectListPage = new ProjectListPage(page);
    }

    @AfterEach
    void closeContext() {
        context.close();
    }

    @Test
    void shouldShowProjectList() throws URISyntaxException {
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
