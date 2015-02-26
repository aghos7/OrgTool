package sample.OrgTool;

/**
 * A simple visitor interface for visiting nodes
 * @author Lucas Anderson
 * @version 0.1
 */
public interface Visitor<T> {
    /**
     * Method to execute on entering the element
     * @param element The element
     */
    void enter(T element);
    
    /**
     * Method to execute on the element
     * @param element The element
     */
    void visit(T element);
    
    /**
     * Method to execute on leaving the element
     * @param element The element
     */
    void leave(T element);
}