package com.gt.common;


import java.io.File;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * WebDriver设置
 * 
 * @author hujun 2017-02-09
 * 
 */
public class DriverUtils {

	private static WebDriver driver;

	private DriverUtils() {

	}

	/**
	 * 获取webdrvier
	 * 
	 * @return WebDriver
	 * @author hujun 2017-02-09
	 * 
	 */
	public static WebDriver getWebDriver() {

		LogUtil.logInfo("开始启动");

		try {

			// 设置IE驱动
			File file = new File("res/IEDriverServer.exe");
			System.setProperty("webdriver.ie.driver", file.getAbsolutePath());

			DesiredCapabilities cap = DesiredCapabilities.internetExplorer();
			cap.setCapability(
					InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
					true);
			if (driver==null)
			driver = new InternetExplorerDriver(cap);
		
		} catch (Exception e) {

			driver = new InternetExplorerDriver();
			
		}

		
		driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
		//最大化浏览器
		driver.manage().window().maximize();

		return driver;
	}
}
