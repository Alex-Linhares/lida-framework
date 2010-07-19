/*
 * @(#)BasicTranslator.java  1.0  January 29, 2010
 *
 * Copyright 2006-2010 Cognitive Computing Research Group.
 * 365 Innovation Dr, Rm 303, Memphis, TN 38152, USA.
 * All rights reserved.
 */

package edu.memphis.ccrg.lida.transientepisodicmemory.sdm;

import cern.colt.bitvector.BitVector;
import edu.memphis.ccrg.lida.framework.shared.Node;
import edu.memphis.ccrg.lida.framework.shared.NodeFactory;
import edu.memphis.ccrg.lida.framework.shared.NodeStructure;
import edu.memphis.ccrg.lida.framework.shared.NodeStructureImpl;
import edu.memphis.ccrg.lida.pam.PerceptualAssociativeMemory;

/**
 * This is the class that translates from nodes to boolean vectors and vice-
 * versa. The translation works by assigning a unique index to every node.
 * @author Javier Snaider
 */
public class BasicTranslator implements Translator {

        private int size;
	private PerceptualAssociativeMemory pam;

        /**
         * Translates a bit vector into a node structure. Since the getQuick
         * method in the BitVector class is used, no preconditions are checked.
         * @param data the boolean vector to be translated
         * @return a node structure representing the positions in the bit
         * vector, each node has a unique ID
         * @throws Exception it is not clear why this method throws an exception,
         * since the getQuick method doesn't check any preconditions, and
         * therefore doesn't throw an exception
         */
	public NodeStructure translate(BitVector data) throws Exception {
		NodeStructure ns = new NodeStructureImpl();
		for (int i = 0;i<size;i++){
			if(data.getQuick(i)){
                                Node n= pam.getNode(i);
				ns.addNode(NodeFactory.getInstance().getNode(n));
			}
		}
		return ns;
	}

        /**
         * Translates a node structure into a bit vector. At this point only
         * nodes are being translated, but links, and maybe activations must
         * be also handled.
         * @param structure the node structure to be translated
         * @return a bit vector representing the nodes in the node structure
         * @throws Exception this hould be an IndexOutOfBoundsException instead
         * of an Exception
         */
	public BitVector translate(NodeStructure structure) throws Exception {
		BitVector v = new BitVector (size);
		for (Node n : structure.getNodes()){
                        v.put((int)n.getId(), true);
		}
		return v;
	}

        /**
         * Constructor of the class.
         * @param size the number of positions of the bit vector
         * @param pam the PAM associated with this translator
         */
	public BasicTranslator(int size, PerceptualAssociativeMemory pam) {
		super();
		this.size = size;
		this.pam = pam;
	}
}