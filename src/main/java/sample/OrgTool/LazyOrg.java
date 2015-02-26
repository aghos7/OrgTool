package sample.OrgTool;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * A lazy loading org that only retrieves children when requested
 * It also has setters, because oddly the Org API didn't include setters,
 * which I believe is standard for java beans
 * @author Lucas Anderson
 * @version 0.1
 */
class LazyOrg extends Org {
    /**
     * The OrgCollection to lazily load children from
     */
    private OrgCollection dao;
    
    /**
     * The id's of the child orgs
     */
    private List<Long> childIds;
    
    /**
     * Construct a lazy org bean
     * @param dao The data access object to request data from
     * @param childIds The id's of the child orgs
     */
    public LazyOrg(OrgCollection dao, List<Long> childIds) {
        super();
        this.dao = dao;
        this.childIds = childIds;
    }
    
    /**
     * Set the total number of users in the org and any children
     * @param totalNumUsers The number of users to set
     */
    public void setTotalNumUsers(int totalNumUsers) {
        this.totalNumUsers = totalNumUsers;
    }

    /**
     * Set the total number of files in the org and any children
     * @param totalNumFiles The number of files to set
     */
    public void setTotalNumFiles(int totalNumFiles) {
        this.totalNumFiles = totalNumFiles;
    }

    /**
     * Set the total number of bytes in the org and any children
     * @param totalNumBytes The number of bytes to set
     */
    public void setTotalNumBytes(int totalNumBytes) {
        this.totalNumBytes = totalNumBytes;
    }
    
    /* (non-Javadoc)
     * @see sample.orgtool.Org#getChildOrgs()
     */
    @Override
    public List<Org> getChildOrgs() {
        if (null == children) {
            children = new LinkedList<Org>();
            for (Long childId : childIds) {
                Org child = dao.getOrg(childId);
                children.add(child);
            }
        }
        return Collections.unmodifiableList(children);
    }
}