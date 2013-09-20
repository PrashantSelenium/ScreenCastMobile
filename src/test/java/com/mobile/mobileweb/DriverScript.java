package com.mobile.mobileweb;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

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

	@BeforeTest
	public void setUp() throws IOException {
		
		
		CONFIG = new Properties();
		FileInputStream fs = new FileInputStream(System.getProperty("user.dir")
				+ "//src//test//java//config//browser.properties");
		CONFIG.load(fs);

		br = new BufferedReader(new FileReader(System.getProperty("user.dir")
				+ "//src//test//java//config//test.csv"));

	}

	@Test
	public void testCaptureScreen() throws WebDriverException, IOException,
			DocumentException, InterruptedException {

		if (CONFIG.getProperty("testBrowser").equals("android")) {
			
		//	TestUtil.executeServer();
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

		while ((line = br.readLine()) != null) {
			String[] urls = line.split(",");
			for (i = 0; i < urls.length; i++) {
				System.out.println(urls[i]);

				driver.get(urls[i]);
			}

		}
		TestUtil.screenScrollByWindowSizeAndCaptureScreenShot();

	}

}
