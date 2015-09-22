import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Rectangle2D;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.naming.InitialContext;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.eclipse.draw2d.IFigure;
import org.jgraph.JGraph;
import org.jgraph.graph.DefaultCellViewFactory;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.DefaultPort;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.GraphLayoutCache;
import org.jgraph.graph.GraphModel;
import org.omg.PortableServer.ID_ASSIGNMENT_POLICY_ID;


public class DrawPaper {
	
	private Sql sql;
	
	private ArrayList<String> queue = new ArrayList<String>();						//��������
	private ArrayList<String> highwords = new ArrayList<String>();					//��λ������
	private ArrayList<String> nosavewords = new ArrayList<String>();				//δ��������
	private int where = -1;															//����ָ��λ��
	private int before = -1;														//��ǰָ��˳��
	private int logo = 0;	
	private ArrayList<ArrayList<String>> samecheck = new ArrayList<ArrayList<String>>();
	private ArrayList<Integer> samecheckcount = new ArrayList<Integer>();
	private ArrayList<String> anotherhighword = new ArrayList<String>();	
	private DefaultListModel name;
	private ListSelectionEvent choose;
	private JFileChooser jfc = new JFileChooser();									// �ļ�ѡ����  
	
	private JFrame frame = new JFrame("���ڴ���ֵ�С����������С���ִʿ��д����");
	private ArrayList<MJTextField> changetext = new ArrayList<MJTextField>();
	
	private JLabel graphPanelabel1 = new JLabel( "���������λ�ʵĹ�ϵ�б���ʾ" );		//��ʾ����
	private JScrollPane graphPane1 = new JScrollPane();				
	private JList List1 = new JList();				
	private JLabel synonymlabel = new JLabel( "ͬ��ʣ�" );							//ͬ����ʾ�ؼ���
	private JTextField synonymtext = new JTextField();
	private JLabel hypernymlabel = new JLabel( "��λ�ʣ�" );							//��λ��ʾ�ؼ���
	private JTextField hypernymtext = new JTextField();
	private JButton hypernymbutton = new JButton("�޸�");
	private JLabel hyponymlabel = new JLabel( "��λ�ʣ�" );							//��λ��ʾ�ؼ���
	private JTextField hyponymtext = new JTextField();
	private JLabel label11 = new JLabel( "�����ѯ�Ĵʣ�" );							//��ѯ�ؼ���
	private JTextField inputtext1 = new JTextField();
	private JButton button1 = new JButton( "����" );
	private JLabel label12 = new JLabel();
	private JLabel label21 = new JLabel( "����ο��Ĵʣ�" );							//�ο��������ؼ���
	private JTextField inputtext2 = new JTextField();
	private JButton button2 = new JButton( "����" );
	private JLabel label22 = new JLabel();
	private JLabel label31 = new JLabel("����λ�ã�"); 								//����λ��ѡ��ؼ���
	private ButtonGroup bg = new ButtonGroup();
	private JRadioButton jrb1 = new JRadioButton("�����");
	private JRadioButton jrb2 = new JRadioButton("��������");
	private JButton savebutton = new JButton("���뵥�ʱ���(�������ֶ�����Ĵʱ���)");	//�����ʱ���ؼ�
	private JLabel label32 = new JLabel();
	private JLabel label41 = new JLabel("�������ݵ�ַ��"); 							//����������������ؼ���
	private JTextField inputtext3 = new JTextField();
	private JButton filebutton = new JButton("......");	
	private JLabel label42 = new JLabel(); 	
	private JButton inputbutton = new JButton("���봦������(ȥ��)");						//�����������ݴ���ؼ���
	private JButton uniqbutton = new JButton("ȥ�����������ﱾ�شʿ��Ѿ��е�����");		
	private JButton cleanbutton = new JButton("��մ�������");	
	private JButton deletebutton = new JButton("ɾ�����شʿ�������ʵ���Ϣ");
	private JButton beforebutton = new JButton("��һ��");	
	private JButton nextbutton = new JButton("��һ��(�Զ����浽����)");
	private JButton next2button = new JButton("��һ��(��������)");
	private JLabel label51 = new JLabel("�������ݵ�����ַ(�����ʽΪtxt)��"); 							//����������������ؼ���
	private JTextField inputtext4 = new JTextField();
	private JLabel label52 = new JLabel();
	private JButton output1button = new JButton("����������дʵ���Ϣ");	
	private JButton output2button = new JButton("����ʿ����дʵ���Ϣ");	
	private JButton outputbackbutton = new JButton("�ʿ⵼�봦������");	
	private JButton nosavewordbackbutton = new JButton("�����Ĵʵ��봦������");	
	//==============================================================================//�Ҳ�
	private JLabel graphPanelabel2 = new JLabel("�Աȴ�����λ�ʵ����νṹ��ʾ");		
	private JScrollPane graphPane2 = new JScrollPane();
	private JList List2 = new JList();
	
	private JLabel show_typelabel = new JLabel("��ʾ����(Ŀǰͼ��ʾֻ�����ڻ��ڴ��������)��"); 								//�Աȴ���ʾ��ʽѡ��ؼ���
	private ButtonGroup bg2 = new ButtonGroup();
	private JRadioButton jrb3 = new JRadioButton("ͼ��ʾ");
	private JRadioButton jrb4 = new JRadioButton("�б���ʾ");

	private JButton deletelistbutton = new JButton("-");	
	private JButton addlistbutton = new JButton("+");


	
	public DrawPaper( Sql sql ){
				
		this.sql = sql;
		
		init();
		/////////////////															//λ��ȷ��
		location();	
		/////////////////															//��ť����	
		define_button();	
		/////////////////															//��ӽ���
		getContentPane();
		
		
		frame.setIconImage((new ImageIcon(DrawPaper.class.getResource("/image/4.jpg"))).getImage());
		frame.setLayout(null);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
		public void windowClosing(WindowEvent arg0) {
			closesystem("nofinish");
			closesystem("nosavewords");
			System.exit(0);
		}});
	}

	

	private void init2() {
		// TODO Auto-generated method stub
		label12.setText("");
		label22.setText("");
		label32.setText("");
		label42.setText("");
		label52.setText("");
	}

	private void init() {
		// TODO Auto-generated method stub
		Font f=new Font("����",Font.BOLD,24);//����ָ���������ơ���ʽ�Ͱ�ֵ��С,����һ���� Font��
		List1.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		List1.setSelectedIndex( 0 );
		List1.setVisibleRowCount(  6 );  
		graphPane1.setViewportView( List1 );
		deletelistbutton.setFont(f);
		addlistbutton.setFont(f);
		synonymtext.setEditable(true);
		hypernymtext.setEditable(true);
		hyponymtext.setEditable(true);
		jrb1.setSelected(true);
		bg.add(jrb1);
		bg.add(jrb2);
		jrb3.setSelected(true);
		bg2.add(jrb3);
		bg2.add(jrb4);
		System.out.println("û����ɵĴʼ���");
		readnofinish("nofinish");
		System.out.println("�����Ĵʼ���");
		readnofinish("nosavewords");
	}



	private void location() {
		
		graphPanelabel1.setBounds( 20, 100, 300, 50 );
		graphPane1.setBounds( graphPanelabel1.getX(), graphPanelabel1.getY() + graphPanelabel1.getHeight(), 500, 200 );
		
		synonymlabel.setBounds( graphPane1.getX(), graphPane1.getY() + graphPane1.getHeight() + 10, 100, 30 );	
		synonymtext.setBounds( synonymlabel.getX() + synonymlabel.getWidth(), synonymlabel.getY(), graphPane1.getWidth() - synonymlabel.getWidth() , 30 );
		
		hypernymlabel.setBounds( synonymlabel.getX(), synonymlabel.getY() + synonymlabel.getHeight() + 10, 100, 30 );	
		hypernymtext.setBounds( hypernymlabel.getX() + hypernymlabel.getWidth(), hypernymlabel.getY(), synonymtext.getWidth() - 80, 30 );
		hypernymbutton.setBounds( hypernymtext.getX() + hypernymtext.getWidth(), hypernymtext.getY(), 80 , 30 );
		
		hyponymlabel.setBounds( hypernymlabel.getX(), hypernymlabel.getY() + hypernymlabel.getHeight() + 10, 100, 30 );	
		hyponymtext.setBounds( hyponymlabel.getX() + hyponymlabel.getWidth(), hyponymlabel.getY(), synonymtext.getWidth(), 30 );
		
		label41.setBounds( hyponymlabel.getX(), hyponymlabel.getY() + hyponymlabel.getHeight() + 10, 100, 30 );
		inputtext3.setBounds( label41.getX() + label41.getWidth(), label41.getY(), 300, 30 );
		filebutton.setBounds( inputtext3.getX() + inputtext3.getWidth() + 20, inputtext3.getY(), 80, 30 );
		label42.setBounds( filebutton.getX() + filebutton.getWidth(), filebutton.getY(), 300, 30 );
		
		inputbutton.setBounds( label41.getX(), label41.getY() + label41.getHeight() + 10, 150, 30 );
		uniqbutton.setBounds( inputbutton.getX() + inputbutton.getWidth() + 20, inputbutton.getY(), 300, 30 );
		cleanbutton.setBounds( uniqbutton.getX() + uniqbutton.getWidth() + 20, uniqbutton.getY(), 150, 30 );
		deletebutton.setBounds( cleanbutton.getX() + cleanbutton.getWidth() + 20, cleanbutton.getY(), 250, 30 );
		
		beforebutton.setBounds( inputbutton.getX(), inputbutton.getY() + inputbutton.getHeight() + 10, 290, 30 );
		nextbutton.setBounds( beforebutton.getX() + beforebutton.getWidth() + 20, beforebutton.getY(), 290, 30 );
		next2button.setBounds( nextbutton.getX() + nextbutton.getWidth() + 20, nextbutton.getY(), 290, 30 );
		
		label51.setBounds( beforebutton.getX(), beforebutton.getY() + beforebutton.getHeight() + 10, 290, 30 );
		inputtext4.setBounds( label51.getX() + label51.getWidth() + 20 , label51.getY(), 290, 30 );
		label52.setBounds( inputtext4.getX() + inputtext4.getWidth(), inputtext4.getY(), 200, 30 );
		
		output1button.setBounds( label51.getX(), label51.getY() + label51.getHeight() + 10, 220, 30 );
		output2button.setBounds( output1button.getX() + output1button.getWidth() + 10, output1button.getY(), output1button.getWidth(), output1button.getHeight() );
		outputbackbutton.setBounds( output2button.getX() + output2button.getWidth() + 10, output2button.getY(), output1button.getWidth(), output1button.getHeight() );
		nosavewordbackbutton.setBounds( outputbackbutton.getX() + outputbackbutton.getWidth() + 10, outputbackbutton.getY(), output1button.getWidth(), output1button.getHeight() );
		//===============================================================�Ҳ�
		graphPanelabel2.setBounds(600, 100, 200, 50);
		show_typelabel.setBounds( graphPanelabel2.getX() + graphPanelabel2.getWidth(), graphPanelabel2.getY(), 300, 50 );	
		jrb3.setBounds( show_typelabel.getX() + show_typelabel.getWidth(), show_typelabel.getY(), 100, 50 );
		jrb4.setBounds( jrb3.getX() + jrb3.getWidth(), jrb3.getY(), 100, 50 );
		graphPane2.setBounds(600, 150, 700, 200);
		
		label11.setBounds( graphPane2.getX(), graphPane2.getY() + graphPane2.getHeight() + 10, 100, 30 );	
		inputtext1.setBounds( label11.getX() + label11.getWidth(), label11.getY(), 380, 30 );
		button1.setBounds( inputtext1.getX() + inputtext1.getWidth() + 20, inputtext1.getY(), 100, 30 );
		label12.setBounds( button1.getX() + button1.getWidth(), button1.getY(), 200, 30 );
		
		label21.setBounds( label11.getX(), label11.getY() + label11.getHeight() + 10, 100, 30 );	
		inputtext2.setBounds( label21.getX() + label21.getWidth(), label21.getY(), 380, 30 );
		button2.setBounds( inputtext2.getX() + inputtext2.getWidth() + 20, inputtext2.getY(), 100, 30 );
		label22.setBounds( button2.getX() + button2.getWidth(), button2.getY(), 200, 30 );
		
		label31.setBounds( label21.getX(), label21.getY() + label21.getHeight() + 10, 100, 30 );	
		jrb1.setBounds( label31.getX() + label31.getWidth(), label31.getY(), 100, 30 );
		jrb2.setBounds( jrb1.getX() + jrb1.getWidth(), jrb1.getY(), 100, 30 );
		
		savebutton.setBounds( jrb2.getX() + jrb2.getWidth() + 0, jrb2.getY(), 300, 30 );	
		label32.setBounds( savebutton.getX() + savebutton.getWidth(), savebutton.getY(), 200, 30 );
		
		addlistbutton.setBounds(graphPane1.getX() + graphPane1.getWidth() + 15, graphPane1.getY(), 50, 50);
		deletelistbutton.setBounds(addlistbutton.getX(), addlistbutton.getY() + addlistbutton.getHeight() + 20, 50, 50);
		
		frame.setBounds(0, 0, 1366, 720);
	}
	
	private void define_button(){
		//��ѯ����
		button1.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				for (int i = 0; i < changetext.size(); i++){
					frame.remove(changetext.get(i));
				}

				changetext = null;
				changetext = new ArrayList<MJTextField>();
				frame.repaint();
				
				init2();
				definebutton1();
			}
		});
		//��������
		button2.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				init2();
				definebutton2();
				
			}
		});
		//��λ���޸ı��水��
		hypernymbutton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				init2();
				definehypernymbutton();
				
			}
		});
		//�������ʱ��水��
		savebutton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				init2();
				definesavebutton();
			}
		});
		//�������ݵ�ַ���밴��
		filebutton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub 
				init2();
				definefilebutton();
			}
		});
		//���ݵ�����а���
		uniqbutton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				init2();
				defineuniqbutton();
			}
		});
		//����ȥ�ذ���
		inputbutton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				init2();
				defineinputbutton();
			}
		});
		//������հ���
		cleanbutton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				init2();
				definecleanbutton();
			}
		});
		
		deletebutton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				init2();
				definedeletebutton();
			}
		});
		
		beforebutton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				init2();
				definebeforebutton();
			}
		});
		
		nextbutton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				init2();
				definenextbutton();
			}
		});
		
		next2button.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				init2();
				definenext2button();
			}
		});
		
		output1button.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				init2();
				defineoutput1button();
			}
		});
		
		output2button.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				init2();
				defineoutput2button();
			}
		});
		
		addlistbutton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				init2();
				defineaddlistbutton();
			}
		});
		
		deletelistbutton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				init2();
				definedeletelistbutton();
			}
		});
		
		outputbackbutton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				init2();
				defineoutputbackbutton();
			}
		});
		
		nosavewordbackbutton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				init2();
				definenosavewordbackbutton();
			}
		});
		
		List1.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				// TODO Auto-generated method stub
				init2();
				if (logo == 0){
					try {
						choose = arg0;
						Object obj=((JList)arg0.getSource()).getSelectedValue();
						String temp = obj.toString();
						defineList(temp);
					} catch (Exception e) {
						// TODO: handle exception
					}
					logo = 1;
				}else {
					logo = 0;
				}

			}
		});
	
	}

	private void getContentPane() {
		// TODO Auto-generated method stub
		frame.getContentPane().add(graphPane1);
		frame.getContentPane().add(graphPanelabel1);
		
		frame.getContentPane().add(synonymlabel);
		frame.getContentPane().add(synonymtext);		

		frame.getContentPane().add(hypernymlabel);
		frame.getContentPane().add(hypernymtext);
		frame.getContentPane().add(hypernymbutton);
		
		frame.getContentPane().add(hyponymlabel);
		frame.getContentPane().add(hyponymtext);
		
		frame.getContentPane().add(label11);
		frame.getContentPane().add(inputtext1);
		frame.getContentPane().add(button1);
		frame.getContentPane().add(label12);

		frame.getContentPane().add(label21);
		frame.getContentPane().add(inputtext2);
		frame.getContentPane().add(button2);
		frame.getContentPane().add(label22);
		
		frame.getContentPane().add(label31);
		frame.getContentPane().add(jrb1);
		frame.getContentPane().add(jrb2);
		
		frame.getContentPane().add(savebutton);
		frame.getContentPane().add(label32);
		
		frame.getContentPane().add(label41);
		frame.getContentPane().add(inputtext3);
		frame.getContentPane().add(filebutton);
		frame.getContentPane().add(label42);
		
		frame.getContentPane().add(inputbutton);
		frame.getContentPane().add(uniqbutton);
		frame.getContentPane().add(cleanbutton);
		frame.getContentPane().add(deletebutton);
		frame.getContentPane().add(beforebutton);
		frame.getContentPane().add(nextbutton);
		frame.getContentPane().add(next2button);
		
		frame.getContentPane().add(label51);
		frame.getContentPane().add(inputtext4);
		frame.getContentPane().add(label52);
		
		frame.getContentPane().add(output1button);
		frame.getContentPane().add(output2button);
		frame.getContentPane().add(outputbackbutton);
		frame.getContentPane().add(nosavewordbackbutton);
		//========================================�Ҳ�
		frame.getContentPane().add(graphPane2);
		frame.getContentPane().add(show_typelabel);
		frame.getContentPane().add(jrb3);
		frame.getContentPane().add(jrb4);
		frame.getContentPane().add(graphPanelabel2);
		
		frame.getContentPane().add(deletelistbutton);
		frame.getContentPane().add(addlistbutton);
	}
	
	private void definebutton1() {
		try {
		//	((DefaultListModel)List1.getModel()).removeAllElements();						//��ʼ��
			hypernymtext.setText("");
			hyponymtext.setText("");
			synonymtext.setText("");
			anotherhighword.clear();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		if(inputtext1.getText().toString().equals( "" )){
			label12.setText( "����Ϊ��ֵ,����������" );
		}else{
			
			if(jrb1.isSelected()){											//���������
				
				Find getFind = new Find(sql);
	        	label12.setText( "��ʼ��ѯ,���Ժ�......" );
	        	long time1 = System.currentTimeMillis();
	        	
	        	ArrayList<ArrayList<String>> show = getFind.Find( inputtext1.getText().toString() );
	        	ArrayList<String> showsame = getFind.Findsame( inputtext1.getText().toString() );
	        	ArrayList<String> showchild = getFind.Findchild( inputtext1.getText().toString() );
	        	
	        	long time2 = System.currentTimeMillis();
	        	label12.setText( "��ѯ���,��ʱ��" + (double)(time2 - time1)/1000 + "��" );
	        	
        		name = new DefaultListModel();
        		
        		String temp = new String();
        		
        		highwords = new ArrayList<String>();
	        	for (int i=0; i<show.size(); i++){
	        		temp = new String();
	        		for (int j=0; j<show.get(i).size(); j++){
	        			temp = temp + show.get(i).get(j) + "->";
	        			if(j == 1){
	        				highwords.add(show.get(i).get(j));
	        			}
	        		}
	        		temp = temp + "|";
	        		name.addElement(temp);
	        	}
	        	//System.out.print(highwords);
	        	List1.setModel(name);
	        	ArrayList<String> Gettreepoint = highsame();
	        	
	        	temp = new String();
	        	samecheck = new ArrayList<ArrayList<String>>();
	        	samecheckcount = new ArrayList<Integer>();
        		try {
            		for (int i = 0; i < Gettreepoint.size() + 1; i++) {
            			ArrayList<String> tempArrayList = new ArrayList<String>();
            			if (i == Gettreepoint.size()){
                			tempArrayList.add("����ͬ���");
            			}else {
                			tempArrayList.add(Gettreepoint.get(i));
    					}
            			samecheck.add(tempArrayList);
            			samecheckcount.add(1);
    				}
        			for (int i=0; i<showsame.size(); i++){
            			String father = getFind.Findonefather(showsame.get(i)).get(0);
            			for (int j = 0; j < samecheck.size(); j++) {
    						if (samecheck.get(j).get(0).equals(father)){
    							samecheck.get(j).add(showsame.get(i));
    							break;
    						}
    						if (j == samecheck.size() - 1){
    							samecheck.get(samecheck.size() - 1).add(showsame.get(i));
    						}
    					}
    	        	}
            		for (int i=0; i<samecheck.size(); i++){
            			if ((samecheck.get(i).size() > 1)&&(samecheckcount.get(i) == 1)){
            				//System.out.print(samecheck.get(i));
            				temp = temp + samecheck.get(i).get(0) + "��" + samecheck.get(i).get(1);
            				for (int j = 2; j < samecheck.get(i).size(); j++) {
    							temp = temp + "��" + samecheck.get(i).get(j);
    						}
            				temp = temp + "��";
            			}
    	        	}
            		synonymtext.setText(temp);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
        		temp = new String();
        		for (int i=0; i<showchild.size(); i++){
	        		temp = temp + showchild.get(i) + "��";
	        	}
        		if(!temp.isEmpty()){
        			temp = temp + "|";
        			temp = temp.replace("��|","");
        		}
        		hyponymtext.setText(temp);
	        	temp = new String();
	        	for (int i=0; i<Gettreepoint.size(); i++){
	        		temp = temp + Gettreepoint.get(i) + "��";
	        	}
        		if(!temp.isEmpty()){
        			temp = temp + "|";
        			temp = temp.replace("��|","");
        		}
        		hypernymtext.setText(temp);
			}else {
				SqlInput file = new SqlInput(inputtext1.getText().toString());
				if (!file.exists()) {
					label12.setText( "�ļ�������" );
				}else{
					try {

			        	label12.setText( "��ʼ��ѯ,���Ժ�......" );
						long time1 = System.currentTimeMillis();
						file.find();
						long time2 = System.currentTimeMillis();
			        	label12.setText( "��ѯ���,��ʱ��" + (double)(time2 - time1)/1000 + "��" );
			        	name = new DefaultListModel();
			        	name = file.GetModel();
						List1.setModel(name);
						highwords = new ArrayList<String>();
						highwords = file.GetHighword();
		        		synonymtext.setText(file.GetSame());
		        		hyponymtext.setText(file.GetChild());
		        		hypernymtext.setText(file.GetTreepoint());
		        		definehypernymbutton();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}

	private void definebutton2() {
		if(inputtext2.getText().toString().equals("")){
			label22.setText("����Ϊ��ֵ,����������");
		}else{
			if(jrb1.isSelected()){
				if (jrb3.isSelected()) {
					Treemake treemake = new Treemake();
					
		        	long time1 = System.currentTimeMillis();
		        	label22.setText("��ʼ��ѯ,���Ժ�......");
		        	Tree tree = treemake.treemake(sql , inputtext2.getText().toString() , 0 , 0.0);
		        	long time2 = System.currentTimeMillis();
		        	
		        	DrawMap drawMap = new DrawMap();
		        	//drawMap.show(tree);
		        	graphPane2.setViewportView(drawMap.show2(tree));
		        	label22.setText("��ѯ���,��ʱ��"+(double)(time2 - time1)/1000+"��");
				} else {
					List2.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
					List2.setSelectedIndex( 0 );
					List2.setVisibleRowCount(  6 );  
					graphPane2.setViewportView( List2 );
					
					Find getFind = new Find(sql);
		        	label22.setText( "��ʼ��ѯ,���Ժ�......" );
		        	long time1 = System.currentTimeMillis();
		        	
		        	ArrayList<ArrayList<String>> show = getFind.Find( inputtext2.getText().toString() );
		        	
		        	long time2 = System.currentTimeMillis();
		        	label22.setText( "��ѯ���,��ʱ��" + (double)(time2 - time1)/1000 + "��" );
		        	
	        		DefaultListModel name2 = new DefaultListModel();
	        		
	        		String temp = new String();
	        		
		        	for (int i=0; i<show.size(); i++){
		        		temp = new String();
		        		for (int j=0; j<show.get(i).size(); j++){
		        			temp = temp + show.get(i).get(j) + "->";
		        		}
		        		temp = temp + "|";
		        		name2.addElement(temp);
		        	}
		        	List2.setModel(name2);
				}
				
			}else {
				SqlInput file = new SqlInput(inputtext2.getText().toString());
				if (!file.exists()) {
					label22.setText( "�ļ�������" );
				} else{
					try {
						List2.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
						List2.setSelectedIndex( 0 );
						List2.setVisibleRowCount(  6 );  
						graphPane2.setViewportView( List2 );

			        	label22.setText( "��ʼ��ѯ,���Ժ�......" );
						long time1 = System.currentTimeMillis();
						file.find();
						long time2 = System.currentTimeMillis();
			        	label22.setText( "��ѯ���,��ʱ��" + (double)(time2 - time1)/1000 + "��" );
			        	
						List2.setModel(file.GetModel());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
    	}
	}

	protected void definehypernymbutton() {
		// TODO Auto-generated method stub
		anotherhighword.clear();
		String[] temp = hypernymtext.getText().split("��");
		//System.out.println(temp.length);
		ArrayList<String> Gettreepoint = highsame();
		for (int i = 0; i < temp.length; i++){
			if (!Gettreepoint.contains(temp[i])){
				anotherhighword.add(temp[i]);
			}
		}
		//System.out.println(anotherhighword);
	}
	
	private void definesavebutton() {
		if(inputtext1.getText().toString().equals("")){
			label32.setText("����Ϊ��ֵ,����������");
		}else{
			File file = new File("sql\\"+inputtext1.getText().toString());
			if(file.exists()&&file.isFile()){
				file.delete();
			}
			try {
				file.createNewFile();
				FileOutputStream out = new FileOutputStream( file, true );        
				StringBuffer sb=new StringBuffer();
				sb.append("����λ��ϵ��\n");
		        for(int i=0;i<List1.getModel().getSize();i++){
		        	String temp = List1.getModel().getElementAt(i).toString();
		        	String temp2 = temp.replaceAll("->",",");
		        	String temp3 = temp2.replace(",|","");
		            sb.append(temp3+"\n");
		        }        
		        
				sb.append("ͬ���ϵ��\n");
				sb.append(synonymtext.getText().toString()+"\n");
				sb.append("��λ��ϵ��\n");
				sb.append(hypernymtext.getText().toString()+"\n");
				sb.append("��λ��ϵ��\n");
				sb.append(hyponymtext.getText().toString()+"\n");
	            out.write(sb.toString().getBytes("utf-8"));
	            
	            out.close();
	            label32.setText("�������");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		    	
		}
	}

	private void definefilebutton() {
		jfc.setDialogTitle("ѡ���ļ�");
		jfc.setFileSelectionMode(0);// �趨ֻ��ѡ���ļ�  
	    int state = jfc.showOpenDialog(null);// �˾��Ǵ��ļ�ѡ��������Ĵ������  
	    if (state == 1) {  
	    	return;// �����򷵻�  
	    } else {  
	    	File f = jfc.getSelectedFile();// fΪѡ�񵽵��ļ�  
	    	inputtext3.setText(f.getAbsolutePath());  
	    }  
	}

	private void defineinputbutton() {
		if(inputtext3.getText().toString().equals("")){
			label42.setText("����Ϊ��ֵ,����������");
		}else{
			FileInput file = new FileInput(inputtext3.getText().toString());
			if (!file.exists()) {
				label42.setText( "�ļ�������" );
			} else{
				try {
					label42.setText( "��ʼ����,���Ժ�......" );
					int oldcount = queue.size();
					queue = file.find(queue);
		        	if (where == -1){
		        		where++;
		        		before = where;
		        	}
					label42.setText( "�������,���¼���" + (queue.size() - oldcount) + "����,Ŀǰ����״̬��" + ( where + 1 ) + "/" + queue.size());
		        	inputtext1.setText(queue.get(where));
		        	jrb1.setSelected(true);
		        	jrb2.setSelected(false);
		        	definebutton1();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
    	}
	}

	private void defineuniqbutton() {
		for ( int i = 0; i < queue.size(); i++ ){
			try {
				File file = new File("sql\\"+queue.get(i));
				if (file.exists()) {
					queue.remove(i);
					i--;
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		//System.out.println("queue:"+queue);
		where = 0;
		before = where;
		if (queue.size() >= 1){
			label42.setText( "�������,Ŀǰ����״̬��" + ( where + 1 ) + "/" + queue.size());
			inputtext1.setText(queue.get(where));
        	jrb1.setSelected(true);
        	jrb2.setSelected(false);
        	definebutton1();
		}else {
			label42.setText( "�������,Ŀǰ����Ϊ�ա�");
		}
	}
	
	private void definecleanbutton() {
		queue.clear();
		where = -1;
		before = where;
		label42.setText( "�Ƴ����,Ŀǰ����Ϊ�ա�");
	}
	
	private void definedeletebutton() {
		File file = new File("sql\\"+inputtext1.getText().toString());
		//System.out.println(file);
		if(file.exists()&&file.isFile()){
			file.delete();
			label42.setText("��Ϣɾ�����");
		}else {
			label42.setText("���شʿ��޴˴���Ϣ");
		}
	}
	
	private void definebeforebutton() {
		if (before <= 0){
			label42.setText("����Ϊ�ջ��ѵ�����ͷ");
		}else {
			before--;
			label42.setText( "��ǰ�鿴���,Ŀǰ����λ�ã�" + ( before + 1 ) + "/" + queue.size());
			inputtext1.setText(queue.get(before));
        	jrb1.setSelected(true);
        	jrb2.setSelected(false);
        	definebutton1();
		}
	}
	/*	�������һ������ģ��	*/
	private void definenextbutton() {
		definesavebutton();
		inputtext1.setText("");
		if (nosavewords.contains(queue.get(before))){
			nosavewords.remove(queue.get(before));
		}
		if (before == where){
			if (( where != -1 )){
				where++;
				before = where;
			}else{
				label42.setText("����Ϊ��");
				return ;
			}
			if (where == queue.size()){
				definecleanbutton();
				return ;
			}
		}else{
			before = where;
		}
		label42.setText( "���鿴���,Ŀǰ����λ�ã�" + ( where + 1 ) + "/" + queue.size());
		inputtext1.setText(queue.get(where));
    	definebutton1();
	}
	/*	��������һ������ģ��	*/
	private void definenext2button() {
		inputtext1.setText("");
		if (where != -1){
			if (!nosavewords.contains(queue.get(before))){
				nosavewords.add(queue.get(before));
			}
		}
		if (before == where){
			if (( where != -1 )){
				where++;
				before = where;
			}else{
				label42.setText("����Ϊ��");
				return ;
			}
			if (where == queue.size()){
				definecleanbutton();
				return ;
			}
		}else{
			before = where;
		}
		label42.setText( "���鿴(����)���,Ŀǰ����λ�ã�" + ( where + 1 ) + "/" + queue.size());
		inputtext1.setText(queue.get(where));
    	definebutton1();
	}
	
	private void defineoutput1button(){
		if(inputtext4.getText().toString().equals( "" )){
			label52.setText( "����Ϊ��ֵ,����������" );
		}else{
			File file = new File(inputtext4.getText().toString());
			try {
				label52.setText( "��ʼ���,���Ժ�......" );
	        	long time1 = System.currentTimeMillis();
				
				BufferedWriter writer  = new BufferedWriter(new FileWriter(file));
				writer.write("��\tͬ���\t��λ��\t��λ��\t������״��ϵ");
				writer.newLine();
				for (int i=0; i < queue.size(); i++){
					inputtext1.setText(queue.get(i));
			    	jrb1.setSelected(true);
			    	jrb2.setSelected(false);
			    	definebutton1();
			    	writer.write(queue.get(i)+"\t"+synonymtext.getText()+"\t"+hypernymtext.getText()+"\t"+hyponymtext.getText()+"\t");
			    	for(int j=0;j<List1.getModel().getSize();j++){
			        	String temp = List1.getModel().getElementAt(j).toString();
			        	String temp2 = temp.replace("->|","��");
			        	writer.write(temp2);
			        }  
			    	writer.newLine();
				}
				writer.flush();
				writer.close();
				
	        	long time2 = System.currentTimeMillis();
	        	label52.setText( "������,��ʱ��" + (double)(time2 - time1)/1000 + "��" );
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void defineoutput2button() {
		if(inputtext4.getText().toString().equals( "" )){
			label52.setText( "����Ϊ��ֵ,����������" );
		}else{
			File file = new File(inputtext4.getText().toString());
			try {
				label52.setText( "��ʼ���,���Ժ�......" );
	        	long time1 = System.currentTimeMillis();
	        	
				BufferedWriter writer  = new BufferedWriter(new FileWriter(file));
				writer.write("��\tͬ���\t��λ��\t��λ��\t������״��ϵ");
				writer.newLine();
		        String path = "sql"; // ·��
		        File f = new File(path);
		        if (!f.exists()) {
		            //System.out.println(path + " not exists");
		            return;
		        }

		        List<File> fa = getFileSort(path);
		        for (int i = fa.size() - 1; i >= 0; i--) {
		            File fs = fa.get(i);
		            //System.out.println(fs.getName());
		            inputtext1.setText(fs.getName());
			    	jrb1.setSelected(false);
			    	jrb2.setSelected(true);
			    	definebutton1();
			    	writer.write(fs.getName()+"\t"+synonymtext.getText()+"\t"+hypernymtext.getText()+"\t"+hyponymtext.getText()+"\t");
			    	for(int j=0;j<List1.getModel().getSize();j++){
			        	String temp = List1.getModel().getElementAt(j).toString();
			        	String temp2 = temp.replace("->|","��");
			        	writer.write(temp2);
			        }  
			    	writer.newLine();
		        }
				writer.flush();
				writer.close();
				
	        	long time2 = System.currentTimeMillis();
	        	label52.setText( "������,��ʱ��" + (double)(time2 - time1)/1000 + "��" );
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static List<File> getFileSort(String path) {
		 
        List<File> list = getFiles(path, new ArrayList<File>());
 
        if (list != null && list.size() > 0) {
 
            Collections.sort(list, new Comparator<File>() {
                public int compare(File file, File newFile) {
                    if (file.lastModified() < newFile.lastModified()) {
                        return 1;
                    } else if (file.lastModified() == newFile.lastModified()) {
                        return 0;
                    } else {
                        return -1;
                    }
 
                }
            });
 
        }
 
        return list;
    }
	
	public static List<File> getFiles(String realpath, List<File> files) {
		 
        File realFile = new File(realpath);
        if (realFile.isDirectory()) {
            File[] subfiles = realFile.listFiles();
            for (File file : subfiles) {
                if (file.isDirectory()) {
                    getFiles(file.getAbsolutePath(), files);
                } else {
                    files.add(file);
                }
            }
        }
        return files;
    }
	
	private void defineaddlistbutton() {
		try {
			String words = new String();
			words = inputtext1.getText() + "-> ->|"; 
			name.addElement(words);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	private void definedeletelistbutton() {
		try {
			Object obj=((JList)choose.getSource()).getSelectedValue();
			String temp = obj.toString();
			//System.out.println(temp);
			temp = temp.replace("->|", "");
			String[] tempStr = temp.split("->");
			for (int i = 0; i < highwords.size(); i++){
				if (tempStr[1].equals(highwords.get(i))){
					highwords.remove(i);
					break;
				}
			}
			ArrayList<String> Gettreepoint = highsame();
			
	    	String temp2 = new String();
			for (int i=0; i<Gettreepoint.size(); i++){
	    		temp2 = temp2 + Gettreepoint.get(i) + "��";
	    	}
			for (int i=0; i<anotherhighword.size(); i++){
	    		temp2 = temp2 + anotherhighword.get(i) + "��";
	    	}
			if(!temp2.isEmpty()){
				temp2 = temp2 + "|";
				temp2 = temp2.replace("��|","");
			}
			hypernymtext.setText(temp2);
			
			name.removeElementAt(((JList)choose.getSource()).getSelectedIndex());
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	private void defineoutputbackbutton() {
		// TODO Auto-generated method stub
		File file=new File("sql");
		File[] tempList = file.listFiles();
		int oldcount = queue.size();
		for (int i = 0; i < tempList.length; i++) {
			String temp = tempList[i].getName().replace("sql", "");
			if (!queue.contains(temp)){
            	queue.add(temp);
            	//System.out.println(temp);
        	}
		}
		if (queue.size() > 0){
			if (where == -1){
	    		where++;
	    		before = where;
	    	}
	     	label42.setText( "�������,���¼���" + (queue.size() - oldcount) + "����,Ŀǰ����״̬��" + ( where + 1 ) + "/" + queue.size());
	    	inputtext1.setText(queue.get(where));
	    	jrb1.setSelected(false);
	    	jrb2.setSelected(true);
	    	definebutton1();
		}
	}
	
	private void definenosavewordbackbutton() {
		// TODO Auto-generated method stub
		int oldcount = queue.size();
		for (int i = 0; i < nosavewords.size(); i++) {
			String temp = nosavewords.get(i);
			if (!queue.contains(temp)){
            	queue.add(temp);
        	}
		}
		if (queue.size() > 0){
	     	if (where == -1){
	    		where++;
	    		before = where;
	    	}
	     	label42.setText( "�������,���¼���" + (queue.size() - oldcount) + "����,Ŀǰ����״̬��" + ( where + 1 ) + "/" + queue.size());
	    	inputtext1.setText(queue.get(where));
	    	jrb1.setSelected(true);
	    	jrb2.setSelected(false);
	    	definebutton1();
		}
	}

	
	private void closesystem(String filename) {
		// TODO Auto-generated method stub
		File file = new File(filename);
		if(file.exists()&&file.isFile()){
			file.delete();
		}
		if (filename == "nofinish"){
			//System.out.println(where);
			if (where == -1){
				return ;
			}
			try {
				file.createNewFile();
				FileOutputStream out = new FileOutputStream( file, true );        
				StringBuffer sb=new StringBuffer();
		        for(int i=where;i<queue.size();i++){
		            sb.append(queue.get(i)+"\n");
		        }        
	            out.write(sb.toString().getBytes("gb2312"));
	            out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			if (nosavewords.size() == 0){
				return ;
			}
			try {
				file.createNewFile();
				FileOutputStream out = new FileOutputStream( file, true );        
				StringBuffer sb=new StringBuffer();
		        for(int i=0; i<nosavewords.size(); i++){
		            sb.append(nosavewords.get(i)+"\n");
		        }        
	            out.write(sb.toString().getBytes("gb2312"));
	            out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	private void readnofinish(String filename) {
		// TODO Auto-generated method stub
		FileInput file = new FileInput(filename);
		if (!file.exists()) {
		} else{
			if (filename == "nofinish"){
				try {
					label42.setText( "��ʼ����,���Ժ�......" );
					queue = file.find(queue);
		        	if (where == -1){
		        		where++;
		        		before = where;
		        	}
					label42.setText( "�������,���¼���" + queue.size() + "����,Ŀǰ����״̬��" + ( where + 1 ) + "/" + queue.size());
		        	inputtext1.setText(queue.get(where));
		        	jrb1.setSelected(true);
		        	jrb2.setSelected(false);
		        	definebutton1();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				try {
					nosavewords = file.find(nosavewords);
					//System.out.println(nosavewords.size());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
	}
	
	public void change(String name2, String name3, int count){
		if (count == 1){
			if (!anotherhighword.contains(name2)){ 
				highwords.add(name2);
			}
			for (int i = 0; i < highwords.size(); i++){
				if (name3.equals(highwords.get(i))){
					highwords.remove(i);
					break;
				}
			}
			ArrayList<String> Gettreepoint = highsame();
			
	    	String temp2 = new String();
			for (int i=0; i<Gettreepoint.size(); i++){
	    		temp2 = temp2 + Gettreepoint.get(i) + "��";
	    	}
			for (int i=0; i<anotherhighword.size(); i++){
	    		temp2 = temp2 + anotherhighword.get(i) + "��";
	    	}
			if(!temp2.isEmpty()){
				temp2 = temp2 + "|";
				temp2 = temp2.replace("��|","");
			}
			hypernymtext.setText(temp2);
		}
		
		int temp = changetext.size() - 1;
		for (int i = 0; i < changetext.size(); i++){
			if (name2.equals(changetext.get(i).getText().toString())){
				temp = i;
				break;
			}
			//System.out.println(name2+" "+changetext.get(i).getText().toString());
		}
		ArrayList<String> changetext2 = new ArrayList<String>();
		for (int i = 0; i <= temp; i++){
			changetext2.add(changetext.get(i).getText().toString());
		}
		changeList(name2, (temp+1), changetext2);
	}
	
	private void changeList(String name2, int count, ArrayList<String> changetext2) {
		sql.Select2(name2);
		//System.out.println("|"+name2+"|"+(count-1)+"|");
		ArrayList<String> treepoint = sql.Getresult2();
		int size = treepoint.size();
		if (size < 1){
			String words = new String();
			for (int i=0; i<count; i++){
				words = words + changetext2.get(i)+"->";
		        //System.out.print(changetext2.get(i)+"->");
			}
			words = words + "|";
	        //System.out.println("|");

			//System.out.println();
			//System.out.println(choose.getSource());
			name.setElementAt(words, ((JList)choose.getSource()).getSelectedIndex());
	        defineList(words);
		}else{
			changetext2.add(treepoint.get(0));
			changeList(treepoint.get(0), (count+1), changetext2);
		}
	}
	
	private void defineList(String temp) {
		for (int i = 0; i < changetext.size(); i++){
			frame.remove(changetext.get(i));
		}

		changetext = null;
		changetext = new ArrayList<MJTextField>();
		temp = temp.replace("->|", "");
		String[] tempStr = temp.split("->");

		Find getFind = new Find(sql);
		for (int i = 0; i < tempStr.length; i++){
			//System.out.print(tempStr[i]+":");
			String fathername;
			if (i > 0 ){
				fathername = tempStr[i-1];
			}else {
				fathername = tempStr[0];
			}
			//System.out.print("("+fathername+")");
			changetext.add(new MJTextField(getFind, tempStr[i], fathername, this, i, anotherhighword));
			changetext.get(i).setEditable(false);
			changetext.get(i).setText(tempStr[i]);
			changetext.get(i).setBounds( 20 + 110*i, 50, 100, 30 );
			frame.getContentPane().add(changetext.get(i));
		}
		frame.repaint();
	}
	
	private ArrayList<String> highsame() {
		ArrayList<String> Gettreepoint = new ArrayList<String>(highwords);
    	
        ArrayList<String> temp2 = new ArrayList<String>();
        for (int i=0; i<Gettreepoint.size()-1; i++){
       	 	for (int j=i+1; j<=Gettreepoint.size()-1; j++){
            	if(Gettreepoint.get(i).equals(Gettreepoint.get(j))){
            		//System.out.println(treepoint.get(i));
            		temp2.add(Gettreepoint.get(i));
            		break;
            	}
            }
        }

        for (int i=0; i<temp2.size(); i++){
        	Gettreepoint.remove(temp2.get(i));
        }
        return Gettreepoint;
	}
}
