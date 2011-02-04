/*******************************************************************************
 * Copyright (c) 2009, 2010 The University of Memphis.  All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the LIDA Software Framework Non-Commercial License v1.0 
 * which accompanies this distribution, and is available at
 * http://ccrg.cs.memphis.edu/assets/papers/2010/LIDA-framework-non-commercial-v1.0.pdf
 *******************************************************************************/
/**
 * Behavior.java
 *
 * @author Ryan J. McCall 
 */
package edu.memphis.ccrg.lida.actionselection.behaviornetwork;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.memphis.ccrg.lida.framework.shared.Node;
import edu.memphis.ccrg.lida.framework.shared.NodeStructure;
import edu.memphis.ccrg.lida.framework.shared.NodeStructureImpl;
import edu.memphis.ccrg.lida.framework.shared.activation.ActivatibleImpl;
import edu.memphis.ccrg.lida.framework.tasks.LidaTaskManager;
import edu.memphis.ccrg.lida.proceduralmemory.Scheme;
import edu.memphis.ccrg.lida.proceduralmemory.Stream;

/**
 * @author ryanjmccall
 *
 */
public class BehaviorImpl extends ActivatibleImpl implements Behavior {

	private static final Logger logger = Logger
			.getLogger(BehaviorImpl.class.getCanonicalName());

	/**
	 * Label for description
	 */
	private String label = "blank behavior";

	/**
	 * Context for this behavior
	 */
	private NodeStructure context;

	/**
	 * Set of nodes that this scheme adds
	 */
	private NodeStructure addingList;

	/**
     * 
     */
	private NodeStructure deletingList;

	/**
	 * Id of the action(s) in sensory-motor to be taken if this behavior
	 * executes
	 */
	private long actionId;

	/**
	 * unique identifier
	 */
	private long id;

	/**
	 * optimization for checking if context is all satisfied
	 */
	private AtomicInteger unsatisfiedContextConditionCount = new AtomicInteger(0);

	/**
	 * The streams that contains this behavior
	 */
	private Set<Stream> containingStreams;

	private double contextSatisfactionThreshold = DEFAULT_CS_THRESHOLD;

	private String contextNodeType;

	private Scheme generatingScheme;

	private static final double DEFAULT_CS_THRESHOLD = 0.5;

	private static long idCounter = 0;
	
	public BehaviorImpl(){
		context = new NodeStructureImpl();
		addingList = new NodeStructureImpl();
		deletingList = new NodeStructureImpl();
	}
	
	public BehaviorImpl(long id, long actionId) {
		this();
		this.id = id;
		this.actionId = actionId;
	}
	
	public BehaviorImpl(long actionId){
		this(idCounter++, actionId);
	}

	// Precondition methods
	@Override
	public void deactivateAllContextConditions() {
		for (Node s : context.getNodes())
			s.setActivation(0.0);
		unsatisfiedContextConditionCount.set(getContextSize());
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setActionId(long actionId) {
		this.actionId = actionId;
	}

	@Override
	public boolean isContextConditionSatisfied(Node prop) {
		if (context.containsNode(prop))
			return context.getNode(prop.getId()).getActivation() > contextSatisfactionThreshold;
		return false;
	}

	@Override
	public boolean isAllContextConditionsSatisfied() {
		return (unsatisfiedContextConditionCount.get() == 0);
	}

	/**
	 * Update the activation of the context condition from the broadcast
	 */
	@Override
	public void updateContextCondition(Node broadcastCondition) {
		Node existingCondition = context.getNode(broadcastCondition.getId());
		if (existingCondition != null) { //Check if this behavior has the condition
			
			double newActivation = broadcastCondition.getActivation();
			existingCondition.setActivation(newActivation);
			if (newActivation < contextSatisfactionThreshold) {
				unsatisfiedContextConditionCount.incrementAndGet();
			}else{
				unsatisfiedContextConditionCount.decrementAndGet();
			}
		}else{
			logger.log(Level.WARNING, "BN asked to update a context condition " + 
						broadcastCondition.getLabel() + " but it wasn't in the context of behavior "
						+ label, LidaTaskManager.getCurrentTick());
		}
	}
	
	@Override
	public void updateAddingCondition(Node broadcastNode) {
		auxUpdateResultCondition(broadcastNode, addingList);		
	}

	@Override
	public void updateDeletingCondition(Node broadcastNode) {
		auxUpdateResultCondition(broadcastNode, deletingList);
	}
	
	private void auxUpdateResultCondition(Node condition, NodeStructure resultList){
		Node existingCondition = resultList.getNode(condition.getId());
		if (existingCondition != null) { //Check if this behavior has the condition
			double newActivation = condition.getActivation();
			existingCondition.setActivation(newActivation);
		}else{
			logger.log(Level.WARNING, "BN asked to update a result condition " + 
						condition.getLabel() + " but it wasn't in the result list of behavior "
						+ label, LidaTaskManager.getCurrentTick());
		}
	}

	@Override
	public void deactiveContextCondition(Node condition) {
		if ((condition = context.getNode(condition.getId())) != null) {
			condition.setActivation(0.0);
			unsatisfiedContextConditionCount.incrementAndGet();
		}
	}

	// start add methods
	@Override
	public boolean addContextCondition(Node condition) {
		logger.log(Level.FINEST, "Adding context condition " +
								 condition.getLabel() + " to " + label);
		if(condition.getActivation() < this.contextSatisfactionThreshold)
			unsatisfiedContextConditionCount.incrementAndGet();
		
		return (context.addNode(condition) != null);
	}

	@Override
	public boolean addToAddingList(Node addResult) {
		logger.log(Level.FINEST, "Adding add result " +
				 addResult.getLabel() + " to " + label);
		return addingList.addNode(addResult) != null;
	}

	@Override
	public boolean addToDeletingList(Node deleteResult) {
		logger.log(Level.FINEST, "Adding delete result " +
				 deleteResult.getLabel() + " to " + label);
		return deletingList.addNode(deleteResult) != null;
	}

	// Get methods
	@Override
	public Collection<Node> getContextConditions() {
		return context.getNodes();
	}

	@Override
	public NodeStructure getAddingList() {
		return addingList;
	}

	@Override
	public NodeStructure getDeletingList() {
		return deletingList;
	}

	@Override
	public int getContextSize() {
		return context.getNodeCount();
	}

	@Override
	public double getAddingListCount() {
		return addingList.getNodeCount();
	}

	@Override
	public double getDeletingListCount() {
		return deletingList.getNodeCount();
	}

	@Override
	public long getActionId() {
		return actionId;
	}

	@Override
	public long getId() {
		return id;
	}

	@Override
	public String getLabel() {
		return label;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Behavior))
			return false;

		Behavior behavior = (Behavior) o;
		return behavior.getId() == id && behavior.getActionId() == actionId;
	}

	@Override
	public int hashCode() {
		int hash = 1;
		Long v1 = new Long(id);
		Long v2 = new Long(actionId);
		hash = hash * 31 + v2.hashCode();
		hash = hash * 31 + (v1 == null ? 0 : v1.hashCode());
		return hash;
	}

	@Override
	public void addContainingStream(Stream stream) {
		containingStreams.add(stream);
	}

	@Override
	public void removeContainingStream(Stream stream) {
		containingStreams.remove(stream);
	}

	@Override
	public Set<Stream> getContainingStreams() {
		return containingStreams;
	}

	@Override
	public void decay(long ticks) {
		super.decay(ticks);
		for (Node contextNode : context.getNodes()) {
			contextNode.decay(ticks);
			if (contextNode.getActivation() < contextSatisfactionThreshold)
				unsatisfiedContextConditionCount.incrementAndGet();
		}
	}

	@Override
	public void setContextNodeType(String nodeType) {
		this.contextNodeType = nodeType;
	}

	@Override
	public String getContextNodeType() {
		return contextNodeType;
	}

	@Override
	public boolean containsContextCondition(Node contextCondition) {
		return context.containsNode(contextCondition);
	}

	@Override
	public boolean containsAddingItem(Node addItem) {
		return addingList.containsNode(addItem);
	}

	@Override
	public boolean containsDeletingItem(Node deleteItem) {
		return deletingList.containsNode(deleteItem);
	}

	@Override
	public void setLabel(String label) {
		this.label = label;
	}

	@Override
	public Scheme getGeneratingScheme() {
		return generatingScheme;
	}

	@Override
	public void setGeneratingScheme(Scheme s) {
		generatingScheme  = s;
	}

	@Override
	public double getResultSize() {
		return addingList.getNodeCount() + deletingList.getNodeCount();
	}

	@Override
	public int getUnsatisfiedContextCount() {
		return unsatisfiedContextConditionCount.get();
	}

	

}