package amiAssignment.readData;

public class Nodes {
	private String node;
	private String neiNode;
	private double dist;
	public String getNode() {
		return node;
	}
	public void setNode(String node) {
		this.node = node;
	}
	
	public double getDist() {
		return dist;
	}
	public void setDist(double d) {
		this.dist = d;
	}
	public String getNeiNode() {
		return neiNode;
	}
	public void setNeiNode(String neiNode) {
		this.neiNode = neiNode;
	}
	@Override
	public String toString() {
		return "Node [node=" + node + ", neiNode=" + neiNode + ", dist=" + dist + "]";
	}

	
	

}
