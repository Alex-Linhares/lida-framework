package edu.memphis.ccrg.lida.framework.gui;

import edu.memphis.ccrg.lida.framework.Module;

/**
 * Event generated by the framework to be handled by the GUI.
 *
 * @author Javier snaider
 *
 */
public class FrameworkGuiEvent {

	private Module module;
	private String message;
	private Object data;

	/**
	 * @param moduleId
	 * @param message
	 * @param data
	 */
	public FrameworkGuiEvent(Module m, String message, Object data) {
		this.module = m;
		this.message = message;
		this.data = data;
	}

	/**
	 * @return the moduleId
	 */
	public Module getModule() {
		return module;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @return the data
	 */
	public Object getData() {
		return data;
	}
}