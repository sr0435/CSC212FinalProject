package edu.smith.cs.csc212.fp;
import java.lang.Math;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class Expr {
	// basic code taken from CSC212 Notes

	public abstract double eval(Map<String, List<Double>> props, int order);

	public abstract Set<String> findVariables();

	// takes care of the variable and the value of a proposition
	public static class Value extends Expr {
		Double value;
		String prop;

		public Value(String prop) {
			this.prop = prop;
		}

		public String toString() {
			return "("+prop+")";
		}

		public double eval(Map<String, List<Double>> props, int order) {
			// checks if this prop has a truth value in this expression
			// the order is for creating the right combinations
			for (String key : props.keySet()) {
				if (key.equals(this.prop)) {
					this.value = props.get(key).get(order);
				}
			}
			try {
				return this.value;
			}
			catch (Exception NullPointerException) {
				throw new Error("You don't have a complete expression");
			}
		}

		@Override
		public Set<String> findVariables() {
			HashSet<String> vars = new HashSet<>();
			vars.add(this.prop);	
			return vars;
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
		// rules for evaluating truth value
		public double eval(Map<String, List<Double>> props, int order) {
			if ("&".equals(operator)) {
				double ans =  Math.min(left.eval(props, order), right.eval(props, order));
				// prints intermediate operations
				System.out.println(this.toString() + ": " + ans);
				return ans;
			}
			else if ("∨".equals(operator)) {
				double ans =  Math.max(left.eval(props, order), right.eval(props, order));
				System.out.println(this.toString() + ": " + ans);
				return ans;
			}
			else if (">".equals(operator)) {
				if (left.eval(props, order) <= right.eval(props, order)) {
					System.out.println(this.toString() + ": " + 1);
					return 1;
				}
				else {
					double ans = 1-(left.eval(props, order)-right.eval(props, order));
					System.out.println(this.toString() + ": " + ans);
					return ans;
				}
			}
			else if ("~".equals(operator)) {
				right = null;
				double ans =  (1-left.eval(props, order));
				System.out.println(this.toString() + ": " + ans);
				return ans;
			}
			throw new UnsupportedOperationException(operator);
		}
		public String toString() {
			if (right != null) {
				return "("+operator+" "+left.toString()+" "+right.toString()+")";
			}
			else {
				return "("+operator+" "+left.toString()+")";
			}
		}

		@Override
		public Set<String> findVariables() {
			HashSet<String> vars = new HashSet<>();
			vars.addAll(left.findVariables());
			if (right != null) {
				vars.addAll(right.findVariables());
			}
			return vars;
		}
	}
}
