package by.epam.edu.webdriver.framework;

import by.epam.edu.webdriver.decorator.WebDriverDecorator;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;

import java.io.File;
import java.io.IOException;

/**
 * Created by Aliaksandr_Sheliutsi on 1/18/2017.
 */
public abstract class AbstractPage {

    protected WebDriverDecorator driver;
    protected JavascriptExecutor jsEx;
    private TakesScreenshot scr;

    public AbstractPage(WebDriver driver) {
        this.driver = new WebDriverDecorator(driver);
        this.jsEx = (JavascriptExecutor) driver;
        this.scr = (TakesScreenshot) driver;
    }

    public void highLightElementAndMakeScreenShot(WebElement element) {
        String jsCodeForChangingBGColor = "arguments[0].style.backgroundColor='%s';";
        String bgColor = element.getCssValue("background-color");

        jsEx.executeScript(String.format(jsCodeForChangingBGColor, "yellow"), element);
        File scrFile = scr.getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(scrFile, new File(System.currentTimeMillis() + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        jsEx.executeScript(String.format(jsCodeForChangingBGColor, bgColor), element);
    }
}
