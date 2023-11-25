package com.example.esms;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.AfterTestClass;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class EsmsApplicationTests {
	WebDriver driver = null;
	@BeforeTestClass
	void init(){
		System.setProperty("webdriver.edge.driver", "C:\\edgedriver_win64\\msedgedriver.exe");
		driver = new EdgeDriver();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}

	@Test
	void TC1() throws InterruptedException {
		System.setProperty("webdriver.edge.driver", "C:\\edgedriver_win64\\msedgedriver.exe");
		driver = new EdgeDriver();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.get("http://10.0.21.79:3000/");
		WebElement usernameHole = driver.findElement(new By.ByXPath("//input[@placeholder='Example@email.com']"));
		WebElement passHole = driver.findElement(new By.ByXPath("//input[@placeholder='at least 8 characters']"));
		usernameHole.sendKeys("abcd");
		passHole.sendKeys("12345");
		driver.findElement(new By.ByXPath("//button[@class='main-button']")).click();
//		driver.wait(driver);
		assertEquals("Admin",driver.findElement(new By.ByXPath("(//b[@class='uname2'])[1]")).getText());
	}
	@AfterTestClass
	void close(){
		driver.close();
	}

}
