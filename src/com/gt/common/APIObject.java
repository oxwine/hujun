package com.gt.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;

import br.eti.kinoshita.testlinkjavaapi.TestLinkAPI;
import br.eti.kinoshita.testlinkjavaapi.constants.ExecutionStatus;
import br.eti.kinoshita.testlinkjavaapi.constants.ExecutionType;
import br.eti.kinoshita.testlinkjavaapi.constants.TestImportance;
import br.eti.kinoshita.testlinkjavaapi.model.Attachment;
import br.eti.kinoshita.testlinkjavaapi.model.ReportTCResultResponse;
import br.eti.kinoshita.testlinkjavaapi.model.TestCase;
import br.eti.kinoshita.testlinkjavaapi.model.TestPlan;
import br.eti.kinoshita.testlinkjavaapi.model.TestSuite;
import br.eti.kinoshita.testlinkjavaapi.util.TestLinkAPIException;

public class APIObject {
	// private static Logger logger = Logger.getLogger(APIObject.class);
	private static TestLinkAPI api = null;
	private String planName;
	private String projectName;
	private String url;
	private String devKey;
	private TestCase[] tcs;
	private TestPlan tl;
	private String buildName;
	private String platformName;

	private Integer tcID = 0;
	private Integer planID = 0;
	public Integer firstLevelSuiteID = 0;
	private TestCase tcase;

	public APIObject(String url, String devKey, String projectName, String planName, String buildName,
			String platformName) {
		this.url = url;
		this.devKey = devKey;
		this.projectName = projectName;
		this.planName = planName;
		this.buildName = buildName;
		this.platformName = platformName;
	}

	/**
	 * 初始化API，TestLinkAPI api赋值
	 * 
	 * @return
	 */
	public APIObject getAPI() {
		if (null == api) {
			try {
				api = new TestLinkAPI(new URL(url), devKey);
			} catch (TestLinkAPIException te) {
				System.err.println(te.getMessage() + te.toString());
			} catch (MalformedURLException mue) {
				System.err.println(mue.getMessage() + mue.toString());
			}
		}
		return this;
	}

	/**
	 * 创建测试用例
	 * 
	 * @param testCaseName
	 */
	public void creatCasese(String testCaseName, int tsid) {
		// tl = api.getTestPlanByName(planName, projectName);
		//
		// int projectID = api.getTestProjectByName(projectName).getId();
		//
		// TestSuite[] ts = api.getTestSuitesForTestPlan(tl.getId());
		//
		// int tsID = ts[0].getId();

		int pjID = api.getTestProjectByName(projectName).getId();
		api.createTestCase(testCaseName, tsid, pjID, "test", "admin", null, null, null, null, null, null, true, null);

	}

	/**
	 * 
	 * @param suiteName
	 * @param parentID
	 */
	public void createTestSuite(String suiteName, int parentID) {
		// 获取project的 ID
		int projectID = api.getTestProjectByName(projectName).getId();

		api.createTestSuite(projectID, suiteName, "测试使用", parentID, null, true, null);
	}

	/**
	 * 根据suite name查出 FirstLevel suite id
	 * 
	 * @return
	 */
	public APIObject getFirstLevelSuiteIDByName(String suiteName) {

		int projectID = api.getTestProjectByName(projectName).getId();
		TestSuite ts[] = api.getFirstLevelTestSuitesForTestProject(projectID);

		for (TestSuite t : ts) {
			if (t.getName().equals(suiteName)) {
				System.out.println(t.getName() + ":" + t.getId() + "PID:" + t.getParentId());
				// t.setParentId(20);
				// System.out.println(t.getName()+":"+t.getId()+"PID:"
				// +t.getParentId());
				firstLevelSuiteID = t.getId();

			}
		}
		return this;

	}

	public int needID = 0;

	/**
	 * 查出给定suite ID的下面 descendant suite的ID， 传给needID
	 * 需要先运行getFirstLevelSuiteIDByName
	 * 
	 * @param id
	 *            suite ID
	 * @param name
	 *            suite name
	 * @return
	 */
	public Integer getChildSuiteID(int id, String name) {
		TestSuite[] tss = api.getTestSuitesForTestSuite(id);
		for (TestSuite ts : tss) {
			System.out.println(ts.getName() + ":" + ts.getId());
			if (ts.getName().equals(name)) {
				// System.out.println(ts.getName() + ":" + ts.getId());
				needID = ts.getId();
				break;
			}
			if (getChildSuiteID(ts.getId(), name) != 0) {
				getChildSuiteID(ts.getId(), name);
			}
		}
		return 0;
	}

	/**
	 * 获取测试用例 集合,需要先运行getAPI()
	 */
	public APIObject getTestCases() {
		this.planName = planName;
		this.projectName = projectName;
		tl = api.getTestPlanByName(planName, projectName);
		tcs = api.getTestCasesForTestPlan(tl.getId(), null, null, null, null, null, null, null, ExecutionType.MANUAL,
				null, null);

		for (TestCase tc : tcs) {
			System.out.println(tc.toString());
		}
		return this;
	}

	/**
	 * 执行某个测试用例，需要先运行getTestCases()
	 * 
	 * @param testcasename
	 * @param status
	 * @param comments
	 */
	public void executeTestCase(String testcasename, int status, String comments) {
		Integer planID = tl.getId();

		for (TestCase tc : tcs) {
			String casename = tc.getName();
			if (casename.equals(testcasename)) {
				tcID = tc.getId();
				tcase = tc;
				break;
			}

		}

		switch (status) {
		case 1:
			api.reportTCResult(tcID, null, planID, ExecutionStatus.PASSED, null, buildName, comments, null, null, null,
					platformName, null, null);
			break;
		case 2:
			api.reportTCResult(tcID, null, planID, ExecutionStatus.FAILED, null, buildName, comments, null, null, null,
					platformName, null, null);
			break;
		case 3:
			api.reportTCResult(tcID, null, planID, ExecutionStatus.BLOCKED, null, buildName, comments, null, null, null,
					platformName, null, null);
			break;
		default:
			System.out.println("please check your status");
		}

	}

	/**
	 * 上传用例附件
	 * 
	 * @param picturePath
	 */
	public void uploadAttchment(String picturePath) {
		File attachmentFile = new File(picturePath);
		String fileContent = null;

		try {
			byte[] byteArray = FileUtils.readFileToByteArray(attachmentFile);
			fileContent = new String(Base64.encodeBase64(byteArray));
		} catch (IOException e) {
			e.printStackTrace(System.err);
			System.exit(-1);
		}

		Attachment attachment = api.uploadExecutionAttachment(4, // executionid
				"ScreenshotFor this Test case-", // title
				"In this screen the attendant is defining the customer plan", // description
				"Autor:terry1", // fileName
				"image/jpg", // fileType
				fileContent); // content

		System.out.println("Attachment uploaded");
	}

	/**
	 * 导入测试用例
	 * 
	 * @throws IOException
	 */
	public void importTestCases() throws IOException {

		InputStream ins = new FileInputStream(new File("C:/Users/Administrator/Desktop/ceshi.xlsx"));
		String[][] data = ExcelData.getData(ins, 2);

		int pjID = api.getTestProjectByName(projectName).getId();

		this.getFirstLevelSuiteIDByName("电子名片");

		int rowLength = data.length;
		for (int i = 0; i < rowLength; i++) {
			if (data[i][2].isEmpty()) {
				String name = data[i][1];
				this.getChildSuiteID(this.firstLevelSuiteID, name);
				continue;
			} else {
				System.out.println(data[i][2]);
				api.createTestCase(data[i][2], this.needID, pjID, "admin", data[i][5], null,
						data[i][6] + "<br>" + data[i][7], TestImportance.HIGH, null, null, null, true, null);
			}
		}

	}

	public static void main(String args[]) throws IOException {
		// the following settings are required;
		String url = "http://localhost/testlink/lib/api/xmlrpc/v1/xmlrpc.php";
		String devKey = "1fd93356574d44866648dd3a3010f4ef";
		String projectName = "Auto";
		String tp = "autoCases";
		String buildName = "weeeewe";
		String platform = "win7";

		APIObject testlinkapi = new APIObject(url, devKey, projectName, tp, buildName, platform);
		// testlinkapi.getAPI().getTestCases().executeTestCase("test", 2,
		// "tested by hujun");
		testlinkapi.getAPI();

		// System.out.println(testlinkapi.needID);
		// 读取EXCEL内容
		// InputStream ins = new FileInputStream(new
		// File("C:/Users/Administrator/Desktop/ceshi.xlsx"));
		// String[][] data = ExcelData.getData(ins, 2);
		//
		// int pjID = api.getTestProjectByName(projectName).getId();
		//
		// testlinkapi.getFirstLevelSuiteIDByName("电子名片").getChildSuiteID(testlinkapi.firstLevelSuiteID,
		// "323333");
		//
		// int rowLength = data.length;
		// for (int i = 0; i < rowLength; i++) {
		// if (data[i][2].isEmpty()) {
		// String name = data[i][1];
		// testlinkapi.getChildSuiteID(testlinkapi.firstLevelSuiteID, name);
		// continue;
		// } else {
		// System.out.println(data[i][2]);
		// api.createTestCase(data[i][2], testlinkapi.needID, pjID, "admin",
		// data[i][5], null,
		// data[i][6] + "<br>" + data[i][7], TestImportance.HIGH, null, null,
		// null, true, null);
		// }
		// }
		//

		// api.createTestCase(data[0][2], testlinkapi.needID, pjID, "admin",
		// data[0][5], null,
		// data[0][6] + "<br>" + data[0][7], TestImportance.HIGH, null, null,
		// null, true, null);

		testlinkapi.getTestCases();
		testlinkapi.executeTestCase("auto", 1, "tested by 马成功2");

		// testlinkapi.creatCasese("autoTest 232");
		// testlinkapi.uploadAttchment("C:\\terry.txt");
		// System.out.println(testlinkapi.tcase.getFullExternalId());
		// testlinkapi.tcase.getExecutionOrder()

	}
}
