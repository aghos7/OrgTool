package sample.OrgTool;

import java.util.List;

/**
 * @author aghos7
 *
 */
public interface OrgCollection {
    /**
     * Add an org to the collection
     * @param orgId The non-null org id;
     * @param parentId The parent org id, may be null
     * @throws IllegalArgumentException The org being added is invalid, details in exception message
     */
    void addOrg(Long orgId, Long parentId, String name) throws IllegalArgumentException;
    
    /**
     * Add user data to an organization
     * @param orgId The organizations id
     * @param userId The users id
     * @param numFiles The number of files associated with the user
     * @param numBytes The number of bytes associated with the user 
     * @throws IllegalArgumentException The user being added is invalid, details in exception message
     */
    void addUserData(Long orgId, Long userId, int numFiles, int numBytes) throws IllegalArgumentException;
    
    /**
     * Apply an orphan policy to the collection
     * @param policy The policy to apply
     * @throws IllegalArgumentException Unsupported policy
     */
    void applyOrphanPolicy(OrphanPolicy policy) throws IllegalArgumentException;
    
    /**
     * Return the Org with the specified org id
     * @param orgId The org id to return
     * @return The Org associated with the id
     */
    public Org getOrg(Long orgId);
    
    /**
     * Return list of orgs below the specified org id in the collection (i.e. flatten the tree)
     * @param orgId The org id to treat as the root
     * @param inclusive Include the Org specified by orgId in the list
     * @return List of all Orgs below the specified orgId, may be inclusive
     */
    public List<Org> getOrgTree(Long orgId, boolean inclusive);
}
