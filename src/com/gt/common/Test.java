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

import org.openqa.selenium.WebElement;

public class Test {

	public static void main(String[] args) {
	
		try {
			//登陆
			OperateDriver.login();
			
			List<String>  serverIPs  = OperateDriver.getIdleServer();
			//点击菜单
			OperateDriver.clickMenu("业务管理", "任务分配");
			//进入frame
			OperateDriver.switchToFrameBySRC("task/list");
			//获取table内容
			Table tal = new Table("//table[@id='tblist']");
			
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
				//获取该任务的IP
				if(tal.getTask(taskName)==0) {
					System.out.println("查找不到或者当前页查找不到的用例名称为:"+taskName);
					continue;
				}
				String ip = tal.getCellElement(taskName, "SlaveIP").getText();
				//存在于可用服务器的IP任务保留
				if(serverIPs.contains(ip)) {
					
				mapNameAndIp.put(taskName, ip);
				
				} else {
					
					System.out.println("不能运行的用例名称为:"+taskName+"  IP地址为："+ip);
					
				}
//				WebElement a = tal.getStartEle(taskName);
//				WebElement ipEle = tal.getCellElement(taskName, "SlaveIP");
				//System.err.println(ipEle.getText());
				//a.click();
//				OperateDriver.sleep(2.0);
//				OperateDriver.clickAlert();
			}
			
			//运行服务
			Boolean flag = true;
			while(flag){
				
				//可用IP为空时直接等待下一次循环
				if(serverIPs.isEmpty()){
					
					OperateDriver.sleep(2.0);
					
					serverIPs  = OperateDriver.getIdleServer();
					
					continue;
				
				//name和IP映射为空说明任务执行完毕，跳出结束
				} else if(mapNameAndIp.isEmpty()){
					
					System.out.println("运行结束");
					
					flag = false;
					
					break;
					
				} else {
					
					//循环IP判断
					Iterator<String> ip = serverIPs.iterator();
					while(ip.hasNext()){
						
						String ipAdress = ip.next();
						
						Set<String> nameKeys = mapNameAndIp.keySet();
						
						Iterator<String> keys = nameKeys.iterator();
						while(keys.hasNext()){
							
							String key = keys.next();
							
							if(mapNameAndIp.get(key).equals(ipAdress)){
								
								WebElement a = tal.getStartEle(key);
								
								a.click();
								
								OperateDriver.sleep(2.0);
								
						        OperateDriver.clickAlert();
						        
								mapNameAndIp.remove(key);
								
								serverIPs = OperateDriver.getIdleServer();
								
								OperateDriver.switchToDefaultContent();
								
								OperateDriver.clickMenu("业务管理", "任务分配");
								
								OperateDriver.switchToFrameBySRC("task/list");
							}
						}
					}	
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		OperateDriver.close();
				
	}

}
