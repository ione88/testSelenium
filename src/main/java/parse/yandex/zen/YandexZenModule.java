package parse.yandex.zen;

import com.google.inject.AbstractModule;

public class YandexZenModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Parser.class).to(YandexZen.class);
    }
}