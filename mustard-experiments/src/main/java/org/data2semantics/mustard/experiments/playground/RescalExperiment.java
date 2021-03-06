package org.data2semantics.mustard.experiments.playground;

import java.util.ArrayList;
import java.util.List;

import org.data2semantics.mustard.experiments.SimpleGraphKernelExperiment;
import org.data2semantics.mustard.experiments.rescal.RESCALKernel;
import org.data2semantics.mustard.experiments.utils.Result;
import org.data2semantics.mustard.experiments.utils.ResultsTable;
import org.data2semantics.mustard.kernels.data.RDFData;
import org.data2semantics.mustard.kernels.graphkernels.rdfdata.RDFWLSubTreeKernel;
import org.data2semantics.mustard.learners.evaluation.Accuracy;
import org.data2semantics.mustard.learners.evaluation.EvaluationFunction;
import org.data2semantics.mustard.learners.evaluation.F1;
import org.data2semantics.mustard.learners.evaluation.utils.EvaluationUtils;
import org.data2semantics.mustard.learners.libsvm.LibSVMParameters;
import org.data2semantics.mustard.rdf.DataSetUtils;
import org.data2semantics.mustard.rdf.RDFDataSet;
import org.data2semantics.mustard.rdf.RDFFileDataSet;
import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.Value;
import org.openrdf.rio.RDFFormat;

public class RescalExperiment {
	private static String dataFile = "datasets/aifb-fixed_complete.n3";


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		RDFDataSet dataset = new RDFFileDataSet(dataFile, RDFFormat.N3);

		List<Statement> stmts = dataset.getStatementsFromStrings(null, "http://swrc.ontoware.org/ontology#affiliation", null);

		List<Resource> instances = new ArrayList<Resource>();
		List<Value> labels = new ArrayList<Value>();

		for (Statement stmt : stmts) {
			instances.add(stmt.getSubject());
			labels.add(stmt.getObject());
		}

		EvaluationUtils.removeSmallClasses(instances, labels, 5);
		List<Statement> blackList = DataSetUtils.createBlacklist(dataset, instances, labels);

		List<Double> target = EvaluationUtils.createTarget(labels);

		List<EvaluationFunction> evalFuncs = new ArrayList<EvaluationFunction>();
		evalFuncs.add(new Accuracy());
		evalFuncs.add(new F1());

		ResultsTable resTable = new ResultsTable();
		resTable.setDigits(3);

		long[] seeds = {11,21,31,41,51}; //,61,71,81,91,101};

		double[] cs = {1, 10, 100, 1000, 10000, 100000};	

		LibSVMParameters svmParms = new LibSVMParameters(LibSVMParameters.C_SVC, cs);
		svmParms.setNumFolds(5);	

		RDFData data = new RDFData(dataset, instances, blackList);

		///*
		int[] numLatent = {10, 20, 50, 100, 200}; //,6,7,8,9,10};

		for (int num : numLatent) {

			List<RESCALKernel> kernels = new ArrayList<RESCALKernel>();
			//RESCALKernel kernel = new RESCALKernel("C:/Users/Gerben/Dropbox/D2S/python_stuff/Ext-RESCAL-master/test", 0.001, 10, true);

			kernels.add(new RESCALKernel("C:/Users/Gerben/Dropbox/D2S/python_stuff/Ext-RESCAL-master/test",  0.0, num, 3, false, true));
			/*
			kernels.add(new RESCALKernel("C:/Users/Gerben/Dropbox/D2S/python_stuff/Ext-RESCAL-master/test",  0.001, num, 3, false, true));
			kernels.add(new RESCALKernel("C:/Users/Gerben/Dropbox/D2S/python_stuff/Ext-RESCAL-master/test",  0.01, num, 3, false, true));
			kernels.add(new RESCALKernel("C:/Users/Gerben/Dropbox/D2S/python_stuff/Ext-RESCAL-master/test",  0.1, num, 3, false, true));
			kernels.add(new RESCALKernel("C:/Users/Gerben/Dropbox/D2S/python_stuff/Ext-RESCAL-master/test",  1.0, num, 3, false, true));
			*/


			//Collections.shuffle(target);
			SimpleGraphKernelExperiment<RDFData> exp = new SimpleGraphKernelExperiment<RDFData>(kernels, data, target, svmParms, seeds, evalFuncs);

			resTable.newRow("Latent factors: " + num);
			exp.run();

			for (Result res : exp.getResults()) {
				resTable.addResult(res);
			}
		}
		//*/
		
		///*
		List<RDFWLSubTreeKernel> kernelsWL = new ArrayList<RDFWLSubTreeKernel>();	
				

		kernelsWL.add(new RDFWLSubTreeKernel(0, 3, false, false, true, true));
		kernelsWL.add(new RDFWLSubTreeKernel(1, 3, false, false, true, true));
		kernelsWL.add(new RDFWLSubTreeKernel(2, 3, false, false, true, true));
		kernelsWL.add(new RDFWLSubTreeKernel(3, 3, false, false, true, true));
		kernelsWL.add(new RDFWLSubTreeKernel(4, 3, false, false, true, true));
		kernelsWL.add(new RDFWLSubTreeKernel(5, 3, false, false, true, true));
		kernelsWL.add(new RDFWLSubTreeKernel(6, 3, false, false, true, true));


		//Collections.shuffle(target);
		SimpleGraphKernelExperiment<RDFData> exp = new SimpleGraphKernelExperiment<RDFData>(kernelsWL, data, target, svmParms, seeds, evalFuncs);

		resTable.newRow("WL");
		exp.run();

		for (Result res : exp.getResults()) {
			resTable.addResult(res);
		}
		//*/

		/*
		List<RDFIntersectionGraphEdgeVertexPathKernel> kernelsIT = new ArrayList<RDFIntersectionGraphEdgeVertexPathKernel>();	
		
		kernelsIT.add(new RDFIntersectionGraphEdgeVertexPathKernel(1, false, true));
		kernelsIT.add(new RDFIntersectionGraphEdgeVertexPathKernel(2, false, true));
		kernelsIT.add(new RDFIntersectionGraphEdgeVertexPathKernel(3, false, true));
		
		
		//Collections.shuffle(target);
		SimpleGraphKernelExperiment<RDFData> exp = new SimpleGraphKernelExperiment<RDFData>(kernelsIT, data, target, svmParms, seeds, evalFuncs);

		resTable.newRow("IT");
		exp.run();

		for (Result res : exp.getResults()) {
			resTable.addResult(res);
		}
		//*/
		

		System.out.println(resTable);


	}

}
