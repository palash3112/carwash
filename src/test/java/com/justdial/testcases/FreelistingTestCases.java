package com.justdial.testcases;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.justdial.homepage.Freelisting;
import com.justdial.homepage.Homepage;



public class FreelistingTestCases extends Freelisting {

	public Freelisting obj;
    
	//Browser calling and Report Generation
	@BeforeClass
	public void browser() {
		loadproperties();
    	setup(prop.getProperty("browser"), prop.getProperty("baseUrl"));
    	ExtentHtmlReporter Reporter1=new ExtentHtmlReporter(System.getProperty("user.dir") + "/HTMLReports/" + "FreeListingReport.Html");
    	report.attachReporter(Reporter1);
    	Reporter1.config().setDocumentTitle("Free Listing Automation Test Results");
		Reporter1.config().setReportName("Test Report");
		Reporter1.config().setTestViewChartLocation(ChartLocation.TOP);
		Reporter1.config().setTimeStampFormat("MMM dd, yyyy HH:mm:ss");
    	driver.manage().timeouts().implicitlyWait(3,TimeUnit.SECONDS);
		obj = new Freelisting();
	}

	//Method for Launching FreeListing Page
	@Test(priority = 1)
	public void launch(){
		logger = report.createTest("Free Listing Page Launching",
				                                                      "Testing title of Free Listing Page");
		FindElement("CityBox_Id").clear();
		TypeText("CityBox_Id",prop.getProperty("location"));
		ButtonClick("locationbox_Xpath", 10);
		ButtonClick("FreeListingBtn_Xpath", 100);
		logger.info("Free Listing is opening");

		Assert.assertEquals(driver.getTitle(), "Free Listing - Just Dial - List In Your Business For Free");
		reportPass("Test case is Passed");
		takeScreenShots("Free Listing Page");
	}
	
	
	//This Method Enter all fields as Null in FreeListing Page
    @Test(priority=2)
    public void allNullValues() {
    	obj.elements();
    	logger = report.createTest("All NullValues","Testing Freelisting Page with all Null values");
    	obj.cityname.clear();
    	obj.submit.click();
    	logger.info("All fields are null");
  
    	String alert = driver.findElement(By.xpath("//span[@class='almsg']")).getText();
    	takeScreenShots("All Invalid");
		Assert.assertEquals("City is blank", alert);
		reportPass("Test case Is Passed");
		driver.navigate().refresh();
    }

    //This Method Keeps Company field as Null 
	@Test(priority = 3, dataProvider = "testData")
	public void companyNullValue(String company, String fname, String lname, String mobile, String land){
		obj.elements();
		logger = report.createTest("Company NullValue",
				                          "Testing Company Null Value Validation Of The Freelisting Page");
		obj.firstname.sendKeys(fname);
		obj.lastname.sendKeys(lname);
		obj.mobilenumber.sendKeys(mobile);
		obj.landline.sendKeys(land);
		obj.submit.click();
		logger.info("The Form values Are Entered With Company Value As Null");
		String alert = driver.findElement(By.xpath("//span[@class='almsg']")).getText();
		logger.info("The Error Message Is Shown In The Window");
		Assert.assertEquals("Company name is blank", alert);
		takeScreenShots("Invalid Company");
		reportPass("Test case Is Passed");
		driver.navigate().refresh();
	}

	//This Method keeps Mobile field as Null
	@Test(priority = 4, dataProvider = "testData")
	public void mobileNullValue(String company, String fname, String lname, String mobile, String land){
		obj.elements();
		logger= report.createTest("MobileNumber Nullvalue",
				                       "Testing MobileNumber Null Value Validation Of The FreelistingPage");
		obj.companyname.sendKeys(company);
		obj.firstname.sendKeys(fname);
		obj.lastname.sendKeys(lname);
		logger.info("The Form values Are Entered With MobileNumber As Null");
		obj.submit.click();
		driver.manage().timeouts().implicitlyWait(2,TimeUnit.SECONDS);
		
		logger.info("The Alert Is Displayed");
		
		try {
			String text = driver.switchTo().alert().getText();
			Assert.assertEquals("Please enter mobile number or landline number", text);
			//If alert is present accept
			driver.switchTo().alert().accept();
		}
		catch(NoAlertPresentException e){
			//if alert is not present 
			System.out.println("No alert found.");	
		}
		
		;
		reportPass("Test case Is Passed");
		driver.navigate().refresh();

	}

	//This method keeps City field as Null
	@Test(priority = 5, dataProvider = "testData")
	public void cityNullvalue(String company, String fname, String lname, String mobile, String land){
		obj.elements();
		logger= report.createTest("City Nullvalue",
				                               "Testing City Null Value Validation Of The FreelistingPage");
		obj.companyname.sendKeys(company);
		obj.cityname.clear();
		obj.firstname.sendKeys(fname);
		obj.lastname.sendKeys(lname);
		logger.info("The Form values Are Entered With City As Null");
		obj.submit.click();

	    logger.info("The Error Message Is Shown In The Window");
		String alert = driver.findElement(By.xpath("//span[@class='almsg']")).getText();
		takeScreenShots("Invalid City");
		Assert.assertEquals("City is blank", alert);
		reportPass("Test case Is Passed");
		driver.navigate().refresh();
	}
	
	//This method Enter all fields with valid data
	@Test(priority = 6, dataProvider = "testData")
	public void validDataTest(String company, String fname, String lname, String mobile, String land) {
		obj.elements();
		logger = report.createTest("Valid Data",
				                                       "Validation Of The FreelistingPage with valid data");
		obj.companyname.sendKeys(company);
		obj.firstname.sendKeys(fname);
		obj.lastname.sendKeys(lname);
		obj.mobilenumber.sendKeys(mobile);
		obj.landline.sendKeys(land);
		takeScreenShots("Valid data");
		obj.submit.click();	
		driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
		logger.info("The Form values Are Entered With all valid details");
		
		String actual=driver.getTitle();
		String expected="Free Listing - Just Dial - List In Your Business For Free";
		Assert.assertEquals(actual, expected);
		
		reportPass("Successfully registered and case is passed");
		
		
	}
	
	//Data Provider to all the used methods
	@DataProvider
	public Object[][] testData() throws Exception {
		return data("Testdata.xlsx");
	}

	//Method for closing Browser
	@AfterClass
	public void terminate() {
		driver.close();
		report.flush();
	}
}
