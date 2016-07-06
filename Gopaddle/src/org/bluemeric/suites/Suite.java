package org.bluemeric.suites;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.JAXBException;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.bluemeric.common.XmltoJava;
import org.bluemeric.utility.Utility;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class Suite implements ITestListener {

	static ChromeDriver driver;
	static boolean status = true;
	static String projectHome = System.getProperty("user.dir");
	static Logger log = Logger.getLogger(Utility.class);
	public static ExtentReports report;
	public static ExtentTest test;
	static String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
	List<XmlSuite> xmlSuites;
	static String workspace = System.getProperty("user.dir");
	//static String cloud;
	public Suite() {
		xmlSuites = new ArrayList<XmlSuite>();
	}

	public WebDriver newDriver() {
		if (driver == null) {
			return new ChromeDriver();
		}
		return driver;
	}
	public void method(boolean stat) {
		status = stat;
	}

	public static void login(String cloud) throws IOException {
		
		report = new ExtentReports(projectHome+"/test-output/"+cloud+"/ExtendedReports"+timeStamp+".html");
		report.loadConfig(new File("C:\\extentreports-java-v2.41.0\\extent-config.xml"));
		try {
			File file = new File(workspace + "/src/file.Properties");

			if (file.delete()) {
			} else {
			}
			file.createNewFile();
			Thread.sleep(10000);

		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
	public static void logout() throws IOException {
		driver.close();
		}

	private void runTests(TestNG tng, String cloud) throws JAXBException, Exception {
		XmltoJava xmltojava;

		xmltojava = (XmltoJava) org.bluemeric.common.GenericClass
				.unmarshallClass(projectHome + "/src" + "/" + "config.xml", XmltoJava.class);
		for (int k = 0; k < xmltojava.getSuite().length; k++) {

			XmlSuite suite = new XmlSuite();

			ArrayList<XmlTest> tests = new ArrayList<XmlTest>();

			for (int l = 0; l < xmltojava.getSuite()[k].getParameter().length; l++) {

				Map<String, String> parameters = new HashMap<String, String>();

				suite.setName(xmltojava.getSuite()[k].getClassname() + " - "
						+ xmltojava.getSuite()[k].getSuitename());
				List<XmlTest> xmlTest = new ArrayList<XmlTest>();
				XmlTest test = new XmlTest(suite);
				test.setName(cloud + xmltojava.getSuite()[k].getParameter()[l].getTestcase());

				parameters.put("suiteName", xmltojava.getSuite()[k].getSuitename());
				parameters.put("Testcase", xmltojava.getSuite()[k].getParameter()[l].getTestcase());
				parameters.put("param", xmltojava.getSuite()[k].getParameter()[l].getParam());
				parameters.put("cloud", cloud);
				parameters.put("param1", xmltojava.getSuite()[k].getParameter()[l].getParam1());
				parameters.put("param2", xmltojava.getSuite()[k].getParameter()[l].getParam2());
				parameters.put("param3", xmltojava.getSuite()[k].getParameter()[l].getParam3());
				parameters.put("param4", xmltojava.getSuite()[k].getParameter()[l].getParam4());
				parameters.put("param5", xmltojava.getSuite()[k].getParameter()[l].getParam5());
				parameters.put("param6", xmltojava.getSuite()[k].getParameter()[l].getParam6());
				parameters.put("param7", xmltojava.getSuite()[k].getParameter()[l].getParam7());
				parameters.put("param8", xmltojava.getSuite()[k].getParameter()[l].getParam8());
				parameters.put("param9", xmltojava.getSuite()[k].getParameter()[l].getParam9());
				parameters.put("param10", xmltojava.getSuite()[k].getParameter()[l].getParam10());
				parameters.put("Depends", xmltojava.getSuite()[k].getParameter()[l].getDependson());

				test.setParameters(parameters);

				ArrayList<XmlInclude> methodsToRun = new ArrayList<XmlInclude>();
				ArrayList<XmlClass> classes1 = new ArrayList<XmlClass>();
				XmlClass classes = new XmlClass();

				classes.setName("org.bluemeric.common." + xmltojava.getSuite()[k].getClassname());

				methodsToRun.add(new XmlInclude(xmltojava.getSuite()[k].getParameter()[l].getTestcase()));
				classes.setIncludedMethods(methodsToRun);
				classes1.add(classes);
				test.setXmlClasses(classes1);
				xmlTest.add(test);
				tests.addAll(xmlTest);
			}
			suite.setTests(tests);

			xmlSuites.add(suite);
		}
		tng.setXmlSuites(xmlSuites);
		
		tng.run();
	}

	public static void main(String[] args) throws JAXBException, Exception {
		System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");
		System.setProperty("file.log", "./log/log.log");
		PropertyConfigurator.configure("./log4j.properties");
		Suite rtest = new Suite();
		TestNG tng = new TestNG();
	
		
		for (int i = 0; i < args.length; i++) {
			String cloud = args[i];
			driver=new ChromeDriver();
			Google google = new Google();
			Azure azure = new Azure();
			login(cloud);
			if (cloud.equals("Google")) {
				rtest = new Suite();
				google.login(cloud);
				tng.setOutputDirectory("test-output/"+cloud+"/");
				tng.addListener(rtest);
				rtest.runTests(tng, cloud);
				logout();
			} else {
				azure.login(cloud);
				rtest = new Suite();
				tng.setOutputDirectory("test-output/"+cloud+"/");
				tng.addListener(rtest);
				rtest.runTests(tng, cloud);
				logout();
			}}
			
	}

	@Override
	public void onFinish(ITestContext arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStart(ITestContext arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTestFailure(ITestResult arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTestSkipped(ITestResult arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTestStart(ITestResult arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTestSuccess(ITestResult arg0) {
		// TODO Auto-generated method stub

	}

}
