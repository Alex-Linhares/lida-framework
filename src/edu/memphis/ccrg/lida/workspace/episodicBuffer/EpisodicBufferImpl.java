package edu.memphis.ccrg.lida.workspace.episodicBuffer;

import java.util.ArrayList;
import java.util.List;

import edu.memphis.ccrg.lida.declarativeMemory.DeclarativeMemoryContent;
import edu.memphis.ccrg.lida.transientEpisodicMemory.TransientEpisodicMemoryContent;
import edu.memphis.ccrg.lida.workspace.main.WorkspaceContent;
import edu.memphis.ccrg.lida.workspace.structureBuildingCodelets.CodeletAccessible;
import edu.memphis.ccrg.lida.workspace.structureBuildingCodelets.CodeletObjective;

public class EpisodicBufferImpl implements EpisodicBuffer, CodeletAccessible{

    private List<EpisodicBufferListener> listeners;
	private TransientEpisodicMemoryContent temAssociation;
	private DeclarativeMemoryContent dmAssociation;
    
	public EpisodicBufferImpl(){
		listeners = new ArrayList<EpisodicBufferListener>();
	}

	public void addEBufferListener(EpisodicBufferListener listener) {
		listeners.add(listener);		
	}

	public WorkspaceContent getCodeletsObjective(CodeletObjective objective) {
		// TODO Auto-generated method stub
		return null;
	}

	public synchronized void receiveTEMContent(
			TransientEpisodicMemoryContent association) {
		temAssociation = association;		
	}

	public synchronized void receivenDMContent(DeclarativeMemoryContent association) {
		dmAssociation = association;		
	}

}
