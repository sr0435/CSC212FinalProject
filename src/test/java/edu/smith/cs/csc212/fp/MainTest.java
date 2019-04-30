package edu.smith.cs.csc212.fp;

import org.junit.Test;

import edu.smith.cs.csc212.fp.Expr.PropExpr;
import edu.smith.cs.csc212.fp.Expr.Value;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;

public class MainTest {
  
  @Test
  public void testTree() {
	Map<Character, Double> propList = new HashMap<>();

	//Expr tree = new PropExpr("#",new Value("p"), new Value("p"));
	//TruthTable.tabler(2,tree);
  }
  
  @Test
  public void testTabler() {
	  Expr tree =  ExprParser.parse("p & q");
	  int props = ExprParser.propNum("p & q");
	  System.out.println(tree.toString());
	  TruthTable.tabler(props,tree);
  }
  
  @Test
  public void testMain() {
	  String input = Main.translator();
	  Expr tree = ExprParser.parse(input);
	  int props = ExprParser.propNum(input);
	  TruthTable.tabler(props, tree);
  }
  
}
