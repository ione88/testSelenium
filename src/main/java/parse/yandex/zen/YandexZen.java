package parse.yandex.zen;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import parse.yandex.News;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class YandexZen implements Parser {
    WebDriver driver;
    JavascriptExecutor jse;

    @Override
    public Object[] parser(String userCity) throws SQLException {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://yandex.ru/");
        jse = (JavascriptExecutor)driver;
        jse.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.linkText("Дзен")));

        (new WebDriverWait(driver, 20))
                .until(ExpectedConditions.presenceOfElementLocated(By.className("feed__row")));

        List<WebElement> feeds = driver.findElements(By.className("doc__link"));

        ArrayList<News> newsfeed = new ArrayList<News>();
        feeds.forEach(webnews -> {
            News news = new News();
            news.setUrl(webnews.getAttribute("href"));
            news.setTitle(webnews.findElement(By.className("clamp__visible-tokens")).getText());
            news.setTypeOfNews("zen");
            newsfeed.add(news);
        });

        driver.quit();
        return newsfeed.toArray();
    }
}
