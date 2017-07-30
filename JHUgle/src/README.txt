Name: Nicholas Garcia
JHED: ngarcia5
Email: ngarcia5@jhu.edu
Class: CS 600.226
Semester: Spring 2016

Assignment: Project 2 Part B

README

Notes: 
I reused the code we had previously in p1 for reading in files from the command 
line. However, I used my own code for parsing the file. I used .next() in order 
to disregard white space (including tab characters). 

I made my JHUgle look exactly like the sample shown in the assignment posting. 
One thing I added that was in the assignment posting was an error message (system.err) 
for when a query is entered that doesn't appear in the HashMap. When that happens, a 
"No results found." message is printed to standard error. However, when commanded to 
PRINT such a query, a blank line will simply be printed. 

Similarly, if AND/OR is called when there isn't two or more queries on the Deque, 
the program continues but an error message is printed. I tested this both with 1 and 0 
queries on the stack and it simply prints a message to standard error and continues. 

JHUgle is set to print a blank line after each "Please enter a query: " prompt, but not 
before the next prompt. Therefore, if PRINT is request on a query with no results (the error 
message having already printed when the query was entered) it will simply output a blank 
line and the Stack Size. However, this only applies when the user is manually entering 
queries and commands. When the user is entering them via a file in standard in, there are no 
blank lines between the prompts (unless PRINT is called on a query with no search results). 

I didn't get a chance to properly finish Part A, so for part B I used the Java API HashMap 
since it was said we could (on Piazza). For the storage of query results I used a Deque 
with a LinkedList of HashSets (as in the HashSet<String> that was used as the values in 
the HashMap). 

Reflection:
I really enjoyed this part of the project. It involved more reasoning and was less 
tedious than the previous projects. 