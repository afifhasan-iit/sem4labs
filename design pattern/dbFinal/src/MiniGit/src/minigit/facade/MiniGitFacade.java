package minigit.facade;

import minigit.composite.Files;
import minigit.composite.Folder;
import minigit.memento.CommitMemento;
import minigit.observer.Refresh;
import minigit.observer.UI;
import minigit.prototype.Branch;
import minigit.prototype.Repository;
import minigit.strategy.DestinationPreferred;
import minigit.strategy.SourcePreferred;

import java.util.ArrayList;
import java.util.List;

// Facade Pattern
public class MiniGitFacade
{
    private List<Repository> repositories;
    private Repository currentRepository;

    public MiniGitFacade()
    {
        repositories = new ArrayList<>();
        currentRepository = null;
    }

    // =========================
    // Repository Operations
    // =========================

    public Repository createRepository(String name)
    {
        Repository repository = new Repository(name);

        repository.addObserver(new Refresh());
        repository.addObserver(new UI());

        repository.setStrategy(new SourcePreferred());

        repositories.add(repository);
        currentRepository = repository;

        return repository;
    }

    public List<Repository> getRepositories()
    {
        return repositories;
    }

    public boolean selectRepository(int index)
    {
        if (index < 0 || index >= repositories.size())
        {
            return false;
        }

        currentRepository = repositories.get(index);
        return true;
    }

    public Repository getCurrentRepository()
    {
        return currentRepository;
    }

    // =========================
    // Project Operations
    // =========================

    public boolean createFile(
            String folderPath,
            String fileName,
            String content)
    {
        if (currentRepository == null)
        {
            return false;
        }

        Folder folder =
                currentRepository.findFolder(folderPath);

        if (folder == null)
        {
            return false;
        }

        currentRepository.addElement(
                folder,
                new Files(fileName, content)
        );

        return true;
    }

    public boolean createFolder(
            String parentPath,
            String folderName)
    {
        if (currentRepository == null)
        {
            return false;
        }

        Folder folder =
                currentRepository.findFolder(parentPath);

        if (folder == null)
        {
            return false;
        }

        currentRepository.addElement(
                folder,
                new Folder(folderName)
        );

        return true;
    }

    public boolean modifyFile(
            String path,
            String content)
    {
        if (currentRepository == null)
        {
            return false;
        }

        Files file =
                currentRepository.findFile(path);

        if (file == null)
        {
            return false;
        }

        file.setContent(content);

        return true;
    }

    public boolean rename(
            String path,
            String newName)
    {
        if (currentRepository == null)
        {
            return false;
        }

        return currentRepository.rename(
                path,
                newName
        );
    }

    public boolean move(
            String sourcePath,
            String destinationPath)
    {
        if (currentRepository == null)
        {
            return false;
        }

        return currentRepository.move(
                sourcePath,
                destinationPath
        );
    }

    public boolean delete(String path)
    {
        if (currentRepository == null)
        {
            return false;
        }

        return currentRepository.delete(path);
    }

    public Folder getProject()
    {
        if (currentRepository == null)
        {
            return null;
        }

        return currentRepository.getRoot();
    }

    // =========================
    // Commit Operations
    // =========================

    public CommitMemento commit(
            String message,
            String author)
    {
        if (currentRepository == null)
        {
            return null;
        }

        return currentRepository.save(
                message,
                author
        );
    }

    public List<CommitMemento> getHistory()
    {
        if (currentRepository == null)
        {
            return new ArrayList<>();
        }

        return currentRepository.getAllCommits();
    }

    public boolean restorePrevious()
    {
        if (currentRepository == null)
        {
            return false;
        }

        return currentRepository.restore();
    }

    // =========================
    // Branch Operations
    // =========================

    public boolean createBranch(String name)
    {
        if (currentRepository == null)
        {
            return false;
        }

        return currentRepository.createBranch(name);
    }

    public boolean switchBranch(String name)
    {
        if (currentRepository == null)
        {
            return false;
        }

        return currentRepository.switchBranch(name);
    }

    public String getCurrentBranch()
    {
        if (currentRepository == null)
        {
            return null;
        }

        Branch branch =
                currentRepository.getCurrentBranch();

        if (branch == null)
        {
            return null;
        }

        return branch.getName();
    }

    public List<String> getBranchNames()
    {
        List<String> names =
                new ArrayList<>();

        if (currentRepository == null)
        {
            return names;
        }

        for (Branch branch :
                currentRepository.getBranches().values())
        {
            names.add(branch.getName());
        }

        return names;
    }

    // =========================
    // Comparison
    // =========================

    public List<String> compareCommits(
            int firstCommit,
            int secondCommit)
    {
        if (currentRepository == null)
        {
            return new ArrayList<>();
        }

        CommitMemento first =
                currentRepository.getCommit(firstCommit);

        CommitMemento second =
                currentRepository.getCommit(secondCommit);

        if (first == null || second == null)
        {
            return new ArrayList<>();
        }

        return currentRepository.compareCommits(
                first,
                second
        );
    }

    // =========================
    // Merge
    // =========================

    public Repository.MergeReport merge(
            String sourceBranch,
            int strategyChoice)
    {
        if (currentRepository == null)
        {
            return null;
        }

        if (strategyChoice == 1)
        {
            currentRepository.setStrategy(
                    new SourcePreferred()
            );
        }
        else
        {
            currentRepository.setStrategy(
                    new DestinationPreferred()
            );
        }

        return currentRepository.merge(
                sourceBranch
        );
    }

    // =========================
    // Statistics
    // =========================

    public int countFiles()
    {
        if (currentRepository == null)
        {
            return 0;
        }

        return currentRepository.countFiles();
    }

    public int getTotalSize()
    {
        if (currentRepository == null)
        {
            return 0;
        }

        return currentRepository.getTotalSize();
    }

    public List<String> findFilesByExtension(
            String extension)
    {
        if (currentRepository == null)
        {
            return new ArrayList<>();
        }

        return currentRepository.findFilesByExtension(
                extension
        );
    }
}