package edu.smith.cs.csc212.fp;

import java.util.Set;

import org.junit.Test;

public class MainTest {
	
	@Test
	public void testMain() {
		Expr tree = ExprParser.parse();
		Set<String> variables = tree.findVariables();
		TruthTable.tabler(tree, variables);
	}

}
