package amiAssignment.aStar;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class TestSet {

	public static void main(String[] args) {
		Node node2 = new Node("java",50);
		Node node = new Node("ucp",50);
		Node node5 = new Node("ucp",60);
		Node node3 = new Node("C",40);
		Node node4 = new Node("d",60);
		NodeComparator nc = new NodeComparator();
		
		Set<Node> set = new TreeSet<>(nc);
		set.add(node);
		set.add(node2);
		set.add(node3);
		set.add(node4);
		set.add(node5);
		for(Node s:set){
			System.out.println(s);
		}
	}
}
