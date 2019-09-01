package tests.automation.com.myautomationtestapplication;

import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

import static tests.automation.com.myautomationtestapplication.ExampleTest.driver;

/**
 * Created by BappY on 7/10/2019.
 */

public class AppiumExample {

    AppiumDriver<MobileElement> appium_Driver;

    @BeforeTest
    public void settingEnvironment() throws MalformedURLException {
        // Created object of DesiredCapabilities class.
        DesiredCapabilities capabilities = new DesiredCapabilities();

        // Set android deviceName desired capability. Set your device name.
        capabilities.setCapability("deviceName", "emulator-5554");

        // Set BROWSER_NAME desired capability. It's Android in our case here.
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "Android");

        // Set android VERSION desired capability. Set your mobile device's OS version.
        //capabilities.setCapability(CapabilityType.VERSION, "7.1.1");

        // Set android platformName desired capability. It's Android in our case here.
        capabilities.setCapability("platformName", "Android");

        // Set android appPackage desired capability. It is
        // com.android.calculator2 for calculator application.
        // Set your application's appPackage if you are using any other app.
        capabilities.setCapability("appPackage", "com.ststudio.smart.calculator");

        // Set android appActivity desired capability. It is
        // com.android.calculator2.Calculator for calculator application.
        // Set your application's appPackage if you are using any other app.
        capabilities.setCapability("appActivity", "com.st.calculator.main.MainActivity");

        // Created object of RemoteWebDriver will all set capabilities.
        // Set appium server address and port number in URL string.
        // It will launch calculator app in android device.
        appium_Driver = new AppiumDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
    }

    @Test
    public void CommencingTest(){

        MobileElement el1 = (MobileElement) driver.findElementById("com.ststudio.smart.calculator:id/kb_8");
        el1.click();
        MobileElement el2 = (MobileElement) driver.findElementById("com.ststudio.smart.calculator:id/kb_multiply");
        el2.click();
        MobileElement el3 = (MobileElement) driver.findElementById("com.ststudio.smart.calculator:id/kb_2");
        el3.click();
        MobileElement el4 = (MobileElement) driver.findElementById("com.ststudio.smart.calculator:id/kb_equal");
        el4.click();

        //Assert.assertEquals(driver.findElementById("com.ststudio.smart.calculator:id/text").getText(), "16");
        Assert.assertEquals(appium_Driver.findElementById("com.ststudio.smart.calculator:id/text").getText(), "16");
        System.out.print("Assertion Done");
    }

    @AfterTest
    public void tearDown(){
        appium_Driver.quit();
        System.out.print("Test Completed");
    }


}
