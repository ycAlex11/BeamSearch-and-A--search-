package amiAssignment.aStar;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.CountDownLatch;

import amiAssignment.readData.Nodes;

public class AStarSearch {

	private List<Nodes> nodes;
	private Map<String, Double> map;
	private String start;
	private String goal;
	private boolean flag = false;
	private int nodeCounter = 1;
	private List<List<Node>> paths = new ArrayList<>();

	public List<List<Node>> getPaths() {
		return paths;
	}

	public void setPaths(List<List<Node>> paths) {
		this.paths = paths;
		buildRoot();
	}

	public void setNodes(List<Nodes> nodes) {
		this.nodes = nodes;
	}

	public void setMap(Map<String, Double> map) {
		this.map = map;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public void setGoal(String goal) {
		this.goal = goal;
	}

	private void buildRoot() {
		Node root = new Node();
		root.setNode(start);
		root.setHerDis(map.get(start));
		NodeComparator nc = new NodeComparator();
		Set<Node> children = new TreeSet<>(nc);
		for (int i = 0; i < nodes.size(); i++) {
			if (start.equals(nodes.get(i).getNode())) {
				Node node = new Node();
				node.setNode(nodes.get(i).getNeiNode());
				if (nodes.get(i).getNeiNode().equals(goal)) {
					node.setHerDis(nodes.get(i).getDist());
					flag = true;
				} else {
					node.setHerDis(nodes.get(i).getDist() + map.get(nodes.get(i).getNeiNode()));
				}

				children.add(node);

			} else if (start.equals(nodes.get(i).getNeiNode())) {
				Node node = new Node();
				node.setNode(nodes.get(i).getNode());
				if (nodes.get(i).getNode().equals(goal)) {
					node.setHerDis(nodes.get(i).getDist());
					flag = true;
				} else {
					node.setHerDis(nodes.get(i).getDist() + map.get(nodes.get(i).getNode()));
				}

				children.add(node);
			}
		}

		addRootToPath(children, root);
	}

	private void addRootToPath(Set<Node> children, Node root) {
		List<Node> list = new ArrayList<>();
		for (Node n : children) {
			List<Node> temp = new ArrayList<>();
			temp.add(root);
			temp.add(n);
			paths.add(temp);
			list.add(n);
			nodeCounter++;

		}

		if (flag == false) {
			double length = 0;
			for (int i = 0; i < nodes.size(); i++) {
				if ((start.equals(nodes.get(i).getNode()) && list.get(0).getNode().equals(nodes.get(i).getNeiNode()))
						|| (start.equals(nodes.get(i).getNeiNode())
								&& list.get(0).getNode().equals(nodes.get(i).getNode()))) {
					length = nodes.get(i).getDist();
					break;
				}

			}
			aStarSearch(length, 0);
		} else {
			double min = paths.get(0).get(1).getHerDis();
			int index =0;
			for (int i = 1; i < paths.size(); i++) {
				if (paths.get(i).get(1).getHerDis() < min) {
					min = paths.get(i).get(1).getHerDis();
					flag = false;
					index = i;
				}
			}
			
			if(flag == false){
				double newLength = min- map.get(paths.get(index).get(paths.get(index).size()-1));
				aStarSearch(newLength, index);
			}
		}

	}

	private void aStarSearch(double length, int index) {
		List<Node> list = paths.get(index);
		int count = 0;
		NodeComparator nc = new NodeComparator();
		Set<Node> children = new TreeSet<>(nc);
		for (int i = 0; i < nodes.size(); i++) {
			if (list.get(list.size() - 1).getNode().equals(nodes.get(i).getNode())
					&& checkNode(list, nodes.get(i).getNeiNode()) == false) {
				Node node = new Node();
				node.setNode(nodes.get(i).getNeiNode());
				if (nodes.get(i).getNeiNode().equals(goal)) {
					node.setHerDis(length + nodes.get(i).getDist());
					flag = true;
				} else {
					node.setHerDis(length + nodes.get(i).getDist() + map.get(nodes.get(i).getNeiNode()));
				}
				children.add(node);

			} else if (list.get(list.size() - 1).getNode().equals(nodes.get(i).getNeiNode())
					&& checkNode(list, nodes.get(i).getNode()) == false) {
				Node node = new Node();
				node.setNode(nodes.get(i).getNode());
				if (nodes.get(i).getNode().equals(goal)) {
					node.setHerDis(length + nodes.get(i).getDist());
					flag=true;
				} else {
					node.setHerDis(length + nodes.get(i).getDist() + map.get(nodes.get(i).getNode()));
				}

				children.add(node);

			}
		}
		List<Node> temp = new ArrayList<>();
		for (Node n : children) {
			temp.add(n);

		}
		for (int i = 0; i < temp.size(); i++) {
			if (i == 0) {
				paths.get(index).add(temp.get(i));
			} else {
				count++;
				List<Node> listTemp = new ArrayList<>();
				for (int j = 0; j < paths.get(index).size() - 1; j++) {
					listTemp.add(paths.get(index).get(j));
				}
				listTemp.add(temp.get(i));
				paths.add(index + count, listTemp);
			}
		}
		double min = paths.get(index).get(paths.get(index).size()-1).getHerDis();
		int newIndex=index;
		double newLength = 0;
		if(flag == true){
			
			for(int i=0;i<paths.size();i++){
				if(i!=index){
					if(paths.get(i).get(paths.get(i).size()-1).getHerDis()<min){
						flag =false;
						min =paths.get(i).get(paths.get(i).size()-1).getHerDis();
						newIndex=i;
					}
				}else{
					i=i+temp.size()-1;
				}
			}
			newLength= min- map.get(paths.get(newIndex).get(paths.get(newIndex).size()-1).getNode());
			
		}else{
		
			for(int i=0;i<paths.size();i++){
				if(i!=index){
					if(paths.get(i).get(paths.get(i).size()-1).getHerDis()<min){
						min =paths.get(i).get(paths.get(i).size()-1).getHerDis();
						
						newIndex=i;
					}
				}else{
					i=i+temp.size()-1;
				}
			}
			
			newLength= min- map.get(paths.get(newIndex).get(paths.get(newIndex).size()-1).getNode());
			
		}
		if(flag==false){
			aStarSearch(newLength, newIndex);
		}
	
		
	}

	private boolean checkNode(List<Node> list, String node) {
		boolean include = false;
		for (int i = 0; i < list.size(); i++) {
			if (node.equals(list.get(i).getNode())) {
				include = true;
			}
		}

		return include;

	}

}
