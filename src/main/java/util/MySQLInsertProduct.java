package util;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.apache.commons.dbutils.QueryRunner;
import parse.dns.Product;

import java.sql.SQLException;

public class MySQLInsertProduct {
    public static void insert(Product product) throws SQLException {

        Injector injector = Guice.createInjector(new DataSourceModule());
        DataSourceMySQL dataSource = injector.getInstance(DataSourceMySQL.class);
        QueryRunner run = new QueryRunner(dataSource.getDataSource());

        run.update("INSERT INTO dnsproducts (code, name, price, description, parametrs, url) VALUES (?, ?, ?, ?, ?, ?)" +
                        "ON DUPLICATE KEY UPDATE name = ?, price = ?, description = ?, parametrs = ?, url = ?",
                product.getCode(), product.getName(), product.getPrice(), product.getDescription(), product.getParametrs(), product.getUrl(),
                product.getName(), product.getPrice(), product.getDescription(), product.getParametrs(), product.getUrl());
    }
}
