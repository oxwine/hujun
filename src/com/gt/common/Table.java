package com.gt.common;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * 
 * @author hujun 封装table
 */
public class Table {
	static WebElement talEle;
	static int col;
	private static String talXpath = "//table[@id='tblist']";
	
	public Table() {
		this("//table[@id='tblist']");
	}

	public Table(String tabXpath) {
		talXpath =  tabXpath;
		OperateDriver.isExistElement(talXpath);

		talEle = OperateDriver.getWedDriver().findElement(By.xpath(talXpath));
		
	}

	//获取输入列名的列号，参数colName为输入的列名
	private int colNum(String colName){
		List<WebElement> thsInThead = talEle.findElements(By.xpath("//thead//th"));
		
		int i = 1;
		for (WebElement th:thsInThead){
			if(th.getText().contains(colName)){
				return i;
			} else {
				i++;
			}
		}
		//talEle.findElement(By.xpath("//thead//th[contains(text(),'任务名称')]"));	
		return 0;
	}
	
    //返回任务名称行号	
	public  int getTask(String taskName) {
		List<WebElement> trsInTbody = talEle.findElements(By.xpath(talXpath+"/tbody/tr"));
		int i = 1;
		for (WebElement tr:trsInTbody){
			List<WebElement> tdsInTr = tr.findElements(By.tagName("td"));
			WebElement td = tdsInTr.get(1);
			//System.out.println(td.getText());
			if(td.getText().contains(taskName)){
				return i;
			}else {
				i++;
			}	
		}
		return 0;
	}
	
	//获取特定行
	private  WebElement getTrByRownum(int rownum){
		List<WebElement> trsInTbody = talEle.findElements(By.xpath(talXpath+"/tbody/tr"));
		WebElement tr = trsInTbody.get(rownum);
		//System.out.println(tr.findElements(By.tagName("td")).get(1).getText());
		return tr;
	}
	
	public Integer getRowNum(){
		List<WebElement> trsInTbody = talEle.findElements(By.xpath(talXpath+"/tbody/tr"));
		return trsInTbody.size();
		
	}
	
	//根据行号，，列号 获取特定列td
	public  WebElement getCellElement(int row,int col){
		WebElement tr = getTrByRownum(row-1);
		List<WebElement> cellElement = tr.findElements(By.tagName("td"));
		WebElement td = cellElement.get(col-1);
		return td;
	}
	
	//根据任务名称，列名称，获取该td元素
    public  WebElement getCellElement(String taskName,String colName){
		int col = colNum(colName);
		int row = getTask(taskName);
		WebElement  tdEle = null;
		if(row != 0){
		System.out.println("可用任务名称为："+taskName+" 行号为："+row+"  列号为："+col);
		tdEle = getCellElement(row,col);
		} else {
			new Exception("该任务不存在："+taskName);
		}
		return tdEle;
	}
    
    //获取启动按钮
    public WebElement getStartEle(String taskName){
       
    	WebElement td = getCellElement(taskName,"管理操作");
    	System.out.println(td.getText());
		
		List<WebElement> a =  td.findElements(By.tagName("a"));
		System.out.println("点击启动");
		return a.get(2);
    	
    }
    
    
}
