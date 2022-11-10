package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//这个类用来封装针对博客表的基本操作
public class BlogDao {
    //1.往博客表里，插入一个博客
    public void insert(Blog blog) {
        Connection connection=null;
        PreparedStatement statement=null;
        try {
            //1)和数据库建立连接
            connection=DBUtil.getConnection();
            //2)构造SQL语句
            String sql="insert into blog values(null,?,?,?,now())";
            statement=connection.prepareStatement(sql);
            statement.setString(1, blog.getTitle());
            statement.setString(2,blog.getContent());
            statement.setInt(3,blog.getUserId());
            //3)执行SQL
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //4)关闭连接，释放资源
            DBUtil.close(connection,statement,null);
        }
    }

    //2.能够获取到博客列表中的所有博客的信息
    // （用在博客列表页，获取到的是博客正文的摘要）
    public List<Blog> selectAll() {
        List<Blog> blogs=new ArrayList<>();
        Connection connection=null;
        PreparedStatement statement=null;
        ResultSet resultSet=null;
        try {
            connection=DBUtil.getConnection();
            String sql="select * from blog order by postTime desc";
            statement= connection.prepareStatement(sql);
            resultSet=statement.executeQuery();
            while (resultSet.next()) {
                Blog blog=new Blog();
                blog.setBlogId(resultSet.getInt("blogId"));
                blog.setTitle(resultSet.getString("title"));
                //截取摘要
                String content=resultSet.getString("content");
                if(content.length()>50) {
                    content = content.substring(0,50)+"...";
                }
                blog.setContent(content);
                blog.setUserId(resultSet.getInt("userId"));
                blog.setPostTime(resultSet.getTimestamp("postTime"));
                blogs.add(blog);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(connection,statement,resultSet);
        }
        return blogs;
    }

    //3.能够根据博客id获取到指定的博客内容（用于博客详情页）
    public Blog selectOne(int blogId) {
        Connection connection=null;
        PreparedStatement statement=null;
        ResultSet resultSet=null;
        try {
            connection=DBUtil.getConnection();
            String sql="select * from blog where blogId=?";
            statement=connection.prepareStatement(sql);
            statement.setInt(1,blogId);
            resultSet=statement.executeQuery();
            //此处我们是使用 主键 来作为查询条件的，查询结果要么是1，要么是0
            if(resultSet.next()) {
                Blog blog=new Blog();
                blog.setBlogId(resultSet.getInt("blogId"));
                blog.setTitle(resultSet.getString("title"));
                blog.setContent(resultSet.getString("content"));
                blog.setUserId(resultSet.getInt("userId"));
                blog.setPostTime(resultSet.getTimestamp("postTime"));
                return blog;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(connection,statement,resultSet);
        }
        return null;
    }

    //4.从博客表中，根据博客id删除博客
    public void delete(int blogId) {
        Connection connection=null;
        PreparedStatement statement=null;
        try {
            connection=DBUtil.getConnection();
            String sql="delete from blog where blogId=?";
            statement=connection.prepareStatement(sql);
            statement.setInt(1,blogId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(connection,statement,null);
        }
    }
}
