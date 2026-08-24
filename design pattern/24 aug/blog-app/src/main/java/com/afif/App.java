package com.afif;

import com.afif.database.DatabaseSeeder;
import com.afif.model.Role;
import com.afif.repository.BlogRepository;
import com.afif.repository.UserRepository;

import java.util.Scanner;

public class App {
    private final BlogRepository blogRepository = new BlogRepository();
    private final Scanner scanner = new Scanner(System.in);
    private final UserRepository userRepository = new UserRepository();

    public void start() {

        DatabaseSeeder.seed();

        while (true) {

            System.out.println("\n===== Blog Application =====");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> register();
                case "2" -> login();
                case "3" -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private void register() {

        System.out.println("\n===== Registration =====");

        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        System.out.println("Select role:");
        System.out.println("1. Author");
        System.out.println("2. Guest");

        System.out.print("Choose: ");
        String roleChoice = scanner.nextLine();

        Role role;

        if (roleChoice.equals("1")) {
            role = Role.AUTHOR;
        } else if (roleChoice.equals("2")) {
            role = Role.GUEST;
        } else {
            System.out.println("Invalid role.");
            return;
        }

        boolean registered =
                userRepository.register(username, password, role);

        if (registered) {
            System.out.println("Registration successful!");
        } else {
            System.out.println("Username already exists.");
        }
    }

    private void login() {

        System.out.println("\n===== Login =====");

        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        var user = userRepository.login(username, password);

        if (user == null) {
            System.out.println("Invalid username or password.");
            return;
        }

        System.out.println("\nLogin successful!");
        System.out.println("Welcome, " + user.getUsername());
        System.out.println("Role: " + user.getRole());

        switch (user.getRole()) {
            case AUTHOR -> blogRepository.findBlogsByUserId(user.getId());
            case GUEST -> guestDashboard();
            case ADMIN -> userRepository.findAuthors();
        }
    }

    private void guestDashboard() {

        int page = 1;
        int pageSize = 2;

        while (true) {

            System.out.println("\n===== Blogs - Page " + page + " =====");

            blogRepository.findBlogsByPage(page, pageSize);

            System.out.println("\n1. Next page");
            System.out.println("2. Previous page");
            System.out.println("3. Back to main menu");
            System.out.print("Choose: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> page++;
                case "2" -> {
                    if (page > 1) {
                        page--;
                    } else {
                        System.out.println("Already on first page.");
                    }
                }
                case "3" -> {
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

}