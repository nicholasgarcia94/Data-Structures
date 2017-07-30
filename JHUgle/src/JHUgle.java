/**
 * The JHUgle Search Engine.
 * @author Ashwin Bhat
 * JHED: abhat4
 * CS 226 Spring 2016
 * Project 2 Part B
 */

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Deque;
import java.util.Scanner;
import java.io.IOException;
import java.io.FileReader;
/** The JHUgle Application.*/
public final class JHUgle {
    
    /**The HashMap containing keywords and URLs.*/
    public static HashMap<String, HashSet<String>> database = 
            new HashMap<String, HashSet<String>>();
    
    /**The Deque that contains searches (URLs obtained from queries).*/
    public static Deque<HashSet<String>> search = 
            new LinkedList<HashSet<String>>();
    
    /**Empty private constructor.*/
    private JHUgle() {
        
    }
        
    /**
     * The main for JHUgle.
     * It reads and parses the input file, then starts the search engine.
     * @param args 
     * @throws IOException  if the input file is bad
     */
    public static void main(String[] args) throws IOException {
        
        
        Scanner inFile = null;
        boolean inError = false;
        try {
            
            inFile = new Scanner(new FileReader(args[0]));
        } catch (ArrayIndexOutOfBoundsException a) {
            System.err.println("Must give input filename at command line.");
            inError = true;
        } catch (IOException f) {
            System.err.println("That file can't be opened, try again.");
            inError = true;
        }
        if (inError) {
            System.err.println("exiting...");
            System.exit(1);
        }
        
        Scanner inLine;
        String url;
        String keyLine;
        String keyword;
        while (inFile.hasNext()) {
            url = inFile.nextLine();
            keyLine = inFile.nextLine();
            inLine = new Scanner(keyLine);
            while (inLine.hasNext()) {
                keyword = inLine.next();
                if (database.containsKey(keyword)) {
                    database.get(keyword).add(url);
                } else {
                    HashSet<String> urlSet = new HashSet<String>();
                    urlSet.add(url);
                    database.put(keyword, urlSet);
                }
            }
        }       
        searchEngine();
    }
    /**
     * The method that runs the search engine.
     */
    public static void searchEngine() {
        Scanner keyboard = new Scanner(System.in);
        
        System.out.println("Welcome to JHUgle!");
        String choice;
        do {
            System.out.print("Please enter a query: ");
            if (keyboard.hasNext()) {
                choice = keyboard.next();
            } else {
                choice = "QUIT";
            }
            System.out.println();
            menuLoop(choice);
        } while (!(choice.equals("QUIT")));
        keyboard.close();
    }
    
    /**
     * The method that keeps the menu looping in the search engine.
     * @param s the user's current menu choice
     */
    public static void menuLoop(String s) {
        switch (s) {
            case "PRINT":
                try {
                    String printResult = search.peek().toString(); 
                    printResult = 
                            printResult.substring(1, printResult.length() - 1);
                    System.out.println(printResult);
                    System.out.println("Stack size: " + search.size());
                } catch (NullPointerException n) {
                    System.out.println("Stack is empty!");
                }
                break;
            case "OR":
                try {
                    HashSet<String> set1 = search.pop();
                    HashSet<String> set2 = search.pop();
                    set1.addAll(set2);
                    search.push(set1);
                } catch (NoSuchElementException e) {
                    System.err.println("There aren't enough queries "
                            + "to perform this operation.");
                }
                break;
            case "AND":
                try {
                    HashSet<String> set1 = search.pop();
                    HashSet<String> set2 = search.pop();
                    set1.retainAll(set2);
                    search.push(set1);
                } catch (NoSuchElementException e) {
                    System.err.println("There aren't enough queries "
                            + "to perform this operation.");
                }
                break;
            case "QUIT":
                System.out.println("Thank you, now quitting JHUgle.");
                break;
            case "":
                System.err.println("There seems to be an illegal query.");
                break;
            default:
                if (database.containsKey(s)) {
                    search.push(database.get(s));
                } else {
                    search.push(new HashSet<String>());
                    System.err.println("No results found.");
                }
                break;
        }
    }    
}
