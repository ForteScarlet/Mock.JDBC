package com.forte.mock.jdbc;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据类型与数据库类型对应表。
 * TODO 注意，需要考虑：Object类型、map中拿不到的类型。
 */
public enum TypeToDatabase {

    /** MySQL数据库字段类型对应表 */
    MYSQL(
            new HashMap<Class, String>(16){{
                put(Integer.class,"INT");
                put(String.class,"VARCHAR");
                put(Double.class,"DOUBLE");
                put(int.class,"INT");
                put(double.class,"DOUBLE");
                put(BigDecimal.class,"DECIMAL");
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

    /**
     * 获取字段数据类型对应的数据库类型
     * @param type 字段数据类型
     * @return 数据库类型
     */
    public String getDatabaseType(Class type){
        if(type.equals(Object.class)){
            // TODO 在Mock.java中，假如字段类型是Object类型，
            //  那么它传入的数据类型可能不是一个指定的javabean而是一个Map类型的映射
            //  这时候想要确定具体的数据类型就会比较困难
            //  mock.java中，字段类型为Object的时候，具体值会依赖于生成的数据
            //  所以目前决定，当值为Object类型的时候，先默认使用varchar类型，即字符串类型。
            return fieldMap.get(String.class);

        }else{
            String get = fieldMap.get(type);
            if(get == null){
                // TODO 获取不到的情况
                //  默认使用String类型代替
                return fieldMap.get(String.class);
            }else{
                return get;
            }

        }

    }


    public Map<Class,String> getFieldMap(){
        return fieldMap;
    }

}
