package edu.smith.cs.csc212.fp;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {
	
	public static String translate(String input) {
		input = input.toLowerCase();
		//https://stackoverflow.com/questions/7347856/how-to-convert-a-string-into-an-arraylist
		List<String> expression = Arrays.asList(input.split(" "));
		
		//https://examples.javacodegeeks.com/core-java/util/collections/replace-specific-element-of-list-example/
		Collections.replaceAll(expression, "and", "&");
		Collections.replaceAll(expression, "or", "#");
		Collections.replaceAll(expression, "not", "~");
		Collections.replaceAll(expression, "impl", ">");
		
		StringBuilder stringer = new StringBuilder();
		for (String term : expression) {
			stringer.append(term);
		}

		System.out.println(stringer);
		return stringer.toString();
	}
	
	public static String translator() {
		System.out.println("Type in your expression using single letters for propositions, as well as ");
		System.out.println("AND, OR, NOT, or IMPL separated by spaces (including parentheses). ");
		System.out.println("Put parentheses around NOT statements (ex. ( not q ) )");
		System.out.println("Example: p and q");
		//https://stackoverflow.com/questions/5287538/how-can-i-get-the-user-input-in-java
		//System.out.println("\n");
		Scanner scan = new Scanner(System.in);
		String input = scan.nextLine();
		scan.close();
		
		return translate(input);
	}
	
	
}
