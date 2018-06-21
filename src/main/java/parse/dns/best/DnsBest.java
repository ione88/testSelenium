package parse.dns.best;

import com.google.gson.Gson;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import parse.dns.Product;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DnsBest implements Parser {

    WebDriver driver;
    JavascriptExecutor jse;

    @Override
    public Object[] parser(String userCity) throws SQLException {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.dns-shop.ru/");

        ArrayList<Product> products = new ArrayList<Product>();

        // передаю в функцию изменения города, город который хочет пользователь и город указаный сейчас на сайте
        changeCity(userCity, driver.findElement(By.className("w-choose-city-widget")).getText());

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

                    allParametrsMaps.get(lastParametr).parametrs.put(key, value);
                }
            });

            Gson gson = new Gson();
            product.setParametrs(gson.toJson(allParametrsMaps));

            products.add(product);
        });
        driver.quit();
        return products.toArray();

    }

    private void changeCity(String userCity, String currentCity) {
        // если города равны, то ничего делать не нужно выходим из программы
        if (userCity.equals(currentCity)) {
            System.out.println("Проверка - город совпадает, изменения не нужны");
            return;
        }
        // кликаем на кнопку
        driver.findElement(By.className("w-choose-city-widget")).click();

        String xpathToCityInput = "//div[contains(@class,'select-city-modal') and not(contains(@id,'select-city'))]//input[@placeholder='Название города']";

        (new WebDriverWait(driver, 20))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathToCityInput)));

        // выбираем форму для ввода города
        WebElement cityInput = driver.findElement(By.xpath(xpathToCityInput));
        cityInput.sendKeys(userCity);
        if (driver.findElement(By.xpath("//div[contains(@class,'show-hint')]")).isDisplayed()) {
            System.out.println("Проверка - город изменён");
            cityInput.sendKeys(Keys.ENTER);
        } else {
            System.out.println("Проверка - город не найден оставляем по умолчанию " + currentCity);
            driver.findElement(By.xpath("//div[contains(@class,'select-city-modal') and not(contains(@id,'select-city'))]//button[contains(@data-dismiss,'modal')]")).click();
        }
    }
}



