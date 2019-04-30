package edu.smith.cs.csc212.fp;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
			throw new RuntimeException("Your expression is not in order");
		}

			// was new Expr.Value((char) Integer.parseInt(value));
			return new Expr.Value(value);
		//} //else {
			//return new Expr.Value("p");
			//return new Variable(value);
		//}
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
	 * Multiplication and division should be considered highest precedence. Except
	 * for parentheses. Every time we want to "recurse" here, we call the one that
	 * knows about parentheses: readExpr.
	 * 
	 * @return a tree of all the multiplication/division expressions we can find.
	 */
	public Expr readMulDivExpr() {
		Expr left = readExpr();

		while (position < tokens.size()) {
			String tok = peek();

			if (tok.equals("*") || tok.equals("/")) {
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
	 * Addition and subtraction should be considered lowest precedence. Every time
	 * we want to "recurse" here, we actually call "readMulDivExpr" to give
	 * multiplication higher precedence.
	 * 
	 * @return a tree of all the multiplication/division expressions we can find.
	 */
	public Expr readAddSubExpr() {
		
		Expr left = readExpr(); //readMulDivExpr();


		while (position < tokens.size()) {
			String tok = peek();

			if (tok.equals("&") || tok.equals("#") || tok.equals(">")) {
				position++;
				Expr right = readExpr(); //readMulDivExpr();
				left = new Expr.PropExpr(tok, left, right);
			} else {
				break;
			}
		}
		return left;
	}

	/**
	 * This rule reads parentheses, or negatives in front, or a number/value.
	 * 
	 * The BNF for this looks like:
	 * <pre>
	 * expr := '(' + addSubExpr + ')' 
	 *       | '-' expr 
	 *       | number 
	 *       | variable
	 * addSubExpr := mulDivExpr '+' mulDivExpr
	 *             | mulDivExpr '-' mulDivExpr
	 *             | mulDivExpr
	 * mulDivExpr := expr '*' expr
	 *             | expr '/' expr
	 *             | expr
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
			Expr e = readAddSubExpr();
			expectExact(")");
			return e;
		} else if (tok.equals("~")) {
			//System.out.println(position);
			expectExact("~");
			//position++;
			//Expr left = readProp();
			//Expr.Value("p")
			return new Expr.PropExpr("~", readAddSubExpr(), null );
		} else {
			return readProp();
		}
	}
	
	public static int propNum(String input) {
		ExprParser p = new ExprParser(Tokenizer.tokenize(input));
		Set<Character> props = new HashSet<>();
		System.out.println(p.tokens);
		for (String token : p.tokens) {
			Character prop = token.charAt(0);
			if (Character.isLetter(prop)) {
				props.add(prop);
			}
		}
		return props.size();
	}

	public static Expr parse(String input) {
		ExprParser p = new ExprParser(Tokenizer.tokenize(input));
		//System.out.println(p.propNum(p.tokens));
		return p.readAddSubExpr();
	}
}
