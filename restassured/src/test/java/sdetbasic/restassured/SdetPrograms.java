package sdetbasic.restassured;

import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SdetPrograms {
	
	WebDriver driver;
	
	@BeforeMethod(alwaysRun = true)
	public void initialize() {
		System.out.println("Initializing WebDriver...");
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\divya\\Documents\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://nocode.autify.com/");
	}
	
	@Test(groups = "WindowHandling")
	public void testWindowHandles() throws InterruptedException {
		String parentWindow = driver.getWindowHandle();
		
        // Click Start Free Trial
		WebElement freeTrialLink = driver.findElement(By.xpath("//a[@class=\"white-button-aut w-button\"]"));
		Actions actions = new Actions(driver);
		actions.keyDown(Keys.CONTROL).click(freeTrialLink).keyUp(Keys.CONTROL).build().perform();
        Thread.sleep(2000); // Let the new tab open

        // Switch to new window
        Set<String> allWindows = driver.getWindowHandles();
        Iterator<String> iterator = allWindows.iterator();

        String childWindow = null;
        while (iterator.hasNext()) {
            String window = iterator.next();
            if (!window.equals(parentWindow)) {
                childWindow = window;
                break;
            }
        }

        driver.switchTo().window(childWindow);
        Thread.sleep(2000);

        // Verify Title
        String expectedTitle = "Trial Application - Autify";
        String actualTitle = driver.getTitle();
        Assert.assertEquals(actualTitle, expectedTitle, "Title does not match!");

        // Close only child window
        driver.close();

        // Switch back to parent
        driver.switchTo().window(parentWindow);
		
	}
	
	@Test(groups = "ValidateForm")
    public void testSignUpErrorMessages() throws InterruptedException {
        String parentWindow = driver.getWindowHandle();

        // Click Start Free Trial
        driver.findElement(By.xpath("//a[@class=\"white-button-aut w-button\"]")).click();
        Thread.sleep(2000);

        // Switch to new window
        Set<String> allWindows = driver.getWindowHandles();
        for (String win : allWindows) {
            if (!win.equals(parentWindow)) {
                driver.switchTo().window(win);
                break;
            }
        }

        // Click Sign Up button without filling the form
        WebElement signUpButton = driver.findElement(By.xpath("//button[@name=\"commit\"]"));
        signUpButton.click();
        Thread.sleep(1000);

        // Verify Error Messages
        WebElement firstNameError = driver.findElement(By.xpath("//span[text()=\"First name can't be blank\"]"));
        Assert.assertEquals(firstNameError.getText().trim(), "First name can't be blank");

        WebElement lastNameError = driver.findElement(By.xpath("//span[text()=\"Last name can't be blank\"]"));
        Assert.assertEquals(lastNameError.getText().trim(), "Last name can't be blank");

        WebElement companyNameError = driver.findElement(By.xpath("//span[text()=\"Company name can't be blank\"]"));
        Assert.assertEquals(companyNameError.getText().trim(), "Company name can't be blank");

        WebElement companySizeError = driver.findElement(By.xpath("//span[text()=\"Company size can't be blank\"]"));
        Assert.assertEquals(companySizeError.getText().trim(), "Company size can't be blank");

        WebElement emailError = driver.findElement(By.xpath("//span[text()=\"Email can't be blank\"]"));
        Assert.assertEquals(emailError.getText().trim(), "Email can't be blank");

        WebElement passwordError = driver.findElement(By.xpath("//span[text()=\"Password cannot be blank\"]"));
        Assert.assertEquals(passwordError.getText().trim(), "Password cannot be blank");

        WebElement phoneError = driver.findElement(By.xpath("//span[text()=\"Phone Number can't be blank\"]"));
        Assert.assertEquals(phoneError.getText().trim(), "Phone Number can't be blank");
        
        Thread.sleep(2000);
    }
	
	@AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

}
