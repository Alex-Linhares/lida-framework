package edu.memphis.ccrg.lida.workspace.sbCodelets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.memphis.ccrg.lida.util.FrameworkTimer;
import edu.memphis.ccrg.lida.perception.PamNodeImpl;
import edu.memphis.ccrg.lida.perception.Percept;
import edu.memphis.ccrg.lida.perception.SpatialLocation;
import edu.memphis.ccrg.lida.util.Misc;
import edu.memphis.ccrg.lida.util.Stoppable;
import edu.memphis.ccrg.lida.workspace.broadcasts.PreviousBroadcastsContentImpl;
import edu.memphis.ccrg.lida.workspace.broadcasts.PreviousBroadcastsImpl;
import edu.memphis.ccrg.lida.workspace.csm.CurrentSituationalModelImpl;
import edu.memphis.ccrg.lida.workspace.episodicBuffer.EpisodicBufferContentImpl;
import edu.memphis.ccrg.lida.workspace.episodicBuffer.EpisodicBufferImpl;
import edu.memphis.ccrg.lida.workspace.perceptualBuffer.PerceptualBufferContentImpl;
import edu.memphis.ccrg.lida.workspace.perceptualBuffer.PerceptualBufferListener;
import edu.memphis.ccrg.lida.workspace.perceptualBuffer.PerceptualBuffer;

public class StructureBuildingCodeletDriver implements Runnable, Stoppable, PerceptualBufferListener {

	//Basics
	private boolean keepRunning = true;
	private FrameworkTimer timer;	
	private long threadID;
	
	//For this Driver, contents of the buffers and codeletMap	
	private PerceptualBufferContentImpl pBufferContent = new PerceptualBufferContentImpl();
	private Percept percept = new Percept();
	//Not yet implemented
	private EpisodicBufferContentImpl eBufferContent = new EpisodicBufferContentImpl();
	private PreviousBroadcastsContentImpl prevBroadcastContent = new PreviousBroadcastsContentImpl();
	//
	private Map<CodeletActivatingContext, StructureBuildingCodelet> codeletMap = new HashMap<CodeletActivatingContext, StructureBuildingCodelet>();//TODO: equals, hashCode
	
	//For codelets to be able to move contents around.
	private PerceptualBuffer pBuffer = null;
	private EpisodicBufferImpl eBuffer = null;
	private PreviousBroadcastsImpl pBroads = null;
	private CurrentSituationalModelImpl csm = null;
	private final double defaultCodeletActivation = 1.0;	
	private final boolean usesPBuffer = true, usesEBuffer = true, usesPBroads = true;
	
	private List<Thread> codeletThreads = new ArrayList<Thread>();
	private List<Stoppable> codelets = new ArrayList<Stoppable>();
	
	public StructureBuildingCodeletDriver(FrameworkTimer timer, PerceptualBuffer p, EpisodicBufferImpl e, 
							PreviousBroadcastsImpl pbroads, CurrentSituationalModelImpl csm){
		this.timer = timer;
		pBuffer = p;
		eBuffer = e;
		this.pBroads = pbroads;
		this.csm = csm;		
	}

	public void run(){
		CodeletObjective objective = new CodeletObjective();
		CodeletAction actions = new CodeletAction();		
		spawnNewCodelet(usesPBuffer, !usesEBuffer, !usesPBroads, defaultCodeletActivation, objective, actions);
		
		int counter = 0;		
		long startTime = System.currentTimeMillis();		
		while(keepRunning){
			try{Thread.sleep(24 + timer.getSleepTime());
			}catch(Exception e){}//TODO: if PBUFFER Content is changed wake up
			timer.checkForStartPause();
			//if BufferContent activates a sbCodelet's context start a new codelet
			getPBufferContent();
			

			counter++;			
		}//while keepRunning
		long finishTime = System.currentTimeMillis();				
		System.out.println("\nCODE: Ave. cycle time: " + 
							Misc.rnd((finishTime - startTime)/(double)counter));
		System.out.println("CODE: Num. cycles: " + counter);		
	}//public void run()

	private void spawnNewCodelet(boolean usesPBuffer, boolean usesEBuffer, boolean usesPBroads,
								 double startingActivation, CodeletObjective context, CodeletAction actions) {
		if(usesPBuffer || usesEBuffer || usesPBroads){
			StructureBuildingCodelet newCodelet = new StructureBuildingCodelet(timer, pBuffer, null, null, csm, 
					  							  defaultCodeletActivation, context, actions);

			long threadNumber = codeletThreads.size() + 1;
			Thread codeletThread = new Thread(newCodelet, "CODELET: " + threadNumber);
			newCodelet.setThreadID(codeletThread.getId());
			codeletThreads.add(codeletThread);   
			codelets.add(newCodelet);	
			codeletThread.start();			
		}//if at least 1 buffer is specified			
	}//spawnNewCodelet

	private void getPBufferContent() {
		synchronized(this){
			percept = (Percept)pBufferContent.getContent();
		}
		percept.print(keepRunning, "SB-CODELETS");
		
		Set<SpatialLocation> locations = null;
		for(PamNodeImpl n: percept){//TODO: should be in PamNode instead
			locations = n.getLocations();
			for(SpatialLocation s: locations){
				s.print();
			}
			System.out.println("\n");
		}
	}

	public void stopRunning(){
		try{Thread.sleep(20);}catch(InterruptedException e){}
		keepRunning = false;		
	}//public void stopRunning()
	
	public void setThreadID(long id){
		threadID = id;
	}
	
	public long getThreadID() {
		return threadID;
	}

	public void addCSM(CurrentSituationalModelImpl csm) {
		this.csm = csm;		
	}

	public synchronized void receivePBufferContent(PerceptualBufferContentImpl pbc) {
		pBufferContent = pbc;		
	}

}//public class SBCodeletsDriver 
