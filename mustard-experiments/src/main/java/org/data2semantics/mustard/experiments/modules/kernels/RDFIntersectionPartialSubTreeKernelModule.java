package org.data2semantics.mustard.experiments.modules.kernels;

import org.data2semantics.mustard.kernels.data.RDFData;
import org.data2semantics.mustard.kernels.graphkernels.rdfdata.RDFIntersectionPartialSubTreeKernel;
import org.data2semantics.platform.annotation.In;
import org.data2semantics.platform.annotation.Main;
import org.data2semantics.platform.annotation.Module;
import org.data2semantics.platform.annotation.Out;

@Module(name="RDFIntersectionPartialSubTreeKernel")
public class RDFIntersectionPartialSubTreeKernelModule extends AbstractKernelModule<RDFData> {

	
	public RDFIntersectionPartialSubTreeKernelModule(
			@In(name="depth") int depth,
			@In(name="discountFactor") double discountFactor,
			@In(name="inference") boolean inference, 
			@In(name="normalize") boolean normalize,
			@In(name="graphData") RDFData graphData) {
		
		super(new RDFIntersectionPartialSubTreeKernel(depth, discountFactor, inference, normalize), graphData);
	}
	
	@Main
	public double[][] compute() {
		return super.compute();
	}
	
	@Out(name="matrix")
	public double[][] getMatrix() {
		return super.getMatrix();
	}

	@Out(name="runtime")
	public long getRuntime() {
		return super.getRuntime();
	}
}
