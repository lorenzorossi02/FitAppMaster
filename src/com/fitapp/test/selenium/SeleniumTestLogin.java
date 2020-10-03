package com.fitapp.test.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class SeleniumTestLogin {
//Lorenzo Rossi
	public String testLogin(String username, String password) {
		System.setProperty("webdriver.chrome.driver","src\\com\\fitapp\\Drivers\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		
		driver.get("localhost:8100/FitAppMaster/index.jsp");
		driver.findElement(By.xpath("//*[@id=\"username\"]")).sendKeys(username);
		driver.findElement(By.xpath("//*[@id=\"password\"]")).sendKeys(password);
		driver.findElement(By.xpath("//*[@id=\"Login\"]")).click();
		WebElement usernameContent = driver.findElement(By.xpath("//*[@id=\"managerUsername\"]"));
		String user = usernameContent.getText();
		driver.close();
		return user;
	}

}
