import com.google.inject.Guice;
import com.google.inject.Injector;
import parse.MyParser;
import parse.dns.Product;
import parse.yandex.News;
import util.DataSourceModule;
import util.DataSourceMySQL;
import util.InsertNews;
import util.InsertProduct;

import java.util.Scanner;


public class Main {

    public static void main(String[] args) throws Exception {
        //пользователь вводит свой город
        String userCity = enterCity("Москва");

        //внедряем зависимости в классы парсеры
        Injector injector = Guice.createInjector(new MyParseModule());
        MyParser myParser = injector.getInstance(MyParser.class);

        //внедряем зависимость в класс по работе с БД
        Injector injectorSQL = Guice.createInjector(new DataSourceModule());
        DataSourceMySQL dataSource = injectorSQL.getInstance(DataSourceMySQL.class);

        // собираем новости с главной страницы Яндекса в БД
        for (News news : myParser.parseYandexNews(userCity))
            InsertNews.insert(dataSource.getDataSource(), news);

        // собираем новости с ленты Дзен Яндекса в БД
        for (News news : myParser.parseYandexZen(userCity))
            InsertNews.insert(dataSource.getDataSource(), news);

        // собираем лучшие товары с главной страницы DNS в БД
        for (Product product : myParser.parseDnsBest(userCity))
            InsertProduct.insert(dataSource.getDataSource(), product);
    }

    private static String enterCity(String defualtCity) {
        Scanner in = new Scanner(System.in);
        System.out.print("Введите ваш город или нажмите Enter для г."+defualtCity+": ");
        String userCity = in.nextLine();
        if (userCity.isEmpty()) return  defualtCity;
        return  userCity;
    }

}
