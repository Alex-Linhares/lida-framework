/*******************************************************************************
 * Copyright (c) 2009, 2011 The University of Memphis.  All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the LIDA Software Framework Non-Commercial License v1.0 
 * which accompanies this distribution, and is available at
 * http://ccrg.cs.memphis.edu/assets/papers/2010/LIDA-framework-non-commercial-v1.0.pdf
 *******************************************************************************/
package edu.memphis.ccrg.lida.proceduralmemory;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.memphis.ccrg.lida.framework.shared.Linkable;
import edu.memphis.ccrg.lida.framework.shared.NodeStructure;
import edu.memphis.ccrg.lida.framework.strategies.StrategyImpl;
import edu.memphis.ccrg.lida.framework.tasks.TaskManager;

/**
 * A basic algorithm to activate {@link Scheme}s given a {@link NodeStructure}
 * @author Ryan J. McCall
 *
 */
public class BasicSchemeActivationStrategy extends StrategyImpl implements SchemeActivationStrategy {

	private static final Logger logger = Logger
			.getLogger(BasicSchemeActivationStrategy.class.getCanonicalName());

	private static final double DEFAULT_SELECTION_THRESHOLD = 0.5;
	private double schemeSelectionThreshold = DEFAULT_SELECTION_THRESHOLD;
	private ProceduralMemory pm;

	/**
	 * Default constructor
	 */
	public BasicSchemeActivationStrategy(){
	}
		
	@Override
	public void init(){
		super.init();
		schemeSelectionThreshold = (Double)getParam("schemeSelectionThreshold", DEFAULT_SELECTION_THRESHOLD);
	}

	/**
	 * params[0] must contain Map<? extends Object, Set<Scheme>> with all the
	 * Schemes of {@link ProceduralMemory}
	 * 
	 * @see edu.memphis.ccrg.lida.proceduralmemory.SchemeActivationStrategy#activateSchemesWithBroadcast(edu.memphis.ccrg.lida.framework.shared.NodeStructure,
	 *      java.lang.Object[])
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void activateSchemesWithBroadcast(NodeStructure broadcast, Object... params) {
		logger.log(Level.FINEST, "activating schemes from broadcast",
				TaskManager.getCurrentTick());
		Set<Scheme> toInstantiate = new HashSet<Scheme>();
		Map<?, Set<Scheme>> schemeMap = (Map<?, Set<Scheme>>) params[0];
		for (Linkable lnk: broadcast.getLinkables()) {	
			Set<Scheme> schemes = schemeMap.get(lnk.getExtendedId());
			if (schemes != null) {
				for (Scheme scheme : schemes) {
					scheme.excite(lnk.getActivation() / scheme.getContext().getNodeCount());
					if (scheme.getActivation() >= schemeSelectionThreshold) {
						//To prevent repeats we stored all schemes over threshold in a set.
						//repeats occur with this algorithm when the scheme selection threshold is low
						toInstantiate.add(scheme);
					}
				}
			}
		}
		
		for(Scheme s: toInstantiate){
			pm.createInstantiation(s);
		}
	}
	
	@Override
	public void setProceduralMemory(ProceduralMemory pm) {
		this.pm = pm;
	}

	@Override
	public void setSchemeSelectionThreshold(double threshold) {
		this.schemeSelectionThreshold = threshold;
	}


}