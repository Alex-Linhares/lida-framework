/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.memphis.ccrg.lida.framework.dao;

import edu.memphis.ccrg.lida.framework.LidaModule;

/**
 *
 * @author Tom
 */
public class SensoryMemoryDAO extends DataAccessObjectImpl {
    public static final String STORAGE_NAME = "sensorymemory";
    public SensoryMemoryDAO(LidaModule module, Storage cStorage, int cLidaId) {
        super(module, cStorage, STORAGE_NAME, cLidaId);
    }
}