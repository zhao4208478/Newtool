

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;




public class Treemake {
	public Treemake() {}
	
	private boolean real;
	
	private ArrayList<String> samepoint1;
	private ArrayList<String> samepoint2;
	
	private ArrayList<String> highpoint1;
	private ArrayList<String> highpoint2;
	
	private ArrayList<String> point;
	private ArrayList<Integer> numcount;
	
	public Tree treemake(Sql sql , String name , int floor , Double condence){		//实体对上位词搜索，对第一层上位词的之间关系搜索
		// TODO Auto-generated method stub
		
		highpoint1 = new ArrayList<String>();
		highpoint2 = new ArrayList<String>();
		
		samepoint1 = new ArrayList<String>();
		samepoint2 = new ArrayList<String>();
        
        point = new ArrayList<String>();
        numcount = new ArrayList<Integer>();
        
    	int logo = 0;
    	floor++;
        sql.Select(name);
        ArrayList<String> treepoint = sql.Getresult();
        
        sql.Select2(name);
        treepoint.addAll(sql.Getresult2());
        

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
    	int size = treepoint.size();
    	
        ArrayList<String> treepoint2 = treepoint;
       // System.out.println("查找上下位词");
        checkhigh(sql , treepoint2);	//查找上下位词
      //  System.out.println("查找同义同位词");
        treepoint2 = treepoint;
    	
        checksame(sql , treepoint2);	//查找同义同位词
    	for (int i = 0; i < samepoint2.size(); i++) {	
    		try {
    			treepoint.remove(samepoint2.get(i));
    		//	System.out.println(treepoint.size());
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

    	for (int i = 0; i < highpoint2.size(); i++) {
    		try {
    			treepoint.remove(highpoint2.get(i));
    		//	System.out.println(treepoint.size());
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
    	
    	size = treepoint.size();
    	
    	Tree tree = new Tree(name, floor);
    	
    	for (int i=0; i<size; i++){
    		if ((treepoint.get(i).equals("人物")|| treepoint.get(i).equals("人")|| treepoint.get(i).equals("物"))&& size>1){
    			
        	}else{
        		treemake2(sql , treepoint.get(i) , floor , tree);
    		}
    	}
    	find(tree);
        return tree;
	}
	
	public void treemake2(Sql sql , String name , int floor , Tree tree){		//子树（上位词）的上位词搜索
		// TODO Auto-generated method stub
    	floor++;

        sql.Select2(name);
        ArrayList<String> treepoint = sql.Getresult2();
        int size = treepoint.size();
        
        checksame(sql , treepoint);	//查找同义同位词
        
        size = treepoint.size();
        for (int i=0; i<size; i++){
        	 for (int j=samepoint2.size()-1; j>=0; j--){
             	if(treepoint.get(i).equals(samepoint2.get(j))&&!treepoint.get(i).equals("球星")){
             		//System.out.println(treepoint.get(i)+" "+samepoint1.get(j));
             		treepoint.set(i, samepoint1.get(j));
             	}	
             }
        }
        ArrayList<String> temp = new ArrayList<String>();
        for (int i=0; i<size-1; i++){
       	 	for (int j=i+1; j<=size-1; j++){
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
        

        point.add(name);
        numcount.add(floor);

        size = treepoint.size();

    	tree.Setchildren(name, floor);
    	
    	if (name.equals("人物")|| name.equals("人")|| name.equals("物") || name.equals("抽象事物")||floor > 20){
    		return ;
    	}
    	
        if (size == 0){
        	if(name.endsWith("人")){
        		treepoint.add("人");
        	}else {
        		for (int i=name.length()-2; i > 0; i--){	
                	String get = name.substring(name.length()-i);
                	sql.Select2(get);
            		if (sql.Getresult2().size() > 0){
            			treepoint.add(get);
            			break;
            		}
            	}
			}
        }
        size = treepoint.size();
        
    	for (int i=0; i<size; i++){
    		if ((treepoint.get(i).equals("人物")|| treepoint.get(i).equals("人")|| treepoint.get(i).equals("物"))&& size>1){
        	}else{
        		treemake2(sql , treepoint.get(i) , floor , tree.Getchildren(name));
    		}
    	}
        
	}
	
	public void checksame(Sql sql , ArrayList<String> headpoint){		//同义词查询
		// TODO Auto-generated method stub
		int size = headpoint.size();
    	for (int i=0; i<size-1; i++){
    		for (int j=i+1; j<=size-1; j++){
    			try {
    			// TODO: handle exception
    			boolean decide = sql.Select3( headpoint.get(i) , headpoint.get(j));
    			if (decide) {
    				samepoint1.add(headpoint.get(i));
    				samepoint2.add(headpoint.get(j));
    			}
				} catch (Exception e) {
				
				}
    		}
    	}
	}

	public void checkhigh(Sql sql , ArrayList<String> headpoint){		//同位上位词查询

		// TODO Auto-generated method stub
		int size = headpoint.size();
    	for (int i=0; i<size; i++){
    		for (int j=0; j<size; j++){
    			try {
    			// TODO: handle exception
    			if ((i == j)||(highpoint1.contains(headpoint.get(j))&&highpoint2.contains(headpoint.get(i)))){
    				
    			}else {
    				boolean decide = sql.Select4( headpoint.get(i) , headpoint.get(j));
    				if (decide) {
    					highpoint1.add(headpoint.get(i));
    					highpoint2.add(headpoint.get(j));
    				}
    			}
    			} catch (Exception e) {	
    			}
    		}
    	}
	}
	
	private void find(Tree tree) {
		// TODO Auto-generated method stub
	    	for (int i=0; i<tree.Getchildrennum(); i++){
	    		real = false;
	    		for (int j=0; j<tree.Getchildrennum(); j++){
	    			if(real == true){
	    				break;
	    			}
	    			if (i != j){
	    				find2(tree.Getchildren(j),tree.Getchildren(i).Gettext() );
	    			}
	    		}
	    		if(real){
	    			tree.Removechild(tree.Getchildren(i));
	    		}
	    	}
	    	for (int i=0; i<tree.Getchildrennum(); i++){
	    		find(tree.Getchildren(i));
	    	}
	}

	private void find2(Tree tree , String name) {
		// TODO Auto-generated method stub
	    	for (int i=0; i<tree.Getchildrennum(); i++){
	    		if (real == true){
	    			return ;
	    		}else{
	    			if (tree.Getchildren(i).Gettext().equals(name)){
	    				//System.out.println(tree.Gettext()+" "+name);
	    				real = true;
	    				return ;
	    			}
	    			find2(tree.Getchildren(i), name);
	    		}
	    	}
	}
	
}
