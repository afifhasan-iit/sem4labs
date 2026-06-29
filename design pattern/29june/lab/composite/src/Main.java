
import java.util.Scanner;
public class Main {
    Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {

        FileExplorer explorer = new FileExplorer();
        Folder root = explorer.root;


        Demo.demoFileExplorer(root);
        explorer.display();


        System.out.println("\n\n\n");
        explorer.deleteFile("file1");
        System.out.println("after deleting: ");
        explorer.display();


        SearchManager.searchFile(root,"file4");

        System.out.println("Size is: " + explorer.getSize());


        Folder newFolder =  root.addFolder("folder1");
        newFolder.addFile(100,"marvel");
        newFolder.display();

        System.out.println("Size is: " + root.getSize());





    }




}
