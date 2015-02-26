package sample.OrgTool;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * A very simple factory for creating different types of OrgCollections
 * Currently supports: SimpleOrgCollection
 * Could have used abstract factory pattern but opted not to
 * @author Lucas Anderson
 * @version 0.1
 */
public class OrgCollectionFactory {
    /**
     * Create the specified OrgCollection using the specified files
     * @param type The type of the OrgCollection
     * @param hierarchy The hierarchy file
     * @param userData The user data file
     * @param log The file to log org structure in
     * @return An OrgCollection containing the specified elements and structure
     * @throws Exception
     */
    public static OrgCollection create(OrgCollectionTypes type, File hierarchy, File userData, File log) 
            throws IOException {

        switch (type) {
            case SIMPLE_ORG_COLLECTION:
                return createSimpleOrg(hierarchy, userData, log);
            default:
                throw new IllegalArgumentException("Unknown OrgCollection type: " + type);
        }
    }

    /**
     * Create a SimpleOrgCollection using the specified files
     * @param hierarchy The hierarchy file
     * @param userData The user data file
     * @param log The file to log org structure in
     * @return A SimpleOrgCollection containing the specified elements and structure
     * @throws Exception
     */
    public static OrgCollection createSimpleOrg(File hierarchy, File userData, File log) 
            throws IOException {
        SimpleOrgCollection result = new SimpleOrgCollection();
        CsvParser parser = new CsvParser();
        parser.parseHierarchy(hierarchy, result);
        result.applyOrphanPolicy(OrphanPolicy.ROOTIFY_PARENT);
        
        parser.parseUserData(userData, result);
        result.updateAllOrgData();
        BufferedWriter logWriter = new BufferedWriter(new FileWriter(log));
        LoggingVisitor treeLogger = new LoggingVisitor(logWriter);
        result.accept(treeLogger);
        logWriter.close();
        return result;
    }
}
