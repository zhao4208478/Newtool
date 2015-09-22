import java.util.ArrayList;


public class Find {
	private ArrayList<String> treepoint;
	private Sql sql;
	
	public Find(Sql sql) {
		this.sql = sql;
	}
	
	public ArrayList<String> Gettreepoint() {
		// TODO Auto-generated method stub
        return treepoint;
	}	
	
	public ArrayList<String> Findonefather( String name) {
		// TODO Auto-generated method stub
		sql.Select7(name);
        return sql.Getresult7();
	}
	
	public ArrayList<String> Findsame( String name) {
		// TODO Auto-generated method stub
		sql.Select5(name);
        return sql.Getresult5();
	}

	public ArrayList<String> Findchild( String name) {
		// TODO Auto-generated method stub
		sql.Select6(name);
        return sql.Getresult6();
	}
	
	public ArrayList<String> Findparent(String name, int i) {
		// TODO Auto-generated method stub
	//	if (i > 1){
	//	FindEasy2(name);
	//	}else {
			FindEasy(name);
	//	}
        return treepoint;
	}	
	
	public ArrayList<ArrayList<String>> Find(String name) {
		// TODO Auto-generated method stub
		FindEasy(name);
    	int size = treepoint.size();
        ArrayList<ArrayList<String>> output = new ArrayList<ArrayList<String>>();
        ArrayList<String> showline = new ArrayList<String>();
    	showline.add(name);
        for (int i=0; i<size; i++){
        	Find2( treepoint.get(i) , showline , output );
    	}
        return output;
	}
	
	private void Find2(String name ,ArrayList<String> showline ,ArrayList<ArrayList<String>> output) {
		// TODO Auto-generated method stub
		sql.Select2(name);
        ArrayList<String> treepoint = sql.Getresult2();
    	ArrayList<String> temp = new ArrayList<String>();
        int size = treepoint.size();
        showline.add(name);
        if (size < 1){
        	for (int i=0; i<showline.size(); i++){
        		//System.out.print(showline.get(i)+"->");
        		temp.add(showline.get(i));
        	}
        	//System.out.println("|");
        	output.add(temp);
            //System.out.println(output);
        	//System.out.println("_______________________________________");
        }else{
        	//System.out.println(size);
        }
        for (int i=0; i<size; i++){
        	Find2(treepoint.get(i) , showline , output);
    	}
        showline.remove(name);
	}
	
	private void FindEasy(String name) {
		sql.Select(name);
        treepoint = sql.Getresult();
        sql.Select2(name);
        treepoint.addAll(sql.Getresult2());
        
    //    for (int i=0; i<treepoint.size(); i++){
      //  	System.out.print(treepoint.get(i)+" ");
       // }
      //  System.out.println();
        
        ArrayList<String> temp = new ArrayList<String>();
        for (int i=0; i<treepoint.size()-1; i++){
       	 	for (int j=i+1; j<=treepoint.size()-1; j++){
            	if(treepoint.get(i).equals(treepoint.get(j))){
            		//System.out.println(treepoint.get(i));
            		temp.add(treepoint.get(i));
            		break;
            	}
            }
        }

        for (int i=0; i<temp.size(); i++){
        	treepoint.remove(temp.get(i));
        }
        
   //     for (int i=0; i<treepoint.size(); i++){
    //    	System.out.print(treepoint.get(i)+" ");
     //   }
      //  System.out.println();
	}

}
