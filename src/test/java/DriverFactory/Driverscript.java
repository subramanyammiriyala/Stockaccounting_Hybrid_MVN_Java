package DriverFactory;

import java.io.File;

//import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import org.apache.commons.io.FileUtils;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import CommonFunLibrary.FunctionLibrary;
import Utilities.ExcelFileUtil;

public class Driverscript {
	ExtentReports report;
	ExtentTest test;
	WebDriver driver;
  @Test
  public void starttest() throws Exception {
	  ExcelFileUtil excel=new ExcelFileUtil();
	  for(int i=1;i<=excel.rowcount("MasterTestCases");i++){
		  String ModuleStatus="";
		  if(excel.getdata("MasterTestCases", i,2).equalsIgnoreCase("Y")){
			String TCModule=excel.getdata("MasterTestCases",i,1);
			report=new ExtentReports("E:\\subramanyam\\Stockaccounting_Hybrid\\Reports\\"+TCModule+FunctionLibrary.generateDate()+".html");
			for(int j=1;j<=excel.rowcount(TCModule);j++)
			{
				test=report.startTest(TCModule);
				String Dscription=excel.getdata(TCModule, j,0);
				String Function_Name=excel.getdata(TCModule, j,1);
				String Locator_type=excel.getdata(TCModule, j,2);
				String Locator_Value=excel.getdata(TCModule, j,3);
				String Test_Data=excel.getdata(TCModule, j,4);
				try{
				if(Function_Name.equalsIgnoreCase("startBrowser")){
					driver=FunctionLibrary.startBrowser(driver);
					test.log(LogStatus.INFO, Dscription);
				}
				if(Function_Name.equalsIgnoreCase("openApplication")){
					FunctionLibrary.openapplication(driver);
					test.log(LogStatus.INFO, Dscription);
				}
				if(Function_Name.equalsIgnoreCase("typeAction")){
					FunctionLibrary.typeAction(driver, Locator_type, Locator_Value, Test_Data);
					test.log(LogStatus.INFO, Dscription);
				}if(Function_Name.equalsIgnoreCase("clickAction")){
					FunctionLibrary.clickAction(driver, Locator_type, Locator_Value);
					test.log(LogStatus.INFO, Dscription);
				}
				if(Function_Name.equalsIgnoreCase("waitForElement")){
					FunctionLibrary.waitForElement(driver, Locator_type, Locator_Value, Test_Data);
					test.log(LogStatus.INFO, Dscription);
				}
				if(Function_Name.equalsIgnoreCase("closeBrowser")){
					FunctionLibrary.closeBrowser(driver);
					test.log(LogStatus.INFO, Dscription);
				}
				if(Function_Name.equalsIgnoreCase("captureData")){
					FunctionLibrary.captureData(driver,  Locator_type, Locator_Value);
					test.log(LogStatus.INFO, Dscription);
				}
				if(Function_Name.equalsIgnoreCase("tableValidation")){
					FunctionLibrary.tableValidation(driver, Test_Data);
					test.log(LogStatus.INFO, Dscription);
				}
			 ModuleStatus = "ture";
				excel.setdata(TCModule, j, 5, "pass");
			}
			catch(Exception e){
				ModuleStatus="false";
				File scrFile=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			    FileUtils.copyFile(scrFile, new File("E:\\subramanyam\\Stockaccounting_Hybrid_MVN\\Screenshots\\"+Dscription+FunctionLibrary.generateDate()));
			    excel.setdata(TCModule, j, 5, "FAIL");
			    break;
			}
			}
			if(ModuleStatus.equalsIgnoreCase("ture")){
				excel.setdata("MasterTestCases", i, 3, "PASS");
			}else if(ModuleStatus.equalsIgnoreCase("false")){
				excel.setdata("MasterTestCases", i, 3, "FAIL");
			}
			report.flush();
			report.endTest(test);
		  }
		  else{
			  excel.setdata("MasterTestCases", i, 3, "NOT Executed");
		  }
	  }
  }
}
