package edu.smith.cs.csc212.fp;

import java.util.ArrayList;
import java.util.List;

// starter code taken from CSC212Calculator
public class Tokenizer {
	public RuntimeException error(String msg) {
		return new RuntimeException(msg+": "+this.toString());
	}
	private char[] data;
	int position;

	//https://www.javatpoint.com/java-string-replace
	// converts what the user put into the symbols
	public Tokenizer(String input) {
		input = input.replace("and", "&");
		input = input.replace("or", "∨");
		input = input.replace("not", "~");
		input = input.replace("impl", ">");
		this.data = input.toCharArray();
		this.position = 0;
	}

	private int peek() {
		if (position < data.length) {
			return data[position];
		}
		return -1;
	}

	public int remaining() {
		return data.length - position;
	}

	// rest="abcd" 
	// (then .getc()=> "a") 
	// then rest="bcd"
	public String rest() {
		if (position >= data.length) {
			return "";
		}
		return new String(data, position, this.remaining());
	}


	public String toString() {
		return "Tokenizer(@"+position+", ..."+rest()+")";
	}

	// "abcd".consume(2) => "ab", rest="cd"
	public String consume(int amt) {
		String out = new String(data, position, amt);
		position += amt;
		return out;
	}


	public void skipWhitespace() { 
		while(true) { 
			int next = peek(); 
			if (next == -1) { 
				return; 
			} 
			char ch = (char) next; 
			if (Character.isWhitespace(ch)) {
				position++; 
				continue; 
			} 
			break; 
		} 
	}


	public String nextToken() {
		// don't need to skip whitespace since the translator already takes care of that
		skipWhitespace();
		int next = peek();
		if (next == -1) {
			return null;
		}
		char ch = (char) next;
		// and, not, or, impl
		//https://www.fileformat.info/info/unicode/char/2228/index.htm ; symbol for or; unicode "\u2228"
		if (ch == '&' || ch == '~' || ch == '∨' || ch == '>' || ch == '(' || ch == ')') {
			return consume(1);
		}

		//https://docs.oracle.com/javase/tutorial/i18n/text/charintro.html
		// for finding out the type of character
		// since all props are single letters, we don't need to worry
		// about finding the entire string
		if (Character.isLetter(ch)) {
			return consume(1);
		}
		throw error("Unknown token.");
	}



	public static List<String> tokenize(String input) {
		// creates a list
		List<String> output = new ArrayList<>();
		Tokenizer tok = new Tokenizer(input);
		while(true) {
			String token = tok.nextToken();
			if (token == null) break;
			output.add(token);
		}
		return output;
	}
}
