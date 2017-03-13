package com.gt.common;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ServerInfo {

	public static void main(String[] args) {
		try {
			OperateDriver.login();
			
			OperateDriver.clickMenu("业务管理", "执行机状态");
			
			OperateDriver.switchToFrameBySRC("slave/status");
			
			Table tal = new Table("//table[@class='table table-bordered table-hover definewidth m10']");
			int num = tal.getRowNum();
			System.out.println("当前服务数量："+num);
			Map<String,String> serverInfo = new HashMap<String,String>();
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
				}
			}
			
			Set<String> ipInfo = serverInfo.keySet();
			Iterator<String> ipIterator = ipInfo.iterator();
			while(ipIterator.hasNext()){
				//serverInfo.get(ipIterator.next());
				String ip = ipIterator.next();
				System.err.println("服务器地址为："+ip+"。状态为："+serverInfo.get(ip));
			}
			OperateDriver.switchToDefaultContent();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
