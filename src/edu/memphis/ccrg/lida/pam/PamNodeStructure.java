package edu.memphis.ccrg.lida.pam;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import edu.memphis.ccrg.lida.framework.shared.Link;
import edu.memphis.ccrg.lida.framework.shared.Linkable;
import edu.memphis.ccrg.lida.framework.shared.Node;
import edu.memphis.ccrg.lida.framework.shared.NodeStructureImpl;
import edu.memphis.ccrg.lida.framework.strategies.DecayBehavior;
import edu.memphis.ccrg.lida.framework.strategies.ExciteBehavior;

//TODO: Check this class
public class PamNodeStructure extends NodeStructureImpl{
	private Logger logger = Logger.getLogger("lida.pam.PamNodeStructure");
	
	private Double upscaleFactor = 0.7;
	@SuppressWarnings("unused")
	private Double downscaleFactor = 0.5;
	private Double selectivityThreshold = 0.8;
	
	public PamNodeStructure(){
		super("PamNodeImpl", "LinkImpl");
	}

	public PamNodeStructure(String defaultPamNode, String defaultLink) {
		super(defaultPamNode, defaultLink);
	}

	public void setUpscale(Double d) {
		upscaleFactor  = d;		
	}
	public double getUpscale(){
		return upscaleFactor;
	}

	/**
	 * Set downscale factor
	 * @param d
	 */
	public void setDownscale(Double d) {
		downscaleFactor  = d;		
	}

	/**
	 * Set selectivity threshold
	 * @param s
	 */
	public void setSelectivity(Double s) {
		selectivityThreshold = s;		
	}
	
	/**
	 * Add a collection of PamNodes to this pam node structure
	 */	
	public void addPamNodes(Collection<PamNode> nodes){
		for(Node n: nodes)
			addNode(n);

		updateActivationThresholds(upscaleFactor, selectivityThreshold);
	}//method
	/**
	 * Add a single PamNode to this pam node structure
	 * @param node
	 */
	public void addPamNode(PamNode node){
		addNode(node);
		updateActivationThresholds(upscaleFactor, selectivityThreshold);
	}
	
	/**
	 * Update the min and max activations and selection threshold
	 * of the Linkables in the layermap
	 * 
	 * @param upscale
	 * @param selectivity
	 */
	private void updateActivationThresholds(double upscale, double selectivity){
		Collection<Node> nodes = getNodes();
        for(Node n: nodes){
        	PamNode pamNode = (PamNode)n;
        	updateSelectionThreshold(pamNode, selectivity);
        }//for	
    }//method
	
	/**
     * Calc selection threshold based on the selectivity and min and max activ.
     */
	private void updateSelectionThreshold(PamNode n, double selectivity){
		double min = n.getMinActivation();
		double max = n.getMaxActivation();
		double threshold = selectivity*(max - min) + min;
		n.setSelectionThreshold(threshold);
	}   
	
	/** 
	 * Determines if linkable has no children.
	 * @param n
	 * @return true if n has no children (it is at the 'bottom' of the network)
	 */
    public boolean hasNoChildren(Linkable n) {
		Set<Link> links = getLinkableMap().get(n);
		if(links != null){
			for(Link link: links){
				Linkable source = link.getSource();
				//if source is a child of n
				if(source instanceof PamNode && !source.equals(n))
					return false;
			}//for
		}//
		return true;
	}//method
    
	/** 
	 * Determine if linkable has no parents.
	 * @param n
	 * @return true if n has no parent (it is at the 'top' of the network)
	 */
	public boolean hasNoParents(Linkable n) {
		Set<Link> links = getLinkableMap().get(n);
		if(links != null){
			for(Link l: links){
				Linkable sink = l.getSink();
				//if sink is a parent of n
				if(sink instanceof PamNode && !sink.equals(n))
					return false;
			}//for
		}
		return true;
	}//method
	
	//************END OF METHODS RELATED TO NODE ADDING*************	

	/**
	 * Set the excite behavior for all nodes
	 */
	public void setNodesExciteBehavior(ExciteBehavior behavior) {
    	for(Node n: getNodes())
    		n.setExciteBehavior(behavior);
	}//method
	
	/**
	 * Set the decay behavior for all nodes.
	 * TODO: make the behavior a field on this class instead?
	 * @param behavior
	 */
	public void setNodesDecayBehavior(DecayBehavior behavior) {
    	for(Node n: getNodes())
    		n.setDecayBehavior(behavior);
	}//method
	
	/**
	 * Get parents of this linkable. 
	 * O(l) where l = number of links connected to n.
	 * 
	 * @param n
	 * @return
	 */
	public Set<PamNode> getParents(Node n) {
		Set<PamNode> parents = new HashSet<PamNode>();
		Set<Link> links = getLinkableMap().get(n);
		if(links != null){
			for(Link l: links){
				Linkable sink = l.getSink();//Sinks are 'above' this node. 
				if(sink instanceof PamNode && !sink.equals(n))
					parents.add((PamNode) sink);
			}
		}
		return parents;
	}//method 
	
	/**
	 * Get children of this linkable. 
	 * O(l) where l = number of links connected to n.
	 * 
	 * @param n
	 * @return set of child nodes
	 */
	public Set<PamNode> getChildren(Linkable n) {
		Set<PamNode> children = new HashSet<PamNode>();
		Set<Link> links = getLinkableMap().get(n);
		if(links != null){
			for(Link l: links){
				Linkable source = l.getSource();//Sources are 'below' this node.
				if(source instanceof PamNode && !source.equals(n))
					children.add((PamNode)source);			
			}
		}
		return children;
	}//method

	/**
	 * Decay the nodes of this pam node structure
	 */
	public void decayNodes(){
		logger.fine("Decaying the Pam NodeStructure");
		for(Node n: getNodes())
			n.decay();
	}//method
	
	/**
	 * Simple utility method
	 */
	public void printPamNodeActivations() {
		for(Node n: getNodes())
			((PamNodeImpl)n).printActivationString();
	}//method


}//class