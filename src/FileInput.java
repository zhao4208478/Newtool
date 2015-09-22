import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.DefaultListModel;


public class FileInput {
	private File file;

	public FileInput(String name) {
		// TODO Auto-generated constructor stub
		this.file = new File(name);
	}
	
	public boolean exists() {
		return file.exists();
	}
	
	public ArrayList<String> find(ArrayList<String> queue) throws IOException {
		HashSet<String> set = new HashSet<String>(queue);  
        InputStreamReader read= new InputStreamReader(new FileInputStream(file), "gbk");
        BufferedReader br = new BufferedReader(read);
        String temp=null;
        temp=br.readLine();
        while(temp!=null){
        	if (!set.contains(temp)){
        		set.add(temp);
            	System.out.println(temp);
        	}
        	temp=br.readLine();
        }
        read.close();
        br.close();
		return new ArrayList<String>(set);
	}
}
