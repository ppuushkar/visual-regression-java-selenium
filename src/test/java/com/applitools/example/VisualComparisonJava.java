package com.applitools.example;

import com.applitools.eyes.selenium.Eyes;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;

public class VisualComparisonJava {
    public static void main(String[] args) {
        // Set up Chrome WebDriver
        System.setProperty("webdriver.chrome.driver", "C:\\Piyush Pushkar\\chrome.exe"); // Update this path
        ChromeDriverService service = new ChromeDriverService.Builder().build();
        WebDriver driver = new ChromeDriver(service, new ChromeOptions());

        // Initialize Eyes
        Eyes eyes = new Eyes();
        eyes.setApiKey("a8zuLxhOWkIovfdAvIC1mvsTjn9tGwtJPe79j63DTpo110");

        try {
            // Start the test
            eyes.open(driver, "Website Visual Comparison", "Test");

            // Navigate to the first website
            driver.get("https://www.privatebank.citibank.com/why-us");
            // Take a screenshot of the page
            eyes.checkWindow("Website 1");

            // Navigate to the second website
            driver.get("https://wwwqa.privatebank.citibank.com/why-us");
            // Take a screenshot of the page
            eyes.checkWindow("Website 2");

            // Close the test
            eyes.close();
        } finally {
            // Quit the WebDriver and close the eyes instance
            driver.quit();
            eyes.abortIfNotClosed();
        }
    }
}
