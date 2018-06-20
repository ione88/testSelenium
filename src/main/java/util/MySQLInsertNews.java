package util;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;

import java.sql.*;
import java.util.Calendar;

import static java.lang.String.format;

public class MySQLInsertNews {

    public static void insert(String title, String url, String typeOfNews) throws SQLException {

        MyResultSetHandler h = new MyResultSetHandler();

        // No DataSource so we must handle Connections manually

        Injector injector = Guice.createInjector(new DataSourceModule());
        DataSourceMySQL dataSource = injector.getInstance(DataSourceMySQL.class);

        QueryRunner run = new QueryRunner(dataSource.getDataSource());

        // create a sql date object so we can use it in our INSERT statement
        Calendar calendar = Calendar.getInstance();
        java.sql.Date currentDate = new java.sql.Date(calendar.getTime().getTime());

        // Execute the query and get the results back from the handler
        Object[] result = run.query(
                "SELECT * FROM yandexnews WHERE title=?", h, title);

        if (result == null){
            int inserts = run.update("INSERT INTO yandexnews (data, title, url, type) VALUES (?, ?, ?, ?)",
                    currentDate, title, url, typeOfNews );
        } else {
            int inserts = run.update("UPDATE yandexnews SET url = ? WHERE title =?",
                    url, title);
        }

    }


}