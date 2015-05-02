package common;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;

import bolt.Tuple;

import com.fasterxml.jackson.databind.ObjectMapper;

abstract public class CommonListner {

	private static LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<String>();

	protected static Tuple receiveTuple(ObjectMapper mapper) {
		String inputTupleStr = queue.poll();
		Tuple tuple = null;
		if (inputTupleStr != null) {
			// bolt logic will written below
			//System.out.println(" input --->   " + inputTupleStr);
			try {
				tuple = mapper.readValue(inputTupleStr, Tuple.class);
				// System.out.println(" key    " + tuple.getKey());
			} catch (IOException e) {
				System.out.println(" exception.............  " + e);
			}
		}
		return tuple;
	}

	protected static void startListener() {
		InputSpout inputSpout = new InputSpout(queue);
		Thread inputSpoutThread = new Thread(inputSpout);
		inputSpoutThread.start();
	}

}
