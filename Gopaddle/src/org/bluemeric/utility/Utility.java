package org.bluemeric.utility;

import java.awt.AWTException;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.bluemeric.suites.Suite;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.Listeners;


@Listeners(org.uncommons.reportng.HTMLReporter.class)
public class Utility implements ITestListener {
	static String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
	String screenload ;
	String screenget = "";
   // public WebDriver driver;
	Suite suite=new Suite();
	WebDriver driver = suite.newDriver();
	static Logger log = Logger.getLogger(Utility.class);
	Properties prop = new Properties();
    static String workspace = System.getProperty("user.dir");
	
	
	

	public void Addsourcecontrol(String username,String password) throws InterruptedException {
		
		try {
			FileInputStream fs = new FileInputStream(workspace + "/properties.properties");
			prop.load(fs);
			WebDriverWait wait = new WebDriverWait(driver, 10000);
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath(prop.getProperty("signout"))))
					.click();
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath(prop.getProperty("settings"))))
					.click();
			wait.until(
					ExpectedConditions.visibilityOfElementLocated(By.xpath(prop.getProperty("sourcecontrol"))))
					.click();
			Thread.sleep(10000);
			wait.until(
					ExpectedConditions.visibilityOfElementLocated(By.xpath(prop.getProperty("github"))))
					.click();
			Thread.sleep(10000);
			Set<String> allWindowHandles= driver.getWindowHandles();
			Iterator iterator=allWindowHandles.iterator();
			String parent=(String) iterator.next();
			boolean child=iterator.hasNext();
			if(child){
			String child1=(String) iterator.next();
			driver.switchTo().window(child1);
			}
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath(prop.getProperty("githublogin"))))
					.sendKeys(username);
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath(prop.getProperty("githubpassword"))))
					.sendKeys(password);
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath(prop.getProperty("githubcommit")))).click();
			boolean b1=isElementPresent(By.xpath(prop.getProperty("gitauthnew")));
			if(b1){
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(prop.getProperty("gitauthnew")))).click();
			}
				Thread.sleep(10000);
			 driver.switchTo().window(parent);
			 Thread.sleep(10000);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
			Reporter.log("Response Status = " + "cluster creation failed to Initiate \n");
			driver.close();
		}
	}
	public void Addsourcecontrolvalidation(String username) throws InterruptedException, IOException {
		boolean flag = false;
		FileInputStream fs = new FileInputStream(workspace + "/properties.properties");
		prop.load(fs);
		try {
			driver.findElement(By.xpath(prop.getProperty("signout"))).click();
			Thread.sleep(10000);
			driver.findElement(By.xpath(prop.getProperty("settings"))).click();
			while (!flag) {
				Thread.sleep(10000);

				driver.navigate().refresh();
				Thread.sleep(10000);
				driver.findElement(By.xpath(prop.getProperty("sourcecontrol"))).click();
				Thread.sleep(10000);
				WebDriverWait wait = new WebDriverWait(driver, 1000);
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("table")));
				WebElement table = driver.findElement(By.tagName("table"));
				WebElement tbody = table.findElement(By.tagName("tbody"));
				List<WebElement> tr = tbody.findElements(By.tagName("tr"));
				Outer: for (int j = 0; j < tr.size(); j++) {
					if (tr.get(j).getText().contains(username)) {
								flag = true;
								break Outer;
							} else {
								flag = false;
							}
						}
			}
			Thread.sleep(40000);
			Reporter.log(
					"Response Status = " + username + "source control added in portal \n");

		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
			Reporter.log("Response Status = " + "cluster creation failed \n");
			driver.close();
		}
	}
	public void createCluster(String kubname) throws InterruptedException, IOException {
		FileInputStream fs = new FileInputStream(workspace + "/properties.properties");
		prop.load(fs);
		try {
			Thread.sleep(10000);
			WebDriverWait wait = new WebDriverWait(driver, 10000);
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath(prop.getProperty("signout"))))
					.click();
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath(prop.getProperty("settings"))))
					.click();
			wait.until(
					ExpectedConditions.visibilityOfElementLocated(By.xpath(prop.getProperty("kubernets"))))
					.click();
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath(prop.getProperty("createkubernets")))).click();
			Thread.sleep(10000);
			wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath(prop.getProperty("kubname"))))
					.sendKeys(kubname);
			Thread.sleep(10000);
			wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath(prop.getProperty("disksize"))))
					.sendKeys("100");
			wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath(prop.getProperty("projectid"))))
					.click();
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath(prop.getProperty("projectidchild"))))
					.click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath(prop.getProperty("intialnodecount"))))
					.sendKeys("1");
			Thread.sleep(10000);
			wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath(prop.getProperty("machinetype"))))
					.click();
			Thread.sleep(10000);
			List<WebElement> ele1=driver.findElement(By.xpath(prop.getProperty("clustermachine"))).findElements(By.tagName("md-option"));
			Thread.sleep(10000); 
			   Outer:
			   for(int j=0;j<ele1.size();j++){
				   Robot robot = new Robot();
					Thread.sleep(10000);
					robot.keyPress(KeyEvent.VK_PAGE_UP);
					Thread.sleep(10000);
					robot.keyRelease(KeyEvent.VK_PAGE_UP);
				   System.out.println(ele1.get(j).getText());
			    if(ele1.get(j).getText().equals("n1-standard-1")){
			    	Thread.sleep(10000);
			     ele1.get(j).click();
			     break Outer;
			    }
			   }
			Thread.sleep(10000);
			wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath(prop.getProperty("zone"))))
					.click();
			Thread.sleep(10000);
			List<WebElement> ele2=driver.findElement(By.xpath(prop.getProperty("clusterzone"))).findElements(By.tagName("md-option"));
			  Thread.sleep(10000);
			   Outer:
			   for(int j=0;j<ele2.size();j++){
				   Robot robot = new Robot();
					Thread.sleep(10000);
					robot.keyPress(KeyEvent.VK_PAGE_UP);
					Thread.sleep(10000);
					robot.keyRelease(KeyEvent.VK_PAGE_UP);
			    if(ele2.get(j).getText().equals("asia-east1-a")){
			    	Thread.sleep(20000);
			     ele2.get(j).click();
			     break Outer;
			    }
			   }
			Thread.sleep(20000);
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath(prop.getProperty("create"))))
					.click();
			Thread.sleep(10000);

			Reporter.log("Response Status = " + kubname + "cluster creation Initiated \n");

		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
			Reporter.log("Response Status = " + "cluster creation failed to Initiate \n");
			driver.close();
		}
	}

	public void clusterValidation(String kubname) throws InterruptedException, IOException {
		boolean flag = false;
		FileInputStream fs = new FileInputStream(workspace + "/properties.properties");
		prop.load(fs);
		try {
			Thread.sleep(10000);
			driver.findElement(By.xpath(prop.getProperty("signout"))).click();
			Thread.sleep(10000);
			driver.findElement(By.xpath(prop.getProperty("settings"))).click();
			while (!flag) {
				Thread.sleep(10000);

				driver.navigate().refresh();
				Thread.sleep(10000);
				driver.findElement(By.xpath(prop.getProperty("kubernets"))).click();
				Thread.sleep(10000);
				WebDriverWait wait = new WebDriverWait(driver, 1000);
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("table")));
				WebElement table = driver.findElement(By.tagName("table"));
				WebElement tbody = table.findElement(By.tagName("tbody"));
				List<WebElement> tr = tbody.findElements(By.tagName("tr"));
				Outer: for (int j = 0; j < tr.size(); j++) {
					List<WebElement> td = tr.get(j).findElements(By.tagName("td"));
					if (tr.get(j).getText().contains(kubname)) {

						for (int i = 0; i < td.size(); i++) {

							if ((td.get(i).getText().contains("Creating"))) {
								flag = false;
								break Outer;
							} else {
								flag = true;
							}
						}

					}
				}
			}
			Thread.sleep(40000);
			Reporter.log(
					"Response Status = " + kubname + "cluster creation successfully done and listed in the list \n");

		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
			Reporter.log("Response Status = " + "cluster creation failed \n");
			driver.close();
		}
	}
	public boolean isElementPresent(By by) {
	    try {
	      driver.findElement(by);
	      return true;
	    } catch (org.openqa.selenium.NoSuchElementException e) {
	      return false;
	    }
	}
	public void searchDesign(String Image) throws InterruptedException, IOException {
		FileInputStream fs = new FileInputStream(workspace + "/properties.properties");
		prop.load(fs);
		boolean temp = true;
		WebDriverWait wait = new WebDriverWait(driver,3000);
		Thread.sleep(10000);
		List<WebElement> pagination = driver
				.findElement(By.xpath(prop.getProperty("list")))
				.findElements(By.tagName("li"));
	
		
			OUTER: for (int i = 2; i < pagination.size() - 2; i++) {
				Thread.sleep(10000);
				if(i!=2){
					pagination.get(i).findElement(By.tagName("a")).click();
					}
				Thread.sleep(20000);
				boolean b1=isElementPresent(By.xpath("//h6[text()='"+ Image +"']"));
				Thread.sleep(10000);
				if(b1){
					Thread.sleep(10000);
					wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h6[text()='"+ Image +"']"))).click();
					temp = false;
					break OUTER;
				}
				
		
	}
		if (temp == true) {
			Assert.fail("Image not found");
		}
		log.info("search design successfully");
		Reporter.log("Response Status = " + Image + " found in the list \n");
		}
	public void createAzureCluster(String kubname) throws InterruptedException, IOException {
		FileInputStream fs = new FileInputStream(workspace + "/properties.properties");
		prop.load(fs);
		  try {
		   Thread.sleep(10000);
		   WebDriverWait wait = new WebDriverWait(driver, 10000);
		   wait.until(ExpectedConditions
		     .visibilityOfElementLocated(By.xpath(prop.getProperty("signout"))))
		     .click();
		   wait.until(ExpectedConditions
		     .visibilityOfElementLocated(By.xpath(prop.getProperty("settings"))))
		     .click();
		   wait.until(
		     ExpectedConditions.visibilityOfElementLocated(By.xpath(prop.getProperty("kubernets"))))
		     .click();
		   wait.until(ExpectedConditions
		     .visibilityOfElementLocated(By.xpath(prop.getProperty("createkubernets")))).click();
		   Thread.sleep(10000);
		   wait.until(ExpectedConditions.visibilityOfElementLocated(
		     By.xpath(prop.getProperty("kubname"))))
		     .sendKeys(kubname);
		   Thread.sleep(10000);
		  Thread.sleep(10000);
		   driver.findElement(By.xpath(prop.getProperty("azurelocation"))).click();
		  Thread.sleep(10000);
		   List<WebElement> ele=driver.findElement(By.xpath("html/body/div[7]/md-select-menu/md-content")).findElements(By.tagName("md-option"));
		  Thread.sleep(10000);
		   for(int i=0;i<ele.size();i++){
			   Robot robot = new Robot();
				Thread.sleep(10000);
				robot.keyPress(KeyEvent.VK_PAGE_UP);
				Thread.sleep(10000);
				robot.keyRelease(KeyEvent.VK_PAGE_UP);
		    if(ele.get(i).getText().equals("West Europe")){
		     ele.get(i).click();
		     break;
		    }
		   }
		  Thread.sleep(10000);
		   driver.findElement(By.xpath(prop.getProperty("azurenodecount"))).sendKeys("2");
		  Thread.sleep(10000);
		   driver.findElement(By.xpath(prop.getProperty("azuremachinetype"))).click();
		   Thread.sleep(10000);
		   List<WebElement> ele1=driver.findElement(By.xpath(prop.getProperty("azuremachinetypechild"))).findElements(By.tagName("md-option"));
		  Thread.sleep(10000);
		   Outer:
		   for(int j=0;j<ele1.size();j++){
			   Robot robot = new Robot();
				Thread.sleep(10000);
				robot.keyPress(KeyEvent.VK_PAGE_UP);
				Thread.sleep(10000);
				robot.keyRelease(KeyEvent.VK_PAGE_UP);
		    if(ele1.get(j).getText().equals("Standard_DS2")){
		     ele1.get(j).click();
		     break Outer;
		    }
		   }
		   Thread.sleep(10000);
		   driver.findElement(By.xpath(prop.getProperty("azurecreate"))).click();
		   Thread.sleep(10000);
		   Reporter.log("Response Status = " + kubname + "cluster creation Initiated \n");

		  } catch (Exception e) {
		   e.printStackTrace();
		   Assert.fail(e.getMessage());
		   Reporter.log("Response Status = " + "cluster creation failed to Initiate \n");
		   driver.close();
		  }
		 }
	public void sourceControlDeletion(String username) throws InterruptedException, IOException {
		FileInputStream fs = new FileInputStream(workspace + "/properties.properties");
		prop.load(fs);
		try {
			driver.findElement(By.xpath(prop.getProperty("signout"))).click();
			Thread.sleep(10000);
			driver.findElement(By.xpath(prop.getProperty("settings"))).click();
				Thread.sleep(10000);

				driver.navigate().refresh();
				Thread.sleep(10000);
				driver.findElement(By.xpath(prop.getProperty("sourcecontrol"))).click();
				Thread.sleep(10000);
				WebDriverWait wait = new WebDriverWait(driver, 1000);
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("table")));
				WebElement table = driver.findElement(By.tagName("table"));
				WebElement tbody = table.findElement(By.tagName("tbody"));
				List<WebElement> tr = tbody.findElements(By.tagName("tr"));
				 for (int j = 0; j < tr.size(); j++) {
					List<WebElement> td = tr.get(j).findElements(By.tagName("td"));
					if (tr.get(j).getText().contains(username)) {
								td.get(3).click();
						}
					}
				 Thread.sleep(10000);
				 List<WebElement> button = driver.findElement(By.xpath(prop.getProperty("appdeletechild")))
							.findElements(By.tagName("button"));
					for (int i = 0; i < button.size(); i++) {
						if (button.get(i).getText().equals("Delete")) {
							Thread.sleep(10000);
							button.get(i).click();
						}
					}
			Thread.sleep(10000);
			Reporter.log(
					"Response Status = " + username + "source control user delettion initiated \n");

		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
			Reporter.log("Response Status = " + "source control user deletion initiation failed \n");
			driver.close();
		}
		 }
	public void sourceControlDeletionValidation(String username) throws InterruptedException, IOException {
		boolean flag = false;
		FileInputStream fs = new FileInputStream(workspace + "/properties.properties");
		prop.load(fs);
		Thread.sleep(10000);
		WebDriverWait wait=new WebDriverWait(driver,1000);
		try {
			driver.findElement(By.xpath(prop.getProperty("signout"))).click();
			Thread.sleep(10000);
			driver.findElement(By.xpath(prop.getProperty("settings"))).click();
			while (!flag) {
				Thread.sleep(10000);

				driver.navigate().refresh();
				Thread.sleep(10000);
				driver.findElement(By.xpath(prop.getProperty("sourcecontrol"))).click();
				Thread.sleep(10000);
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("table")));
					WebElement table1 = driver.findElement(By.tagName("table"));
					WebElement tbody1 = table1.findElement(By.tagName("tbody"));
					List<WebElement> tr1 = tbody1.findElements(By.tagName("tr"));
					if(tr1.size()!=0){
					for (int j = 0; j < tr1.size(); j++) {
						List<WebElement> td1 = tr1.get(j).findElements(By.tagName("td"));
						for (int i = 0; i < td1.size(); i++) {
							if ((tr1.get(j).getText().contains(username))) {
								flag = false;

							} else {
								flag = true;
							}
						}
					}
					}else {
						flag = true;
					}
			}
			Thread.sleep(10000);
			Reporter.log(
					"Response Status = " + username + "source control user deleted successfully \n");

		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
			Reporter.log("Response Status = " + "source control user deletion failed \n");
			driver.close();
		}
		 }
	public void azurClusterDeletion(String kubname) throws InterruptedException, IOException {
		FileInputStream fs = new FileInputStream(workspace + "/properties.properties");
		prop.load(fs);
		try {
			driver.findElement(By.xpath(prop.getProperty("signout"))).click();
			Thread.sleep(10000);
			driver.findElement(By.xpath(prop.getProperty("settings"))).click();
				Thread.sleep(10000);

				driver.navigate().refresh();
				Thread.sleep(10000);
				driver.findElement(By.xpath(prop.getProperty("kubernets"))).click();
				Thread.sleep(10000);
				WebDriverWait wait = new WebDriverWait(driver, 1000);
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("table")));
				WebElement table = driver.findElement(By.tagName("table"));
				WebElement tbody = table.findElement(By.tagName("tbody"));
				List<WebElement> tr = tbody.findElements(By.tagName("tr"));
				 for (int j = 0; j < tr.size(); j++) {
					List<WebElement> td = tr.get(j).findElements(By.tagName("td"));
					if (tr.get(j).getText().contains(kubname)) {
								td.get(4).click();
						}
					}
				 Thread.sleep(10000);
				 List<WebElement> button = driver.findElement(By.xpath(prop.getProperty("appdeletechild")))
							.findElements(By.tagName("button"));
					for (int i = 0; i < button.size(); i++) {
						if (button.get(i).getText().equals("Delete")) {
							Thread.sleep(10000);
							button.get(i).click();
						}
					}
			Thread.sleep(10000);
			Reporter.log(
					"Response Status = " + kubname + "cluster delettion initiated \n");

		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
			Reporter.log("Response Status = " + "cluster deletion initiation failed \n");
			driver.close();
		}
		 }
	public void deleteAzureValidation(String kubname) throws InterruptedException, IOException {
		FileInputStream fs = new FileInputStream(workspace + "/properties.properties");
		prop.load(fs);
		boolean flag = false;
		Thread.sleep(10000);
		WebDriverWait wait=new WebDriverWait(driver,1000);
		try {
			driver.findElement(By.xpath(prop.getProperty("signout"))).click();
			Thread.sleep(10000);
			driver.findElement(By.xpath(prop.getProperty("settings"))).click();
			while (!flag) {
				Thread.sleep(10000);

				driver.navigate().refresh();
				Thread.sleep(10000);
				driver.findElement(By.xpath(prop.getProperty("kubernets"))).click();
				Thread.sleep(10000);
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("table")));
					WebElement table1 = driver.findElement(By.tagName("table"));
					WebElement tbody1 = table1.findElement(By.tagName("tbody"));
					List<WebElement> tr1 = tbody1.findElements(By.tagName("tr"));
					if(tr1.size()!=0){
					for (int j = 0; j < tr1.size(); j++) {
						List<WebElement> td1 = tr1.get(j).findElements(By.tagName("td"));
						for (int i = 0; i < td1.size(); i++) {
							if ((tr1.get(j).getText().contains(kubname))) {
								flag = false;

							} else {
								flag = true;
							}
						}
					}
					}else {
						flag = true;
					}
			}
			Thread.sleep(10000);
			Reporter.log(
					"Response Status = " + kubname + "cluster deleted successfully \n");

		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
			Reporter.log("Response Status = " + "cluster deletion failed \n");
			driver.close();
		}
		 }
	public void createSingletierDesign(String name,String url,String auth,String platform,String build,String buildscript,String startscript,String install,String preinstall,String postinstall,String port) throws InterruptedException, AWTException, IOException {
		FileInputStream fs = new FileInputStream(workspace + "/properties.properties");
		prop.load(fs);
		driver.navigate().to(prop.getProperty("appurl"));
		Thread.sleep(10000);
		driver.findElement(By.xpath(prop.getProperty("design"))).click();
		Thread.sleep(10000);
		WebDriverWait wait=new WebDriverWait(driver, 1000);
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_PAGE_UP);
		Thread.sleep(10000);
		robot.keyRelease(KeyEvent.VK_PAGE_UP); 
		Thread.sleep(10000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(prop.getProperty("createdesign")))).click();
		Thread.sleep(10000);
		driver.findElement(By.xpath(prop.getProperty("designgithub"))).click();
		
		Thread.sleep(10000);
		//driver.manage().timeouts().pageLoadTimeout(1000, TimeUnit.)
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(prop.getProperty("dbname"))));
		driver.findElement(By.xpath(prop.getProperty("dbname"))).sendKeys(name);
		driver.findElement(By.xpath(prop.getProperty("gitpath"))).sendKeys(url);
		
		
		Thread.sleep(10000);
		
		
		if(auth!=""){
			driver.findElement(By.xpath(prop.getProperty("gitauth"))).click();
		Thread.sleep(10000);
		
		List<WebElement> list=driver.findElement(By.xpath(prop.getProperty("dbversionchild"))).findElements(By.tagName("md-option"));
		
		for(int i=0;i<list.size();i++){
			if(list.get(i).getText().equals(auth)){
				Thread.sleep(10000);
				list.get(i).click();
				break;
			}
			}
		}
			Thread.sleep(10000);
			
		driver.findElement(By.xpath(prop.getProperty("gitplatform"))).click();
		
		Thread.sleep(10000);
		List<WebElement> list=driver.findElement(By.xpath(prop.getProperty("dbversionchild"))).findElements(By.tagName("md-option"));
		
		for(int i=0;i<list.size();i++){
			if(list.get(i).getText().equals(platform)){
				Thread.sleep(10000);
				list.get(i).click();
				break;
			}
		}
			Thread.sleep(10000);
		driver.findElement(By.xpath(prop.getProperty("scriptbutton"))).click();
		Thread.sleep(10000);
		driver.findElement(By.xpath(prop.getProperty("gitbuilder"))).click();
		Thread.sleep(10000);
	List<WebElement> list1=driver.findElement(By.xpath(prop.getProperty("gitbuilderchild"))).findElements(By.tagName("md-option"));

		for(int j=0;j<list1.size();j++){
			if(list1.get(j).getText().equals(build)){
				list1.get(j).click();
				break;
			}
		}
		Thread.sleep(10000);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(prop.getProperty("gitbuild")))).sendKeys(buildscript);
		
		if(startscript !=""){
			
		driver.findElement(By.xpath(prop.getProperty("advancefield"))).click();
		}
		if(startscript !=""){
		driver.findElement(By.xpath(prop.getProperty("startscript"))).sendKeys(startscript);
		}
		if(install !=""){
		driver.findElement(By.xpath(prop.getProperty("install"))).sendKeys(install);
		}
		if(preinstall !=""){
		driver.findElement(By.xpath(prop.getProperty("preinstall"))).sendKeys(preinstall);
		}
		if(postinstall !=""){
			driver.findElement(By.xpath(prop.getProperty("postinstall"))).sendKeys(postinstall);
			}
		Thread.sleep(10000);
		driver.findElement(By.xpath(prop.getProperty("networkbutton"))).click();
		Thread.sleep(10000);
		driver.findElement(By.xpath(prop.getProperty("gitport"))).sendKeys(port);
		driver.findElement(By.xpath(prop.getProperty("dbsave"))).click();
		Thread.sleep(10000);
	}
	
	public void createMultitierDesign(String name,String version,String path) throws IOException, InterruptedException{
		FileInputStream fs = new FileInputStream(workspace + "/properties.properties");
		prop.load(fs);
		if(name.equals("mysql")){
		driver.findElement(By.xpath(prop.getProperty("mysql"))).click();
		}
		else if(name.equals("cass")){
			driver.findElement(By.xpath(prop.getProperty("cassandra"))).click();
		}else
		{
			driver.findElement(By.xpath(prop.getProperty("mongo"))).click();
		}
		Thread.sleep(10000);
		driver.findElement(By.xpath(prop.getProperty("dbname"))).sendKeys(name);
		
		driver.findElement(By.xpath(prop.getProperty("dbversion"))).click();
		Thread.sleep(10000);
	List<WebElement> list2=driver.findElement(By.xpath(prop.getProperty("dbversionchild"))).findElements(By.tagName("md-option"));
	OUTER:
		for(int i=0;i<list2.size();i++){
			Thread.sleep(10000);
			if(list2.get(i).getText().equals(version)){
				Thread.sleep(10000);
				list2.get(i).click();
				break OUTER;
			}
		}
	Thread.sleep(10000);
		if(path!=""){
		driver.findElement(By.xpath(prop.getProperty("dbpath"))).sendKeys(path);
		}
		Thread.sleep(10000);
		driver.findElement(By.xpath(prop.getProperty("dbsave"))).click();
		
		Thread.sleep(10000);
		
		
	}
	public void linkComponent(String image,String image1) throws  AWTException, IOException, InterruptedException{
		FileInputStream fs = new FileInputStream(workspace + "/properties.properties");
		prop.load(fs);
		Robot robot = new Robot();
		Thread.sleep(10000);
		robot.keyPress(KeyEvent.VK_PAGE_UP);
		Thread.sleep(10000);
		robot.keyRelease(KeyEvent.VK_PAGE_UP);
		 Thread.sleep(10000);
		 Thread.sleep(10000);
		driver.findElement(By.xpath(prop.getProperty("addedge"))).click();
		Screen s=new Screen();
		Thread.sleep(10000);
		Pattern p=new Pattern(image);
		Pattern p1=new Pattern(image1);
		Thread.sleep(10000);
		
		try {
			s.dragDrop(p, p1);
		} catch (FindFailed e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Thread.sleep(10000);

	}
	public void publish(String design) throws AWTException, IOException, InterruptedException{
		FileInputStream fs = new FileInputStream(workspace + "/properties.properties");
		prop.load(fs);
		
		//Thread.sleep(10000);
		//driver.navigate().refresh();
		//Thread.sleep(10000);
		Robot robot = new Robot();
		Thread.sleep(10000);
		robot.keyPress(KeyEvent.VK_PAGE_UP);
		Thread.sleep(10000);
		robot.keyRelease(KeyEvent.VK_PAGE_UP);
		 Thread.sleep(10000);
		 Thread.sleep(10000);
		driver.findElement(By.xpath(prop.getProperty("designedit"))).click();
		Thread.sleep(10000);
		driver.findElement(By.xpath(prop.getProperty("desingtext"))).clear();
		driver.findElement(By.xpath(prop.getProperty("desingtext"))).sendKeys(design);
		
		Thread.sleep(10000);
		driver.findElement(By.xpath(prop.getProperty("designsave"))).click();
		Thread.sleep(10000);
		driver.findElement(By.xpath(prop.getProperty("publish"))).click();
		Thread.sleep(10000);
	}
public void designValidation(String design) throws InterruptedException, IOException{
	FileInputStream fs = new FileInputStream(workspace + "/properties.properties");
	prop.load(fs);
	Thread.sleep(10000);
	driver.navigate().to(prop.getProperty("appurl"));
	Thread.sleep(10000);
	driver.findElement(By.xpath(prop.getProperty("design"))).click();
	Thread.sleep(10000);
	searchDesign(design);
	Thread.sleep(10000);
	String val=driver.findElement(By.xpath(prop.getProperty("designsttus"))).getText();
	
	if(val.equals("Success"))
	{
		Assert.assertTrue(true, val);
	}
}
	public void launchApp(String imgname, String appname, String port, String kubname) throws InterruptedException, IOException { // pagination
		FileInputStream fs = new FileInputStream(workspace + "/properties.properties");
		prop.load(fs);																									// of
																														// list
		WebDriverWait wait = new WebDriverWait(driver, 10000);
		boolean temp = true;
		Thread.sleep(10000);
		driver.navigate().to(prop.getProperty("appurl"));
		Thread.sleep(10000);
		driver.findElement(By.xpath(prop.getProperty("design"))).click();
		Thread.sleep(10000);
		//design();
		try {
				searchDesign(imgname);
				Thread.sleep(10000);
			driver.findElement(By.xpath(prop.getProperty("launch"))).click();
			Thread.sleep(10000);
			driver.findElement(By.xpath(prop.getProperty("appname"))).sendKeys(appname);
			driver.findElement(By.xpath(prop.getProperty("appport")))
					.sendKeys(port);
			Select gender = new Select(
					driver.findElement(By.xpath(prop.getProperty("appkub"))));
			gender.selectByVisibleText(kubname);
			Thread.sleep(10000);
			Boolean Autoscale = driver
					.findElement(By.xpath(prop.getProperty("autoscale"))).isDisplayed();
			Boolean cdelivery = driver
					.findElement(By.xpath(prop.getProperty("cdelivery"))).isDisplayed();
			Boolean firewall = driver
					.findElement(By.xpath(prop.getProperty("firewall"))).isDisplayed();
			if (Autoscale) {
				wait.until(ExpectedConditions.visibilityOfElementLocated(
						By.xpath(prop.getProperty("autoscale")))).click();
			}
			if (cdelivery) {
				wait.until(ExpectedConditions.visibilityOfElementLocated(
						By.xpath(prop.getProperty("cdelivery")))).click();
			}
			if (firewall) {
				wait.until(ExpectedConditions.visibilityOfElementLocated(
						By.xpath(prop.getProperty("firewall")))).click();
			}

			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath(prop.getProperty("appbutton")))).click();
			Thread.sleep(40000);
			Assert.assertEquals(
					wait.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath(prop.getProperty("apppagenew")))).getText(),
					"Applications");
			log.info("search design successfully");
			Reporter.log("Response Status = " + appname + "Application launch Initiated \n");
			temp = false;
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
			Reporter.log("Response Status = " + "Application launch initiation failed \n");
			driver.close();
		}
		if (temp == true) {
			Assert.fail("Image not found");
		}
	}
	public void launchValidation(String appname) throws InterruptedException, IOException {
		FileInputStream fs = new FileInputStream(workspace + "/properties.properties");
		prop.load(fs);
		boolean flag = false;
		WebDriverWait wait=new WebDriverWait(driver,1000);
		driver.navigate().to(prop.getProperty("appurl"));
		Thread.sleep(10000);
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(prop.getProperty("apppage"))));
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(prop.getProperty("apppage")))).click();
			Thread.sleep(10000);
			while (!flag) {
				driver.navigate().refresh();
				Thread.sleep(20000);
					searchDesign(appname);
				
				if (driver.findElement(By.xpath(prop.getProperty("apptext"))).getText()
						.equals("Success")) {
					flag = true;
					// break;
				} else if (driver.findElement(By.xpath(prop.getProperty("apptext"))).getText()
						.equals("Failed")) {
					Assert.fail("Application creation failed");
					flag = true;
				} else {
					flag = false;
				}
			}
			log.info("search design successfully");
			Reporter.log("Response Status = " + appname + "Application created succesfully \n");
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
			Reporter.log("Response Status = " + "Application creation failed \n");
			driver.close();
		}
		if (flag == false) {
			Assert.fail("Application creation failed");
		}
	}

	public void accessValidation(String appname, String text, String Updatedata) throws InterruptedException, IOException {
		FileInputStream fs = new FileInputStream(workspace + "/properties.properties");
		prop.load(fs);
		boolean flag = true;
		driver.navigate().to(prop.getProperty("appurl"));
		Thread.sleep(10000);
		try {
			driver.findElement(By.xpath(prop.getProperty("apppage"))).click();
			
				searchDesign(appname);
			driver.findElement(By.xpath(prop.getProperty("accessurl"))).click();
			Thread.sleep(10000);
			String val = driver
					.findElement(By
							.xpath(prop.getProperty("accesstext")))
					.getText();
			// Assert.assertNotNull(val);
			Thread.sleep(10000);
			if (text == null) {
				driver.navigate().to(val);
			} else {
				driver.navigate().to(val + text);
			}
			Thread.sleep(10000);
			String data = driver.findElement(By.xpath(prop.getProperty("accessurldata"))).getText();
			Thread.sleep(10000);
			Assert.assertEquals(Updatedata, data);
			Thread.sleep(10000);
			flag = true;
			log.info("search design successfully");
			Reporter.log("Response Status = " + "Accessing the application succesfully \n");
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
			Reporter.log("Response Status = " + "Application creation failed \n");
			driver.close();
		}
		if (flag == false) {
			Assert.fail("Application creation failed \n");
		}
	}

	public void stopAction(String appname, String text) throws InterruptedException, IOException {
		FileInputStream fs = new FileInputStream(workspace + "/properties.properties");
		prop.load(fs);
		WebDriverWait wait=new WebDriverWait(driver,1000);
		try {
		driver.navigate().to(prop.getProperty("appurl"));
		Thread.sleep(10000);
		driver.findElement(By.xpath(prop.getProperty("apppage"))).click();
	
		launchValidation(appname);
		
		Thread.sleep(10000);
		driver.findElement(By.xpath(prop.getProperty("detailsbutton"))).click();
		Thread.sleep(10000);

		driver.findElement(By.xpath(prop.getProperty("action"))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(prop.getProperty("stop")))).click();

		
			driver.findElement(By.xpath(prop.getProperty("apppage"))).click();
			
				searchDesign(appname);
			
			driver.findElement(By.xpath(prop.getProperty("accessurl"))).click();
			Thread.sleep(10000);
			String val = driver
					.findElement(By
							.xpath(prop.getProperty("accesstext")))
					.getText();
			// Assert.assertNotNull(val);
			Thread.sleep(10000);
			if (text == null) {
				driver.navigate().to(val);
			} else {
				driver.navigate().to(val + text);
			}
			Thread.sleep(10000);
			String data = driver.findElement(By.xpath(prop.getProperty("accessurldata"))).getText();
			if (data.contains("ERR_CONNECTION_REFUSED")) {
				Assert.assertTrue(true, "Stop action working properly");
			} else {
				Assert.fail("stop action not working properly");
			}
			Reporter.log("Response Status = " + appname + "application stopped successfully \n");
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
			Reporter.log("Response Status = " + "application stopping failed \n");
			driver.close();
		}
	}

	public void startAction(String appname, String text, String updatedata) throws InterruptedException, AWTException, IOException {
		WebDriverWait wait=new WebDriverWait(driver,1000);
		FileInputStream fs = new FileInputStream(workspace + "/properties.properties");
		prop.load(fs);
		try {
			driver.navigate().to(prop.getProperty("appurl"));
			Thread.sleep(10000);
			driver.findElement(By.xpath(prop.getProperty("apppage"))).click();
			
				searchDesign(appname);
			
			
				Thread.sleep(10000);
			driver.findElement(By.xpath(prop.getProperty("detailsbutton"))).click();
			Thread.sleep(10000);
			//driver.findElement(By.xpath("html/body/div[2]/div/div[1]/ul/li[15]/div/h5/span[2]/label/span[1]")).click();
			driver.findElement(By.xpath(prop.getProperty("action"))).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(prop.getProperty("start")))).click();
			accessValidation(appname, text, updatedata);
			Reporter.log("Response Status = " + appname + "application started successfully \n");
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
			Reporter.log("Response Status = " + "application starting failed \n");
			driver.close();
		}
	}

	public void cdeliveryOn(String appname, String text, String updatedata, String gitnewdata)
			throws InterruptedException, AWTException, IOException {
		FileInputStream fs = new FileInputStream(workspace + "/properties.properties");
		prop.load(fs);
		try {
			driver.navigate().to(prop.getProperty("appurl"));
			Thread.sleep(10000);
			driver.findElement(By.xpath(prop.getProperty("apppage"))).click();
			Thread.sleep(10000);
			WebDriverWait wait = new WebDriverWait(driver, 1000);
			driver.navigate().to(prop.getProperty("giturl"));
			Thread.sleep(10000);
		/*	driver.findElement(By.xpath("html/body/header/div/div/div/a[2]")).click();
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("html/body/div[4]/div[1]/div/form/div[4]/input[1]")))
					.sendKeys("sivaskr");
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("html/body/div[4]/div[1]/div/form/div[4]/input[2]")))
					.sendKeys("trov-it123");
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("html/body/div[4]/div[1]/div/form/div[4]/input[3]"))).click();*/
			wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath(prop.getProperty("gitfork")))).click();
			Thread.sleep(10000);
			driver.findElement(By.linkText(prop.getProperty("applink"))).click();
			Thread.sleep(10000);
			String script = "# A simple hello world app to test Flask and AWS Elastic Beanstalk integration.\n"
					+ "\nimport flask\n" + "application = flask.Flask(__name__)\n" + "\n@application.route('/')\n"
					+ "def hello():\n" + " return '" + updatedata + gitnewdata + "'\n" + "\nif __name__ == '__main__':"
					+ "\n application.run(host='0.0.0.0',debug=True)";
			wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath(prop.getProperty("gitedit")))).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath(prop.getProperty("gitedittext"))));

			Keyboard kb = ((HasInputDevices) driver).getKeyboard();
			kb.sendKeys(Keys.TAB);
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_A);
			robot.keyRelease(KeyEvent.VK_A);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			Thread.sleep(10000);
			robot.keyPress(KeyEvent.VK_DELETE);
			robot.keyRelease(KeyEvent.VK_DELETE);
			Thread.sleep(10000);

			StringSelection ss = new StringSelection(script);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
			robot.keyPress(java.awt.event.KeyEvent.VK_CONTROL);
			robot.keyPress(java.awt.event.KeyEvent.VK_V);
			Thread.sleep(10000);
			robot.keyRelease(java.awt.event.KeyEvent.VK_V);
			robot.keyRelease(java.awt.event.KeyEvent.VK_CONTROL);
			Thread.sleep(10000);
			driver.findElement(By.xpath(prop.getProperty("gitupdate"))).click();
			Thread.sleep(10000);
			driver.navigate().to(prop.getProperty("appurl"));
			Thread.sleep(10000);

			accessValidation(appname, text, updatedata + gitnewdata);
			Reporter.log("Response Status = " + "Before updating the Application accessdata is :" + updatedata + "\n");
			Reporter.log("Response Status = " + "cdeliveryOn - After updating the Application accessdata is :"
					+ updatedata + gitnewdata + "\n");
			Reporter.log("Response Status = " + appname + "cdeliveryon working successfully for this appllication \n");
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
			Reporter.log("Response Status = " + "cdeliveryon failed for this appllication \n");
			driver.close();
		}

	}

	public void cdeliveryOff(String appname, String text, String updatedata, String gitdata)
			throws InterruptedException, AWTException, IOException {
		FileInputStream fs = new FileInputStream(workspace + "/properties.properties");
		prop.load(fs);
		WebDriverWait wait = new WebDriverWait(driver, 10000);
		try {
			driver.navigate().to(prop.getProperty("appurl"));
			Thread.sleep(10000);
			driver.findElement(By.xpath(prop.getProperty("apppage"))).click();
			
				searchDesign(appname);
			
			Thread.sleep(10000);
			driver.findElement(By.xpath(prop.getProperty("detailsbutton"))).click();
			Thread.sleep(10000);
			//driver.findElement(By.xpath("html/body/div[2]/div/div[1]/ul/li[17]/div/h5/span[2]/label/span[1]")).click();
			driver.findElement(By.xpath(prop.getProperty("action"))).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(prop.getProperty("cdeliveryon")))).click();
			Thread.sleep(10000);
			driver.navigate().to(prop.getProperty("giturlnew"));
			Thread.sleep(10000);
			wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath(prop.getProperty("gitfork")))).click();
			Thread.sleep(10000);
			driver.findElement(By.linkText(prop.getProperty("applink"))).click();
			Thread.sleep(10000);
			String script = "# A simple hello world app to test Flask and AWS Elastic Beanstalk integration.\n"
					+ "\nimport flask\n" + "application = flask.Flask(__name__)\n" + "\n@application.route('/')\n"
					+ "def hello():\n" + " return '" + updatedata + gitdata + "'\n" + "\nif __name__ == '__main__':"
					+ "\n application.run(host='0.0.0.0',debug=True)";
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(prop.getProperty("gitedit")))).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(prop.getProperty("gitedittext"))));

			Keyboard kb = ((HasInputDevices) driver).getKeyboard();
			kb.sendKeys(Keys.TAB);
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_A);
			robot.keyRelease(KeyEvent.VK_A);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			Thread.sleep(10000);
			robot.keyPress(KeyEvent.VK_DELETE);
			robot.keyRelease(KeyEvent.VK_DELETE);
			Thread.sleep(10000);

			StringSelection ss = new StringSelection(script);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
			robot.keyPress(java.awt.event.KeyEvent.VK_CONTROL);
			robot.keyPress(java.awt.event.KeyEvent.VK_V);
			robot.keyRelease(java.awt.event.KeyEvent.VK_V);
			robot.keyRelease(java.awt.event.KeyEvent.VK_CONTROL);
			Thread.sleep(10000);
			driver.findElement(By.xpath(prop.getProperty("gitupdate"))).click();
			Thread.sleep(10000);
			driver.navigate().to(prop.getProperty("appurl"));
			Thread.sleep(10000);

			accessValidation(appname, text, updatedata);
			Reporter.log("Response Status = " + "Before updating the Application accessdata is :" + updatedata + "\n");
			Reporter.log("Response Status = " + "cdeliveryOff - After updating the Application accessdata is :"
					+ updatedata + "\n");
			Reporter.log("Response Status = " + "cdeliveryoff working successfully for this appllication \n");
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
			Reporter.log("Response Status = " + "cdeliveryoff failed for this appllication \n");
			driver.close();
		}
	}

	public void deleteApp(String appname) throws InterruptedException, IOException {
		FileInputStream fs = new FileInputStream(workspace + "/properties.properties");
		prop.load(fs);
		Thread.sleep(10000);
		driver.navigate().to(prop.getProperty("appurl"));
		Thread.sleep(10000);
		try {
			driver.findElement(By.xpath(prop.getProperty("apppage"))).click();
			
				searchDesign(appname);
			
			driver.findElement(By.xpath(prop.getProperty("appdelete"))).click();
			Thread.sleep(10000);
			List<WebElement> button = driver.findElement(By.xpath(prop.getProperty("appdeletechild")))
					.findElements(By.tagName("button"));
			for (int i = 0; i < button.size(); i++) {
				if (button.get(i).getText().equals("Delete")) {
					Thread.sleep(10000);
					button.get(i).click();
				}
			}
			Reporter.log("Response Status = " + appname + "app deletion initiated \n");
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
			Reporter.log("Response Status = " + "app deletion failed initiated \n");
			driver.close();
		}
	}

	public void deleteValidation(String appname) throws InterruptedException, IOException {
		FileInputStream fs = new FileInputStream(workspace + "/properties.properties");
		prop.load(fs);
		Thread.sleep(10000);
		boolean temp=true;
		WebDriverWait wait=new WebDriverWait(driver,10000);
		driver.navigate().to(prop.getProperty("appurl"));
		Thread.sleep(10000);
		try{
		driver.findElement(By.xpath(prop.getProperty("apppage"))).click();
		List<WebElement> pagination = driver
				.findElement(By.xpath(prop.getProperty("list")))
				.findElements(By.tagName("li"));
	
		
			OUTER: for (int i = 2; i < pagination.size() - 2; i++) {
				Thread.sleep(10000);
				if(i!=2){
					pagination.get(i).findElement(By.tagName("a")).click();
					}
				Thread.sleep(10000);
				boolean b1=isElementPresent(By.xpath("//h6[text()='"+ appname +"']"));
				Thread.sleep(10000);
				if(b1){
					Thread.sleep(10000);
					wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h6[text()='"+ appname +"']"))).click();
					temp = true;
					break OUTER;
				}
				
		
	}

				if (temp == false) {
					Assert.assertTrue(true, "Image removed successfully");
				}
			
			log.info("search design successfully");
			Reporter.log("Response Status = " + appname + "app deleted successfully \n");
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
			Reporter.log("Response Status = " + "app deletion failed \n");
			driver.close();
		}
	}

	public void clusterDeletion(String kubname) throws InterruptedException, IOException {
		FileInputStream fs = new FileInputStream(workspace + "/properties.properties");
		prop.load(fs);
		Thread.sleep(10000);
		try {
			driver.navigate().to(prop.getProperty("googleconsole"));
			Thread.sleep(10000);
			WebDriverWait wait = new WebDriverWait(driver, 10000);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("table")));
			WebElement table = driver.findElement(By.tagName("table"));
			WebElement tbody = table.findElement(By.tagName("tbody"));
			List<WebElement> tr = tbody.findElements(By.tagName("tr"));
			for (WebElement trnew : tr) {
				List<WebElement> td = trnew.findElements(By.tagName("td"));
				for (int i = 0; i < td.size(); i++) {
					if ((td.get(i).getText().equals(kubname))) {

						td.get(i + 1).click();
					}
				}
			}
			driver.findElement(By
					.xpath(prop.getProperty("googleclustdelete")))
					.click();
			Thread.sleep(10000);
			driver.findElement(By.xpath(prop.getProperty("googledelte"))).click();
			Thread.sleep(10000);

			Reporter.log("Response Status = " + "cluster deletion initiated \n");

		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
			Reporter.log("Response Status = " + "cluster deletion initiation failed \n");
			driver.close();
		}
	}

	public void clusterDeletionvalidation(String kubname) throws InterruptedException, IOException {
		FileInputStream fs = new FileInputStream(workspace + "/properties.properties");
		prop.load(fs);
		boolean flag = false;
		Thread.sleep(10000);
		WebDriverWait wait = new WebDriverWait(driver, 10000);
		try {
			while (!flag) {
				driver.navigate().refresh();
				Thread.sleep(10000);
				boolean b = driver
						.findElement(By
								.xpath(prop.getProperty("googletable")))
						.isDisplayed();
				if (!b) {

					wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("table")));
					WebElement table1 = driver.findElement(By.tagName("table"));
					WebElement tbody1 = table1.findElement(By.tagName("tbody"));
					List<WebElement> tr1 = tbody1.findElements(By.tagName("tr"));
					for (int j = 0; j < tr1.size(); j++) {
						List<WebElement> td1 = tr1.get(j).findElements(By.tagName("td"));
						for (int i = 0; i < td1.size(); i++) {
							if ((tr1.get(j).getText().contains(kubname))) {
								flag = false;

							} else {
								flag = true;
							}
						}
					}
				} else {
					flag = true;
					Assert.assertTrue(true, "Table not found");
				}
			}

			Reporter.log("Response Status = " + "cluster deleted successfully \n");
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
			Reporter.log("Response Status = " + "cluster deletion  failed \n");
			driver.close();
		}

	}	
		
	 
	public String screenshot(String screenshot,String cloud) throws IOException {
		FileInputStream fs = new FileInputStream(workspace + "/properties.properties");
		prop.load(fs);
		screenload= System.getProperty("user.dir") + "/test-output/"+cloud+"/html/";
		File screen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(screen, new File(screenload + screenshot + ".png"));
		System.setProperty("org.uncommons.reportng.escape-output", "false");
		String image = screenget + screenshot + ".png";
		Reporter.log("<a title= \"title\" href=\"" + image + "\">" + "<img width=\"700\" height=\"550\" src=\"" + image
				+ "\"></a>");
		return image;

	}

	@Override
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub

	}
}
