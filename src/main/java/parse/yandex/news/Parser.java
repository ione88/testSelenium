package parse.yandex.news;

import java.sql.SQLException;

public interface Parser {

    Object[] parser(String city) throws SQLException;
}

