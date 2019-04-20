package edu.smith.cs.csc212.fp;
import java.lang.Math;

public abstract class Expr {
	public abstract double eval();
	private static class Node extends Expr{
		// each node has a truth value, a left node, and a right node
		char prop;
		Node left;
		Node right;
		// this creates a new node with a certain truth value
		public Node(char letter) {
			this.prop = letter;
			this.left = null;
			this.right = null;
		}
		public double eval() {
			// TODO Auto-generated method stub
			return this.prop;
		}
	}
	
	public static class Value extends Expr{
		double value;
		public Value(double value, char letter) {
			this.value = value;
			
		}
		public double eval() {
			return value;
		}
	}
	
	public static class PropExpr extends Expr {
		String operator;
		Expr left;
		Expr right;
		
		public PropExpr(String operator, Expr left, Expr right) {
			this.operator = operator;
			this.left = left;
			this.right = right;
		}
		
		public double eval() {
			if ("AND".equals(operator)) {
				return Math.min(left.eval(), right.eval());
			}
			else if ("OR".equals(operator)) {
				return Math.max(left.eval(), right.eval());
			}
			else if ("IMPL".equals(operator)) {
				if (left.eval() <= right.eval()) {
					return 1;
				}
				else {
					return 1-(left.eval()-right.eval());
				}
			}
			else if ("NOT".equals(operator)) {
				if (left==null) {
					return 1-right.eval();
				}
				return 1-left.eval();
			}
			throw new UnsupportedOperationException(operator);
		}
	}
	
	public static void main(String[] args) {
		Expr tree = new PropExpr("OR", new PropExpr("AND", new Value(0, 'p'),new Value(1, 'q')), new Value(0, 'q'));
	    System.out.println(tree.eval());
	  }
}
