import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;


public class Main {

	public static void main(String[] args) throws ClassNotFoundException {
		// TODO Auto-generated method stub
		
        String url = "jdbc:mysql://localhost:3306/dbwork?"
        			+ "user=root&password=qq884208478&useUnicode=true&characterEncoding=UTF8";
        Sql sql= new Sql(url);
        new DrawPaper(sql);
	}

}
