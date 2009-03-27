/*
 * @(#)MemoryAssociationListener.java  1.0  February 12, 2009
 *
 * Copyright 2006-2009 Cognitive Computing Research Group.
 * 365 Innovation Dr, Rm 303, Memphis, TN 38152, USA.
 * All rights reserved.
 */

package edu.memphis.ccrg.lida.episodicMemory;

/**
 *
 * @author Rodrigo Silva L.
 */
public interface MemoryAssociationListener {

    /**
     * 
     * @param association
     */
    public void receiveAssociation(MemoryAssociation association);
}