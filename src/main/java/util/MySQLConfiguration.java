package util;


public interface MySQLConfiguration {

    // JDBC driver name and database URL
    String getJDBC_DRIVER();
    String getDB_URL();

    //  Database credentials

    String getUSER();
    String getPASS();
}
