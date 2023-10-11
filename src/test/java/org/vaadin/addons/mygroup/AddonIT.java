package org.vaadin.addons.mygroup;

import com.vaadin.flow.component.button.testbench.ButtonElement;
import com.vaadin.flow.component.html.testbench.DivElement;
import com.vaadin.flow.component.html.testbench.ParagraphElement;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;


public class AddonIT extends AbstractViewTest {

    @Test
    public void addonTextIsRendered() {
        $(ButtonElement.class).first().click();

        String hex = $(ParagraphElement.class).first().getText();
        Assert.assertEquals("#ff0d00", hex);

        WebElement element = getDriver().findElement(By.tagName("react-color-picker"));
        new Actions(getDriver()).moveToElement(element).moveByOffset(20, 20).click().perform();


        hex = $(ParagraphElement.class).first().getText();
        Assert.assertEquals("#ff0d00", hex);


    }
}
