package bolt;

import java.io.Serializable;

public class Tuple implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 123
	private String reqID;
	// 1,2
	private String subQueryID;

	private String queryXML;
	// 2
	private Integer totalNoOfSubQuery;
	// 3
	private Integer noOfChunkInSubQuery;
	// 50, 50 50....
	private Integer noOfReceivingChunk;

	public Integer getTotalNoOfSubQuery() {
		return totalNoOfSubQuery;
	}

	public void setTotalNoOfSubQuery(Integer totalNoOfSubQuery) {
		this.totalNoOfSubQuery = totalNoOfSubQuery;
	}

	public Integer getNoOfChunkInSubQuery() {
		return noOfChunkInSubQuery;
	}

	public void setNoOfChunkInSubQuery(Integer noOfChunkInSubQuery) {
		this.noOfChunkInSubQuery = noOfChunkInSubQuery;
	}

	public Integer getNoOfReceivingChunk() {
		return noOfReceivingChunk;
	}

	public void setNoOfReceivingChunk(Integer noOfReceivingChunk) {
		this.noOfReceivingChunk = noOfReceivingChunk;
	}

	public String getReqID() {
		return reqID;
	}

	public void setReqID(String reqID) {
		this.reqID = reqID;
	}

	public String getSubQueryID() {
		return subQueryID;
	}

	public void setSubQueryID(String subQueryID) {
		this.subQueryID = subQueryID;
	}

	public String getQueryXML() {
		return queryXML;
	}

	public void setQueryXML(String queryXML) {
		this.queryXML = queryXML;
	}

}
