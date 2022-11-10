package model;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtil {
    private static final String url="jdbc:mysql://localhost:3306/blog_system?characterEncoding=utf8&useSSL=false";
    private static final String user="root";
    private static final String password="1234";

    private volatile static DataSource dataSource=null;

    //线程安全的单例模式
    private static DataSource getDataSource() {
        if(dataSource==null) {
            synchronized (DBUtil.class) {
                if(dataSource==null) {
                    dataSource=new MysqlDataSource();
                    ((MysqlDataSource)dataSource).setURL(url);
                    ((MysqlDataSource)dataSource).setUser(user);
                    ((MysqlDataSource)dataSource).setPassword(password);
                }
            }
        }
        return dataSource;
    }

    public static Connection getConnection() throws SQLException {
        return getDataSource().getConnection();
    }

    public static void close(Connection connection, PreparedStatement statement, ResultSet resultSet) {
        if(resultSet!=null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
