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

public class Google {
	Suite suite=new Suite();
	WebDriver driver = suite.newDriver();
	Properties prop = new Properties();
    String workspace = System.getProperty("user.dir");
	public void login(String cloud) throws IOException, InterruptedException {
		FileInputStream fs = new FileInputStream(workspace + "/properties.properties");
		prop.load(fs);
	try{
		//Google cloud login code
				//------------------------------start----------------------------
		driver.get(prop.getProperty("url"));
		driver.manage().window().maximize();
		 WebDriverWait wait=new WebDriverWait(driver,200);
			//driver.get("http://stage.gopaddle.io/#/");
			
			//Handling the popup for gopaddle
			
			//------------------------------start----------------------------
			/*Thread.sleep(40000);
			Alert alert = driver.switchTo().alert();
			System.out.println(alert.getText());
			Thread.sleep(10000);
			alert.sendKeys("redbulll***");
			Thread.sleep(10000);
			alert.accept();
			Thread.sleep(10000);
			alert.accept();*/
			//------------------------------end----------------------------
			
			Thread.sleep(10000);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("html/body/div[2]/div/section[1]/div[2]/a[1]/span[2]"))).click();
			Thread.sleep(10000);
			Set<String> allWindowHandles= driver.getWindowHandles();
			Iterator iterator=allWindowHandles.iterator();
			String parent=(String) iterator.next();
			String child=(String) iterator.next();
			
			driver.switchTo().window(child);
			driver.findElement(By.id(prop.getProperty("email"))).sendKeys("demogopaddle@gmail.com");
			driver.findElement(By.id(prop.getProperty("next"))).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(prop.getProperty("password"))));
			driver.findElement(By.id(prop.getProperty("password"))).sendKeys("gopaddle#123");
			Thread.sleep(10000);
			boolean check=driver.findElement(By.xpath(prop.getProperty("staysignin"))).isSelected();
			
			if(check){
				driver.findElement(By.xpath(prop.getProperty("staysignin"))).click();
			}
			driver.findElement(By.id(prop.getProperty("signin"))).click();
			Thread.sleep(20000);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(prop.getProperty("allow")))).click();
			//------------------------------end----------------------------
			
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
