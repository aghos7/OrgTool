package sample.OrgTool;

import java.io.IOException;
import java.io.Writer;

/**
 * @author aghos7
 *
 */
public class LoggingVisitor implements Visitor<OrgNode> {

    /**
     * Writer to write log to
     */
    Writer logWriter;
    
    /**
     * The amount to indent each level in the OrgNode tree
     */
    public static final String INDENT = "   ";
    
    /**
     * The current depth in the tree
     */
    private long depth = -1;
    
    /**
     * A simple visitor that logs the nodes
     * @param output Writer to write log to
     */
    public LoggingVisitor(Writer output) {
        logWriter = output;
    }
    
    /* (non-Javadoc)
     * @see sample.OrgTool.Visitor#enter(java.lang.Object)
     */
    @Override
    public void enter(OrgNode element) {
        depth++;
    }

    /* (non-Javadoc)
     * @see sample.OrgTool.Visitor#visit(java.lang.Object)
     */
    @Override
    public void visit(OrgNode element) {
        try {
            for(int i = 0; i < depth; i++) {
                logWriter.write(INDENT);
            }
            
            logWriter.write(element.getId() + ", " + 
                    element.getTotalNumUsers() + ", " + 
                    element.getTotalNumFiles() + ", " + 
                    element.getTotalNumBytes() + System.getProperty("line.separator"));
        } catch(IOException e) {
            System.out.println("error writting log: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /* (non-Javadoc)
     * @see sample.OrgTool.Visitor#leave(java.lang.Object)
     */
    @Override
    public void leave(OrgNode element) {
        depth--;
    }

}
