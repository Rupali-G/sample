package com.qa.tests;

import java.io.FileReader;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.opencsv.CSVReader;


import io.github.bonigarcia.wdm.WebDriverManager;

public class LoginTest {
	WebDriver driver;
	private By LoginInLink = By.xpath("//a[text()='Log in']");
	private By emailIDTextBox = By.xpath("//input[@id='Email']");
	private By passwordTextBox = By.xpath("//input[@id='Password']");
	private By loginBtn = By.xpath("//input[@value='Log in']");
	private By accountNameTxt = By.xpath("(//a[@class='account'])[1]");
	private By loginErrorMsg = By.xpath("//div[@class='validation-summary-errors']/ul/li");
	private By logoutLink = By.xpath("//a[text()='Log out']");

	@BeforeMethod
	public void setup() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("https://demowebshop.tricentis.com/");
	}

	@Test(priority = 1)
	public void verifyLoginCredentialsTest() {
		String scenario = null;
		String emailId = null;
		String password = null;
		String actualResult = null;
		try {
			CSVReader reader = new CSVReader(new FileReader(
					"D:\\Rupali\\Workspace\\SampleProject\\src\\main\\java\\TestData\\LoginCredentialsTestData.csv"));
			String cellData[];
			while ((cellData = reader.readNext()) != null) {
				if (!cellData[0].equalsIgnoreCase("scenario")) {

//				System.out.println(cellData[0]);
//				System.out.println(cellData[1]);
//				System.out.println(cellData[2]);
//				for (int i = 0; i < cellData.length; i++) {
					scenario = cellData[0];
					//System.out.println(scenario);
					emailId = cellData[1];
					//System.out.println(emailId);
					password = cellData[2];
					//System.out.println(password);
					
					driver.findElement(LoginInLink).click();
					Thread.sleep(2000);
					driver.findElement(emailIDTextBox).sendKeys(emailId);
					driver.findElement(passwordTextBox).sendKeys(password);
					driver.findElement(loginBtn).click();
					Thread.sleep(2000);
					//DateTimeUtil dt = new DateTimeUtil();
					
					//EmailUtils e = new EmailUtils();

					if (scenario.equalsIgnoreCase("bothcorrect")) {
						actualResult = driver.findElement(accountNameTxt).getText();
						driver.findElement(logoutLink).click();
						Assert.assertEquals(actualResult, "sonistar16@gmail.com");
					} else if (scenario.equalsIgnoreCase("bothwrong")) {
						actualResult = driver.findElement(loginErrorMsg).getText();
						Assert.assertEquals(actualResult, "The credentials provided are incorrect");
					} else if (scenario.equalsIgnoreCase("correctemail")) {
						actualResult = driver.findElement(loginErrorMsg).getText();
						Assert.assertEquals(actualResult, "The credentials provided are incorrect");
					} else if (scenario.equalsIgnoreCase("correctpassword")) {
						actualResult = driver.findElement(loginErrorMsg).getText();
						Assert.assertEquals(actualResult, "The credentials provided are incorrect");
					} else {
						System.out.println("Something is Wrong.");
					}
				}
//			}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
}
