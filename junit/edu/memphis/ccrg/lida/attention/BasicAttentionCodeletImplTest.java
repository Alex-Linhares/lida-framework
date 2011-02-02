/**
 * 
 */
package edu.memphis.ccrg.lida.attention;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;



import edu.memphis.ccrg.lida.framework.ModuleName;
import edu.memphis.ccrg.lida.framework.shared.LinkCategory;
import edu.memphis.ccrg.lida.framework.shared.LinkCategoryNode;
import edu.memphis.ccrg.lida.framework.shared.LinkImpl;
import edu.memphis.ccrg.lida.framework.shared.NodeImpl;
import edu.memphis.ccrg.lida.framework.shared.NodeStructure;
import edu.memphis.ccrg.lida.framework.shared.NodeStructureImpl;
import edu.memphis.ccrg.lida.workspace.workspaceBuffer.WorkspaceBuffer;
import edu.memphis.ccrg.lida.workspace.workspaceBuffer.WorkspaceBufferImpl;

/**
 * @author Home
 *
 */
public class BasicAttentionCodeletImplTest {
	WorkspaceBufferImpl buffer;
	NodeImpl node1,node2,node3;
	LinkImpl link1,link2;
	NodeStructure nodeStructure;
	BasicAttentionCodeletImpl attnCodelet;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		buffer = new WorkspaceBufferImpl();
		node1 = new NodeImpl();
		node2 = new NodeImpl();
		node3 = new NodeImpl();
		
		nodeStructure= new NodeStructureImpl();
		
		node1.setId(1);
		node2.setId(2);
		node3.setId(3);		
				
		link1 = new LinkImpl(node1,node2,LinkCategoryNode.CHILD);
		link2 = new LinkImpl(node2,node3,LinkCategoryNode.CHILD);
		
		nodeStructure.addNode(node1);
		nodeStructure.addNode(node2);
		nodeStructure.addNode(node3);
		nodeStructure.addLink(link1);
		nodeStructure.addLink(link2);
		
		attnCodelet = new BasicAttentionCodeletImpl();
	}

	/**
	 * Test method for {@link edu.memphis.ccrg.lida.attention.BasicAttentionCodeletImpl#hasSoughtContent(edu.memphis.ccrg.lida.workspace.workspaceBuffer.WorkspaceBuffer)}.
	 */
	@Test
	public void testHasSoughtContent() {
		
		attnCodelet.setSoughtContent(nodeStructure);
		NodeStructure model = (NodeStructure) buffer.getModuleContent();
		model.mergeWith(nodeStructure);
		
		assertEquals("Problem with HasSoughtContent",true,attnCodelet.hasSoughtContent(buffer));
	}

	/**
	 * Test method for {@link edu.memphis.ccrg.lida.attention.BasicAttentionCodeletImpl#getWorkspaceContent(edu.memphis.ccrg.lida.workspace.workspaceBuffer.WorkspaceBuffer)}.
	 */
	@Test
	public void testGetWorkspaceContent() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link edu.memphis.ccrg.lida.attention.BasicAttentionCodeletImpl#BasicAttentionCodeletImpl()}.
	 */
	@Test
	public void testBasicAttentionCodeletImpl() {
		fail("Not yet implemented"); // TODO
	}

}