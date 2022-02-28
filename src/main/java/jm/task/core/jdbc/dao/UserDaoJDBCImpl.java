package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {

    }

    private final Connection connection = new Util().connection();


    public void createUsersTable() {

        try (Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS User (Id INT PRIMARY KEY AUTO_INCREMENT, Name VARCHAR(20), LastName VARCHAR(20), Age TINYINT)");
            System.out.println("Таблица User созданна");
            connection.commit();

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
            System.out.println(e);
        }
    }


    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            statement.executeUpdate("DROP TABLE IF EXISTS Kata1JDBC.user");
            System.out.println("Таблица User удалена");
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }

            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        String sql = "INSERT into Kata1JDBC.user (Name, LastName, Age) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            connection.setAutoCommit(false);
            preparedStatement.setString(1, name );
            preparedStatement.setString(2, lastName );
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.printf("User с именем – %s добавлен в базу данных \n", name);
            connection.commit();

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }


    public void removeUserById(long id) {

        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Kata1JDBC.user WHERE Id = ?")) {
            connection.setAutoCommit(false);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            System.out.printf("User с id = %d удален", id);
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
            e.printStackTrace();
        }

    }

    public List<User> getAllUsers() {

        List<User> list = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            ResultSet resultSet = statement.executeQuery("SELECT * FROM user");
            while (resultSet.next()) {
                User user = new User(resultSet.getLong(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getByte(4));
                list.add(user);
            }
            connection.commit();

            list.forEach(System.out::println);

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
            e.printStackTrace();
        }
        return list;
    }

    public void cleanUsersTable() {

        try (Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            statement.executeUpdate("TRUNCATE TABLE user");
            System.out.println("Таблица User очищена");
            connection.commit();

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Connection is closed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
