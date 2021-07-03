package com.justdial.homepage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

import org.testng.Assert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class Baseclass {
	public static WebDriver driver;
	public XSSFWorkbook wb;
	public static Properties prop;
	public ExtentReports report= ExtentReportManager.getReportInstance();
	public ExtentTest logger;
	public String path=System.getProperty("user.dir");
	//Method for loading properties file
	public void loadproperties() {
		try {
			prop = new Properties();
			InputStream in = new FileInputStream(path + "\\src\\test\\resources\\ObjectRepository\\config.properties");
			prop.load(in);
		} catch (Exception e) {

		}
	}

	//browser setup method
	public void setup(String browser, String url) {

		String path = System.getProperty("user.dir");
		if (browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", path + "\\src\\test\\resources\\Drivers\\chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--disable-notifications");
			options.addArguments("--disable-blink-features=AutomationControlled");
			driver = new ChromeDriver(options);
		} else if (browser.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver", path + "\\src\\test\\resources\\Drivers\\geckodriver.exe");
			FirefoxOptions options = new FirefoxOptions();
			options.setProfile(new FirefoxProfile());
			options.addPreference("dom.webnotifications.enabled", false);
			driver = new FirefoxDriver(options);
		}
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(url);
		driver.manage().window().maximize();

	}
	
	
		
		 //Data Providing method 
		public Object[][] data(String fileName) throws Exception {
			String path = System.getProperty("user.dir");
			FileInputStream fis = new FileInputStream(path + "\\src\\test\\resources\\ExcelFiles\\" + fileName);
			wb = new XSSFWorkbook(fis);
			XSSFSheet sh = wb.getSheetAt(0);
			int rows = sh.getPhysicalNumberOfRows();
			int cols = sh.getRow(0).getPhysicalNumberOfCells();
			Object arrayobj[][] = new Object[rows - 1][cols];
			for (int i = 1; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					arrayobj[i - 1][j] = getData(0, i, j);
				}
			}
			fis.close();
			wb.close();
			return arrayobj;
		}
	    
		//Retrieving data from excel file
		public String getData(int sheetnumber, int row, int column) {
			XSSFSheet sheet = wb.getSheetAt(sheetnumber);
			DataFormatter df = new DataFormatter();
			String data = df.formatCellValue(sheet.getRow(row).getCell(column));
			// String data = String.valueOf(sheet.getRow(row).getCell(column));
			return data;
		}
		
		//Method Used for getting digits from resultPage
		public String digit(String s) {
	    	String k;
	    	switch(s) { 
	    		    case "dc": 
	    	        	k="+";
	    	            break;
	    	        case "fe": 
	    	        	k="(";
	    	            break;
	    	        case "hg": 
	    	        	k=")";
	    	            break;
	    	        case "ba": 
	    	        	k="-";
	    	            break;
	    	        case "acb":
	    	        	k="0";
	    	            break;
	    	        case "yz": 
	    	        	k="1";
	    	            break;
	    	        case "wx": 
	    	        	k="2";
	    	            break;
	    	        case "vu": 
	    	        	k="3";
	    	        	break;
	    	        case "ts": 
	    	        	k="4";
	    	            break;
	    	        case "rq": 
	    	        	k="5";
	    	            break;
	    	        case "po": 
	    	        	k="6";
	    	            break;
	    	        case "nm": 
	    	        	k="7";
	    	            break;
	    	        case "lk": 
	    	        	k="8";
	    	            break;
	    	        case "ji": 
	    	        	k="9";
	    	           break;
	    	        default:
	    	        	k="invalid";
	    	    } 
	    	return k;
	    }
		/****************** Capture Screen Shot ***********************/
		public void takeScreenShots(String text) {
			TakesScreenshot takeScreenShot = (TakesScreenshot) driver;
			File sourceFile = takeScreenShot.getScreenshotAs(OutputType.FILE);

			File destFile = new File(path+"/ScreenShots/" +text +".png");
			try {
				FileUtils.copyFile(sourceFile, destFile);
				logger.addScreenCaptureFromPath(path+"/ScreenShots/" +text +".png");

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
			public void reportFail(String reportString) {
				logger.log(Status.FAIL, reportString);
				takeScreenShots(reportString);
				Assert.fail(reportString);
			}

			public void reportPass(String reportString) {
				logger.log(Status.PASS, reportString);
				
			}
			
			

}
