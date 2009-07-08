/* 
 * RunLida.java - Initializes, starts, and ends the main LIDA components.  
 * Sets up the GUIs and the  
 */
package edu.memphis.ccrg.lida.example.genericLIDA.main;

import java.util.List;
import java.lang.Thread; 
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import edu.memphis.ccrg.lida.actionSelection.ActionSelectionImpl;
import edu.memphis.ccrg.lida.attention.AttentionDriver;
import edu.memphis.ccrg.lida.declarativeMemory.DeclarativeMemoryImpl;
import edu.memphis.ccrg.lida.example.genericLIDA.environSensoryMem.VisionEnvironment;
import edu.memphis.ccrg.lida.example.genericLIDA.environSensoryMem.VisionSensoryContent;
import edu.memphis.ccrg.lida.example.genericLIDA.environSensoryMem.VisionSensoryMemory;
import edu.memphis.ccrg.lida.example.genericLIDA.gui.ControlPanelGui;
import edu.memphis.ccrg.lida.example.genericLIDA.gui.NodeLinkFlowGui;
import edu.memphis.ccrg.lida.example.genericLIDA.gui.VisualFieldGui;
import edu.memphis.ccrg.lida.example.genericLIDA.io.GlobalWorkspace_Input;
import edu.memphis.ccrg.lida.example.genericLIDA.io.PamInput;
import edu.memphis.ccrg.lida.framework.FrameworkModuleDriver;
import edu.memphis.ccrg.lida.framework.FrameworkTimer;
import edu.memphis.ccrg.lida.framework.ThreadSpawner;
import edu.memphis.ccrg.lida.globalworkspace.GlobalWorkspaceImpl;
import edu.memphis.ccrg.lida.perception.PAMDriver;
import edu.memphis.ccrg.lida.perception.PerceptualAssociativeMemoryImpl;
import edu.memphis.ccrg.lida.proceduralMemory.ProceduralMemoryDriver;
import edu.memphis.ccrg.lida.proceduralMemory.ProceduralMemoryImpl;
import edu.memphis.ccrg.lida.sensoryMemory.SensoryMemoryDriver;
import edu.memphis.ccrg.lida.sensoryMotorAutomatism.SensoryMotorAutomatism;
import edu.memphis.ccrg.lida.sensoryMotorAutomatism.SensoryMotorAutomatismImpl;
import edu.memphis.ccrg.lida.shared.NodeStructureImpl;
import edu.memphis.ccrg.lida.transientEpisodicMemory.TEMImpl;
import edu.memphis.ccrg.lida.workspace.broadcastBuffer.BroadcastQueueDriver;
import edu.memphis.ccrg.lida.workspace.broadcastBuffer.BroadcastQueueImpl;
import edu.memphis.ccrg.lida.workspace.currentSituationalModel.CSMDriver;
import edu.memphis.ccrg.lida.workspace.currentSituationalModel.CurrentSituationalModelImpl;
import edu.memphis.ccrg.lida.workspace.episodicBuffer.EpisodicBufferDriver;
import edu.memphis.ccrg.lida.workspace.episodicBuffer.EpisodicBufferImpl;
import edu.memphis.ccrg.lida.workspace.main.WorkspaceImpl;
import edu.memphis.ccrg.lida.workspace.perceptualBuffer.PerceptualBufferDriver;
import edu.memphis.ccrg.lida.workspace.perceptualBuffer.PerceptualBufferImpl;
import edu.memphis.ccrg.lida.workspace.structureBuildingCodelets.StructBuildCodeletDriver;

public class GenericLida implements ThreadSpawner{
	
	//Perception 
	private VisionEnvironment environment;
	private SensoryMotorAutomatism sma;
	private VisionSensoryMemory sensoryMemory;
	private PerceptualAssociativeMemoryImpl pam;
	//Episodic memory
	private TEMImpl tem;
	private DeclarativeMemoryImpl declarativeMemory;
	//Workspace
	private WorkspaceImpl workspace;
	private PerceptualBufferImpl perceptBuffer;
	private EpisodicBufferImpl episodicBuffer;
	private BroadcastQueueImpl broadcastQueue;
	private CurrentSituationalModelImpl csm;
	//Attention
	private GlobalWorkspaceImpl globalWksp;
	//Action Selection
	private ProceduralMemoryImpl procMem;
	private ActionSelectionImpl actionSelection;
	//Drivers
	private SensoryMemoryDriver sensoryMemoryDriver;
	private PAMDriver pamDriver;
	private PerceptualBufferDriver perceptBufferDriver;
	private EpisodicBufferDriver episodicBufferDriver;
	private BroadcastQueueDriver broadcastBufferDriver;
	private StructBuildCodeletDriver sbCodeletDriver;
	private CSMDriver csmDriver;
	private AttentionDriver attnDriver;
	private ProceduralMemoryDriver proceduralMemDriver;		
	
	//GUIs
	/**
	 * To view the environment
	 */
	private VisualFieldGui visualFieldGui = new VisualFieldGui();
	
	/**
	 * GUI Showing counts of active nodes and links in the modules
	 */
	private NodeLinkFlowGui nodeLinkFlowGui = new NodeLinkFlowGui();
	
	/**
	 * For basic control of the system
	 */
	private ControlPanelGui controlPanelGui;
	
	//Threads & thread control
	private List<FrameworkModuleDriver> drivers = new ArrayList<FrameworkModuleDriver>();
	private FrameworkTimer timer;

	public static void main(String[] args){
		new GenericLida().run();
	}//method

	public void run(){
		initThreadControl();
		
		initEnvironmentThread();		
		//perception
		initSensoryMemoryThread();
		initPAMThread();		
		//episodic memories
		initTransientEpisodicMemory();
		initDeclarativeMemory();		
		//workspace
		initWorkspace();			
		//attention
		initGlobalWorkspace();
		initAttentionThread();		
		//action selection and execution
		initProceduralMemoryThread();
		initActionSelectionThread();
		initSensoryMotorAutomatism();
		//gui
		initGUI();
		defineListeners();//Define Observer pattern listeners
		
		//Start everything up, threads are terminated via GUI
		startLidaSystem();
	}//method
	
	private void initThreadControl(){
		boolean frameworkStartsRunning = false;
		int threadSleepTime = 150;
		timer = new FrameworkTimer(frameworkStartsRunning, threadSleepTime);		
	}

	private void initEnvironmentThread(){
		int height = 10;
		int width = 10;
		environment = new VisionEnvironment(timer, height, width);	
		drivers.add(environment);			
	}
	private void initSensoryMotorAutomatism(){
		sma = new SensoryMotorAutomatismImpl();
	}	
	private void initSensoryMemoryThread(){
		sensoryMemory = new VisionSensoryMemory();		
		sensoryMemoryDriver = new SensoryMemoryDriver(sensoryMemory, timer);
		drivers.add(sensoryMemoryDriver);				
	}
	private void initPAMThread(){
		pam = new PerceptualAssociativeMemoryImpl(new VisionSensoryContent());
		String pamInputPath = "";
		PamInput reader = new PamInput();
		reader.read(pam, pamInputPath);
		//PAM THREAD		
		pamDriver = new PAMDriver(pam, timer);  
		drivers.add(pamDriver);
	}//method
	
	private void initWorkspace(){
		initPerceptualBufferThread();
		initEpisodicBufferThread();
		initBroadcastBufferThread();
		initCSMThread();
		initWorkspaceFacade();
		initSBCodeletsThread();
	}
	private void initPerceptualBufferThread(){
		int capacity = 2;
		perceptBuffer = new PerceptualBufferImpl(capacity);	
	    perceptBufferDriver = new PerceptualBufferDriver(perceptBuffer, timer); 
		drivers.add(perceptBufferDriver);
	}
	private void initEpisodicBufferThread(){
		int capacity = 10;
		episodicBuffer = new EpisodicBufferImpl(capacity);
		episodicBufferDriver = new EpisodicBufferDriver(episodicBuffer, timer); 
		drivers.add(episodicBufferDriver);
	}
	private void initBroadcastBufferThread(){
		int capacity = 10;
		broadcastQueue = new BroadcastQueueImpl(capacity);
		broadcastBufferDriver = new BroadcastQueueDriver(broadcastQueue, timer);
		drivers.add(broadcastBufferDriver);		
	}
	private void initCSMThread(){
		csm = new CurrentSituationalModelImpl();
		csmDriver = new CSMDriver(csm, timer);
		drivers.add(csmDriver);
	}
	private void initDeclarativeMemory() {
		declarativeMemory = new DeclarativeMemoryImpl();
		//TODO will we use a driver?
	}
	private void initTransientEpisodicMemory() {
		tem = new TEMImpl(new NodeStructureImpl());
		//TODO will we use a driver?
	}
	private void initWorkspaceFacade() {
		workspace = new WorkspaceImpl(perceptBuffer, episodicBuffer, 
									  broadcastQueue, csm);		
	}//method
	private void initSBCodeletsThread() {
		sbCodeletDriver = new StructBuildCodeletDriver(workspace, timer);		 
		drivers.add(sbCodeletDriver);			
	}
	private void initGlobalWorkspace() {
		globalWksp = new GlobalWorkspaceImpl();
		String globalWorkspaceInputPath = "";
		GlobalWorkspace_Input reader = new GlobalWorkspace_Input();
		reader.read(globalWksp, globalWorkspaceInputPath);
	}//method
	private void initAttentionThread(){
		attnDriver = new AttentionDriver(timer, csm, globalWksp);
		drivers.add(attnDriver);
	}	
	private void initProceduralMemoryThread(){
		procMem = new ProceduralMemoryImpl();
		proceduralMemDriver = new ProceduralMemoryDriver(procMem, timer);
		drivers.add(proceduralMemDriver);
	}
	private void initActionSelectionThread() {
		actionSelection = new ActionSelectionImpl();
	}
	private void initGUI() {	
		controlPanelGui = new ControlPanelGui(timer, this, sbCodeletDriver, environment, actionSelection);
		controlPanelGui.setVisible(true);
		visualFieldGui.setVisible(true);
		nodeLinkFlowGui.setVisible(true);
	}//method
	
	private void defineListeners(){
		//TODO: NODELINKFLOWGUI!!
		environment.addEnvironmentListener(sensoryMemory);
		environment.addEnvironmentListener(visualFieldGui);
		
		sensoryMemory.addSensoryListener(sma);
		sma.addSensoryMotorListener(sensoryMemory);
		sensoryMemory.addSensoryListener(pam);
		//
		pam.addPAMListener(workspace);
		pam.addFrameworkGui(nodeLinkFlowGui);
		
		perceptBuffer.addBufferListener(workspace);
		perceptBuffer.addFrameworkGui(nodeLinkFlowGui);
		//
		episodicBuffer.addBufferListener(workspace);
		episodicBuffer.addFrameworkGui(nodeLinkFlowGui);
		//
		broadcastQueue.addBufferListener(workspace);
		broadcastQueue.addFrameworkGui(nodeLinkFlowGui);
		//
		csm.addBufferListener(workspace);
		csm.addFrameworkGui(nodeLinkFlowGui);
		
		workspace.addCueListener(declarativeMemory);
		//workspace.addCueListener(tem);
		workspace.addCodeletListener(sbCodeletDriver);
		workspace.addPamListener(pam);
		sbCodeletDriver.addFrameworkGui(nodeLinkFlowGui);
			
		actionSelection.addBehaviorListener(workspace);		
		globalWksp.addBroadcastListener(pam);
		globalWksp.addBroadcastListener(workspace);
		globalWksp.addBroadcastListener(tem);
		globalWksp.addBroadcastListener(attnDriver);
		globalWksp.addBroadcastListener(procMem);
		globalWksp.addFrameworkGui(nodeLinkFlowGui);
		globalWksp.start();
		procMem.addProceduralMemoryListener(actionSelection);
		procMem.addFrameworkGui(nodeLinkFlowGui);
		actionSelection.addBehaviorListener(environment);
		actionSelection.addFrameworkGui(nodeLinkFlowGui);
	}//method
	
	private void startLidaSystem(){
		ExecutorService executorService = Executors.newCachedThreadPool();
		int size = drivers.size();
		for(int i = 0; i < size; i++)
			executorService.execute(drivers.get(i));
		
		executorService.shutdown();
	}//method
	
	/**
	 * Stop in reverse order of starting
	 */	
	public void stopRunningSpawnedThreads(){		
		int size = drivers.size();
		for(int i = 0; i < size; i++)			
			(drivers.get(size - 1 - i)).stopRunning();
		
		//TODO: Run serialization!
		try{Thread.sleep(500);}catch(Exception e){}
		System.exit(0);//Kills gui windows & anything still running
	}//method	

	/**
	 * For ControlGUI to display thread count
	 * 
	 * @return number of threads started by this class
	 */
	public int getSpawnedThreadCount() {
		return drivers.size();
	}//method

}//class