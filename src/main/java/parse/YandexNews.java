package parse;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.sql.SQLException;
import java.util.List;

public class YandexNews implements Parse {
    WebDriver driver;
    JavascriptExecutor jse;

    @Override
    public void parse() throws SQLException {
        driver = new ChromeDriver();
        driver.get("https://yandex.ru/");

        List<WebElement> newsc = driver.findElement(By.id("tabnews_newsc")).findElement(By.tagName("ol")).findElements(By.tagName("a"));
        News mainNews = new News(newsc.size());
        newsc.forEach(news -> {
            try {
                mainNews.push(news.getAttribute("aria-label"), news.getAttribute("href"),"main");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
       // mainNews.print("Главные новости");

        driver.findElement(By.linkText("в Ростовской области")).click();

        List<WebElement> regionc = driver.findElement(By.id("tabnews_regionc")).findElement(By.tagName("ol")).findElements(By.tagName("a"));
        News regionNews = new News(regionc.size());
        regionc.forEach(news -> {
            try {
                regionNews.push(news.getAttribute("aria-label"), news.getAttribute("href"), "region");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
      //  regionNews.print("Региональные новости");
        driver.quit();
    }
}
