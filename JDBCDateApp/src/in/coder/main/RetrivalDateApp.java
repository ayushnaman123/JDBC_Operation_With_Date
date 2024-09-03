package in.coder.main;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import in.coder.util.JdbcUtil;

public class RetrivalDateApp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		Scanner scanner = null;
		
		int id =0;
		try {
			connection = JdbcUtil.getJdbcConnection();
			
			String sqlSelectQuery = "select sid, sname, sage, sdob, sdom from studentdata where sid=?";
			if (connection != null)
				pstmt = connection.prepareStatement(sqlSelectQuery);
			
			if(pstmt!=null) {
				scanner = new Scanner(System.in);
				if(scanner !=null) {
					System.out.println("Enter the id: ");
					id = scanner.nextInt();
					
					pstmt.setInt(1, id);
					resultSet = pstmt.executeQuery();
				}
			}
			
			if(resultSet!=null) {
				if(resultSet.next()) {
					System.out.println("SID\tSNAME\tSAGE\tSDOB\tSDOM");
					int sid = resultSet.getInt(1);
					String sname = resultSet.getString(2);
					int sage = resultSet.getInt(3);
					java.sql.Date dob = resultSet.getDate(4);
					java.sql.Date dom = resultSet.getDate(5);
					
					SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
					String sdob = sdf.format(dob);
					String sdom = sdf.format(dom);
					
					System.out.println(sid+"\t"+sname+"\t"+sage+"\t"+sdob+"\t"+sdom);
					
				}
			}
			
		} catch (IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				JdbcUtil.cleanUp(connection, pstmt, resultSet);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			scanner.close();
		}

	}

}
