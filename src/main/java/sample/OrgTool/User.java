package sample.OrgTool;

/**
 * A user within an org
 * @author Lucas Anderson
 * @version 0.1
 */
class User {
    /**
     * The userId of the user
     */
    private Long id;
    
    /**
     * The number of files associated with the user
     */
    private int numFiles;
    
    /**
     * The number of bytes associated with the user
     */
    private int numBytes;
    
    /**
     * Construct a user with the specified attributes
     * @param id The userId of the user
     * @param numFiles The number of files associated with the user
     * @param numBytes The number of bytes associated with the user
     */
    public User(Long id, int numFiles, int numBytes) {
        setId(id);
        setNumFiles(numFiles);
        setNumBytes(numBytes);
    }
    
    /**
     * Get the user's id
     * @return The id
     */
    public Long getId() {
        return id;
    }
    
    /**
     * Set the user's id
     * @param id The id to set
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     * Get the number of files associated with the user
     * @return The number of files associated with the user
     */
    public int getNumFiles() {
        return numFiles;
    }
    
    /**
     * Set the number of files associated with the user
     * @param numFiles The number of files to set
     */
    public void setNumFiles(int numFiles) {
        this.numFiles = numFiles;
    }
    
    /**
     * Get the number of bytes associated with the user
     * @return The number of bytes associated with the user
     */
    public int getNumBytes() {
        return numBytes;
    }
    
    /**
     * Set the number of bytes associated with the user
     * @param numBytes The number of bytes to set
     */
    public void setNumBytes(int numBytes) {
        this.numBytes = numBytes;
    }
}
