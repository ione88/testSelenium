import com.google.inject.*;
import parse.*;


public class Main {

    public static void main(String[] args) throws Exception {

        Injector injector = Guice.createInjector(new YandexNewsModule());
        MyParser yandexNews = injector.getInstance(MyParser.class);
        yandexNews.makeParse();


        injector = Guice.createInjector(new YandexZenModule());
        MyParser yandexZen = injector.getInstance(MyParser.class);
        yandexZen.makeParse();

    }
}
