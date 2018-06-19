package parse;

import com.google.inject.Inject;

import java.sql.SQLException;

public class MyParser {
    private Parse parse;

    @Inject
    public MyParser(Parse parse){
        this.parse = parse;
    }
    public void makeParse() throws SQLException {
        parse.parse();
    }
}
