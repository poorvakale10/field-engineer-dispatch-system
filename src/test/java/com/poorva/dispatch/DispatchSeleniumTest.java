
package com.poorva.dispatch;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DispatchSeleniumTest {

    @LocalServerPort
    int port;

    WebDriver driver;

    @BeforeEach
    void setUp() {
        // Use the ChromeDriver installed on this computer.
        System.setProperty(
            "webdriver.chrome.driver",
            "C:\\Selenium\\chromedriver.exe"
        );

        ChromeOptions options = new ChromeOptions();
        options.addArguments(
            "--headless=new",
            "--window-size=1440,1000"
        );

        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    String url(String path) {
        return "http://localhost:" + port + path;
    }

    @Test
    void loginPageLoads() {
        driver.get(url("/login"));

        assertTrue(
            driver.getTitle().contains("DispatchFlow"),
            "Login page title should contain DispatchFlow"
        );

        assertTrue(
            driver.findElement(By.id("loginButton")).isDisplayed(),
            "Login button should be visible"
        );
    }

    @Test
    void createDispatchJourney() {
        driver.get(url("/requests/new"));

        driver.findElement(By.id("customerName"))
              .sendKeys("Selenium Customer");

        driver.findElement(By.id("location"))
              .sendKeys("Mumbai");

        driver.findElement(By.id("issue"))
              .sendKeys("Automated test issue");

        driver.findElement(By.id("engineer"))
              .sendKeys("Test Engineer");

        driver.findElement(By.id("saveButton")).click();

        assertTrue(
            driver.getCurrentUrl().contains("/requests"),
            "After saving, the application should navigate to requests"
        );

        assertTrue(
            driver.getPageSource().contains("Selenium Customer"),
            "The created customer should appear on the requests page"
        );
    }

    @Test
    void searchJourney() {
        driver.get(url("/requests?q=Mumbai"));

        assertTrue(
            driver.getPageSource().contains("Mumbai"),
            "Search results should contain Mumbai"
        );
    }

    // Captures a screenshot if the home-page assertion fails.
    @Test
    void screenshotMechanismIsReady() throws Exception {
        driver.get(url("/"));

        try {
            assertTrue(
                driver.getTitle().contains("DispatchFlow"),
                "Home page title should contain DispatchFlow"
            );
        } catch (AssertionError error) {
            Path screenshotDirectory = Path.of("selenium-screenshots");
            Files.createDirectories(screenshotDirectory);

            Path screenshotFile =
                screenshotDirectory.resolve("failure.png");

            Files.write(
                screenshotFile,
                ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.BYTES)
            );

            throw error;
        }
    }
}
