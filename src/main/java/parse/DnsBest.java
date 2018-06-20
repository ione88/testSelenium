package parse;

import com.google.gson.Gson;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DnsBest implements Parse {

    WebDriver driver;
    JavascriptExecutor jse;

    @Override
    public void parse() throws SQLException {
        driver = new ChromeDriver();
        driver.get("https://www.dns-shop.ru/");

        List<WebElement> bestOffers = driver.findElement(By.className("shopwindow-products")).findElements(By.xpath("//a[@data-product-param='name']"));
        ArrayList<String> urls = new ArrayList<String>();
        bestOffers.forEach(offer -> urls.add(offer.getAttribute("href")));

        urls.forEach(url -> {
            driver.get(url);
            Product product = new Product();

            (new WebDriverWait(driver, 20))
                    .until(ExpectedConditions.presenceOfElementLocated(By.className("price-item-description")));
            product.setUrl(url);
            //Наименование - строка
            product.setName(driver.findElement(By.className("price-item-title")).getText());
            //Код товара – уникальное поле, целое
            product.setCode(Integer.parseInt(driver.findElement(By.className("price-item-code")).findElement(By.tagName("span")).getText()));
            //Цена - целое
            product.setPrice(Integer.parseInt(driver.findElement(By.className("price_g")).findElement(By.tagName("span")).getAttribute("data-price-value")));
            //Описание – строка
            product.setDescription(driver.findElement(By.id("description")).findElement(By.tagName("p")).getText());

            //считывает Характеристики в объект и превращаем его в gson строку продукты
            driver.findElement(By.linkText("Характеристики")).click();
            List<WebElement> parametrs = driver.findElement(By.id("characteristics")).findElements(By.tagName("tr"));
            ArrayList<ParametrsMap> allParametrsMaps = new ArrayList();
            parametrs.forEach(parametr -> {
                List<WebElement> row = parametr.findElements(By.tagName("td"));

                if (row.size() == 1) {
                    // создать новый массив обекта
                    allParametrsMaps.add(new ParametrsMap(row.get(0).getText()));
                } else {
                    // добавить в текущий массив новый элемент
                    String key = row.get(0).findElement(By.tagName("span")).getText();
                    String value = row.get(1).getText();
                    Integer lastParametr = allParametrsMaps.size() - 1;

                    allParametrsMaps.get(lastParametr).parametrs.put(key,value);
                }
            });

            Gson gson = new Gson();
            product.setParametrs(gson.toJson(allParametrsMaps));

            try {
                product.push();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        driver.quit();

    }

}
