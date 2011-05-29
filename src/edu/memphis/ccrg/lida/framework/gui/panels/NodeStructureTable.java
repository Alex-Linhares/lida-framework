/*******************************************************************************
 * Copyright (c) 2009, 2011 The University of Memphis.  All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the LIDA Software Framework Non-Commercial License v1.0 
 * which accompanies this distribution, and is available at
 * http://ccrg.cs.memphis.edu/assets/papers/2010/LIDA-framework-non-commercial-v1.0.pdf
 *******************************************************************************/
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * NodeStructureTable.java
 *
 * Created on Sep 14, 2009, 7:10:07 PM
 */

package edu.memphis.ccrg.lida.framework.gui.panels;

import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.table.AbstractTableModel;

import edu.memphis.ccrg.lida.framework.FrameworkModule;
import edu.memphis.ccrg.lida.framework.gui.utils.GuiUtils;
import edu.memphis.ccrg.lida.framework.shared.Node;
import edu.memphis.ccrg.lida.framework.shared.NodeStructure;
import edu.memphis.ccrg.lida.framework.tasks.TaskManager;
import edu.memphis.ccrg.lida.pam.PamNode;
import edu.memphis.ccrg.lida.pam.PerceptualAssociativeMemoryImpl;

/**
 * A {@link GuiPanel} which displays the attributes of a {@link NodeStructure}
 * in a table.
 * @author Javier Snaider
 */
public class NodeStructureTable extends GuiPanelImpl {

	private static final Logger logger = Logger.getLogger(NodeStructureTable.class.getCanonicalName());
	
	private NodeStructure nodeStructure;
	private FrameworkModule module;
    
	/** Creates new form NodeStructureTable */
    public NodeStructureTable() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jToolBar1 = new javax.swing.JToolBar();
        refreshButton = new javax.swing.JButton();        

        jToolBar1.setRollover(true);

        refreshButton.setText("refresh");
        refreshButton.setFocusable(false);
        refreshButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        refreshButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(refreshButton);
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE))
        );
        
        table = new javax.swing.JTable();

        table.setModel(new NodeStructureTableModel());
        jScrollPane1.setViewportView(table);

       /* org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 452, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE))
        );*/
    }// </editor-fold>


    // Variables declaration - do not modify
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JButton refreshButton;
    // End of variables declaration
    
    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed
    	refresh();
    }//GEN-LAST:event_refreshButtonActionPerformed
    
	/**
	 * Definition of this Panel should include a parameter for the ModuleName for the
	 * module from which the NodeStructure will be obtained.  
	 * E.g., workspace.PerceptualBuffer or PerceptualAssociativeMemory
	 * @see edu.memphis.ccrg.lida.framework.gui.panels.GuiPanelImpl#initPanel(java.lang.String[])
	 */
	@Override
	public void initPanel(String[]param){
		if (param == null || param.length == 0) {
			logger.log(Level.WARNING,
					"Error initializing NodeStructureTable, not enough parameters.",
					0L);
			return;
		}
		module = GuiUtils.parseFrameworkModule(param[0], agent);
		
		if(module != null){
			display(module.getModuleContent());
		}else{
			logger.log(Level.WARNING,
					"Unable to parse module " + param[0] + ". Panel not initialized.",
					0L);
		}
	}

    @Override
	public void refresh(){
    	display(module.getModuleContent());
    }
    
	/*
	 * Implementation of abstract table model to adapt a NodeStructure to a Table.
	 * Columns are the attributes of the Nodes in the NodeStructure.  Rows are the Nodes
	 * @author Javier Snaider
	 */
	private class NodeStructureTableModel extends AbstractTableModel {
		//TODO support links as well
		private String[] columnNames ={"Node","Activation","Base Activation","Threshold"};
		@Override
		public int getColumnCount() {
			return columnNames.length;
		}

		@Override
		public int getRowCount() {
			return nodeStructure.getNodeCount();
		}

		@Override
		public String getColumnName(int column){
			if(column < columnNames.length){
				return columnNames[column];
			}
			return "";
		}

		/**
		 * Depending on the columnIndex, the appropriate method is called 
		 * to get an attribute of the Node. 
		 * 
		 * @param rowIndex which row that is being filled in
		 * @param columnIndex the attribute being asked for
		 * @see javax.swing.table.TableModel#getValueAt(int, int)
		 */
		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			Node node=null;
			if (rowIndex>nodeStructure.getNodeCount() || columnIndex > columnNames.length
					||rowIndex<0||columnIndex<0){
				return null;
			}
			Collection<Node> nodes = nodeStructure.getNodes();
			Iterator<Node> it = nodes.iterator();
			//TODO optimize
			for (int i=0;i<=rowIndex;i++){
				node=it.next();
			}
			switch(columnIndex){
			case 0:
				return node.getLabel();
			case 1:
				return node.getActivation();
			case 2:
				if (node instanceof PamNode){
					return ((PamNode)node).getBaseLevelActivation();
				}else{
					return "";
				}
			case 3:
				if (node instanceof PamNode){
					return PerceptualAssociativeMemoryImpl.getPerceptThreshold();
				}else{
					return "";
				}
			default:
				return "";
			}
		}
	}
	
	@Override
	public void display(Object o) {
		if (o instanceof NodeStructure) {
			nodeStructure = (NodeStructure) o;
			((AbstractTableModel) table.getModel()).fireTableStructureChanged();
		}else{
			logger.log(Level.WARNING, "Can only display NodeStructure, but received " +
					o + " from module: " + module.getModuleName(), TaskManager.getCurrentTick());
		}
	}
}
