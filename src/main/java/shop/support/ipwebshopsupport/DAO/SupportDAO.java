package shop.support.ipwebshopsupport.DAO;

import shop.support.ipwebshopsupport.beans.SupportBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SupportDAO {

    public static boolean registerSupportUser(SupportBean supportUser) {
        Connection connection = null;
        boolean isRegistered = false;

        String checkUsernameSql = "SELECT COUNT(*) FROM user WHERE username = ?";
        String insertSql = "INSERT INTO user (username, password, first_name, last_name, role) VALUES (?, ?, ?, ?, 'SUPPORT')";

        try {
            connection = ConnectionPool.getInstance().checkOut();
            PreparedStatement checkStatement = connection.prepareStatement(checkUsernameSql);
            checkStatement.setString(1, supportUser.getUsername());
            ResultSet rs = checkStatement.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return false;
            }

            PreparedStatement insertStatement = connection.prepareStatement(insertSql);
            insertStatement.setString(1, supportUser.getUsername());
            insertStatement.setString(2, supportUser.getPassword());
            insertStatement.setString(3, supportUser.getFirstName());
            insertStatement.setString(4, supportUser.getLastName());

            int rowsAffected = insertStatement.executeUpdate();

            if (rowsAffected > 0) {
                isRegistered = true;
            }

            checkStatement.close();
            insertStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionPool.getInstance().checkIn(connection);
        }

        return isRegistered;
    }



    public static SupportBean getSupportUserByUsername(String username) {
        Connection connection = null;
        ResultSet resultSet;
        SupportBean supportUser = null;

        String sql = "SELECT username, password, first_name, last_name FROM user WHERE username = ?";

        try {
            connection = ConnectionPool.getInstance().checkOut();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                supportUser = new SupportBean();
                supportUser.setUsername(resultSet.getString("username"));
                supportUser.setPassword(resultSet.getString("password"));
                supportUser.setFirstName(resultSet.getString("first_name"));
                supportUser.setLastName(resultSet.getString("last_name"));
            }
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionPool.getInstance().checkIn(connection);
        }

        return supportUser;
    }

    public static String getEmailById(Integer id) {
        Connection connection = null;
        ResultSet resultSet = null;
        String email = null;

        String sql = "SELECT email FROM user WHERE id = ?";

        try {
            connection = ConnectionPool.getInstance().checkOut();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                email = resultSet.getString("email");
            }
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if(resultSet != null) {
                try {
                    resultSet.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }
            }
            ConnectionPool.getInstance().checkIn(connection);
        }

        return email;
    }



}
