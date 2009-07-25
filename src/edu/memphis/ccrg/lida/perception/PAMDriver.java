package edu.memphis.ccrg.lida.perception;

import edu.memphis.ccrg.lida.framework.LidaTaskManager;
import edu.memphis.ccrg.lida.framework.GenericModuleDriver;

public class PAMDriver extends GenericModuleDriver{

	private PerceptualAssociativeMemory pam;
	
	public PAMDriver(PerceptualAssociativeMemory pam, LidaTaskManager timer){
		super(timer);
		this.pam = pam;
	}//constructor
		

	public void cycleStep() {
		pam.detectSensoryMemoryContent();				
		pam.propogateActivation();//Pass activation	
		pam.sendOutPercept(); //Send the percept to p-Workspace
		if (pam instanceof PerceptualAssociativeMemoryImpl){
			((PerceptualAssociativeMemoryImpl)pam).sendEvent();
		}
		pam.decayPAM();  //Decay the activations			
	}
	
}//class PAMDriver