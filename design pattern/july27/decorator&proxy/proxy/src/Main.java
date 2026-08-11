import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


class PrivacyLevel {
    public static final String PUBLIC     = "PUBLIC";
    public static final String PRIVATE    = "PRIVATE";
    public static final String LOCKED     = "LOCKED";
    public static final String RESTRICTED = "RESTRICTED";
}


class ImageCategory {
    public static final String PERSONAL   = "PERSONAL";
    public static final String PUBLIC     = "PUBLIC";
    public static final String WORK       = "WORK";
    public static final String RESTRICTED = "RESTRICTED";
}


class ImageTag {
    private String id;

    public ImageTag(String id) {
        this.id = id;
    }

    public String getId() { return id; }

    @Override
    public String toString() { return id; }
}


class ImageMetadata {
    private String name;
    private List<ImageTag> tags;
    private String category;
    private String privacyLevel;

    public ImageMetadata(String name, String category, String privacyLevel) {
        this.name         = name;
        this.category     = category;
        this.privacyLevel = privacyLevel;
        this.tags         = new ArrayList<>();
    }

    public void addTag(ImageTag tag) {
        tags.add(tag);
    }

    public String getName()         { return name; }
    public String getCategory()     { return category; }
    public String getPrivacyLevel() { return privacyLevel; }
    public List<ImageTag> getTags() { return tags; }

    @Override
    public String toString() {
        return "Name: " + name +
                " | Category: " + category +
                " | Privacy: " + privacyLevel +
                " | Tags: " + tags;
    }
}



class User {
    private String username;
    private String role; // admin,user,guest

    public User(String username, String role) {
        this.username = username;
        this.role     = role;
    }

    public String getUsername() { return username; }
    public String getRole()     { return role; }

    public boolean hasPermission(String privacyLevel) {
        switch (privacyLevel) {
            case "PUBLIC":     return true;
            case "PRIVATE":    return role.equals("ADMIN") || role.equals("USER");
            case "LOCKED":     return role.equals("ADMIN");
            case "RESTRICTED": return role.equals("ADMIN");
            default:           return false;
        }
    }
}


interface Image {
    void display(User user);
    void viewInfo();
}



class RealImage implements Image {
    private String imageId;
    private String content;
    private ImageMetadata metadata;

    public RealImage(String imageId, ImageMetadata metadata) {
        this.imageId  = imageId;
        this.metadata = metadata;
        loadFromDisk();
    }

    private void loadFromDisk() {
        System.out.println("[LOAD] Loading image '" + metadata.getName() + "(will take time..)");
        this.content = "[IMAGE CONTENT of " + metadata.getName() + "]";
    }

    @Override
    public void display(User user) {
        System.out.println("[DISPLAY] Showing image: " + content);
    }

    @Override
    public void viewInfo() {
        System.out.println("[INFO] " + metadata);
    }
}



class ImageProxy implements Image {
    private String imageId;
    private ImageMetadata metadata;
    private RealImage realImage;

    public ImageProxy(String imageId, ImageMetadata metadata) {
        this.imageId   = imageId;
        this.metadata  = metadata;
        this.realImage = null;
    }

    private boolean checkAccess(User user) {
        String level = metadata.getPrivacyLevel();
        if (!user.hasPermission(level)) {
            System.out.println("[ACCESS DENIED]");
            return false;
        }
        return true;
    }

    @Override
    public void display(User user) {
        if (!checkAccess(user)) return;

        if (realImage == null) {
            realImage = new RealImage(imageId, metadata);
        } else {
            System.out.println("[CACHE] Reusing cached image for '" + metadata.getName() + "'");
        }

        realImage.display(user);
    }

    @Override
    public void viewInfo() {
        System.out.println("[INFO] " + metadata);
    }
}


class ImageLibrary {
    private Map<String, Image> images = new HashMap<>();

    public void addImage(String imageId, ImageMetadata metadata) {
        images.put(imageId, new ImageProxy(imageId, metadata));
    }

    public Image getImage(String imageId) {
        if (!images.containsKey(imageId)) {
            System.out.println("[LIBRARY] Image '" + imageId + "' not found.");
            return null;
        }
        return images.get(imageId);
    }

    public void listAll() {
        System.out.println("\n--- Image Library ---");
        for (Image img : images.values()) {
            img.viewInfo();
        }
        System.out.println("---------------------\n");
    }
}


public class Main {
    public static void main(String[] args) {


        ImageMetadata m1 = new ImageMetadata("beach_sunset.jpg", ImageCategory.PUBLIC, PrivacyLevel.PUBLIC);
        m1.addTag(new ImageTag("nature"));
        m1.addTag(new ImageTag("landscape"));

        ImageMetadata m2 = new ImageMetadata("profile_photo.jpg", ImageCategory.PERSONAL, PrivacyLevel.PRIVATE);
        m2.addTag(new ImageTag("profile"));

        ImageMetadata m3 = new ImageMetadata("confidential_doc.jpg", ImageCategory.RESTRICTED, PrivacyLevel.RESTRICTED);
        m3.addTag(new ImageTag("document"));
        m3.addTag(new ImageTag("sensitive"));

        ImageMetadata m4 = new ImageMetadata("work_report.jpg", ImageCategory.WORK, PrivacyLevel.LOCKED);
        m4.addTag(new ImageTag("document"));


        ImageLibrary library = new ImageLibrary();
        library.addImage("IMG001", m1);
        library.addImage("IMG002", m2);
        library.addImage("IMG003", m3);
        library.addImage("IMG004", m4);

        library.listAll();


        User guest  = new User("guest_user", "GUEST");
        User normal = new User("afif", "USER");
        User admin  = new User("admin", "ADMIN");

       
        System.out.println("========== GUEST USER ==========");
        library.getImage("IMG001").display(guest);
        library.getImage("IMG002").display(guest);
        library.getImage("IMG003").display(guest);
        library.getImage("IMG004").display(guest);

        System.out.println("\n========== NORMAL USER ==========");
        library.getImage("IMG001").display(normal);
        library.getImage("IMG002").display(normal);
        library.getImage("IMG003").display(normal);
        library.getImage("IMG004").display(normal);

        System.out.println("\n========== ADMIN USER ==========");
        library.getImage("IMG001").display(admin);
        library.getImage("IMG002").display(admin);
        library.getImage("IMG003").display(admin);
        library.getImage("IMG004").display(admin);
    }
}