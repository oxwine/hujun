package com.gt.common;



import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 读取properties文件
 * 
 * @author chenpan
 * 
 */
public class PropertiesUtil {

	public static Map<String, String> propMap = new HashMap<String, String>();

	static {
		try {
			InputStream in = PropertiesUtil.class
					.getResourceAsStream("/verreportdb.properties");

			Properties prop = new Properties();
			prop.load(in);

//			propMap.put("oracle_driver", prop.getProperty("oracle_driver"));
//			propMap.put("oracle_url", prop.getProperty("oracle_url"));
//			propMap.put("oracle_username", prop.getProperty("oracle_username"));
//			propMap.put("oracle_password", prop.getProperty("oracle_password"));

			propMap.put("base_url", prop.getProperty("base_url"));
			propMap.put("login_name", prop.getProperty("login_name"));
			propMap.put("login_pwds", prop.getProperty("login_pwds"));
			propMap.put("project_id", prop.getProperty("project_id"));
			propMap.put("eparchy_code", prop.getProperty("eparchy_code"));
			propMap.put("element_maxtime", prop.getProperty("element_maxtime"));
			
			propMap.put("url", prop.getProperty("url"));
			propMap.put("devKey", prop.getProperty("devKey"));
			propMap.put("projectName", prop.getProperty("projectName"));
			propMap.put("testPlan", prop.getProperty("testPlan"));
			propMap.put("platform", prop.getProperty("platform"));
			propMap.put("filePath", prop.getProperty("filePath"));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
