package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl extends Util implements UserDao {
    public UserDaoJDBCImpl() {

    }

    Connection connection = getConnection();


    public void createUsersTable() {
        PreparedStatement preparedStatement = null;

        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                "id BIGINT NOT NULL AUTO_INCREMENT, " +
                "name VARCHAR(40), " +
                "lastname VARCHAR(40), " +
                "age TINYINT, " +
                "PRIMARY KEY (id))";

        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Таблица создана");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void dropUsersTable() {
        String sqlDrop = "DROP TABLE IF EXISTS users";
        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate(sqlDrop);
            System.out.println("Таблица была успешно удалена");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sqlSave = "INSERT INTO users (name, lastname, age) VALUES(?, ?, ?)";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlSave)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем — " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        String sqlRemove = "DELETE FROM users WHERE id=?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlRemove)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
     String sqlGetAll = "SELECT * FROM users";

     try (Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sqlGetAll)) {

         while (resultSet.next()) {
             User user = new User();
             user.setId(resultSet.getLong("id"));
             user.setName(resultSet.getString("name"));
             user.setLastName(resultSet.getString("lastname"));
             user.setAge(resultSet.getByte("age"));

             users.add(user);

         }
     } catch (SQLException e) {
         throw new RuntimeException(e);
     }
return users;
    }

    public void cleanUsersTable() {
        String sqlClean = "TRUNCATE TABLE users";

        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate(sqlClean);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
