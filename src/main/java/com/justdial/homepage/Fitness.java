package com.justdial.homepage;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Fitness extends Homepage{
	
	
	By fitness_key=By.xpath("//ul[@class='hotkeys-list ']//li/a[contains(@title,'Fitness')]");
	By Gym_key=By.xpath("//ul[contains(@class,'listview')]//li/a/span[@title='Gym']");
	public List<WebElement> FitnessMenu,GymMenu;
	public WebElement fitnessText,Gym;
	
	public void elements() {
		FitnessMenu = driver.findElements(By.xpath("//*[@id=\"mnintrnlbnr\"]/ul"));
		GymMenu=driver.findElements(By.className("mm-listview"));
	}

	 public void fitnessMenuClick() {
		 WebElement element=driver.findElement(fitness_key);
		 WebDriverWait wait = new WebDriverWait(driver, 10);
		 wait.until(ExpectedConditions.visibilityOf(element));
		 element.click();
	 }
	 public void GymMenuClick() {
		 WebElement element=driver.findElement(Gym_key);
		 WebDriverWait wait = new WebDriverWait(driver, 10);
		 wait.until(ExpectedConditions.visibilityOf(element));
		 element.click();
	 }
}
