package page.projectList;

import abstracts.CommonPage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import utils.PropertyUtils;

import java.net.URISyntaxException;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class ProjectListPage extends CommonPage {
    private final Page page;
    private final PropertyUtils propertyUtils = new PropertyUtils();

    public ProjectListPage(Page page) {
        this.page = page;
    }

    @Override
    public void navigate() throws URISyntaxException {
        page.navigate(propertyUtils.getBaseUri().toString() + "/projects");
    }

    public Locator getCreateProjectButton() {
        var option = new Page.GetByRoleOptions();
        option.setName("Proje Olustur");

        return page.getByRole(AriaRole.BUTTON, option);
    }

    public Locator getProjectNewModal() {
        return page.getByRole(AriaRole.DIALOG, new Page.GetByRoleOptions()
                .setName("Proje Yarat"));
    }

    public Locator getProjectNewModalNameInput() {
        return getProjectNewModal().getByPlaceholder("Proje adını giriniz");
    }

    public Locator getProjectList() {
        return page.locator("ul.ant-list-items");
    }

    public void createNewProject(String name) {
        getCreateProjectButton().click();

        assertThat(getProjectNewModal()).isVisible();

        getProjectNewModalNameInput().fill(name);

        Locator okButton = getProjectNewModal().getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("OK"));

        assertThat(okButton).isVisible();
        okButton.click();

        assertThat(getProjectList().getByText(name)).isVisible();
    }

    public void removeProject(String name) {
        var createdProject = getProjectList().locator(">li").filter(
                new Locator.FilterOptions().setHas(
                        page.getByText(name)
                )
        ).first();

        // Delete project
        var option = new Locator.GetByRoleOptions();
        option.setName("Sil");

        var deleteButton = createdProject.getByRole(AriaRole.BUTTON, option);
        assertThat(deleteButton).isVisible();
        deleteButton.click();

        var deleteTooltipContainer = page.getByRole(AriaRole.TOOLTIP, new Page.GetByRoleOptions()
                .setName("Projeyi Sil"));

        var tooltipDeleteButton = deleteTooltipContainer.getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions()
                .setName("Yes"));

        assertThat(deleteTooltipContainer).isVisible();
        assertThat(tooltipDeleteButton).isVisible();
        tooltipDeleteButton.click();
    }

    public void shouldProjectsListEmptyCheck() {
        assertThat(getProjectList()).not().isInViewport();
        assertThat(page.getByText("No data")).isVisible();
    }
}
