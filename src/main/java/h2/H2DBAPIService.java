package h2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Kartik_Bhatnagar
 *
 */
public class H2DBAPIService extends H2DBAPI {

	public static void insertNoOfSubDivision(String key,
			Integer noOfChunkInSubQuery_noOfSubQuery) {
		String sql = "insert into aggregationtable(key,noOfSubDivision) values ('"
				+ key + "'," + noOfChunkInSubQuery_noOfSubQuery + ")";
		// System.out.println(" sql : " + sql);
		runSqlCommand(sql);
	}

	public static void updateNoOfSubDivision(String key,
			Integer noOfChunkInSubQuery_noOfSubQuery) {
		String sql = "update ControlTable set noOfSubDivision = "
				+ noOfChunkInSubQuery_noOfSubQuery + " Where key = '" + key
				+ "'";
		// System.out.println("Update sql : " + sql);
		runSqlCommand(sql);

	}

	public static void insertQueryXML(String key, String queryXML) {
		String sql = "insert into aggregationtable (key,queryXML) values('" + key
				+ "', '" + queryXML + "')";
		// System.out.println("Insert sql : " + sql);
		runSqlCommand(sql);
	}

	public static void updateQueryXML(String key, String queryXML) {
		if (queryXML != null) {
			String sql = "update aggregationtable SET queryXML ='" + queryXML
					+ "' where key ='" + key + "'";
			runSqlCommand(sql);
		}

	}
	
	public static int processCountQuery(String key) {
		String sql = "select count(1) AS Total from aggregationtable where key ='"
				+ key + "'";
		return doSelect(sql);
	}

	public static boolean processCompareCount(String key) {
		String sql = "select noOfSubDivision, runningCount  from aggregationtable where key ='"
				+ key + "'";
		List<String> values = new ArrayList<String>();
		values.add("noOfSubDivision");
		values.add("runningCount");
		Map<String, Integer> valueResults = doSelect(sql, values);
		int subdivision = valueResults.get("noOfSubDivision");
		int runningCount = valueResults.get("runningCount");
		return subdivision == runningCount;
	}

	public static int fetchTotalCount(String key) {
		String sql = "select totalCount from aggregationtable where key ='" + key
				+ "'";	
		List<String> values = new ArrayList<String>();
		values.add("totalCount");		
		return doSelect(sql,values).get("totalCount");
	}

	
	public static void insertTotalCount(String key, Integer totalCount) {
		String sql = "insert into aggregationtable (key,totalCount,runningCount) values ('"
				+ key + "'," + totalCount + ",1)";
		// System.out.println("Insert total sql : " + sql);
		runSqlCommand(sql);

	}

	public static void updateTotalCount(String key, Integer totalCount) {
		if (totalCount != null) {
			String sql = "update aggregationtable set totalCount = totalCount +"
					+ totalCount
					+ " , runningCount = runningCount+1  where key ='" + key
					+ "'";
			System.out.println("Update total sql : " + sql);
			runSqlCommand(sql);
		}

	}


}