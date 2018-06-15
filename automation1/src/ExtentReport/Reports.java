package ExtentReport;

import org.testng.annotations.Test;

//import com.aventstack.extentreports.ExtentReporter;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;

public class Reports {
	
	ExtentHtmlReporter htmlReporter;
	ExtentReports extent;
	ExtentTest test;
	
	@BeforeTest
	  public void startReport() {
		  htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir")+"/test-output/MyOwnReport.html");
			extent = new ExtentReports();
			extent.attachReporter(htmlReporter);
			
			extent.setSystemInfo("OS", "Windows");
			extent.setSystemInfo("HostName", "Parth");
			extent.setSystemInfo("Environment", "QA");
			extent.setSystemInfo("User Name", "Parth Chaturvedi");
			
			htmlReporter.config().setDocumentTitle("AutomationTesting.in Demo Report");
			htmlReporter.config().setReportName("My Own Report");
			htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
			htmlReporter.config().setTheme(Theme.STANDARD);
	  }
	
	@Test
	public void demoTestPass()
	{
		test=extent.createTest("demoTestPass", "This test case will Demonstrate the Pass Test case");
		Assert.assertTrue(true);
	}
    
	@Test
	public void demoTestFail()
	{
		test=extent.createTest("demoTestFail", "This test case will demonstrte the Fail Test case");
		Assert.assertTrue(false);
	}
	@Test
	public void demoTestSkip()
	{
		test=extent.createTest("demoTestSkip", "This test case will demonstrate the Skipped Test case");
		throw new SkipException("This test case cannot be executed");
	}
  @AfterMethod
  public void getResult(ITestResult result)
	{
		if(result.getStatus()==ITestResult.FAILURE)
		{
			test.log(Status.FAIL, MarkupHelper.createLabel(result.getName()+"Test case failed due to below issues", ExtentColor.RED));
			test.fail(result.getThrowable());
		}
		else if(result.getStatus()==ITestResult.SUCCESS)
		{
			test.log(Status.PASS, MarkupHelper.createLabel(result.getName()+"Test case Passed", ExtentColor.GREEN));
		}
		else
		{
			test.log(Status.SKIP, MarkupHelper.createLabel(result.getName()+"Test case was skipped", ExtentColor.YELLOW));
			test.skip(result.getThrowable());
		}
	}

  

  @AfterTest
  public void tearDown()
	{
		extent.flush();
	}

}
