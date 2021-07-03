package com.justdial.testcases;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.justdial.homepage.Homepage;

public class HomepageTestCases extends Homepage{

        //Browser Calling and Report Generation
        @BeforeClass
        public void Browser() {
        	loadproperties();
        	setup(prop.getProperty("browser"), prop.getProperty("baseUrl"));
        	ExtentHtmlReporter Reporter1=new ExtentHtmlReporter(System.getProperty("user.dir") + "/HTMLReports/" + "HomePageReport.Html");
        	report.attachReporter(Reporter1);
        	Reporter1.config().setDocumentTitle("HomePage Automation Results");
			Reporter1.config().setReportName("Test Report");
			Reporter1.config().setTestViewChartLocation(ChartLocation.TOP);
			Reporter1.config().setTimeStampFormat("MMM dd, yyyy HH:mm:ss");
        	driver.manage().timeouts().implicitlyWait(3,TimeUnit.SECONDS);
    		
        }
        
        //Method to check whether WebSite is Opening or not
        @Test(priority=1)
        public void websiteTest() {
        	logger=report.createTest("Website validation", "Test for checking website Functioning");
        	String actual=driver.getTitle();
        	String expected="Justdial - Local Search, Social, News, Videos, Shopping";
        	Assert.assertEquals(actual, expected);
        	reportPass("Website is opened");
        	takeScreenShots("Website Home");
        }
        
        //Method to check The Presence of Elements in justdial.com
        
        @Test(priority=2)
        public void elementsTest() {
        	logger=report.createTest("Validating Elements in homepage", 
        			           "Test for checking City,Search and Search box elements");
        	Assert.assertTrue(FindElement("CityBox_Id").isDisplayed());
        	logger.info("City box is Present");
        	Assert.assertTrue(FindElement("searchbox_Id").isDisplayed());
        	logger.info("Search box is Present");
        	Assert.assertTrue(FindElement("searchbtn_Id").isDisplayed());
        	logger.info("Search button is Present");
        	reportPass("All required elements are present and case is passed");
        	
        	
        }
        
        //This Method is used to Check Login Pop-up
        @Test(priority=3)
        public void loginPageTest() {
        	logger=report.createTest("Login page validation", "Test for checking login popup");
        	ButtonClick("loginBtn_Id",1);
        	logger.info("login is clicked");
        	takeScreenShots("Login Box");
        	Assert.assertTrue(FindElement("loginPop_ClassName").isDisplayed());
        	driver.navigate().refresh();
        	reportPass("login popup is displayed and case is passed");
        	
        }
        
        //This Method is used To check Location
        @Test(priority=4)
        public void locationTest() {
        	logger=report.createTest("Validating Location", 
        		                   	"Test for checking whether location can be changeable or not");
        	FindElement("CityBox_Id").clear();
    		logger.info("City box is clicked");
    		TypeText("CityBox_Id",prop.getProperty("location"));
    		ButtonClick("locationbox_Xpath",1);
    		takeScreenShots("City Entered");
    		logger.info(prop.getProperty("location")+" Location is entered");
    		
    		String expected=prop.getProperty("location");
    		driver.navigate().refresh();
    		String actual=FindElement("CityBox_Id").getAttribute("placeholder");
    		Assert.assertEquals(actual,expected);
    		reportPass("Location is as expected and case is passed");

        }
        
        //This method is used to Enter all search details
        @Test(priority=5,dependsOnMethods="locationTest")
        public void searchingTest() {
        	logger=report.createTest("Entering Search details",
        			                      "Test for entering details related to search");
        	TypeText("searchbox_Id",prop.getProperty("service"));
        	takeScreenShots("Service Entered");
        	logger.info(prop.getProperty("service")+" is entered in searchbox");
        	ButtonClick("searchbtn_Id",1);
        	logger.info("search button is clicked");
        	Assert.assertTrue(true);
        	reportPass("All entered details are taken and case is passed");
        }
        
        //This method is used to check Result page
        @Test(priority=6,dependsOnMethods="searchingTest")
        public void resultPageTest() {
    		logger= report.createTest("Result Page Validation",
    				             "Test To Validate The Title of SearchResultPage");
    		String actual = FindElement("ResultPageTiltle_tagName").getText();
    		String expected = prop.getProperty("service") + " in " + prop.getProperty("location");
    		Assert.assertEquals(actual, expected);
    		reportPass("Result page is as expected and case is passed");
    		takeScreenShots("Search Result page");
    	}
        
        //This Method is used to check Best deals Pop-up
        @Test(priority=7,dependsOnMethods="resultPageTest")
        public void bestdealsTest() {
        	logger=report.createTest("validating BestDeals Popup", 
        			                               "Test to check Bestdeals in ResultPage");
        	ButtonClick("BestDeal_Xpath",5);
        	logger.info("Bestdeals button is clicked");  
        	takeScreenShots("Bestdeals POpup");
    	    String text=FindElement("BestDealPop_Xpath").getText();
    		Assert.assertTrue(text.contains(prop.getProperty("service")));
    		logger.info("Best Deals Popup Is Displayed");
    		ButtonClick("BestDealCancel_Xpath",1);
    		reportPass("Best Deals Filter Test Case Is Passed");
    		

        }
        
        //This Method is used to check Votes and Rating
        @Test(priority=8,dependsOnMethods="bestdealsTest")
        public void ratingCheck() {
        	logger=report.createTest("Validating Stars and Votes", 
        			                              "Test for checking the presence of stars and votes");
        	Boolean rating=FindElement("Ratings_Xpath").isDisplayed();
            Boolean votes=FindElement("Votes_Xpath").isDisplayed();
            Assert.assertTrue(rating);
            logger.info("Ratings are present");
            Assert.assertTrue(votes);
            logger.info("Votes are present");
            reportPass("Stars,Votes are validated and Case is passed");
           
        }
        
        //This method is used to print data 
        @Test(priority=9,dependsOnMethods="ratingCheck")
        public void printData() {
        	logger=report.createTest("Validating Print data",
        			                                            "Test to display top 5 Car Wash Services");
        	FindElement("RatingUpBtn_Id").click();
        	takeScreenShots("Rating wise Result");
        	ArrayList<String> phone=new ArrayList<String>();
    	    List<WebElement> shop = FindElementList("shopNameList_Xpath");
    	    List<WebElement> contact=FindElementList("ContactList_Xpath");
    		List<WebElement> star =FindElementList("RatingList_Xpath");
    		List<WebElement> votes =FindElementList("VoteList_Xpath");
    		
    		int totalpassed=0;
    		for (int i = 0; i < star.size(); i++) {
    			List<WebElement> num=contact.get(i).findElements(By.xpath("//*[@id="
                                            +"'"+"bcard"+i+"'"+"]/div[1]/section/div[1]/p[2]/span/a/span"));
                ArrayList<String> numlist = new ArrayList<String>();

                for(int j=0;j<num.size();j++) {
                String s=num.get(j).getAttribute("class").split("-")[1];
                String digits=digit(s);
                numlist.add(digits);
                }

                phone.add(String.join("",numlist));
    
    			float ratings = Float.parseFloat(star.get(i).getText());
    			String numeric[] = votes.get(i).getText().split(" ");
    			int vote = Integer.parseInt(numeric[0]);
    			if(totalpassed<5) {
    			if (ratings > 4 && vote > 20) {
    				System.out.println(i + 1 + ". " + shop.get(i).getText() + "|| " + ratings + "|| "
    						+ votes.get(i).getText());
    				System.out.println("   Contact Number : "+phone.get(i));
    				totalpassed++;
    			} 
    		}
        }
    		System.out.println("--------------------------------------------------------------");
    		reportPass("Top 5 Car Wash Services are displayed");
    		
        }
        
       //This Method is used to check result page from Menu-bar
       @Test(priority=10)
       public void dataFromMenubar() {
       	logger=report.createTest("Validating data from menu bar", 
       			                                      "Test to check result Page from menubar");
       	ButtonClick("JustDialLogo_Xpath",2);
       	takeScreenShots("HomePage");
       	FindElement("CityBox_Id").clear();
   		logger.info("City box is clicked");
   		TypeText("CityBox_Id",prop.getProperty("location"));

   		
   		logger.info("Location is selected");
   		ButtonClick("locationbox_Xpath",2);
   		
   		FindElement("AutomobileMenu_Id").click();
   		logger.info("Automobile is selected in menubar");

   		takeScreenShots("Automobile Page");
   		ButtonClick("Carservice_Xpath",1);
   		takeScreenShots("Cars services");
   		logger.info("Car Services is selected");
   		String title="Car Repair & Services in "+prop.getProperty("location");
   		Assert.assertTrue(driver.getTitle().contains(title));
   		reportPass("Result Page is as expected and case is passed");
   		
       }
       
       //Method for closing Browser
        @AfterClass
    	public void terminate() {
    		driver.close();
    		report.flush();
    	}

       
}
