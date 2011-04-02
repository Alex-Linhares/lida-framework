/*******************************************************************************
 * Copyright (c) 2009, 2010 The University of Memphis.  All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the LIDA Software Framework Non-Commercial License v1.0 
 * which accompanies this distribution, and is available at
 * http://ccrg.cs.memphis.edu/assets/papers/2010/LIDA-framework-non-commercial-v1.0.pdf
 *******************************************************************************/
package edu.memphis.ccrg.lida.pam;

import java.util.logging.Level;
import java.util.logging.Logger;

import edu.memphis.ccrg.lida.framework.shared.LidaElementFactory;
import edu.memphis.ccrg.lida.framework.shared.Node;
import edu.memphis.ccrg.lida.framework.shared.NodeImpl;
import edu.memphis.ccrg.lida.framework.shared.activation.Learnable;
import edu.memphis.ccrg.lida.framework.shared.activation.LearnableImpl;
import edu.memphis.ccrg.lida.framework.strategies.DecayStrategy;
import edu.memphis.ccrg.lida.framework.strategies.ExciteStrategy;
import edu.memphis.ccrg.lida.framework.strategies.TotalActivationStrategy;
import edu.memphis.ccrg.lida.framework.tasks.LidaTaskManager;

/**
 * Default implementation of PamNode.  Basically a {@link Learnable} {@link Node}.
 * Overrides hashCode and equals.  Has a private {@link LearnableImpl} to help 
 * implement all {@link Learnable} methods.
 * @author Ryan J. McCall
 *
 */
public class PamNodeImpl extends NodeImpl implements PamNode{
	
	private static final Logger logger = Logger.getLogger(PamNodeImpl.class.getCanonicalName());
	
	
	/*
	 * Private Learnable object used for all learnable methods
	 */
	private LearnableImpl learnable;
	
	public PamNodeImpl() {
		super();
		groundingPamNode = this;
		learnable = new LearnableImpl();
		initLearnable();
	}
	public PamNodeImpl(PamNodeImpl pamNode) {
		super(pamNode);
		groundingPamNode = this;
		this.learnable=new LearnableImpl(pamNode.learnable);
	}
	
	/**
	 * 
	 */
	private void initLearnable() {
		learnable.setBaseLevelActivation(DEFAULT_BASE_LEVEL_ACTIVATION);
		LidaElementFactory factory = LidaElementFactory.getInstance();
		learnable.setDecayStrategy(factory.getDecayStrategy("pamDefaultDecay"));
		learnable.setExciteStrategy(factory.getExciteStrategy("pamDefaultExcite"));
		learnable.setLearnableRemovalThreshold(Learnable.DEFAULT_REMOVAL_THRESHOLD);
	}

	/** 
	 * This init method sets the following values from the parameters in the 
	 * Factorydata.xml file:
	 * 
	 * baseLevelDecayStrategy: a String with the factory name of the DecayStrategy
	 * baseLevelExciteStrategy: a String with the factory name of the ExciteStrategy
	 * baseLevelRemovalThreshold: a double value. -1.0 for no removal
	 * baseLevelActivation: a double value between 0.0 and 1.0;
	 * 
	 * @see edu.memphis.ccrg.lida.framework.shared.NodeImpl#init()
	 */
	@Override
	public void init(){
		LidaElementFactory factory = LidaElementFactory.getInstance();
		
	    String strategyName = (String)getParam("baseLevelDecayStrategy","pamDefaultDecay");
		learnable.setDecayStrategy(factory.getDecayStrategy(strategyName));
		
	    strategyName = (String)getParam("baseLevelExciteStrategy","pamDefaultExcite");
		learnable.setExciteStrategy(factory.getExciteStrategy(strategyName));
		
		double threshold = (Double)getParam("baseLevelRemovalThreshold",Learnable.DEFAULT_REMOVAL_THRESHOLD);
		learnable.setLearnableRemovalThreshold(threshold);

		double activation = (Double)getParam("baseLevelActivation",Learnable.DEFAULT_BASE_LEVEL_ACTIVATION);
		learnable.setBaseLevelActivation(activation);	
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof PamNodeImpl))
			return false;
		return getId() == ((PamNodeImpl) obj).getId();
	}
	@Override
	public int hashCode() { 
	    return getId();
	}

	@Override
	public String toString() {
		return getLabel() + " " + getId();
	}
	
	//LEARNABLE METHODS

	@Override
	public double getActivation() {
		return learnable.getActivation();
	}

	@Override
	public void setActivation(double activation) {
		learnable.setActivation(activation);
	}

	@Override
	public double getTotalActivation() {
		return learnable.getTotalActivation();
	}

	@Override
	public void excite(double amount) {
		learnable.excite(amount);
	}

	@Override
	public void setExciteStrategy(ExciteStrategy strategy) {
		learnable.setExciteStrategy(strategy);
	}

	@Override
	public ExciteStrategy getExciteStrategy() {
		return learnable.getExciteStrategy();
	}

	@Override
	public void decay(long ticks) {
		learnable.decay(ticks);
	}

	@Override
	public void setDecayStrategy(DecayStrategy strategy) {
		learnable.setDecayStrategy(strategy);
	}

	@Override
	public DecayStrategy getDecayStrategy() {
		return learnable.getDecayStrategy();
	}

	@Override
	public void setActivatibleRemovalThreshold(double threshold) {
		learnable.setActivatibleRemovalThreshold(threshold);
	}

	@Override
	public double getActivatibleRemovalThreshold() {
		return learnable.getActivatibleRemovalThreshold();
	}

	@Override
	public boolean isRemovable() {
		return learnable.isRemovable();
	}

	@Override
	public double getBaseLevelActivation() {
		return learnable.getBaseLevelActivation();
	}

	@Override
	public void setBaseLevelActivation(double amount) {
		learnable.setBaseLevelActivation(amount);
	}

	@Override
	public void reinforceBaseLevelActivation(double amount) {
		learnable.reinforceBaseLevelActivation(amount);
	}

	@Override
	public void setBaseLevelExciteStrategy(ExciteStrategy strategy) {
		learnable.setBaseLevelExciteStrategy(strategy);
	}

	@Override
	public ExciteStrategy getBaseLevelExciteStrategy() {
		return learnable.getBaseLevelExciteStrategy();
	}

	@Override
	public void decayBaseLevelActivation(long ticks) {
		learnable.decayBaseLevelActivation(ticks);
	}

	@Override
	public void setBaseLevelDecayStrategy(DecayStrategy strategy) {
		learnable.setBaseLevelDecayStrategy(strategy);
	}

	@Override
	public DecayStrategy getBaseLevelDecayStrategy() {
		return learnable.getBaseLevelDecayStrategy();
	}

	@Override
	public void setLearnableRemovalThreshold(double threshold) {
		learnable.setLearnableRemovalThreshold(threshold);
	}

	@Override
	public double getLearnableRemovalThreshold() {
		return learnable.getLearnableRemovalThreshold();
	}

	@Override
	public TotalActivationStrategy getTotalActivationStrategy() {
		return learnable.getTotalActivationStrategy();
	}

	@Override
	public void setTotalActivationStrategy(TotalActivationStrategy strategy) {
		learnable.setTotalActivationStrategy(strategy);
	}	

	@Override
	public PamNode copy() {
		return new PamNodeImpl(this);
	}	
	
	@Override
	public void updateNodeValues(Node n) {
		if(n instanceof PamNodeImpl){
			PamNodeImpl pn = (PamNodeImpl) n;
			learnable = new LearnableImpl(pn.learnable);
		}else if(n != null){
			logger.log(Level.WARNING, "Cannot update type-specified values of this object.  Required: " + 
					PamNodeImpl.class.getCanonicalName() + " but received: " + 
					n, LidaTaskManager.getCurrentTick());
		}
	}
}