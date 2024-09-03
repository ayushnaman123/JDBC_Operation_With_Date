package in.coder.main;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import in.coder.util.JdbcUtil;

public class InsertDateApp {
	public static void main(String[] args) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		Scanner scanner = null;
		
		String name = null;
		int age = 0;
		String dob = null;
		String dom = null;
		
		java.sql.Date sqlDob = null;
		java.sql.Date sqlDom = null;
		try {
			connection = JdbcUtil.getJdbcConnection();
			
			String sqlInsertQuery = "Insert into studentdata(`sname`, `sage`, `sdob`, `sdom`) values(?,?,?,?)";
			if(connection!=null)
				pstmt = connection.prepareStatement(sqlInsertQuery);
			
			if(pstmt!=null) {
				
				scanner = new Scanner(System.in);
				if(scanner != null) {
					System.out.println("Enter the name: ");
					name = scanner.next();
					
					System.out.println("Enter the age: ");
					age = scanner.nextInt();
					
					System.out.println("Enter the dob(dd-MM-yyyy)");
					dob = scanner.next();
					
					System.out.println("Enter the dom(yyyy-MM-dd)");
					dom = scanner.next();
					
				}
				
				if(dob!=null) {
					SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
					java.util.Date uDate = sdf.parse(dob);
					
					long values = uDate.getTime();
					sqlDob = new java.sql.Date(values); 
				}
				
				if(dom != null) {
					sqlDom = java.sql.Date.valueOf(dom);
				}
				
				//set the input values to the query
				pstmt.setString(1, name);
				pstmt.setInt(2, age);
				pstmt.setDate(3, sqlDob);
				pstmt.setDate(4, sqlDom);
				
				int rowsAffected = pstmt.executeUpdate();
				System.out.println("No of rows Affected: "+rowsAffected);
				
				
			}
		} catch (IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			try {
				JdbcUtil.cleanUp(connection, pstmt, null);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			scanner.close();
		}
	}
}
