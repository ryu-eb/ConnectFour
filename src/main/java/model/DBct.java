package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import board.Board;


public class DBct {
	private Connection con;
	private String sql;
	PreparedStatement ps = null;
	ResultSet rs = null;
	ArrayList<Board> list = null;
	
	public DBct() throws SQLException{
		String url = "jdbc:mysql://localhost:3306/mydb?serverTimezone=UTC";
		String user = "root";
		String pass ="root";
		con = DriverManager.getConnection(url, user, pass);
	}
	
	public void close() throws SQLException {
		try {
			if(con != null) con.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public ArrayList<Board> getAll() throws SQLException{
		sql = "SELECT * FROM fieldtb";
		ps = con.prepareStatement(sql);
		return search(ps);
	}
	
	public Board getWhoAsBoard(String str) throws SQLException{
		sql = "SELECT " + str + " FROM fieldtb WHERE id = 1";
		ps = con.prepareStatement(sql);
		Board bd = null;
		try {
			rs = ps.executeQuery();
			if (rs.next()) { /**DBにid=1しか存在しないし追加もしないからこれでok*/
				long vl = rs.getLong(str);
				if (vl > 0) bd = new Board(rs.getLong(str),str);
				else bd = new Board(0,str);
			}
		}finally {
			ps.close();
		}
		
		return bd;
	}
	
	private ArrayList<Board> search(PreparedStatement ps) throws SQLException{
		list = new ArrayList<>();
		try {
			rs = ps.executeQuery();
			if (rs.next()) { /**DBにid=1しか存在しないし追加もしないからこれでok*/
				list.add(new Board(rs.getLong("my")));
				list.add(new Board(rs.getLong("opp"),"opp"));
			}
		}finally {
			ps.close();
		}
		
		return list;
	}
	
	public int update(String col, long val) throws SQLException{
		sql = "UPDATE fieldtb SET " + col + " = ? where id = 1;";
		int n = 0;
		try {
			ps = con.prepareStatement(sql);
			ps.setLong(1,val);
			n = ps.executeUpdate();
		}finally {
			ps.close();
		}
		
		return n;
	}
	
	public int initialize() throws SQLException{
		sql = "UPDATE fieldtb SET my = 0, opp = 0 where id = 1;";
		int n = 0;
		try {
			ps = con.prepareStatement(sql);
			n = ps.executeUpdate();
		}finally {
			ps.close();
		}
		
		return n;
	}
}
