package util;

import org.apache.commons.dbutils.QueryRunner;
import parse.dns.Product;

import javax.sql.DataSource;
import java.sql.SQLException;

public class InsertProduct {
    public static void insert(DataSource dataSource, Product product) throws SQLException {
        new QueryRunner(dataSource).update
                ("INSERT INTO dnsproducts (code, name, price, description, parametrs, url) VALUES (?, ?, ?, ?, ?, ?)" +
                                "ON DUPLICATE KEY UPDATE name = ?, price = ?, description = ?, parametrs = ?, url = ?",
                        product.getCode(), product.getName(), product.getPrice(), product.getDescription(), product.getParametrs(), product.getUrl(),
                        product.getName(), product.getPrice(), product.getDescription(), product.getParametrs(), product.getUrl());
    }
}
