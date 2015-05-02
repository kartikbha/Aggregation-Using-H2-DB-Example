package bolt;

import h2.H2DBAPIService;

import com.fasterxml.jackson.databind.ObjectMapper;

import common.CommonListner;

public class AggregationBolt extends CommonListner {

	public static void main(String[] args) {
		// starting listener to accept the tuples.
		startListener();
		ObjectMapper mapper = new ObjectMapper();
		// this while loop will mimic execute method of Bolt.
		while (true) {
			Tuple tuple = receiveTuple(mapper);
			if (tuple != null) {
				// #########################################
				// start bolt logic from here
				execute(tuple);
				// ###########################################
				// end bolt logic from here
			}
		}
	}

	/**
	 * 
	 * Write your entire business logic in below execute method.
	 * 
	 * @param tuple
	 */
	private static void execute(Tuple tuple) {

		String reqID = tuple.getReqID();
		String subQID = tuple.getSubQueryID();
		String queryXML = tuple.getQueryXML();
		Integer totalNoOfSubQuery = tuple.getTotalNoOfSubQuery();
		Integer noOfChunkInSubQuery = tuple.getNoOfChunkInSubQuery();
		Integer noOfReceivingChunk = tuple.getNoOfReceivingChunk();
		String key = AggregationBoltHelper.prepareKey(reqID, subQID);
		if (key != null) {

			// 123 has total 2.
			saveSubQueryCount(key, totalNoOfSubQuery);
			// 123-1 has total 3 
			saveChunkCount(key, noOfChunkInSubQuery);
			saveXML(key, queryXML);
			
			// 123-1, 50
			saveChunk(key, noOfReceivingChunk);
			// aggregate into parent.
			boolean parentUpdated = checkAndUpdateParent(reqID, key);

		}

	}

	private static void saveChunk(String key, Integer chunk) {
		AggregationBoltHelper.saveCount(key, chunk);
	}
	private static void saveXML(String key, String queryXML) {
		AggregationBoltHelper.processQueryXML(key, queryXML);
	}

	// sub query 123-1 has 3 chunk of each upto 50
	private static void saveChunkCount(String key, Integer noOfChunkInSubQuery) {
		AggregationBoltHelper.processChunkCountInSubQuery(key, noOfChunkInSubQuery);
	}

	// query 123 has total 3 subquery of name 123-1, 123-2,123-3
	private static void saveSubQueryCount(String key, Integer totalNoOfSubQuery) {
		AggregationBoltHelper.processSubQueryCount(key, totalNoOfSubQuery);
	}

	// once 123-1 is completed, update 123.
	// once 123-2 is completed, update 123.
	// once 123-2 is completed, update 123.
	private static boolean checkAndUpdateParent(String reqID, String subKey) {
		// query using this subKey
		// check if numberOfSubDivision == running count
		boolean isParentUpdateRequired = AggregationBoltHelper.compareCount(subKey);
		if (isParentUpdateRequired) {
			// if yes. aggregate in parent reqID key
			// select total count using sub key
			int totalCount = H2DBAPIService.fetchTotalCount(subKey);
			H2DBAPIService.updateTotalCount(reqID, totalCount);
			return true;
		} else {
			return false;
		}

	}

}
