README
Person 1: Ashwin Bhat, abhat4 (Section2)
Person 2: Nicholas Garcia, ngarcia5 (Section2)
Person 3: Shain Bannowsky, sbannow1 (Section2)
Person 4: Lousanna Cai, lcai10 (Section1)

600.226 Spring 2016
Project 4

Division of Work---
Part A:
-GVertex: Nicholas
-WEdge: Lousanna, Nicholas
-WGraphP4: Ashwin, Shain (Kruskal's--Part B)
-WGraphTest: Nicholas (everything else), Ashwin (Depth-first)

Part B:
PQHeap: Ashwin, Shain (Iterator)
PQHeapTest: Ashwin (testIterator, testIteratorOnEmpty), Nick (everything else)

Part C: 
Kruskal's: Shain
Image Reading: Lousanna
PixelDistance: Nicholas, Ashwin

We had some team work issues. Parts A and B were done well ahead of time, but our team


Notes---
Part A:
We implemented our WGraphP4 to properly keep track of IDs. We did this by
forcing IDs to be the next value in sequence regardless of what the user-end
program my have inputted for the ID of the vertex. Our WGraph can accept a 
vertex with the same data as another currently in the graph, but it ensures 
that the IDs are unique. 
We also made the graph undirected so that when an edge was added, a complementary
edge was also made between the vertices so either one could be considered the
source or end based on the edge being used. However, the two complementary edges 
were only counted as one edge and only added to the PQHeap of edges and the 
master list of edges once each.


Part B:
We used the provided default comparator so that by default the PQHeap would be a min 
heap. We of course tested it with both the min and max PQHeap. We decided to add an 
internal iterator class as well for use in kruskals algorithm. 

Part C:
We included a few sample output files. 

What was awkward with respect to the original WGraph interface when using it to solve part C? 
How would you have liked to change it?

-The WGraph interface did not specify Getter methods for the list of vertices and
sorted edges, and implementing Kruskals required access to the vertex array list
and sorted edges heap. Therefore, we added Getter methods for the vertex array
and sorted edge priority heap within the implementation class. 

What k values worked for certain images you tested with? 
What challenges did you encounter in part C in particular?

-We tested with k values of 10, 100, and 200. Challenges encountered with Part C
included getting the RGB values of a pixel. Since getRGB() kept returning -1,
we passed in the value from getRGB() into a Color variable to maintain the RGB
values.

Where is your solution the slowest and why? Could you have done things differently to improve this?

-Our solution is slowest when reading in each pixel value to a graph because the getRGB() method 
combines the alpha, red, green, and blue values into one integer for each pixel, and the pixels are 
stored within a single-dimensional data structure. Instead of using getRGB, one could access the pixels 
directly by reading in the BufferedImage’s raster data.