package org.data2semantics.mustard.experiments.utils;

import java.util.ArrayList;
import java.util.List;

import org.data2semantics.mustard.kernels.data.RDFData;
import org.data2semantics.mustard.learners.evaluation.EvaluationUtils;
import org.data2semantics.mustard.rdf.DataSetUtils;
import org.data2semantics.mustard.rdf.RDFDataSet;
import org.data2semantics.mustard.rdf.RDFFileDataSet;
import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.Value;
import org.openrdf.rio.RDFFormat;

public class AIFBDataSet implements ClassificationDataSet {
	private RDFDataSet tripleStore;
	private RDFData data;
	private List<Double> target;

	public AIFBDataSet(RDFDataSet tripleStore) {
		super();
		this.tripleStore = tripleStore;
	}

	public void create() {
		List<Statement> stmts = tripleStore.getStatementsFromStrings(null, "http://swrc.ontoware.org/ontology#affiliation", null);

		List<Resource> instances = new ArrayList<Resource>();
		List<Value> labels = new ArrayList<Value>();

		for (Statement stmt : stmts) {
			instances.add(stmt.getSubject());
			labels.add(stmt.getObject());
		}

		List<Statement> blackList = DataSetUtils.createBlacklist(tripleStore, instances, labels);
		EvaluationUtils.removeSmallClasses(instances, labels, 5);
		
		target = EvaluationUtils.createTarget(labels);
		data = new RDFData(tripleStore, instances, blackList);
	}

	public RDFData getRDFData() {
		return data;
	}

	public List<Double> getTarget() {
		return target;	
	}

}
