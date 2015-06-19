import java.sql.*;

public class DatabaseHandler {
	
	static Connection conn = null;
	static Statement stmt = null;
	static ResultSet rs = null;
	PreparedStatement ps=null;
	static String connectionUrl = "jdbc:mysql://localhost:3306/social_messenger";
	static String connectionUser = "root";
	static String connectionPassword = "951753";	

	/*
	public static void main(String[] args) {
		
		//getRegistrationNumber("newdoctor");
		System.out.println(getChatroomContents());
	}
	*/
	public static int getRegistrationNumber(String name){
		int i=0;
		try{
			String n=name;
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(connectionUrl, connectionUser, connectionPassword);
			stmt = conn.createStatement();			
			rs=stmt.executeQuery("SELECT * FROM user WHERE name=\""+n+"\"");
			if(rs.next())
			{
				i=rs.getInt(1);
				System.out.println("Registration Number: "+ i);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
			try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
			try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
		}		
		return i;
	}
	
	public static String getChatroomContents(){
		String contents="";
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(connectionUrl, connectionUser, connectionPassword);
			stmt = conn.createStatement();			
			rs=stmt.executeQuery("SELECT * FROM chatrooms WHERE chatroom_ID=1");
			if(rs.next())
			{
				contents=rs.getString(2);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
			try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
			try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
		}		
		return contents;
	}
	
	public void sendMessage(String message){
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(connectionUrl, connectionUser, connectionPassword);
			stmt = conn.createStatement();		
			PreparedStatement ps=conn.prepareStatement("update chatrooms set chatroom_contents= CONCAT(chatroom_contents, ?) where chatroom_ID=?");
			ps.setString(1, message+"\n");
			ps.setInt(2, 1);
			ps.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}    	
		finally {
			try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
			try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
			try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
		}	
	}
}
