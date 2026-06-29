import java.util.ArrayList;

public class FileExplorer{
    ArrayList<FileSystemComponent> components = new ArrayList<>();

    Folder root = new Folder(0,"home");


    public void display(){
        for(FileSystemComponent child : root.children){
            System.out.println(child.getName());
        }
    }



    public void deleteFile(String name){
        for (int i = 0; i < root.children.size(); i++) {
            if (root.children.get(i).getName().equals(name)) {
                root.children.remove(i);
                break;
            }
        }
    }
    public void searchFile(String name){
        for(FileSystemComponent child : root.children){
            if(child.getName().equals(name)){
                System.out.println("File found");
                break;
            }

        }
        System.out.println("File not found");
    }

    public double getSize(){
        double size = 0;
        for(FileSystemComponent child : root.children){
            size += child.getSize();
        }
        return size;
    }

    public void addFolder(String name){
        Folder folder = new Folder(0,name);
        root.children.add(folder);
    }

}
