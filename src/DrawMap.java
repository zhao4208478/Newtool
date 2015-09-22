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
		// ����һ�����cell�ļ�����
		cells = new ArrayList<DefaultGraphCell>();
		point = new ArrayList<String>();
		// model���ڿ�������ģ����ʾ���Եȣ�view���ڿ������ͼ����ʾ���ԣ����ﶼ��Ĭ�ϼ���
		model = new DefaultGraphModel();
		view = new GraphLayoutCache(model, new DefaultCellViewFactory());
		// JGraph����
		JGraph graph = new JGraph(model, view);
		temp = new int[10][10];
		makevertex(tree, 0, 0);

		makeedge(tree);
		
		// �����϶����cells�������graph����
		Iterator it = cells.iterator();
		while (it.hasNext()) {
			graph.getGraphLayoutCache().insert(it.next());
		}
		// һЩgraph����ļ򵥵���
		// graph.setMoveable(false);//�ɷ��ƶ�����ͼ��
		graph.setDisconnectable(false);//�����ƶ��ߵ�ָ��,���ǿ����ƶ�ͼ��
		// graph.setDisconnectOnMove(false);//�ɷ��ƶ�������,�����ڱߵ�Դ���յ�ı��ʧЧ
		// { graph.setGridEnabled(true); graph.setGridVisible(true); } // ��ʾ����
		// graph.setMoveBelowZero(true); //�Ƿ�����cellԽ�����Ͻ�.ͨ������Ϊfalse,�����������ô�
		graph.setAntiAliased(true);// Բ��ͼ������
		// graph.setSelectionEnabled(false);//�ܷ�ѡ�񵥸�cell
		return graph;
	}
	
	private void makevertex(Tree tree, int floor, int whichone) {
		// ������ĵ�һ��vertex����
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
			// ������ɫ��͸������
			if((cells.size() == 1)||(tree.Getchildrennum() == 0)){
				GraphConstants.setGradientColor(cells.get(cells.size()-1).getAttributes(), Color.red);
			}else {
				GraphConstants.setGradientColor(cells.get(cells.size()-1).getAttributes(), Color.orange);
			}
			GraphConstants.setOpaque(cells.get(cells.size()-1).getAttributes(), true);
			// Ϊ���vertex����һ��port
			DefaultPort port = new DefaultPort();
			cells.get(cells.size()-1).add(port);
		}
		for (int i=0; i<tree.Getchildrennum(); i++){
			makevertex(tree.Getchildren(i), floor+1, i);
    	}
	}
	
	private static void makeedge(Tree tree) {
		// ������ĵ�һ��vertex����
		for (int i=0; i<tree.Getchildrennum(); i++){
			int which1 = point.indexOf(tree.Gettext());
			int which2 = point.indexOf(tree.Getchildren(i).Gettext());
			// ����һ���ߣ���hello��world������port��������
			DefaultEdge edge = new DefaultEdge();
			edge.setSource(cells.get(which1).getChildAt(0));
			edge.setTarget(cells.get(which2).getChildAt(0));
			// ��edge����cell������
			cells.add(edge);
			// Ϊedge����һ����ͷ
			int arrow = GraphConstants.ARROW_CLASSIC;
			GraphConstants.setLineEnd(edge.getAttributes(), arrow);
			GraphConstants.setEndFill(edge.getAttributes(), true);
			makeedge(tree.Getchildren(i));
    	}
	}
}
