package amiAssignment.beamSearch;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import amiAssignment.readData.Heuristic;
import amiAssignment.readData.Nodes;
import amiAssignment.readData.ReadHeuristic2;
import amiAssignment.readData.ReadNodes;

public class Test2 {

	public static void main(String[] args) {

		File file = new File("d:/testCase/node.txt");
		File file2 = new File("D:/testCase/her.txt");
		ReadNodes rn = new ReadNodes();
		rn.setFile(file);
		rn.readData();
		List<Nodes> nodes = rn.getList();;
		ReadHeuristic2 rh =new ReadHeuristic2();
		rh.setFile(file2);
		rh.readData();
		Map<String, Double> map =rh.getMap();
		BS5 bs = new BS5();
		bs.setK(1);
		bs.setStart("A");
		bs.setGoal("GOAL");
		//bs.setGoal("Z");
		
		bs.setNodes(nodes);
		bs.setMap(map);
		List<List<String>> paths = new ArrayList<>();
		bs.setPaths(paths);
		bs.setBackCounter(new ArrayList<>());
		//List<List<String>> paths = bs.getPaths();
		List<String> resultPath = bs.findResultPath();
		showList(paths);
		showResult(resultPath);
		double result = bs.findResult();
		System.out.println();
		System.out.println("the result is:"+result);
		
		
	}
	private static void showResult(List<String> resultPath) {
		System.out.println("the result path is:");
		for(int i =0;i<resultPath.size();i++){
			System.out.print(resultPath.get(i)+ "  ");
		}
		
	}
	private static void showList(List<List<String>> paths) {
		System.out.println("the whole paths are:");
		for(int i=0;i<paths.size();i++){
			for(int j = 0;j<paths.get(i).size();j++){
				System.out.print(paths.get(i).get(j)+"  ");
			}
			System.out.println();
		}
		
	}
}
