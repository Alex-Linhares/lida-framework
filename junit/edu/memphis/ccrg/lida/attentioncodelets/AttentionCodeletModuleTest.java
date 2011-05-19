package edu.memphis.ccrg.lida.attentioncodelets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import edu.memphis.ccrg.lida.framework.ModuleName;
import edu.memphis.ccrg.lida.framework.initialization.ModuleUsage;
import edu.memphis.ccrg.lida.framework.mockclasses.MockAttentionCodeletImpl;
import edu.memphis.ccrg.lida.framework.mockclasses.MockGlobalWorkspaceImpl;
import edu.memphis.ccrg.lida.framework.mockclasses.MockTaskSpawner;
import edu.memphis.ccrg.lida.framework.shared.ElementFactory;
import edu.memphis.ccrg.lida.framework.shared.Link;
import edu.memphis.ccrg.lida.framework.shared.Node;
import edu.memphis.ccrg.lida.framework.shared.NodeStructure;
import edu.memphis.ccrg.lida.framework.shared.NodeStructureImpl;
import edu.memphis.ccrg.lida.framework.tasks.TaskSpawner;
import edu.memphis.ccrg.lida.globalworkspace.BroadcastContent;
import edu.memphis.ccrg.lida.pam.PamNode;
import edu.memphis.ccrg.lida.workspace.WorkspaceContent;
import edu.memphis.ccrg.lida.workspace.WorkspaceImpl;
import edu.memphis.ccrg.lida.workspace.workspaceBuffer.WorkspaceBuffer;
import edu.memphis.ccrg.lida.workspace.workspaceBuffer.WorkspaceBufferImpl;

/**
 * This is a JUnit class which can be used to test methods of the AttentionCodeletModule class.
 * 
 * @author Ryan McCall
 * 
 */
public class AttentionCodeletModuleTest{
	
	private AttentionCodeletModule attentionModule;
	private TaskSpawner taskSpawner;
	private WorkspaceBuffer csm;
	private WorkspaceImpl workspace;
	private MockGlobalWorkspaceImpl globalWorkspace;
	private Node node1,node2;
	private Link link1;
	private NodeStructure ns;
	private MockAttentionCodeletImpl codelet;
	private static final ElementFactory factory = ElementFactory.getInstance();

	@Before
	public void setUp() throws Exception {
		attentionModule = new AttentionCodeletModule();
		taskSpawner = new MockTaskSpawner();
		attentionModule.setAssistingTaskSpawner(taskSpawner);
		
		csm = new WorkspaceBufferImpl();
		csm.setModuleName(ModuleName.CurrentSituationalModel);
		workspace = new WorkspaceImpl();
		workspace.addSubModule(csm);
		
		globalWorkspace= new MockGlobalWorkspaceImpl();
		
		ns = new NodeStructureImpl();
		node1 = factory.getNode();
		node2 = factory.getNode();
		PamNode category = (PamNode) factory.getNode("PamNodeImpl");
		link1 = factory.getLink(node1,node2,category);
		ns.addDefaultNode(node1);
		ns.addDefaultNode(node2);
		ns.addDefaultLink(link1);	
		
		csm.addBufferContent((WorkspaceContent) ns);
		
		codelet = new MockAttentionCodeletImpl();
		codelet.setSoughtContent(ns);	
	
		codelet.setAssociatedModule(csm, ModuleUsage.TO_READ_FROM);
		codelet.setAssociatedModule(globalWorkspace, ModuleUsage.TO_WRITE_TO);
	}
	
	@Test
	public void testReceiveBroadcast() {	
		assertEquals(0, attentionModule.getAssistingTaskSpawner().getRunningTasks().size());
		
		attentionModule.receiveBroadcast((BroadcastContent)ns);
		
		assertEquals(1, attentionModule.getAssistingTaskSpawner().getRunningTasks().size());
		//TODO more testing when fully implemented
	}

	@Test
	public void testGetDefaultCodelet() {
		//Without associated modules 
		AttentionCodeletImpl codelet = (AttentionCodeletImpl) attentionModule.getDefaultCodelet();		
		assertTrue(codelet instanceof BasicAttentionCodelet);
		codelet.setSoughtContent(ns);
		try{
			codelet.runThisFrameworkTask();
			assertTrue(false);
		}catch(NullPointerException e){
		}
		
		//with associated modules
		attentionModule.setAssociatedModule(workspace, null);
		attentionModule.setAssociatedModule(globalWorkspace, null);
		
		codelet = (AttentionCodeletImpl) attentionModule.getDefaultCodelet();		
		assertTrue(codelet instanceof BasicAttentionCodelet);
		
		assertEquals(null, globalWorkspace.coalition);
		
		codelet.setSoughtContent(ns);
		codelet.runThisFrameworkTask();
		
		assertNotNull(globalWorkspace.coalition);
	}
	

	@Test
	public void testGetCodeletString() {
		AttentionCodeletImpl codelet = (AttentionCodeletImpl) attentionModule.getCodelet("foo");
		assertEquals(null, codelet);
		
		factory.addCodeletType("coolCodelet", MockAttentionCodeletImpl.class.getCanonicalName());
		codelet = (AttentionCodeletImpl) attentionModule.getCodelet("coolCodelet");
		assertTrue(codelet instanceof MockAttentionCodeletImpl);
		
		
		//with associated modules
		attentionModule.setAssociatedModule(workspace, null);
		attentionModule.setAssociatedModule(globalWorkspace, null);
		
		codelet = (AttentionCodeletImpl) attentionModule.getCodelet("BasicAttentionCodelet");	
		assertTrue(codelet instanceof BasicAttentionCodelet);
		assertEquals(100, codelet.getParam("arg0", 100));
		
		assertEquals(null, globalWorkspace.coalition);
		
		codelet.setSoughtContent(ns);
		codelet.runThisFrameworkTask();
		
		assertNotNull(globalWorkspace.coalition);
	}

	@Test
	public void testGetCodeletStringMapOfStringObject() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("arg0", 10.0);
		params.put("name", "Javier");
		
		AttentionCodelet codelet = attentionModule.getCodelet("BasicAttentionCodelet", params);
		assertTrue(codelet instanceof BasicAttentionCodelet);
		assertEquals(10.0, codelet.getParam("arg0", null));
		assertEquals("Javier", codelet.getParam("name", null));
		
		codelet = attentionModule.getCodelet("foo", params);
		assertEquals(null, codelet);
		
		params.put("arg0", 20.0);
		params.put("name", "Ryan");
		codelet = (AttentionCodeletImpl) attentionModule.getCodelet("coolCodelet", params);
		assertTrue(codelet instanceof MockAttentionCodeletImpl);
		assertEquals(20.0, codelet.getParam("arg0", null));
		assertEquals("Ryan", codelet.getParam("name", null));
	}
	

	@Test
	public void testGetDefaultCodeletMapOfStringObject() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("arg0", 10.0);
		params.put("name", "Javier");
		
		AttentionCodelet codelet = attentionModule.getDefaultCodelet(params);
		assertTrue(codelet instanceof BasicAttentionCodelet);
		assertEquals(10.0, codelet.getParam("arg0", null));
		assertEquals("Javier", codelet.getParam("name", null));
		
		params = null;
		codelet = attentionModule.getDefaultCodelet(params);
		assertTrue(codelet instanceof BasicAttentionCodelet);
		assertEquals(20.0, codelet.getParam("arg0", 20.0));
		assertEquals("Ryan", codelet.getParam("name", "Ryan"));
	}

	@Test
	public void testAddCodelet() {
		assertEquals(0, taskSpawner.getRunningTasks().size());
		
		attentionModule.addCodelet(codelet);
		
		assertTrue(taskSpawner.containsTask(codelet));
		assertEquals(1, taskSpawner.getRunningTasks().size());
	}	
	
	@Test
	public void testGetModuleContent() {
		attentionModule.setAssociatedModule(globalWorkspace, "");
		
		Object[] o = null;
		Object content = attentionModule.getModuleContent(o);
		assertEquals(null, content);
		
		content = attentionModule.getModuleContent(new Object[]{});
		assertEquals(null, content);
		
		content = attentionModule.getModuleContent(new Object[0]);
		assertEquals(null, content);
		
		content = attentionModule.getModuleContent(new Object[]{null});
		assertEquals(null, content);
		
		content = attentionModule.getModuleContent(new Object[]{2456});
		assertEquals(null, content);
		
		content = attentionModule.getModuleContent(new Object[]{"hi"});
		assertEquals(null, content);
		
		content = attentionModule.getModuleContent(new Object[]{"globalworkspace"});
		assertEquals(globalWorkspace, content);
		
		content = attentionModule.getModuleContent(new Object[]{"GLOBALWORKSPACE"});
		assertEquals(globalWorkspace, content);
	}

	@Test
	public void testSetDefaultCodeletType() {
		AttentionCodelet codelet = attentionModule.getDefaultCodelet();
		assertTrue(codelet instanceof BasicAttentionCodelet);
		
		attentionModule.setDefaultCodeletType("34t90j");
		
		codelet = attentionModule.getDefaultCodelet();
		assertTrue(codelet instanceof BasicAttentionCodelet);
		
		assertTrue(factory.containsCodeletType("coolCodelet"));
		attentionModule.setDefaultCodeletType("coolCodelet");
		
		codelet = attentionModule.getDefaultCodelet();
		assertTrue(codelet instanceof MockAttentionCodeletImpl);
	}

	@Test
	public void testToString() {
		assertEquals(ModuleName.AttentionModule.toString(), attentionModule.toString());
	}

	@Test
	public void testAddListener() {
		//TODO when implemented
	}

	@Test
	public void testDecayModule() {
		//TODO when implemented
	}
}
