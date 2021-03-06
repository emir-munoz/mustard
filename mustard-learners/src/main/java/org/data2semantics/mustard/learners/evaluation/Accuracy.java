package org.data2semantics.mustard.learners.evaluation;

import java.io.Serializable;

import org.data2semantics.mustard.learners.Prediction;

public class Accuracy implements EvaluationFunction, Serializable {
		private static final long serialVersionUID = 6731294479222557468L;

	public double computeScore(double[] target, Prediction[] prediction) {
		double correct = 0;	
		for (int i = 0; i < target.length; i++) {
			if (target[i] == prediction[i].getLabel()) {
				correct += 1;
			}
		}
		return correct / (target.length);	
	}

	public boolean isBetter(double scoreA, double scoreB) {
		if (scoreA > scoreB) {
			return true;
		}
		return false;
	}
	
	public String getLabel() {
		return "Accuracy";
	}
	
	public boolean isHigherIsBetter() {
		return true;
	}
	
	
}
