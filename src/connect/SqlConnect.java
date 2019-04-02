package connect;

import entities.Message;
import entities.Post;
import entities.ThreadTitle;
import entities.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqlConnect {

    public SqlConnect() {
    }

    private static Connection getConnection() throws SQLException {
        Connection conn;
        String connectionURL = "jdbc:mysql://localhost:3306/forum?autoReconnect=true&useSSL=false";
        String userna = "forumAdmin";
        String passwordjdbc = "konnos1987";
        conn = DriverManager.getConnection(connectionURL, userna, passwordjdbc);
        return conn;

    }

    public static PreparedStatement executeSqlUpdate(String sql, String... values) throws SQLException {
        try (
                Connection conn = getConnection();
                PreparedStatement prs = conn.prepareStatement(sql);) {
            int i = 1;
            for (String value : values) {
                prs.setString(i, values[i - 1]);
                i++;
            }
            prs.executeUpdate();

            return prs;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static PreparedStatement executeSqlUpdate(String sql, int x, String y) throws SQLException {

        try (
                Connection conn = getConnection();
                PreparedStatement prs = conn.prepareStatement(sql);) {
            prs.setInt(1, x);
            prs.setString(2, y);
            prs.executeUpdate();

            return prs;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static PreparedStatement executeSqlUpdate(String sql, int x) throws SQLException {
        try (
                Connection conn = getConnection();
                PreparedStatement prs = conn.prepareStatement(sql);) {
            prs.setInt(1, x);
            prs.executeUpdate();

            return prs;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static PreparedStatement executeSqlUpdate(String sql, String y, int x) throws SQLException {
        try (
                Connection conn = getConnection();
                PreparedStatement prs = conn.prepareStatement(sql);) {
            prs.setString(1, y);
            prs.setInt(2, x);
            prs.executeUpdate();

            return prs;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static ResultSet executeSqlQuery(String sql, String... values) throws SQLException {

        ResultSet rs;
        Connection conn;
        PreparedStatement prs;

        try {
            conn = getConnection();
            prs = conn.prepareStatement(sql);
            int i = 1;
            for (String value : values) {
                prs.setString(i, values[i - 1]);
                i++;
            }
            rs = prs.executeQuery();

            return rs;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static ResultSet executeSqlQuery(String sql, int x) throws SQLException {

        ResultSet rs;
        Connection conn;
        PreparedStatement prs;

        try {
            conn = getConnection();
            prs = conn.prepareStatement(sql);
            prs.setInt(1, x);
            rs = prs.executeQuery();
            return rs;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static List<User> getAllUsers() {
        try {
            List<User> users = new ArrayList();
            ResultSet rs;
            String sql = "select * from users";
            rs = executeSqlQuery(sql);
            while (rs.next()) {
                users.add(new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getInt(7)));
            }
            rs.close();
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }

    }

    public static User getUserByUsername(String username) throws SQLException {
        ResultSet rs;
        String sql1 = "select * from users where username=?";
        rs = SqlConnect.executeSqlQuery(sql1, username);
        if (rs.next()) {
            User user = new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getInt(7));
            return user;
        } else {
            return null;
        }
    }

    public static User getUser(String username, String password) throws SQLException {
        ResultSet rs;
        String sql1 = "select * from users where username=? and password=?";
        rs = SqlConnect.executeSqlQuery(sql1, username, password);
        if (rs.next()) {
            User user = new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getInt(7));
            return user;
        } else {
            return null;
        }
    }

    public static List<User> getBannedUsers() {

        ResultSet rs;
        List<User> users = new ArrayList();
        try {
            String sql = "select * from users where banned_status=?";
            rs = SqlConnect.executeSqlQuery(sql, 1);
            while (rs.next()) {
                users.add(new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getInt(7)));
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static List<Message> getUsersMessages(String username, String receiver) {

        ResultSet rs;
        List<Message> msgs = new ArrayList();
        try {
            String sql = "select * from messages\n"
                    + "where sender=? and receiver=? or sender=? and receiver=? order by m_id";
            rs = executeSqlQuery(sql, username, receiver, receiver, username);
            while (rs.next()) {
                msgs.add(new Message(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getTimestamp(5), rs.getInt(6)));
            }
            rs.close();

            return msgs;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static List<Message> getUsersSentMessages(String username, String receiver) {

        ResultSet rs;
        List<Message> msgs = new ArrayList();
        try {
            String sql = "select * from messages\n"
                    + "where sender=? and receiver=? order by m_id";
            rs = executeSqlQuery(sql, username, receiver);
            while (rs.next()) {
                msgs.add(new Message(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getTimestamp(5), rs.getInt(6)));
            }
            rs.close();

            return msgs;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static List<Message> getUsersUnreadMessages(String username) {

        ResultSet rs;
        List<Message> msgs = new ArrayList();
        try {
            String sql = "select * from messages\n"
                    + "where receiver=? and is_read=1 order by m_id";
            rs = executeSqlQuery(sql, username);
            while (rs.next()) {
                msgs.add(new Message(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getTimestamp(5), rs.getInt(6)));
            }
            rs.close();

            return msgs;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static List<Post> getThreadPosts(String title) {

        ResultSet rs;
        List<Post> posts = new ArrayList();
        try {
            String sql = "select m_id,forum_messages,title,date,sender from forum.forum_messages where title=?";
            rs = executeSqlQuery(sql, title);
            while (rs.next()) {
                posts.add(new Post(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDate(4), rs.getString(5)));
            }
            rs.close();

            return posts;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static List<ThreadTitle> getAllThreads() {

        ResultSet rs;
        List<ThreadTitle> threads = new ArrayList();
        try {
            String sql = "select title,locked_status from forum.forum_title";
            rs = executeSqlQuery(sql);
            while (rs.next()) {
                threads.add(new ThreadTitle(rs.getString(1), rs.getInt(2)));
            }
            rs.close();

            return threads;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
