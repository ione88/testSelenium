package util;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.apache.commons.dbutils.QueryRunner;
import parse.yandex.News;

import java.sql.*;

public class MySQLInsertNews {

    public static void insert(News news) throws SQLException {

        Injector injector = Guice.createInjector(new DataSourceModule());
        DataSourceMySQL dataSource = injector.getInstance(DataSourceMySQL.class);
        QueryRunner run = new QueryRunner(dataSource.getDataSource());
        run.update("INSERT INTO yandexnews (data, title, url, type) VALUES (CURRENT_TIMESTAMP(), ?, ?, ?)" +
                        "ON DUPLICATE KEY UPDATE url=?",
                news.getTitle(), news.getUrl(), news.getTypeOfNews(),
                news.getUrl());
    }
}