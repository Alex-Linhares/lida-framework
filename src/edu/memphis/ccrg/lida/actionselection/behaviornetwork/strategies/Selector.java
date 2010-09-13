package edu.memphis.ccrg.lida.actionselection.behaviornetwork.strategies;

import java.util.Collection;

import edu.memphis.ccrg.lida.actionselection.behaviornetwork.main.Behavior;

/**
 * A strategy for choosing which behavior to execute
 * @author ryanjmccall
 *
 */
public interface Selector {

	/**
	 * Select a single behavior as the current winner
	 * @return
	 */
	public abstract Behavior selectBehavior(Collection<Behavior> behaviors, double candidateThreshold);

}