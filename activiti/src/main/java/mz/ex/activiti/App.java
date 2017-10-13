package mz.ex.activiti;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) throws SQLException {
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	}

	private static Connection testQuery() throws SQLException {
		String url = "jdbc:postgresql://192.168.99.100/activiti?user=root&password=root";
		Connection conn = DriverManager.getConnection(url);
		
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("SELECT * FROM mytable WHERE columnfoo = 500");
		while (rs.next()) {
		    System.out.println("Values: ");
		    System.out.println(rs.getInt(1));
		    System.out.println(rs.getString(2));
		}
		rs.close();
		st.close();
		return conn;
	}
}
