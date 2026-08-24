package com.afif.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseSeeder {

    public static void seed() {

        String createUsersTable = """
                CREATE TABLE IF NOT EXISTS users (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    username TEXT NOT NULL UNIQUE,
                    password TEXT NOT NULL,
                    role TEXT NOT NULL
                );
                """;

        String createBlogsTable = """
                CREATE TABLE IF NOT EXISTS blogs (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    title TEXT NOT NULL,
                    content TEXT NOT NULL,
                    user_id INTEGER NOT NULL,
                    FOREIGN KEY (user_id) REFERENCES users(id)
                );
                """;

        String insertUsers = """
        INSERT OR IGNORE INTO users (username, password, role)
        VALUES
            ('rahim', '1234', 'AUTHOR'),
            ('karim', '1234', 'AUTHOR'),
            ('admin', 'admin123', 'ADMIN'),
            ('guest', 'guest123', 'GUEST');
        """;

        String insertBlogs = """
        INSERT OR IGNORE INTO blogs (title, content, user_id)
        VALUES
            (
                'Exploring Dhaka University Campus',
                'A short blog about exploring the beautiful Dhaka University campus.',
                (SELECT id FROM users WHERE username = 'rahim')
            ),
            (
                'Street Food in Dhaka',
                'Fuchka, chotpoti and other popular street foods in Dhaka.',
                (SELECT id FROM users WHERE username = 'rahim')
            ),
            (
                'Trip to Coxs Bazar',
                'My experience visiting the longest sea beach in Coxs Bazar.',
                (SELECT id FROM users WHERE username = 'karim')
            ),
            (
                'Learning Java in Bangladesh',
                'My journey of learning Java and software engineering.',
                (SELECT id FROM users WHERE username = 'karim')
            );
        """;

        try (
                Connection connection = DatabaseConnection.getConnection();
                Statement statement = connection.createStatement()
        ) {

            statement.execute(createUsersTable);
            statement.execute(createBlogsTable);

            statement.execute(insertUsers);
            statement.execute(insertBlogs);

            System.out.println("Database seeded successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}