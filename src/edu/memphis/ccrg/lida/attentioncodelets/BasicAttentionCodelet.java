/*******************************************************************************
 * Copyright (c) 2009, 2010 The University of Memphis.  All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the LIDA Software Framework Non-Commercial License v1.0 
 * which accompanies this distribution, and is available at
 * http://ccrg.cs.memphis.edu/assets/papers/2010/LIDA-framework-non-commercial-v1.0.pdf
 *******************************************************************************/

package edu.memphis.ccrg.lida.attentioncodelets;

import java.util.logging.Level;
import java.util.logging.Logger;

import edu.memphis.ccrg.lida.framework.shared.Linkable;
import edu.memphis.ccrg.lida.framework.shared.NodeStructure;
import edu.memphis.ccrg.lida.framework.tasks.TaskManager;
import edu.memphis.ccrg.lida.workspace.workspaceBuffer.WorkspaceBuffer;

/**
 * Basic implementation of {@link AttentionCodelet}
 * WARNING:	Renaming this class requires renaming values in
 * 	  configs/factoriesData.xml
 */
public class BasicAttentionCodelet extends AttentionCodeletImpl {
	
	private static final Logger logger = Logger.getLogger(BasicAttentionCodelet.class.getCanonicalName());
	
	public BasicAttentionCodelet(){
		super();
	}
	
	/**
	 * Returns true if specified WorkspaceBuffer contains this codelet's sought
	 * content.
	 * 
	 * @param buffer
	 *            the WorkspaceBuffer to be checked for content
	 * @return true, if successful
	 */
	@Override
	public boolean hasSoughtContent(WorkspaceBuffer buffer) {
		NodeStructure model = (NodeStructure) buffer.getBufferContent(null);
		
		for(Linkable ln: soughtContent.getLinkables()){
			if(!model.containsLinkable(ln)){
				return false;
			}
		}

		logger.log(Level.FINEST, "Attn codelet " + this.toString() + " found sought content", TaskManager.getCurrentTick());
		return true;
	}
	
	/**
	 * Returns sought content and related content from specified
	 * WorkspaceBuffer.
	 * 
	 * @param buffer
	 *            the buffer
	 * @return the workspace content
	 */
	@Override
	public NodeStructure retrieveWorkspaceContent(WorkspaceBuffer buffer) {
		//TODO Naive implementation. Should not copy entire buffer.
		return ((NodeStructure) buffer.getBufferContent(null)).copy();
	}

}