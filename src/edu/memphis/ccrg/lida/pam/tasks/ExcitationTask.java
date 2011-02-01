/*******************************************************************************
 * Copyright (c) 2009, 2010 The University of Memphis.  All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the LIDA Software Framework Non-Commercial License v1.0 
 * which accompanies this distribution, and is available at
 * http://ccrg.cs.memphis.edu/assets/papers/2010/LIDA-framework-non-commercial-v1.0.pdf
 *******************************************************************************/
package edu.memphis.ccrg.lida.pam.tasks;

import java.util.logging.Level;
import java.util.logging.Logger;

import edu.memphis.ccrg.lida.framework.tasks.LidaTaskImpl;
import edu.memphis.ccrg.lida.framework.tasks.LidaTaskManager;
import edu.memphis.ccrg.lida.framework.tasks.LidaTaskStatus;
import edu.memphis.ccrg.lida.framework.tasks.TaskSpawner;
import edu.memphis.ccrg.lida.pam.PamLink;
import edu.memphis.ccrg.lida.pam.PamLinkable;
import edu.memphis.ccrg.lida.pam.PamNode;
import edu.memphis.ccrg.lida.pam.PerceptualAssociativeMemory;

/**
 * A task that allows PAM nodes to be excited asynchronously.
 * Created in PAM method 'receiveActivationBurst' 
 * 
 * @see PerceptualAssociativeMemory#receiveActivationBurst(PamNode, double)
 * @author Ryan J McCall
 *
 */
public class ExcitationTask extends LidaTaskImpl{
	
	private static final Logger logger = Logger.getLogger(ExcitationTask.class.getCanonicalName());
	
	/**
	 * PamNode to be excited
	 */
	private PamLinkable pamLinkable;
	
	/**
	 * Amount to excite
	 */
	private double excitationAmount;
	
	/**
	 * Used to make another excitation call
	 */
	private PerceptualAssociativeMemory pam;
	
	/**
	 * For threshold task creation
	 */
	private TaskSpawner taskSpawner;

	/**
	 * Instantiates a new excitation task.
	 * 
	 * @param linkable
	 *            to be excited
	 * @param excitation
	 *            amount to excite
	 * @param ticksPerRun
	 *            the ticks per run
	 * @param pam
	 *            PerceptualAssociativeMemory module
	 * @param ts
	 *            the ts
	 */
	public ExcitationTask(PamLinkable linkable, double excitation, int ticksPerRun,
			              PerceptualAssociativeMemory pam, TaskSpawner ts){
		super();
		this.pamLinkable = linkable;
		this.excitationAmount = excitation;
		this.setTicksPerStep(ticksPerRun);
		this.pam = pam;
		this.taskSpawner = ts;
	}

	/**
	 * 
	 */
	@Override
	protected void runThisLidaTask() {
		pamLinkable.excite(excitationAmount); 
		if(pam.isOverPerceptThreshold(pamLinkable)){
			//If over threshold then spawn a new task to add the node to the percept
			AddToPerceptTask task;
			if(pamLinkable instanceof PamNode){
				PamNode pamNode = (PamNode) pamLinkable;
				task = new AddToPerceptTask(pamNode, pam);
				taskSpawner.addTask(task);
				//Tell PAM to propagate the activation of pamNode to its parents
				pam.propagateActivationToParents(pamNode);
			}else if(pamLinkable instanceof PamLink){
				task = new AddToPerceptTask((PamLink) pamLinkable, pam);
				taskSpawner.addTask(task);
			}else{
				logger.log(Level.WARNING, "pam linkable is not a PamNode or PamLink", LidaTaskManager.getCurrentTick());
			}
		}else if(pamLinkable instanceof PamNode){
			//TODO what if its an instanceof PamLink?
			pam.propagateActivationToParents((PamNode) pamLinkable);
		}
		
		this.setTaskStatus(LidaTaskStatus.FINISHED);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		return "Excitation " + getTaskId();
	}

}