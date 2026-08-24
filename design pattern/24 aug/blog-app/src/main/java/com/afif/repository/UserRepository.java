package com.afif.repository;

import com.afif.database.DatabaseConnection;
import com.afif.model.Role;
import com.afif.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {

    public User login(String username, String password) {

        String sql = """
                SELECT id, username, password, role
                FROM users
                WHERE username = ? AND password = ?
                """;

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                return new User(
                        resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        Role.valueOf(resultSet.getString("role"))
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    public boolean register(String username, String password, Role role) {

        String sql = """
            INSERT INTO users (username, password, role)
            VALUES (?, ?, ?)
            """;

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, role.name());

            statement.executeUpdate();

            return true;

        } catch (SQLException e) {
            return false;
        }
    }

    public void findAuthors() {

        String sql = """
            SELECT id, username
            FROM users
            WHERE role = 'AUTHOR'
            """;

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {

            System.out.println("\n===== Authors =====");

            while (resultSet.next()) {

                System.out.println(
                        resultSet.getInt("id") +
                                ". " +
                                resultSet.getString("username")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}