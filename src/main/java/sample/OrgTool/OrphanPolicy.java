package sample.OrgTool;

/**
 * The various orphan policies
 * @author Lucas Anderson
 * @version 0.1
 */
public enum OrphanPolicy {
    LEAVE, // Leave them alone (default)
    ROOTIFY, // Move all the children whose parent hasn't been added under the root node
    ROOTIFY_PARENT, // Move all parents that haven't been added under the root node
}
