package by.epam.edu.autoframework.decorator;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by Aliaksandr_Sheliutsi on 1/27/2017.
 */
public class WebDriverDecorator implements WebDriver {

    protected WebDriver driver;
    protected WebDriverWait webDriverWait;
    protected int maxWaitTime = 10;

    public WebDriverDecorator(WebDriver driver) {
        this.driver = driver;
        webDriverWait = new WebDriverWait(driver, maxWaitTime);
    }

    //======Extension methods=================
    public void waitForVisibilityOf(WebElement elem) {
        webDriverWait.until(ExpectedConditions.visibilityOf(elem));
    }

    public void waitForElementToBeClicable(WebElement elem) {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(elem));
    }

    public void waitForAlertIsPresent() {
        webDriverWait.until(ExpectedConditions.alertIsPresent());
    }

    public void waitForInvisibilityOf(WebElement elem) {
        final WebElement el = elem;
        webDriverWait.until(ExpectedConditions.
                invisibilityOfAllElements(new LinkedList<WebElement>() {
                    {
                        add(el);
                    }
                }));
    }
    //========================

    public WebDriver getActualDriver() {return driver;}

    public void get(String s) {
        driver.get(s);
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public String getTitle() {
        String title = driver.getTitle();
        System.out.println("Title:" + title);
        return title;
    }

    public List<WebElement> findElements(By by) {
        return driver.findElements(by);
    }

    public WebElement findElement(By by) {
        return driver.findElement(by);
    }

    public String getPageSource() {
        return driver.getPageSource();
    }

    public void close() {
        driver.close();
    }

    public void quit() {
        driver.quit();
    }

    public Set<String> getWindowHandles() {
        return driver.getWindowHandles();
    }

    public String getWindowHandle() {
        return driver.getWindowHandle();
    }

    public TargetLocator switchTo() {
        return driver.switchTo();
    }

    public Navigation navigate() {
        return driver.navigate();
    }

    public Options manage() {
        return driver.manage();
    }
}
