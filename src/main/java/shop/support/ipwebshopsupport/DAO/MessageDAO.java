package shop.support.ipwebshopsupport.DAO;

import shop.support.ipwebshopsupport.beans.MessageBean;
import shop.support.ipwebshopsupport.beans.SupportBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {

    public static List<MessageBean> getAllMessages() {
        List<MessageBean> messages = new ArrayList<>();

        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            connection = ConnectionPool.getInstance().checkOut();

            String sql = "SELECT * FROM message ORDER BY seen";
            stmt = connection.prepareStatement(sql);

            rs = stmt.executeQuery();

            while (rs.next()) {
                MessageBean message = new MessageBean();
                message.setId(rs.getInt(1));
                message.setText(rs.getString(2));
                message.setSeen(rs.getBoolean(3));
                message.setUserId(rs.getString(4));
                messages.add(message);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }
            }
            if(stmt != null) {
                try {
                    stmt.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }
            }
            ConnectionPool.getInstance().checkIn(connection);
        }
        return messages;
    }

    public static boolean setSeen(Integer messageId) {
        Connection connection = null;
        boolean seen = false;
        String setSeenSql = "UPDATE message SET seen = TRUE WHERE id = ?";
        try {
            connection = ConnectionPool.getInstance().checkOut();
            PreparedStatement updateStatement = connection.prepareStatement(setSeenSql);
            updateStatement.setInt(1,messageId);
            int rowsAffected = updateStatement.executeUpdate();

            if (rowsAffected > 0) {
                seen = true;
            }
            updateStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionPool.getInstance().checkIn(connection);
        }

        return seen;
    }
}
