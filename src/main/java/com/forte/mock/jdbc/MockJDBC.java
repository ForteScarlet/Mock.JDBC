package com.forte.mock.jdbc;

import com.forte.mock.jdbc.connect.ConnectAble;
import com.forte.mock.jdbc.connect.ConnectInfo;
import com.forte.mock.jdbc.table.MockTable;
import com.forte.mock.jdbc.table.MockTableField;
import com.forte.mock.jdbc.utils.TableUtils;
import com.forte.util.Mock;
import com.forte.util.mockbean.MockBean;
import com.forte.util.mockbean.MockObject;

import java.util.Map;

/**
 *
 * MockJDBC，对外窗口
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class MockJDBC {


    /**
     * 根据一个类获取一个MockTable
     * @param type type类型
     * @return     MockTable对象
     */
    public static <T> MockTable<T> getTable(ConnectAble connectable, Class<T> type){
        MockObject<T> mockObj = Mock.get(type);
        return getTable(connectable, mockObj);
    }

    /**
     * 根据一个名称获取一个MockTable
     * @param name name
     * @return MockTable对象
     */
    public static MockTable getTable(ConnectAble connectable, String name){
        MockObject<Map> mockObj = Mock.get(name);
        return getTable(connectable, mockObj);
    }


    /**
     * 根据MockObject来获取Table对象
     * @param mockObject mockObject
     * @return table
     */
    public static <T> MockTable<T> getTable(ConnectAble connectable, MockObject<T> mockObject){
        if(mockObject != null){
            return getTable(connectable, mockObject.getMockBean());
        }else{
            return null;
        }
    }

    /**
     * 根据MockBean来获取Table对象.
     * 基础方法
     * @param mockBean mockBean对象
     * @return table
     */
    public static <T> MockTable<T> getTable(ConnectAble connectable, MockBean<T> mockBean){
        String tableName = TableUtils.toUnderline(mockBean.getObjectClass());
        MockTableField[] fields = MockTableField.parse(mockBean.getFields());
        return new MockTable<>(
                connectable,
                tableName,
                fields,
                new String[0],
                mockBean
        );
    }




}
