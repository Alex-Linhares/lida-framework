package edu.memphis.ccrg.lida.pam;

import java.util.Collection;

import junit.framework.TestCase;
import edu.memphis.ccrg.lida.framework.mockclasses.MockPAM;
import edu.memphis.ccrg.lida.framework.mockclasses.MockTaskSpawner;
import edu.memphis.ccrg.lida.framework.shared.activation.Activatible;
import edu.memphis.ccrg.lida.framework.strategies.DefaultExciteStrategy;
import edu.memphis.ccrg.lida.framework.tasks.FrameworkTask;
import edu.memphis.ccrg.lida.framework.tasks.TaskStatus;
import edu.memphis.ccrg.lida.pam.tasks.AddToPerceptTask;
import edu.memphis.ccrg.lida.pam.tasks.ExcitationTask;

public class ExcitationTaskTest extends TestCase {
	
	private PamNode pamNode;
	
	/*
	 * Used to make another excitation call
	 */
	private  MockPAM pam;
	
	/*
	 * For threshold task creation
	 */
	private MockTaskSpawner taskSpawner;
	
	@Override
	public void setUp() throws Exception {
		pamNode = new PamNodeImpl();
		pam = new MockPAM();
		taskSpawner= new MockTaskSpawner();
	}
	
	
	public void test(){
		pam.setPerceptThreshold(1.0);
		pamNode.setExciteStrategy(new DefaultExciteStrategy());
		ExcitationTask excite= new ExcitationTask(pamNode, 0.5, 1, pam, taskSpawner);
		
		excite.call();
		assertEquals(pamNode.getActivation(), 0.5 + Activatible.DEFAULT_ACTIVATION);
		assertEquals(pam.testGetSink().getActivation(), 0.5 + Activatible.DEFAULT_ACTIVATION);
		assertTrue(TaskStatus.FINISHED == excite.getStatus() );
	 
	}
	
	public void testTaskSpawner(){
		pam.setPerceptThreshold(0.4);
		pamNode.setExciteStrategy(new DefaultExciteStrategy());
		ExcitationTask excite= new ExcitationTask(pamNode, 0.5, 1, pam, taskSpawner);
		
		excite.call();
		assertEquals(pamNode.getActivation(), 0.5 + Activatible.DEFAULT_ACTIVATION);
		assertEquals(pam.testGetSink().getActivation(), 0.5 + Activatible.DEFAULT_ACTIVATION);
		
		Collection<FrameworkTask> tasks= taskSpawner.getRunningTasks(); 
		for(FrameworkTask tsk: tasks){
			assertTrue(tsk instanceof AddToPerceptTask);
		}
		 
		assertTrue(TaskStatus.FINISHED == excite.getStatus() );
	 
	}

}
