import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;


public class MJTextField extends JTextField implements MouseListener {

	private ArrayList<String> words = new ArrayList<String>();
	
	private Find getFind;
	private String name;
	private String fathername;
	private DrawPaper drawPaper;
	private int count;
	private JPopupMenu pop = null; // µ¯³ö²Ëµ¥
	private ArrayList<String> anotherhighword;
	
	public MJTextField(Find getFind,String name,String fathername, DrawPaper drawPaper, int count, ArrayList<String> anotherhighword) {
		super();
		this.getFind = getFind;
		this.name = name;
		this.fathername = fathername;
		this.drawPaper = drawPaper;
		this.count = count;
		this.anotherhighword = anotherhighword;
		this.addMouseListener(this);
	}

	private void init() {
		//System.out.println(fathername);
		words = getFind.Findparent(fathername,count);
		pop = new JPopupMenu();
		ArrayList<JMenuItem> items = new ArrayList<JMenuItem>();
		for (int i = 0; i < words.size(); i++){
			items.add(new JMenuItem(words.get(i)));
			pop.add(items.get(i));
			items.get(i).addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String str = e.getActionCommand();
					String name2 = getText();
					setText(str);
					name = str;
					drawPaper.change(name,name2,count);
				}
			});
		}
		//System.out.println(anotherhighword);
		if (count == 1){
			ArrayList<JMenuItem> items2 = new ArrayList<JMenuItem>();
			for (int i = 0; i < anotherhighword.size(); i++){
				items2.add(new JMenuItem(anotherhighword.get(i)));
				pop.add(items2.get(i));
				items2.get(i).addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String str = e.getActionCommand();
						String name2 = getText();
						setText(str);
						name = str;
						drawPaper.change(name,name2,count);
					}
				});
			}
		}
		this.add(pop);
	}

	public JPopupMenu getPop() {
		return pop;
	}

	public void setPop(JPopupMenu pop) {
		this.pop = pop;
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			if (count != 0){
				init();
				pop.show(this, e.getX(), e.getY());
			}
		}
	}

	public void mouseReleased(MouseEvent e) {
	}

}