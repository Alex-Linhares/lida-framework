package edu.memphis.ccrg.lida.sensoryMemory;

public class SimulationContent {
	
	private char[][][] simSense;
	private String environment;
	
	public SimulationContent(int size){
		simSense = new char[size][size][4];
	}
	
	public void setContent(Object o, String environment){
		simSense = (char[][][])o;
		this.environment = environment;
	}
	
	public Object getSenseContent(){
		char[][][] copy = new char[simSense.length][simSense[0].length][simSense[0][0].length];
		System.arraycopy(simSense, 0, copy, 0, simSense.length);		
		return copy;
	}	
	
	public String getEnvironment(){
		return environment;
	}

	public void print(){

	}

	public int getSize() {
		return simSense.length;
	}

}//SimulationContent

