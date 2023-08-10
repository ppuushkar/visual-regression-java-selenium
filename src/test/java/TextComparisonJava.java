import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TextComparisonJava {
    public static void main(String[] args) {
        // URLs of the web pages to compare
        String url1 = "https://www.privatebank.citibank.com/why-us";
        String url2 = "https://wwwqa.privatebank.citibank.com/why-us";

        // Set up ChromeOptions and WebDriver
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // Run Chrome in headless mode
        WebDriver driver = new ChromeDriver(options);

        // Accept cookies
        acceptCookies(driver, url1);
        acceptCookies(driver, url2);

        // Get visible text content from both websites
        String text1 = getVisibleText(driver, url1);
        String text2 = getVisibleText(driver, url2);

        // Close the WebDriver
        driver.quit();

        // Compare text content word by word
        String[] words1 = text1.split("\\s+");
        String[] words2 = text2.split("\\s+");

        // Generate an HTML report with the differences highlighted
        StringBuilder htmlReport = new StringBuilder();
        htmlReport.append("<!DOCTYPE html>\n");
        htmlReport.append("<html>\n<head>\n");
        htmlReport.append("  <title>Text Comparison Report</title>\n");
        htmlReport.append("  <style>\n");
        htmlReport.append("    .diff { background-color: #FF9999; }\n");
        htmlReport.append("  </style>\n</head>\n<body>\n");
        htmlReport.append("  <h1>Text Comparison Report</h1>\n");

        htmlReport.append("  <h2>Website 1</h2>\n");
        htmlReport.append("  <pre>\n");
        highlightDifferences(htmlReport, words1, words2);
        htmlReport.append("  </pre>\n");

        htmlReport.append("  <h2>Website 2</h2>\n");
        htmlReport.append("  <pre>\n");
        highlightDifferences(htmlReport, words2, words1);
        htmlReport.append("  </pre>\n");

        htmlReport.append("</body>\n</html>");

        // Write the HTML report to a file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("text_comparison_report.html"))) {
            writer.write(htmlReport.toString());
            System.out.println("Text comparison report generated.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void acceptCookies(WebDriver driver, String url) {
        driver.get(url);
        // Add code to locate and interact with the cookie consent banner or element
        // For example, you might use driver.findElement(By.id("cookie-accept-button")).click();
        // You need to adapt this step based on how the cookie consent is implemented on the webpage.
    }

    public static String getVisibleText(WebDriver driver, String url) {
        driver.get(url);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        WebElement bodyElement = driver.findElement(By.tagName("body"));
        return bodyElement.getText();
    }

    public static void highlightDifferences(StringBuilder htmlReport, String[] words1, String[] words2) {
        for (int i = 0; i < Math.min(words1.length, words2.length); i++) {
            if (!words1[i].equals(words2[i])) {
                htmlReport.append("<mark class=\"diff\">").append(words1[i]).append("</mark> ");
            } else {
                htmlReport.append(words1[i]).append(" ");
            }
        }
        if (words1.length > words2.length) {
            for (int i = words2.length; i < words1.length; i++) {
                htmlReport.append("<mark class=\"diff\">").append(words1[i]).append("</mark> ");
            }
        } else if (words2.length > words1.length) {
            for (int i = words1.length; i < words2.length; i++) {
                htmlReport.append("<mark class=\"diff\">").append(words2[i]).append("</mark> ");
            }
        }
        htmlReport.append("\n");
    }
}
