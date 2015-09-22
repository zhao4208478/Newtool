

import java.util.ArrayList;
import java.util.Timer;
import java.security.interfaces.RSAKey;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;

public class Sql {
	
	private String url;
	private Connection conn;
	private Statement stmt;
	private ArrayList<String> treepoint;
	private ArrayList<Double> size;
	private ArrayList<String> treepoint2;
	private ArrayList<Double> size2;

	private ArrayList<String> treepoint3;
	
	public Sql(String url) throws ClassNotFoundException{
		this.url = url;
		Connectsql();
	}
	
	public void Select(String name){
		Selectsql(name);
	}
	
	public void Select2(String name){
		Selectsql2(name);
	}
	
	public boolean Select3(String name , String name2){
		return Selectsql3(name , name2);
	}
	
	public boolean Select4(String name , String name2){
		return Selectsql4(name , name2);
	}
	
	public void Select5(String name){
		Selectsql5(name);
	}
	
	public void Select6(String name){
		Selectsql6(name);
	}
	
	public void Select7(String name){
		Selectsql7(name);
	}
	
	public ArrayList<String> Getresult(){
		return treepoint;
	}
	
	public ArrayList<Double> Getsize(){
		return size;
	}	
	
	public ArrayList<String> Getresult2(){
		return treepoint2;
	}
	
	public ArrayList<Double> Getsize2(){
		return size2;
	}	

	public ArrayList<String> Getresult5(){
		return treepoint3;
	}

	public ArrayList<String> Getresult6(){
		return treepoint3;
	}
	
	public ArrayList<String> Getresult7(){
		return treepoint3;
	}	
	
	private void Connectsql() throws ClassNotFoundException{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url);
            stmt = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void Selectsql(String name){
		try {
			treepoint = new ArrayList<String>();
			size = new ArrayList<Double>();
	        //    System.out.println("成功加载MySQL驱动程序");
			String sql = "select check_entity.id from check_entity where check_entity.name = \"" + name + "\";";
	        ResultSet rs = stmt.executeQuery(sql);
	        while (rs.next()) {
	        	String sql2 = "select check_hhpair.hyper_id,check_hhpair.score,check_hhpair.correct_count from check_hhpair where check_hhpair.entity_id = " + rs.getString(1) + ";";
	        	Statement stmt2 = conn.createStatement();
	        	ResultSet rs2 = stmt2.executeQuery(sql2);
		        while (rs2.next()) {
		        	String sql3 = "select check_hyper.name from check_hyper where check_hyper.id = " + rs2.getString(1)+ ";";
		        	Statement stmt3 = conn.createStatement();
		        	ResultSet rs3 = stmt3.executeQuery(sql3);
			        while (rs3.next()) {
			        	if((Double.parseDouble(rs2.getString(2)) > 0.985)/*&&(Double.parseDouble(rs2.getString(3)) > 0)*/){
			        		//System.out.println(rs3.getString(1) + "\t" + rs2.getString(2));
			        		treepoint.add(rs3.getString(1));
			            	size.add(Double.parseDouble(rs2.getString(2)));
			            }else{
			            	if (treepoint.size() < 1){
			            		treepoint.add(rs3.getString(1));
				            	size.add(Double.parseDouble(rs2.getString(2)));
				            	return ;
							}
			            }
			        }
		        }
		    }
	        } catch (SQLException e) {
	            System.out.println("MySQL操作错误");
	            e.printStackTrace();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		return ;
	}
	
	private void Selectsql2(String name){
		try {
			treepoint2 = new ArrayList<String>();
			size2 = new ArrayList<Double>();
	        //    System.out.println("成功加载MySQL驱动程序");
	        String sql = "select a.id from Check_hyper a where a.name = \"" + name + "\";";
	        ResultSet rs = stmt.executeQuery(sql);
	        while (rs.next()) {
		        String sql2 = "select c.hyper_parent_id, c.confidence, c.correct_count from Check_hyperhierarchy c where c.hyper_child_id = " + rs.getString(1) + ";";
		        Statement stmt2 = conn.createStatement();
		        ResultSet rs2 = stmt2.executeQuery(sql2);
		        while (rs2.next()) {
			        String sql3 = "select b.name from Check_hyper b where b.id = " + rs2.getString(1) + ";";
			        Statement stmt3 = conn.createStatement();
			        ResultSet rs3 = stmt3.executeQuery(sql3);
			        while (rs3.next()) {
			            if (Integer.parseInt(rs2.getString(3)) >= 0){
			            	treepoint2.add(rs3.getString(1));
			            	size2.add(Double.parseDouble(rs2.getString(2)));
			            }
			        }
		        }
	        }
	        } catch (SQLException e) {
	            System.out.println("MySQL操作错误");
	            e.printStackTrace();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		return ;
	}
	
	private boolean Selectsql3(String name , String name2){
		try {
			treepoint2 = new ArrayList<String>();
			size2 = new ArrayList<Double>();
	        //    System.out.println("成功加载MySQL驱动程序");
	            String sql = "select a.id from Check_hyper a where a.name = \"" + name + "\";";
	            ResultSet rs = stmt.executeQuery(sql);
	            while (rs.next()) {
	            	String temp2 = rs.getString(1);
	            	String sql2 = "select b.id from Check_hyper b where b.name = \"" + name2 + "\";";
		            Statement stmt2 = conn.createStatement();
		            ResultSet rs2 = stmt2.executeQuery(sql2);
		            while (rs2.next()) {
		            	String temp3 = rs2.getString(1);
		            	String sql3 = "select * from check_synonym c where c.Word1_id = " + temp2 + " and c.Word2_id = " + temp3 +";";
			            Statement stmt3 = conn.createStatement();
			            ResultSet rs3 = stmt3.executeQuery(sql3);
			            while (rs3.next()) {
			            	return true;
			            }
		            	//return true;
		            }
	            	//return true;
	            }
	        } catch (SQLException e) {
	            System.out.println("MySQL操作错误");
	            e.printStackTrace();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		return false;
	}
	
	private boolean Selectsql4(String name , String name2){
		try {
			treepoint2 = new ArrayList<String>();
			size2 = new ArrayList<Double>();

	            String sql = "select a.id from Check_hyper a where a.name = \"" + name + "\";";
	            ResultSet rs = stmt.executeQuery(sql);
	            while (rs.next()) {
	            	String temp2 = rs.getString(1);
	            	String sql2 = "select b.id from Check_hyper b where b.name = \"" + name2 + "\";";
		            Statement stmt2 = conn.createStatement();
		            ResultSet rs2 = stmt2.executeQuery(sql2);
		            while (rs2.next()) {
		            	String temp3 = rs2.getString(1);
		            	String sql3 = "select c.correct_count from check_hyperhierarchy c where c.hyper_child_id = " + temp2 + " and c.hyper_parent_id = " + temp3 +";";
			            Statement stmt3 = conn.createStatement();
			            ResultSet rs3 = stmt3.executeQuery(sql3);
			            while (rs3.next()) {
			            	if (Integer.parseInt(rs3.getString(1)) >= 0){
			            		return true;
				            }else {
								return false;
							}
			            	
			            }
		            	//return true;
		            }
	            	//return true;
	            }
	        } catch (SQLException e) {
	            System.out.println("MySQL操作错误");
	            e.printStackTrace();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		return false;
	}
	
	private void Selectsql5(String name){
		try {
				treepoint3 = new ArrayList<String>();
				//    System.out.println("成功加载MySQL驱动程序");
	            String sql = "select a.id from Check_hyper a where a.name = \"" + name + "\";";
	            ResultSet rs = stmt.executeQuery(sql);
	            while (rs.next()) {
	            	String temp2 = rs.getString(1);
	            	String sql2 = "select c.Word2_id from check_synonym c where c.Word1_id = " + temp2 + ";";
		            Statement stmt2 = conn.createStatement();
		            ResultSet rs2 = stmt2.executeQuery(sql2);
		            while (rs2.next()) {
		            	String temp3 = rs2.getString(1);
		            	String sql3 = "select b.name from Check_hyper b where b.id = " + temp3 + ";";
			            Statement stmt3 = conn.createStatement();
			            ResultSet rs3 = stmt3.executeQuery(sql3);
			            while (rs3.next()) {
			            	treepoint3.add(rs3.getString(1));
			            }
		            }
	            }
	        } catch (SQLException e) {
	            System.out.println("MySQL操作错误");
	            e.printStackTrace();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	}
	
	private boolean Selectsql6(String name){
		try {
				treepoint3 = new ArrayList<String>();
	            String sql = "select a.id from Check_hyper a where a.name = \"" + name + "\";";
	            ResultSet rs = stmt.executeQuery(sql);
	            while (rs.next()) {
	            	String temp2 = rs.getString(1);
	            	String sql2 = "select c.hyper_child_id from check_hyperhierarchy c where c.hyper_parent_id = " + temp2 + ";";
		            Statement stmt2 = conn.createStatement();
		            ResultSet rs2 = stmt2.executeQuery(sql2);
		            while (rs2.next()) {
		            	String temp3 = rs2.getString(1);
		            	String sql3 = "select b.name from Check_hyper b where b.id = " + temp3 + ";";
			            Statement stmt3 = conn.createStatement();
			            ResultSet rs3 = stmt3.executeQuery(sql3);
			            while (rs3.next()) {
			            	treepoint3.add(rs3.getString(1));
			            }
		            }
	            }
	        } catch (SQLException e) {
	            System.out.println("MySQL操作错误");
	            e.printStackTrace();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		return false;
	}
	
	private void Selectsql7(String name){
		try {
				treepoint3 = new ArrayList<String>();
				//    System.out.println("成功加载MySQL驱动程序");
				//select b.name,e.name from check_synonym c ,Check_hyper b ,check_hyperhierarchy d ,Check_hyper e where b.name = \"" + name + "\" and b.id = c.Word2_id and d.hyper_child_id = c.Word2_id and d.hyper_parent_id = e.id;
	            String sql = "select b.id from Check_hyper b where b.name = \"" + name + "\";";
	            ResultSet rs = stmt.executeQuery(sql);
	            while (rs.next()) {
	            	String temp2 = rs.getString(1);
	            	String sql2 = "select c.Hyper_parent_id from check_hyperhierarchy c where c.Hyper_child_id = " + temp2 + ";";
		            Statement stmt2 = conn.createStatement();
		            ResultSet rs2 = stmt2.executeQuery(sql2);
		            while (rs2.next()) {
		            	String temp3 = rs2.getString(1);
		            	String sql3 = "select b.name from Check_hyper b where b.id = " + temp3 + ";";
			            Statement stmt3 = conn.createStatement();
			            ResultSet rs3 = stmt3.executeQuery(sql3);
			            while (rs3.next()) {
			            	treepoint3.add(rs3.getString(1));
			            }
		            }
	            }
	        } catch (SQLException e) {
	            System.out.println("MySQL操作错误");
	            e.printStackTrace();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	}
}
