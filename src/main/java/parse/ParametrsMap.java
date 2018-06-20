package parse;

import java.util.HashMap;

public class ParametrsMap {
    String parametrType;
    HashMap<String,String> parametrs;


    ParametrsMap(String parametrType){
        this.parametrType = parametrType;
        this.parametrs = new HashMap<>();
    }
}
