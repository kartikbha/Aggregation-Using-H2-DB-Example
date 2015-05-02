package h2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.h2.tools.Server;

/**
 * 
 * @author Kartik_Bhatnagar
 *
 */
public class H2DBAPI {

	private static String dbURL = "jdbc:h2:tcp://localhost/~/test;DATABASE_TO_UPPER=false";
	private static Server server;

	// jdbc Connection

	protected static void runSqlCommand(String sql) {
		Statement stmt = null;
		Connection conn = createConnection();
		try {
			stmt = conn.createStatement();
			stmt.execute(sql);
		} catch (SQLException e) {
			System.out.println(" doUpdate " + e);
		} finally {
			try {

				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				System.out.println(" error in finally  runSqlCommand" + e);
			}

		}
	}

	protected static int doSelect(String sql) {
		int rowCount = 0;
		Statement stmt = null;
		ResultSet result = null;
		Connection conn = createConnection();
		try {
			stmt = conn.createStatement();
			result = stmt.executeQuery(sql);
			if (result.next()) {
				rowCount = result.getInt(1);
			}
			return rowCount;
		} catch (SQLException e) {
			System.out.println(" doSelect " + e);
			return rowCount;
		} finally {
			try {
				if (result != null)
					result.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				System.out.println(" error in finally  doSelect" + e);
			}

		}
	}

	protected static Map<String, Integer> doSelect(String sql,
			List<String> values) {
		Statement stmt = null;
		ResultSet result = null;
		Map<String, Integer> valuesResult = new HashMap<String, Integer>();
		Connection conn = createConnection();
		try {
			stmt = conn.createStatement();
			result = stmt.executeQuery(sql);
			if (result.next()) {
				for (String value : values) {
					valuesResult.put(value, result.getInt(value));
				}
			}
			return valuesResult;

		} catch (SQLException e) {
			System.out.println(" doSelect " + e);
			return valuesResult;
		} finally {
			try {
				if (result != null)
					result.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				System.out.println(" error in finally  doSelect" + e);
			}

		}

	}

	private static Connection createConnection() {
		Connection conn = null;
		
		try {
			Class.forName("org.h2.Driver").newInstance();
			// Get a connection
			conn = DriverManager.getConnection(dbURL, "admin", "admin");
		} catch (Exception except) {
			System.out.println(" createConnection error " + except);
		}
		return conn;
	}

}