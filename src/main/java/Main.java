import com.google.inject.*;
import parse.MyParser;
import parse.dns.Product;
import parse.yandex.News;
import util.MySQLInsertNews;
import util.MySQLInsertProduct;


public class Main {

    public static void main(String[] args) throws Exception {


        String userCity = "москва";
        Injector injector = Guice.createInjector(new MyParseModule());
        MyParser myParser = injector.getInstance(MyParser.class);

        Object[] newsFeed = myParser.parseYandexNews(userCity);
        for (Object news : newsFeed) {
            MySQLInsertNews.insert((News) news);
        }

        Object[] zenFeed = myParser.parseYandexZen(userCity);
        for (Object news : zenFeed) {
            MySQLInsertNews.insert((News) news);
        }

        Object[] products = myParser.parseDnsBest(userCity);
        for (Object product : products) {
            MySQLInsertProduct.insert((Product) product);
        }

    }

}
