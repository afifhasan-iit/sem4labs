package minigit.memento;

import minigit.composite.Folder;

import java.time.LocalDateTime;

// Memento Pattern
public class CommitMemento
{
    private String message;
    private String author;
    private LocalDateTime timestamp;
    private Folder snapshot;
    private CommitMemento parent;

    public CommitMemento(
            String message,
            String author,
            LocalDateTime timestamp,
            Folder snapshot,
            CommitMemento parent)
    {
        this.message = message;
        this.author = author;
        this.timestamp = timestamp;
        this.snapshot = snapshot;
        this.parent = parent;
    }

    public void store()
    {
        // The snapshot is already stored in this memento.
    }

    public Folder getData()
    {
        return snapshot;
    }

    public String getMessage()
    {
        return message;
    }

    public String getAuthor()
    {
        return author;
    }

    public LocalDateTime getTimestamp()
    {
        return timestamp;
    }

    public CommitMemento getParent()
    {
        return parent;
    }

    @Override
    public String toString()
    {
        return message + " | " + author + " | " + timestamp;
    }
}
