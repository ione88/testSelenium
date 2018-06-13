import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.internal.Locatable;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.List;

public class Main {

    DesiredCapabilities caps;
    WebDriver driver;
    String html;
    Document doc;
    JavascriptExecutor jse;

    public static void main(String[] args) throws Exception {

        Main exe = new Main();
        exe.init();
        exe.yandexNews();
        exe.yandexZen();
        exe.googleSearch("Cheese!");
        exe.quit();
    }

    public void init() throws Exception {

        caps = new DesiredCapabilities();
        caps.setJavascriptEnabled(true);
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,"c:/phantomjs.exe");
        driver = new PhantomJSDriver();

    }

    //Close the browser
    public void quit() throws Exception {

        driver.quit();
    }

    public void googleSearch(String search) throws Exception {
        driver.get("http://www.google.com");
        WebElement element = driver.findElement(By.name("q"));
        element.sendKeys(search);

        // Now submit the form. WebDriver will find the form for us from the element
        element.submit();

        // Check the title of the page
        System.out.println("Page title is: " + driver.getTitle());

        // Google's search is rendered dynamically with JavaScript.
        // Wait for the page to load, timeout after 10 seconds
        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.getTitle().toLowerCase().startsWith(search);
            }
        });

        // Should see: "cheese! - Google Search"
        System.out.println("Page title is: " + driver.getTitle());


    }

    public void yandexNews() throws Exception{

        driver.get("https://yandex.ru/");
        html = driver.getPageSource();

        doc = Jsoup.parse(html);

        Elements newsc = doc.select("div[id$=newsc]").select("ol[class$=list news__list]").select("li");

        print("Главные новости:");
        for (Element news : newsc) {
            print(news.text());
        }
        Elements regionc = doc.select("div[id$=regionc]").select("ol[class$=list news__list]").select("li");
        print("\nРегиональные новости:");
        for (Element news : regionc) {
            print(news.text());
        }

    }


    public void yandexZen() throws Exception{

        driver.get("https://yandex.ru/");
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        WebElement element = driver.findElement(By.linkText("Дзен"));
        jse.executeScript("arguments[0].scrollIntoView(true);",element);

        html = driver.getPageSource();
        doc = Jsoup.parse(html);

        print("\nДЗЕН");
        Elements feeds = doc.select("span[class$='i-multiline-overflow']");

        for (Element feed : feeds) {
            print(feed.text());
        }

    }

    private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }

}
