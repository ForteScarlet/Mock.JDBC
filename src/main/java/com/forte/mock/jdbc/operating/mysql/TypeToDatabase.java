package com.forte.mock.jdbc.operating.mysql;

import java.util.HashMap;
import java.util.Map;

public class TypeToDatabase {

    private static Map<Class,String> fieldMap;

    static {
        fieldMap = new HashMap<>(10);
        fieldMap.put(Integer.class,"int");
        fieldMap.put(String.class,"varchar");
        fieldMap.put(Double.class,"double");
        fieldMap.put(int.class,"int");
        fieldMap.put(double.class,"double");
    }

    public static Map<Class,String> getFieldMap(){
        return fieldMap;
    }

}
