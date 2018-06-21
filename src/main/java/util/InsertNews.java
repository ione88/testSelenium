package util;

import org.apache.commons.dbutils.QueryRunner;
import parse.yandex.News;

import javax.sql.DataSource;
import java.sql.*;

public class InsertNews {
    public static void insert(DataSource dataSource, News news) throws SQLException {
        new QueryRunner(dataSource).update
                ("INSERT INTO yandexnews (data, title, url, type) VALUES (CURRENT_TIMESTAMP(), ?, ?, ?)" +
                                "ON DUPLICATE KEY UPDATE url=?",
                        news.getTitle(), news.getUrl(), news.getTypeOfNews(),
                        news.getUrl());
    }
}