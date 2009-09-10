/**
 * 
 */
package edu.memphis.ccrg.lida.framework;

import java.util.Map;

import edu.memphis.ccrg.lida.framework.shared.ActivatibleImpl;

/**
 * @author Javier Snaider
 */
public abstract class LidaTaskImpl extends ActivatibleImpl implements LidaTask {

	private static int defaultTicksPerStep = 1;

	/**
	 * @return the defaultTicksPerStep
	 */
	public static int getDefaultTicksPerStep() {
		return defaultTicksPerStep;
	}

	/**
	 * @param defaultTicksPerStep
	 *            the defaultTicksPerStep to set
	 */
	public static void setDefaultTicksPerStep(int defaultTicksPerStep) {
		if (defaultTicksPerStep > 0) {
			LidaTaskImpl.defaultTicksPerStep = defaultTicksPerStep;
		}
	}

	private long taskID;
	private int ticksPerStep = defaultTicksPerStep;
	private int accumulatedTicks;
	protected int status = LidaTask.WAITING;
	private LidaTaskNames taskName;
	private LidaTaskManager taskManager;
	private Map<String, Object> parameters;
	
	public LidaTaskImpl(LidaTaskManager tm, LidaTaskNames name) {
		this(defaultTicksPerStep, tm, name);
	}

	public LidaTaskImpl(int ticksForCycle, LidaTaskManager tm, LidaTaskNames name) {
		taskManager = tm;
		taskName = name;
		taskID = LidaTaskManager.getNextTaskID();

		setNumberOfTicksPerStep(ticksForCycle);
	}

	public void run() {
		if (!LidaTaskManager.isInTicksMode()) {
			sleep();
			runThisLidaTask();
		} else if (hasEnoughTicks()) {
			useOneStepOfTicks();
			sleep();
			runThisLidaTask();
		}
	}

	private void sleep() {
		try {
			// Sleeps a lap proportional for each task
			Thread.sleep(taskManager.getTickDuration() * getTicksPerStep());
		} catch (InterruptedException e) {
			stopRunning();
		}
	}

	/**
	 * To be overridden by child classes. Overriding method should execute a
	 * handful of statements considered to constitute a single iteration of the
	 * task. For example, a codelet might look in a buffer for some
	 * representation and make a change to it in a single iteration.
	 * 
	 */
	protected void runThisLidaTask() {
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setTaskStatus(int status) {
		// If a task is canceled it cannot be restarted.
		// So only set the status if the status is not CANCELED.
		if (this.status != LidaTask.CANCELED)
			this.status = status;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	public long getTaskId() {
		return taskID;
	}

	public void setTaskID(long id) {
		taskID = id;
	}

	public int getTicksPerStep() {
		return ticksPerStep;
	}

	public void setNumberOfTicksPerStep(int ticks) {
		if (ticks > 0)
			ticksPerStep = ticks;
	}

	public void addTicks(int ticks) {
		this.accumulatedTicks = accumulatedTicks + ticks;
	}

	public int getAccumulatedTicks() {
		return accumulatedTicks;
	}

	public boolean useOneStepOfTicks() {
		if (accumulatedTicks >= ticksPerStep) {
			accumulatedTicks = accumulatedTicks - ticksPerStep;
			return true;
		}
		return false;
	}

	public boolean hasEnoughTicks() {
		return accumulatedTicks >= ticksPerStep;
	}

	public void reset() {

	}

	public void stopRunning() {
		setTaskStatus(LidaTask.CANCELED);
	}

	public void setTaskManager(LidaTaskManager taskManager) {
		this.taskManager = taskManager;
		if (taskID == 0) {
			taskID = LidaTaskManager.getNextTaskID();
		}
	}

	public LidaTaskManager getTaskManager() {
		return taskManager;
	}

	public void init(Map<String, Object> parameters) {
		this.parameters = parameters;
		init();
	}

	protected void init() {
	}

	public Object getParameter(String name) {
		Object res = null;
		if (parameters != null) {
			res = parameters.get(name);
		}
		return res;
	}

}// class
