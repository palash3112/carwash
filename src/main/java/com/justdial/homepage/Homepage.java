package com.justdial.homepage;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Homepage  extends Baseclass{
	
	public WebElement FindElement(String LocatorKey) {
		if(LocatorKey.endsWith("_Id"))
			return driver.findElement(By.id(prop.getProperty(LocatorKey)));
		else if(LocatorKey.endsWith("_ClassName"))
			return driver.findElement(By.className(prop.getProperty(LocatorKey)));
		else if(LocatorKey.endsWith("_Xpath"))
			return driver.findElement(By.xpath(prop.getProperty(LocatorKey)));
		else if(LocatorKey.endsWith("_tagName"))
			return driver.findElement(By.tagName(prop.getProperty(LocatorKey)));
		else 
			return null;
			
	}
	
	public List<WebElement> FindElementList(String LocatorKey){
		
		return driver.findElements(By.xpath(prop.getProperty(LocatorKey)));
	}
	
	public void TypeText(String element, String city) {
		FindElement(element).sendKeys(city);
	}
	
	public void ButtonClick(String LocatorKey, int seconds) {
		WebDriverWait wait = new WebDriverWait(driver, seconds);
		wait.until(ExpectedConditions.visibilityOf(FindElement(LocatorKey)));
		FindElement(LocatorKey).click();
	}
	
	
	

	}


