import com.google.inject.*;
import parse.*;

import java.sql.SQLException;


public class Main {

    public static void main(String[] args) throws Exception {

        Main main = new Main();

        main.parseYandexNews();
        main.parseYandexZen();
        main.parseDnsBest();

    }

    private void parseYandexNews() throws SQLException {
        Injector injector = Guice.createInjector(new YandexNewsModule());
        MyParser yandexNews = injector.getInstance(MyParser.class);
        yandexNews.makeParse();
    }

    private void parseYandexZen() throws SQLException {
        Injector injector = Guice.createInjector(new YandexZenModule());
        MyParser yandexZen = injector.getInstance(MyParser.class);
        yandexZen.makeParse();
    }

    private void parseDnsBest() throws SQLException {
        Injector injector = Guice.createInjector(new DnsBestModule());
        MyParser DnsBest = injector.getInstance(MyParser.class);
        DnsBest.makeParse();
    }
}
