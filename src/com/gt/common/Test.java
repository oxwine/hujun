package com.gt.common;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.openqa.selenium.WebElement;

public class Test {

	public static void main(String[] args) {
	
		try {
			//登陆
			OperateDriver.login();
			//点击菜单
			OperateDriver.clickMenu("业务管理", "任务分配");
			//进入frame
			OperateDriver.switchToFrameBySRC("task/list");
			//获取table内容
			Table tal = new Table("//table[@id='tblist']");
			
			//WebElement td = tal.getCellElement("execute002","管理操作");
			
			//从excel获取需要运行的任务名称存入data
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
			
			//WebElement a = tal.getStartEle(data.get(1));
			ListIterator<String> da = data.listIterator();
			Map<String,List<WebElement>> ipEles= new HashMap<String,List<WebElement>>();
			while(da.hasNext()){
				String taskName = da.next();
				WebElement a = tal.getStartEle(taskName);
				WebElement ipEle = tal.getCellElement(taskName, "SlaveIP");
				System.err.println(ipEle.getText());
				a.click();
				OperateDriver.sleep(2.0);
				OperateDriver.clickAlert();
			}	

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
				
	}

}
