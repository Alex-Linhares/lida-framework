package edu.memphis.ccrg.lida.globalworkspace;

import edu.memphis.ccrg.lida.shared.NodeStructure;
import edu.memphis.ccrg.lida.shared.NodeStructureImpl;
import edu.memphis.ccrg.lida.wumpusWorld.d_perception.RyanNodeStructure;

public class CoalitionImpl implements Coalition{
	
	private BroadcastContent struct;
	
	public CoalitionImpl() {
		struct = new NodeStructureImpl();
	}
	
	//coalition activation: average of all nodes in coalition (0.0 - 1.0) times activation of attention codelet  (0.0 - 1.0)
	public CoalitionImpl(BroadcastContent content){
		struct = content;
	}

	public void decay() {
		// TODO Auto-generated method stub
		
	}

	public double getActivation() {
		// TODO Auto-generated method stub
		return 0;
	}

	public BroadcastContent getContent() {
		return struct;
	}

}
