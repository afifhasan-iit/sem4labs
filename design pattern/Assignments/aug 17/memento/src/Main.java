import java.util.*;

class EditorMemento {
    private final String content;
    private final int cursorPosition;

    EditorMemento(String content, int cursorPosition) {
        this.content = content;
        this.cursorPosition = cursorPosition;
    }

    String getContent() { return content; }
    int getCursorPosition() { return cursorPosition; }
}

class TextEditor {
    private String content;
    private int cursorPosition;

    TextEditor() {
        this.content = "";
        this.cursorPosition = 0;
    }

    void insertText(String text) {
        content = content.substring(0, cursorPosition) + text + content.substring(cursorPosition);
        cursorPosition += text.length();
    }

    void deleteText(int length) {
        if (cursorPosition - length < 0) length = cursorPosition;
        content = content.substring(0, cursorPosition - length) + content.substring(cursorPosition);
        cursorPosition -= length;
    }

    void display() {
        System.out.println(content);
    }

    EditorMemento createMemento() {
        return new EditorMemento(content, cursorPosition);
    }

    void restore(EditorMemento memento) {
        this.content = memento.getContent();
        this.cursorPosition = memento.getCursorPosition();
    }
}

class Version {
    private final String versionName;
    private final EditorMemento memento;

    Version(String versionName, EditorMemento memento) {
        this.versionName = versionName;
        this.memento = memento;
    }

    String getVersionName() { return versionName; }
    EditorMemento getMemento() { return memento; }
}

class HistoryManager {
    private final Stack<EditorMemento> undoStack = new Stack<>();
    private final Stack<EditorMemento> redoStack = new Stack<>();
    private final List<Version> versions = new ArrayList<>();

    void saveState(EditorMemento memento) {
        undoStack.push(memento);
        redoStack.clear();
    }

    void undo(TextEditor editor) {
        if (undoStack.isEmpty()) {
            System.out.println("Nothing to undo.");
            return;
        }
        redoStack.push(editor.createMemento());
        editor.restore(undoStack.pop());
    }

    void redo(TextEditor editor) {
        if (redoStack.isEmpty()) {
            System.out.println("Nothing to redo.");
            return;
        }
        undoStack.push(editor.createMemento());
        editor.restore(redoStack.pop());
    }

    void saveVersion(String name, TextEditor editor) {
        for (Version v : versions) {
            if (v.getVersionName().equals(name)) {
                System.out.println("A version named \"" + name + "\" already exists. Please choose a different name.");
                return;
            }
        }
        versions.add(new Version(name, editor.createMemento()));
        System.out.println("Version \"" + name + "\" saved.");
    }

    void rollbackToVersion(String name, TextEditor editor) {
        for (Version v : versions) {
            if (v.getVersionName().equals(name)) {
                undoStack.push(editor.createMemento());
                redoStack.clear();
                editor.restore(v.getMemento());
                System.out.println("Rolled back to version \"" + name + "\".");
                return;
            }
        }
        System.out.println("Version \"" + name + "\" not found.");
    }

    void listVersions() {
        if (versions.isEmpty()) {
            System.out.println("No saved versions.");
            return;
        }
        System.out.println("Saved Versions:");
        for (int i = 0; i < versions.size(); i++) {
            System.out.println((i + 1) + ". " + versions.get(i).getVersionName());
        }
    }
}

public class Main {
    public static void main(String[] args) {
        TextEditor editor = new TextEditor();
        HistoryManager history = new HistoryManager();

        System.out.println("=== Example 1: Creating Versions ===");
        history.saveState(editor.createMemento());
        editor.insertText("Hello");

        history.saveVersion("Initial", editor);

        history.saveState(editor.createMemento());
        editor.insertText(" World");
        history.saveVersion("First Draft", editor);

        history.saveState(editor.createMemento());
        editor.insertText("!");
        history.saveVersion("Final", editor);

        System.out.println("Current Document:");
        editor.display();
        history.listVersions();

        System.out.println();
        System.out.println("=== Example 2: Undo and Redo ===");
        history.undo(editor);
        System.out.println("After Undo:");
        editor.display();

        history.redo(editor);
        System.out.println("After Redo:");
        editor.display();

        System.out.println();
        System.out.println("=== Example 3: Multiple Undo Operations ===");
        history.undo(editor);
        history.undo(editor);
        System.out.println("Current Document:");
        editor.display();

        System.out.println();
        System.out.println("=== Example 4: Rollback to a Named Version ===");
        history.saveState(editor.createMemento());
        editor.insertText(" World! Welcome");
        System.out.println("Current Document:");
        editor.display();

        history.rollbackToVersion("First Draft", editor);
        System.out.println("Current Document:");
        editor.display();

        System.out.println();
        System.out.println("=== Example 5: Undoing a Rollback ===");
        history.undo(editor);
        System.out.println("Current Document:");
        editor.display();

        history.redo(editor);
        System.out.println("Current Document:");
        editor.display();

        System.out.println();
        System.out.println("=== Duplicate Version Name Test ===");
        history.saveVersion("Final", editor);
    }
}