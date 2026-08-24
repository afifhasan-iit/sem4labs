package com.afif.repository;

import com.afif.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BlogRepository {

    public void findBlogsByUserId(int userId) {

        String sql = """
                SELECT blogs.title, blogs.content, users.username
                FROM blogs
                JOIN users ON blogs.user_id = users.id
                WHERE blogs.user_id = ?
                """;

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, userId);

            ResultSet resultSet = statement.executeQuery();

            boolean found = false;

            while (resultSet.next()) {

                found = true;

                System.out.println("\nTitle: " +
                        resultSet.getString("title"));

                System.out.println("Author: " +
                        resultSet.getString("username"));

                System.out.println("Content: " +
                        resultSet.getString("content"));

                System.out.println("-------------------");
            }

            if (!found) {
                System.out.println("You don't have any blogs.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void findBlogsByPage(int page, int pageSize) {

        int offset = (page - 1) * pageSize;

        String sql = """
            SELECT blogs.title, blogs.content, users.username
            FROM blogs
            JOIN users ON blogs.user_id = users.id
            LIMIT ? OFFSET ?
            """;

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, pageSize);
            statement.setInt(2, offset);

            ResultSet resultSet = statement.executeQuery();

            boolean found = false;

            while (resultSet.next()) {

                found = true;

                System.out.println("\nTitle: " +
                        resultSet.getString("title"));

                System.out.println("Author: " +
                        resultSet.getString("username"));

                System.out.println("Content: " +
                        resultSet.getString("content"));

                System.out.println("-------------------");
            }

            if (!found) {
                System.out.println("No blogs found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}