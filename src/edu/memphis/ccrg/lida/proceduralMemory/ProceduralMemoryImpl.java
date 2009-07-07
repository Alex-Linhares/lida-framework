package edu.memphis.ccrg.lida.proceduralMemory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import edu.memphis.ccrg.lida.framework.BroadcastLearner;
import edu.memphis.ccrg.lida.framework.FrameworkGui;
import edu.memphis.ccrg.lida.framework.FrameworkGuiProvider;
import edu.memphis.ccrg.lida.globalworkspace.BroadcastContent;
import edu.memphis.ccrg.lida.shared.Node;
import edu.memphis.ccrg.lida.shared.NodeStructure;
import edu.memphis.ccrg.lida.shared.NodeStructureImpl;

public class ProceduralMemoryImpl implements ProceduralMemory, FrameworkGuiProvider, BroadcastLearner{
	
	private NodeStructure broadcastContent = new NodeStructureImpl();
	private List<ProceduralMemoryListener> listeners = new ArrayList<ProceduralMemoryListener>();
	private List<Object> guiContent = new ArrayList<Object>();

	public void addProceduralMemoryListener(ProceduralMemoryListener listener) {
		listeners.add(listener);		
	}

	public void addFrameworkGui(FrameworkGui listener) {
		// TODO Auto-generated method stub
		
	}

	public void receiveBroadcast(BroadcastContent bc) {
		broadcastContent = (NodeStructure) bc;
	}//method

	public void activateSchemes() {
		// TODO Auto-generated method stub
		
	}

	public void sendSchemes() {
		// TODO Auto-generated method stub
		
	}
	
	public void learn() {
		Collection<Node> nodes = broadcastContent.getNodes();
		for(Node n: nodes){
			//TODO:
			n.getId();
		}
	}
	
	public void sendGuiContent() {
		// TODO Auto-generated method stub
		
	}

}//class
