package amiAssignment.aStar;

import java.io.Serializable;

public class Node implements Serializable {

	private String node;
	private double herDis;
	
	public Node() {
		
	}
	public Node(String node, double herDis) {
		super();
		this.node = node;
		this.herDis = herDis;
	}
	public String getNode() {
		return node;
	}
	public void setNode(String node) {
		this.node = node;
	}
	public double getHerDis() {
		return herDis;
	}
	public void setHerDis(double herDis) {
		this.herDis = herDis;
	}


	@Override
	public String toString() {
		return "Node [node=" + node + ", herDis=" + herDis + "]";
	}
	
}
