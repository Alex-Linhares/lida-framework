/**
 * 
 */
package edu.memphis.ccrg.lida.framework.shared;

import edu.memphis.ccrg.lida.framework.strategies.DecayStrategy;
import edu.memphis.ccrg.lida.framework.strategies.ExciteStrategy;

/**
 * An Activatible that has a base level activation. It is used mostly for learning.
 * The classes that implement this interface have a decay and reinforce base level activation.
 * If the base level activation reaches zero, the element should be deleted (decay away)
 * 
 * @author Javier Snaider
 *
 */
public interface LearnableActivatible extends Activatible{
	/**
	 * Returns base level activation. 
	 * @return
	 */
	public abstract double getBaseLevelActivation();
	
	/**
	 * Set base level activation
	 * @param amount
	 */
	public abstract void setBaseLevelActivation(double amount);
	/**
	 * The Base Level activation of this node is increased 
	 * using the excitation value as a parameter for the ExciteStrategy.
	 * This is primarily used for learning.
	 * 
	 * @param   amount the value to be used to increase the Base Level activation of
	 *          this node
	 */
    public void reinforceBaseLevelActivation(double amount); 
    /**
     * 
     * @param strategy the Excite strategy for the current activation.
     */
	public void setBaseLevelExciteStrategy(ExciteStrategy strategy);
	/**
	 * 
	 * @return the excite strategy
	 */
	public ExciteStrategy getBaseLevelExciteStrategy();
	
	/**
	 * decay the Base Level activation using the decay strategy. The decay depends on 
	 * the time since the last decaying. It is indicated by the parameter ticks.
	 * 
	 * @param ticks the number of ticks to decay
	 */
	public void decayBaseLevelActivation(long ticks);	

    /**
     * 
     * @param strategy the decay strategy for the Base Level activation.
     */
	public void setBaseLevelDecayStrategy(DecayStrategy strategy);
	/**
	 * 
	 * @return the decay strategy for the Base Level activation.
	 */
	public DecayStrategy getBaseLevelDecayStrategy();
}