package Tests;

import Pages.CommonMethods;
import Pages.MainPage;
import Utils.Listener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static Utils.DriverHandler.*;
import static Utils.DriverHandler.Device.MIPHONE;

@Listeners(Listener.class)
public class SpeedCheck {

    MainPage mainPage;
    CommonMethods commonMethods;

    public void setUp (){
        commonMethods = new CommonMethods(createDriver(MIPHONE));
        commonMethods.installIfNeed(MIPHONE);
        mainPage = new MainPage(getDriver());
    }

    @Test
    public void verifySpeed()
    {
        setUp();
        mainPage.skipOnboarding();
        mainPage.startTesting();
        mainPage.getSpeedValuesAndPrint();
    }
}
