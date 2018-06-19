package util;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import parse.MyParser;
import parse.YandexNewsModule;

import java.sql.*;
import java.util.Calendar;

public class MySQLInsert {

    public static void insert(String title, String url, String typeOfNews) throws SQLException {

        // Create a ResultSetHandler implementation to convert the
        // first row into an Object[].
        ResultSetHandler<Object[]> h = new ResultSetHandler<Object[]>() {
            public Object[] handle(ResultSet rs) throws SQLException {
                if (!rs.next()) {
                    return null;
                }

                ResultSetMetaData meta = rs.getMetaData();
                int cols = meta.getColumnCount();
                Object[] result = new Object[cols];

                for (int i = 0; i < cols; i++) {
                    result[i] = rs.getObject(i + 1);
                }

                return result;
            }
        };

        // No DataSource so we must handle Connections manually
        QueryRunner run = new QueryRunner(CustomDataSource.getInstance());

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