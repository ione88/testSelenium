package parse.dns;

import java.util.HashMap;

public class ParametrsMap {
    String parametrType;
    public HashMap<String,String> parametrs;


    public ParametrsMap(String parametrType){
        this.parametrType = parametrType;
        this.parametrs = new HashMap<>();
    }
}
