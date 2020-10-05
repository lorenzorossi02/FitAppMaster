package com.fitapp.test.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class SeleniumTestUserStreet{
	public String getStreet() {
		System.setProperty("webdriver.chrome.driver","src\\com\\fitapp\\Drivers\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("localhost:8100/FitAppMaster/index.jsp");
		driver.findElement(By.xpath("//*[@id=\"username\"]")).sendKeys("effi");
		driver.findElement(By.xpath("//*[@id=\"password\"]")).sendKeys("pass");
		driver.findElement(By.xpath("//*[@id=\"Login\"]")).click();
		WebElement streetContent = driver.findElement(By.xpath("//*[@id=\"userStreet\"]"));
		String street = streetContent.getText();
		driver.close();
		return street;
			
	}
	

}
