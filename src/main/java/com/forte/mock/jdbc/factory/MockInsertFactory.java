package com.forte.mock.jdbc.factory;

import com.forte.mock.jdbc.operating.MockInsert;
import com.forte.mock.jdbc.operating.mysql.MySQLMockInsert;
import com.forte.mock.jdbc.table.MockTable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

/**
 *
 * 工厂用于创建与注册针对数据库的insert对象
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class MockInsertFactory {

    /** 保存所有的驱动与Insert获取器的集合 */
    private static final Map<String, MockInsertAnalyst> ANALYST_MAP;

    static {
        ANALYST_MAP = new HashMap<>(2);
        // MYSQL 驱动
        MockInsertAnalyst<?> mysqlAnalyst = table -> new MySQLMockInsert(table);
        ANALYST_MAP.put("com.mysql.jdbc.Driver", mysqlAnalyst);
        ANALYST_MAP.put("com.mysql.cj.jdbc.Driver", mysqlAnalyst);


    }

    /**
     * 注册数据库驱动以及其对应的数据库插入器获取函数。
     * @param driver   驱动名称
     * @param analyst  插入器获取函数
     * @param override 是否可覆盖
     */
    public static void register(String driver, MockInsertAnalyst analyst, boolean override) {
        ANALYST_MAP.merge(driver, analyst, remappingFunction(driver, override));
    }

    /**
     * 注册数据库驱动以及其对应的数据库插入器获取函数。默认不可覆盖
     * @param driver   驱动名称
     * @param analyst  插入器获取函数
     */
    public static void register(String driver, MockInsertAnalyst analyst) {
        register(driver, analyst, false);
    }

    /**
     * 根据是否可以覆盖获取merge函数
     * @param driver   driver驱动
     * @param override 是否可覆盖
     */
    private static <V> BiFunction<? super V, ? super V, ? extends V> remappingFunction(String driver, boolean override) {
        if(override){
            return (old, val) -> val;
        }else{
            return (old, val) -> {
                throw new IllegalArgumentException(driver + "'s analyst has already exist.");
            };
        }
    }


    /**
     * 通过一个table获取一个insert
     * @param table mockTable
     * @return      MockInsert
     */
    public static <T> MockInsert<T> getInsert(MockTable<T> table){
        String driver = table.getConnectCreator().getConnectInfo().getDriver();
        MockInsertAnalyst analyst = ANALYST_MAP.get(driver);
        if(analyst != null){
            return (MockInsert<T>) analyst.apply(table);
        }else{
            return null;
        }
    }




}
