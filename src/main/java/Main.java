import com.google.inject.Guice;
import com.google.inject.Injector;
import parse.MyParseModule;
import parse.MyParser;
import parse.dns.Product;
import parse.yandex.News;
import util.MySQLInsertNews;
import util.MySQLInsertProduct;

import java.util.Scanner;


public class Main {

    public static void main(String[] args) throws Exception {
        String userCity = enterSity("Москва");
        Injector injector = Guice.createInjector(new MyParseModule());
        MyParser myParser = injector.getInstance(MyParser.class);

        for (Object news : myParser.parseYandexNews(userCity))
            MySQLInsertNews.insert((News) news);

        for (Object news : myParser.parseYandexZen(userCity))
            MySQLInsertNews.insert((News) news);

        for (Object product : myParser.parseDnsBest(userCity))
            MySQLInsertProduct.insert((Product) product);
    }

    private static String enterSity(String defualtCity) {
        Scanner in = new Scanner(System.in);
        System.out.print("Введите ваш город или нажмите Enter для г."+defualtCity+": ");
        String userCity = in.nextLine();
        if (userCity=="") return  defualtCity;
        return  userCity;
    }

}
