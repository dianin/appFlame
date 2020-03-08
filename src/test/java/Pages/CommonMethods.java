package Pages;

import Utils.DriverHandler.*;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;

import static Utils.GetProperty.getConfigProperty;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class CommonMethods {

    AndroidDriver<AndroidElement> driver;
    private WebDriverWait shortWait;
    private WebDriverWait regularWait;
    private WebDriverWait longWait;

    private By allowButton = By.xpath("//*[@text='Allow' or @text='allow' or @text='ALLOW']");

    public CommonMethods(AndroidDriver<AndroidElement> driver)
    {
        this.driver	= driver;
        shortWait	= new WebDriverWait(driver,10);
        regularWait	= new WebDriverWait(driver,60);
        longWait	= new WebDriverWait(driver, 180);
    }

    public  boolean isInstalled(Device device)
    {
       return driver.isAppInstalled(getConfigProperty("speedTestPackage"));
    }

    public  void installIfNeed(Device device){
        if (isInstalled(device)) driver.installApp("speedTestPackage");
    }

    public void openSpeedTest(){
        driver.activateApp(getConfigProperty("speedTestPackage"));
    }

    public enum Permission
    {
        CALLS           ("Allow Speedtest to access this device's location?"),
        LOCATION        ("Allow Speedtest to make and manage phone calls?");

        public String name;
        Permission (String name) { this.name = name;}
    }


    public void allowPermission (Permission permission)
    {
        By generalPermission= By.xpath("//*[contains(@text,'"+permission.name+"')]");
        if(isPresentWithWait(generalPermission, 3))
        {
            click(allowButton);
            waitForAbsenceShort(allowButton);
        }
    }

    public void waitThenClick(By locator)
    {
        regularWait.until(elementToBeClickable(locator)).click();
    }

    public void click(By locator)
    {
        try
        {
            shortWait.until(elementToBeClickable(locator)).click();
        }
        catch(StaleElementReferenceException exception)
        {
            click(locator);
        }
    }

    public String getText(By locator)
    {
        try
        {
            return shortWait.until(presenceOfElementLocated(locator)).getText();
        }
        catch(StaleElementReferenceException e)
        {
            return getContentDescription(locator);
        }
    }

    public String getContentDescription(By locator)
    {
        try
        {
            return shortWait.until(presenceOfElementLocated(locator)).getAttribute("content-desc");
        }
        catch(StaleElementReferenceException e)
        {
            return getContentDescription(locator);
        }
    }
    public List<AndroidElement> returnList(By locator)
    {
        return driver.findElements(locator);
    }

    public void waitForLoadShort(By locator)
    {
        shortWait.until(visibilityOfElementLocated(locator));
    }

    public void waitForAnyElementShort(By ... locators)
    {
        ExpectedCondition<WebElement>[] conditions = new ExpectedCondition[locators.length];
        for(int x = 0; x < locators.length; x++)
        {
            conditions[x] = visibilityOfElementLocated(locators[x]);
        }
        shortWait.until(ExpectedConditions.or(conditions));
    }

    public void waitForLoad(By locator)
    {
        regularWait.until(visibilityOfElementLocated(locator));
    }

    public void waitForAbsenceShort(By locator)
    {
        shortWait.until(invisibilityOfElementLocated(locator));
    }


    public boolean isPresentWithWait(By locator, Integer seconds)
    {
        WebDriverWait waiter = new WebDriverWait(driver, seconds);
        try {
            waiter.until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (Exception e){
            System.out.println(e.toString());
        }
        return !driver.findElements(locator).isEmpty();
    }

}
