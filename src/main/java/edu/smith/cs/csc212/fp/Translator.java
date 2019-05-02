package edu.smith.cs.csc212.fp;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Translator {
	
	// since people use different symbols for the connectives
	// it's easier to put into words first and then convert them
	public static String translate(String input) {
		// changes everything to lower case to make it consistent
		input = input.toLowerCase();
		String inputt = input.replace("and", "&");
		System.out.println("iinput: " + inputt);
		//https://stackoverflow.com/questions/7347856/how-to-convert-a-string-into-an-arraylist
		// converts the input into a list
		List<String> expression = Arrays.asList(input.split(" "));
		
		//https://examples.javacodegeeks.com/core-java/util/collections/replace-specific-element-of-list-example/
		// replaces the instances of the connector words into the symbols
		Collections.replaceAll(expression, "and", "&");
		Collections.replaceAll(expression, "or", "∨");
		Collections.replaceAll(expression, "not", "~");
		Collections.replaceAll(expression, "impl", ">");
		
		// puts the terms in the expression list into a string
		StringBuilder stringer = new StringBuilder();
		for (String term : expression) {
			stringer.append(term);
		}

		System.out.println("Your Expression: " + input + "; Translated Into: " + stringer);
		System.out.println("");
		return stringer.toString();
		// it's redundant to go from string to list to string to array but i'm too lazy to change it at this point
	}
	
	public static String translator() {
		// instructions
		System.out.println("Type in your expression using single letters for propositions, as well as ");
		System.out.println("AND, OR, NOT, or IMPL separated by spaces (including parentheses). ");
		System.out.println("Put parentheses around NOT statements (ex. ( not q ) )");
		System.out.println("Only works with p, q, and r as propositions");
		System.out.println("Example: p and q");
		System.out.println("");
		//https://stackoverflow.com/questions/5287538/how-can-i-get-the-user-input-in-java
		// takes in the user's input
		Scanner scan = new Scanner(System.in);
		String input = scan.nextLine();
		scan.close();
		System.out.println("");
		return input; //translate(input);
	}
	
	
}
