/*******************************************************************************
 * Copyright (c) 2009, 2011 The University of Memphis.  All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the LIDA Software Framework Non-Commercial License v1.0 
 * which accompanies this distribution, and is available at
 * http://ccrg.cs.memphis.edu/assets/papers/2010/LIDA-framework-non-commercial-v1.0.pdf
 *******************************************************************************/
/**
 * 
 */
package edu.memphis.ccrg.lida.framework.tasks;
 
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.memphis.ccrg.lida.framework.FrameworkModule;
import edu.memphis.ccrg.lida.framework.shared.activation.LearnableImpl;

/**
 * This class implements the FrameworkTask Interface. This class should be used as the base class for all FrameworkTasks.
 * @author Javier Snaider
 */
public abstract class FrameworkTaskImpl extends LearnableImpl implements FrameworkTask {

	private static final Logger logger= Logger.getLogger(FrameworkTaskImpl.class.getCanonicalName());

	private final static int defaultTicksPerRun = 1;
	private static long nextTaskID;
	
	private int ticksPerRun = defaultTicksPerRun;
	private long taskID;
	private long nextExcecutionTicksPerRun = defaultTicksPerRun;
	/**
	 * {@link TaskStatus} of this task
	 */
	protected TaskStatus status = TaskStatus.WAITING;
	private Map<String, ? extends Object> parameters;
	private TaskSpawner controllingTS;
	private long scheduledTick;
    private final String taskName;
	
	/**
	 * Constructs a {@link FrameworkTaskImpl} with default ticksPerRun
	 */
	public FrameworkTaskImpl() {
		this(defaultTicksPerRun,null);
	}
	
	/**
	 * Constructs a {@link FrameworkTaskImpl} with specified ticksPerRun
	 * @param ticksPerRun task's run frequency
	 */
	public FrameworkTaskImpl(int ticksPerRun) {
		this(ticksPerRun,null);
	}
	
	/**
	 * Constructs a {@link FrameworkTaskImpl} with default ticksPerRun and specified
	 * controlling {@link TaskSpawner}
	 * @param ticksPerRun task's run frequency
	 * @param ts controlling {@link TaskSpawner}
	 */
	public FrameworkTaskImpl(int ticksPerRun, TaskSpawner ts) {
		taskID = nextTaskID++;
		controllingTS = ts;
		setTicksPerRun(ticksPerRun);
        taskName = getClass().getSimpleName() + "["+taskID+"]";
	}
	
	@Override
	public long getScheduledTick() {
		return scheduledTick;
	}

	@Override
	public void setScheduledTick(long scheduledTick) {
		this.scheduledTick = scheduledTick;
	}

	/** 
	 * This method should not be called directly nor should it be overridden.
	 * Override {@link #runThisFrameworkTask()} instead.
	 * @see java.util.concurrent.Callable#call()
	 */
	@Override
	public FrameworkTask call() {
		nextExcecutionTicksPerRun = ticksPerRun;
		
		try{
			runThisFrameworkTask();
		}catch(Exception e){
			logger.log(Level.WARNING, "Exception {1} encountered in task {2}", new Object[] {TaskManager.getCurrentTick(),e.getMessage(),this});
			e.printStackTrace();
		}
		
		if (controllingTS != null){ 
			controllingTS.receiveFinishedTask(this);
		}else {
			logger.log(Level.WARNING, "This task {1} doesn't have an assigned TaskSpawner",new Object[] {TaskManager.getCurrentTick(), this });
		}
			
		return this;
	}


	/**
	 * To be overridden by extending classes. Overriding method should execute a
	 * handful of statements considered to constitute a single iteration of the
	 * task. For example, a codelet might look in a buffer for some
	 * representation and make a change to it in a single iteration.
	 */
	protected abstract void runThisFrameworkTask();

	@Override
	public synchronized void setTaskStatus(TaskStatus status) {
		if (this.status != TaskStatus.CANCELED){
			this.status = status;
		}else {
			logger.log(Level.WARNING, "Cannot set TaskStatus to {1}.  TaskStatus is already CANCELED so it cannot be modified again.", 
					new Object[]{TaskManager.getCurrentTick(),status});
		}
	}

	@Override
	public TaskStatus getTaskStatus() {
		return status;
	}

	@Override
	public long getTaskId() {
		return taskID;
	}

	@Override
	public synchronized int getTicksPerRun() {
		return ticksPerRun;
	}

	@Override
	public synchronized void setTicksPerRun(int ticks) {
		if (ticks > 0){
			ticksPerRun = ticks;
			setNextTicksPerRun(ticks);
		}
	}

	@Override
	public void stopRunning() {
		setTaskStatus(TaskStatus.CANCELED);
	}
	
	@Override
	public void init(Map<String, ?> parameters) {
		this.parameters = parameters;
		init();
	}

	/**
	 * This is a convenience method to initialize Tasks. It is called from init(Map<String, Object> parameters). 
	 * Subclasses can overwrite this method in order to initialize the FrameworkTask
	 */
	@Override
	public void init() {
	}

	@Override
	public Object getParam(String name, Object defaultValue) {
		Object value = null;
		if (parameters != null) {
			if(parameters.containsKey(name)){
				value = parameters.get(name);
			}else{
				logger.log(Level.WARNING, "Cannot find parameter with name: \"{1}\" for task: \"{2}\". " +
						"\nCheck the parameter name in the factory task definition and/or its agent xml file declaration",
						new Object[]{TaskManager.getCurrentTick(),name, toString()});
			}
		}
		if (value == null) {
			value = defaultValue;
		}
		return value;
	}
	
	@Override
	public TaskSpawner getControllingTaskSpawner() {		
		return controllingTS;
	}
	
	@Override
	public void setControllingTaskSpawner(TaskSpawner controllingTS) {
		this.controllingTS=controllingTS;		
	}
	
	@Override
	public long getNextTicksPerRun() {		
		return nextExcecutionTicksPerRun;
	}
	
	@Override
	public void setNextTicksPerRun(long nextTicksPerRun) {
		this.nextExcecutionTicksPerRun = nextTicksPerRun;	
	}
	
	/**
	 * Subclasses may override this method.
	 */
	@Override
	public void setAssociatedModule(FrameworkModule module, String moduleUsage) {
	}	
	
	@Override
	public boolean equals(Object o){
		if(o instanceof FrameworkTaskImpl){
			return taskID == ((FrameworkTaskImpl) o).getTaskId();
		}
		return false;
	}
	@Override
	public int hashCode(){
		return (int) taskID;
	}
	
	@Override
	public String toString(){
        return taskName;
    }
	
}
