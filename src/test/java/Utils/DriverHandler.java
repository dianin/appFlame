package Utils;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

import static Utils.GetProperty.getConfigProperty;

public class DriverHandler {

    public enum Device {

        EMULATOR("nexus"),
        REAL("MiPhone");
        private String name;

        Device(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public static AndroidDriver<AndroidElement> driver;

    public static AndroidDriver<AndroidElement> createDriver(Device device){
        driver = new AndroidDriver<>(getURL(device), getCapabilities(device));
        return driver;
    }

    private static DesiredCapabilities getCapabilities (Device device)
    {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.UDID,                   getConfigProperty(device+"Udid"));
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME,			device);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME,			getConfigProperty("platformName"));
        capabilities.setCapability(MobileCapabilityType.NO_RESET,				getConfigProperty("noReset"));
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME,		"UiAutomator2");
        capabilities.setCapability("appPackage",                  getConfigProperty("speedTestPackage"));
        capabilities.setCapability("appActivity",                 getConfigProperty("speedTestActivity"));
        capabilities.setCapability(AndroidMobileCapabilityType.SYSTEM_PORT,     getConfigProperty("port"));
        return capabilities;
    }

    private static URL getURL(Device device)
    {
        try
        {
            return new URL(getConfigProperty("url"));
        }
        catch (MalformedURLException e)
        {
            return null;
        }
    }

    public static AndroidDriver<AndroidElement> getDriver() {
        return driver;
    }
}
