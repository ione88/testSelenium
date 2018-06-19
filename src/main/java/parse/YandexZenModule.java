package parse;

import com.google.inject.AbstractModule;

public class YandexZenModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Parse.class).to(YandexZen.class);
    }
}