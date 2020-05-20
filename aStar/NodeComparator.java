package amiAssignment.aStar;

import java.util.Comparator;

public class NodeComparator implements Comparator<Node>{

	@Override
	public int compare(Node o1, Node o2) {
		int result = 0;
		if(o1.getNode().compareTo(o2.getNode())==0){
			result =0;
		}else{
			if(o1.getHerDis()-o2.getHerDis()==0){
				result= o2.getNode().compareTo(o1.getNode());
			}else{
				result = (int) (o1.getHerDis()-o2.getHerDis());
			}
			
		}
		return result;
	}

}
