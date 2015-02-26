package sample.OrgTool;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * A simple org collection for storing an organizational hierarchy
 * Internally it has a single dummy root that all other nodes become children of
 * Keeps a map from orgId to OrgNode to avoid the need to constantly search from the root OrgNode
 * Orphans are left alone, in hopes that one day their parent will come along
 * Simple cycles are detected
 * It is not thread safe!!
 * This could/should be made into a generic forest data struct
 * @author Lucas Anderson
 * @version 0.1
 */
public class SimpleOrgCollection implements OrgCollection, Visitable<OrgNode> {
    
    /**
     * The roots of this collection, Uses the children of the 
     * OrgNode as a map of roots
     */
    private OrgNode root;
    
    private OrgNode orphans;

    /**
     * Lookup table for quickly finding OrgNodes Constantly searching the tree
     * is too slow. Maps orgId to OrgNode
     */
    private Map<Long, OrgNode> locate;

    /**
     * Constructs an empty SimpleOrgCollection
     */
    public SimpleOrgCollection() {
        locate = new HashMap<Long, OrgNode>();
        root = new OrgNode(null, null, "root");
        orphans = new OrgNode(null, null, "orphans");
        locate.put(null, root);
    }
    
    /**
     * Updates the statistics of all the orgs in the collection
     */
    public void updateAllOrgData() {
        updateOrgData(null);
    }

    /**
     * Updates the statistics of the specified org
     * This should probably be changed to follow the visitor pattern
     * @param orgId The org id to update
     */
    public void updateOrgData(Long orgId) {
        OrgNode org = locate.get(orgId);
        for (OrgNode child : org.getChildren().values()) {
            updateOrgData(child.getId());
            org.setTotalNumUsers(org.getTotalNumUsers() + child.getTotalNumUsers());
            org.setTotalNumFiles(org.getTotalNumFiles() + child.getTotalNumFiles());
            org.setTotalNumBytes(org.getTotalNumBytes() + child.getTotalNumBytes());
        }
    }
    
    /* (non-Javadoc)
     * @see sample.OrgTool.OrgCollection#addOrg(java.lang.Long, java.lang.Long, java.lang.String)
     */
    @Override
    public void addOrg(Long orgId, Long parentId, String name) throws IllegalArgumentException {
        if (null == orgId) {
            throw new IllegalArgumentException("Invalid orgId: " + orgId);
        }
        if (orgId.equals(parentId)) {
            throw new IllegalArgumentException("Cycle detected: orgId: " + orgId + " parentId: " + parentId );
        }
        
        OrgNode parent = getOrCreateNode(parentId, orphans);
        OrgNode child = getOrCreateNode(orgId);
        // update the name, this could be moved out to another method if we need to update more data
        child.setName(name);
        
        orphans.removeChild(child.getId());;
        
        if (null != child.getParent()) {
            child.getParent().removeChild(child.getId());
        }
        
        child.setParent(parent);
        parent.addChild(child);
    }
    
    /**
     * Get the OrgNode associated with the specified id or create one
     * @param orgId The org id to get/create
     * @return The org node requested
     */
    public OrgNode getOrCreateNode(Long orgId) {
        return getOrCreateNode(orgId, null);
    }
    
    /**
     * Get the OrgNode associated with the specified id or create one
     * @param orgId The org id to get/create
     * @param parent The parent of the node, if it doesn't already exist
     * @return The org node requested
     */
    public OrgNode getOrCreateNode(Long orgId, OrgNode parent) {
        if (locate.containsKey(orgId)) {
            return locate.get(orgId);
        } else {
            OrgNode result = new OrgNode(orgId);
            if (null != parent) {
                result.setParent(parent);
                parent.addChild(result);
            }
            locate.put(orgId, result);
            return result;
        }
    }
    
    /**
     * Create a lazy org bean that corresponds to the specified org id
     * @note This should be moved outside the collection
     * @param orgId The org id
     * @return A LazyOrg 
     */
    public LazyOrg createLazyOrg(Long orgId) {
        LazyOrg lazyOrg = null;
        if (locate.containsKey(orgId)) {
            OrgNode org = locate.get(orgId);
            lazyOrg = new LazyOrg(this, new LinkedList<Long>(org.getChildren().keySet()));
            lazyOrg.setTotalNumUsers(org.getTotalNumUsers());
            lazyOrg.setTotalNumFiles(org.getTotalNumFiles());
            lazyOrg.setTotalNumBytes(org.getTotalNumBytes());
        }
        return lazyOrg;
    }

    /* (non-Javadoc)
     * @see sample.OrgTool.OrgCollection#addUserData(java.lang.Long, java.lang.Long, int, int)
     */
    @Override
    public void addUserData(Long orgId, Long userId, int numFiles, int numBytes) throws IllegalArgumentException {
        if (!locate.containsKey(orgId)) {
            throw new IllegalArgumentException("orgId does not exist: " + orgId);
        }
        if (locate.get(orgId).getUsers().containsKey(userId)) {
            throw new IllegalArgumentException("The user already exists in this org. orgId: " + orgId + " userId: " + userId);
        }
        locate.get(orgId).addUser(userId, numFiles, numBytes);
    }

    /* (non-Javadoc)
     * @see sample.OrgTool.OrgCollection#applyOrphanPolicy(sample.OrgTool.OrphanPolicy)
     */
    @Override
    public void applyOrphanPolicy(OrphanPolicy policy) throws IllegalArgumentException {
        switch(policy) {
            case LEAVE:
                break;
            case ROOTIFY:
                for (OrgNode parent : orphans.getChildren().values())
                {
                    root.getChildren().putAll(parent.getChildren());
                }
                orphans.getChildren().clear();
                break;
            case ROOTIFY_PARENT:
                root.getChildren().putAll(orphans.getChildren());
                locate.remove(orphans.getChildren().keySet());
                orphans.getChildren().clear();
                break;
            default:
                throw new IllegalArgumentException("Unsupported Policy: " + policy );
        }
    }

    /* (non-Javadoc)
     * @see sample.OrgTool.OrgCollection#getOrg(java.lang.Long)
     */
    @Override
    public Org getOrg(Long orgId) {
        return createLazyOrg(orgId);
    }

    /* (non-Javadoc)
     * @see sample.OrgTool.OrgCollection#getOrgTree(java.lang.Long, boolean)
     */
    @Override
    public List<Org> getOrgTree(Long orgId, boolean inclusive) {
        // this could also be turned into a visitor that appends nodes to result
        List<Org> result = new LinkedList<Org>();
        OrgNode org = locate.get(orgId);
        if (null != org) {
            if (inclusive) {
                result.add(createLazyOrg(orgId));
            }
            for(Long childId : org.getChildren().keySet()) {
                result.addAll(getOrgTree(childId, true));
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see sample.OrgTool.Visitable#accept(sample.OrgTool.Visitor)
     */
    @Override
    public void accept(Visitor<OrgNode> visitor) {
        root.accept(visitor);
    }

}
