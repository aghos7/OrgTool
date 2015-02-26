package sample.OrgTool;

import java.util.Map;
import java.util.HashMap;

/**
 * A node for storing org data in an OrgCollection
 * This should probably be reworked into a line by line iterator
 * @author Lucas Anderson
 * @version 0.1
 */
class OrgNode implements Visitable<OrgNode> {
    
    /**
     * The id of this org
     */
    private Long id;
    
    /**
     * This nodes parent
     */
    private OrgNode parent;
    
    /**
     * The name of this org
     */
    private String name;
    
    /**
     * The total number of users in the org and any children
     */
    private int totalNumUsers;
    
    /**
     * The total number of users in the org and any children
     */
    private int totalNumFiles;
    
    /**
     * The total number of users in the org and any children
     */
    private int totalNumBytes;
    
    /**
     * The immediate children of the org
     */
    private Map<Long, OrgNode> children;
    
    /**
     * The users in this org
     */
    private Map<Long, User> users;
    
    /**
     * Construct an org node with the specified id
     * @param id The org's id
     */
    public OrgNode(Long id) {
        this(id, null, "");
    }
    
    /**
     * Construct an org node with the specified id and name
     * @param id The org's id
     * @param parent The org's parent
     */
    public OrgNode(Long id, OrgNode parent) {
        this(id, parent, "");
    }
    
    /**
     * Construct an org node with the specified id and name
     * @param id The org's id
     * @param parentId The org's parent id
     * @param name The org's name
     */
    public OrgNode(Long id, OrgNode parent, String name) {
        setId(id);
        setName(name);
        setParent(parent);
        setChildren(new HashMap<Long, OrgNode>());
        setUsers(new HashMap<Long, User>());
    }
    
    /**
     * Add a user to the org
     * @param userId The user id to add
     * @param numFiles The number of files of this user
     * @param numBytes The number of bytes of this user
     */
    public void addUser(Long userId, int numFiles, int numBytes) {
        users.put(userId, new User(userId, numFiles, numBytes));
        totalNumUsers++;
        totalNumFiles += numFiles;
        totalNumBytes += numBytes;
    }
    
    /**
     * Get the id of the node
     * @return The id
     */
    public Long getId() {
        return id;
    }
    
    /**
     * Set the id of the node
     * @param id The id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the parent of the node
     * @return The parent
     */
    public OrgNode getParent() {
        return parent;
    }

    /**
     * Set the parent of the node
     * @param parent The parent to set
     */
    public void setParent(OrgNode parent) {
        this.parent = parent;
    }

    /**
     * Get the name of the node
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the node
     * @param name The name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the total number of users of the node and all of its children
     * @return The totalNumUsers
     */
    public int getTotalNumUsers() {
        return totalNumUsers;
    }

    /**
     * Set the total number of users of the node and all of its children
     * @param totalNumUsers the totalNumUsers to set
     */
    public void setTotalNumUsers(int totalNumUsers) {
        this.totalNumUsers = totalNumUsers;
    }

    /**
     * Get the total number of files of the node and all of its children
     * @return The totalNumFiles
     */
    public int getTotalNumFiles() {
        return totalNumFiles;
    }

    /**
     * Set the total number of files of the node and all of its children
     * @param totalNumFiles The totalNumFiles to set
     */
    public void setTotalNumFiles(int totalNumFiles) {
        this.totalNumFiles = totalNumFiles;
    }

    /**
     * Get the total number of files of the node and all of its children
     * @return The totalNumBytes
     */
    public int getTotalNumBytes() {
        return totalNumBytes;
    }

    /**
     * Set the total number of bytes of the node and all of its children
     * @param totalNumBytes The totalNumBytes to set
     */
    public void setTotalNumBytes(int totalNumBytes) {
        this.totalNumBytes = totalNumBytes;
    }

    /**
     * Get the immediate children of the node
     * @return The immediate children
     */
    public Map<Long, OrgNode> getChildren() {
        return children;
    }

    /**
     * Set the immediate children of the node
     * @param children The children to set
     */
    public void setChildren(Map<Long, OrgNode> children) {
        this.children = children;
    }
    
    /**
     * Add a child to the node
     * @param child The child to add
     */
    public void addChild(OrgNode child) {
        getChildren().put(child.getId(), child);
    }
    
    /**
     * Remove a child from the node
     * @param id The id of the child to remove
     */
    public void removeChild(Long id) {
        getChildren().remove(id);
    }

    /**
     * Get the users in the org
     * @return The users in the org
     */
    public Map<Long, User> getUsers() {
        return users;
    }

    /**
     * Set the users in the org
     * @param users The users to set
     */
    public void setUsers(Map<Long, User> users) {
        this.users = users;
    }

    /* (non-Javadoc)
     * @see sample.orgtool.Visitable#accept(sample.orgtool.Visitor)
     */
    @Override
    public void accept(Visitor<OrgNode> visitor) {
        visitor.enter(this);
        visitor.visit(this);
        for (OrgNode child : getChildren().values()) {
            child.accept(visitor);
        }
        visitor.leave(this);
    }
}