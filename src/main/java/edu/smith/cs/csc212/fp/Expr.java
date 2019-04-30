package edu.smith.cs.csc212.fp;
import java.lang.Math;
import java.util.Map;

public abstract class Expr {
	// basic code taken from CSC212 Notes

	public abstract double eval(Map<String, Double> props);


	public static class Value extends Expr {
		Double value;
		String prop;

		public Value(String prop) {
			this.prop = prop;
			//this.value = null;
		}
		
		public String toString() {
			return "("+prop+" "+value+")";
		}

		public double eval(Map<String, Double> props) {
			for (String key : props.keySet()) {
				if (key.equals(this.prop)) {
					this.value = props.get(key);
					//System.out.println("value" + this.value);
				}
			}
			try {
				return this.value;
			}
			catch (Exception NullPointerException) {
				throw new Error("You don't have a complete expression! ");
			}
			
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

		public PropExpr(String operator) {
			this(operator, null, null);
		}

		public double eval(Map<String, Double> props) {
			if ("&".equals(operator)) {
				//System.out.println("left: " + left.toString() + " " + left.eval(props));
				return Math.min(left.eval(props), right.eval(props));
			}
			else if ("#".equals(operator)) {
				return Math.max(left.eval(props), right.eval(props));
			}
			else if (">".equals(operator)) {
				if (left.eval(props) <= right.eval(props)) {
					return 1;
				}
				else {
					return 1-(left.eval(props)-right.eval(props));
				}
			}
			else if ("~".equals(operator)) {
				//if (left==null) {
				//return 1-right.eval(props);
				//}
				right = null;
				return 1-left.eval(props);
			}
			throw new UnsupportedOperationException(operator);
		}
		public String toString() {
			return "("+operator+" "+left.toString()+" "+right.toString()+")";
		}


	}
}
