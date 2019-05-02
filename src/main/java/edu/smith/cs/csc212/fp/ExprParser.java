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
	
	// reads the expressions with logical connectives
	public Expr readConnectives() {

		Expr left = readExpr();

		while (position < tokens.size()) {
			String tok = peek();
			if (tok==null) {
				System.out.println("expression is not complete");
				throw new Error();
			}
			else if (tok.equals("&") || tok.equals("∨") || tok.equals(">")) {
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
		try {
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
		catch(Exception IndexOutOfBoundsException) {
			System.out.println("your expression is not complete");
			throw new Error();
		}
	}

	public static Expr parse() {
		// beginning instructions
		System.out.println("Type in your expression using single letters for propositions, as well as ");
		System.out.println("AND, OR, NOT, or IMPL separated by spaces (including parentheses). ");
		System.out.println("Put parentheses around NOT statements to specify bound ");
		System.out.println("(ex. (not q) and p vs. not q and p, which is equivalent to not (q and p))");
		System.out.println("Example: p and (not q)");
		System.out.println("");
		//https://stackoverflow.com/questions/5287538/how-can-i-get-the-user-input-in-java
		// takes in the user's input
		Scanner scan = new Scanner(System.in);
		String userInput = scan.nextLine().toLowerCase().strip();
		System.out.println("");
		ExprParser p = new ExprParser(Tokenizer.tokenize(userInput));
		return p.readConnectives();
	}
}
