package amiAssignment.aStar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import amiAssignment.readData.Nodes;
import amiAssignment.readData.ReadHeuristic2;
import amiAssignment.readData.ReadNodes;

public class Test {

	public static void main(String[] args) {
		
		File file = new File("d:/testCase/tutNode.txt");
		File file2 = new File("D:/testCase/tutHer.txt");
		ReadNodes rn = new ReadNodes();
		rn.setFile(file);
		rn.readData();
		List<Nodes> nodes = rn.getList();;
		ReadHeuristic2 rh =new ReadHeuristic2();
		rh.setFile(file2);
		rh.readData();
		Map<String, Double> map =rh.getMap();
		AStarSearch aStarSearch = new AStarSearch();
		aStarSearch.setStart("A");
		aStarSearch.setGoal("J");
		aStarSearch.setNodes(nodes);
		aStarSearch.setMap(map);
		aStarSearch.setPaths(new ArrayList<>());
		List<List<Node>> paths = aStarSearch.getPaths();
		System.out.println(paths.size());
		for(int i=0;i<paths.size();i++){
			for(int j=0;j<paths.get(i).size();j++){
				System.out.print(paths.get(i).get(j)+"  ");
			}
			System.out.println();
		}
	}
}
