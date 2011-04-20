/*******************************************************************************
 * Copyright (c) 2009, 2010 The University of Memphis.  All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the LIDA Software Framework Non-Commercial License v1.0 
 * which accompanies this distribution, and is available at
 * http://ccrg.cs.memphis.edu/assets/papers/2010/LIDA-framework-non-commercial-v1.0.pdf
 *******************************************************************************/
package edu.memphis.ccrg.lida.framework.initialization;


import edu.memphis.ccrg.lida.framework.FrameworkModule;

/**
 * An Initializable module will be initialized automatically by the AgentXmlFactory
 * 
 * @see AgentXmlFactory
 * @author Ryan J. McCall, Javier Snaider
 *
 */
public interface FullyInitializable extends Initializable {
	
	/**
	 * Sets an associated FrameworkModule.
	 * @param module the module to be associated.
     * @param moduleUsage how module will be used @see ModuleUsage
	 */
	public void setAssociatedModule(FrameworkModule module, String moduleUsage);
	
}
