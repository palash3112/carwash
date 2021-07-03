package com.justdial.testcases;

import com.justdial.homepage.Fitness;
import com.justdial.homepage.Homepage;

import java.util.List;
import java.util.concurrent.TimeUnit;


import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;

public class FitnessTestCases extends Homepage {
    
	public Fitness object;
	//Method for Browser calling and Report generation
	@BeforeClass
	public void browser() {
		loadproperties();
    	setup(prop.getProperty("browser"), prop.getProperty("baseUrl"));
    	ExtentHtmlReporter Reporter1=new ExtentHtmlReporter(System.getProperty("user.dir") + "/HTMLReports/" + "FitnessReport.Html");
    	report.attachReporter(Reporter1);
    	Reporter1.config().setDocumentTitle("Fitness Automation Test Results");
		Reporter1.config().setReportName("Test Report");
		Reporter1.config().setTestViewChartLocation(ChartLocation.TOP);
		Reporter1.config().setTimeStampFormat("MMM dd, yyyy HH:mm:ss");
    	driver.manage().timeouts().implicitlyWait(2,TimeUnit.SECONDS);
    	object = new Fitness();
	}
    
	//This Method is used to click Fitness Option in Menu bar
		@Test(priority = 1)
		public void fitness() {
			object.elements();
			logger = report.createTest("Fitness Page Validation","Test for Validating FitnessPage");
			FindElement("CityBox_Id").clear();
			TypeText("CityBox_Id",prop.getProperty("location"));
			logger.info("The City Is Selected In The HomePage");
			ButtonClick("locationbox_Xpath", 10);
			object.fitnessMenuClick();
			logger.info("The Fitness Menu Is Clicked");
			Assert.assertEquals(driver.getTitle(), "Fitness in " + prop.getProperty("location") + 
					                                                                 ", India - Justdial");
			
			reportPass("The Fitness Page Is Validated");
			takeScreenShots("Fitess page");
		}
	    
		
		//This Method is used to print Sub-Menu Options in Fitness Page
		@Test(priority=2)
		public void fitnessMenu() {
			object.elements();
			logger= report.createTest("Fitness Menu","Test To Check The Sub-menu options in Fitness Page");
			List<WebElement> menuList=object.FitnessMenu;
			System.out.println("The Displayed Sub-Menu Items in Fitness are: ");
			for (WebElement menus : menuList) {
				System.out.println(menus.getText());
			}
			System.out.println("----------------------------------------");
			reportPass("Sub-menu options In The Fitness Page Are Printed");
			
		}
		
		//This Method is used to click Gym Option
		@Test(priority = 3)
		public void gym() {
			object.elements();
			logger= report.createTest("Gym Option", "Test To Check The GYM option In The Fitness Page");
			object.GymMenuClick();
			logger.info("GYM Option is clicked");
			Assert.assertTrue(driver.getCurrentUrl().contains("Gym"));
			reportPass("The Gym Option Is Selected");
			takeScreenShots("Gym Page");
		}

		//This Method is used to Print all Sub-Menu options in Gym Page
		@Test(priority = 4)
		public void gymMenu() {
			object.elements();
			logger= report.createTest("Gym Menu Item's", "Test To Print Sub-menu's In The Gym Page");
			List<WebElement> menuList = object.GymMenu;
			System.out.println("The Displayed Sub-Menu Items in Gym are: ");
			for (WebElement menus : menuList) {
				System.out.println(menus.getText());
			}
			reportPass("Sub-menu options In The Gym Page Are Printed");
		}
    
	//Method to close Browser
	@AfterClass
	public void terminate() {
		driver.close();
		report.flush();
	}

}

