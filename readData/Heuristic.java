package amiAssignment.readData;

public class Heuristic {
	private String node;
	private double distance;
	public String getNode() {
		return node;
	}
	public void setNode(String node) {
		this.node = node;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	@Override
	public String toString() {
		return "Heuristic [node=" + node + ", distance=" + distance + "]";
	}
	

}
