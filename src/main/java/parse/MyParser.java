package parse;

import com.google.inject.Inject;
import parse.dns.best.Parser;

import java.sql.SQLException;

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


    public Object[] parseYandexNews(String city) throws SQLException {
        return yandexNews.parser(city);
    }

    public Object[] parseYandexZen(String city) throws SQLException {
        return yandexZen.parser(city);
    }

    public Object[] parseDnsBest(String city) throws SQLException {
        return dnsBest.parser(city);
    }
}
