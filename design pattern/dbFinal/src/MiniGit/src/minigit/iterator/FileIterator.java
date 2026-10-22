package minigit.iterator;

import minigit.composite.Element;
import minigit.composite.Files;
import minigit.composite.Folder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Iterator Pattern
public class FileIterator implements iterator
{
    private List<FileInfo> files;
    private int position;

    public FileIterator(Folder root)
    {
        files = new ArrayList<>();
        position = 0;

        collectFiles(root, "");
    }

    private void collectFiles(Folder folder, String currentPath)
    {
        String folderPath;

        if (currentPath.isEmpty())
        {
            folderPath = "";
        }
        else
        {
            folderPath = currentPath + "/";
        }

        for (Element element : folder.getChildren())
        {
            if (element instanceof Files)
            {
                Files file = (Files) element;

                String filePath = folderPath + file.getName();

                files.add(
                        new FileInfo(
                                filePath,
                                file.getContent(),
                                file
                        )
                );
            }
            else if (element instanceof Folder)
            {
                Folder childFolder = (Folder) element;

                String childPath = folderPath + childFolder.getName();

                collectFiles(childFolder, childPath);
            }
        }
    }

    @Override
    public boolean hasNext()
    {
        return position < files.size();
    }

    @Override
    public Object next()
    {
        if (!hasNext())
        {
            return null;
        }

        return files.get(position++);
    }

    public List<FileInfo> getFiles()
    {
        return files;
    }

    public List<String> compare(FileIterator other)
    {
        Map<String, String> first = new HashMap<>();
        Map<String, String> second = new HashMap<>();

        for (FileInfo file : files)
        {
            first.put(file.getPath(), file.getContent());
        }

        for (FileInfo file : other.files)
        {
            second.put(file.getPath(), file.getContent());
        }

        List<String> allPaths = new ArrayList<>();

        for (String path : first.keySet())
        {
            allPaths.add(path);
        }

        for (String path : second.keySet())
        {
            if (!allPaths.contains(path))
            {
                allPaths.add(path);
            }
        }

        List<String> result = new ArrayList<>();

        for (String path : allPaths)
        {
            if (!first.containsKey(path))
            {
                result.add("Added: " + path);
            }
            else if (!second.containsKey(path))
            {
                result.add("Deleted: " + path);
            }
            else if (!first.get(path).equals(second.get(path)))
            {
                result.add("Modified: " + path);
            }
            else
            {
                result.add("Unchanged: " + path);
            }
        }

        return result;
    }

    // Stores path, content and actual file reference.
    public static class FileInfo
    {
        private String path;
        private String content;
        private Files file;

        public FileInfo(String path, String content, Files file)
        {
            this.path = path;
            this.content = content;
            this.file = file;
        }

        public String getPath()
        {
            return path;
        }

        public String getContent()
        {
            return content;
        }

        public Files getFile()
        {
            return file;
        }
    }
}
