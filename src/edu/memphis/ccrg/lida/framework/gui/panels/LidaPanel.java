/**
 *
 */
package edu.memphis.ccrg.lida.framework.gui.panels;

import javax.swing.JPanel;

import edu.memphis.ccrg.lida.framework.Lida;
import edu.memphis.ccrg.lida.framework.Module;
import edu.memphis.ccrg.lida.framework.gui.LidaGuiController;

/**
 *  Lida Gui Panel
 * @author Javier Snaider
 *
 */
public interface LidaPanel {
	public void registrerLidaGuiController(LidaGuiController lgc);
	public void registrerLida(Lida lida);
	public void display (Object o);
	public void refresh();
	public JPanel getPanel();
    public Module getSupportedModule();
    public void setSupportedModule(Module module);
    public void setName(String name);
    public String getName();
}
