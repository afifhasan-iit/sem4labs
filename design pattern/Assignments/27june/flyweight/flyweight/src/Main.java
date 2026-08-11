import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

class CharacterStyle {
    private final String fontFamily;
    private final int fontSize;
    private final String textColor;
    private final boolean bold;
    private final boolean italic;
    private final boolean underline;

    public CharacterStyle(String fontFamily, int fontSize, String textColor,
                          boolean bold, boolean italic, boolean underline) {
        this.fontFamily = fontFamily;
        this.fontSize   = fontSize;
        this.textColor  = textColor;
        this.bold       = bold;
        this.italic     = italic;
        this.underline  = underline;
        System.out.println("Creating style: [" + fontFamily + ", " + fontSize +
                "px, " + textColor + ", bold=" + bold +
                ", italic=" + italic + ", underline=" + underline + "]");
    }

    public String getFontFamily() { return fontFamily; }
    public int getFontSize()      { return fontSize; }
    public String getTextColor()  { return textColor; }
    public boolean isBold()       { return bold; }
    public boolean isItalic()     { return italic; }
    public boolean isUnderline()  { return underline; }

    public String getSummary() {
        return fontFamily + "|" + fontSize + "|" + textColor +
                "|" + bold + "|" + italic + "|" + underline;
    }
}


class StyleFactory {
    private static Map<String, CharacterStyle> pool = new HashMap<>();

    public static CharacterStyle getStyle(String fontFamily, int fontSize,
                                          String textColor, boolean bold,
                                          boolean italic, boolean underline) {
        String key = fontFamily + "|" + fontSize + "|" + textColor +
                "|" + bold + "|" + italic + "|" + underline;
        if (!pool.containsKey(key)) {
            pool.put(key, new CharacterStyle(fontFamily, fontSize, textColor,
                    bold, italic, underline));
        }
        return pool.get(key);
    }

    public static int getPoolSize() { return pool.size(); }
}

//handle one character
class DocumentCharacter {
    private final char value;
    private int line;
    private int column;
    private CharacterStyle style;

    public DocumentCharacter(char value, int line, int column,
                             String fontFamily, int fontSize, String textColor,
                             boolean bold, boolean italic, boolean underline) {
        this.value  = value;
        this.line   = line;
        this.column = column;
        this.style  = StyleFactory.getStyle(fontFamily, fontSize, textColor,
                bold, italic, underline);
    }

    public char getValue()           { return value; }
    public int getLine()             { return line; }
    public int getColumn()           { return column; }
    public CharacterStyle getStyle() { return style; }

    public void setLine(int line)     { this.line = line; }
    public void setColumn(int col)    { this.column = col; }

    public void applyStyle(String fontFamily, int fontSize, String textColor,
                           boolean bold, boolean italic, boolean underline) {
        this.style = StyleFactory.getStyle(fontFamily, fontSize, textColor,
                bold, italic, underline);
    }

    public void print() {
        System.out.println("Char: '" + value + "' | Line: " + line +
                " | Col: " + column + " | Style: [" +
                style.getFontFamily() + ", " + style.getFontSize() +
                "px, " + style.getTextColor() +
                ", bold=" + style.isBold() +
                ", italic=" + style.isItalic() +
                ", underline=" + style.isUnderline() + "]");
    }
}

// holds all characters
class Document {
    private List<DocumentCharacter> characters = new ArrayList<>();

    public void insertCharacter(char value, int line, int column,
                                String fontFamily, int fontSize, String textColor,
                                boolean bold, boolean italic, boolean underline) {
        characters.add(new DocumentCharacter(value, line, column,
                fontFamily, fontSize, textColor,
                bold, italic, underline));
    }

    public void deleteCharacter(int index) {
        if (index >= 0 && index < characters.size()) {
            DocumentCharacter removed = characters.remove(index);
            System.out.println("Deleted character: '" + removed.getValue() + "'");
        } else {
            System.out.println("Invalid index for deletion: " + index);
        }
    }

    public void modifyCharacter(int index, String fontFamily, int fontSize,
                                String textColor, boolean bold,
                                boolean italic, boolean underline) {
        if (index >= 0 && index < characters.size()) {
            characters.get(index).applyStyle(fontFamily, fontSize, textColor,
                    bold, italic, underline);
            System.out.println("Modified style of character at index " + index);
        } else {
            System.out.println("Invalid index for modification: " + index);
        }
    }

    public void display() {
        System.out.println("\n--- Document Contents ---");
        for (DocumentCharacter dc : characters) {
            dc.print();
        }
        System.out.println("-------------------------\n");
    }

    public int getTotalCharacters()  { return characters.size(); }
    public List<DocumentCharacter> getCharacters() { return characters; }
}


class StatisticsManager {
    public static void printStats(Document document) {
        int total       = document.getTotalCharacters();
        int uniqueStyles = StyleFactory.getPoolSize();

        //
        int bytesPerStyle    = 6 * 8 ; // ~48 bytes per style object  (common)
        int bytesPerContext  = 4+4+2; // int, int, char  (unique)

        int memoryWithSharing    = (uniqueStyles * bytesPerStyle) + (total * bytesPerContext);
        int memoryWithoutSharing = total * (bytesPerStyle + bytesPerContext);
        int memorySaved          = memoryWithoutSharing - memoryWithSharing;

        System.out.println("===== Statistics =====");
        System.out.println("Total characters       : " + total);
        System.out.println("Unique style objects   : " + uniqueStyles);
        System.out.println(" memory (sharing)  : " + memoryWithSharing + " bytes");
        System.out.println(" memory (no share) : " + memoryWithoutSharing + " bytes");
        System.out.println("Estimated memory saved : " + memorySaved + " bytes");
        System.out.println("======================\n");
    }
}


class Editor {
    private final Document document = new Document();

    public void insert(char value, int line, int column,
                       String fontFamily, int fontSize, String textColor,
                       boolean bold, boolean italic, boolean underline) {
        document.insertCharacter(value, line, column, fontFamily, fontSize,
                textColor, bold, italic, underline);
    }

    public void delete(int index) {
        document.deleteCharacter(index);
    }

    public void modify(int index, String fontFamily, int fontSize,
                       String textColor, boolean bold,
                       boolean italic, boolean underline) {
        document.modifyCharacter(index, fontFamily, fontSize, textColor,
                bold, italic, underline);
    }

    public void display() {
        document.display();
    }

    public void showStats() {
        StatisticsManager.printStats(document);
    }
}


public class Main {
    public static void main(String[] args) {

        Editor editor = new Editor();

        String[] chars = {"H", "e", "l", "l", "o", " ", "W", "o", "r", "l", "d"};
        for (int i = 0; i < chars.length; i++) {
            editor.insert(chars[i].charAt(0), 1, i + 1,
                    "Arial", 12, "Black", false, false, false);
        }

        // Insert some bold characters — same bold style shared
        String[] bold = {"J", "a", "v", "a"};
        for (int i = 0; i < bold.length; i++) {
            editor.insert(bold[i].charAt(0), 2, i + 1,
                    "Arial", 12, "Black", true, false, false);
        }

        // Insert italic characters
        editor.insert('!', 3, 1, "Times New Roman", 14, "Red", false, true, false);
        editor.insert('!', 3, 2, "Times New Roman", 14, "Red", false, true, false);


        editor.display();


        editor.modify(0, "Arial", 12, "Blue", false, false, true);

        editor.delete(5);

        System.out.println("Display after modification");
        editor.display();

        editor.showStats();
    }
}