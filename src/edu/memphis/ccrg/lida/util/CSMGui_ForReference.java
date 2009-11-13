/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * CSMGui.java
 *
 * Created on Mar 10, 2009, 5:28:06 PM
 */ 

package edu.memphis.ccrg.lida.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import edu.memphis.ccrg.lida.framework.ModuleType;
import edu.memphis.ccrg.lida.framework.shared.Link;
import edu.memphis.ccrg.lida.framework.shared.LinkImpl;
import edu.memphis.ccrg.lida.framework.shared.Linkable;
import edu.memphis.ccrg.lida.framework.shared.Node;
import edu.memphis.ccrg.lida.framework.shared.NodeStructureImpl;
import edu.memphis.ccrg.lida.workspace.main.WorkspaceContent;
import edu.memphis.ccrg.lida.workspace.workspaceBuffer.WorkspaceBufferListener;

public class CSMGui_ForReference extends javax.swing.JFrame implements WorkspaceBufferListener{

	private static final long serialVersionUID = 1L;
    public CSMGui_ForReference() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
   private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        linkList = new java.awt.List();
        jLabel4 = new javax.swing.JLabel();
        sourceTextField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        selectedLink = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        sinkTextField = new javax.swing.JTextField();
        nodeList = new java.awt.List();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        linkCountTextField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 1, 16));
        jLabel1.setText("Current Situational Model");

        jLabel2.setFont(new java.awt.Font("Lucida Grande", 1, 13));
        jLabel2.setText("Links");

        linkList.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                linkListItemStateChanged(evt);
            }
        });

        jLabel4.setText("Sink");

        sourceTextField.setText("--");

        jLabel6.setText("Source");

        selectedLink.setText("--");

        jLabel3.setText("Type");

        sinkTextField.setText("--");

        jLabel5.setFont(new java.awt.Font("Lucida Grande", 1, 13));
        jLabel5.setText("Nodes - IDs");

        jLabel7.setFont(new java.awt.Font("Lucida Grande", 1, 13));
        jLabel7.setText("Selected Link");

        linkCountTextField.setText("--");

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel1)
                    .add(layout.createSequentialGroup()
                        .addContainerGap()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(layout.createSequentialGroup()
                                .add(jLabel2)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(linkCountTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 41, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(linkList, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 234, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel7)
                            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                                .add(jLabel4)
                                .add(jLabel6)
                                .add(jLabel3)
                                .add(sourceTextField)
                                .add(sinkTextField)
                                .add(layout.createSequentialGroup()
                                    .add(selectedLink, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 142, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED))))
                        .add(14, 14, 14)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(nodeList, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 130, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel5))))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(jLabel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 39, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel2)
                    .add(jLabel5)
                    .add(jLabel7)
                    .add(linkCountTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, linkList, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                        .add(jLabel3)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(selectedLink, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel6)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(sourceTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel4)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(sinkTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(nodeList, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE))
                .addContainerGap())
        );
        setLocation(545, 375);
        pack();
    }// </editor-fold>

    /**
     * Get the item from the event and get its value (a string).
     * There are 3 text fields that need filled.
     * 1. Selected links's ID
     * 2. Sink's label
     * 3. Source's label
     * 
     * @param evt
     */
    private void linkListItemStateChanged(java.awt.event.ItemEvent evt) {
    	String linkId = linkList.getItem((Integer)evt.getItem());       	
    	LinkImpl linkInStruct = (LinkImpl)struct.getLink(linkId);
    	if(linkInStruct != null){
    		selectedLink.setText(linkInStruct.getType() + "");
    		
	    	Linkable source = linkInStruct.getSource();
			Linkable sink = linkInStruct.getSink();
			sinkTextField.setText(sink.getLabel());						
    		sourceTextField.setText(source.getLabel());
    	}else{
    		selectedLink.setText(linkId);
    		sourceTextField.setText("NA");
    		sinkTextField.setText("NA");
    		System.out.println("couldnt get link by id!!");
    	}
    }//method
    
    /**
     * When a new struct comes from the CSM, clear the node and link lists
     * and fill them with the new stuff
     */
	public void receiveBufferContent(ModuleType buffer, WorkspaceContent content) {
		synchronized(this){
			struct = (NodeStructureImpl)content;
		}		
		Collection<Link> links = struct.getLinks();
		Collection<Node> nodes = struct.getNodes();		
		
		Set<Link> otherLinks = new HashSet<Link>();
		linkList.removeAll();
		int i = 0;
		linkCountTextField.setText(links.size() + "");
		for(Link l: links){
			linkList.add(l.getIds() + "", i);				
			i++;
		}//for links		
		for(Link l: otherLinks)
			linkList.add(l.getIds() + "", i);
	
		i = 0;
		nodeList.removeAll();

		for(Node n: nodes){
			nodeList.add(n.getLabel() + " ID: " + n.getId(), i);
			i++;
		}
	}//method
	
	 private NodeStructureImpl struct = new NodeStructureImpl(); 
	 // Variables declaration - do not modify
	 private javax.swing.JLabel jLabel1;
	 private javax.swing.JLabel jLabel2;
	 private javax.swing.JLabel jLabel3;
	 private javax.swing.JLabel jLabel4;
	 private javax.swing.JLabel jLabel5;
	 private javax.swing.JLabel jLabel6;
	 private javax.swing.JLabel jLabel7;
	 private javax.swing.JTextField linkCountTextField;
	 private java.awt.List linkList;
	 private java.awt.List nodeList;
	 private javax.swing.JTextField selectedLink;
	 private javax.swing.JTextField sinkTextField;
	 private javax.swing.JTextField sourceTextField;
	 // End of variables declaration

}//class