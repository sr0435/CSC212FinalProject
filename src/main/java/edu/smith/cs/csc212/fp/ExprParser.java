package edu.smith.cs.csc212.fp;

import java.util.List;
import java.util.Scanner;

// starter code taken from CSC212Calculator
public class ExprParser {
	/**
	 * The list of tokens so far.
	 */
	List<String> tokens;
	/**
	 * The position in this token stream.
	 */
	int position;

	/**
	 * Construct a parse from a list of tokens.
	 * 
	 * @param tokens the list of important strings.
	 */
	public ExprParser(List<String> tokens) {
		this.tokens = tokens;
		this.position = 0;
	}

	/**
	 * Advance past a given token; or crash if it is not what we think.
	 * 
	 * @param what the token to skip over.
	 */
	public void expectExact(String what) {
		String value = tokens.get(position++);
		if (!value.equals(what)) {
			throw new RuntimeException("Expected: " + what);
		}
	}

	/**
	 * It's either a number or a variable. If it starts with a number, try it as a
	 * number.
	 * 
	 * @return Value or Variable.
	 */
	public Expr readProp() {

		String value = tokens.get(position++);
		if (!Character.isLetter(value.charAt(0))) {
			throw new RuntimeException("Your expression is not in order or complete");
		}

		return new Expr.Value(value);
	}

	/**
	 * Which token is next?
	 * 
	 * @return get the current token or null.
	 */
	public String peek() {
		if (position < tokens.size()) {
			return tokens.get(position);
		}
		return null;
	}

	/**
	 * Addition and subtraction should be considered lowest precedence. Every time
	 * we want to "recurse" here, we actually call "readMulDivExpr" to give
	 * multiplication higher precedence.
	 * 
	 * @return a tree of all the multiplication/division expressions we can find.
	 */
	public Expr readConnectives() {

		Expr left = readExpr();

		while (position < tokens.size()) {
			String tok = peek();
			if (tok.equals("&") || tok.equals("∨") || tok.equals(">")) {
				position++;
				Expr right = readExpr();
				left = new Expr.PropExpr(tok, left, right);
			} else {
				break;
			}
		}
		return left;
	}

	/**
	 * This rule reads parentheses, or negations in front, or a proposition.
	 * 
	 * The BNF for this looks like:
	 * <pre>
	 * expr := '(' + connectiveExpr + ')' 
	 *       | '~' expr 
	 *       | variable
	 * connectiveExpr := expr '&' expr
	 *             | expr '∨' expr
	 *             | expr '>' expr
	 * </pre>
	 * 
	 * In order for precedence to work inside a parentheses; we basically start at
	 * the lowest level after seeing one.
	 * 
	 * @return the expression subtree starting from here.
	 */
	public Expr readExpr() {
		String tok = tokens.get(position);
		if (tok.equals("(")) {
			expectExact("(");
			Expr e = readConnectives();
			expectExact(")");
			return e;
		} else if (tok.equals("~")) {
			expectExact("~");
			if (peek() == null) {
				throw new RuntimeException("Your expression is not in order or complete");
			}
			return new Expr.PropExpr("~", readConnectives(), null );
		} else {
			return readProp();
		}
	}

	public static Expr parse() {
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
		String userInput = scan.nextLine();
		scan.close();
		System.out.println("");
		ExprParser p = new ExprParser(Tokenizer.tokenize(userInput));
		return p.readConnectives();
	}
}
