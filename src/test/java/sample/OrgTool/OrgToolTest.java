package sample.OrgTool;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.junit.Test;

/**
 * A JUnit test to verify OrgTool functionality
 * @author Lucas Anderson
 * @version 0.1
 */
public class OrgToolTest {
    
    public static void assertFilesEqual(File expected, File actual) throws IOException {
        BufferedReader expectedReader = new BufferedReader(new FileReader(expected));
        BufferedReader actualReader = new BufferedReader(new FileReader(actual));
        
        String expectedLine;
        while ((expectedLine = expectedReader.readLine()) != null) {
            String actualLine = actualReader.readLine();
            assertNotNull("Expected had more lines then the actual.", actualLine);
            assertEquals(expectedLine, actualLine);
        }
        assertNull("Actual had more lines then the expected.", actualReader.readLine());
        expectedReader.close();
        actualReader.close();
    }

    /**
     * All parent orgs are added before their children
     */
    @Test
    public void parentsBeforeChildren() {
        try {
            File hierarchy = new File("src/test/resources/parentsBeforeChildren.hierarchy");
            File userData = new File("src/test/resources/parentsBeforeChildren.userdata");
            File expected = new File("src/test/resources/parentsBeforeChildren.expected");
            File log = new File("src/test/logs/parentsBeforeChildren.log");
            
            OrgCollection orgCol = OrgCollectionFactory.create(OrgCollectionTypes.SIMPLE_ORG_COLLECTION,
                            hierarchy, userData, log);
            
            // Test the getOrg method
            Org bean = orgCol.getOrg(new Long(2));
            assertEquals(2, bean.getTotalNumUsers());
            assertEquals(138, bean.getTotalNumFiles());
            assertEquals(239, bean.getTotalNumBytes());
            assertEquals(123, bean.getChildOrgs().get(0).getTotalNumFiles());
            
            bean = orgCol.getOrg(new Long(42));
            assertNull(bean);
            
            List<Org> flatTree = orgCol.getOrgTree(new Long(1), true);
            assertNotNull(flatTree);
            // For ease of testing going to assume nodes are in a specific order (i.e. tree order, same as log)
            assertEquals(287, flatTree.get(0).getTotalNumBytes()); // orgId = 1
            assertEquals(123, flatTree.get(2).getTotalNumFiles()); // orgId = 4
            assertEquals(2, flatTree.get(3).getTotalNumUsers()); // orgId = 3
          
            // Compare expected log output to what was logged
            assertFilesEqual(expected, log);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * All child orgs are added before their parents
     */
    @Test
    public void childrenBeforeParents() {
        try {
            File hierarchy = new File("src/test/resources/childrenBeforeParents.hierarchy");
            File userData = new File("src/test/resources/childrenBeforeParents.userdata");
            File expected = new File("src/test/resources/childrenBeforeParents.expected");
            File log = new File("src/test/logs/childrenBeforeParents.log");
            
            OrgCollection orgCol = OrgCollectionFactory.create(OrgCollectionTypes.SIMPLE_ORG_COLLECTION,
                            hierarchy, userData, log);
            
            // Test the getOrg method
            Org bean = orgCol.getOrg(new Long(2));
            assertEquals(2, bean.getTotalNumUsers());
            assertEquals(138, bean.getTotalNumFiles());
            assertEquals(239, bean.getTotalNumBytes());
            
            bean = orgCol.getOrg(new Long(42));
            assertNull(bean);
            
            List<Org> flatTree = orgCol.getOrgTree(new Long(1), true);
            assertNotNull(flatTree);
            // For ease of testing going to assume nodes are in a specific order (i.e. tree order, same as log)
            assertEquals(287, flatTree.get(0).getTotalNumBytes()); // orgId = 1
            assertEquals(123, flatTree.get(2).getTotalNumFiles()); // orgId = 4
            assertEquals(2, flatTree.get(3).getTotalNumUsers()); // orgId = 3
            
            assertFilesEqual(expected, log);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Detect attempt to add duplicate orgs
     */
    @Test
    public void duplicateOrgId() {
        try {
            File hierarchy = new File("src/test/resources/duplicateOrgId.hierarchy");
            File userData = new File("src/test/resources/duplicateOrgId.userdata");
            File expected = new File("src/test/resources/duplicateOrgId.expected");
            File log = new File("src/test/logs/duplicateOrgId.log");
            
            OrgCollection orgCol = OrgCollectionFactory.create(OrgCollectionTypes.SIMPLE_ORG_COLLECTION,
                            hierarchy, userData, log);
            
            assertNull(orgCol.getOrg(new Long(2000)));
            
            assertFilesEqual(expected, log);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Detect attempt to add an org where it is its own parent (i.e. orgId == parentId)
     */
    @Test(expected = IllegalArgumentException.class)
    public void simpleCycle() {
        try {
            File hierarchy = new File("src/test/resources/simpleCycle.hierarchy");
            File userData = new File("src/test/resources/simpleCycle.userdata");
            File expected = new File("src/test/resources/simpleCycle.expected");
            File log = new File("src/test/logs/simpleCycle.log");
            
            OrgCollection orgCol = OrgCollectionFactory.create(OrgCollectionTypes.SIMPLE_ORG_COLLECTION,
                            hierarchy, userData, log);
            
            assertNull(orgCol.getOrg(new Long(2000)));
            
            assertFilesEqual(expected, log);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Verify that org id's of "0" and "null" are treated as null
     */
    @Test
    public void treatZeroAndNullAsNull() {
        try {
            File hierarchy = new File("src/test/resources/treatZeroAndNullAsNull.hierarchy");
            File userData = new File("src/test/resources/treatZeroAndNullAsNull.userdata");
            File expected = new File("src/test/resources/treatZeroAndNullAsNull.expected");
            File log = new File("src/test/logs/treatZeroAndNullAsNull.log");
            
            OrgCollection orgCol = OrgCollectionFactory.create(OrgCollectionTypes.SIMPLE_ORG_COLLECTION,
                            hierarchy, userData, log);
            
            assertNull(orgCol.getOrg(new Long(2000)));
            
            assertFilesEqual(expected, log);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test a "random" hierarchy
     * It also has an orphan in it
     */
    @Test
    public void random() {
        try {
            File hierarchy = new File("src/test/resources/random.hierarchy");
            File userData = new File("src/test/resources/random.userdata");
            File expected = new File("src/test/resources/random.expected");
            File log = new File("src/test/logs/random.log");
            
            OrgCollection orgCol = OrgCollectionFactory.create(OrgCollectionTypes.SIMPLE_ORG_COLLECTION,
                            hierarchy, userData, log);
            
            assertNull(orgCol.getOrg(new Long(2000)));
            
            assertFilesEqual(expected, log);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
