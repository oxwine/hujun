package com.gt.common;



import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;



/**
 * @author hujun
 */
public class OperateDriver {

	private static WebDriver webdriver;
	
	public static WebDriver getWedDriver(){
		return webdriver;
	}
	public static void login() throws Exception {

		try {
			// 获取浏览器
			LogUtil.logInfo("启动浏览器");

			webdriver = DriverUtils.getWebDriver();
			
			LogUtil.logInfo("获取首页");

			String baseurl = PropertiesUtil.propMap.get("base_url");
			webdriver.get(baseurl);

			String parentWin = webdriver.getWindowHandle();
			LogUtil.logInfo("当前浏览器为" + parentWin);

			// 输入登陆框id
			OperateDriver.isExistElement("//input[@name='username']");
			WebElement name = webdriver.findElement(By.xpath("//input[@name='username']"));
			name.clear();
			// 输入用户名
			name.sendKeys(PropertiesUtil.propMap.get("login_name"));

			
			// 输入密码框id
			OperateDriver.isExistElement("//input[@name='password']");
			WebElement pwds = webdriver.findElement(By.xpath("//input[@name='password']"));
			pwds.clear();
			// 输入密码
			pwds.sendKeys(PropertiesUtil.propMap.get("login_pwds"));

			// 输入登陆按钮XPATH
			WebElement lgbtn = webdriver.findElement(By.xpath("//button[@type='submit']"));
			// 点击登陆按钮
			((JavascriptExecutor) webdriver).executeScript("arguments[0].click();", lgbtn);

			// 页面等待
			OperateDriver.sleep(3);
			LogUtil.logInfo("登陆成功");
			
		} catch (Exception e) {
			throw new Exception("登陆异常");
		}
	}

	public static void sleep(double number) {
		try {
			String numberstr = number * 1000d + "";
			numberstr = numberstr.split("\\.")[0];
			Thread.sleep(Long.valueOf(numberstr));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void clickMenu(String menuName, String treeName) throws Exception {

		try {
			// 检查元素是否纯在
			String menuXpath = "//div[contains(text(),'"+menuName+"')]";
			System.out.println(menuXpath);
			OperateDriver.isExistElement(menuXpath);

			// 查找菜单元素
			WebElement menuEle = webdriver.findElement(By.xpath(menuXpath));

			// js执行菜单点击
			LogUtil.logInfo("点击菜单栏");
			((JavascriptExecutor) webdriver).executeScript("arguments[0].click();", menuEle);
		} catch (Exception e) {

			LogUtil.logInfo("点击menuEle{" + menuName + "}失败");
			throw new Exception("点击menuEle" + menuName + "出现异常");
		}

		try {
			// 树形xpath
			//a[em[contains(text(),'任务分配')]]
			String treeXpath = "//a[em[contains(text(),'"+treeName+"')]]";
			System.out.println(treeXpath);

			// 检查元素是否纯在
			OperateDriver.isExistElement(treeXpath);

			// 查找树形元素
			WebElement treeEle = webdriver.findElement(By.xpath(treeXpath));

			// js执行树形目录点击
			LogUtil.logInfo("点击树目录");
			((JavascriptExecutor) webdriver).executeScript("arguments[0].click();", treeEle);
			treeEle.click();
		} catch (Exception e) {
			LogUtil.logInfo("点击treeEle{" + menuName + "}失败");
			throw new Exception("点击treeEle" + menuName + "出现异常");
		}
	}

	// 隐式等待元素是否存在
	public static Boolean isExistElement(final String elePath) {

		// 获取等待时间时间
		long second = Long.valueOf(PropertiesUtil.propMap.get("element_maxtime"));

		WebDriverWait driverWait = new WebDriverWait(webdriver, second);
		// 在获取时间内，元素是否存在，返回flag
		Boolean flag = driverWait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				WebElement element = d.findElement(By.xpath(elePath));
				return element.isDisplayed();
			}
		});

		return flag;
	}

	//关闭所有弹出窗口
	@SuppressWarnings("unused")
	private static void closeAllAlert() {
		try {
			Alert alert = webdriver.switchTo().alert();
			while (alert != null) {
				alert.dismiss();
				alert = webdriver.switchTo().alert();
			}

		} catch (Exception e) {

		}
	}
	
	//切换frame 参数frameSRC为frame的src值
	public static void switchToFrameBySRC(String frameSRC) throws Exception {
		try {
			// 是否存在
			String frameXpath = "//iframe[@src='" + frameSRC + "']";
			System.out.println(frameXpath);
			OperateDriver.isExistElement(frameXpath);
			WebElement frame = OperateDriver.webdriver.findElement(By.xpath(frameXpath));
			OperateDriver.switchToDefaultContent();
			webdriver.switchTo().frame(frame);
			//webdriver.switchTo().frame(null);
		} catch (Exception e) {
			throw new Exception("切换" + frameSRC + "失败");
		}
	}
	
	//切换到默认的frame
	public static void switchToDefaultContent() throws Exception{
		
		try {
			webdriver.switchTo().defaultContent();
		} catch (Exception e) {
			throw new Exception("切换defaultContent 失败");
	}
	}

	public static void clickAlert(){
		List<WebElement> ele = 
				OperateDriver.webdriver.findElements(By.xpath("//table[@id='tblist']/preceding-sibling::div"));
//		if ( ele.size()==1 ) {
		//System.out.println(ele.get(0).getAttribute("style"));
		System.out.println(ele.get(0).getText());
		//点击确定按钮
		ele.get(0).findElement(By.tagName("button")).click();
		//System.out.println(ele.get(0).getText());
//		}
	}
	
}
