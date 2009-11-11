/*
 * @(#)ProceduralMemory.java  1.0  February 14, 2009
 *
 * Copyright 2006-2009 Cognitive Computing Research Group.
 * 365 Innovation Dr, Rm 303, Memphis, TN 38152, USA.
 * All rights reserved.
 */

package edu.memphis.ccrg.lida.proceduralmemory;

import java.util.Collection;

public interface ProceduralMemory{

	/**
	 * 
	 * @param listener
	 */
    public abstract void addProceduralMemoryListener(ProceduralMemoryListener listener); 
	
	/**
	 * Using the Broadcast content, activate the relevant schemes of procedural memory 
	 */
	public abstract void activateSchemesWithBroadcast();
	
	/**
	 * Send out the active schemes to the PM listeners.
	 */
	public abstract void sendInstantiatedScheme(Scheme s);
	
	/**
	 * Add supplied schemes to this procedural memory.
	 * @param params
	 */
	public abstract void addSchemes(Collection<Scheme> schemes);
}
