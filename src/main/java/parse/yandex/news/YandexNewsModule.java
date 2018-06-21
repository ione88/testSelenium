package parse.yandex.news;

import com.google.inject.AbstractModule;

public class YandexNewsModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Parser.class).to(YandexNews.class);
    }
}