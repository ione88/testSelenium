package util;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.apache.commons.dbutils.QueryRunner;
import parse.Product;
import java.sql.SQLException;
import java.util.Calendar;

public class MySQLInsertProduct {
    public static void insert(Product product) throws SQLException {


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
                "SELECT * FROM dnsproduct WHERE code = ?", h, product.getCode());

        if (result == null){
            int inserts = run.update("INSERT INTO dnsproduct (code, name, price, description, parametrs, url) VALUES (?, ?, ?, ?, ?, ?)",
                    product.getCode(), product.getName(), product.getPrice(), product.getDescription(), product.getParametrs(), product.getUrl() );
        } else {
            int inserts = run.update("UPDATE dnsproduct SET name = ?, price = ?, description = ?, url = ? WHERE code = ?",
                    product.getName(), product.getPrice(), product.getDescription(), product.getUrl(), product.getParametrs(), product.getCode());
        }

    }
}
