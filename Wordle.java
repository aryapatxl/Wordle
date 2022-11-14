/*
 * Wordle.java 
 * 
 * An console-based implementation of a popular word-guessing game
 * 
 * starter code: Computer Science 112 staff (cs112-staff@cs.bu.edu)
 *
 * completed by: Shruti Gajjar (shrutiga@bu.edu)
 * Partner: Arya Patel (aryxrp@bu.edu)
 * 
 */

import java.util.*;

public class Wordle {
    // the name of a file containing a collection of English words, one word per line
    public static final String WORD_FILE = "words.txt";

    /*
     * printWelcome - prints the message that greets the user at the beginning of the game
     */  
    public static void printWelcome() {
        System.out.println();   
        System.out.println("Welcome to Wordle!");
        System.out.println("The mystery word is a 5-letter English word.");
        System.out.println("You have 6 chances to guess it.");
        System.out.println();
    }
    
    /*
     * initWordList - creates the WordList object that will be used to select
     * the mystery work. Takes the array of strings passed into main(),
     * since that array may contain a random seed specified by the user 
     * from the command line.
     */
    public static WordList initWordList(String[] args) {
        int seed = -1;
        if (args.length > 0) {
            seed = Integer.parseInt(args[0]);
        }

        return new WordList(WORD_FILE, seed);
    }

    /*
     * readGuess - reads a single guess from the user and returns it
     * inputs:
     *   guessNum - the number of the guess (1, 2, ..., 6) that is being read
     *   console - the Scanner object that will be used to get the user's inputs
     */
    public static String readGuess(int guessNum, Scanner console) {
        String guess;
        do {
            System.out.print("guess " + guessNum + ": ");
            guess = console.next();
        } while (! isValidGuess(guess));

        return guess.toLowerCase();
    }

    /**** ADD YOUR METHODS FOR TASK 1 HERE ****/

    /* 
    * static method called includes that takes two inputs: an arbitrary
    * String object s followed by a single char c. The method returns
    * the boolean literal true if c is found somewhere in s and the
    * boolean literal false if otherwise
    */
     public static boolean includes(String s, char c) {
         for (int i = 0; i < 5; i++) {
             if (s.charAt(i) == c) {
                 return true;
             }
        }
            return false;
     } //includes

    /* 
    * static method called isAlpha that takes an arbitrary String object
    * s as its only input and returns true if all of the characters
    * in s are letters of the alphabet, and returns false if otherwise
    */
     public static boolean isAlpha(String s) {
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isAlphabetic(c) == false) {
                return false;
            }
        }
        return true;
     }

    /* 
    * static method called numOccur that takes two inputs: a single char c followed
    * by an arbitrary String object s. The method counts and returns the
    * number of times that c occurs in s
    */
     public static int numOccur(char c, String s) {
         int count = 0;
         for (int i = 0; i < s.length(); i++) {
             if (s.charAt(i) == c) {
                 count++;
             }
         }
         return count;
     }
     
     /* 
     * static method called numInSamePosn that takes three inputs: a single char c
     * followed by two String objects s1 and s2 that you may assume have the same
     * length. The method counts and returns the number of times that c occurs
     * in the same position in both s1 and s2
     */
     public static int numInSamePosn(char c, String s1, String s2) {
         int count = 0;
         for (int i = 0; i < s1.length(); i++) {
             if (s1.charAt(i) == c && s2.charAt(i) == c) {
                 count++;
             }
         }
         return count;
     }


    /*
     * TASK 2: Implement this method
     * 
     * isValidGuess -  takes an arbitrary string guess and returns true
     * if it is a valid guess for Wordle, and false otherwise
     */
    public static boolean isValidGuess(String guess) {
        if (guess.length() < 5 || guess.length() > 5) {
            System.out.println("Your guess must be 5 letters long.");
            return false;
        }
        if (isAlpha(guess) == false) {
            System.out.println("Your guess must only contain letters of the alphabet.");
            return false;
        }
        else {
            return true;
        }
    }

    /**** ADD YOUR METHOD FOR TASKS 3 and 5 HERE. ****/

    /* method called processGuess that takes two inputs: a 5-character String object guess
    * representing a guess made by the user followed by a 5-character String object mystery
    * representing the “mystery” word that the user is trying to guess. The methos processes
    * the input string guess and provides feedback to the user about how guess compares to
    * mystery by printing the appropriate sequence of characters and returns true if guess is
    * equal to mystery and false if otherwise.
    */ 
    public static boolean processGuess(String guess, String mystery) {
        String answer = "";
        String feedback = "";
        for (int i = 0; i < mystery.length(); i++) {
            if (guess.charAt(i) == mystery.charAt(i)) {
                feedback += (guess.charAt(i) + " ");
                answer += (guess.charAt(i) + "");
                if (answer.equals(mystery)) {
                    return true;
                }
            }
            else if (mystery.indexOf(guess.charAt(i)) != -1) {
                feedback += "[" + guess.charAt(i) + "] ";
            }
            else if (mystery.indexOf(guess.charAt(i)) == -1) {
                feedback += "_ ";
            }
        }
        System.out.println("  " + feedback);
        System.out.println();
        return false;

    }


    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        
        printWelcome();

        // Create the WordList object for the collection of possible words.
        WordList words= initWordList(args);

        // Choose one of the words as the mystery word.
        String mystery = words.getRandomWord();
        
        /*** TASK 4: Implement the rest of the main method below. ***/
        int i = 1;
        do {
            String guess = readGuess(i, console);
            if (processGuess(guess, mystery) == true) {
                System.out.println(mystery.charAt(0) + " " + mystery.charAt(1) + " " + mystery.charAt(2) + " " + mystery.charAt(3) + " " + mystery.charAt(4));
                System.out.println();
                System.out.println("Congrats! You guessed it!");
                break;
            }
            i++;
        } while (i < 7);
        if (i==7) {
            System.out.println("Sorry! Better luck next time!");
            System.out.println("The word was " + mystery + ".");
        }

        console.close();
    }
}