package Hospital;

public final class AuthenticationManager {

    private static volatile AuthenticationManager authenticationManager;

    private String securityLevel;
    private int failedLoginAttempts;
    private boolean systemLocked;

    private AuthenticationManager() {
        securityLevel = "HIGH";
        failedLoginAttempts = 0;
        systemLocked = false;
    }

    public static AuthenticationManager getInstance() {

        if (authenticationManager == null) {

            synchronized (AuthenticationManager.class) {

                if (authenticationManager == null) {
                    authenticationManager = new AuthenticationManager();
                }
            }
        }

        return authenticationManager;
    }

    public boolean validateUser(String username, String role) {

        HospitalLogger logger = HospitalLogger.getInstance();

        if (systemLocked) {
            logger.logActivity("Access denied - system locked | User: " + username);
            return false;
        }

        logger.logActivity("User validated | Username: " + username + " | Role: " + role);
        return true;
    }

    public void recordFailedAttempt(String username) {

        synchronized (this) {
            failedLoginAttempts++;
            if (failedLoginAttempts >= 3) {
                systemLocked = true;
            }
        }

        HospitalLogger logger = HospitalLogger.getInstance();
        logger.logActivity("Failed login attempt | Username: " + username + " | Total Failures: " + failedLoginAttempts);
    }

    public void resetFailedAttempts() {

        synchronized (this) {
            failedLoginAttempts = 0;
            systemLocked = false;
        }

        HospitalLogger logger = HospitalLogger.getInstance();
        logger.logActivity("Failed attempts reset | System unlocked");
    }
}