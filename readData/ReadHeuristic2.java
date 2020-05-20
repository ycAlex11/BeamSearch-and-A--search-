package amiAssignment.readData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ReadHeuristic2 {

	private File file;
	private Map<String, Double> map = new TreeMap<>();
	

	public Map<String, Double> getMap() {
		return map;
	}

	public void setMap(Map<String, Double> map) {
		this.map = map;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public void readData() {

		InputStream is = null;

		BufferedReader br = null;
		List<String> temp = new ArrayList<>();
		try {
			is = new FileInputStream(file);
			br = new BufferedReader(new InputStreamReader(is));
			String value = br.readLine();

			if (value != null) {

				while (value != null) {
					temp.add(value);
					value = br.readLine();
				}

			}
			for(int i=2;i<temp.size();i++){
				Heuristic hr = new Heuristic();
				String[] array = temp.get(i).split(" ");
				hr.setNode(array[0]);
				hr.setDistance(Double.parseDouble(array[1]));
				map.put(hr.getNode(), hr.getDistance());
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}
}
