Org Hierarchy
==
A simple tool for turning a csv adjacency lists into a graph (tree).  The graph may be disconnected resulting in a (forest)

#Assumptions
- each orgId in the hierarchy csv file is unique<br />
- each orgId has a unique parent
- there are no cycles
- an orgId is in the range [1, N] where N <= Long.MAX, it may not be null
- a parentId is in the range [0, N], it may be null. 0 is logically the same as null
- a parentId that never is a orgId (ie an orphan) is left alone, maybe someone will add the node later

#Directions:
- building:<br />
`ant build`
- run tests<br />
`ant OrgToolTest`
- add tests
 - edit `./src/test/java/sample/orgtool/OrgToolTest.java`
    either follow the existing tests which use files to verify logging
    and some make calls thru the api to verify accessors are working
    or use standard JUnit logic.
    You could also just modify one of the existing
    `<testname>.hierarchy, <testname>.userdata, <testname>.expected` in the
    `./src/test/resources/` directory
    and rerun the tests a test may expect an exception to occur, so verify that
    the files are not associated with a test the expects an exceptions



