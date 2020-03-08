package Pages;

import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.By;

import static Pages.CommonMethods.Permission.CALLS;
import static Pages.CommonMethods.Permission.LOCATION;

public class MainPage extends CommonMethods {

    public MainPage(AndroidDriver<AndroidElement> driver) {
        super(driver);
    }

    private By nextButton           = By.id("org.zwanoo.android.speedtest:id/welcome_message_next_button");
    private By continueButton       = By.id("org.zwanoo.android.speedtest:id/permissions_continue_button");
    private By startButton          = MobileBy.AccessibilityId("Start a Speedtest");
    private By downloadValue        = By.xpath("//*[@content-desc='DOWNLOAD']/*/*[@resource-id='org.zwanoo.android.speedtest:id/txt_test_result_value']");
    private By uploadValue          = By.xpath("//*[@content-desc='UPLOAD']/*/*[@resource-id='org.zwanoo.android.speedtest:id/txt_test_result_value']");
    private By pingValue            = By.xpath("//*[@content-desc='Ping']/*/*[@resource-id='org.zwanoo.android.speedtest:id/txt_test_result_value']");
    private By jitterValue          = By.xpath("//*[@content-desc='Jitter']/*/*[@resource-id='org.zwanoo.android.speedtest:id/txt_test_result_value']");
    private By lossValue            = By.xpath("//*[@resource-id='org.zwanoo.android.speedtest:id/test_result_item_packet_loss']/*/*[@resource-id='org.zwanoo.android.speedtest:id/txt_test_result_value']");
    private By testAgainButton      = By.id("org.zwanoo.android.speedtest:id/suite_completed_feedback_assembly_test_again");

    public void skipOnboarding()
    {
        openSpeedTest();
        waitForLoad(nextButton);
        waitThenClick(nextButton);
        waitThenClick(continueButton);
        allowPermission(LOCATION);
        allowPermission(CALLS);
    }

    public void startTesting(){
        waitThenClick(startButton);
    }

    public void getSpeedValuesAndPrint() {
        waitForLoad(testAgainButton);
        System.out.println("Download value = "   + getText(downloadValue));
        System.out.println("Upload value = "     + getText(uploadValue));
        System.out.println("Ping = "             + getText(pingValue));
        System.out.println("Jitter = "           + getText(jitterValue));
        System.out.println("Lose"                + getText(lossValue));

    }
}
