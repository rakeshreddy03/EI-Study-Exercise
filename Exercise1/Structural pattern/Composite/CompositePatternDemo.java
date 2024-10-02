// Component
import java.util.*;
abstract class FileSystemComponent {
    String name;

    public FileSystemComponent(String name) {
        this.name = name;
    }

    public abstract void display();
}

// Leaf (File)
class File extends FileSystemComponent {
    public File(String name) {
        super(name);
    }

    @Override
    public void display() {
        System.out.println("File: " + name);
    }
}

// Composite (Directory)
class Directory extends FileSystemComponent {
    private List<FileSystemComponent> components = new ArrayList<>();

    public Directory(String name) {
        super(name);
    }

    public void addComponent(FileSystemComponent component) {
        components.add(component);
    }

    public void removeComponent(FileSystemComponent component) {
        components.remove(component);
    }

    @Override
    public void display() {
        System.out.println("Directory: " + name);
        for (FileSystemComponent component : components) {
            component.display();
        }
    }
}

// Test the Composite pattern
public class CompositePatternDemo {
    public static void main(String[] args) {
        Directory root = new Directory("Root");
        File file1 = new File("File1.txt");
        File file2 = new File("File2.txt");
        Directory subDir = new Directory("SubDirectory");

        root.addComponent(file1);
        root.addComponent(subDir);
        subDir.addComponent(file2);

        root.display();
    }
}
