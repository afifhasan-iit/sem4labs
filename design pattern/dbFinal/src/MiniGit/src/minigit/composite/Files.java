package minigit.composite;

// Composite Pattern - Leaf
public class Files implements Element
{
    private String name;
    private String content;
    private int size;

    public Files(String name, String content)
    {
        this.name = name;
        this.content = content;
        this.size = content.length();
    }

    @Override
    public int getSize()
    {
        return size;
    }

    public boolean hasExtension(String ext)
    {
        return name.endsWith(ext);
    }

    public String getName()
    {
        return name;
    }

    public String getContent()
    {
        return content;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setContent(String content)
    {
        this.content = content;
        this.size = content.length();
    }

    @Override
    public String toString()
    {
        return name;
    }
}
