import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

/** Set of Junit tests for our WGraphP4 implementations.
 * 
 * Nicholas Garcia, ngarcia5
 *  Ashwin Bhat, abhat4
 *  Lousanna Cai, lcai10
 *  Shain Bannowsky, sbannow1
 *  Data Structures 600.226.02
 *  Project 4
 * 600.226.02
 * Data Structures Project 4
 */
public class WGraphTest {
    /**Graph for testing.*/
    WGraphP4<Character> g;
    /**Some Gvertices for testing.*/
    GVertex<Character> v, u, x, y;
    /**Some Wedges for testing.*/
    WEdge<Character> e, f;
    
    /**
     * Setup before testing.
     */
    @Before
    public void setupGraph() {
        this.g = new WGraphP4<Character>(100);
        this.v = new GVertex<Character>('v', 0);
        this.u = new GVertex<Character>('u', 1);
        this.x = new GVertex<Character>('x', 2);
        this.y = new GVertex<Character>('y', 3);
        this.e = new WEdge<Character>(this.v, this.u, 1.0);
        this.f = new WEdge<Character>(this.v, this.x, 2.0);
    }
    
    /**
     * Tests if graph is empty.
     */
    @Test
    public void testEmpty() {
        assertEquals(0, this.g.numEdges());
        assertEquals(0, this.g.numVerts());
    }
    
    /**
     * Tests if add vertex works.
     */
    @Test
    public void testAddVertex() {
        assertEquals(true, this.g.addVertex(this.v));
        assertEquals(true, this.g.addVertex(this.u));
        //vertex with same data can be added again, ID is changed
        assertEquals(true, this.g.addVertex(this.v));
        //minor test for setID
        this.v.setID(1);
        assertEquals(1, this.v.id());
        GVertex<Character> a = new GVertex<Character>('v', 10);
        //checks if vertex with same data as another can still be added
        assertEquals(true, this.g.addVertex(a));
        //tests if vertices that are null or have null for data are false
        GVertex<Character> na = new GVertex<Character>(null, 5);
        GVertex<Character> nada = null;
        assertEquals(false, this.g.addVertex(na));
        assertEquals(false, this.g.addVertex(nada));
        assertEquals(4, this.g.numVerts());
        
        
    }
    
    /**
     * Tests if addEdge works.
     */
    @Test
    public void testAddEdge() {
        assertEquals(true, this.g.addEdge(this.e));
        assertTrue(this.g.addEdge(this.f));
        //this next one already exists
        assertEquals(false, this.g.addEdge(this.v, this.x, 2));
        assertEquals(true, this.g.allEdges().contains(this.f));
        assertEquals(true, this.g.allEdges().contains(this.e));
        assertEquals(false, this.g.addEdge(this.v, this.u, 1));
        assertEquals(false, this.g.addEdge(this.f));
        assertEquals(2, this.g.numEdges());
        
    }
    
    /**
     * Tests adjacency method.
     */
    @Test
    public void testAdjacency() {
        this.g.addVertex(this.v);
        this.g.addVertex(this.u);
        this.g.addVertex(this.x);
        this.g.addVertex(this.y);
        assertEquals(false, this.g.areAdjacent(this.u, this.v));
        this.g.addEdge(this.e);
        this.g.addEdge(this.v, this.x, 2.0);
        assertEquals(true, this.g.areAdjacent(this.v, this.u));
        assertTrue(this.g.neighbors(this.v).contains(this.u));
        assertTrue(this.g.neighbors(this.u).contains(this.v));
        assertEquals(true, this.g.areAdjacent(this.u, this.v));
        assertTrue(this.g.neighbors(this.v).contains(this.x));
        assertEquals(true, this.g.areAdjacent(this.v, this.x));
        assertEquals(false, this.g.areAdjacent(this.x, this.u));
        assertEquals(false, this.g.areAdjacent(this.v, this.y));
    }
    
    /**
     * Tests the incidence method.
     */
    @Test
    public void testIncidence() {
        this.g.addVertex(this.v);
        this.g.addVertex(this.u);
        this.g.addVertex(this.x);
        this.g.addVertex(this.y);
        this.g.addEdge(this.e);
        assertEquals(false, this.g.areIncident(this.e, this.x));
        assertEquals(false, this.g.areIncident(this.e, this.y));
        assertEquals(true, this.g.areIncident(this.e, this.v));
        assertEquals(true, this.g.areIncident(this.e, this.u));
        this.g.addEdge(this.f);
        assertEquals(true, this.g.areIncident(this.f, this.x));
        assertEquals(false, this.g.areIncident(this.f, this.u));
        assertEquals(4, this.g.numVerts());
        assertEquals(2, this.g.numEdges());
    }
    
    /**
     * Tests the degree method.
     */
    @Test
    public void testDegree() {
        this.g.addVertex(this.v);
        this.g.addVertex(this.u);
        this.g.addVertex(this.x);
        this.g.addVertex(this.y);
        assertEquals(0, this.g.degree(this.v));
        this.g.addEdge(this.e);
        assertEquals(1, this.g.degree(this.v));
        this.g.addEdge(this.f);
        assertEquals(2, this.g.degree(this.v));
        assertEquals(1, this.g.degree(this.x));
        assertEquals(0, this.g.degree(this.y));
    }
    
    /**
     * Tests the neighbors method.
     */
    @Test
    public void testNeighbors() {
        this.g.addVertex(this.v);
        this.g.addVertex(this.u);
        this.g.addVertex(this.x);
        this.g.addVertex(this.y);
        assertEquals("[]", this.g.neighbors(this.v).toString());
        this.g.addEdge(this.e);
        //        System.out.println(g.neighbors(v).toString());
        assertEquals("[1]", this.g.neighbors(this.v).toString());
        assertEquals("[0]", this.g.neighbors(this.u).toString());
        this.g.addEdge(this.f);
        assertEquals("[1, 2]", this.g.neighbors(this.v).toString());
        assertEquals("[0]", this.g.neighbors(this.u).toString());
        assertEquals("[0]", this.g.neighbors(this.x).toString());
        assertEquals("[]", this.g.neighbors(this.y).toString());
    }
    
    /**
     * Tests the delete edge method.
     */
    @Test
    public void testDeleteEdge() {
        this.g.addVertex(this.v);
        this.g.addVertex(this.u);
        this.g.addVertex(this.x);
        this.g.addVertex(this.y);
        assertEquals(0, this.g.numEdges());
        this.g.addEdge(this.e);
        assertEquals(1, this.g.numEdges());
        this.g.addEdge(this.f);
        assertEquals(2, this.g.numEdges());
        assertEquals(true, this.g.deleteEdge(this.v, this.u));
        assertEquals(1, this.g.numEdges());
        assertEquals(true, this.g.deleteEdge(this.v, this.x));
        assertEquals(0, this.g.numEdges());
        assertEquals(4, this.g.numVerts());   
    }
    
    /**
     * Tests the allEdges method.
     */
    @Test
    public void testAllVertices() {
        this.g.addVertex(this.v);
        this.g.addVertex(this.u);
        this.g.addVertex(this.x);
        this.g.addVertex(this.y); 
        assertEquals("[0, 1, 2, 3]", this.g.allVertices().toString());
    }
    
    /**
     * Tests the incidentEdge method.
     */
    @Test
    public void testIncidentEdge() {
        this.g.addVertex(this.v);
        this.g.addVertex(this.u);
        this.g.addVertex(this.x);
        this.g.addVertex(this.y);
        this.g.addEdge(this.e); 
        this.g.addEdge(this.f); 
        assertEquals("[(0, 1, 1.0), (0, 2, 2.0)]", 
                this.g.incidentEdges(this.v).toString());
    }
    
    /**
     * Tests the all vertices method.
     */
    @Test
    public void testAllEdges() {
        this.g.addVertex(this.v);
        this.g.addVertex(this.u);
        this.g.addVertex(this.x);
        this.g.addVertex(this.y);
        this.g.addEdge(this.e); 
        this.g.addEdge(this.f); 
        assertEquals("[(0, 1, 1.0), (0, 2, 2.0)]", 
                this.g.allEdges().toString());
    }
    
    /**
     * Test the depth-first method.
     */
    @Test
    public void testDepthFirst() {
        WGraphP4<String> graph = new WGraphP4<String>();
        
        GVertex<String> a = new GVertex<String>("a", 0);
        GVertex<String> b = new GVertex<String>("b", 1);
        GVertex<String> c = new GVertex<String>("c", 2);
        GVertex<String> d = new GVertex<String>("d", 3);
        GVertex<String> i = new GVertex<String>("e", 4);
        GVertex<String> h = new GVertex<String>("f", 5);
        
        graph.addEdge(a, c, 1);
        graph.addEdge(a, i, 2);
        graph.addEdge(b, c, 3);
        graph.addEdge(b, h, 4);
        graph.addEdge(c, d, 5);
        graph.addEdge(c, h, 6);
        graph.addEdge(c, i, 7);
        graph.addEdge(i, h, 8);
        graph.addEdge(d, h, 9);
        
        List<GVertex<String>> list = graph.depthFirst(a);
        assertEquals("[0, 2, 3, 1]", list.toString());
        
        
    }
    
    
    
    
    
    
    

}


