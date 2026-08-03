import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

class User {
    private String userId;
    private String name;
    private String role; // "student", "faculty", etc.
    private boolean isAuthenticated;

    public User(String userId, String name, String role) {
        this.userId = userId;
        this.name = name;
        this.role = role;
        this.isAuthenticated = false;
    }

    public String getUserId() { return userId; }
    public String getName() { return name; }
    public String getRole() { return role; }
    public boolean isAuthenticated() { return isAuthenticated; }
    public void setAuthenticated(boolean authenticated) { isAuthenticated = authenticated; }
}


 class Resource {
    private String resourceId;
    private String title;
    private String author;
    private String category;  // "book", "journal", "video", etc.
    private String format;    // "PDF", "MP4", etc.
    private boolean isAvailable;

    public Resource(String resourceId, String title, String author, String category, String format) {
        this.resourceId = resourceId;
        this.title = title;
        this.author = author;
        this.category = category;
        this.format = format;
        this.isAvailable = true;
    }

    public String getResourceId() { return resourceId; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getCategory() { return category; }
    public String getFormat() { return format; }
    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }
}



 class AuthenticationService {
    public boolean authenticate(User user) {
        System.out.println(" Authenticating user: " + user.getName());
        user.setAuthenticated(true);
        return true;
    }
}
 class AuthorizationService {
    public boolean authorize(User user, String action) {
        System.out.println(" Checking if " + user.getName() + " can perform: " + action);
        // Simple rule: all authenticated users can do everything
        return user.isAuthenticated();
    }
}


 class SearchService {
    public List<Resource> search(String query, List<Resource> catalog) {
        System.out.println(" Searching for: " + query);
        List<Resource> results = new ArrayList<>();
        for (Resource r : catalog) {
            if (r.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                    r.getAuthor().toLowerCase().contains(query.toLowerCase()) ||
                    r.getCategory().toLowerCase().contains(query.toLowerCase())
            ) {
                results.add(r);
            }
        }
        return results;
    }
}


 class CatalogService {
    private List<Resource> resources = new ArrayList<>();

    public void addResource(Resource r) {
        resources.add(r);
    }

    public Resource findById(String resourceId) {
        for (Resource r : resources) {
            if (r.getResourceId().equals(resourceId)) return r;
        }
        return null;
    }

    public List<Resource> getAllResources() {
        return resources;
    }
}



 class BorrowService {
    public boolean borrow(User user, Resource resource) {
        if (!resource.isAvailable()) {
            System.out.println("[Borrow] Resource not available: " + resource.getTitle());
            return false;
        }
        System.out.println("[Borrow] " + user.getName() + " borrowed: " + resource.getTitle());
        return true;
    }

    public void returnResource(User user, Resource resource) {
        System.out.println("[Borrow] " + user.getName() + " returned: " + resource.getTitle());
    }
}

 class PreviewService {
    public void generatePreview(Resource resource) {
        System.out.println("[Preview] Generating preview for: " + resource.getTitle() + " [" + resource.getFormat() + "]");
    }
}

 class DownloadService {
    public void download(User user, Resource resource) {
        System.out.println("[Download] " + user.getName() + " downloading: " + resource.getTitle());
    }
}

 class NotificationService {
    public void notify(User user, String message) {
        System.out.println("[Notification] To " + user.getName() + ": " + message);
    }
}

 class ActivityLogger {
    public void log(User user, String activity) {
        System.out.println("[Log]: " + user.getName() + " " + activity );
    }
}




 class LibraryFacade {

    private AuthenticationService authService = new AuthenticationService();
    private AuthorizationService authzService = new AuthorizationService();
    private SearchService searchService = new SearchService();
    private CatalogService catalogService = new CatalogService();
    private BorrowService borrowService = new BorrowService();
    private PreviewService previewService = new PreviewService();
    private DownloadService downloadService = new DownloadService();
    private NotificationService notificationService = new NotificationService();
    private ActivityLogger logger = new ActivityLogger();


    public void addResourceToCatalog(Resource r) {
        catalogService.addResource(r);
    }




    public List<Resource> searchResource(User user, String query) {
        if(!authService.authenticate(user)) {
            System.out.println("user not authenticated");
            return null;

        }
        if (!authzService.authorize(user, "search")) {
            System.out.println("Access denied.");
            return null;
        }
        List<Resource> results = searchService.search(query, catalogService.getAllResources());
        logger.log(user, "Searched for: " + query);
        return results;
    }


    public void previewResource(User user, String resourceId) {
        if(!authService.authenticate(user)) {
            System.out.println("user not authenticated");
            return;

        }
        if (!authzService.authorize(user, "preview")) {
            System.out.println("Access denied.");
            return;
        }
        Resource resource = catalogService.findById(resourceId);
        if (resource == null) { System.out.println("Resource not found."); return; }
        previewService.generatePreview(resource);
        logger.log(user, "Previewed: " + resource.getTitle());
    }


    public void borrowResource(User user, String resourceId) {
        if(!authService.authenticate(user)) {
            System.out.println("user not authenticated");
            return;

        }
        if (!authzService.authorize(user, "borrow")) {
            System.out.println("Access denied.");
            return;
        }
        Resource resource = catalogService.findById(resourceId);
        if (resource == null) {
            System.out.println("Resource not found.");
            return;
        }
        boolean success = borrowService.borrow(user, resource);
        if (success) {
            notificationService.notify(user, "You have borrowed: " + resource.getTitle());
            logger.log(user, "Borrowed: " + resource.getTitle());
        }
    }


    public void downloadResource(User user, String resourceId) {

        if(!authService.authenticate(user)) {
            System.out.println("user not authenticated");
            return;

        }
        if (!authzService.authorize(user, "download")) {
            System.out.println("Access denied.");
            return;
        }

        Resource resource = catalogService.findById(resourceId);
        if (resource == null) { System.out.println("Resource not found."); return; }
        downloadService.download(user, resource);
        notificationService.notify(user, "Download started: " + resource.getTitle());
        logger.log(user, "Downloaded: " + resource.getTitle());
    }
}




public class Main {
    public static void main(String[] args) {

        LibraryFacade library = new LibraryFacade();


        library.addResourceToCatalog(new Resource("R001", "Clean Code", "youknowwho", "book", "PDF"));
        library.addResourceToCatalog(new Resource("R002", "OS Concepts", "idonno", "book", "PDF"));
        library.addResourceToCatalog(new Resource("R003", "Java Lecture", "Fuad sir", "video", "MP4"));


        User user = new User("U001", "Afif", "student");


        List<Resource> results = library.searchResource(user, "clean");
        if (results != null) {
            System.out.println("Results found: " + results.size());
        }
        System.out.println("\n\n\n");


        library.previewResource(user, "R001");
        System.out.println("\n\n\n");

        library.borrowResource(user, "R001");
        System.out.println("\n\n\n");


        library.downloadResource(user, "R001");
    }
}