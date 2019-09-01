package tests.automation.com.myautomationtestapplication;

import org.junit.Assert;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.remote.MobileCapabilityType;

/**
 * Created by BappY on 7/10/2019.
 */

public class ExampleTest {

    static AppiumDriver<MobileElement> driver;


    public void PerfromTest() {

        MobileElement el1 = (MobileElement) driver.findElementById("com.ststudio.smart.calculator:id/kb_8");
        el1.click();
        MobileElement el2 = (MobileElement) driver.findElementById("com.ststudio.smart.calculator:id/kb_multiply");
        el2.click();
        MobileElement el3 = (MobileElement) driver.findElementById("com.ststudio.smart.calculator:id/kb_2");
        el3.click();
        MobileElement el4 = (MobileElement) driver.findElementById("com.ststudio.smart.calculator:id/kb_equal");
        el4.click();

        Assert.assertEquals(driver.findElementById("com.ststudio.smart.calculator:id/text").getText(), "16");
        System.out.print("Assertion Done");

    }

    public static void Main(String args[]) throws MalformedURLException {

        ExampleTest created_Object = new ExampleTest();

        DesiredCapabilities dc = new DesiredCapabilities();

        dc.setCapability(MobileCapabilityType.DEVICE_NAME, Platform.valueOf("emulator-5554"));
        dc.setCapability("platformName","Android");

        //dc.setCapability();
        //dc.setCapability();

        dc.setCapability("appPackage", "com.ststudio.smart.calculator");
        dc.setCapability("appActivity", "com.st.calculator.main.MainActivity");

        driver = new AppiumDriver<MobileElement>(new URL("http://127.0.0.1/4723/wd/hub"), dc);

        created_Object.PerfromTest();

//        MobileElement el1 = (MobileElement) driver.findElementById("com.ststudio.smart.calculator:id/kb_8");
//        el1.click();
//        MobileElement el2 = (MobileElement) driver.findElementById("com.ststudio.smart.calculator:id/kb_multiply");
//        el2.click();
//        MobileElement el3 = (MobileElement) driver.findElementById("com.ststudio.smart.calculator:id/kb_2");
//        el3.click();
//        MobileElement el4 = (MobileElement) driver.findElementById("com.ststudio.smart.calculator:id/kb_equal");
//        el4.click();
//
//        Assert.assertEquals(driver.findElementById("com.ststudio.smart.calculator:id/text").getText(), "16");
//        System.out.print("Assertion Done");

    }


}
