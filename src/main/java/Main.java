import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;


public class Main {

    WebDriver driver;
    JavascriptExecutor jse;

    public static void main(String[] args) throws Exception {


        Main exe = new Main();

        exe.initChrome();
        exe.yandexNews();
        exe.yandexZen();
        exe.quit();
    }

    public void initChrome() throws Exception {

        driver = new ChromeDriver();
    }

    //Close the browser
    public void quit() throws Exception {

        driver.quit();
    }

    public void yandexNews() throws Exception {

        driver.get("https://yandex.ru/");
        List<WebElement> newsc = driver.findElement(By.id("tabnews_newsc")).findElement(By.tagName("ol")).findElements(By.tagName("a"));

        News mainNews = new News(newsc.size());
        newsc.forEach(news -> mainNews.push(news.getAttribute("aria-label"),news.getAttribute("href")));
        mainNews.print("Главные новости");
        driver.findElement(By.linkText("в Ростовской области")).click();

        List<WebElement> regionc = driver.findElement(By.id("tabnews_regionc")).findElement(By.tagName("ol")).findElements(By.tagName("a"));


        News regionNews = new News(regionc.size());
        regionc.forEach(news -> regionNews.push(news.getAttribute("aria-label"),news.getAttribute("href")));
        regionNews.print("Региональные новости");
    }


    public void yandexZen() throws Exception {

        driver.get("https://yandex.ru/");
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        WebElement element = driver.findElement(By.linkText("Дзен"));
        jse.executeScript("arguments[0].scrollIntoView(true);", element);

        (new WebDriverWait(driver, 20))
                .until(ExpectedConditions.presenceOfElementLocated(By.className("feed__row")));

        List<WebElement> feeds = driver.findElements(By.className("doc__link"));

        News zenNews = new News(feeds.size());
        feeds.forEach(news -> zenNews.push(news.findElement(By.className("clamp__visible-tokens")).getText(),news.getAttribute("href")));
        zenNews.print("Лента Дзен");
    }


}
