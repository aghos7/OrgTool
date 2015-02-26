package sample.OrgTool;

/**
 * A simple visitable interface
 * @author Lucas Anderson
 * @version 0.1
 */
public interface Visitable<T> {
    /**
     * Accept the visitor
     * @param visitor The visitor
     */
    void accept(Visitor<T> visitor);
}
