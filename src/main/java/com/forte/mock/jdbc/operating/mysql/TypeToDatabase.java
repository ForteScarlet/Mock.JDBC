package com.forte.mock.jdbc.operating.mysql;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public enum TypeToDatabase {

    MYSQL(
            new HashMap<Class, String>(16){{
                put(Integer.class,"int");
                put(String.class,"varchar");
                put(Double.class,"double");
                put(int.class,"int");
                put(double.class,"double");
                put(BigDecimal.class,"decimal");
            }}
    ),
    ORACLE(
            new HashMap<Class,String>(16){{

            }}
    ),
    SQLSERVER(
            new HashMap<Class,String>(16){{

            }}
    );

    private final Map<Class,String> fieldMap;

    TypeToDatabase(Map<Class,String> fieldMap){
        this.fieldMap = fieldMap;
    }

    /**
     * 将java的类型转换到MYSQL数据类型
     */


    public Map<Class,String> getFieldMap(){
        return fieldMap;
    }

}
