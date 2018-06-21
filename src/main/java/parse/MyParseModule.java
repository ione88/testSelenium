package parse;

import com.google.inject.AbstractModule;
import parse.dns.best.DnsBest;
import parse.dns.best.Parser;
import parse.yandex.news.YandexNews;
import parse.yandex.zen.YandexZen;

public class MyParseModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(parse.yandex.zen.Parser.class).to(YandexZen.class);
        bind(parse.yandex.news.Parser.class).to(YandexNews.class);
        bind(Parser.class).to(DnsBest.class);
    }
}