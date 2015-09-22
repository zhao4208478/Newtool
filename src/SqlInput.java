import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.DefaultListModel;


public class SqlInput {
	private String name;
	private File file;
	private DefaultListModel model;
	private ArrayList<String> highword;
	private String showsame;
	private String showchild;
	private String Gettreepoint;
	
	public SqlInput(String name) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.file = new File("sql\\"+name);
	}
	
	public boolean exists() {
		return file.exists();
	}
	
	public DefaultListModel GetModel() {
		System.out.print(model);
		return model;
	}
	
	public ArrayList<String> GetHighword() {
		System.out.print(highword);
		return highword;
	}
	
	public String GetSame() {
		// TODO Auto-generated method stub
		System.out.print(showsame);
		return showsame;
	}
	
	public String GetChild() {
		// TODO Auto-generated method stub
		System.out.print(showchild);
		return showchild;
	}

	public String GetTreepoint() {
		// TODO Auto-generated method stub
		System.out.print(Gettreepoint);
		return Gettreepoint;
	}
	
	public void find() throws IOException {
         InputStreamReader read= new InputStreamReader(new FileInputStream(file), "UTF-8");
         BufferedReader br = new BufferedReader(read);
         String temp=null;
         highword = new ArrayList<String>();
         model = new DefaultListModel();
         int logo = 0;
         temp=br.readLine();
         while(temp!=null){
             switch (logo) {
			case 0:
				System.out.print(temp);
				if (temp.equals("上下位关系：")){
					System.out.print(1);
					logo = 1;
				}
				break;
			case 1:
				if (!temp.equals("同义关系：")){
					String[] temp2 = temp.split(",");
					highword.add(temp2[1]);
					temp = temp.replaceAll(",","->");
					temp = temp + "->|";
					model.addElement(temp);
				}else {
					System.out.print(2);
					logo = 2;
				}
			case 2:
				if (!temp.equals("上位关系：")){
					showsame = temp;
				}else {
					System.out.print(3);
					logo = 3;
				}
				break;
			case 3:
				if (!temp.equals("下位关系：")){
					Gettreepoint = temp;
				}else {System.out.print(4);
					logo = 4;
				}
				break;
			case 4:
				showchild = temp;
				System.out.print(5);
				logo = 5;
				break;
			default:
				break;
			}
            temp=br.readLine();
         }
         read.close();
         br.close();
	}

}
