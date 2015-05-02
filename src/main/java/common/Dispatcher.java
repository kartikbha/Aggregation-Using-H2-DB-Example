package common;

import org.zeromq.ZMQ;

import bolt.Tuple;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Dispatcher {

	public static void send(Tuple tuple) throws InterruptedException {
		final ZMQ.Context ctx = ZMQ.context(1);
		final ZMQ.Socket pub = ctx.socket(ZMQ.PUSH);
		pub.bind("tcp://localhost:6001");
		// pub.setHWM(1000000);
		// pub.setSndHWM(1000000);
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		try {
			json = mapper.writeValueAsString(tuple);
		} catch (JsonProcessingException e) {

		}
		System.out.println(" sending --" + json);
		pub.send(json);
		pub.close();
		ctx.term();

	}
}