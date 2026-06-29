import java.util.ArrayList;

public interface FileSystemComponent {
    double getSize();
    String getName();
}

class File implements FileSystemComponent {
    double size;
    String name;
    @Override
    public double getSize() {
        return size;
    }
    @Override
    public String getName() {
        return name;
    }
    public File(double size, String name) {
        this.size = size;
        this.name = name;
    }
}
class Folder implements FileSystemComponent {
    double size=0;
    String name;
    ArrayList<FileSystemComponent> children = new ArrayList<>();
    @Override
    public double getSize() {
        for(FileSystemComponent child : children) {
            size = size + child.getSize();
        }
        return size;
    }
    @Override
    public String getName() {
        return name;
    }
    public Folder(double size, String name) {
        this.size = size;
        this.name = name;
    }
    public void addFile(FileSystemComponent childFolder) {
        this.children.add(childFolder);
    }
    public void addFile(int w ,String name) {
        this.children.add(new File(w,name));
    }
    public Folder addFolder(String name) {

        Folder child = new Folder(0, name);
        this.children.add( child);
        return child;
    }

    public void deleteFile(FileSystemComponent childFolder) {
        this.children.remove(childFolder);
    }
    public void display() {
        for (FileSystemComponent child : children) {
            System.out.println(child.getName());
        }
    }


}



class SearchManager{
   static FileSystemComponent searchFile(Folder root, String name){
        for(FileSystemComponent child : root.children){
            if(child.getName().equals(name)){
                System.out.println("File found");
                return child;
            }

        }
        System.out.println("File not found");
        return null;
    }
}


class Demo{
    public static void demoFileExplorer(Folder root ){
        FileSystemComponent downloads = new Folder(0,"downloads");
        FileSystemComponent documents = new Folder(0,"documents");
        FileSystemComponent file1 = new File(2,"file1");
        FileSystemComponent file2 = new File(3,"file2");
        FileSystemComponent file3 = new File(4,"file3");
        FileSystemComponent file4 = new File(5,"file4");

        root.addFile(downloads);
        root.addFile(documents);
        root.addFile(file1);
        root.addFile(file2);
        root.addFile(file3);
        root.addFile(file4);
    }
}