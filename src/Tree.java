

import java.util.ArrayList;

public class Tree {
	/**节点名称**/
	private String text;    
	/**子节点**/
	private int floor;    	
	private ArrayList<Tree> children;
	
	public Tree(String name , int floor) {
		// TODO Auto-generated constructor stub
		this.text = name;
		this.floor = floor;
		this.children = new ArrayList<Tree>();
	}
	
	public String Gettext() {
		return text;
	}
	
	public int Getfloor() {
		return floor;
	}
	
	public int Getchildrennum() {
		return children.size();
	}
	
	public Tree Getchildren(String name) {
		int num = children.size();
		for (int i=0;i<num;i++){
			if (name.equals(children.get(i).text)){
				return children.get(i);
			}
		}
		return null;
	}
	
	public Tree Getchildren(int i) {
		return children.get(i);
	}
	
	public void Setchildren(String name , int floor) {
		Tree childrenpoint = new Tree(name , floor);
		children.add(childrenpoint);
		return ;
	}
	
	public void Removechild(Tree name) {
		children.remove(name);
		return ;
	}
}
