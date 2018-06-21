package parse.dns.best;

import java.sql.SQLException;
import java.util.ArrayList;

public interface Parser {

    Object[] parser(String city) throws SQLException;
}

