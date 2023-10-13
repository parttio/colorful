package org.vaadin.addons.parttio.colorful;


import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.BoundingBox;
import org.junit.jupiter.api.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class AddonIT {

    static Playwright playwright = Playwright.create(); // <4>
    @Test
    public void testClicking() {
    }


    @Test
    public void addonTextIsRendered() {
        Browser browser = playwright.chromium().launch(); // <5>
        Page page = browser.newPage();
        page.navigate("http://localhost:" + 8080 + "/");
        page.getByText("Show value").click();

        final String originalValue ="#ff0d00";

        assertThat(page.locator("//p")).containsText(originalValue); // <8>

        BoundingBox boundingBox = page.locator("//hex-color-picker").boundingBox();

        var x = boundingBox.x + boundingBox.width/2;
        var y  = boundingBox.y + boundingBox.height/2;

        // click in the middle and see that the value changes
        page.mouse().click(x, y);

        page.getByText("Show value").click();

        assertThat(page.locator("//p")).not().containsText(originalValue);

    }
}
