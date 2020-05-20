package amiAssignment.beamSearch;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import amiAssignment.readData.Heuristic;
import amiAssignment.readData.Nodes;

public class BS5 {
	private List<Nodes> nodes;
	private Map<String, Double> map;
	private List<List<String>> paths;
	private String start;
	private String goal;
	private int k;
	private boolean flag = false;
	private List<String> resultPath = new ArrayList<>();
	private List<List<Integer>> backCounter = new ArrayList<>();
	private int level = 0;

	public void setBackCounter(List<List<Integer>> backCounter) {
		this.backCounter = backCounter;

	}

	public void setK(int k) {
		this.k = k;
	}

	public void setNodes(List<Nodes> nodes) {
		this.nodes = nodes;
	}


	public void setMap(Map<String, Double> map) {
		this.map = map;
	}

	public List<List<String>> getPaths() {
		return paths;
	}

	public void setPaths(List<List<String>> paths) {
		this.paths = paths;
		beamSearch();
	}

	public void setStart(String start) {
		this.start = start;
	}

	public void setGoal(String goal) {
		this.goal = goal;
	}

	private void buildRoot() {
		Set<String> neighbors = new TreeSet<>();
		for (int i = 0; i < nodes.size(); i++) {
			if (nodes.get(i).getNode().equals(start)) {
				if (nodes.get(i).getNeiNode().equals(goal) == false) {
					neighbors.add(nodes.get(i).getNeiNode());
				} else {
					List<String> temp = new ArrayList<>();
					temp.add(start);
					temp.add(nodes.get(i).getNeiNode());
					paths.add(temp);
					flag = true;
					return;
				}
			} else if (nodes.get(i).getNeiNode().equals(start)) {
				if (nodes.get(i).getNode().equals(goal) == false) {
					neighbors.add(nodes.get(i).getNode());
				} else {
					List<String> temp = new ArrayList<>();
					temp.add(start);
					temp.add(nodes.get(i).getNode());
					paths.add(temp);
					flag = true;
					return;
				}
			}
		}

		List<String> successors = new ArrayList<>();
		findOptimal(neighbors, successors);
		int length = 0;
		if (k >= successors.size()) {
			length = successors.size();
		} else {
			length = k;
		}
		for (int i = 0; i < length; i++) {
			List<String> temp = new ArrayList<>();
			temp.add(start);
			temp.add(successors.get(i));
			paths.add(temp);

		}
		List<Integer> bcTemp = new ArrayList<>();
		bcTemp.add(length);
		backCounter.add(bcTemp);
		level++;

	}

	private void buildRootAgain() {
		Set<String> neighbors = new TreeSet<>();
		for (int i = 0; i < nodes.size(); i++) {
			if (nodes.get(i).getNode().equals(start)) {
				neighbors.add(nodes.get(i).getNeiNode());
			} else if (nodes.get(i).getNeiNode().equals(start)) {
				neighbors.add(nodes.get(i).getNode());
			}
		}
		List<String> successors = new ArrayList<>();
		findOptimal(neighbors, successors);
		int length = 0;
		if (k + backCounter.get(0).get(0) >= successors.size()) {
			length = successors.size();
		} else {
			length = k + backCounter.get(0).get(0);
		}
		for (int i = backCounter.get(0).get(0); i < length; i++) {
			List<String> temp = new ArrayList<>();
			temp.add(start);
			temp.add(successors.get(i));
			paths.add(temp);
		}
		backCounter.get(0).set(0, length);
		level++;

	}

	private void beamSearch() {
		buildRoot();
		while (flag == false) {
			int count = 0;
			boolean goback = true;
			List<List<String>> temp = new ArrayList<>();
			List<Integer> bsTemp = new ArrayList<>();
			copyList(temp);
			for (int j = 0; j < temp.size(); j++) {
				Set<String> neighbors = new TreeSet<>();
				if (temp.get(j).size() > level) {
					for (int i = 0; i < nodes.size(); i++) {
						if (temp.get(j).get(temp.get(j).size() - 1).equals(nodes.get(i).getNode())) {
							if (nodes.get(i).getNeiNode().equals(goal) == false) {
								if (checkNode(temp.get(j), nodes.get(i).getNeiNode()) == false) {
									neighbors.add(nodes.get(i).getNeiNode());
									goback = false;
								}
							} else {
								paths.get(j).add(nodes.get(i).getNeiNode());
								flag = true;
								return;
							}

						} else if (temp.get(j).get(temp.get(j).size() - 1).equals(nodes.get(i).getNeiNode())) {
							if (nodes.get(i).getNode().equals(goal) == false) {
								if (checkNode(temp.get(j), nodes.get(i).getNode()) == false) {
									neighbors.add(nodes.get(i).getNode());
									goback = false;
								}
							} else {
								paths.get(j).add(nodes.get(i).getNode());
								flag = true;
								return;
							}
						}
					}
				}

				List<String> optimals = new ArrayList<>();
				findOptimal(neighbors, optimals);
				int length = 0;

				if (k >= optimals.size()) {
					length = optimals.size();
				} else {
					length = k;
				}

				for (int l = 0; l < length; l++) {
					if (l == 0) {
						paths.get(j + count).add(optimals.get(l));

					} else {
						count++;
						List<String> tempNodes = new ArrayList<>();
						for (int i = 0; i < temp.get(j).size(); i++) {
							tempNodes.add(temp.get(j).get(i));
						}
						tempNodes.add(optimals.get(l));
						paths.add(j + count, tempNodes);

					}
				}

				bsTemp.add(j, length);

			}
			backCounter.add(level, bsTemp);
			level++;
			while (goback == true) {
				level--;
				backCounter.remove(level);
				if (level - 1 != 0) {
					backOneLevel(temp);
					delete(temp);
					copyList(temp);
					count = 0;
					for (int j = 0; j < backCounter.get(level - 1).size(); j++) {
						Set<String> children = new TreeSet<>();
						if (backCounter.get(level - 1).get(j) != 0 && (backCounter.get(level - 1).get(j) % k == 0)) {

							for (int i = 0; i < nodes.size(); i++) {
								if (temp.get(j).get(temp.get(j).size() - 1).equals(nodes.get(i).getNode())) {
									if (checkNode(temp.get(j), nodes.get(i).getNeiNode()) == false) {
										children.add(nodes.get(i).getNeiNode());
									}

								} else if (temp.get(j).get(temp.get(j).size() - 1).equals(nodes.get(i).getNeiNode())) {

									if (checkNode(temp.get(j), nodes.get(i).getNode()) == false) {
										children.add(nodes.get(i).getNode());

									}
								}
							}

							if (children.size() > backCounter.get(level - 1).get(j)) {
								int size = 0;
								goback = false;
								if (k + backCounter.get(level - 1).get(j) >= children.size()) {
									size = children.size();
								} else {
									size = k + backCounter.get(level - 1).get(j);
								}
								List<String> sorting = new ArrayList<>();
								findOptimal(children, sorting);
								int localcount = 0;
								for (int i = backCounter.get(level - 1).get(j); i < size; i++) {
									if (localcount == 0) {
										paths.get(j + count).add(sorting.get(i));
										localcount++;
									} else {
										
										List<String> tempNodes = new ArrayList<>();
										for (int l = 0; l < temp.get(j).size(); l++) {
											tempNodes.add(temp.get(j).get(l));
										}
										tempNodes.add(sorting.get(i));
										paths.add(j + count + localcount, tempNodes);
										localcount++;

									}
								}
								count = count + (size - backCounter.get(level - 1).get(j) - 1);
								backCounter.get(level - 1).set(j, size);

							}

						}
					}
				} else {

					level--;
					delete(paths);
					buildRootAgain();
					goback=false;
				}
			}
		}

	}
	//need change think level!!!
	private void backOneLevel(List<List<String>> tempList) {
		delete(paths);
		int i = 0;
		int count = 0;
		while (count < backCounter.get(level - 1).size()) {
			if (tempList.get(i).size()<=level) {
				copyThePath(tempList, i);
			} else {
				if (k == 1) {
					copyByDelete(tempList, i);
				} else if (k == 2) {
					if (backCounter.get(level - 1).get(count) % k != 0) {
						copyByDelete(tempList, i);
					} else {
						copyByDelete(tempList, i);
						i++;
					}
				} else if (k == 3) {
					int index = 0;
					if (backCounter.get(level - 1).get(count) <= 3) {
						//need check 0
						if (backCounter.get(level - 1).get(count) == 1) {
							copyByDelete(tempList, i);
						} else {
							copyByDelete(tempList, i);
							i = i + backCounter.get(level - 1).get(count) - 1;
						}
					} else {
						index = backCounter.get(level - 1).get(count);
						while (index > 3) {

							index = index - 3;
						}
						if (index == 1) {
							copyByDelete(tempList, i);
						} else {
							copyByDelete(tempList, i);
							i = i + index - 1;
						}
					}
				}
			}
			i++;
			count++;
		}
	}

	private void copyThePath(List<List<String>> tempList, int i) {
		List<String> temp = new ArrayList<>();
		for (int j = 0; j < tempList.get(i).size(); j++) {
			temp.add(tempList.get(i).get(j));
		}
		paths.add(temp);
	}

	private void copyByDelete(List<List<String>> tempList, int i) {
		List<String> temp = new ArrayList<>();
		for (int j = 0; j < tempList.get(i).size() - 1; j++) {
			temp.add(tempList.get(i).get(j));
		}
		paths.add(temp);
	}

	private void copyList(List<List<String>> temp) {
		for (int i = 0; i < paths.size(); i++) {
			List<String> templist = new ArrayList<>();
			for (int j = 0; j < paths.get(i).size(); j++) {
				templist.add(paths.get(i).get(j));
			}
			temp.add(i, templist);
		}
	}

	private void delete(List<List<String>> temp) {
		int length = temp.size();
		for (int i = 0; i < length; i++) {
			temp.remove(0);
		}
	}

	private void findOptimal(Set<String> set, List<String> listOptimal) {
		List<Heuristic> temp = new ArrayList<>();
		for (String s : set) {
	
			Heuristic heuristic = new Heuristic();
			heuristic.setNode(s);
			heuristic.setDistance(map.get(s));
			temp.add(heuristic);
		}
		sortList(temp, listOptimal);
	}

	private boolean checkNode(List<String> list, String node) {
		boolean include = false;
		for (int i = 0; i < list.size(); i++) {
			if (node.equals(list.get(i))) {
				include = true;
			}
		}

		return include;

	}

	public List<String> findResultPath() {
		for (int i = 0; i < paths.size(); i++) {
			if (paths.get(i).get(paths.get(i).size() - 1).equals(goal)) {
				for (int j = 0; j < paths.get(i).size(); j++) {
					resultPath.add(paths.get(i).get(j));
				}
			}
		}
		return resultPath;
	}

	public double findResult() {

		double distance = 0;
		for (int i = 0; i < resultPath.size() - 1; i++) {
			for (int j = 0; j < nodes.size(); j++) {
				if ((resultPath.get(i).equals(nodes.get(j).getNode())
						&& resultPath.get(i + 1).equals(nodes.get(j).getNeiNode()))
						|| (resultPath.get(i).equals(nodes.get(j).getNeiNode())
								&& resultPath.get(i + 1).equals(nodes.get(j).getNode()))) {
					distance = distance + nodes.get(j).getDist();
					break;
				}
			}
		}
		return distance;
	}

	private void sortList(List<Heuristic> list, List<String> listOptimal) {
		int length = list.size();
		for (int i = 0; i < length; i++) {
			String node = list.get(0).getNode();
			int count = 0;
			double min = list.get(0).getDistance();
			for (int j = 0; j < list.size(); j++) {
				if (min > list.get(j).getDistance()) {
					min = list.get(j).getDistance();
					node = list.get(j).getNode();
					count = j;
				}

			}
			listOptimal.add(node);
			list.remove(count);
		}
	}
}
