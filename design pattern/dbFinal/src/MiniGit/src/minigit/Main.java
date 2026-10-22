package minigit;

import minigit.composite.Element;
import minigit.composite.Files;
import minigit.composite.Folder;
import minigit.facade.MiniGitFacade;
import minigit.memento.CommitMemento;
import minigit.prototype.Repository;

import java.util.List;
import java.util.Scanner;

public class Main
{
    private static Scanner scanner = new Scanner(System.in);
    private static MiniGitFacade git = new MiniGitFacade();

    public static void main(String[] args)
    {
        mainMenu();
    }

    // =========================
    // Main Menu
    // =========================

    public static void mainMenu()
    {
        while (true)
        {
            System.out.println("\n===== Mini Git =====");
            System.out.println("1. Create Repository");
            System.out.println("2. Select Repository");
            System.out.println("3. Exit");

            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1)
            {
                createRepository();
            }
            else if (choice == 2)
            {
                selectRepository();
            }
            else if (choice == 3)
            {
                System.out.println("Goodbye.");
                break;
            }
            else
            {
                System.out.println("Invalid choice.");
            }
        }
    }

    // =========================
    // Repository
    // =========================

    public static void createRepository()
    {
        System.out.print("Enter repository name: ");
        String name = scanner.nextLine();

        git.createRepository(name);

        System.out.println("Repository created.");
        repositoryMenu();
    }

    public static void selectRepository()
    {
        List<Repository> repositories = git.getRepositories();

        if (repositories.isEmpty())
        {
            System.out.println("No repositories available.");
            return;
        }

        System.out.println("\nAvailable Repositories:");

        for (int i = 0; i < repositories.size(); i++)
        {
            System.out.println(
                    (i + 1) + ". " + repositories.get(i).getName()
            );
        }

        System.out.print("Select repository: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (git.selectRepository(choice - 1))
        {
            repositoryMenu();
        }
        else
        {
            System.out.println("Invalid repository.");
        }
    }

    // =========================
    // Repository Menu
    // =========================

    public static void repositoryMenu()
    {
        while (true)
        {
            System.out.println(
                    "\n===== Repository: "
                            + git.getCurrentRepository().getName()
                            + " ====="
            );

            System.out.println(
                    "Current Branch: [" + git.getCurrentBranch() + "]"
            );

            System.out.println("1. View Project");
            System.out.println("2. Create File");
            System.out.println("3. Create Folder");
            System.out.println("4. Modify File");
            System.out.println("5. Rename");
            System.out.println("6. Move");
            System.out.println("7. Delete");
            System.out.println("8. Commit");
            System.out.println("9. Create Branch");
            System.out.println("10. Switch Branch");
            System.out.println("11. View History");
            System.out.println("12. Compare Commits");
            System.out.println("13. Restore Previous Commit");
            System.out.println("14. Merge Branch");
            System.out.println("15. Statistics");
            System.out.println("16. Find Files By Extension");
            System.out.println("17. Find Total Size");
            System.out.println("18. Back");


            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice)
            {
                case 1:
                    viewProject();
                    break;

                case 2:
                    createFile();
                    break;

                case 3:
                    createFolder();
                    break;

                case 4:
                    modifyFile();
                    break;

                case 5:
                    rename();
                    break;

                case 6:
                    move();
                    break;

                case 7:
                    delete();
                    break;

                case 8:
                    commit();
                    break;

                case 9:
                    createBranch();
                    break;

                case 10:
                    switchBranch();
                    break;

                case 11:
                    viewHistory();
                    break;

                case 12:
                    compareCommits();
                    break;

                case 13:
                    restorePrevious();
                    break;

                case 14:
                    merge();
                    break;

                case 15:
                    statistics();
                    break;

                case 16:
                    findByExtension();
                    break;

                case 17:
                    System.out.println("Total Size: " + git.getTotalSize() + " bytes");
                    break;

                case 18:
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    // =========================
    // Project Operations
    // =========================

    public static void viewProject()
    {
        System.out.println("\nProject Structure:");

        Folder root = git.getProject();

        printFolder(root, 0);
    }

    public static void printFolder(
            Folder folder,
            int level)
    {
        for (Element element : folder.getChildren())
        {
            for (int i = 0; i < level; i++)
            {
                System.out.print("  ");
            }

            if (element instanceof Files)
            {
                Files file = (Files) element;

                System.out.println(
                        "File: " + file.getName()
                );
            }
            else if (element instanceof Folder)
            {
                Folder child = (Folder) element;

                System.out.println(
                        "Folder: " + child.getName()
                );

                printFolder(
                        child,
                        level + 1
                );
            }
        }
    }

    public static void createFile()
    {
        System.out.print("Enter folder path: ");
        String folderPath = scanner.nextLine();

        System.out.print("Enter file name: ");
        String fileName = scanner.nextLine();

        System.out.print("Enter content: ");
        String content = scanner.nextLine();

        if (git.createFile(
                folderPath,
                fileName,
                content))
        {
            System.out.println("File created.");
        }
        else
        {
            System.out.println("Failed to create file.");
        }
    }

    public static void createFolder()
    {
        System.out.print("Enter parent folder path: ");
        String parentPath = scanner.nextLine();

        System.out.print("Enter folder name: ");
        String folderName = scanner.nextLine();

        if (git.createFolder(
                parentPath,
                folderName))
        {
            System.out.println("Folder created.");
        }
        else
        {
            System.out.println("Failed to create folder.");
        }
    }

    public static void modifyFile()
    {
        System.out.print("Enter file path: ");
        String path = scanner.nextLine();

        System.out.print("Enter new content: ");
        String content = scanner.nextLine();

        if (git.modifyFile(path, content))
        {
            System.out.println("File modified.");
        }
        else
        {
            System.out.println("File not found.");
        }
    }

    public static void rename()
    {
        System.out.print("Enter path: ");
        String path = scanner.nextLine();

        System.out.print("Enter new name: ");
        String newName = scanner.nextLine();

        if (git.rename(path, newName))
        {
            System.out.println("Renamed successfully.");
        }
        else
        {
            System.out.println("Rename failed.");
        }
    }

    public static void move()
    {
        System.out.print("Enter source path: ");
        String source = scanner.nextLine();

        System.out.print("Enter destination folder path: ");
        String destination = scanner.nextLine();

        if (git.move(source, destination))
        {
            System.out.println("Moved successfully.");
        }
        else
        {
            System.out.println("Move failed.");
        }
    }

    public static void delete()
    {
        System.out.print("Enter path to delete: ");
        String path = scanner.nextLine();

        if (git.delete(path))
        {
            System.out.println("Deleted successfully.");
        }
        else
        {
            System.out.println("Delete failed.");
        }
    }

    // =========================
    // Commit
    // =========================

    public static void commit()
    {
        System.out.print("Enter commit message: ");
        String message = scanner.nextLine();

        System.out.print("Enter author: ");
        String author = scanner.nextLine();

        CommitMemento commit =
                git.commit(message, author);

        if (commit != null)
        {
            System.out.println("Commit created.");
        }
        else
        {
            System.out.println("Commit failed.");
        }
    }

    // =========================
    // Branch
    // =========================

    public static void createBranch()
    {
        System.out.print("Enter branch name: ");
        String name = scanner.nextLine();

        if (git.createBranch(name))
        {
            System.out.println("Branch created.");
        }
        else
        {
            System.out.println("Branch creation failed.");
        }
    }

    public static void switchBranch()
    {
        List<String> branches = git.getBranchNames();

        System.out.println("\nBranches:");

        for (String branch : branches)
        {
            System.out.println("- " + branch);
        }

        System.out.print("Enter branch name: ");
        String name = scanner.nextLine();

        if (git.switchBranch(name))
        {
            System.out.println("Switched to branch: " + name);
        }
        else
        {
            System.out.println("Branch not found.");
        }
    }

    // =========================
    // History
    // =========================

    public static void viewHistory()
    {
        List<CommitMemento> commits =
                git.getHistory();

        if (commits.isEmpty())
        {
            System.out.println("No commits.");
            return;
        }

        System.out.println("\nCommit History:");

        for (int i = 0; i < commits.size(); i++)
        {
            System.out.println(
                    i + ". " + commits.get(i)
            );
        }
    }

    // =========================
    // Compare
    // =========================

    public static void compareCommits()
    {
        List<CommitMemento> commits =
                git.getHistory();

        if (commits.size() < 2)
        {
            System.out.println(
                    "At least two commits are required."
            );
            return;
        }

        viewHistory();

        System.out.print("Enter first commit index: ");
        int first = scanner.nextInt();

        System.out.print("Enter second commit index: ");
        int second = scanner.nextInt();

        scanner.nextLine();

        List<String> result =
                git.compareCommits(first, second);

        System.out.println("\nComparison:");

        for (String line : result)
        {
            System.out.println(line);
        }
    }

    // =========================
    // Restore
    // =========================

    public static void restorePrevious()
    {
        if (git.restorePrevious())
        {
            System.out.println(
                    "Previous commit restored into working state."
            );

            System.out.println(
                    "Create a new commit to save the restored state."
            );
        }
        else
        {
            System.out.println(
                    "No previous commit available."
            );
        }
    }

    // =========================
    // Merge
    // =========================

    public static void merge()
    {
        List<String> branches =
                git.getBranchNames();

        System.out.println("\nBranches:");

        for (String branch : branches)
        {
            System.out.println("- " + branch);
        }

        System.out.print(
                "Enter source branch to merge: "
        );

        String source = scanner.nextLine();

        System.out.println("\nMerge Strategy:");
        System.out.println("1. Source Preferred");
        System.out.println("2. Destination Preferred");

        System.out.print("Enter strategy: ");
        int strategy = scanner.nextInt();
        scanner.nextLine();

        Repository.MergeReport report =
                git.merge(source, strategy);

        if (report == null)
        {
            System.out.println("Merge failed.");
            return;
        }

        report.print();
    }

    // =========================
    // Statistics
    // =========================

    public static void statistics()
    {
        System.out.println(
                "Total files: " + git.countFiles()
        );

        System.out.println(
                "Total size: " + git.getTotalSize()
        );
    }

    // =========================
    // Extension Search
    // =========================

    public static void findByExtension()
    {
        System.out.print(
                "Enter extension (example .java): "
        );

        String extension = scanner.nextLine();

        List<String> files =
                git.findFilesByExtension(extension);

        if (files.isEmpty())
        {
            System.out.println("No matching files.");
            return;
        }

        System.out.println("\nMatching files:");

        for (String file : files)
        {
            System.out.println(file);
        }
    }
}