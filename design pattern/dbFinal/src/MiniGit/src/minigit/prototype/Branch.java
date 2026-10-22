package minigit.prototype;

import minigit.composite.Folder;
import minigit.memento.CommitMemento;
import minigit.memento.GitHistory;

// Represents a branch in the repository
public class Branch
{
    private String name;
    private Folder project;
    private GitHistory history;
    private CommitMemento head;

    public Branch(String name, Folder project)
    {
        this.name = name;
        this.project = project;
        this.history = new GitHistory();
        this.head = null;
    }

    public Branch(
            String name,
            Folder project,
            CommitMemento head)
    {
        this.name = name;
        this.project = project;
        this.head = head;

        this.history = new GitHistory();
        this.history.addExistingCommits(head);
    }

    public String getName()
    {
        return name;
    }

    public Folder getProject()
    {
        return project;
    }

    public void setProject(Folder project)
    {
        this.project = project;
    }

    public GitHistory getHistory()
    {
        return history;
    }

    public CommitMemento getHead()
    {
        return head;
    }

    public void setHead(CommitMemento head)
    {
        this.head = head;
    }
}
