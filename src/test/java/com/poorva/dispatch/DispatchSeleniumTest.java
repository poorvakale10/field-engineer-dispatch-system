package com.poorva.dispatch;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import java.nio.file.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DispatchSeleniumTest {
  @LocalServerPort int port;
  WebDriver driver;

  @BeforeEach
  void setUp(){
    ChromeOptions o=new ChromeOptions();
    o.addArguments("--headless=new","--window-size=1440,1000");
    driver=new ChromeDriver(o);
  }

  @AfterEach
  void tearDown(){ if(driver!=null) driver.quit(); }

  String url(String path){ return "http://localhost:"+port+path; }

  @Test
  void loginPageLoads(){
    driver.get(url("/login"));
    assertTrue(driver.getTitle().contains("DispatchFlow"));
    assertTrue(driver.findElement(By.id("loginButton")).isDisplayed());
  }

  @Test
  void createDispatchJourney(){
    driver.get(url("/requests/new"));
    driver.findElement(By.id("customerName")).sendKeys("Selenium Customer");
    driver.findElement(By.id("location")).sendKeys("Mumbai");
    driver.findElement(By.id("issue")).sendKeys("Automated test issue");
    driver.findElement(By.id("engineer")).sendKeys("Test Engineer");
    driver.findElement(By.id("saveButton")).click();
    assertTrue(driver.getCurrentUrl().contains("/requests"));
    assertTrue(driver.getPageSource().contains("Selenium Customer"));
  }

  @Test
  void searchJourney(){
    driver.get(url("/requests?q=Mumbai"));
    assertTrue(driver.getPageSource().contains("Mumbai"));
  }

  // Reusable failure-screenshot mechanism for Task 10.
  // To demonstrate a deliberate defect, temporarily change the assertion below
  // to an incorrect condition, run the pipeline, capture the failure, then fix it.
  @Test
  void screenshotMechanismIsReady() throws Exception {
    driver.get(url("/"));
    try {
      assertTrue(driver.getTitle().contains("DispatchFlow"));
    } catch (AssertionError e) {
      Files.createDirectories(Path.of("selenium-screenshots"));
      Files.write(Path.of("selenium-screenshots/failure.png"),
          ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES));
      throw e;
    }
  }
}
