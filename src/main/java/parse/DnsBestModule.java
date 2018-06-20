package parse;

import com.google.inject.AbstractModule;

public class DnsBestModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Parse.class).to(DnsBest.class);
    }
}