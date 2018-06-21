package parse;

import com.google.inject.Inject;
import parse.dns.Product;
import parse.dns.best.Parser;
import parse.yandex.News;

import java.sql.SQLException;
import java.util.ArrayList;

public class MyParser {
    private parse.yandex.news.Parser yandexNews;
    private parse.yandex.zen.Parser yandexZen;
    private parse.dns.best.Parser dnsBest;


    @Inject
    public MyParser(parse.yandex.news.Parser parserNews,
                    parse.yandex.zen.Parser parserZen,
                    parse.dns.best.Parser parserBest) {
        this.yandexNews = parserNews;
        this.yandexZen = parserZen;
        this.dnsBest = parserBest;
    }


    public ArrayList<News> parseYandexNews(String city) {
        return yandexNews.parser(city);
    }

    public ArrayList<News> parseYandexZen(String city) {
        return yandexZen.parser(city);
    }

    public ArrayList<Product> parseDnsBest(String city)  {
        return dnsBest.parser(city);
    }
}
