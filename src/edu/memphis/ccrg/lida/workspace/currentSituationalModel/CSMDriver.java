package edu.memphis.ccrg.lida.workspace.currentSituationalModel;

import java.util.Set;

import edu.memphis.ccrg.lida.globalworkspace.BroadcastContentImpl;
import edu.memphis.ccrg.lida.globalworkspace.CoalitionImpl;
import edu.memphis.ccrg.lida.globalworkspace.GlobalWorkspaceImpl;
import edu.memphis.ccrg.lida.proceduralMemory.ProceduralMemory;
import edu.memphis.ccrg.lida.proceduralMemory.ProceduralMemoryDriver;
import edu.memphis.ccrg.lida.shared.Link;
import edu.memphis.ccrg.lida.shared.Node;
import edu.memphis.ccrg.lida.shared.NodeStructure;
import edu.memphis.ccrg.lida.util.FrameworkTimer;
import edu.memphis.ccrg.lida.util.Misc;
import edu.memphis.ccrg.lida.util.Stoppable;

public class CSMDriver implements Runnable, Stoppable{
	
	private boolean keepRunning = true;
	private CurrentSituationalModelImpl csm;
	private long threadID;
	private FrameworkTimer timer;
	private CSMListener guiListener;
	
	public CSMDriver(FrameworkTimer t, CurrentSituationalModelImpl csm, CSMListener gui){
		timer = t;
		this.csm = csm;
		guiListener = gui;
	}

	public void run(){
		int counter = 0;		
		long startTime = System.currentTimeMillis();		
		while(keepRunning){
			try{Thread.sleep(25 + timer.getSleepTime());}catch(Exception e){}
			timer.checkForStartPause();
		
			NodeStructure struct = csm.getContent();
			Set<Node> nodes = struct.getNodes();
			Set<Link> links = struct.getLinks();
			
			System.out.println(nodes.size() + " links " + links.size());
			
			for(Node n: nodes)
				System.out.println(n.getLabel() + " ");
			
				
			for(Link l: links)
				System.out.println(l.toString() + " ");
			
			
			//CoalitionImpl coalition = new CoalitionImpl(content);
			//BroadcastContentImpl content = new BroadcastContentImpl(struct);
			
			counter++;			
		}//while keepRunning
		long finishTime = System.currentTimeMillis();				
		System.out.println("\nCSM: Ave. cycle time: " + 
							Misc.rnd((finishTime - startTime)/(double)counter));
		System.out.println("CSM: Num. cycles: " + counter);		
	}//public void run()

	public void stopRunning(){
		try{Thread.sleep(20);}catch(Exception e){}
		keepRunning = false;		
	}//public void stopRunning()
	
	public void setThreadID(long id){
		threadID = id;
	}
	
	public long getThreadID() {
		return threadID;
	}
	
}
