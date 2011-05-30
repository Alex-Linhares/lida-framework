/*******************************************************************************
 * Copyright (c) 2009, 2011 The University of Memphis.  All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the LIDA Software Framework Non-Commercial License v1.0 
 * which accompanies this distribution, and is available at
 * http://ccrg.cs.memphis.edu/assets/papers/2010/LIDA-framework-non-commercial-v1.0.pdf
 *******************************************************************************/
package edu.memphis.ccrg.lida.actionselection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.memphis.ccrg.lida.framework.FrameworkModuleImpl;
import edu.memphis.ccrg.lida.framework.ModuleListener;
import edu.memphis.ccrg.lida.framework.tasks.FrameworkTaskImpl;
import edu.memphis.ccrg.lida.framework.tasks.TaskManager;
import edu.memphis.ccrg.lida.globalworkspace.BroadcastContent;
import edu.memphis.ccrg.lida.proceduralmemory.ProceduralMemoryListener;

/**
 * Rudimentary action selection that selects the behavior with the highest activation.
 * 
 * @author Ryan J McCall
 * 
 */
public class BasicActionSelection extends FrameworkModuleImpl implements
		ActionSelection, ProceduralMemoryListener {

	private static final Logger logger = Logger
			.getLogger(BasicActionSelection.class.getCanonicalName());

//	private List<FrameworkGuiEventListener> guis = new ArrayList<FrameworkGuiEventListener>();
	private List<ActionSelectionListener> listeners = new ArrayList<ActionSelectionListener>();
	private Queue<Behavior> behaviors = new LinkedList<Behavior>();
	
	/**
	 * Default constructor
	 */
	public BasicActionSelection() {
	}

	@Override
	public void init() {
		int ticksPerRun = (Integer) getParam(
				"actionSelection.backgroundTaskTicksPerRun", 10);
		taskSpawner.addTask(new BackgroundTask(ticksPerRun));
	}

	// TODO move to xml as initial task
	private class BackgroundTask extends FrameworkTaskImpl {
		public BackgroundTask(int ticksPerRun) {
			super(ticksPerRun);
		}
		@Override
		protected void runThisFrameworkTask() {
			selectAction();
		}
		@Override
		public String toString() {
			return BasicActionSelection.class.getSimpleName()
					+ " background task";
		}
	}

	@Override
	public void addListener(ModuleListener listener) {
		if (listener instanceof ActionSelectionListener) {
			addActionSelectionListener((ActionSelectionListener) listener);
		}
	}
	@Override
	public void addActionSelectionListener(ActionSelectionListener listener) {
		listeners.add(listener);
	}

	@Override
	public void receiveBehavior(Behavior b) {
		synchronized(this){
			behaviors.add(b);
		}
		logger.log(Level.FINE, "Behavior added {1}",
				   new Object[]{TaskManager.getCurrentTick(), b});
	}

	@Override
	public void selectAction() {
		Behavior behavior = chooseBehavior();
		if (behavior != null) {
			AgentAction action = behavior.getAction();
			logger.log(Level.FINE, "Action Selected: {1}",
					   new Object[]{TaskManager.getCurrentTick(),action});
			for (ActionSelectionListener bl : listeners) {
				bl.receiveAction(action);
			}
		}
	}

	private Behavior chooseBehavior() {
		Behavior selected = behaviors.peek();
		for (Behavior b : behaviors) {
			if (b.getActivation() > selected.getActivation()) {
				selected = b;
			}
		}
		synchronized(this){
			behaviors.clear();
		}
		return selected;
	}
	
	@Override
	public Object getModuleContent(Object... params) {
		if(params[0].equals("behaviors")){
			return Collections.unmodifiableCollection(behaviors);
		}
		return null;
	}

	@Override
	public void addPreafferenceListener(PreafferenceListener listener) {
	}

	@Override
	public void learn(BroadcastContent content) {
	}

	@Override
	public void receiveBroadcast(BroadcastContent bc) {
	}

	@Override
	public void decayModule(long ticks) {
		// TODO decay behaviors
	}

}