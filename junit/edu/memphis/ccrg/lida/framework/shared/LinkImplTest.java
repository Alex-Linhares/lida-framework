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
package edu.memphis.ccrg.lida.framework.shared;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.memphis.ccrg.lida.pam.PamLinkImpl;
import edu.memphis.ccrg.lida.pam.PamNodeImpl;

/**
 * This is a JUnit class which can be used to test methods of the LinkImpl class
 * @author Siminder Kaur
 *
 */
public class LinkImplTest extends TestCase{
	
	NodeImpl node1,node2,node3,node4;
	LinkImpl link1,link2,link3;	
	PamLinkImpl pamLink1;
	PamNodeImpl pamNode1;
	LinkCategory linktype1,linktype2;		

	/**
	 * This method is called before running each test case to initialize the objects
	 */
	@Override
	@Before
	public void setUp() throws Exception {
		node1 = new NodeImpl();
		node2 = new NodeImpl();
		node3 = new NodeImpl();
		node4 = new NodeImpl();
		
		pamLink1 = new PamLinkImpl();
		pamNode1 = new PamNodeImpl();
		
		link1 = new LinkImpl();
		link2 = new LinkImpl();
		link3 = new LinkImpl();
		linktype1 = LinkCategoryNode.PARENT ;
		linktype2 = LinkCategoryNode.CHILD ;	

		node1.setId(1);	
		node2.setId(2);
		node3.setId(3);		
		node4.setId(4);		
		
		link1 = new LinkImpl(node1,node2,LinkCategoryNode.CHILD);
		link2 = new LinkImpl(node3,node4,LinkCategoryNode.PARENT);
		link3 = new LinkImpl(node3,link2,LinkCategoryNode.GROUNDING);
		linktype1 = LinkCategoryNode.PARENT ;
		linktype2 = LinkCategoryNode.CHILD;		
	
	}

	/**
	 * This method is called after running each test case
	 */
	@Override
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * This method is used to test the LinkImpl.equals() method
	 */
	@Test
	public void testEqualsObject() {
		
		link1.setSource(node1);
		link1.setSink(node2);
		link1.setCategory(linktype1);
		
		link3.setSource(node1);
		link3.setSink(node2);
		link3.setCategory(linktype1);
		
		assertEquals("Problem with equals", link1, link3);
		
	}

	/**
	 * This method is used to test the LinkImpl.getSink() method
	 */
	@Test
	public void testGetSink() {
		
		link1.setSink(node1);
		assertEquals("Problem with getSink", node1, link1.getSink());
	}

	/**
	 * This method is used to test the LinkImpl.getSource() method
	 */
	@Test
	public void testGetSource() {
		link1.setSource(node1);
		assertEquals("Problem with getSource", node1, link1.getSource());
	}

	/**
	 * This method is used to test the LinkType.getSource() method
	 */
	@Test
	public void testGetType() {
		
		link1.setCategory(linktype1);
		assertEquals("Problem with getType", linktype1, link1.getCategory());
	}


	/**
	 * This method is used to test the LinkImpl.GetId() method
	 */
	@Test
	public void testGetId() {
		//TODO FIXXXXXXXX
//		assertEquals("Problem with getId:"+link1.getId(), "L(1:2:CHILD)", link1.getId());
//		assertEquals("Problem with getId:"+link3.getId(), "L(3:L(3:4:PARENT):GROUNDING)", link3.getId());
	}

	/**
	 * This method is used to test the LinkImpl.SetSink() method
	 */
	@Test
	public void testSetSink() {
		link1.setSink(node1);
		assertEquals("Problem with setSink", node1, link1.getSink());
	}

	/**
	 * This method is used to test the LinkImpl.SetSource() method
	 */
	@Test
	public void testSetSource() {
		link1.setSource(node1);
		assertEquals("Problem with setSource", node1, link1.getSource());
	}

	/**
	 * This method is used to test the LinkImpl.SetType() method
	 */
	@Test
	public void testSetType() {
		link1.setCategory(linktype1);
		assertEquals("Problem with setType", linktype1, link1.getCategory());
	}

	/**
	 * This method is used to test the LinkImpl.GetReferencedLink() method
	 */
	@Test
	public void testGetReferencedLink() {
		link1.setGroundingPamLink(pamLink1);
		assertEquals("Problem with getReferencedLink", pamLink1, link1.getGroundingPamLink());
	}

	/**
	 * This method is used to test the LinkImpl.SetReferencedLink() method
	 */
	@Test
	public void testSetReferencedLink() {
		link1.setGroundingPamLink(pamLink1);
		assertEquals("Problem with setReferencedLink", pamLink1, link1.getGroundingPamLink());
	}
	
	/**
	 * This method is used to test the LinkImpl.LinkImpl(Linkable source, Linkable sink, LinkType type, String ids) method
	 */
	@Test
	public void testLinkImpl() {
		LinkImpl link5= new LinkImpl(node1,node2,linktype1);
				
		assertEquals("Problem with LinkImpl constructor having parameters", node1, link5.getSource());
		assertEquals("Problem with LinkImpl constructor having parameters", node2, link5.getSink());
		assertEquals("Problem with LinkImpl constructor having parameters", linktype1, link5.getCategory());
//		assertEquals("Problem with LinkImpl constructor having parameters", "L(1:2:PARENT)", link5.getId());
//TODO: FIXXXXXXX		
	}

}
