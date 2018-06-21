package parse.dns.best;

import com.google.gson.Gson;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import parse.dns.ParametrsMap;
import parse.dns.Product;

import java.util.ArrayList;
import java.util.List;

public class DnsBest implements Parser {

    WebDriver driver;
    JavascriptExecutor jse;

    @Override
    public ArrayList<Product> parser(String userCity) {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.dns-shop.ru/");
        //создаем новый пустой список продуктов, которые будем парсить
        ArrayList<Product> products = new ArrayList<Product>();

        // передаю в функцию изменения города, город который хочет пользователь и город указаный сейчас на сайте
        changeCity(userCity, driver.findElement(By.className("w-choose-city-widget")).getText());
        //получаем список url всех продуктов из категории лучшие предложения
        List<WebElement> bestOffers = driver.findElement(By.className("shopwindow-products")).findElements(By.xpath("//a[@data-product-param='name']"));
        //сохраняем все ссылки в список, что бы потом идти по нему
        ArrayList<String> urls = new ArrayList<String>();
        bestOffers.forEach(offer -> urls.add(offer.getAttribute("href")));
        //идём по список страниц с товарами и сохраняем в нашем списке products
        urls.forEach(url -> products.add(getproduct(url)));
        //закрываем браузер
        driver.quit();
        //возвращаем найденые товары
        return products;

    }

    private void changeCity(String userCity, String currentCity) {
        // если города равны, то ничего делать не нужно выходим из программы
        if (userCity.equals(currentCity)) {
            System.out.println("Проверка - город совпадает, изменения не нужны");
            return;
        }
        // кликаем на кнопку
        driver.findElement(By.className("w-choose-city-widget")).click();

        //выбрали строку для ввода
        String xpathToCityInput = "//div[contains(@class,'select-city-modal') and not(contains(@id,'select-city'))]//input[@placeholder='Название города']";

        (new WebDriverWait(driver, 20))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathToCityInput)));

        // выбираем форму для ввода города
        WebElement cityInput = driver.findElement(By.xpath(xpathToCityInput));
        //отправляем название города
        cityInput.sendKeys(userCity);
        // если город найден однозначно (появилась подсказка), то меняет город
        if (driver.findElement(By.xpath("//div[contains(@class,'show-hint')]")).isDisplayed()) {
            System.out.println("Проверка - город изменён");
            cityInput.sendKeys(Keys.ENTER);
        // город определить не удалось
        } else {
            System.out.println("Проверка - город не найден оставляем по умолчанию " + currentCity);
            driver.findElement(By.xpath("//div[contains(@class,'select-city-modal') and not(contains(@id,'select-city'))]//button[contains(@data-dismiss,'modal')]")).click();
        }
    }

    private Product getproduct(String url) {
        driver.get(url);
        Product product = new Product();

        (new WebDriverWait(driver, 20))
                .until(ExpectedConditions.presenceOfElementLocated(By.className("price-item-description")));

        //Ссылка на страницу с товаром
        product.setUrl(url);
        //Наименование - строка
        product.setName(driver.findElement(By.className("price-item-title")).getText());
        //Код товара – уникальное поле, целое
        product.setCode(Integer.parseInt(driver.findElement(By.className("price-item-code")).findElement(By.tagName("span")).getText()));
        //Цена - целое
        product.setPrice(Integer.parseInt(driver.findElement(By.className("price_g")).findElement(By.tagName("span")).getAttribute("data-price-value")));
        //Описание – строка
        product.setDescription(driver.findElement(By.id("description")).findElement(By.tagName("p")).getText());
        // превращаем объект с параметами в gson строку продукты
        Gson gson = new Gson();
        product.setParametrs(gson.toJson(getallParametrsMaps()));

        return product;
    }

    //считывает Характеристики в объект
    private ArrayList<ParametrsMap> getallParametrsMaps() {
        //открываем вкладку характеристики
        driver.findElement(By.linkText("Характеристики")).click();
        //получаем список параметров (характеристик)
        List<WebElement> parametrs = driver.findElement(By.id("characteristics")).findElements(By.tagName("tr"));
        ArrayList<ParametrsMap> allParametrsMaps = new ArrayList();
        //для каждый параметр сохраняем в объект
        parametrs.forEach(parametr -> {
            List<WebElement> row = parametr.findElements(By.tagName("td"));
            // если в строке только 1 столбец, значит это новый параметр, сохраняем его
            if (row.size() == 1) {
            // создать новый массив обекта
                allParametrsMaps.add(new ParametrsMap(row.get(0).getText()));
            //если 2 столбца, то значит в этой строке есть характеристика и значение параметра, сохраняем их.
            } else {
                // добавить в текущий массив новый элемент
                String key = row.get(0).findElement(By.tagName("span")).getText();
                String value = row.get(1).getText();
                Integer lastParametr = allParametrsMaps.size() - 1;

                allParametrsMaps.get(lastParametr).parametrs.put(key, value);
            }
        });
        //возвращаем объект с параметрами
        return allParametrsMaps;
    }
}