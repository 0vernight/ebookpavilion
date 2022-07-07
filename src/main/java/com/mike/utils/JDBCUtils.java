package com.mike.utils;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @Author: 23236
 * @createTime: 2022/5/21   20:30
 * @description: 链接数据的工具类
 * 这个功能apache封装了如下的jar包
 * <!-- https://mvnrepository.com/artifact/commons-dbutils/commons-dbutils -->
 * <dependency>
 *     <groupId>commons-dbutils</groupId>
 *     <artifactId>commons-dbutils</artifactId>
 *     <version>1.7</version>
 * </dependency>
 * 使用里面的QueryRunner类
 *
 *
 **/
public class JDBCUtils {

    private static DataSource dataSource=null;

    static {
        DruidDataSource druidDataSource = new DruidDataSource();

        try {
            Properties pros = new Properties();
            //读取数据库配置文件
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("druid.properties");
            pros.load(is);
            dataSource = DruidDataSourceFactory.createDataSource(pros);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    public static Connection getConnectionPool() throws SQLException {
        //任何数据库连接池都实现了datasource接口
//        DataSource dataSource=null;

        Connection connection = dataSource.getConnection();
//        System.out.println(connection);
        return connection;
    }

    public static Connection getConnection() throws SQLException, ClassNotFoundException, IOException {
//        System.out.println("连接数据库begin");
        Connection connection = null;
        PreparedStatement preparedStatement = null;


        //1.读取配置文件的信息
        //InputStream resourceAsStream = JDBC.class.getClassLoader().getResourceAsStream("jdbc.properties");//加载器当中的系统加载器
        InputStream resourceAsStream = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");//也跟上面的一样
        Properties properties = new Properties();
        properties.load(resourceAsStream);
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        String url = properties.getProperty("url");
        String driverClass = properties.getProperty("driverClass");

        //2.记载驱动
        Class.forName(driverClass);

        //3.获取链接
        connection = DriverManager.getConnection(url, user, password);

//        System.out.println("链接成功结束=" + connection);

        return connection;
    }

    public static void closeResource(Connection connection, Statement preparedStatement) {
        //7.资源关闭
        try {
            if (preparedStatement != null) {//避免对象没有创建就要关闭的判断
                preparedStatement.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            if (connection != null) {
                connection.close();

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
    public static void closeResource(Connection connection, Statement preparedStatement,ResultSet resultSet) {
        //7.资源关闭
        try {
            if (preparedStatement != null) {//避免对象没有创建就要关闭的判断
                preparedStatement.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }




}
