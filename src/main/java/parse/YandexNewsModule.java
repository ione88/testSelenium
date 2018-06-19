package parse;

import com.google.inject.AbstractModule;

public class YandexNewsModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Parse.class).to(YandexNews.class);
    }
}