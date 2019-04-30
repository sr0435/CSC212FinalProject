package edu.smith.cs.csc212.fp;

import java.lang.Math;
import java.util.HashMap;
import java.util.Map;

public class TruthTable {

	public static void tabler(int props, Expr tree) {
		if (props > 3) {
			// the function only works for upto 3 propositions
			System.out.println("You're using too many propositions");
			throw new Error();
		}
		Map<String, Double> propList = new HashMap<>();
		// 
		int height = (int) Math.pow(3, props);

		for (int i=0; i<height; i++) {

			int section = height/3;

			if (i<section) {
				propList.put("p", 1.0);
				int subsect = section/3;

				if (i<subsect) {
					propList.put("q", 1.0);
				}

				else if (i<2*subsect) {
					propList.put("q", 0.5);
				}

				else {
					propList.put("q", 0.0);
				}
			}


			else if (i<2*section) {
				propList.put("p", 0.5);
				int subsect = (section/3)+section;

				if (i<subsect) {
					propList.put("q", 1.0);
				}

				else if (i<(subsect+(section/3))) {
					propList.put("q", 0.5);
				}

				else {
					propList.put("q", 0.0);
				}
			}


			else if (i<height) {
				propList.put("p", 0.0);
				int subsect = (2*section)+(section/3);

				if (i<subsect) {
					propList.put("q", 1.0);
				}

				else if (i<(subsect+(section/3))) {
					propList.put("q", 0.5);
				}

				else {
					propList.put("q", 0.0);
				}
			}


			if (props == 3) {
				double r = 1.0 - (i%3)*0.5;
				propList.put("r",r);
				System.out.println("p: " + propList.get("p") + " q: " 
						+ propList.get("q") + " r " + propList.get("r") 
						+ " tree: " + tree.eval(propList));
				System.out.println("\n");
			}

			else if (props == 1) {
				System.out.println("p: " + propList.get("p") + " tree: " + tree.eval(propList));
				System.out.println("\n");
			}

			else {
				System.out.println("p: " + propList.get("p") + " q: " 
						+ propList.get("q") + " tree: " + tree.eval(propList));
				System.out.println("\n");
			}

		}

	}
}
