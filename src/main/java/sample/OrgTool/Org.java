package sample.OrgTool;

import java.util.List;

/**
 * A simple org to get stats and children in an org structure
 * @author Lucas Anderson
 * @version 0.1
 */
public abstract class Org {
    /**
     * The total number of users in the org and any children
     */
    protected int totalNumUsers;
    
    /**
     * The total number of files in the org and any children
     */
    protected int totalNumFiles;
    
    /**
     * The total number of bytes in the org and any children
     */
    protected int totalNumBytes;
    
    /**
     * The immediate children of the org
     */
    protected List<Org> children;
    
    /**
     * Return the total number of users in the org and any children
     * @return The total number of users
     */
    public int getTotalNumUsers() {
        return totalNumUsers;
    }
    
    /**
     * Return the total number of files in the org and any children
     * @return The total number of files
     */
    public int getTotalNumFiles() {
        return totalNumFiles;
    }
    
    /**
     * Return the total number of bytes in the org and any children
     * @return The total number of bytes
     */
    int getTotalNumBytes() {
        return totalNumBytes;
    }
    
    /**
     * Return the immediate children of the org
     * @return The immediate children
     */
    public List<Org> getChildOrgs() {
        return children;
    }
}
