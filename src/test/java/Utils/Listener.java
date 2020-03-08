package Utils;

import io.appium.java_client.android.AndroidDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.testng.IRetryAnalyzer;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import static Utils.DriverHandler.getDriver;

public class Listener implements ITestListener, IRetryAnalyzer {

    private int retryCount;
    private int maxRetryCount = 1;
    private static Path pathToLogs;

    public boolean retry(ITestResult iTestResult) {
            if (retryCount < maxRetryCount)
            {
                iTestResult.getThrowable().printStackTrace();
                System.out.println(
                        "Rerun method " + iTestResult.getMethod().getTestClass().getRealClass().getSimpleName()
                                + "." + iTestResult.getMethod().getMethodName());
                retryCount++;
                return true;
            }
            return false;
    }

    public void onTestStart(ITestResult result) {
        System.out.println(saveMethodName(result));

    }

    public void onTestSuccess(ITestResult result) {
        System.out.println(saveMethodName(result) + "Passed");
    }

    public void onTestFailure(ITestResult result) {
        createScreenshot(getDriver());
        saveMethodName(result);
        createFolders(result);
    }

    public void onTestSkipped(ITestResult result) {
        createScreenshot(getDriver());
        saveMethodName(result);
        createFolders(result);

    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    public void onTestFailedWithTimeout(ITestResult result) {

    }

    public void onStart(ITestContext context) {

    }

    public void onFinish(ITestContext context) {

    }

    private void createFolders(ITestResult testResult)
    {
        try
        {
            Path directory = Paths.get("failures\\"
                    + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "\\"
                    + testResult.getTestClass().getRealClass().getSimpleName()
                    + "." + testResult.getMethod().getMethodName());
            Files.createDirectories(directory);

            int x = 0;
            while(Files.exists(directory.resolve("Run " + ++x))){}

            pathToLogs = directory.resolve("Run " + x);
            Files.createDirectory(pathToLogs);

        }
        catch (Throwable e) {e.printStackTrace();}
    }


    private static void createScreenshot(AndroidDriver driver)
    {
        try
        {
            File file = driver.getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(file, pathToLogs.resolve("\\Screenshot.jpg").toFile());
        }
        catch (Throwable e) {e.printStackTrace();}
    }



    private static String saveMethodName(ITestResult iTestResult)
    {
      String lastExecutedTestName;
      return lastExecutedTestName = iTestResult.getMethod().getTestClass().getRealClass().getSimpleName()
                + "."
                + iTestResult.getMethod().getMethodName();
    }
}
