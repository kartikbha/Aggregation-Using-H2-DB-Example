package bolt.test;

import org.junit.Test;

import bolt.Tuple;

import common.Dispatcher;

public class AggregationBoltJunitTest {

	@Test
	public void testWithTwoSubQuery() throws InterruptedException {

		Tuple tuple = new Tuple();
		// 123
		tuple.setReqID("123");
		tuple.setTotalNoOfSubQuery(2);
		Dispatcher.send(tuple);

		tuple = new Tuple();
		tuple.setReqID("123");
		tuple.setQueryXML("<xml> xml for main xml </xml>");
		Dispatcher.send(tuple);

		System.out.println(" total subquery  " + 2);

		// 123-1 ======> total chunk 3
		tuple = new Tuple();
		tuple.setReqID("123");
		tuple.setNoOfChunkInSubQuery(3);
		tuple.setSubQueryID("1");
		Dispatcher.send(tuple);

		// query 123-1 =========> query xml
		tuple = new Tuple();
		tuple.setReqID("123");
		tuple.setSubQueryID("1");
		tuple.setQueryXML("<xml> xml for subquery 1 </xml>");
		Dispatcher.send(tuple);
		System.out.println(" total chunk in subquery 123-1 is 3  ");
		
		// 123-1  ===========> 50
		tuple = new Tuple();
		tuple.setReqID("123");
		tuple.setSubQueryID("1");
		tuple.setNoOfReceivingChunk(50);
		Dispatcher.send(tuple);

		// 123-1  ==============> 50
		tuple = new Tuple();
		tuple.setReqID("123");
		tuple.setSubQueryID("1");
		tuple.setNoOfReceivingChunk(50);
		Dispatcher.send(tuple);

		// 123-1 ===========>  50
		tuple = new Tuple();
		tuple.setReqID("123");
		tuple.setSubQueryID("1");
		tuple.setNoOfReceivingChunk(50);
		Dispatcher.send(tuple);
		System.out.println(" total record in subquery 123-1  " + 150);
		
		// total 123-1 ==========> 150
		tuple = new Tuple();
		tuple.setReqID("123");
		tuple.setSubQueryID("2");
		tuple.setQueryXML("<xml> xml for subquery 2 </xml>");
		Dispatcher.send(tuple);
	
		
		// 123-2 ======> total chunk 2
		tuple = new Tuple();
		tuple.setReqID("123");
		tuple.setNoOfChunkInSubQuery(2);
		tuple.setSubQueryID("2");
		Dispatcher.send(tuple);
		
		// 123-2   ============> 50
		tuple = new Tuple();
		tuple.setReqID("123");
		tuple.setSubQueryID("2");
		tuple.setNoOfReceivingChunk(50);
		Dispatcher.send(tuple);

		// 123-2  ==============> 49
		tuple = new Tuple();
		tuple.setReqID("123");
		tuple.setSubQueryID("2");
		tuple.setNoOfReceivingChunk(49);
		Dispatcher.send(tuple);

		// total  123-2 ==========> 99.
		System.out.println(" total record in subquery 123-2  " + 99);

	}

	
}
