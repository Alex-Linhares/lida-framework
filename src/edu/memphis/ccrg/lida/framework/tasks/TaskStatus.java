/*******************************************************************************
 * Copyright (c) 2009, 2011 The University of Memphis.  All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the LIDA Software Framework Non-Commercial License v1.0 
 * which accompanies this distribution, and is available at
 * http://ccrg.cs.memphis.edu/assets/papers/2010/LIDA-framework-non-commercial-v1.0.pdf
 *******************************************************************************/
package edu.memphis.ccrg.lida.framework.tasks;

/**
 * Enumeration of the possible statuses of FrameworkTasks
 * @author Javier Snaider
 *
 */
public enum TaskStatus {
	
	/**
	 * FrameworkTask status value:
	 * Task is running
	 */
	RUNNING,
	
	/**
	 * FrameworkTask status value:
	 * Task is finished and cannot be restarted
	 */
	CANCELED,
	
	/**
	 * FrameworkTask status value:
	 * Task is finished and has a result
	 */
	FINISHED,
	
	/**
	 * FrameworkTask status value:
	 * Task is not running and not finished.
	 */
	WAITING,

	/**
	 * FrameworkTask status value:
	 * Task has finished and has results to process
	 */
	FINISHED_WITH_RESULTS
}