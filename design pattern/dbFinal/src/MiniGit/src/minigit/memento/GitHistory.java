package minigit.memento;

import java.util.Stack;

// Memento Pattern - Caretaker
public class GitHistory
{
    private Stack<CommitMemento> commits;

    public GitHistory()
    {
        commits = new Stack<>();
    }

    public void save(CommitMemento commit)
    {
        commits.push(commit);
    }

    public CommitMemento previous()
    {
        if (commits.isEmpty())
        {
            return null;
        }

        return commits.peek();
    }

    public Stack<CommitMemento> getCommits()
    {
        return commits;
    }

    public void addExistingCommits(CommitMemento commit)
    {
        if (commit == null)
        {
            return;
        }

        addExistingCommits(commit.getParent());

        commits.push(commit);
    }
}
