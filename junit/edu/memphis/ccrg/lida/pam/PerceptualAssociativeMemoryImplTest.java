/*******************************************************************************
 * Copyright (c) 2009, 2010 The University of Memphis.  All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the LIDA Software Framework Non-Commercial License v1.0 
 * which accompanies this distribution, and is available at
 * http://ccrg.cs.memphis.edu/assets/papers/2010/LIDA-framework-non-commercial-v1.0.pdf
 *******************************************************************************/
/**
 * 
 */
package edu.memphis.ccrg.lida.pam;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import edu.memphis.ccrg.lida.example.genericlida.environment.VisualSensoryMemory;
import edu.memphis.ccrg.lida.example.genericlida.featuredetectors.BasicDetector;
import edu.memphis.ccrg.lida.framework.ModuleName;
import edu.memphis.ccrg.lida.framework.mockclasses.MockTaskSpawner;
import edu.memphis.ccrg.lida.framework.shared.ExtendedId;
import edu.memphis.ccrg.lida.framework.shared.LidaElementFactory;
import edu.memphis.ccrg.lida.framework.shared.LinkCategoryNode;
import edu.memphis.ccrg.lida.framework.shared.Node;
import edu.memphis.ccrg.lida.framework.shared.NodeStructureImpl;
import edu.memphis.ccrg.lida.framework.strategies.LinearDecayStrategy;
import edu.memphis.ccrg.lida.framework.tasks.TaskSpawner;
import edu.memphis.ccrg.lida.framework.tasks.TaskSpawnerImpl;
import edu.memphis.ccrg.lida.pam.tasks.FeatureDetector;
import edu.memphis.ccrg.lida.sensorymemory.SensoryMemoryImpl;

/**
 * @author Siminder Kaur
 *
 */
public class PerceptualAssociativeMemoryImplTest extends TestCase{
	
	PerceptualAssociativeMemoryImpl pam;
	NodeStructureImpl nodeStructure;
	PamNodeImpl node1,node2,node3;
	LinearDecayStrategy decayStrategy ;
	PamLinkImpl link1,link2;
	SensoryMemoryImpl sem;
	PamNodeImpl pamNode;
	
	private LidaElementFactory factory = LidaElementFactory.getInstance();
	
	
	@Override
	@Before
	public void setUp() throws Exception {
		pam = new PerceptualAssociativeMemoryImpl();
		nodeStructure = new NodeStructureImpl(PamNodeImpl.class.getSimpleName(), PamLinkImpl.class.getSimpleName());
		
		node1 = new PamNodeImpl();
		node2 = new PamNodeImpl();
		node3 = new PamNodeImpl();
		decayStrategy = new LinearDecayStrategy();
		sem = new VisualSensoryMemory();
		pamNode = new PamNodeImpl();
		
				
		node1.setId(1);
		node2.setId(2);
		node3.setId(3);		
			
		node1.setActivation(0.8);
		node1.setDecayStrategy(decayStrategy);
		
		link1 = (PamLinkImpl) factory.getLink(PamLinkImpl.getFactoryLinkType(), node1, node2, LinkCategoryNode.CHILD);
		link2 = (PamLinkImpl) factory.getLink(PamLinkImpl.getFactoryLinkType(), node2, node3, LinkCategoryNode.CHILD);
		
		link1.setSource(node1);
		link1.setSink(node2);
		link2.setSource(node2);
		link2.setSink(node3);
		
		//pam.addNode(node1);	
		
		TaskSpawner taskSpawner = new MockTaskSpawner();	
//		taskSpawner.setTaskID(1);
		pam.setAssistingTaskSpawner(taskSpawner);
	}

	/**
	 * Test method for {@link edu.memphis.ccrg.lida.pam.PerceptualAssociativeMemoryImpl#getModuleName()}.
	 */
	@Test
	public void testGetModuleName() {
		ModuleName name = pam.getModuleName();
		assertEquals("Problem with GetModuleName", ModuleName.PerceptualAssociativeMemory, name);
	}

	/**
	 * Test method for {@link edu.memphis.ccrg.lida.pam.PerceptualAssociativeMemoryImpl#decayModule(long)}.
	 */
	@Test
	public void testDecayModule() {		
		pam.addDefaultNode(node1);	
		pam.decayModule(100);
		assertTrue("Problem with DecayModule",pam.getPamNode(node1.getId()).getTotalActivation()<0.8);		
	}

	/**
	 * Test method for {@link edu.memphis.ccrg.lida.pam.PerceptualAssociativeMemoryImpl#setAssistingTaskSpawner(edu.memphis.ccrg.lida.framework.tasks.TaskSpawner)}.
	 *
	 */
	@Test
	public void testSetTaskSpawner() {
		TaskSpawner taskSpawner = new TaskSpawnerImpl();
		pam.setAssistingTaskSpawner(taskSpawner);
		assertEquals("Problem with SetTaskSpawner", taskSpawner, pam.getAssistingTaskSpawner());
	}

	/**
	 * Test method for {@link edu.memphis.ccrg.lida.pam.PerceptualAssociativeMemoryImpl#setPropagationBehavior(edu.memphis.ccrg.lida.pam.PropagationBehavior)}.
	 */
	@Test
	public void testSetPropagationBehavior() {
		PropagationBehavior pb = new UpscalePropagationBehavior();
		pam.setPropagationBehavior(pb);		
		assertEquals("Problem with SetPropagationBehavior", pb, pam.getPropagationBehavior());
	}

	/**
	 * Test method for {@link edu.memphis.ccrg.lida.pam.PerceptualAssociativeMemoryImpl#addDefaultNodes(java.util.Set)}.
	 */
	@Test
	public void testAddNodes() {
		Set<PamNode> nodes = new HashSet<PamNode>();
		nodes.add(node2);
		nodes.add(node3);
		pam.addDefaultNode(node1);	
		pam.addDefaultNodes(nodes);
		
		assertTrue("Problem with AddNodes", pam.containsNode(node1) && pam.containsNode(node2) && pam.containsNode(node3));
	}

	/**
	 * Test method for {@link edu.memphis.ccrg.lida.pam.PerceptualAssociativeMemoryImpl#addDefaultLinks(Set)}.
	 */
	@Test
	public void testAddLinks() {
		Set<PamLink> links = new HashSet<PamLink>();
		
		pam.addDefaultNode(node1);	
		pam.addDefaultNode(node2);
		pam.addDefaultNode(node3);
		links.add(link1);
		links.add(link2);
		pam.addDefaultLinks(links);
		
		assertTrue("Problem with AddLinks",pam.containsLink(link1) && pam.containsLink(link2));
	}

	/**
	 * Test method for {@link edu.memphis.ccrg.lida.pam.PerceptualAssociativeMemoryImpl#addFeatureDetector(edu.memphis.ccrg.lida.pam.tasks.FeatureDetector)}.
	 */
	@Test
	public void testAddFeatureDetector() {
		
		pamNode.setId(12);		
		FeatureDetector detector = new BasicDetector(pamNode,sem,pam);
		
		pam.addFeatureDetector(detector);
		assertTrue("Problem with AddFeatureDetector",pam.getAssistingTaskSpawner().containsTask(detector));
	}

	
	/**
	 * Test method for {@link edu.memphis.ccrg.lida.pam.PerceptualAssociativeMemoryImpl#receiveWorkspaceContent(edu.memphis.ccrg.lida.framework.ModuleName, edu.memphis.ccrg.lida.workspace.main.WorkspaceContent)}.
	 */
	@Test
	public void testReceiveWorkspaceContent() {
		nodeStructure.addDefaultNode(node1);
		nodeStructure.addDefaultNode(node2);
		pam.receiveWorkspaceContent(ModuleName.Workspace, nodeStructure);
		
		//Workspace content not handled in pam
		//assertEquals("Problem with ReceiveWorkspaceContent",nodeStructure,pam.getSubmodule(ModuleName.Workspace).getModuleContent());
		
	}

	/**
	 * Test method for {@link edu.memphis.ccrg.lida.pam.PerceptualAssociativeMemoryImpl#receiveBroadcast(edu.memphis.ccrg.lida.globalworkspace.BroadcastContent)}.
	 */
	@Test
	public void testReceiveBroadcast() {
		nodeStructure.addDefaultNode(node1);
		nodeStructure.addDefaultNode(node2);
		pam.receiveBroadcast(nodeStructure);		
			
		//Broadcast content not handled in pam
		//assertEquals("Problem with ReceiveBroadcast",nodeStructure,pam.getModuleContent());
		
	}

	/**
	 * Test method for {@link edu.memphis.ccrg.lida.pam.PerceptualAssociativeMemoryImpl#propagateActivationToParents(edu.memphis.ccrg.lida.pam.PamNode)}.
	 */
	@Test
	public void testSendActivationToParentsOf() {
		fail("Not yet implemented"); 
	}

	/**
	 * Test method for {@link edu.memphis.ccrg.lida.pam.PerceptualAssociativeMemoryImpl#addNodeToPercept(edu.memphis.ccrg.lida.pam.PamNode)}.
	 */
	@Test
	public void testAddNodeToPercept() {
		fail("Not yet implemented"); 
	}

	/**
	 * Test method for {@link edu.memphis.ccrg.lida.pam.PerceptualAssociativeMemoryImpl#addLinkToPercept(edu.memphis.ccrg.lida.framework.shared.Link)}.
	 */
	@Test
	public void testAddLinkToPercept() {
		fail("Not yet implemented"); 
	}

	/**
	 * Test method for {@link edu.memphis.ccrg.lida.pam.PerceptualAssociativeMemoryImpl#addNodeStructureToPercept(edu.memphis.ccrg.lida.framework.shared.NodeStructure)}.
	 */
	@Test
	public void testAddNodeStructureToPercept() {
		fail("Not yet implemented"); 
	}

	/**
	 * Test method for {@link edu.memphis.ccrg.lida.pam.PerceptualAssociativeMemoryImpl#setDecayStrategy(edu.memphis.ccrg.lida.framework.strategies.DecayStrategy)}.
	 */
	@Test
	public void testSetDecayStrategy() {
		fail("Not yet implemented"); 
	}

	/**
	 * Test method for {@link edu.memphis.ccrg.lida.pam.PerceptualAssociativeMemoryImpl#setExciteStrategy(edu.memphis.ccrg.lida.framework.strategies.ExciteStrategy)}.
	 */
	@Test
	public void testSetExciteStrategy() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link edu.memphis.ccrg.lida.pam.PerceptualAssociativeMemoryImpl#setParameters(java.util.Map)}.
	 */
	@Test
	public void testSetParameters() {
		fail("Not yet implemented"); 
	}

	/**
	 * Test method for {@link edu.memphis.ccrg.lida.pam.PerceptualAssociativeMemoryImpl#getFeatureDetectors()}.
	 */
	@Test
	public void testGetFeatureDetectors() {
		fail("Not yet implemented"); 
	}

	/**
	 * Test method for {@link edu.memphis.ccrg.lida.pam.PerceptualAssociativeMemoryImpl#getNodeStructure()}.
	 */
	@Test
	public void testGetNodeStructure() {
		fail("Not yet implemented"); 
	}

	/**
	 * Test method for {@link edu.memphis.ccrg.lida.pam.PerceptualAssociativeMemoryImpl#getModuleContent()}.
	 */
	@Test
	public void testGetModuleContent() {
		fail("Not yet implemented"); 
	}

	/**
	 * Test method for {@link edu.memphis.ccrg.lida.pam.PerceptualAssociativeMemoryImpl#addListener(edu.memphis.ccrg.lida.framework.ModuleListener)}.
	 */
	@Test
	public void testAddListener() {
		fail("Not yet implemented"); 
	}

	/**
	 * Test method for {@link edu.memphis.ccrg.lida.pam.PerceptualAssociativeMemoryImpl#getPamNode(int)}.
	 */
	@Test
	public void testGetNode() {
		fail("Not yet implemented"); 
	}

	/**
	 * Test method for {@link edu.memphis.ccrg.lida.pam.PerceptualAssociativeMemoryImpl#addDefaultNode(Node)}.
	 */
	@Test
	public void testAddNode() {
		fail("Not yet implemented"); 
	}

	/**
	 * Test method for {@link edu.memphis.ccrg.lida.pam.PerceptualAssociativeMemoryImpl#addLink(ExtendedId, ExtendedId, edu.memphis.ccrg.lida.framework.shared.LinkType, double)}.
	 */
	@Test
	public void testAddLinkPamNodePamNodeLinkTypeDouble() {
		fail("Not yet implemented"); 
	}

	/**
	 * Test method for {@link edu.memphis.ccrg.lida.pam.PerceptualAssociativeMemoryImpl#addLink(ExtendedId, ExtendedId, edu.memphis.ccrg.lida.framework.shared.LinkType, double)}.
	 */
	@Test
	public void testAddLinkStringStringLinkTypeDouble() {
		fail("Not yet implemented"); 
	}

	/**
	 * Test method for {@link edu.memphis.ccrg.lida.pam.PerceptualAssociativeMemoryImpl#addNewNode(java.lang.String)}.
	 */
	@Test
	public void testAddNewNode() {
		pam.addNewNode("BOB");
		boolean hasIt = false;
		for(Node n: pam.getPamNodes()){
			if(n.getLabel().equals("BOB"))
				hasIt = true;
		}
		assertTrue(hasIt);		
	}
	
	@Test
	public void testIsOverPerceptThreshold() {
		pam.setPerceptThreshold(0.5);
		node1.setBaseLevelActivation(0.4);
		node1.setActivation(0.2);
		assertTrue(pam.isOverPerceptThreshold(node1));
	}

}
