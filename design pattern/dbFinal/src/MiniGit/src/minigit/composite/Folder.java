package minigit.composite;

import java.util.ArrayList;
import java.util.List;

// Composite Pattern - Composite
public class Folder implements Element
{
    private String name;
    private List<Element> children;

    public Folder(String name)
    {
        this.name = name;
        this.children = new ArrayList<>();
    }

    @Override
    public int getSize()
    {
        int total = 0;

        for (Element child : children)
        {
            total += child.getSize();
        }

        return total;
    }

    public void add(Element element)
    {
        children.add(element);
    }

    public void remove(Element element)
    {
        children.remove(element);
    }

    public List<Element> getChildren()
    {
        return children;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return name;
    }
}
