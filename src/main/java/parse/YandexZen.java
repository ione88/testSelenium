package parse;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.SQLException;
import java.util.List;

public class YandexZen implements Parse {
    WebDriver driver;
    JavascriptExecutor jse;

    @Override
    public void parse() throws SQLException {
        driver = new ChromeDriver();
        jse = (JavascriptExecutor)driver;

        driver.get("https://yandex.ru/");
        WebElement element = driver.findElement(By.linkText("Дзен"));
        jse.executeScript("arguments[0].scrollIntoView(true);", element);

        (new WebDriverWait(driver, 20))
                .until(ExpectedConditions.presenceOfElementLocated(By.className("feed__row")));

        List<WebElement> feeds = driver.findElements(By.className("doc__link"));

        News zenNews = new News(feeds.size());
        feeds.forEach(news -> {
            try {
                zenNews.push(news.findElement(By.className("clamp__visible-tokens")).getText(), news.getAttribute("href"),"zen");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        zenNews.print("Лента Дзен");
        driver.quit();
    }
}
