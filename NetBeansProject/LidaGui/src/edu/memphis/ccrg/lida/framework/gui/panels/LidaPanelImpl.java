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
 * LidaPanelImpl.java
 *
 * Created on 17/08/2009, 08:08:08
 */

package edu.memphis.ccrg.lida.framework.gui.panels;

import edu.memphis.ccrg.lida.framework.Lida;
import edu.memphis.ccrg.lida.framework.Module;
import edu.memphis.ccrg.lida.framework.gui.FrameworkGuiController;
import edu.memphis.ccrg.lida.framework.gui.events.FrameworkGuiEvent;

import javax.swing.JPanel;

/**
 *
 * @author Javier Snaider
 */
public abstract class LidaPanelImpl extends javax.swing.JPanel implements LidaPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected FrameworkGuiController controller;
	protected Lida lida;
    private Module supportedModule;

    /** Creates new form LidaPanelImpl */
    public LidaPanelImpl() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    public void registrerGuiController(FrameworkGuiController lgc) {
        controller = lgc;
    }

    public void display(Object o) {

    }

    public void refresh() {
    }

    public JPanel getPanel() {
        return this;
    }

    public void registerLida(Lida lida) {
		this.lida=lida;
	}

    public Module getSupportedModule() {
        return supportedModule;
    }
    public void setSupportedModule(Module module) {
         this.supportedModule=module;
    }
	
	public void initPanel(String[] param){
	}
}
