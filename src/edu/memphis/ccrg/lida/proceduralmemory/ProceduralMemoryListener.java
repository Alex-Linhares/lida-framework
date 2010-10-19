/*******************************************************************************
 * Copyright (c) 2009, 2010 The University of Memphis.  All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the LIDA Software Framework Non-Commercial License v1.0 
 * which accompanies this distribution, and is available at
 * http://ccrg.cs.memphis.edu/assets/papers/2010/LIDA-framework-non-commercial-v1.0.pdf
 *******************************************************************************/

package edu.memphis.ccrg.lida.proceduralmemory;

import edu.memphis.ccrg.lida.actionselection.behaviornetwork.main.Behavior;
import edu.memphis.ccrg.lida.framework.ModuleListener;

/**
 * A procedural memory listener receives instantiated schemes which are behaviors
 * @author Ryan McCall
 */
public interface ProceduralMemoryListener extends ModuleListener{

    /**
     * @param behavior (instantiated scheme)
     */
    public abstract void receiveBehavior(Behavior behavior);

    /**
     * 
     * @param stream - a stream, a partial order, of behaviors
     */
    public abstract void receiveStream(Stream stream);
   
}
