import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;

import org.jgraph.JGraph;
import org.jgraph.graph.DefaultCellViewFactory;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.DefaultPort;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.GraphLayoutCache;
import org.jgraph.graph.GraphModel;


public class DrawMap {
	private static ArrayList<DefaultGraphCell> cells;
	private GraphModel model;
	private GraphLayoutCache view;
	private static ArrayList<String> point;
	private int[][] temp;
	
	private void show(Tree tree) {
		// TODO Auto-generated method stub
		 for (int i=2; i<tree.Getfloor(); i++){
	            System.out.print("   ");
	        }
	        if(tree.Getfloor() != 1){
	        	System.out.print("|->");
	        }
	    	System.out.println(tree.Gettext()+" "+tree.Getfloor());
	    	for (int i=0; i<tree.Getchildrennum(); i++){
	    		show(tree.Getchildren(i));
	    	}
	}
	
	public JGraph show2(Tree tree) {
		// 定义一个存放cell的集合类
		cells = new ArrayList<DefaultGraphCell>();
		point = new ArrayList<String>();
		// model用于控制整个模型显示属性等，view用于控制你的图形显示属性，这里都用默认即可
		model = new DefaultGraphModel();
		view = new GraphLayoutCache(model, new DefaultCellViewFactory());
		// JGraph对象
		JGraph graph = new JGraph(model, view);
		temp = new int[10][10];
		makevertex(tree, 0, 0);

		makeedge(tree);
		
		// 将以上定义的cells对象加入graph对象
		Iterator it = cells.iterator();
		while (it.hasNext()) {
			graph.getGraphLayoutCache().insert(it.next());
		}
		// 一些graph对象的简单调整
		// graph.setMoveable(false);//可否移动整个图形
		graph.setDisconnectable(false);//不能移动边的指向,但是可以移动图形
		// graph.setDisconnectOnMove(false);//可否移动整个边,但是在边的源点终点改变后失效
		// { graph.setGridEnabled(true); graph.setGridVisible(true); } // 显示网格
		// graph.setMoveBelowZero(true); //是否允许cell越出左上角.通常设置为false,除非有特殊用处
		graph.setAntiAliased(true);// 圆滑图像线条
		// graph.setSelectionEnabled(false);//能否选择单个cell
		return graph;
	}
	
	private void makevertex(Tree tree, int floor, int whichone) {
		// 建立你的第一个vertex对象
		if (point.contains(tree.Gettext())){
			
		}else {
			//System.out.println(tree.Gettext()+" "+floor+" "+whichone);
			point.add(tree.Gettext());
			cells.add(new DefaultGraphCell(new String(tree.Gettext())));
			int floortemp = floor;
			int whichonetemp = whichone;
			while(temp[floortemp][whichonetemp] == 1){
				floortemp++;
				whichonetemp++;
			}
			temp[floortemp][whichonetemp] = 1;
			GraphConstants.setBounds(cells.get(cells.size()-1).getAttributes(), new Rectangle2D.Double(floortemp*100, whichonetemp*30, 80, 20));
			// 设置颜色和透明属性
			if((cells.size() == 1)||(tree.Getchildrennum() == 0)){
				GraphConstants.setGradientColor(cells.get(cells.size()-1).getAttributes(), Color.red);
			}else {
				GraphConstants.setGradientColor(cells.get(cells.size()-1).getAttributes(), Color.orange);
			}
			GraphConstants.setOpaque(cells.get(cells.size()-1).getAttributes(), true);
			// 为这个vertex加入一个port
			DefaultPort port = new DefaultPort();
			cells.get(cells.size()-1).add(port);
		}
		for (int i=0; i<tree.Getchildrennum(); i++){
			makevertex(tree.Getchildren(i), floor+1, i);
    	}
	}
	
	private static void makeedge(Tree tree) {
		// 建立你的第一个vertex对象
		for (int i=0; i<tree.Getchildrennum(); i++){
			int which1 = point.indexOf(tree.Gettext());
			int which2 = point.indexOf(tree.Getchildren(i).Gettext());
			// 加入一条边，将hello和world的两个port连接起来
			DefaultEdge edge = new DefaultEdge();
			edge.setSource(cells.get(which1).getChildAt(0));
			edge.setTarget(cells.get(which2).getChildAt(0));
			// 将edge加入cell集合类
			cells.add(edge);
			// 为edge设置一个箭头
			int arrow = GraphConstants.ARROW_CLASSIC;
			GraphConstants.setLineEnd(edge.getAttributes(), arrow);
			GraphConstants.setEndFill(edge.getAttributes(), true);
			makeedge(tree.Getchildren(i));
    	}
	}
}
