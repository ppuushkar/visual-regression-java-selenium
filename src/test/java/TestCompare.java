import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class TestCompare {
    public static void main(String[] args) {
        // URLs of the web pages to compare
        String url1 = "https://www.privatebank.citibank.com/why-us";
        String url2 = "https://wwwqa.privatebank.citibank.com/why-us";

        // Set up FirefoxOptions and WebDriver
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--headless"); // Run Firefox in headless mode
        WebDriver driver = new FirefoxDriver(options);

        // Get text from both websites
        String text1 = getTextFromURL(driver, url1);
        String text2 = getTextFromURL(driver, url2);

        // Close the WebDriver
        driver.quit();

        // Generate HTML report with differences highlighted
        String htmlReport = generateHTMLReport(text1, text2);

        // Write the HTML report to a file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("text_comparison_report.html"))) {
            writer.write(htmlReport);
            System.out.println("Text comparison report generated.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getTextFromURL(WebDriver driver, String url) {
        driver.get(url);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        String pageText = driver.getPageSource();
        return pageText;
    }

    public static String generateHTMLReport(String text1, String text2) {
        String[] lines1 = text1.split("\n");
        String[] lines2 = text2.split("\n");

        StringBuilder htmlReport = new StringBuilder();
        htmlReport.append("<!DOCTYPE html>\n");
        htmlReport.append("<html>\n<head>\n");
        htmlReport.append("  <title>Text Comparison Report</title>\n");
        htmlReport.append("  <style>\n");
        htmlReport.append("    .diff { background-color: #FF9999; }\n");
        htmlReport.append("  </style>\n</head>\n<body>\n");
        htmlReport.append("  <h1>Text Comparison Report</h1>\n");

        htmlReport.append("  <h2>URL 1: Summary</h2>\n");
        htmlReport.append("  <pre>\n");
        htmlReport.append(lines1.length + " lines in total\n");
        htmlReport.append("</pre>\n");

        htmlReport.append("  <h2>URL 2: Summary</h2>\n");
        htmlReport.append("  <pre>\n");
        htmlReport.append(lines2.length + " lines in total\n");
        htmlReport.append("</pre>\n");

        htmlReport.append("  <h2>Differences</h2>\n");
        htmlReport.append("  <table>\n");
        htmlReport.append("    <tr>\n");
        htmlReport.append("      <th>URL 1</th>\n");
        htmlReport.append("      <th>URL 2</th>\n");
        htmlReport.append("    </tr>\n");
        for (int i = 0; i < Math.max(lines1.length, lines2.length); i++) {
            boolean isDifferent = (i < lines1.length && i < lines2.length) && !lines1[i].equals(lines2[i]);
            htmlReport.append("    <tr>\n");
            htmlReport.append("      <td " + (isDifferent ? "class=\"diff\"" : "") + ">" + (i < lines1.length ? lines1[i] : "") + "</td>\n");
            htmlReport.append("      <td " + (isDifferent ? "class=\"diff\"" : "") + ">" + (i < lines2.length ? lines2[i] : "") + "</td>\n");
            htmlReport.append("    </tr>\n");
        }
        htmlReport.append("  </table>\n");

        htmlReport.append("</body>\n</html>");
        return htmlReport.toString();
    }
}
