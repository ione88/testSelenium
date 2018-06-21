package parse.yandex.zen;

import java.sql.SQLException;

public interface Parser {

    Object[] parser(String city) throws SQLException;
}

