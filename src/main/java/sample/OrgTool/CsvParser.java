/**
 * 
 */
package sample.OrgTool;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * A very simple csv parser that adds data to the collection
 * This horrible design, should be decoupled from OrgCollection
 * Should be refactored into a line iterator
 * @author Lucas Anderson
 * @version 0.1
 */
class CsvParser {
    
    /**
     * Parse a org hierarchy csv file and add elements to the collection
     * @param csv The file to parse
     * @param orgCollection The OrgCollection to add the elements to
     * @throws Exception Any exception
     */
    public void parseHierarchy(File csv, OrgCollection orgCollection) throws IOException
    {
        Scanner scanner = new Scanner(csv);
        // read file line by line
        scanner.useDelimiter(System.getProperty("line.separator"));
        while(scanner.hasNext()){
            String line = scanner.next();
            // split the line into tokens
            Scanner lineScanner = new Scanner(line.trim());
            lineScanner.useDelimiter("\\s*,\\s*");
            Long orgId = lineScanner.nextLong();
            String parentIdStr = lineScanner.next();
            Long parentId;
            // treat "null" and "0" as null
            if (parentIdStr.equals("null") || parentIdStr.equals("0")) {
                parentId = null;
            } else {
                parentId = new Long(parentIdStr);
            } 
            String name = lineScanner.next();
            lineScanner.close();
            // add it to the collection
            orgCollection.addOrg(orgId, parentId, name);
        }
        scanner.close();
    }
    
    /**
     * Parse a org hierarchy csv file and add elements to the collection
     * @param csv The file to parse
     * @param orgCollection The OrgCollection to add the elements to
     * @throws Exception Any exception
     */
    public void parseUserData(File csv, OrgCollection orgCollection) throws IOException
    {
        Scanner scanner = new Scanner(csv);
        // read file line by line
        scanner.useDelimiter(System.getProperty("line.separator"));
        while(scanner.hasNext()){
            String line = scanner.next();
            // split the line into tokens
            Scanner lineScanner = new Scanner(line.trim());
            lineScanner.useDelimiter("\\s*,\\s*");
            Long userId = lineScanner.nextLong();
            String orgIdStr = lineScanner.next();
            Long orgId;
            // treat "null" and "0" as null
            if (orgIdStr.equals("null") || orgIdStr.equals("0")) {
                orgId = null;
            } else {
                orgId = new Long(orgIdStr);
            } 
            int numFiles = lineScanner.nextInt();
            int numBytes = lineScanner.nextInt();
            lineScanner.close();
            // add it to the collection
            orgCollection.addUserData(orgId, userId, numFiles, numBytes);
        }
        scanner.close();
    }
}
