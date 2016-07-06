package org.bluemeric.suites;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.bluemeric.utility.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

public class Azure {
	Suite suite=new Suite();
	WebDriver driver = suite.newDriver();
	Properties prop = new Properties();
    String workspace = System.getProperty("user.dir");
	public void login(String cloud) throws IOException, InterruptedException {
		FileInputStream fs = new FileInputStream(workspace + "/properties.properties");
		prop.load(fs);
		try{
		//Azure cloud login code
		
		//------------------------------start----------------------------
			
		driver.get(prop.getProperty("url"));
		driver.manage().window().maximize();
		WebDriverWait wait=new WebDriverWait(driver,1000);
		Thread.sleep(10000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(prop.getProperty("connectwithazure")))).click();
		Thread.sleep(40000);
		Set<String> allWindowHandles= driver.getWindowHandles();
		Iterator iterator=allWindowHandles.iterator();
		String parent=(String) iterator.next();
		String child=(String) iterator.next();
		driver.switchTo().window(child);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(prop.getProperty("azureuserid")))).sendKeys("trov.inc@outlook.com");
		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(prop.getProperty("azuresignin")))).click();
		 Thread.sleep(10000);
		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(prop.getProperty("azurepassword")))).sendKeys("Trov-it123");
		 Thread.sleep(10000);
		  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(prop.getProperty("azurenewsignin")))).click();
		Thread.sleep(10000);	
		
		//-------------------------------------------end------------------------------
		
		Thread.sleep(10000);
		driver.switchTo().window(parent);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
			Reporter.log("URL : " + prop.getProperty("url"));
			Reporter.log("Response Status = " + "Login Failed due to invalid credentials");
			driver.close();
		}
	}
}
