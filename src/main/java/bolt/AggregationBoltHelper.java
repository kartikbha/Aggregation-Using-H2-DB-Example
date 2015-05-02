package bolt;

import h2.H2DBAPI;
import h2.H2DBAPIService;

public class AggregationBoltHelper {

	protected static String prepareKey(String reqID, String subQID) {
		String key = null;
		if (reqID != null && subQID != null) {
			key = reqID + "-" + subQID;
		} else {
			key = reqID;
		}
		return key;
	}

	protected static int checkCount(String key) {
		// TODO Auto-generated method stub
		if (key != null) {
			return H2DBAPIService.processCountQuery(key);
		}
		return 0;
	}

	protected static boolean compareCount(String key) {
		if (key != null) {
			return H2DBAPIService.processCompareCount(key);
		}
		return false;
	}
	protected static void processQueryXML(String key, String queryXML) {
		int resCount = checkCount(key);
		if (resCount >= 1) {
			H2DBAPIService.updateQueryXML(key, queryXML);
		} else {
			H2DBAPIService.insertQueryXML(key, queryXML);
		}
	}

	protected static void processChunkCountInSubQuery(String key,
			Integer noOfChunkInSubQuery) {
		if (noOfChunkInSubQuery != null) {
					int resCount = checkCount(key);
			if (resCount >= 1) {
				H2DBAPIService.updateNoOfSubDivision(key, noOfChunkInSubQuery);

			} else {
				H2DBAPIService.insertNoOfSubDivision(key, noOfChunkInSubQuery);
			}
		}
	}

	protected static void processSubQueryCount(String key,
			Integer totalNoOfSubQuery) {
		if (totalNoOfSubQuery != null) {
			// save in table reqID + totalNoOfSubQuery
			int resCount = checkCount(key);
			if (resCount >= 1) {
				H2DBAPIService.updateNoOfSubDivision(key, totalNoOfSubQuery);
			} else {
				H2DBAPIService.insertNoOfSubDivision(key, totalNoOfSubQuery);

			}
		}

	}


	public static void saveCount(String key, Integer totalCount) {
		// TODO Auto-generated method stub
		if (totalCount != null) {
			int resCount = checkCount(key);
			//System.out.println("Key : " + key + " Count : " + resCount);
			if (resCount >= 1) {
				H2DBAPIService.updateTotalCount(key, totalCount);
			} else {
				H2DBAPIService.insertTotalCount(key, totalCount);

			}
		}

	}

}
