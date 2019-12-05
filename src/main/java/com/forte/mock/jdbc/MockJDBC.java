package com.forte.mock.jdbc;

import com.forte.mock.jdbc.table.BaseMockTable;
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
    public static <T> BaseMockTable<T> getTable(Class<T> type){
        MockObject<T> mockObj = Mock.get(type);
        return getTable(mockObj);
    }

    /**
     * 根据一个名称获取一个MockTable
     * @param name name
     * @return MockTable对象
     */
    public static BaseMockTable getTable(String name){
        MockObject<Map> mockObj = Mock.get(name);
        return getTable(mockObj);
    }


    /**
     * 根据MockObject来获取Table对象
     * @param mockObject mockObject
     * @return table
     */
    public static <T> BaseMockTable<T> getTable(MockObject<T> mockObject){
        if(mockObject != null){
            return getTable(mockObject.getMockBean());
        }else{
            return null;
        }
    }

    /**
     * 根据MockBean来获取Table对象
     * @param mockBean mockBean对象
     * @return table
     */
    public static <T> BaseMockTable<T> getTable(MockBean<T> mockBean){
        // TODO 转化为 table
        return null;
    }




}
