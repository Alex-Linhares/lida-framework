package edu.memphis.ccrg.lida.workspace.main;

import edu.memphis.ccrg.lida._perception.interfaces.PAMListener;
import edu.memphis.ccrg.lida.actionSelection.ActionSelectionListener;
import edu.memphis.ccrg.lida.declarativeMemory.DeclarativeMemoryListener;
import edu.memphis.ccrg.lida.globalworkspace.BroadcastListener;
import edu.memphis.ccrg.lida.transientEpisodicMemory.TransientEpisodicMemoryListener;

public interface Workspace extends PAMListener, 
								   TransientEpisodicMemoryListener, 
								   DeclarativeMemoryListener,
								   BroadcastListener, 
								   ActionSelectionListener{
	
}
