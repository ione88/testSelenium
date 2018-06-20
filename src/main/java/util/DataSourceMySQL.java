package util;

import net.jmob.guice.conf.core.BindConfig;
import net.jmob.guice.conf.core.InjectConfig;
import net.jmob.guice.conf.core.Syntax;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;

@BindConfig(value = "dataSource", syntax = Syntax.CONF)
public class DataSourceMySQL {

    // JDBC driver name and database URL
    @InjectConfig
    String JDBC_DRIVER;
    @InjectConfig
    String DB_URL;

    //  Database credentials
    @InjectConfig
    String USER;
    @InjectConfig
    String PASS;

    private BasicDataSource basicDataSource;


    public DataSource getDataSource() {
        basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName(JDBC_DRIVER);
        basicDataSource.setUsername(USER);
        basicDataSource.setPassword(PASS);
        basicDataSource.setUrl(DB_URL);

        return basicDataSource;
    }
}
