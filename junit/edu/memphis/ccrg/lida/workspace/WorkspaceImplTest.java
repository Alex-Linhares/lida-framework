/*******************************************************************************
 * Copyright (c) 2009, 2010 The University of Memphis.  All rights reserved.
 * This program and the accompanying materials are made available
 * under the terms of the LIDA Software Framework Non-Commercial License v1.0
 * which accompanies this distribution, and is available at
 * http://ccrg.cs.memphis.edu/assets/papers/2010/LIDA-framework-non-commercial-v1.0.pdf
 *******************************************************************************/

package edu.memphis.ccrg.lida.workspace;

import edu.memphis.ccrg.lida.episodicmemory.CueListener;
import edu.memphis.ccrg.lida.framework.FrameworkModule;
import edu.memphis.ccrg.lida.framework.ModuleListener;
import edu.memphis.ccrg.lida.framework.shared.NodeStructure;
import edu.memphis.ccrg.lida.globalworkspace.BroadcastContent;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * This class is the JUnit test for <code>WorkspaceImpl</code> class.
 * @author Rodrigo Silva-Lugo
 */
public class WorkspaceImplTest {

    /**
     *
     */
    public WorkspaceImplTest() {
    }

    /**
     *
     * @throws Exception
     */
    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    /**
     *
     * @throws Exception
     */
    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     *
     */
    @Before
    public void setUp() {
    }

    /**
     *
     */
    @After
    public void tearDown() {
    }

    /**
     * Test of addSubModule method, of class WorkspaceImpl.
     */
    @Test
    public void testAddSubModule() {
        // TODO review test
        System.out.println("addSubModule");
        FrameworkModule lm = null;
        WorkspaceImpl instance = new WorkspaceImpl();
        instance.addSubModule(lm);
    }

    /**
     * Test of addListener method, of class WorkspaceImpl.
     */
    @Test
    public void testAddListener() {
        // TODO review test
        System.out.println("addListener");
        ModuleListener listener = null;
        WorkspaceImpl instance = new WorkspaceImpl();
        instance.addListener(listener);
    }

    /**
     * Test of addCueListener method, of class WorkspaceImpl.
     */
    @Test
    public void testAddCueListener() {
        // TODO review test
        System.out.println("addCueListener");
        CueListener l = null;
        WorkspaceImpl instance = new WorkspaceImpl();
        instance.addCueListener(l);
    }

    /**
     * Test of addWorkspaceListener method, of class WorkspaceImpl.
     */
    @Test
    public void testAddWorkspaceListener() {
        // TODO review test
        System.out.println("addWorkspaceListener");
        WorkspaceListener listener = null;
        WorkspaceImpl instance = new WorkspaceImpl();
        instance.addWorkspaceListener(listener);
    }

    /**
     * Test of cueEpisodicMemories method, of class WorkspaceImpl.
     */
    @Test
    public void testCueEpisodicMemories() {
        // TODO review test
        System.out.println("cueEpisodicMemories");
        NodeStructure content = null;
        WorkspaceImpl instance = new WorkspaceImpl();
        instance.cueEpisodicMemories(content);
    }

    /**
     * Test of receiveBroadcast method, of class WorkspaceImpl.
     */
    @Test
    public void testReceiveBroadcast() {
        // TODO review test
        System.out.println("receiveBroadcast");
        BroadcastContent bc = null;
        WorkspaceImpl instance = new WorkspaceImpl();
        instance.receiveBroadcast(bc);
    }

    /**
     * Test of receiveLocalAssociation method, of class WorkspaceImpl.
     */
    @Test
    public void testReceiveLocalAssociation() {
        // TODO review test
        System.out.println("receiveLocalAssociation");
        NodeStructure association = null;
        WorkspaceImpl instance = new WorkspaceImpl();
        instance.receiveLocalAssociation(association);
    }

    /**
     * Test of receivePercept method, of class WorkspaceImpl.
     */
    @Test
    public void testReceivePercept() {
        // TODO review test
        System.out.println("receivePercept");
        NodeStructure newPercept = null;
        WorkspaceImpl instance = new WorkspaceImpl();
        instance.receivePercept(newPercept);
    }

    /**
     * Test of getModuleContent method, of class WorkspaceImpl.
     */
    @Test
    public void testGetModuleContent() {
        // TODO review test
        System.out.println("getModuleContent");
        Object[] params = null;
        WorkspaceImpl instance = new WorkspaceImpl();
        Object expResult = null;
        Object result = instance.getModuleContent(params);
        assertEquals(expResult, result);
    }

    /**
     * Test of learn method, of class WorkspaceImpl.
     */
    @Test
    public void testLearn() {
        // TODO review test
        System.out.println("learn");
        BroadcastContent content = null;
        WorkspaceImpl instance = new WorkspaceImpl();
        instance.learn(content);
    }

    /**
     * Test of init method, of class WorkspaceImpl.
     */
    @Test
    public void testInit() {
        // TODO review test
        System.out.println("init");
        WorkspaceImpl instance = new WorkspaceImpl();
        instance.init();
    }
}