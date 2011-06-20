/*******************************************************************************
 * Copyright (c) 2009, 2011 The University of Memphis.  All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the LIDA Software Framework Non-Commercial License v1.0 
 * which accompanies this distribution, and is available at
 * http://ccrg.cs.memphis.edu/assets/papers/2010/LIDA-framework-non-commercial-v1.0.pdf
 *******************************************************************************/
/**
 * 
 */
package edu.memphis.ccrg.lida.framework.shared.activation;

import java.util.logging.Level;
import java.util.logging.Logger;

import edu.memphis.ccrg.lida.framework.shared.ElementFactory;
import edu.memphis.ccrg.lida.framework.strategies.DecayStrategy;
import edu.memphis.ccrg.lida.framework.strategies.ExciteStrategy;
import edu.memphis.ccrg.lida.framework.strategies.TotalActivationStrategy;
import edu.memphis.ccrg.lida.framework.tasks.TaskManager;

/**
 * 
 * Default implementation of Learnable.
 * @author Javier Snaider, Ryan J. McCall
 *
 */
public class LearnableImpl extends ActivatibleImpl implements Learnable {

	private static final Logger logger = Logger.getLogger(LearnableImpl.class.getCanonicalName());
	private static final ElementFactory factory = ElementFactory.getInstance();
	
	private double baseLevelActivation;
	private double learnableRemovalThreshold;
	private ExciteStrategy baseLevelExciteStrategy;
	private DecayStrategy baseLevelDecayStrategy;
	private TotalActivationStrategy totalActivationStrategy;

	/**
	 * Constructs a new instance with default values.
	 */
	public LearnableImpl() {
		super();
		baseLevelActivation = DEFAULT_BASE_LEVEL_ACTIVATION;
		learnableRemovalThreshold = DEFAULT_LEARNABLE_REMOVAL_THRESHOLD;
		baseLevelDecayStrategy = factory.getDefaultDecayStrategy();
		baseLevelExciteStrategy = factory.getDefaultExciteStrategy();
		totalActivationStrategy = (TotalActivationStrategy) factory.getStrategy("DefaultTotalActivation");
	}
	
	/**
	 * Copy constructor
	 * @param l {@link LearnableImpl}
	 */
	public LearnableImpl(LearnableImpl l) {
		this(l.getActivation(), l.getActivatibleRemovalThreshold(),  l.getBaseLevelActivation(), l.getLearnableRemovalThreshold(),
			l.getExciteStrategy(), l.getDecayStrategy(),l.getBaseLevelExciteStrategy(), l.getBaseLevelDecayStrategy(), l.getTotalActivationStrategy());
	}
	
	/**
	 * Constructs a new instance with specified attributes
	 * @param activation current activation
	 * @param activatibleRemovalThreshold activation threshold needed for this instance to remain active
	 * @param baseLevelActivation base-level activation for learning
	 * @param learnableRemovalThreshold base-level activation needed for this instance to remain active
	 * @param exciteStrategy {@link ExciteStrategy} for exciting {@link ActivatibleImpl} activation.
	 * @param decayStrategy {@link DecayStrategy} for decaying {@link ActivatibleImpl} activation.
	 * @param baseLevelExciteStrategy {@link ExciteStrategy} for reinforcing {@link LearnableImpl} base-level activation.
	 * @param baseLevelDecayStrategy {@link DecayStrategy} for decaying {@link LearnableImpl} base-level activation.
	 * @param taStrategy {@link TotalActivationStrategy} how this instance will calculate its total activation.
	 */
	public LearnableImpl(double activation, double activatibleRemovalThreshold, double baseLevelActivation, double learnableRemovalThreshold,
			ExciteStrategy exciteStrategy, DecayStrategy decayStrategy, ExciteStrategy baseLevelExciteStrategy, DecayStrategy baseLevelDecayStrategy, TotalActivationStrategy taStrategy) {
		super(activation, activatibleRemovalThreshold, exciteStrategy, decayStrategy);
		
		this.baseLevelActivation = baseLevelActivation;
		this.learnableRemovalThreshold=learnableRemovalThreshold;
		this.baseLevelExciteStrategy = baseLevelExciteStrategy;
		this.baseLevelDecayStrategy = baseLevelDecayStrategy;
		this.totalActivationStrategy = taStrategy;
	}

	@Override
	public void decay(long ticks){
		decayBaseLevelActivation(ticks);
		super.decay(ticks);
	}
	
	@Override
	public boolean isRemovable() {
		return baseLevelActivation <= learnableRemovalThreshold;
	}

	@Override
	public double getTotalActivation() { 
	    return totalActivationStrategy.calculateTotalActivation(baseLevelActivation, getActivation());
	}

	@Override
	public void decayBaseLevelActivation(long ticks) {
		if (baseLevelDecayStrategy != null) {
			if(logger.isLoggable(Level.FINEST)){
				logger.log(Level.FINEST, "Before decaying {1} has base-level activation: {2}",
							new Object[]{TaskManager.getCurrentTick(),this,baseLevelActivation});
			}
			synchronized(this){
				baseLevelActivation = baseLevelDecayStrategy.decay(baseLevelActivation,ticks);
			}
			if(logger.isLoggable(Level.FINEST)){
				logger.log(Level.FINEST, "After decaying {1} has base-level activation: {2}",
							new Object[]{TaskManager.getCurrentTick(),this,baseLevelActivation});
			}
		}		
	}
	
	@Override
	public void reinforceBaseLevelActivation(double amount) {
		if (baseLevelExciteStrategy != null) {
			if(logger.isLoggable(Level.FINEST)){
				logger.log(Level.FINEST, "Before reinforcement {1} has base-level activation: {2}",
							new Object[]{TaskManager.getCurrentTick(),this,baseLevelActivation});
			}
			synchronized(this){
				baseLevelActivation = baseLevelExciteStrategy.excite(baseLevelActivation, amount);
			}
			if(logger.isLoggable(Level.FINEST)){
				logger.log(Level.FINEST, "After reinforcement {1} has base-level activation: {2}",
							new Object[]{TaskManager.getCurrentTick(),this,baseLevelActivation});
			}
		}		
	}

	@Override
	public ExciteStrategy getBaseLevelExciteStrategy() {
		return baseLevelExciteStrategy;
	}

	@Override
	public void setBaseLevelExciteStrategy(ExciteStrategy baseLevelExciteStrategy) {
		this.baseLevelExciteStrategy = baseLevelExciteStrategy;
	}

	@Override
	public DecayStrategy getBaseLevelDecayStrategy() {
		return baseLevelDecayStrategy;
	}

	@Override
	public void setBaseLevelDecayStrategy(DecayStrategy baseLevelDecayStrategy) {
		this.baseLevelDecayStrategy = baseLevelDecayStrategy;
	}

	@Override
	public void setBaseLevelActivation(double activation) {
		this.baseLevelActivation=activation;		
	}
	
	@Override
	public double getBaseLevelActivation() {
		return baseLevelActivation;
	}

	@Override
	public double getLearnableRemovalThreshold() {
		return learnableRemovalThreshold;
	}

	@Override
	public void setBaseLevelRemovalThreshold(double threshold) {
		this.learnableRemovalThreshold = threshold;
	}
	
	@Override
	public TotalActivationStrategy getTotalActivationStrategy() {
		return totalActivationStrategy;
	}

	@Override
	public void setTotalActivationStrategy(
			TotalActivationStrategy totalActivationStrategy) {
		this.totalActivationStrategy = totalActivationStrategy;
	}
	
}
