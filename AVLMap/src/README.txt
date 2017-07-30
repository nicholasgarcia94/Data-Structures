Names: Ashwin Bhat, Nicholas Garcia
JHEDs: abhat4, ngarcia5
CS 226 Spring 2016
Data Structures
Project3B

This implementation took us a long time to do. We had to make a few 
changes to the BSTMap implementation that was provided to us after 
part A. At first, we tried to avoid making major changes and using 
inheritance along with adding height information to the BNode class.

However, despite getting a to an implementation for AVL tree map that 
we thought would work, we had issues getting rebalancing to work. This 
seemed to be because the function we had to check if the tree was 
balanced always thought the heights were only off by 1 (thus not needing 
a rebalance). However, that was obviously a big issue since the nodes 
themselves, when printed with key, value, and height, had the correct values 
for all three fields, and the heights indicated that a rebalanced should 
have been performed.

Ultimately, we made changes to the implementation and avoided using 
inheritance. The main change to existing functions were put and remove. 
Remove remained mostly the same with some changes in that it called 
rebalance on the tree, and that we set the root of the entire tree 
to be the return of private remove, which in turn was rebalance being 
called on the root. 

Put was changed more. In order to get rebalance and the appropriate rotates 
to move the node needing rebalancing, we decided to change the return type of 
the private put method to a BNode to mirror that of the private remove. Then 
we used the same concept of returning the rebalance and setting that as the 
root.

By doing this our AVL tree map, then rebalanced beyond what we thought. 
For example a tree was inserted and without balancing it would have looked 
like this:

      10
     /  \
    8    15
   /
  4
 /
2

As we had originally planned to do our implementation to have it balance like this:

      10
     /  \
    4    15
   / \
  2   8

Instead, we balanced by the root. We chose to do this because it got rid of some 
NullPointerException issues when working with parent pointers, and doing this let 
implement the AVLMap without any parent pointers at all. 
That resulted in even better balances like this:

       8
      / \
     4   10
    /     \
   2       15


While we are not sure if this is what the project required, this does indeed fulfil the 
typical conditions of an AVL tree map. The entire tree is balanced at the end and can be 
searched through in order.  

This project was overall, very time-consuming, easily taking us over 30 hours 
of work due to the setbacks we had. 