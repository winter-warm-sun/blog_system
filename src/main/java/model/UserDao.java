package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//这个表用来针对用户表的基本操作
public class UserDao {
    //1.根据用户名来查找用户信息(在登录逻辑中使用)
    public User selectByName(String username) {
        Connection connection=null;
        PreparedStatement statement=null;
        ResultSet resultSet=null;
        try {
            connection=DBUtil.getConnection();
            String sql="select * from user where username=?";
            statement=connection.prepareStatement(sql);
            statement.setString(1,username);
            resultSet=statement.executeQuery();
            //此处username使用unique约束，要么能查到一个，要么一个都查不到
            if(resultSet.next()) {
                User user=new User();
                user.setUserId(resultSet.getInt("userId"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(connection,statement,resultSet);
        }
        return null;
    }

    //2.根据用户id来找用户信息
    //博客详情页，就可以根据用户id来查询作者的名字并显示
    public User selectById(int userId) {
        Connection connection=null;
        PreparedStatement statement=null;
        ResultSet resultSet=null;
        try {
            connection=DBUtil.getConnection();
            String sql="select * from user where userId=?";
            statement=connection.prepareStatement(sql);
            statement.setInt(1,userId);
            resultSet=statement.executeQuery();
            // 此处 username 使用 unique 约束, 要么能查到一个, 要么一个都查不到.
            if (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getInt("userId"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(connection, statement, resultSet);
        }
        return null;
    }
}
