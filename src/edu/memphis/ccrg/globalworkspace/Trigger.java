/**
 * 
 */
package edu.memphis.ccrg.globalworkspace;

import java.util.Map;
import java.util.Set;

/**
 * A Trigger determines when a new Broadcast must be triggered.
 * Its start method should be invoked once (most of the cases when the GlobalWorkspace starts)
 * Its command method is called every time a new coalition enters the GW.
 * It can implement a timer if it needed
 * See default Triggers as examples of implementation.
 * 
 * @author Javier Snaider
 *
 */
public interface Trigger {
	/**
	 * This method is a generic way to setup the Trigger. It should be called when 
	 * the trigger is created.
	 * @param parameters a map for generic parameters.
	 * @param gw A TriggerListener. Most of the cases is the same class that 
	 * implements GlobalWorkspace Interface.
	 */
	public void setUp (Map<String,Object> parameters,TriggerListener gw);
	/**
	 * Each time a new Coalition is putted in the GW, this method is called for all registered the Triggers.
	 * @param coalitions All the coalitions in the GW.
	 */
	public void command (Set<Coalition> coalitions);
	/**
	 * To reset the Trigger. Its called each time a new Broadcast is Triggered.
	 */
	public void reset();
	/**
	 * To start the Trigger behavior
	 */
	public void start();
}
