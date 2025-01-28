package com.ensam.hotelalrbadr.api.model;

/**
 * This class represents a hotel service (like WiFi, breakfast, pool access, etc.)
 * Each service has an ID, name, and an icon to display in the UI.
 */
public class Services {
    // Basic properties of a service
    private Long id;             // Unique identifier for the service
    private String name;         // Name of the service (e.g., "WiFi", "Breakfast")
    private String iconUrl;      // Path to the service's icon image

    // Where to find service icons and default icon
    private static final String ICONS_FOLDER = "/homePageImages/services/";
    private static final String DEFAULT_ICON = "default-service-icon.png";

    /**
     * Empty constructor - creates an empty service
     */
    public Services() {
    }

    /**
     * Creates a new service with all its properties
     */
    public Services(Long id, String name, String iconUrl) {
        this.id = id;
        setName(name);          // Using setter to validate name
        setIconUrl(iconUrl);    // Using setter to process icon URL
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    /**
     * Sets the service name after checking it's valid
     */
    public void setName(String name) {
        // Make sure name isn't empty
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Service name cannot be empty");
        }
        this.name = name.trim();
    }

    public String getIconUrl() {
        return iconUrl;
    }

    /**
     * Sets the icon URL, cleaning up the path if needed
     */
    public void setIconUrl(String iconUrl) {
        this.iconUrl = cleanUpIconPath(iconUrl);
    }

    /**
     * Gets the complete path to the service's icon
     * If no icon is set, returns path to default icon
     */
    public String getFormattedIconUrl() {
        String iconPath = cleanUpIconPath(iconUrl);
        if (iconPath == null) {
            return ICONS_FOLDER + DEFAULT_ICON;
        } else {
            return ICONS_FOLDER + iconPath;
        }
    }

    /**
     * Cleans up an icon path by:
     * 1. Removing any folder paths (we only want the filename)
     * 2. Converting backslashes to forward slashes
     * 3. Trimming whitespace
     */
    private String cleanUpIconPath(String path) {
        // If path is empty, return null
        if (path == null || path.trim().isEmpty()) {
            return null;
        }

        // Clean up the path
        String cleanPath = path.replace('\\', '/').trim();

        // Get just the filename (remove any folder paths)
        int lastSlash = cleanPath.lastIndexOf('/');
        if (lastSlash >= 0) {
            return cleanPath.substring(lastSlash + 1);
        } else {
            return cleanPath;
        }
    }

    /**
     * Two services are equal if they have the same ID
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Services other = (Services) obj;
        return id != null && id.equals(other.id);
    }

    /**
     * Hash code is based on ID to match equals method
     */
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    /**
     * Returns a string representation of the service
     */
    @Override
    public String toString() {
        return "Service{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", iconUrl='" + iconUrl + '\'' +
                '}';
    }
}