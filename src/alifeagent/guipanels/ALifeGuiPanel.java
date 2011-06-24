/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ALifeGuiPanel.java
 *
 * Created on Jun 15, 2011, 3:18:20 PM
 */
package alifeagent.guipanels;

import edu.memphis.ccrg.alife.gui.ALifeWorldRenderer;
import edu.memphis.ccrg.alife.world.ALifeWorld;
import edu.memphis.ccrg.alife.world.WorldLoader;
import edu.memphis.ccrg.lida.environment.Environment;
import edu.memphis.ccrg.lida.framework.ModuleName;
import edu.memphis.ccrg.lida.framework.gui.panels.GuiPanelImpl;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author UofM
 */
public class ALifeGuiPanel extends GuiPanelImpl {

    private static final Logger logger = Logger.getLogger(ALifeGuiPanel.class.getCanonicalName());
    private Environment environment;

    /** Creates new form ALifeGuiPanel */
    public ALifeGuiPanel() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        aLifePanel1 = new edu.memphis.ccrg.alife.gui.ALifePanel();

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(aLifePanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 820, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(aLifePanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 622, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private edu.memphis.ccrg.alife.gui.ALifePanel aLifePanel1;
    // End of variables declaration//GEN-END:variables

    @Override
    public void initPanel(String[] param) {
        Properties iconsProperties = new Properties();
        environment = (Environment) agent.getSubmodule(ModuleName.Environment);
        if (environment != null) {
            ALifeWorld world = (ALifeWorld) environment.getModuleContent();
            try {
                iconsProperties.load(new FileReader((String) param[0]));
            } catch (IOException ex) {
                logger.log(Level.WARNING, "icon properties file can not be loaded.", ex);
            }
            if (world != null) {
                ALifeWorldRenderer renderer = WorldLoader.createRenderer(world, iconsProperties);
                if (renderer != null) {
                    int scalingFactor = 100;
                    if (param.length>1){
                        try{
                            scalingFactor = Integer.parseInt(param[1]);
                        }catch(NumberFormatException e){
                            logger.log(Level.WARNING, " Invalid Scaling Factor in param 1",0L);
                        }
                    }
                    aLifePanel1.init(renderer, world,scalingFactor);
                    return;
                }
            }
        }

        logger.log(Level.WARNING, "Unable to create the AlifeGuiPanel", 0L);

    }

    @Override
    public void refresh() {
        aLifePanel1.refresh();
    }
}
