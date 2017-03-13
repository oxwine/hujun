package com.gt.common;



import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.SystemUtils;
import org.jboss.netty.util.internal.SystemPropertyUtil;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.os.WindowsUtils;
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

	//睡眠延迟
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

	//选择需要的菜单
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

	//关闭弹出层
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
	
	//运行结束，关闭浏览器，和当前进程
	 public static void close(){
		 webdriver.close();
		 WindowsUtils.tryToKillByName("IEDriverServer.exe");
    }
	
	 
	 //获取服务器当前状态信息，并返回
	public static Map<String,String> getServerInfo(){
		
		Map<String,String> serverInfo = null;
		
		try {
			OperateDriver.switchToDefaultContent();
			
			OperateDriver.clickMenu("业务管理", "执行机状态");
			
			OperateDriver.switchToFrameBySRC("slave/status");
			
			//每次new一个对象问题
			Table tal = new Table("//table[@class='table table-bordered table-hover definewidth m10']");
			
			int num = tal.getRowNum();
			System.out.println("当前服务数量："+num);
			
			//存储服务和状态
			serverInfo = new HashMap<String,String>();
			/**
			 * 0:服务器异常；1：服务器空闲； 2：服务器正在执行
			 */
			for(int i = 1; i<=num; i++){
				System.out.println(tal.getCellElement(i, 2).getText());
				String ip = tal.getCellElement(i, 2).getText();
				System.out.println(tal.getCellElement(i, 4).getText());
				String statuas = tal.getCellElement(i, 4).getText();
				if(statuas.contains("该分机当前正在执行测试任务")) {
					serverInfo.put(ip, "2");
				} else if (statuas.contains("当前执行机处于空闲状态")){
					serverInfo.put(ip, "1");
				} else {
					serverInfo.put(ip, "0");
				}
			}
			
			Set<String> ipInfo = serverInfo.keySet();
			Iterator<String> ipIterator = ipInfo.iterator();
			while(ipIterator.hasNext()){
				//serverInfo.get(ipIterator.next());
				String ip = ipIterator.next();
				System.err.println("当前的服务器地址为："+ip+"。状态为："+serverInfo.get(ip));
			}
			
			OperateDriver.switchToDefaultContent();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return serverInfo;	
	}
	
	public static List<String> getIdleServer(){
		//存储可用服务器IP地址
		List<String>  serverIPs = new ArrayList<String>();
		//获取所有服务器信息和状态
		Map<String,String> serverInfo = OperateDriver.getServerInfo();
		
		//获取键的集合
		Set<String> ipInfo = serverInfo.keySet();
		Iterator<String> ipIterator = ipInfo.iterator();
		while(ipIterator.hasNext()){
			//serverInfo.get(ipIterator.next());
			String ip = ipIterator.next();
			if(serverInfo.get(ip).indexOf("1") !=-1){
				serverIPs.add(ip);
				System.out.println("可用服务器为："+ip);
			}
		}
		return serverIPs;
		
	}
	
	//获取EXCEL中的任务名称，并返回
	public static List<String> getTaskName() throws FileNotFoundException, IOException{
		List<String> data = new ArrayList<String>();
		//读取excel数据
		String[][] excelDate = ExcelData.getData();
		
		for (int i=0 ;i<excelDate.length; i++) {
			//获取状态为1的任务名称
			if ( Integer.parseInt(excelDate[i][1])==1) {
				 data.add(excelDate[i][0]);
				 System.out.println(excelDate[i][0]);
			}
		}
		return data;
	}
	
	//查询，并且获取可以运行的任务
	public static void getMapNameAndIP(Table tal,List<String>  serverIPs) throws FileNotFoundException, IOException {

		//		 tal = new Table("//table[@id='tblist']");
        //		  serverIPs  = OperateDriver.getIdleServer();
		//准备存储从Excel读取的任务名称
		
		List<String> data = new ArrayList<String>();
		
		//读取excel数据，存入data
		data = OperateDriver.getTaskName();
		
		//获取data迭代器
		ListIterator<String> da = data.listIterator();
		
		//准备存储taskName和IP地址 对应关系
		Map<String,String> mapNameAndIp= new HashMap<String,String>();
		while(da.hasNext()){
			
			String taskName = da.next();
			//获取该任务的IP.taskName不存在的时候需要补充异常
			if(tal.getTask(taskName)==0) {
				System.out.println("查找不到或者当前页查找不到的用例名称为:"+taskName);
				continue;
			}
			String ip = tal.getCellElement(taskName, "SlaveIP").getText();
			//存在于可用服务器的IP任务保留
			if(serverIPs.contains(ip)) {
				
			mapNameAndIp.put(taskName, ip);
			
			} else {
				
				System.out.println("不能运行的用例名称为:"+taskName+"IP地址为："+ip);
				
			}
	}
	}

}
