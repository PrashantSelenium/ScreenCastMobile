package com.merck.mobileweb;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;
import org.uiautomation.ios.IOSCapabilities;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;

public class DriverScript {
	public static WebDriver driver;
	public static RemoteWebDriver remotedriver;
	public static String screenname = "screen";
	public String line = null;
	public int screenCount = 0;
	public BufferedReader br = null;
	public static Properties CONFIG;
	public static Document document;
	public int i;

	@Test
	public void testCaptureScreen() throws WebDriverException, IOException,
			DocumentException, InterruptedException {

		CONFIG = new Properties();
		FileInputStream fs = new FileInputStream(System.getProperty("user.dir")
				+ "//src//test//java//config//browser.properties");
		CONFIG.load(fs);

		if (CONFIG.getProperty("testBrowser").equals("android")) {
			driver = new AndroidDriver();

			System.out.println("android Is launched");

		}

		else if (CONFIG.getProperty("testBrowser").equals("firefox")) {

			System.out.println("launch Firefox Test Browser");

		} else {

			driver = new RemoteWebDriver(new URL(
					"http://172.16.37.181:3001/wd/hub/"),
					DesiredCapabilities.iphone());
			System.out.println("launch IPhone Test Browser");
		}

		br = new BufferedReader(new FileReader(System.getProperty("user.dir")
				+ "//src//test//java//config//test.csv"));

		while ((line = br.readLine()) != null) {
			String[] urls = line.split(",");
			for (i = 0; i < urls.length; i++) {
				System.out.println(urls[i]);

				driver.get(urls[i]);
			}

		}

		JavascriptExecutor jsx = (JavascriptExecutor) driver;

		Long y2 = (Long) jsx.executeScript("return window.innerHeight");

		System.out.println(y2);

		Long y = (Long) jsx
				.executeScript("return document.documentElement.scrollHeight");

		long screenScrollSize = y / y2;

		System.out.println("Number of screens to scroll by :::"
				+ screenScrollSize);

		System.out.println(y);

		for (int i = 0; i <= screenScrollSize; i++) {

			((JavascriptExecutor) driver).executeScript("window.scrollBy(0,"
					+ y2 + ")");
			//TestUtil.captureScreenShot(i);
		}

	}

}
