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

public class ReadNodes {
	
	private File file;
	private List<Nodes> list = new ArrayList<>();

	


	public void setFile(File file) {
		this.file = file;
	}

	public List<Nodes> getList() {
		return list;
	}
	
	public void readData(){
		
		InputStream is = null;
		
		BufferedReader br= null;
		try {
			is = new FileInputStream(file);
			br = new BufferedReader(new InputStreamReader(is));
			String value = br.readLine();
			
			if(value !=null){
				
				while(value!= null){
					String[] array = value.split(" ");
					Nodes node = new Nodes();
					node.setNode(array[0]);
					node.setNeiNode(array[1]);
					node.setDist(Double.parseDouble(array[2]));
					list.add(node);
					value = br.readLine();
				}
				
			}
			
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(br!=null){
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
