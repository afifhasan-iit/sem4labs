package minigit.prototype;

import minigit.composite.Element;
import minigit.composite.Files;
import minigit.composite.Folder;
import minigit.iterator.FileIterator;
import minigit.memento.CommitMemento;
import minigit.memento.GitHistory;
import minigit.observer.Observer;
import minigit.strategy.MergeStrategy;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Prototype Pattern + Repository
public class Repository implements Cloneable
{
    private String name;

    private Folder root;

    private Map<String, Branch> branches;

    private Branch currentBranch;

    // Strategy Pattern
    private MergeStrategy strategy;

    // Observer Pattern
    private List<Observer> tasks;

    public Repository(String name)
    {
        this.name = name;
        this.root = new Folder(name);

        branches = new HashMap<>();
        tasks = new ArrayList<>();

        Branch main = new Branch(
                "main",
                root
        );

        branches.put("main", main);
        currentBranch = main;
    }

    public String getName()
    {
        return name;
    }

    public Folder getRoot()
    {
        return currentBranch.getProject();
    }

    public Branch getCurrentBranch()
    {
        return currentBranch;
    }

    public Map<String, Branch> getBranches()
    {
        return branches;
    }

    // --------------------------------------------------
    // Composite operations
    // --------------------------------------------------

    public void addElement(Folder folder, Element element)
    {
        folder.add(element);
    }

    public void removeElement(Folder folder, Element element)
    {
        folder.remove(element);
    }

    public Files findFile(String path)
    {
        String[] parts = path.split("/");

        if (parts.length == 0)
        {
            return null;
        }

        Folder folder = currentBranch.getProject();

        int start = 0;

        if (parts[0].equals(folder.getName()))
        {
            start = 1;
        }

        for (int i = start; i < parts.length - 1; i++)
        {
            Element found = findChild(
                    folder,
                    parts[i]
            );

            if (!(found instanceof Folder))
            {
                return null;
            }

            folder = (Folder) found;
        }

        Element result =
                findChild(
                        folder,
                        parts[parts.length - 1]
                );

        if (result instanceof Files)
        {
            return (Files) result;
        }

        return null;
    }

    public Folder findFolder(String path)
    {
        if (path.equals(currentBranch.getProject().getName())
                || path.equals(""))
        {
            return currentBranch.getProject();
        }

        String[] parts = path.split("/");

        Folder folder = currentBranch.getProject();

        int start = 0;

        if (parts[0].equals(folder.getName()))
        {
            start = 1;
        }

        for (int i = start; i < parts.length; i++)
        {
            Element found =
                    findChild(folder, parts[i]);

            if (!(found instanceof Folder))
            {
                return null;
            }

            folder = (Folder) found;
        }

        return folder;
    }

    private Element findChild(
            Folder folder,
            String name)
    {
        for (Element element : folder.getChildren())
        {
            if (element instanceof Files)
            {
                Files file = (Files) element;

                if (file.getName().equals(name))
                {
                    return file;
                }
            }
            else if (element instanceof Folder)
            {
                Folder child = (Folder) element;

                if (child.getName().equals(name))
                {
                    return child;
                }
            }
        }

        return null;
    }

    public boolean delete(String path)
    {
        if (path == null || path.isEmpty())
        {
            return false;
        }

        String[] parts = path.split("/");

        Folder parent = currentBranch.getProject();

        int start = 0;

        if (parts[0].equals(parent.getName()))
        {
            start = 1;
        }

        if (start >= parts.length)
        {
            return false;
        }

        for (int i = start; i < parts.length - 1; i++)
        {
            Element element =
                    findChild(parent, parts[i]);

            if (!(element instanceof Folder))
            {
                return false;
            }

            parent = (Folder) element;
        }

        Element target =
                findChild(
                        parent,
                        parts[parts.length - 1]
                );

        if (target == null)
        {
            return false;
        }

        parent.remove(target);

        return true;
    }

    public boolean rename(String path, String newName)
    {
        Element element = findElement(path);

        if (element == null)
        {
            return false;
        }

        if (element instanceof Files)
        {
            ((Files) element).setName(newName);
        }
        else
        {
            ((Folder) element).setName(newName);
        }

        return true;
    }

    private Element findElement(String path)
    {
        Files file = findFile(path);

        if (file != null)
        {
            return file;
        }

        return findFolder(path);
    }

    public boolean move(String sourcePath, String destinationFolderPath)
    {
        Element element = findElement(sourcePath);

        if (element == null)
        {
            return false;
        }

        Folder destination =
                findFolder(destinationFolderPath);

        if (destination == null)
        {
            return false;
        }

        String[] parts = sourcePath.split("/");

        Folder parent = currentBranch.getProject();

        int start = 0;

        if (parts[0].equals(parent.getName()))
        {
            start = 1;
        }

        for (int i = start; i < parts.length - 1; i++)
        {
            Element child =
                    findChild(parent, parts[i]);

            if (!(child instanceof Folder))
            {
                return false;
            }

            parent = (Folder) child;
        }

        parent.remove(element);
        destination.add(element);

        return true;
    }

    public int countFiles()
    {
        FileIterator iterator =
                new FileIterator(
                        currentBranch.getProject()
                );

        return iterator.getFiles().size();
    }

    public int getTotalSize()
    {
        return currentBranch.getProject().getSize();
    }

    public List<String> findFilesByExtension(String extension)
    {
        List<String> result = new ArrayList<>();

        FileIterator iterator =
                new FileIterator(
                        currentBranch.getProject()
                );

        for (FileIterator.FileInfo info :
                iterator.getFiles())
        {
            if (info.getFile().hasExtension(extension))
            {
                result.add(info.getPath());
            }
        }

        return result;
    }

    // --------------------------------------------------
    // Memento Pattern
    // --------------------------------------------------

    public CommitMemento save(
            String message,
            String author)
    {
        Folder snapshot =
                deepCopyFolder(
                        currentBranch.getProject()
                );

        CommitMemento parent =
                currentBranch.getHead();

        CommitMemento commit =
                new CommitMemento(
                        message,
                        author,
                        LocalDateTime.now(),
                        snapshot,
                        parent
                );

        commit.store();

        currentBranch.getHistory().save(commit);
        currentBranch.setHead(commit);

        fireTask(message);

        return commit;
    }

    // Restores the parent of the current commit.
    public boolean restore()
    {
        CommitMemento head =
                currentBranch.getHead();

        if (head == null)
        {
            System.out.println("No commits available.");
            return false;
        }

        CommitMemento previous =
                head.getParent();

        if (previous == null)
        {
            System.out.println("There is no previous commit.");
            return false;
        }

        currentBranch.setProject(
                deepCopyFolder(
                        previous.getData()
                )
        );

        System.out.println(
                "Restored to: " +
                previous.getMessage()
        );

        return true;
    }

    public void restore(CommitMemento commit)
    {
        if (commit == null)
        {
            return;
        }

        currentBranch.setProject(
                deepCopyFolder(
                        commit.getData()
                )
        );
    }

    // --------------------------------------------------
    // Branching
    // --------------------------------------------------

    public boolean createBranch(String branchName)
    {
        if (branchName == null ||
                branchName.trim().isEmpty())
        {
            return false;
        }

        if (branches.containsKey(branchName))
        {
            return false;
        }

        CommitMemento head =
                currentBranch.getHead();

        Folder project;

        if (head != null)
        {
            project =
                    deepCopyFolder(
                            head.getData()
                    );
        }
        else
        {
            project =
                    deepCopyFolder(
                            currentBranch.getProject()
                    );
        }

        Branch newBranch =
                new Branch(
                        branchName,
                        project,
                        head
                );

        branches.put(
                branchName,
                newBranch
        );

        return true;
    }

    public boolean switchBranch(String branchName)
    {
        Branch branch =
                branches.get(branchName);

        if (branch == null)
        {
            return false;
        }

        currentBranch = branch;

        return true;
    }

    // --------------------------------------------------
    // Prototype Pattern
    // --------------------------------------------------

    @Override
    public Repository clone()
    {
        Repository copy =
                new Repository(name);

        copy.branches.clear();

        for (Map.Entry<String, Branch> entry :
                branches.entrySet())
        {
            Branch original =
                    entry.getValue();

            Branch branchCopy =
                    new Branch(
                            original.getName(),
                            deepCopyFolder(
                                    original.getProject()
                            ),
                            original.getHead()
                    );

            copy.branches.put(
                    branchCopy.getName(),
                    branchCopy
            );

            if (original == currentBranch)
            {
                copy.currentBranch = branchCopy;
            }
        }

        return copy;
    }

    // --------------------------------------------------
    // Deep Copy
    // --------------------------------------------------

    public static Folder deepCopyFolder(Folder original)
    {
        Folder copy =
                new Folder(
                        original.getName()
                );

        for (Element element :
                original.getChildren())
        {
            if (element instanceof Files)
            {
                Files file =
                        (Files) element;

                copy.add(
                        new Files(
                                file.getName(),
                                file.getContent()
                        )
                );
            }
            else if (element instanceof Folder)
            {
                copy.add(
                        deepCopyFolder(
                                (Folder) element
                        )
                );
            }
        }

        return copy;
    }

    // --------------------------------------------------
    // Iterator Pattern
    // --------------------------------------------------

    public List<String> compareCommits(
            CommitMemento first,
            CommitMemento second)
    {
        if (first == null || second == null)
        {
            return new ArrayList<>();
        }

        FileIterator firstIterator =
                new FileIterator(
                        first.getData()
                );

        FileIterator secondIterator =
                new FileIterator(
                        second.getData()
                );

        return firstIterator.compare(
                secondIterator
        );
    }

    // --------------------------------------------------
    // Strategy Pattern - Merge
    // --------------------------------------------------

    public void setStrategy(MergeStrategy strategy)
    {
        this.strategy = strategy;
    }

    public MergeStrategy getStrategy()
    {
        return strategy;
    }

    public MergeReport merge(
            String sourceBranchName)
    {
        MergeReport report =
                new MergeReport();

        Branch source =
                branches.get(sourceBranchName);

        if (source == null)
        {
            report.messages.add(
                    "Source branch does not exist."
            );

            return report;
        }

        if (source == currentBranch)
        {
            report.messages.add(
                    "Cannot merge a branch into itself."
            );

            return report;
        }

        if (strategy == null)
        {
            report.messages.add(
                    "No merge strategy selected."
            );

            return report;
        }

        Folder sourceProject =
                source.getProject();

        Folder destinationProject =
                currentBranch.getProject();

        FileIterator sourceIterator =
                new FileIterator(sourceProject);

        FileIterator destinationIterator =
                new FileIterator(destinationProject);

        Map<String, FileIterator.FileInfo> sourceFiles =
                createFileMap(sourceIterator);

        Map<String, FileIterator.FileInfo> destinationFiles =
                createFileMap(destinationIterator);

        List<String> allPaths =
                new ArrayList<>();

        allPaths.addAll(sourceFiles.keySet());

        for (String path :
                destinationFiles.keySet())
        {
            if (!allPaths.contains(path))
            {
                allPaths.add(path);
            }
        }

        for (String path : allPaths)
        {
            boolean inSource =
                    sourceFiles.containsKey(path);

            boolean inDestination =
                    destinationFiles.containsKey(path);

            if (inSource && !inDestination)
            {
                addFileToProject(
                        destinationProject,
                        path,
                        sourceFiles.get(path).getContent()
                );

                report.added.add(path);
            }
            else if (!inSource && inDestination)
            {
                report.deleted.add(path);
            }
            else
            {
                String sourceContent =
                        sourceFiles.get(path).getContent();

                String destinationContent =
                        destinationFiles.get(path).getContent();

                if (sourceContent.equals(destinationContent))
                {
                    report.unchanged.add(path);
                }
                else
                {
                    Files sourceFile =
                            sourceFiles.get(path).getFile();

                    Files destinationFile =
                            destinationFiles.get(path).getFile();

                    Files result =
                            strategy.applyStrategy(
                                    sourceFile,
                                    destinationFile
                            );

                    replaceFile(
                            destinationProject,
                            path,
                            result
                    );

                    report.conflicts.add(path);
                    report.resolved.add(path);
                }
            }
        }

        return report;
    }

    private Map<String, FileIterator.FileInfo> createFileMap(
            FileIterator iterator)
    {
        Map<String, FileIterator.FileInfo> map =
                new HashMap<>();

        for (FileIterator.FileInfo info :
                iterator.getFiles())
        {
            map.put(info.getPath(), info);
        }

        return map;
    }

    private void addFileToProject(
            Folder root,
            String path,
            String content)
    {
        String[] parts =
                path.split("/");

        Folder folder = root;

        int start = 0;

        if (parts.length > 0 &&
                parts[0].equals(root.getName()))
        {
            start = 1;
        }

        for (int i = start;
             i < parts.length - 1;
             i++)
        {
            Element child =
                    findChild(
                            folder,
                            parts[i]
                    );

            if (child instanceof Folder)
            {
                folder = (Folder) child;
            }
            else
            {
                Folder newFolder =
                        new Folder(parts[i]);

                folder.add(newFolder);
                folder = newFolder;
            }
        }

        folder.add(
                new Files(
                        parts[parts.length - 1],
                        content
                )
        );
    }

    private void replaceFile(
            Folder root,
            String path,
            Files selected)
    {
        String[] parts =
                path.split("/");

        Folder folder = root;

        int start = 0;

        if (parts[0].equals(root.getName()))
        {
            start = 1;
        }

        for (int i = start;
             i < parts.length - 1;
             i++)
        {
            Element child =
                    findChild(
                            folder,
                            parts[i]
                    );

            if (!(child instanceof Folder))
            {
                return;
            }

            folder = (Folder) child;
        }

        Element old =
                findChild(
                        folder,
                        parts[parts.length - 1]
                );

        if (old instanceof Files)
        {
            folder.remove(old);

            folder.add(
                    new Files(
                            selected.getName(),
                            selected.getContent()
                    )
            );
        }
    }

    // --------------------------------------------------
    // Observer Pattern
    // --------------------------------------------------

    public void addObserver(Observer observer)
    {
        tasks.add(observer);
    }

    public void fireTask(String message)
    {
        for (Observer observer : tasks)
        {
            observer.onCommit(message);
        }
    }

    // --------------------------------------------------
    // Utility methods
    // --------------------------------------------------

    public List<CommitMemento> getAllCommits()
    {
        List<CommitMemento> result =
                new ArrayList<>();

        CommitMemento current =
                currentBranch.getHead();

        while (current != null)
        {
            result.add(current);
            current = current.getParent();
        }

        return result;
    }

    public CommitMemento getCommit(int index)
    {
        List<CommitMemento> commits =
                getAllCommits();

        if (index < 0 ||
                index >= commits.size())
        {
            return null;
        }

        return commits.get(index);
    }

    // --------------------------------------------------
    // Merge result
    // --------------------------------------------------

    public static class MergeReport
    {
        private List<String> added =
                new ArrayList<>();

        private List<String> deleted =
                new ArrayList<>();

        private List<String> unchanged =
                new ArrayList<>();

        private List<String> conflicts =
                new ArrayList<>();

        private List<String> resolved =
                new ArrayList<>();

        private List<String> messages =
                new ArrayList<>();

        public void print()
        {
            System.out.println("\n===== MERGE RESULT =====");

            printList("Added", added);
            printList("Deleted", deleted);
            printList("Unchanged", unchanged);
            printList("Conflicts", conflicts);
            printList("Resolved", resolved);

            for (String message : messages)
            {
                System.out.println(message);
            }

            System.out.println("========================\n");
        }

        private void printList(
                String title,
                List<String> list)
        {
            System.out.println(title + ":");

            if (list.isEmpty())
            {
                System.out.println("  None");
                return;
            }

            for (String item : list)
            {
                System.out.println("  " + item);
            }
        }
    }
}