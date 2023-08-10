import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class HTMLComparisonJava {
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

        // Get HTML source from both websites
        String html1 = getHTMLSource(driver, url1);
        String html2 = getHTMLSource(driver, url2);

        // Close the WebDriver
        driver.quit();

        // Split HTML source into lines
        String[] lines1 = html1.split("\n");
        String[] lines2 = html2.split("\n");

        // Generate an HTML report with the differences highlighted
        StringBuilder htmlReport = new StringBuilder();
        htmlReport.append("<!DOCTYPE html>\n");
        htmlReport.append("<html>\n<head>\n");
        htmlReport.append("  <title>HTML Comparison Report</title>\n");
        htmlReport.append("  <style>\n");
        htmlReport.append("    .diff { background-color: #FF9999; }\n");
        htmlReport.append("  </style>\n</head>\n<body>\n");
        htmlReport.append("  <h1>HTML Comparison Report</h1>\n");

        htmlReport.append("  <table>\n");
        for (int i = 0; i < Math.min(lines1.length, lines2.length); i++) {
            if (!lines1[i].equals(lines2[i])) {
                htmlReport.append("    <tr>\n");
                htmlReport.append("      <td class=\"diff\">" + lines1[i] + "</td>\n");
                htmlReport.append("      <td class=\"diff\">" + lines2[i] + "</td>\n");
                htmlReport.append("    </tr>\n");
            }
        }
        htmlReport.append("  </table>\n");

        htmlReport.append("</body>\n</html>");

        // Write the HTML report to a file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("html_comparison_report.html"))) {
            writer.write(htmlReport.toString());
            System.out.println("HTML comparison report generated.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void acceptCookies(WebDriver driver, String url) {
        driver.get(url);
        // Add code to locate and interact with the cookie consent banner or element
        // You need to adapt this step based on how the cookie consent is implemented on the webpage.
    }

    public static String getHTMLSource(WebDriver driver, String url) {
        driver.get(url);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return driver.getPageSource();
    }
}
