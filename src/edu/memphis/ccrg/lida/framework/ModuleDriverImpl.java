package edu.memphis.ccrg.lida.framework;

import java.util.logging.Logger;

public abstract class ModuleDriverImpl extends LidaTaskImpl implements ModuleDriver {

	private static Logger logger = Logger.getLogger("lida.framework.ModuleDriverImpl");

	public ModuleDriverImpl(int ticksPerCycle, LidaTaskManager tm){
		super(ticksPerCycle, tm);
	}

	protected void runThisLidaTask(){
		runThisDriver();
	}
	protected abstract void runThisDriver();
	
}// class