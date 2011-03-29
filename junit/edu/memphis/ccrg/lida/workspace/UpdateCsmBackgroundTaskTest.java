package edu.memphis.ccrg.lida.workspace;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.memphis.ccrg.lida.framework.ModuleName;
import edu.memphis.ccrg.lida.framework.initialization.ModuleUsage;
import edu.memphis.ccrg.lida.framework.shared.Node;
import edu.memphis.ccrg.lida.framework.shared.NodeImpl;
import edu.memphis.ccrg.lida.framework.shared.NodeStructure;
import edu.memphis.ccrg.lida.framework.shared.NodeStructureImpl;
import edu.memphis.ccrg.lida.workspace.workspaceBuffer.WorkspaceBuffer;
import edu.memphis.ccrg.lida.workspace.workspaceBuffer.WorkspaceBufferImpl;

public class UpdateCsmBackgroundTaskTest {

	@Test
	public final void testRunThisLidaTask() {

		UpdateCsmBackgroundTask uct = new UpdateCsmBackgroundTask();
		
    	//step 3-1:
		//Create 3 nodes and add them into a node structure
		NodeStructure ns = new NodeStructureImpl();
		
		Node n1 = new NodeImpl();
		n1.setId(2);
		n1.setActivation(0.2);
		ns.addDefaultNode(n1);
		
		Node n2 = new NodeImpl();
		n2.setId(6);
		n2.setActivation(0.6);
		ns.addDefaultNode(n2);
		
		Node n3 = new NodeImpl();
		n3.setId(8);
		n3.setActivation(0.8);
		ns.addDefaultNode(n3);

		//Step 3-2:
		//Create workspaceBuffer and add them into mockWorkspace
		WorkspaceBuffer perceptualBuffer = new WorkspaceBufferImpl();
		WorkspaceBuffer CSMBuffer = new WorkspaceBufferImpl();
		
		perceptualBuffer.setModuleName(ModuleName.PerceptualBuffer);
		CSMBuffer.setModuleName(ModuleName.CurrentSituationalModel);
		
		WorkspaceImpl wMoudle = new WorkspaceImpl();
		wMoudle.addSubModule(perceptualBuffer);
		wMoudle.addSubModule(CSMBuffer);
		
		//Step 3-3:
		// Add node structure into workspaceBuffer of percetualBuffer
		((NodeStructure)wMoudle.getSubmodule(ModuleName.PerceptualBuffer)
				.getModuleContent()).mergeWith(ns);

		// Testing of setAssociateMoudle()
		uct.setAssociatedModule(wMoudle, ModuleUsage.NOT_SPECIFIED);
		
		//Run method of target class
		uct.runThisLidaTask();
		
		// Check CSM Buffer
		NodeStructure ns2 = (NodeStructure) CSMBuffer.getModuleContent();
		
		assertTrue("Problem with class UpdateCsmBackgroundTask for testRunThisLidaTask()",
				(NodeStructureImpl.compareNodeStructures(ns, ns2)));
	}

	@Test
	public final void testInit() {
        //NA
	}

	@Test
	public final void testSetAssociatedModule() {
		//SetAssociatedModule() be tested in testRunThisLidaTask method above with testing of 
		//RunThisLidaTask() together.
	}

	@Test
	public final void testToString() {
		UpdateCsmBackgroundTask cbt = new UpdateCsmBackgroundTask();
		String sAns = "UpdateCsmBackgroundTask";
		
		assertEquals("Problem with class UpdateCsmBackgroundTask for testToString()", cbt.toString(), sAns);
	}

}