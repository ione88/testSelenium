package parse.yandex.news;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import parse.yandex.News;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class YandexNews implements Parser {
    WebDriver driver;
    JavascriptExecutor jse;

    @Override
    public Object[] parser(String userCity) throws SQLException {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://yandex.ru/");

        ArrayList<News> newsfeed = new ArrayList<News>();

        List<WebElement> mainnewsfeed = driver.findElements(By.xpath("//div[contains(@class,'content-tabs__items_active_true')]//ol[not(contains(@class,'news__animation-list'))]//a"));

        mainnewsfeed.forEach(mainnews -> {
            News news = new News();
            news.setUrl(mainnews.getAttribute("href"));
            news.setTitle(mainnews.getAttribute("aria-label"));
            news.setTypeOfNews("main");
            newsfeed.add(news);
        });


        driver.findElement(By.linkText("в Ростовской области")).click();

        List<WebElement> regionnewsfeed = driver.findElements(By.xpath("//div[contains(@class,'content-tabs__items_active_true')]//ol[not(contains(@class,'news__animation-list'))]//a"));
        regionnewsfeed.forEach(regionnews -> {
            News news = new News();
            news.setUrl(regionnews.getAttribute("href"));
            news.setTitle(regionnews.getAttribute("aria-label"));
            news.setTypeOfNews("region");
            newsfeed.add(news);
        });

        driver.quit();
        return newsfeed.toArray();

    }
}
