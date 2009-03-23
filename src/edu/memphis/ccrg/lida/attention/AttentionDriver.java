package edu.memphis.ccrg.lida.attention;

import edu.memphis.ccrg.lida.globalworkspace.BroadcastContent;
import edu.memphis.ccrg.lida.globalworkspace.BroadcastListener;
import edu.memphis.ccrg.lida.util.FrameworkTimer;
import edu.memphis.ccrg.lida.util.Stoppable;
import edu.memphis.ccrg.lida.workspace.csm.CurrentSituationalModelImpl;

public class AttentionDriver implements Runnable, Stoppable, BroadcastListener{

	private FrameworkTimer timer;
	private boolean keepRunning = true;
	private BroadcastContent broadcastContent;
	private CurrentSituationalModelImpl csm;
	private long threadID;
	
	public AttentionDriver(FrameworkTimer timer) {
		this.timer = timer;
	}

	public void addCSM(CurrentSituationalModelImpl csm) {
		this.csm = csm;		
	}

	public void run() {
		while(keepRunning){
			timer.checkForStartPause();
			
		}
		
	}

	public void stopRunning() {
		keepRunning = false;		
	}

	public void receiveBroadcast(BroadcastContent bc) {
		synchronized(this){
			broadcastContent = bc;
		}
	}
	
	public void setThreadID(long id){
		threadID = id;
	}
	
	public long getThreadID() {
		return threadID;
	}

}
