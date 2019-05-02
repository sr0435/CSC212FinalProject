package edu.smith.cs.csc212.fp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TruthTable {

	public static ArrayList<List<Double>> propLister(ArrayList<List<Double>> input) {
		ArrayList<List<Double>> output = new ArrayList<List<Double>>();
		for (List<Double> tva : input) {
			List<Double> ttva = new ArrayList<>(tva);
			ttva.add(1.0);
			output.add(ttva);
			List<Double> itva = new ArrayList<>(tva);
			itva.add(0.5);
			output.add(itva);
			List<Double> ftva = new ArrayList<>(tva);
			ftva.add(0.0);
			output.add(ftva);
		}
		return output;
	}

	public static void tabler(Expr tree, Set<String> variables) {
		List<String> orderedVariables = new ArrayList<>(variables);

		// creates a list of values for each proposition
		ArrayList<List<Double>> input = new ArrayList<List<Double>>();
		input.add(Arrays.asList(1.0));
		input.add(Arrays.asList(0.5));
		input.add(Arrays.asList(0.0));
		for (int i = 0; i < (orderedVariables.size() - 1); i++) {
			ArrayList<List<Double>> output = propLister(input);
			input = output;
		}
		
		// then creates a map of each value to the propositional variable
		int j = 0;
		Map<String, List<Double>> propList = new HashMap<>();
		for (String var : orderedVariables) {
			List<Double> propInput = new ArrayList<Double>();
			for (int i=0; i<input.size(); i++) {
				propInput.add(input.get(i).get(j));
			}
			propList.put(var, propInput);
			j++;		
		}
		// prints the truth "table"
		for (int k=0; k<input.size(); k++) {
			for (String var : orderedVariables) {
				System.out.println(var + ": " + propList.get(var).get(k));
			}
			System.out.println("tree: " + tree.eval(propList, k));
			System.out.println("\n");
		}
	}
}
